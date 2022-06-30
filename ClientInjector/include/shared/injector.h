//
// Created by 0x41c on 2022-06-28.
//

#ifndef CEROCLIENT_INJECTOR_H
#define CEROCLIENT_INJECTOR_H

#include <commons.h>
#include <list>
#include <map>
#include <argparse/argparse.h>

class Injector {
public:
    virtual bool inject(argparse::ArgumentParser parser, int pid) = 0;
    virtual int getLunarPID(argparse::ArgumentParser parser) = 0;
    string getDLLPath();
};

#endif //CEROCLIENT_INJECTOR_H
