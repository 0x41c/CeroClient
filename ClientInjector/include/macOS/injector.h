//
// Created by 0x41c on 2022-06-28.
//

#ifndef CEROCLIENT_MINJECTOR_H
#define CEROCLIENT_MINJECTOR_H

#include "../shared/injector.h"

class MacOSInjector: public Injector {
public:
    MacOSInjector() = default;
    bool inject(int pid) final;
};

#endif //CEROCLIENT_MINJECTOR_H
