//
// Created by 0x41c on 2022-06-29.
//

#include <shared/injector.h>

string Injector::getDLLPath() {
    auto currentPath = (string)current_path(); // :(
    string ext;

#ifdef MacOS
    ext = ".dylib";
#elifdef Windows
    ext = ".dll";
#endif
    return currentPath + path_sep + RESOURCE_CLIENTLOADER_NAME + ext;
}