package com.ashin.vplayer.matrix;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class ImageHelper {

    /**
     * @param resource   原图
     * @param hue        色相
     * @param saturation 饱和度
     * @param lum        亮度
     * @return bitmap
     */
    public static Bitmap getChangedBitmap(Bitmap resource, float hue, float saturation, float lum) {
        Bitmap bitmap = Bitmap.createBitmap(resource.getWidth(), resource.getWidth(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿

        //调整饱和度
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        //调整色相
        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue); //R
        hueMatrix.setRotate(1, hue);//G
        hueMatrix.setRotate(2, hue);//B

        //调整亮度
        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.postConcat(hueMatrix);
        colorMatrix.postConcat(saturationMatrix);
        colorMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(resource, 0, 0, paint);
        return bitmap;
    }
}
