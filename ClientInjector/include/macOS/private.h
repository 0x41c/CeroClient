//
// Created by 0x41c on 2022-06-29.
//

#ifndef CEROCLIENT_PRIVATE_H
#define CEROCLIENT_PRIVATE_H

// Include List
#include <dlfcn.h>
#include <array>
#include <vector>

#include <alloca.h>
#include <mach/mach.h>
#include <mach/mach_vm.h>
#include <mach/thread_state.h>
#include <mach/vm_map.h>
#include <mach-o/dyld_images.h>
#include <mach-o/loader.h>
#include <mach-o/nlist.h>
#include <sys/sysctl.h>

// Don't think we need these?
//#include <stdio.h>
//#include <stdbool.h>

// Macros for early return etc...
#define CheckedCall(functionCall) if ((ret = (functionCall))) return ret;
#define CheckedCallE(functionCall, errorMessage) \
if ((ret = (functionCall))) {                    \
    cerr << #errorMessage << endl;               \
    return ret;                                  \
}


#endif //CEROCLIENT_PRIVATE_H
