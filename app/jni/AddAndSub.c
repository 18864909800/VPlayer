#include <jni.h>

/*
 * Class:     com_shi_androidstudy_ndk02_MainActivity
 * Method:    add
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_ashin_vplayer_ndk_NdkCalActivity_add
  (JNIEnv * env, jobject obj, jint i, jint j){
    return i+j;
  }

/*
 * Class:     com_shi_androidstudy_ndk02_MainActivity
 * Method:    sub
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_ashin_vplayer_ndk_NdkCalActivity_sub
  (JNIEnv * env, jobject obj, jint i, jint j){
    return i-j;
  }



