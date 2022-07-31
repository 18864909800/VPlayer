package com.ashin.vplayer.services;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ashin.vplayer.MainActivity;
import com.ashin.vplayer.MyApplication;
import com.ashin.vplayer.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;

import java.io.IOException;
import java.net.URL;

public class PlayerActivity extends Activity {
    private static final String TAG="VP-PlayerActivity";
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder holder;
    NanoHttp nanoHttp;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        surfaceView = findViewById(R.id.surface);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    nanoHttp = new NanoHttp();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                try {
//                    dataSource = new UrlMediaDataSource("smb://192.168.31.240/Videos/1.mkv");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                mediaPlayer = new MediaPlayer();
                holder = surfaceView.getHolder();
                //"https://stream7.iqilu.com/10339/upload_transcode/202002/17/20200217021133Eggh6zdlAO.mp4"
                //mediaPlayer.setDataSource(MyApplication.getContextObject(), Uri.parse("http://localhost:8080"));
                //mediaPlayer.setDataSource(dataSource);
                try {
                    mediaPlayer.setDataSource("http://localhost:30025/2.mp4");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                holder.addCallback(new MyCallBack());
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                        mediaPlayer.setLooping(true);
                    }
                });
            }
        }).start();

    }

    private class MyCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mediaPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

}