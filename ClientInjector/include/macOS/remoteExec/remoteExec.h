//
// Created by 0x41c on 2022-06-30.
//

#ifndef CEROCLIENT_REMOTEEXEC_H
#define CEROCLIENT_REMOTEEXEC_H

#include <constants.h>
#ifdef MacOS

#include <cstdio>
#include <mach/mach.h>
#include <mach/task.h>
#include <libproc.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Main */
mach_error_t RemoteExecSystem(task_t remoteTask, const char* command);
mach_error_t RemoteExecDlopen(task_t remoteTask, const char* path);

/* Tools */
[[maybe_unused]]
pid_t find_pid_by_name(const char *name);

#ifdef __cplusplus
}
#endif

#endif // MacOS
#endif //CEROCLIENT_REMOTEEXEC_H
