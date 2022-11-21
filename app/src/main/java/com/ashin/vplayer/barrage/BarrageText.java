package com.ashin.vplayer.barrage;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class BarrageText extends androidx.appcompat.widget.AppCompatTextView {
    private int moveStep = 2;

    public BarrageText(Context context) {
        super(context);
    }

    public BarrageText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BarrageText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 此条弹幕的移动速度
     **/
    public int getMoveStep() {
        return moveStep;
    }

    /**
     * 设置此条弹幕的移动速度
     **/
    public void setMoveStep(int moveStep) {
        this.moveStep = moveStep;
    }

}
