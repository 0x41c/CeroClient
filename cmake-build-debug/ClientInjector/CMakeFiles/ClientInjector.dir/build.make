# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.22

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/Corban/Desktop/development/cero-project

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/Corban/Desktop/development/cero-project/cmake-build-debug

# Include any dependencies generated for this target.
include ClientInjector/CMakeFiles/ClientInjector.dir/depend.make
# Include any dependencies generated by the compiler for this target.
include ClientInjector/CMakeFiles/ClientInjector.dir/compiler_depend.make

# Include the progress variables for this target.
include ClientInjector/CMakeFiles/ClientInjector.dir/progress.make

# Include the compile flags for this target's objects.
include ClientInjector/CMakeFiles/ClientInjector.dir/flags.make

ClientInjector/CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/flags.make
ClientInjector/CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.o: ../ClientInjector/src/ClientInjector.cpp
ClientInjector/CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/Corban/Desktop/development/cero-project/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object ClientInjector/CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.o"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT ClientInjector/CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.o -MF CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.o.d -o CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.o -c /Users/Corban/Desktop/development/cero-project/ClientInjector/src/ClientInjector.cpp

ClientInjector/CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.i"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/Corban/Desktop/development/cero-project/ClientInjector/src/ClientInjector.cpp > CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.i

ClientInjector/CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.s"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/Corban/Desktop/development/cero-project/ClientInjector/src/ClientInjector.cpp -o CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.s

ClientInjector/CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/flags.make
ClientInjector/CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.o: ../ClientInjector/src/macOS/injector.cpp
ClientInjector/CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/Corban/Desktop/development/cero-project/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object ClientInjector/CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.o"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT ClientInjector/CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.o -MF CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.o.d -o CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.o -c /Users/Corban/Desktop/development/cero-project/ClientInjector/src/macOS/injector.cpp

ClientInjector/CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.i"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/Corban/Desktop/development/cero-project/ClientInjector/src/macOS/injector.cpp > CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.i

ClientInjector/CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.s"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/Corban/Desktop/development/cero-project/ClientInjector/src/macOS/injector.cpp -o CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.s

ClientInjector/CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/flags.make
ClientInjector/CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.o: ../ClientInjector/src/shared/Injector.cpp
ClientInjector/CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/Corban/Desktop/development/cero-project/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building CXX object ClientInjector/CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.o"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT ClientInjector/CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.o -MF CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.o.d -o CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.o -c /Users/Corban/Desktop/development/cero-project/ClientInjector/src/shared/Injector.cpp

ClientInjector/CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.i"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/Corban/Desktop/development/cero-project/ClientInjector/src/shared/Injector.cpp > CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.i

ClientInjector/CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.s"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/Corban/Desktop/development/cero-project/ClientInjector/src/shared/Injector.cpp -o CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.s

ClientInjector/CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/flags.make
ClientInjector/CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.o: ../ClientInjector/src/windows/injector.cpp
ClientInjector/CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/Corban/Desktop/development/cero-project/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building CXX object ClientInjector/CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.o"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT ClientInjector/CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.o -MF CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.o.d -o CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.o -c /Users/Corban/Desktop/development/cero-project/ClientInjector/src/windows/injector.cpp

ClientInjector/CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.i"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/Corban/Desktop/development/cero-project/ClientInjector/src/windows/injector.cpp > CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.i

ClientInjector/CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.s"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/Corban/Desktop/development/cero-project/ClientInjector/src/windows/injector.cpp -o CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.s

ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/flags.make
ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.o: ../shared/src/commons.cpp
ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/Corban/Desktop/development/cero-project/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Building CXX object ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.o"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.o -MF CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.o.d -o CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.o -c /Users/Corban/Desktop/development/cero-project/shared/src/commons.cpp

ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.i"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/Corban/Desktop/development/cero-project/shared/src/commons.cpp > CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.i

ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.s"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/Corban/Desktop/development/cero-project/shared/src/commons.cpp -o CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.s

ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/flags.make
ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.o: ../shared/src/zip.cpp
ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.o: ClientInjector/CMakeFiles/ClientInjector.dir/compiler_depend.ts
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/Corban/Desktop/development/cero-project/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_6) "Building CXX object ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.o"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -MD -MT ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.o -MF CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.o.d -o CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.o -c /Users/Corban/Desktop/development/cero-project/shared/src/zip.cpp

ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.i"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/Corban/Desktop/development/cero-project/shared/src/zip.cpp > CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.i

ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.s"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/Corban/Desktop/development/cero-project/shared/src/zip.cpp -o CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.s

# Object files for target ClientInjector
ClientInjector_OBJECTS = \
"CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.o" \
"CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.o" \
"CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.o" \
"CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.o" \
"CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.o" \
"CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.o"

# External object files for target ClientInjector
ClientInjector_EXTERNAL_OBJECTS =

../build/bin/ClientInjector: ClientInjector/CMakeFiles/ClientInjector.dir/src/ClientInjector.cpp.o
../build/bin/ClientInjector: ClientInjector/CMakeFiles/ClientInjector.dir/src/macOS/injector.cpp.o
../build/bin/ClientInjector: ClientInjector/CMakeFiles/ClientInjector.dir/src/shared/Injector.cpp.o
../build/bin/ClientInjector: ClientInjector/CMakeFiles/ClientInjector.dir/src/windows/injector.cpp.o
../build/bin/ClientInjector: ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/commons.cpp.o
../build/bin/ClientInjector: ClientInjector/CMakeFiles/ClientInjector.dir/__/shared/src/zip.cpp.o
../build/bin/ClientInjector: ClientInjector/CMakeFiles/ClientInjector.dir/build.make
../build/bin/ClientInjector: ../ClientInjector/resources/libRemoteExec.a
../build/bin/ClientInjector: ClientInjector/CMakeFiles/ClientInjector.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/Corban/Desktop/development/cero-project/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_7) "Linking CXX executable ../../build/bin/ClientInjector"
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/ClientInjector.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
ClientInjector/CMakeFiles/ClientInjector.dir/build: ../build/bin/ClientInjector
.PHONY : ClientInjector/CMakeFiles/ClientInjector.dir/build

ClientInjector/CMakeFiles/ClientInjector.dir/clean:
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector && $(CMAKE_COMMAND) -P CMakeFiles/ClientInjector.dir/cmake_clean.cmake
.PHONY : ClientInjector/CMakeFiles/ClientInjector.dir/clean

ClientInjector/CMakeFiles/ClientInjector.dir/depend:
	cd /Users/Corban/Desktop/development/cero-project/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/Corban/Desktop/development/cero-project /Users/Corban/Desktop/development/cero-project/ClientInjector /Users/Corban/Desktop/development/cero-project/cmake-build-debug /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector /Users/Corban/Desktop/development/cero-project/cmake-build-debug/ClientInjector/CMakeFiles/ClientInjector.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : ClientInjector/CMakeFiles/ClientInjector.dir/depend

