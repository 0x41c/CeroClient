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

    Injector() = default;
    virtual ~Injector() = default;

    virtual bool inject(const argparse::ArgumentParser& parser, int pid) = 0;

    virtual int getLunarPID(const argparse::ArgumentParser& parser) = 0;

    static string getDLLPath(const argparse::ArgumentParser& parser);

};

#endif //CEROCLIENT_INJECTOR_H
