//
// Created by 0x41c on 2022-06-29.
//

#include <ClientLoader.h>
#include <Zippy.hpp>
#include <jnif.hpp>
#include <map>


#ifdef MacOS
typedef jint (*getcreatedvms_t)(JavaVM **, jsize, jsize *);
#else
typedef jint (__stdcall *getcreatedvms_t)(JavaVM **, jsize, jsize *);
#endif



void ClientLoader::begin() {
    Logger::get("ClientLoader");
    attach();
    Logger::get().info("Finding JAR path");

    auto resourceDir = getHomePath() + path_sep + HOMEDIR_RESOURCES_NAME;
    if (!exists(resourceDir))
        Logger::get().error("Resource dir missing");

    auto jarPath = resourceDir + path_sep + RESOURCE_CLIENTJAR_NAME + ".jar";
    if (!exists(jarPath))
        Logger::get().error("Missing client jar. (" + jarPath + ")");

    Logger::get().info("Found jar");

    jobject classLoader = getClassLoader();

    Zippy::ZipArchive clientArchive(jarPath);
    map<string, vector<unsigned char>> classDataMap;
    map<string, string> superClassMap;

    // Pre-process the classes into a map we can use
    for (auto const &entry : clientArchive.GetEntryNames(false)) {
        auto zipEntryData = clientArchive.GetEntry(entry).GetData();
        auto parser = jnif::parser::ClassFileParser(zipEntryData.data(), zipEntryData.size());
        string name = entry.substr(0, entry.length() - 6);
        string superClassName = parser.getSuperClassName();

        Logger::get().info("Mapping " + name + "with superclass: " + superClassName);

        classDataMap.insert({ name, zipEntryData });
        superClassMap.insert({ name, superClassName });
    }

    set<string> loadedClasses = {
            "java/lang/Object" // Default
    };

    while (!classDataMap.empty()) {
        for (auto const &classData : classDataMap) {
            string name = classData.first;
            string superclass_name = superClassMap.at(name);
            if (loadedClasses.find(superclass_name) != loadedClasses.end()) {
                defineClass(classLoader, name, classData.second);
                loadedClasses.insert(name);
            }
        }

        for (auto const &loaded: loadedClasses)
            if (classDataMap.contains(loaded))
                classDataMap.erase(loaded);

    }

    Logger::get().info("Got our class!: " + ptoh(entryPoint));

    jmethodID entry = env->GetStaticMethodID(entryPoint, "entry", "()V");

    if (entry) Logger::get().info("Retrieved entryPoint: " + ptoh(entry));
    else Logger::get().error("Couldn't get entryPoint");

    Logger::get().info("Calling java method!");
    env->CallStaticVoidMethod(entryPoint, entry);
    Logger::get().info("Called entryPoint. Exiting");

    env->DeleteLocalRef(entryPoint);
}



void ClientLoader::attach() {
    Logger::get().info("Attaching...");

    jint ret;
    auto JNI_GetCreatedJavaVMs_ = (getcreatedvms_t)getJavaSymbol("JNI_GetCreatedJavaVMs");

    ret = JNI_GetCreatedJavaVMs_(&vm, 1, nullptr);
    CheckRet("JNI_GetCreatedJavaVMs");

    ret = vm->AttachCurrentThread((void**)&env, nullptr);
    CheckRet("AttachCurrentThread");

    Logger::get().info("Native Interface: " + ptoh(env));
    Logger::get().info("Java VM: " + ptoh(vm));
    Logger::get().info("Attached.");
}

jobject ClientLoader::getClassLoader() const {
    Logger::get().info("Getting class loader");

    jclass thread_cls = env->FindClass("java/lang/Thread");
    jclass threadGroup_cls = env->FindClass("java/lang/ThreadGroup");

    // java.lang.Thread
    jmethodID thread_ct_mid = env->GetStaticMethodID(
            thread_cls, "currentThread", "()Ljava/lang/Thread;");
    jmethodID thread_tg_mid = env->GetMethodID(
            thread_cls, "getThreadGroup", "()Ljava/lang/ThreadGroup;");
    jmethodID thread_ccl_mid = env->GetMethodID(
            thread_cls, "getContextClassLoader", "()Ljava/lang/ClassLoader;");

    // java.lang.ThreadGroup
    jfieldID threadG_nt_id = env->GetFieldID(
            threadGroup_cls, "nthreads", "I");
    jmethodID threadG_en_id = env->GetMethodID(
            threadGroup_cls, "enumerate", "([Ljava/lang/Thread;)I");

    jobject thread = env->CallStaticObjectMethod(thread_cls, thread_ct_mid);
    jobject threadg = env->CallObjectMethod(thread, thread_tg_mid);
    jint num_threads = env->GetIntField(threadg, threadG_nt_id);

    jobjectArray thread_array = env->NewObjectArray(num_threads, thread_cls, nullptr);
    (void) env->CallIntMethod(threadg, threadG_en_id, thread_array);

    jobject parent_thread = env->GetObjectArrayElement(thread_array, 0);
    jobject classLoader = env->CallObjectMethod(parent_thread, thread_ccl_mid);

    env->DeleteLocalRef(thread_cls); env->DeleteLocalRef(threadGroup_cls); env->DeleteLocalRef(thread);
    env->DeleteLocalRef(threadg); env->DeleteLocalRef(thread_array); env->DeleteLocalRef(parent_thread);

    classLoader = env->NewGlobalRef(classLoader);

    Logger::get().info("Retrieved class loader: " + ptoh(classLoader));

    return classLoader;
}

void ClientLoader::defineClass(jobject classLoader, string name, vector<unsigned char> data) {
    jclass defClass = env->DefineClass(name.c_str(), classLoader, reinterpret_cast<const jbyte *>(data.data()), data.size());
    if (defClass == nullptr)
        Logger::get().error("Couldn't define class " + name);

    if (name == "com/cero/Client")
        entryPoint = defClass;
}