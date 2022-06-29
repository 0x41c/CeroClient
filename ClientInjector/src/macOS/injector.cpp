//
// Created by 0x41c on 2022-06-28.
//

#include <commons.h>

#ifdef MacOS
#include <macOS/injector.h>
#include <mach/mach.h>
#include <dlfcn.h>
#include <array>
#include <vector>

bool MacOSInjector::inject(int pid) {
    void *bsFunction;

    string dllPath = getDLLPath();
    auto handle = dlopen(dllPath.c_str(), RTLD_NOW | RTLD_LOCAL);

    if (!handle) {
        cerr << "Missing ClientLoader, please re-download the client" << endl;
        return false;
    }

    cout << "DLL Handle: " + ptoh(handle) << endl;

    bsFunction = dlsym(handle, "bootstrap");

    if (!bsFunction) {
        cerr << "Invalid ClientLoader, please re-download the client" << endl;
        return false;
    }

    cout << "Bootstrap function: " + ptoh(bsFunction) << endl;

    mach_error_t err = mach_inject((mach_inject_entry)bsFunction, nullptr, 0, pid, 0);
    if (err != KERN_SUCCESS) {
        cerr << "Error while injecting: \"" + to_string(err) + "\"" << endl;
        return err;
    }

    cout << "Injection successful." << endl;

    return true;
}


string exec(const string& cmd);
vector<string> split(string phrase, const string& delimiter);

int MacOSInjector::getLunarPID() {
    string output = exec("ps aux | grep LunarMain");
    vector<string> newList;
    double versionIDX = 0.0;

    auto nl_list = split((char *)output.c_str(), "\n");

    int count = 0;
    for (auto thing = nl_list.begin(); thing < nl_list.end(); thing++)
        count++;
    if (count <= 3) {
        cerr << "Lunar Client isn't launched." << endl;
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
        cerr << "Lunar launched on unsupported version. " + to_string(versionIDX) << endl;
        exit(1);
    }

    return stoi(newList[1]);
}

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