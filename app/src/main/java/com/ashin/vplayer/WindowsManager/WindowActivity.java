package com.ashin.vplayer.WindowsManager;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ashin.vplayer.R;

public class WindowActivity extends AppCompatActivity {

    private Handler mHandler = null;
    private Button negativeBtn;
    private Button positiveBtn;
    private TextView textView;

    public static final String TEXT_SUBTITLE_TYPEFACE_MEDIUM = "/system/fonts/FZLTYS_GBK.ttf";
    private String mTypefacePath = TEXT_SUBTITLE_TYPEFACE_MEDIUM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        negativeBtn = findViewById(R.id.negativeBtn);
        positiveBtn = findViewById(R.id.positiveBtn);
        textView = findViewById(R.id.window_textview);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                negativeBtn.animate().translationY(1).alpha(0.8f).setDuration(10).start();
            }
        });

        textView.setTypeface(Typeface.createFromFile(mTypefacePath));
        textView.setText("门门门门门门门门门门门门门门");

    }



}