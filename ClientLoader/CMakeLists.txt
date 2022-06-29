cmake_minimum_required(VERSION 3.22)
project(ClientLoader VERSION 0.1 DESCRIPTION "Cero client JAR loader")
set(CMAKE_CXX_STANDARD 20)

# Source files
file(GLOB ClientLoader_Source CONFIGURE_DEPENDS
        "src/*.h" "src/**/*.h"
        "src/*.cpp" "src/**/*.cpp"
        "src/*.c" "src/**/*.c")

add_library(ClientLoader SHARED ${ClientLoader_Source})

include(FindJNI)
include(FindJava)

IF(!JNI_FOUND)
    message(FATAL_ERROR "Unable to find JNI on this machine.")
ELSEIF(!JAVA_FOUND)
    message(FATAL_ERROR "Unable to find Java on this machine.")
ELSE()
    message(STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message(STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
ENDIF()


target_include_directories(ClientLoader PRIVATE include)
target_include_directories(ClientLoader PRIVATE ${JNI_INCLUDE_DIRS})