package com.ashin.vplayer.qrCode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.ashin.vplayer.R;
import com.ashin.vplayer.utils.QRCodeUtil;

public class QRCodeActivity extends AppCompatActivity {

    private ImageView bitMapPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        bitMapPic = findViewById(R.id.bitmap);

        Bitmap qrCodeBitmap = QRCodeUtil.createQRCodeBitmap("aaaaaaaaaaaaaaaa", 800, 800, "UTF-8", "H", "1", Color.BLACK, Color.WHITE, null, 0.2F);
        Message msg = Message.obtain();
        msg.obj = qrCodeBitmap;
        setQRHandler.sendMessage(msg);
    }


    private Handler setQRHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            bitMapPic.setImageBitmap(bitmap);
        }
    };
}