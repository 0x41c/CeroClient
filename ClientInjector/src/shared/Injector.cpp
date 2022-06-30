//
// Created by 0x41c on 2022-06-29.
//

#include <shared/injector.h>

string Injector::getDLLPath(argparse::ArgumentParser parser) {

    // The injector binary needs a couple resources to be in certain places.
    // This is what the local structure needs to look like:
    //
    //                current_path
    //               ---------------
    //             | ClientInjector |       resources/
    //             |    resources   |------------------------
    //              --------------- | libClientLoader.ext   |
    //                              | Client.jar            |
    //                               ------------------------
    //
    //
    // Resources is copied to ~/.ceroclient/resources so that libClientLoader.dylib
    // knows where to look for Client.jar.

    auto currentPath = (string)current_path();

    // ensure ./Resources
    auto localResourcePath = currentPath + path_sep + HOMEDIR_RESOURCES_NAME;
    if (!exists(localResourcePath)) {
        cerr << "Resource path missing from current path." << endl << parser << endl;
        exit(1);
    }

    // Ensure ./Resources/libClientLoader.ext
    auto libClientLoaderPath = localResourcePath + path_sep + RESOURCE_CLIENTLOADER_NAME + DYNAMIC_EXT;
    if (!exists(libClientLoaderPath)) {
        cerr << "Missing libClientLoader." << endl << parser << endl;
        exit(1);
    }

    // Ensure ./Resources/Client.jar
    auto clientPath = localResourcePath + path_sep + RESOURCE_CLIENTJAR_NAME + ".jar";
    if (!exists(clientPath)) {
        cerr << "Missing client jar." << endl << parser << endl;
        exit(1);
    }

    auto homePath = getHomePath();
    Ensure(homePath);

    // NOTE: Only do this for debug builds
    auto homeResourcePath = homePath + path_sep + HOMEDIR_RESOURCES_NAME;
    copy(localResourcePath, homeResourcePath);
    return homeResourcePath + path_sep + RESOURCE_CLIENTLOADER_NAME + DYNAMIC_EXT;

}