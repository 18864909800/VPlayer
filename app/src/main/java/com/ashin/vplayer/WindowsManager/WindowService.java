package com.ashin.vplayer.WindowsManager;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.ashin.vplayer.MainActivity;
import com.ashin.vplayer.MyApplication;
import com.ashin.vplayer.R;

public class WindowService extends Service {
    private static final String TAG = "WindowService";
    WindowReceiver windowReceiver;

    public WindowService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册BroadcastReceiver
       // checkDrawWindow();
        openWindow();
        windowReceiver = new WindowReceiver(MyApplication.getContextObject());
        windowReceiver.register();
        windowReceiver.setCallback(callback);
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeViewImmediate(inflate);
        Log.d(TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private final WindowReceiver.Callback callback = new WindowReceiver.Callback() {
        @Override
        public void onDate(int date) {
            Log.d(TAG, "date: " + date);
            getDate(date);
        }
    };

    private void getDate(int date) {
        Log.d(TAG, "date: " + date);
    }

    //检查是否有悬浮窗权限，目前TV不具有此权限
    private void checkDrawWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "当前无权限");
//            Intent intent1 = new Intent();
//            intent1.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//            //intent1.setPackage("com.ashin.vplayer");
//            intent1.setData(Uri.parse("package:" + getPackageName()));
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));//应用的包名，可直接跳转到这个应用的悬浮窗设置；
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            openWindow();
        }
    }

    WindowManager windowManager;
    View inflate;

    private void openWindow() {
        windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        //layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        //layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        //layoutParams.token = MainActivity.getWindow().getDecorView().getWindowToken();  //这样设置，在activity中打开悬浮框可绕过权限；
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            //layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.TRANSLUCENT;  //透明
        layoutParams.gravity = Gravity.TOP | Gravity.RIGHT;  //右上角显示
        //inflate = LayoutInflater.from(MyApplication.getContextObject()).inflate(R.layout.tvme_control_view_course, null);

        windowManager.addView(inflate, layoutParams);
    }
}