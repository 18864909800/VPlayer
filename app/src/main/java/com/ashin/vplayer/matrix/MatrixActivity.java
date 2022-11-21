package com.ashin.vplayer.matrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.ashin.vplayer.R;

public class MatrixActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private ImageView mChangeColorIv;
    private SeekBar mHueSeekBar, mSaturationSeekBar, mLumSeekBar;

    private Bitmap mBitmap;

    private float mHue = 0, mSaturation = 1f, mLum = 1f;
    private static final int MID_VALUE = 128;

    private Button oldTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        mChangeColorIv = (ImageView) findViewById(R.id.change_color_iv);
        mHueSeekBar = (SeekBar) findViewById(R.id.hue_seek_bar);
        mSaturationSeekBar = (SeekBar) findViewById(R.id.saturation_seek_bar);
        mLumSeekBar = (SeekBar) findViewById(R.id.lum_seek_bar);

        //获得图片资源
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.matrix);
        mChangeColorIv.setImageBitmap(mBitmap);

        //对seekBar设置监听
        mHueSeekBar.setOnSeekBarChangeListener(this);
        mSaturationSeekBar.setOnSeekBarChangeListener(this);
        mLumSeekBar.setOnSeekBarChangeListener(this);

        oldTimeBtn = (Button) findViewById(R.id.old_time_btn);
        oldTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接操作矩阵 来改变图片的风格(加滤镜);
                Bitmap bmp = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(),
                        Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bmp);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                //操作矩阵
                ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                        0.393f, 0.769f, 0.189f, 0, 0,
                        0.349f, 0.686f, 0.168f, 0, 0,
                        0.272f, 0.534f, 0.131f, 0, 0,
                        0, 0, 0, 1, 0
                });
                paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                canvas.drawBitmap(mBitmap, 0, 0, paint);
                mChangeColorIv.setImageBitmap(bmp);
            }
        });


    }

    @Override
    public  void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.hue_seek_bar:
                //色相的范围是正负180
                mHue = (progress - MID_VALUE) * 1f / MID_VALUE * 180;
                break;
            case R.id.saturation_seek_bar:
                //范围是0-2;
                mSaturation = progress * 1f / MID_VALUE;
                break;
            case R.id.lum_seek_bar:
                mLum = progress * 1f / MID_VALUE;
                break;
            default:
                break;
        }

        Bitmap bitmap = ImageHelper.getChangedBitmap(mBitmap,
                mHue, mSaturation, mLum);
        mChangeColorIv.setImageBitmap(bitmap);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}