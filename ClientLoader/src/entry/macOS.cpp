//
// Created by 0x41c on 2022-06-29.
//

#include <commons.h>

#ifdef MacOS

#include <entry/macos.h>

void *callEntry(void *_unused) {
    ClientLoader().begin();
    return nullptr;
}

void bootstrap(ptrdiff_t offset, void *param, size_t psize, void *dummy) {
    _pthread_set_self(dummy);

    pthread_attr_t attr;
    pthread_attr_init(&attr);

    int policy;
    pthread_attr_getschedpolicy(&attr, &policy);
    pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);
    pthread_attr_setinheritsched(&attr, PTHREAD_EXPLICIT_SCHED);

    struct sched_param sched {};
    sched.sched_priority = sched_get_priority_max(policy);
    pthread_attr_setschedparam(&attr, &sched);

    pthread_t thread;

    pthread_create(&thread, &attr, callEntry, nullptr);

    pthread_attr_destroy(&attr);
    thread_suspend(mach_thread_self());
}

#endif