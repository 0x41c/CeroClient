//
// Created by 0x41c on 2022-06-30.
//

#ifndef CEROCLIENT_HEADER_FUNCTIONS_H
#define CEROCLIENT_HEADER_FUNCTIONS_H

#include "mh_util.h"
#include <mach-o/dyld_images.h>
#include <mach-o/loader.h>
#include <mach-o/nlist.h>
#include <string.h>
#include <dlfcn.h>

// Non-arch specific macho command's
#define SM symtab_command *
#define LC load_command *

char argument[ARGUMENT_MAX_LENGTH] = ARGUMENT_MAGIC_STR;

// 64-bit structures
#if defined(__aarch64__) || defined(__x86_64__) // TODO: Test if the __aarch64__ flag actually works lol
dyld_all_image_infos *dyld_info = (dyld_all_image_infos *)DYLD_MAGIC_64;
#define SEG segment_command_64 *
#define NL nlist_64 *
#define MH mach_header_64 *
#define LC_SEG LC_SEGMENT_64
#else

// 32-bit structures
dyld_all_image_infos *dyld_info = (dyld_all_image_infos *)DYLD_MAGIC_32;
#define SEG segment_command *
#define NL nlist *
#define MH mach_header *
#define LC_SEG LC_SEGMENT

#endif

static const struct MH get_header_by_path(const char *name)
{
    for (unsigned i = 0; i < dyld_info->infoArrayCount; i++)
        if (strcmp(dyld_info->infoArray[i].imageFilePath, name) == 0)
            return (const struct MH) dyld_info->infoArray[i].imageLoadAddress;

    return NULL;
}

static const void *get_symbol_from_header(const struct MH header, const char *symbol)
{
    if (!header) {
        return NULL;
    }

    /* Get the required commands */

    const struct SM symtab = NULL;
    const struct SEG first = NULL;
    const struct SEG linkedit = NULL;
    const struct LC cmd = (LC)(header + 1);

    for (unsigned i = 0; i < header->ncmds; i++, cmd = (LC) ((char*) cmd + cmd->cmdsize)) {
        if (cmd->cmd == LC_SEGMENT_64) {
            if (!first && ((SEG)cmd)->filesize ) first = (SEG) cmd;
            if (strcmp(((SEG) cmd)->segname, "__LINKEDIT") == 0) linkedit = (SEG) cmd;
        } else if (cmd->cmd == LC_SYMTAB) symtab = (SM) cmd;
        if (symtab && linkedit) break;
    }
    if (!symtab || !linkedit) return NULL;

    const char *string_table =
            ((const char *) header + symtab->stroff - linkedit->fileoff + linkedit->vmaddr - first->vmaddr);
    const struct NL symbols = (NL)
            ((const char *) header + symtab->symoff - linkedit->fileoff + linkedit->vmaddr - first->vmaddr);

    for (unsigned i = 0; i < symtab->nsyms; i++)
        if (strcmp(string_table + symbols[i].n_un.n_strx, symbol) == 0)
            return (char *)header + symbols[i].n_value - first->vmaddr;

    return NULL;
}

/* Taken from Apple's libc */

int strcmp(const char *s1, const char *s2) {
    while (*s1 == *s2++)
        if (*s1++ == 0) return (0);
    return (*(const unsigned char *)s1 - *(const unsigned char *)(s2 - 1));
}


#endif //CEROCLIENT_HEADER_FUNCTIONS_H

