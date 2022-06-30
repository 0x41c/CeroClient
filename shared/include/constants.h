//
// Created by 0x41c on 2022-06-28.
//

#ifndef CONSTANTS_H
#define CONSTANTS_H

#define HOMEDIR_NAME ".ceroclient"
#define HOMEDIR_DATA_NAME "settings"
#define HOMEDIR_LOG_NAME "logs"
#define HOMEDIR_RESOURCES_NAME "resources"
#define RESOURCE_BIN_NAME "bin"
#define RESOURCE_CLIENTINJECTOR_NAME "ClientInjector"
#define RESOURCE_CLIENTLOADER_NAME "libClientLoader"
#define RESOURCE_CLIENTJAR_NAME "Client"

// MacOS compiler flag
#if defined(__APPLE__)
#ifndef MacOS
#define MacOS
#endif

// TODO: Implement linux support
//#elif defined(__linux__)
//#ifndef Linux
//#define Linux
//#endif


// Windows compiler flag
#elif defined(_WIN32) || defined
#ifndef Windows
#define Windows
#endif
#endif

#ifdef MacOS
#define DYNAMIC_EXT ".dylib"
#else
#define DYNAMIC_EXT ".dll"
#endif

#endif //CONSTANTS_H
