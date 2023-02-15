cmake_minimum_required(VERSION 3.22)
project(ClientInjector VERSION 0.1 DESCRIPTION "Cero client process injector")
set(CMAKE_CXX_STANDARD 20)

# Source files
# Restrict for shellcode sections.
file(GLOB ClientInjector_Source CONFIGURE_DEPENDS
        "src/*.cpp" "src/macOS/*.cpp" "src/windows/*.cpp" "src/shared/*.cpp"
        "src/*.c" "src/macOS/*.c" "src/windows/*.c" "src/shared/*.c")

add_executable(ClientInjector ${ClientInjector_Source})

IF(APPLE)
    # sections
    execute_process(COMMAND make WORKING_DIRECTORY ${CMAKE_CURRENT_LIST_DIR}/mip-mini)
    target_link_libraries(ClientInjector PRIVATE ${CMAKE_CURRENT_LIST_DIR}/resources/inject.a)
ENDIF()

target_include_directories(ClientInjector PRIVATE include)