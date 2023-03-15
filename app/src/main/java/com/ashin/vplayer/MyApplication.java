package com.ashin.vplayer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.leakcanary.internal.ActivityLifecycleCallbacksAdapter;

public class MyApplication extends Application {
    private static final String TAG = "VP-MyApplication";
    private static Context context;

    private static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取Context
        context = getApplicationContext();

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
       // refWatcher = LeakCanary.install(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                Log.d(TAG,"onActivityCreated activity: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                super.onActivityStarted(activity);
                Log.d(TAG,"onActivityStarted activity: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                super.onActivityResumed(activity);
                Log.d(TAG,"onActivityResumed activity: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                super.onActivityPaused(activity);
                Log.d(TAG,"onActivityPaused activity: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                super.onActivityStopped(activity);
                Log.d(TAG,"onActivityStopped activity: "+activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                super.onActivitySaveInstanceState(activity, outState);
                Log.d(TAG,"onActivitySaveInstanceState activity: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                Log.d(TAG,"onActivityDestroyed activity: "+activity.getLocalClassName());
            }
        });
    }

    //返回
    public static Context getContextObject() {
        return context;
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
