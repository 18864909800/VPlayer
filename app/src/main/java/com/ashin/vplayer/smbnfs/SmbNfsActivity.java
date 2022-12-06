package com.ashin.vplayer.smbnfs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ashin.vplayer.MyApplication;
import com.ashin.vplayer.R;
import com.ashin.vplayer.services.NanoHttp;
import com.ashin.vplayer.utils.NetFileUtil;

import java.io.IOException;

public class SmbNfsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="VP-SmbNfsActivity";

    private Button downNfs;
    private Button startNano;
    private Button stopNano;
    private NanoHttp nanoHttp;
    private NetFileUtil netFileUtil;
    private long downTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smbnfs);
        downNfs = findViewById(R.id.downloadNfs);
        startNano=findViewById(R.id.startNanoHttp);
        stopNano=findViewById(R.id.stopNanoHttp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.downloadNfs:
                Log.d(TAG, "click downloadNfs");
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
            case R.id.startNanoHttp:
                try {
                    nanoHttp = new NanoHttp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.stopNanoHttp:
                nanoHttp.stopServer();
                break;
            default:
                break;
        }
    }
}