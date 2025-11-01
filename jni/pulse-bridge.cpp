
#include <jni.h>
#include <projectM-4/projectM.h>
#include <projectM-4/render_opengl.h>

extern "C" {

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_init(JNIEnv *env, jobject thiz, jint width, jint height) {
    projectm_opengl_create(width, height, NULL, NULL, NULL);
}

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_onDrawFrame(JNIEnv *env, jobject thiz) {
    projectm_opengl_render_frame();
}

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_addPCM(JNIEnv *env, jobject thiz, jfloatArray pcm) {
    jfloat *p = env->GetFloatArrayElements(pcm, NULL);
    if (p == NULL) {
        return;
    }
    jsize len = env->GetArrayLength(pcm);
    projectm_pcm_add_float(projectm_get_core(0), p, len / 2, 2);
    env->ReleaseFloatArrayElements(pcm, p, 0);
}

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_loadPreset(JNIEnv *env, jobject thiz, jstring path) {
    const char *pathStr = env->GetStringUTFChars(path, NULL);
    if (pathStr == NULL) {
        return;
    }
    if (projectm_playlist_add_url(projectm_playlist_get_core(0), pathStr, 1) == 0) {
        env->ReleaseStringUTFChars(path, pathStr);
        jclass exClass = env->FindClass("java/io/IOException");
        if (exClass != NULL) {
            env->ThrowNew(exClass, "Failed to load preset");
        }
        return;
    }
    env->ReleaseStringUTFChars(path, pathStr);
}

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_clearPreset(JNIEnv *env, jobject thiz) {
    projectm_playlist_clear(projectm_playlist_get_core(0));
}

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_nextPreset(JNIEnv *env, jobject thiz) {
    projectm_playlist_next(projectm_playlist_get_core(0), 1);
}

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_previousPreset(JNIEnv *env, jobject thiz) {
    projectm_playlist_previous(projectm_playlist_get_core(0), 1);
}

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_setPresetDuration(JNIEnv *env, jobject thiz, jint duration) {
    projectm_set_preset_duration(projectm_get_core(0), duration);
}

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_setBeatSensitivity(JNIEnv *env, jobject thiz,
                                                                 jfloat sensitivity) {
    projectm_set_beat_sensitivity(projectm_get_core(0), sensitivity);
}

JNIEXPORT void JNICALL
Java_com_example_pulsebridge_PulseBridge_setBeatDetection(JNIEnv *env, jobject thiz,
                                                               jboolean enabled) {
    // This function doesn't exist in the projectM API.
    // We would need to implement this ourselves by either not calling projectm_pcm_add_float()
    // or by calling it with an empty array.
}

}
