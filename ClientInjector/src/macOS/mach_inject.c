// mach_inject.c semver:1.2.0
//   Copyright (c) 2003-2012 Jonathan 'Wolf' Rentzsch: http://rentzsch.com
//   Some rights reserved: http://opensource.org/licenses/mit
//   https://github.com/rentzsch/mach_inject
// -----
// Edited by 0x41c on 6/28/22 to fix warnings and unused if-blocks/imports.

#ifdef __APPLE__

#include <macOS/mach_inject.h>

#include <mach-o/dyld.h>
#include <mach-o/getsect.h>
#include <mach/mach.h>
#include <sys/stat.h>
#include <sys/errno.h>
#include <assert.h>
#include <stdio.h>          // for printf()
#include <mach-o/fat.h>     // for fat structure decoding
#include <mach-o/arch.h>    // to know which is local arch
#include <fcntl.h>          // for open
#include <unistd.h>         // for close
#include <sys/mman.h>

#ifndef	COMPILE_TIME_ASSERT
#define COMPILE_TIME_ASSERT( exp ) { switch (0) { case 0: case (exp):; } }
#endif
#define ASSERT_CAST( CAST_TO, CAST_FROM ) \
COMPILE_TIME_ASSERT( sizeof(CAST_TO)==sizeof(CAST_FROM) )
#include <mach/MACH_ERROR.h>
#define MACH_ERROR(msg, err) { if((err) != err_none) mach_error(msg, err); }

/*******************************************************************************
 *
 *	Interface
 *
 *******************************************************************************/
#pragma mark	-
#pragma mark	(Interface)

mach_error_t
mach_inject(
        const mach_inject_entry	threadEntry,
        const void				*paramBlock,
        size_t					paramSize,
        pid_t					targetProcess,
        vm_size_t				stackSize )
{
    assert( threadEntry );
    assert( targetProcess > 0 );
    assert( stackSize == 0 || stackSize > 1024 );

    //	Find the image.
    const void		*image;
    unsigned long	imageSize;
    unsigned int	jumpTableOffset;
    unsigned int	jumpTableSize;
    mach_error_t	err = machImageForPointer( threadEntry, &image, &imageSize, &jumpTableOffset, &jumpTableSize );

    //	Initialize stackSize to default if requested.
    if( stackSize == 0 )
        /** @bug
         We only want an 8K default, fix the plop-in-the-middle code below.
         */
        stackSize = 16 * 1024;

    //	Convert PID to Mach Task ref.
    mach_port_t	remoteTask = 0;
    if( !err ) {
        err = task_for_pid( mach_task_self(), targetProcess, &remoteTask );
        if (err == 5) fprintf(stderr, "Could not access task for pid %d. You probably need to add user to procmod group\n", targetProcess);
    }

    /** @todo
     Would be nice to just allocate one block for both the remote stack
     *and* the remoteCode (including the parameter data block once that's
     written.
     */

    //	Allocate the remoteStack.
    vm_address_t remoteStack = (vm_address_t)NULL;
    if( !err )
        err = vm_allocate( remoteTask, &remoteStack, stackSize, 1 );

    vm_protect(remoteTask, remoteStack, stackSize, 0, VM_PROT_WRITE | VM_PROT_READ);

    //	Allocate the code.
    vm_address_t remoteCode = (vm_address_t)NULL;
    if( !err )
        err = vm_allocate( remoteTask, &remoteCode, imageSize, 1 );
    err = vm_protect(remoteTask, remoteCode, imageSize, 0, VM_PROT_EXECUTE | VM_PROT_WRITE | VM_PROT_READ);
    if( !err ) {
        ASSERT_CAST( pointer_t, image );
        err = vm_write( remoteTask, remoteCode, (pointer_t) image, imageSize );
    }

    //	Allocate the paramBlock if specified.
    vm_address_t remoteParamBlock = (vm_address_t)NULL;
    if( !err && paramBlock != NULL && paramSize ) {
        err = vm_allocate( remoteTask, &remoteParamBlock, paramSize, 1 );
        if( !err ) {
            ASSERT_CAST( pointer_t, paramBlock );
            err = vm_write( remoteTask, remoteParamBlock,
                            (pointer_t) paramBlock, paramSize );
            printf("wrote param with size %zu\n", paramSize);
        }
    }

    //	Calculate offsets.
    ptrdiff_t	threadEntryOffset, imageOffset;
    if( !err ) {
        assert( (void*)threadEntry >= image && (void*)threadEntry <= (image+imageSize) );
        ASSERT_CAST( void*, threadEntry );
        threadEntryOffset = ((void*) threadEntry) - image;

        imageOffset = 0; // RIP-relative addressing

    }

    //	Allocate the thread.
    thread_act_t remoteThread;


    if( !err ) {
		x86_thread_state64_t remoteThreadState;
		bzero( &remoteThreadState, sizeof(remoteThreadState) );

		vm_address_t dummy_thread_struct = remoteStack;
		remoteStack += (stackSize / 2); // this is the real stack
		// (*) increase the stack, since we're simulating a CALL instruction, which normally pushes return address on the stack
		remoteStack -= 4;

#define PARAM_COUNT 0
#define STACK_CONTENTS_SIZE ((1+PARAM_COUNT) * sizeof(unsigned long long))
		unsigned long long stackContents[1 + PARAM_COUNT]; // 1 for the return address and 1 for each param
		// first entry is return address (see above *)
		stackContents[0] = 0x00000DEADBEA7DAD; // invalid return address.

		// push stackContents
		err = vm_write( remoteTask, remoteStack,
                       (pointer_t) stackContents, STACK_CONTENTS_SIZE);

		remoteThreadState.__rdi = (unsigned long long) (imageOffset);
		remoteThreadState.__rsi = (unsigned long long) (remoteParamBlock);
		remoteThreadState.__rdx = (unsigned long long) (paramSize);
		remoteThreadState.__rcx = (unsigned long long) (dummy_thread_struct);

		// set remote Program Counter
		remoteThreadState.__rip = (unsigned long long) (remoteCode);
		remoteThreadState.__rip += threadEntryOffset;

		// set remote Stack Pointer
		ASSERT_CAST( unsigned long long, remoteStack );
		remoteThreadState.__rsp = (unsigned long long) remoteStack;

		// create thread and launch it
		err = thread_create_running( remoteTask, x86_THREAD_STATE64,
                                    (thread_state_t) &remoteThreadState, x86_THREAD_STATE64_COUNT,
                                    &remoteThread );
	}

    if( err ) {
        MACH_ERROR("mach_inject failing..", err);
        if( remoteParamBlock )
            vm_deallocate( remoteTask, remoteParamBlock, paramSize );
        if( remoteCode )
            vm_deallocate( remoteTask, remoteCode, imageSize );
        if( remoteStack )
            vm_deallocate( remoteTask, remoteStack, stackSize );
    }

    return err;
}

