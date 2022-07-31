package com.ashin.vplayer.WindowsManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.ashin.vplayer.MyApplication;

public class WindowReceiver extends BroadcastReceiver {
    private static final String TAG = "WindowReceiver";
    private static final String ACTION_WINDOW= "com.ashin.vplayer.window";
    private static final String ACTION_WINDOW_DATA = "com.ashin.vplayer.window.data";

    private Context mContext;
    private Callback callback;

    public WindowReceiver(Context context) {
        mContext = context;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());
        if (ACTION_WINDOW_DATA.equals(intent.getAction())) {
            Log.d(TAG, "ACTION_WINDOW_DATA");
            if (callback != null) {
                callback.onDate(intent.getIntExtra("text", 0));
            }
        }
    }

    public void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_WINDOW_DATA);
        mContext.registerReceiver(this, intentFilter);
    }

    public interface Callback {
        void onDate(int date);
    }
}