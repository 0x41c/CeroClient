//
// Created by 0x41c on 2022-06-29.
//

#ifndef CEROCLIENT_MACOS_H
#define CEROCLIENT_MACOS_H

#include <ClientLoader.h>
#include <pthread.h>

#if false
#include <mach/mach.h>
#include <mach/mach_init.h>
#include <mach/thread_act.h>

#define DLLExport __attribute__((visibility("default")))

extern "C" void _pthread_set_self(void*);
extern "C" void bootstrap(ptrdiff_t offset, void *param, size_t psize, void *dummy) DLLExport;

void *callEntry(void *_unused);
#endif

#endif //CEROCLIENT_MACOS_H
