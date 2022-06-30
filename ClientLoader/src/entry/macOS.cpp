//
// Created by 0x41c on 2022-06-29.
//

#include <commons.h>

#ifdef MacOS

#include <entry/macos.h>

void *pthread_callback(void *) {
    ClientLoader loader;
    loader.begin();
    return nullptr;
}

__attribute__((constructor))
void entry() {
    pthread_t thread_id;
    pthread_create(&thread_id, nullptr, pthread_callback, nullptr);
    pthread_detach(thread_id); pthread_kill(pthread_self(), 0);
}

#endif