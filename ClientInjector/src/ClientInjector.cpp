//
// Created by 0x41c on 2022-06-28.
//


#include <ClientInjector.h>
#include <argparse/argparse.h>

#ifdef MacOS
#include <macOS/injector.h>
#elifdef Windows
#include <windows/injector.h>
#endif


/**
 * The main function. Takes two different arguments on macOS and windows.
 * Will always attempt to inject the ClientLoader as there's no need to
 * inject anything else.
 * @param argc argument count
 * @param argv arguments
 * @return The error code or 0 on success.
 */
int main(int argc, const char *argv[]) {
    argparse::ArgumentParser injector("ClientInjector");

    injector.add_argument("pid")
    .required()
    .help("The PID of the MC instance to inject into.")
    .scan<'i', int>();

    try {
        injector.parse_args(argc, argv);
    } catch (const runtime_error& err) {
        cerr << err.what() << endl;
        cerr << injector << endl;
        return 1;
    }

#ifdef MacOS
    MacOSInjector injectorPlatform;
#elifdef Windows
    WindowsInjector injectorPlatform;
#endif

    return (int)injectorPlatform.inject(injector.get<int>("pid"));
}
