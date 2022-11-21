package com.ashin.vplayer.barrage;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;

import android.view.View;
import android.widget.FrameLayout;

import android.os.Handler;

import androidx.annotation.NonNull;

public class BarrageView extends FrameLayout {

    /**最大弹幕容纳量**/
    private int container = 300;

    /**宏：触发滑动**/
    private final int SWEEP = 0;

    public BarrageView(Context context) {
        super(context);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private class UIHandler extends Handler{

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SWEEP:
                    if (getChildCount()>container){
                        synchronized (this){
                            removeViews(0,(int)(container*0.6));
                        }
                    }
                    //弹幕移动
                    for(int i = 0; i < getChildCount(); i++){
                        View view = getChildAt(i);
                        if(view instanceof BarrageText){
                            BarrageText barrageText = (BarrageText) view;
                            barrageText.setX(barrageText.getX() - barrageText.getMoveStep());
                            if(barrageText.getX() + barrageText.getWidth() < 0){
                                //Log.i("清理", barrageText.toString());
                                removeViewAt(i);
                                i--;
                            }
                        }
                    }
//                    Log.i("View数量", getChildCount() + "");
                    break;
            }
        }
    }
    private UIHandler handler = new UIHandler();


    /**是否保持轮播**/
    private boolean run = true;
    /**轮播线程**/
    private Loop loop = null;

    /**轮播线程**/
    private class Loop extends Thread {
        @Override
        public void run() {
            super.run();
            while (run) {
                try {
                    Thread.sleep(16);
                    //Log.i("looping", "looping");
                    handler.sendEmptyMessage(SWEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            loop = null;
        }
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //检测本控件的依附情况
        this.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {

            //重新被依附就启动轮播线程
            @Override
            public void onViewAttachedToWindow(View v) {
                run = true;
                startLoop();
            }

            //脱离依附则关闭循环线程
            @Override
            public void onViewDetachedFromWindow(View v) {
                run = false;
                if(loop != null && loop.isAlive()){
                    try {
                        loop.interrupt();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**轮播触发**/
    private void startLoop() {
        //初始化循环
        if (loop == null) {
            loop = new Loop();
        }
        if (!loop.isAlive()) {
            loop.start();
        }
    }




    /**添加弹幕**/
    public void addBarrageText(BarrageText barrageText){
        barrageText.setX(getWidth());
        addView(barrageText);
        startLoop();
    }


    /**可以容纳多少个弹幕**/
    public void setContainer(int container) {
        this.container = container;
    }

}
