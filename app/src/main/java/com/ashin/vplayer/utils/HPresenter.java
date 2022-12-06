package com.ashin.vplayer.utils;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.leanback.widget.Presenter;

import com.ashin.vplayer.MyApplication;
import com.ashin.vplayer.R;
import com.ashin.vplayer.matrix.MatrixActivity;
import com.google.android.exoplayer2.C;

import java.util.List;

public class HPresenter extends Presenter {
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (context == null) {
            context = parent.getContext();
        }
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_list, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Button button = (Button) viewHolder.view.findViewById(R.id.buttonItem);
        Class<?> aClass = (Class<?>) item;
        if (item != null) {
            button.setText(aClass.getSimpleName());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContextObject(), aClass);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    @Override
    public void setOnClickListener(ViewHolder holder, View.OnClickListener listener) {
        super.setOnClickListener(holder, listener);
    }
}
