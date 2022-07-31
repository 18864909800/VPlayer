package com.ashin.vplayer.utils;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLifeCycleObserver implements LifecycleObserver {
    private static final String TAG = "MyLocationListener";

    public MyLifeCycleObserver(Context activity, OnLocationChangeListener changeListener) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void startGetLocation() {
        Log.i(TAG, "OnResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void stopGetLocation() {
        Log.i(TAG, "OnPause");
    }


    public interface OnLocationChangeListener {
        void onChange(double latitude, double longitude);
    }
}
