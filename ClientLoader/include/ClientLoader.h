//
// Created by 0x41c on 2022-06-29.
//

#ifndef CEROCLIENT_CLIENTLOADER_H
#define CEROCLIENT_CLIENTLOADER_H

#include <commons.h>
#include <logger.h>
#include <jni.h>

class ClientLoader {
public:

    ClientLoader() = default;
    void begin();
    void attach();

    JNIEnv *env;
};


#endif //CEROCLIENT_CLIENTLOADER_H
