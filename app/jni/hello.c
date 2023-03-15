#include <jni.h>

/*
 * 对应的native方法, 需要一个string的返回值
 * jstring 对应Java中的String
 * Java_com_ashin_vplayer_MainActivity_sayHello 方法名
 * Java_包名(.改成_)_类名_方法名
 *
 * JNIEnv* Java当前虚拟机运行环境
 * jobject 当前调用这个函数的Java对象
 */

/* 这里我们说一下NDK开发Android应用的步骤
 定义native方法
 创建jni(名字一定是jni)文件夹
 创建C代码文件
 创建C函数
 返回值和传入参数类型需要用到Java对应的C类型(主要参考jni.h文件的具体内容)
 函数名Java_包名(.改成)类名_native方法名
 默认传入两个参数 JNIEnv* env(jvm运行环境), jobject obj(调用这个函数的Java对象)
 编译
 ndk-build命令来编译
 Android.mk文件来指定编译谁
 加载动态库
 需要在static代码块中加载
 System.loadLibrary
 加载时候的名字: 库文件去掉.so, 去掉前面的lib
 可以开始使用native方法了, 这个时候native方法已经和so文件对应了
 NDK开发中常见错误
 加载动态库出错
 java.lang.UnsatisfiedLinkError: Couldn’t load hello123 from loader dalvik.system.PathClassLoader
 [dexPath=/data/app/apk名称,libraryPath=/data/app-lib/apk名称]: findLibrary returned null
 解决之道:
 1、写对库文件名字
 2、加载时候的名字: 库文件去掉.so, 去掉前面的lib
 3、按照文章的第五步修改gradle文件

 C函数名写错
 java.lang.UnsatisfiedLinkError: Native method not found:
 com.shi.androidstudy.myjni.MainActivity.sayHello:()Ljava/lang/String;
 解决之道:
 函数名Java_包名(.改成)类名_native方法名

 在不支持的平台上运行(无法安装的问题)
 Installation error: INSTALL_FAILED_CPU_ABI_INCOMPATIBLE
 解决之道:

 新建Application.mk文件(jni目录)
 APP_ABI := all
 */

jstring Java_com_ashin_vplayer_MainActivity_sayHello(JNIEnv* env, jobject obj) {
    char* pc = "Hello from c";
    // jstring返回值  (*NewStringUTF)(JNIEnv*  java虚拟机运行环境, const char* C语言中的字符串);
    jstring str = (**env).NewStringUTF(env, pc);
    return str;
}

