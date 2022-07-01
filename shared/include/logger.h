//
// Created by 0x41c on 2022-06-28.
//

#ifndef LOGGER_H
#define LOGGER_H

#include "commons.h"

using namespace filesystem;

class Logger {

public:

    static Logger& get(const string& name) {
        static Logger logger(name);
        return logger;
    }

    static Logger& get() {
        return get("");
    }

    void raw(const string& message) {
        m_file.open(m_filePath, ios_base::out | ios_base::app);
        m_file << message + "\n";
        m_file.close();
    }

    void statusMessage(const string& status, const string& message) {
        raw("[" + m_name + "/" + status + "] " + message);
    }

    void info(const string& message) {
        statusMessage("INFO", message);
    }

    void warning(const string& message) {
        statusMessage("WARNING", message);
    }

    // TODO: Stack dumping both C++ and java.
    void error(const string& message) {
        statusMessage("ERROR", message);
        exit(1);
    }


private:

    explicit Logger(const string& loggerName) {
        m_name = "CeroClient/" + loggerName;

        string sep = path_sep;
        string path = getHomePath();

        string logsPath = path + sep + HOMEDIR_LOG_NAME;
        Ensure(logsPath);

        string currentLogsFile = logsPath + sep + "current.log";
        EnsureDeleted(currentLogsFile);

        m_filePath = currentLogsFile;
    };

    ofstream m_file;
    string m_filePath;
    string m_name;
};

#ifndef CheckRet
#define CheckRet(functionName) if (ret != JNI_OK) \
    Logger::get().error((string)"(" + (functionName) + ") Ret failed with code: " + to_string(ret))
#endif

#endif //LOGGER_H
