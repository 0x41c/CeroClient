//
// Created by 0x41c on 2022-06-28.
//

#ifndef CEROCLIENT_WINJECTOR_H
#define CEROCLIENT_WINJECTOR_H

#include <shared/injector.h>

class WindowsInjector: public Injector {
public:
    WindowsInjector() = default;
    bool inject(int pid) final;
    int getLunarPID() final;
};

#endif //CEROCLIENT_WINJECTOR_H
