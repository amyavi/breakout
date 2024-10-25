#include <jni.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#ifdef __cplusplus
extern "C" {
#endif

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    return JNI_VERSION_1_2;
}

JNIEXPORT jint JNICALL Java_com_github_amyavi_breakout_BreakoutNative_execvp
    (JNIEnv * env, jclass thisClass, jstring jFile, jobjectArray jArgv) {
    const char* file;
    char** argv;
    jstring jTemp;
    const char* temp;
    int argc;
    int ret;

    file = env->GetStringUTFChars(jFile, NULL);
    argc = env->GetArrayLength(jArgv);
    argv = (char**) malloc(argc + 1); // +1 for NULL
    if (argv == NULL) return 1;

    for (int i = 0; i < argc; i++) {
        jTemp = (jstring)(env->GetObjectArrayElement(jArgv, i));
        temp = env->GetStringUTFChars(jTemp, NULL);
        argv[i] = strdup(temp);
        env->ReleaseStringUTFChars(jTemp, temp);
        env->DeleteLocalRef(jTemp);
    }
    argv[argc] = NULL;

    ret = execvp(file, argv);

    for (int i = 0; i < argc; i++) free(argv[i]);
    free(argv);
    env->ReleaseStringUTFChars(jFile, file);

    return ret;
}

#ifdef __cplusplus
}
#endif
