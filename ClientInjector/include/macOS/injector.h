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

    bool inject(const argparse::ArgumentParser& parser, int pid) final;

    int getLunarPID(const argparse::ArgumentParser& parser) final;

};

#endif //CEROCLIENT_MINJECTOR_H
