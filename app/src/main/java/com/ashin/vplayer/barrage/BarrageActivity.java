package com.ashin.vplayer.barrage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.ashin.vplayer.R;

import java.util.Random;

public class BarrageActivity extends AppCompatActivity {
    private BarrageView barrageView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrage);
        barrageView = findViewById(R.id.barrageView);
        final Runnable barrageRunnable = new Runnable() {
            final Random random = new Random();
            @Override
            public void run() {
                BarrageText barrageText = new BarrageText(BarrageActivity.this);
                barrageText.setText(getRandomString(random.nextInt(15)));
                barrageText.setY(random.nextInt(1080));
                barrageText.setTextSize((int)(30 + Math.random()*(100 - 1 + 30)));
                barrageText.setMoveStep((int)(30 + Math.random()*(100 - 1 + 30)));
                barrageText.setTextColor((0xFF000000 | (random.nextInt(255) & 0xFF) << 16 | (random.nextInt(255) & 0xFF) << 8 | (random.nextInt(255) & 0xFF)));
                barrageView.addBarrageText(barrageText);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    handler.post(barrageRunnable);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public String getRandomString(int length){
        String str="弹幕测试，好好玩哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}