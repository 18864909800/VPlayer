package com.ashin.vplayer;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ashin.vplayer.Okhttp.OkhttpActivity;
import com.ashin.vplayer.WindowsManager.WindowReceiver;
import com.ashin.vplayer.glideLea.GlideActivity;
import com.ashin.vplayer.listLearn.ListActivity;
import com.ashin.vplayer.qrCode.QRCodeActivity;
import com.ashin.vplayer.services.ExoPlayerActivity;
import com.ashin.vplayer.services.NanoHttp;
//import com.ashin.vplayer.services.PlayerActivity;
import com.ashin.vplayer.services.PlayerActivity;
import com.ashin.vplayer.services.SmbActivity;
import com.ashin.vplayer.services.TimerActivity;
import com.ashin.vplayer.taskList.Task1Activity;
import com.ashin.vplayer.utils.MyLifeCycleObserver;
import com.ashin.vplayer.utils.NetFileUtil;
import com.ashin.vplayer.utils.QRCodeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "VP-MainActivity";

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private TextView textView2;
    Intent intent;

    private NanoHttp nanoHttp;
    private MyLifeCycleObserver myLifeCycleObserver;
    private NetFileUtil netFileUtil;
    private long downTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.button5);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.button7);
        button7.setOnClickListener(this);
        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(this);
        textView2 = findViewById(R.id.text2);
        checkPermission();
        try {
            nanoHttp = new NanoHttp();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                intent = new Intent(MyApplication.getContextObject(), ExoPlayerActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Log.d(TAG, "click button2");
                netFileUtil = new NetFileUtil();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        netFileUtil.setDownloadFinListener(new NetFileUtil.DownloadFinListener() {
                            @Override
                            public void onDownloadFinish(long time) {
                                downTime = time;
                                Log.d(TAG, "downTime: " + downTime);
                            }
                        });
                        netFileUtil.downLoadFileFromNfs(); //6.539527092 mbps
                        // netFileUtil.downFileByYaNfs();  //4.7818 mbps
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContextObject(), "下载完成，时间为: " + downTime, Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.button3:
                intent = new Intent();
                intent.setAction("mitv.mediaexplorer.action.LOCAL_VIDEO_PLAY");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("url", "http://localhost:25757/2.mp4");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("playInfo", jsonObject.toString());
                startActivity(intent);
                break;
            case R.id.button4:
                intent = new Intent(MyApplication.getContextObject(), SmbActivity.class);
                startActivity(intent);
                break;
            case R.id.button5:
                intent = new Intent(MyApplication.getContextObject(), QRCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.button6:
                intent = new Intent(MyApplication.getContextObject(), GlideActivity.class);
                startActivity(intent);
                break;
            case R.id.button7:
                break;
            case R.id.button8:
                break;
            default:
                break;
        }
    }



    //todo: 权限
    private void checkPermission() {
        Log.i(TAG, "Line ==> 检查读取外部存储的权限......");
        int permissionCode = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCode == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Line ==> 已拥有权限......");
            Toast.makeText(MyApplication.getContextObject(), "Line ==> 已拥有权限......", Toast.LENGTH_LONG).show();
        } else {
            Log.i(TAG, "Line ==> 无权限，向用户申请......");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            Toast.makeText(MyApplication.getContextObject(), "Line ==> 无权限，向用户申请......", Toast.LENGTH_LONG).show();
        }
    }


}