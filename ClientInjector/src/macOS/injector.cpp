//
// Created by 0x41c on 2022-06-28.
//

#include <commons.h>
#ifdef MacOS

#include <macOS/injector.h>
#include <macOS/remoteExec/remoteExec.h>

string exec(const string& cmd);
vector<string> split(string phrase, const string& delimiter);

bool MacOSInjector::inject(argparse::ArgumentParser parser, int pid) {

    string dllPath = getDLLPath();
    mach_port_t task;
    kern_return_t ret;
    if ((ret = task_for_pid(mach_task_self_, pid, &task))) {
        cerr
        << "Couldn't get task from provided PID, either the process died, or the PID was incorrect." << endl
        << "Please double check the PID was entered correctly, or lunar wasn't killed." << endl
        << parser << endl;
        return false;
    }

#if false
     if ((ret = injectToTask(task, dllPath))) return false;
#endif

    ret = RemoteExecDlopen(task, dllPath.c_str());

    cout << "Injection successful." << endl;
    return !ret;
}

int MacOSInjector::getLunarPID(argparse::ArgumentParser parser) {
    string output = exec("ps aux | grep LunarMain");
    vector<string> newList;
    double versionIDX = 0.0;

    auto nl_list = split((char *)output.c_str(), "\n");

    int count = 0;
    for (auto thing = nl_list.begin(); thing < nl_list.end(); thing++)
        count++;
    if (count <= 3) {
        cerr
        << "Lunar Client isn't launched." << endl
        << parser << endl;
        exit(1);
    }

    auto list = split((char *) nl_list.at(0).c_str(), " ");

    string lastItem;
    for (const auto& item : list) {
        if (item.empty()) {
            continue;
        }

        if (lastItem == "--version")
            versionIDX = stod(item);

        newList.push_back(item);
        lastItem = item;
    }

    if (versionIDX != 1.8) {
        cerr
        << "Lunar launched on unsupported version. " + to_string(versionIDX) << endl
        << parser << endl;
        exit(1);
    }

    return stoi(newList[1]);
}

//kern_return_t MacOSInjector::injectToTask(mach_port_t task, string dllpath) {
//
//
//
//    return KERN_SUCCESS;
//}

// Utility functions
string exec(const string& cmd) {
    array<char, 128> buffer {};
    string result;
    unique_ptr<FILE, decltype(&pclose)> pipe(popen(cmd.c_str(), "r"), pclose);
    if (!pipe) {
        throw std::runtime_error("popen() failed!");
    }
    while (fgets(buffer.data(), buffer.size(), pipe.get()) != nullptr) {
        result += buffer.data();
    }
    return result;
}

vector<string> split(string phrase, const string& delimiter) {
    vector<string> list;
    size_t pos = 0;
    string token;
    while ((pos = phrase.find(delimiter)) != string::npos) {
        token = phrase.substr(0, pos);
        list.push_back(token);
        phrase.erase(0, pos + delimiter.length());
    }
    list.push_back(phrase);
    return list;
}

#endif