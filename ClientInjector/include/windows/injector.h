//
// Created by 0x41c on 2022-06-28.
//

#ifndef CEROCLIENT_WINJECTOR_H
#define CEROCLIENT_WINJECTOR_H

#include <shared/injector.h>

class WindowsInjector: public Injector {
public:

    WindowsInjector() = default;
    virtual ~WindowsInjector() = default;

    bool inject(const argparse::ArgumentParser& parser, int pid) final;

    int getLunarPID(const argparse::ArgumentParser& parser) final;

};

#endif //CEROCLIENT_WINJECTOR_H
