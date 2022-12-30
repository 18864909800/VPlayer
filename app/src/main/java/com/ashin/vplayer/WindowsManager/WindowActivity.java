package com.ashin.vplayer.WindowsManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.ashin.vplayer.R;

public class WindowActivity extends AppCompatActivity {

    private Handler mHandler = null;
    private Button negativeBtn;
    private Button positiveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);

        negativeBtn = findViewById(R.id.negativeBtn);
        positiveBtn = findViewById(R.id.positiveBtn);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                negativeBtn.animate().translationY(1).alpha(0.8f).setDuration(10).start();
            }
        });

    }



}