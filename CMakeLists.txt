cmake_minimum_required(VERSION 3.22)
project(CeroClient VERSION 0.1 DESCRIPTION "Cero client JAR loader")

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/build/bin)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/build/bin)

add_subdirectory(ClientLoader)
add_subdirectory(ClientInjector)

# Shared source files
file(GLOB SharedSources CONFIGURE_DEPENDS
        "shared/src/*.h" "shared/src/**/*.h"
        "shared/src/*.cpp" "shared/src/**/*.cpp"
        "shared/src/*.c" "shared/src/**/*.c")

target_sources(ClientLoader PRIVATE ${SharedSources})
target_sources(ClientInjector PRIVATE ${SharedSources})

target_include_directories(ClientLoader PRIVATE shared/include)
target_include_directories(ClientInjector PRIVATE shared/include)