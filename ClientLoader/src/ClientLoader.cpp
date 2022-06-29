//
// Created by 0x41c on 2022-06-29.
//

#include <ClientLoader.h>

ClientLoader::ClientLoader() {}
void ClientLoader::begin() {
    exit(1);
    Logger::get("ClientLoader").info("Begin called!");
}