//
// Created by 0x41c on 2022-06-28.
//

#ifndef CEROCLIENT_INJECTOR_H
#define CEROCLIENT_INJECTOR_H

#include <commons.h>
#include <list>
#include <map>

class Injector {
public:
    virtual bool inject(int pid) = 0;
    virtual int getLunarPID() = 0;
    string getDLLPath();
};

#endif //CEROCLIENT_INJECTOR_H
