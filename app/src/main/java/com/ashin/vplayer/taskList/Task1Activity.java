package com.ashin.vplayer.taskList;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ashin.vplayer.R;

//1. 在 Android 平台绘制一张图片，使用至少 3 种不同的 API，ImageView，SurfaceView，自定义 View
public class Task1Activity extends Activity {

    ImageView imageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);
        imageView = findViewById(R.id.image1);
        imageView.setImageResource(R.drawable.image);
        progressBar = findViewById(R.id.processBar);
        progressBar.setVisibility(View.VISIBLE);

    }
}