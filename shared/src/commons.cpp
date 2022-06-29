//
// Created by 0x41c on 2022-06-29.
//

#include <commons.h>

string getHomePath() {
    string sep = path_sep;
    string homeName;

    if (sep == "\\")
        homeName = "USERPROFILE";
    else
        homeName = "HOME";

    string path = getenv(homeName.c_str());
    path += sep + HOMEDIR_NAME;
    Ensure(path);
    return path;
}
