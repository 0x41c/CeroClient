//
// Created by 0x41c on 2022-06-28.
//

#include <commons.h>

#ifdef MacOS
#include <macOS/injector.h>
#include <mach/mach.h>
#include <dlfcn.h>

bool MacOSInjector::inject(int pid) {
    void *bsFunction;

    string dllPath = getDLLPath();
    auto handle = dlopen(dllPath.c_str(), RTLD_NOW | RTLD_LOCAL);

    if (!handle) {
        cerr << "Missing ClientLoader, please re-download the client" << endl;
        return false;
    }

    cout << "DLL Handle: " + ptoh(handle) << endl;

    bsFunction = dlsym(handle, "macOS_bootstrap");

    if (!bsFunction) {
        cerr << "Invalid ClientLoader, please re-download the client" << endl;
        return false;
    }

    return true;
}

#endif