mach_error_t
machImageForPointer(
        const void *pointer,
        const void **image,
        unsigned long *size,
        unsigned int *jumpTableOffset,
        unsigned int *jumpTableSize )
{
    assert( pointer );
    assert( image );
    assert( size );

    unsigned long p = (unsigned long) pointer;

    if (jumpTableOffset && jumpTableSize) {
        *jumpTableOffset = 0;
        *jumpTableSize = 0;
    }

    unsigned long imageIndex, imageCount = _dyld_image_count();
    for( imageIndex = 0; imageIndex < imageCount; imageIndex++ ) {
        const struct mach_header_64 *header = (const struct mach_header_64 *)_dyld_get_image_header( imageIndex ); // why no function that returns mach_header_64
		const struct section_64 *section = getsectbynamefromheader_64( header, SEG_TEXT, SECT_TEXT );
        if (section == 0) continue;
        long start = section->addr + _dyld_get_image_vmaddr_slide( imageIndex );
        long stop = start + section->size;
        //		printf("start %ld %p %s b\n", start, header, _dyld_get_image_name(imageIndex));
        if( p >= start && p <= stop ) {
            //	It is truely insane we have to stat() the file system in order
            //	to discover the size of an in-memory data structure.
            const char *imageName = _dyld_get_image_name( imageIndex );
            //printf("image name: %s\n", imageName);
            assert( imageName );
            struct stat sb;
            if( stat( imageName, &sb ) )
                return unix_err( errno );
            if( image ) {
                ASSERT_CAST( void*, header );
                *image = (void*) header;
            }
            if( size ) {
                ;//assertUInt32( st_size );
                *size = sb.st_size;

                // needed for Universal binaries. Check if file is fat and get image size from there.
                int fd = open (imageName, O_RDONLY);
                size_t mapSize = *size;
                char * fileImage = mmap (NULL, mapSize, PROT_READ, MAP_FILE|MAP_SHARED, fd, 0);

                assert(fileImage != MAP_FAILED);
                struct fat_header* fatHeader = (struct fat_header *)fileImage;
                if (fatHeader->magic == OSSwapBigToHostInt32(FAT_MAGIC)) {
                    //printf("This is a fat binary\n");
                    uint32_t archCount = OSSwapBigToHostInt32(fatHeader->nfat_arch);

                    NXArchInfo const *localArchInfo = NXGetLocalArchInfo();

                    struct fat_arch* arch = (struct fat_arch *)(fileImage + sizeof(struct fat_header));
                    struct fat_arch* matchingArch = NULL;

                    int archIndex = 0;
                    for (archIndex = 0; archIndex < archCount; archIndex++) {
                        cpu_type_t cpuType = OSSwapBigToHostInt32(arch[archIndex].cputype);
                        cpu_subtype_t cpuSubtype = OSSwapBigToHostInt32(arch[archIndex].cpusubtype);

                        if (localArchInfo->cputype == cpuType) {
                            matchingArch = arch + archIndex;
                            if (localArchInfo->cpusubtype == cpuSubtype) break;
                        }
                    }

                    if (matchingArch) {
                        *size = OSSwapBigToHostInt32(matchingArch->size);
                        //printf ("found arch-specific size : %p\n", *size);
                    }
                }

                munmap (fileImage, mapSize);
                close (fd);
            }
            return err_none;
        }
    }

    return err_threadEntry_image_not_found;
}

#endif /* __APPLE__ */