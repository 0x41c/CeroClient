//
// Created by 0x41c on 2022-06-29.
//

#include <commons.h>

string getHomePath() {
    string sep = path_sep;
    string homeName;

    if (sep == "\\")
        homeName = "USERPROFILE";
    else
        homeName = "HOME";

    string path = getenv(homeName.c_str());
    path += sep + HOMEDIR_NAME;
    Ensure(path);
    return path;
}

#ifdef MacOS
#include <dlfcn.h>
void *getJavaSymbol(const string& name) {
    void *handle = dlopen(nullptr, RTLD_NOW);
    if (!handle) return nullptr;
    void *symbol = dlsym(handle, name.c_str());
    return symbol;
}
#else
void *getJavaSymbol(string name) {
    HMODULE jvm = GetModuleHandleA("jvm.dll");
    if (!jvm) return nullptr;

    void *symbol = GetProcAddress(jvm, name);
    return symbol;
}
#endif