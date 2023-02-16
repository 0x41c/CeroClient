//
// Created by 0x41c on 2022-06-28.
//


#include <unistd.h>
#include <ClientInjector.h>
#include <argparse/argparse.h>

#ifdef MacOS
#include <macOS/injector.h>
MacOSInjector injectorPlatform;
#elifdef Windows
#include <windows/injector.h>
WindowsInjector injectorPlatform;
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

    injector.add_argument("--lunar")
    .help("Inject into the currently launched client (1.8.9 only)")
    .default_value(false)
    .implicit_value(true);

    injector.add_argument("--pid")
    .help("The PID of the MC instance to inject into.")
    .scan<'i', int>();

    if (getuid() != 0) {
        cerr << "Injector needs to be run as root, re-run with sudo." << endl;
        cerr << injector << endl;
        return 1;
    }

    try {
        injector.parse_args(argc, argv);
    } catch (const runtime_error& err) {
        cerr << err.what() << endl;
        cerr << injector << endl;
        return 1;
    }

    auto lunar = injector.get<bool>("--lunar");

    if (!lunar && injector["pid"] == nullptr) {
        cerr << injector << endl;
        return 1;
    }

    int pid;
    if (lunar) {
        pid = injectorPlatform.getLunarPID(injector);
        if (pid < 0) {
            cerr << "Lunar Client isn't launched." << endl;
            cerr << injector << endl;
            return 1;
        }
    } else pid = injector.get<int>("pid");

    return (int)injectorPlatform.inject(injector, pid);
}
