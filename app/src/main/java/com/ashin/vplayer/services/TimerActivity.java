package com.ashin.vplayer.services;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.TextView;

import com.ashin.vplayer.R;

import java.text.DecimalFormat;
import java.util.Timer;

public class TimerActivity extends AppCompatActivity {
    private TextView timerView;
    private long baseTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timerView = (TextView) this.findViewById(R.id.timerView);
        Handler myhandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (0 == TimerActivity.this.baseTimer) {
                    TimerActivity.this.baseTimer = SystemClock.elapsedRealtime();
                }

                int time = (int) ((SystemClock.elapsedRealtime() - TimerActivity.this.baseTimer) / 1000);
                String hh = new DecimalFormat("00").format(time / 3600);
                String mm = new DecimalFormat("00").format(time % 3600 / 60);
                String ss = new DecimalFormat("00").format(time % 60);
                if (null != TimerActivity.this.timerView) {
                    timerView.setText(hh + ":" + mm + ":" + ss);
                }
                sendMessageDelayed(Message.obtain(this, 0x0), 1000);
            }
        };
        myhandler.sendMessageDelayed(Message.obtain(myhandler, 0x0), 1000);
    }
}