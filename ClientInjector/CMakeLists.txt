cmake_minimum_required(VERSION 3.22)
project(ClientInjector VERSION 0.1 DESCRIPTION "Cero client process injector")
set(CMAKE_CXX_STANDARD 20)

# Source files
file(GLOB ClientInjector_Source CONFIGURE_DEPENDS
        "src/*.h" "src/**/*.h"
        "src/*.cpp" "src/**/*.cpp"
        "src/*.c" "src/**/*.c")


message(STATUS "ClientInjector Source: ${ClientInjector_Source}")
add_executable(ClientInjector ${ClientInjector_Source})

target_include_directories(ClientInjector PRIVATE include)