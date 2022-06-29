//
// Created by 0x41c on 2022-06-28.
//

#ifndef COMMONS_H
#define COMMONS_H

#include <filesystem>
#include <iostream>
#include <fstream>
#include <string>
#include <cstdint>
#include <sstream>
#include "constants.h"

// Namespaces
using namespace std;
using namespace filesystem;

// Common functions

inline string ptoh(uintptr_t ptr) {
    ostringstream stream; {
        stream << "0x" << setfill('0') << setw(16) << std::hex << (uint64_t)ptr;
    }
    return stream.str();
}

inline string ptoh(void *ptr) {
    return ptoh((uintptr_t)ptr);
}

// Common regular functions
string getHomePath();

// Common macros
#define Ensure(dirVar) \
        if (!exists(dirVar)) create_directory(dirVar)
#define EnsureD(dirVar) \
        if (exists(dirVar)) remove(dirVar)
#define path_sep ((string)"" + path::preferred_separator)

#endif //COMMONS_H
