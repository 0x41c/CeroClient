//
// Created by 0x41c on 2022-06-28.
//

#ifndef CEROCLIENT_MINJECTOR_H
#define CEROCLIENT_MINJECTOR_H

#include "../shared/injector.h"
#include "private.h"

class MacOSInjector: public Injector {
public:

    MacOSInjector() = default;
    bool inject(argparse::ArgumentParser parser, int pid) final;
    int getLunarPID(argparse::ArgumentParser parser) final;

//private:
//    kern_return_t injectToTask(mach_port_t task, string dllpath);
//    kern_return_t getThreadPortForTask(mach_port_t task, mach_port_t *thread);
};

#endif //CEROCLIENT_MINJECTOR_H
