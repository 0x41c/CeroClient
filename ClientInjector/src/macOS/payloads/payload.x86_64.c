//
// Created by 0x41c on 2022-06-29.
//

#include <macOS/lookup/mh_functions.h>

uint64_t get_flags(void);
void set_flags(uint64_t);
__asm__ (
    "_get_flags:    \n"
    "    pushfq     \n"
    "    pop %rax   \n"
    "    ret        \n"
    "_set_flags:    \n"
    "    push %rdi  \n"
    "    popfq      \n"
    "    ret        \n"
);


__attribute__((preserve_all))
void entry()
{
    // Store to-be-clobbered flags.
    uint64_t flags = get_flags();

    typedef void *(*dlopen_t)(const char *, int);
    dlopen_t *$dlopen;

    /* We can't call dyld`dlopen when dyld3 is being used, so we must find libdyld`dlopen and call that instead */
    $dlopen = get_symbol_from_header(get_header_by_path("/usr/lib/system/libdyld.dylib"), "_dlopen");

    if ($dlopen) (*$dlopen)(argument, RTLD_NOW);

    // Restore flags.
    set_flags(flags);
}
