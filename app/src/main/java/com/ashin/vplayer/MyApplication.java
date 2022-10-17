package com.ashin.vplayer;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class MyApplication extends Application {
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
    }

    //返回
    public static Context getContextObject() {
        return context;
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }
}
