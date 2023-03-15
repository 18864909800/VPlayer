
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := hello
LOCAL_SRC_FILES := hello.c AddAndSub.c Encrypt.c

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_LDLIBS += -llog
LOCAL_MODULE    := alipay
LOCAL_SRC_FILES := alipay.c

include $(BUILD_SHARED_LIBRARY)
