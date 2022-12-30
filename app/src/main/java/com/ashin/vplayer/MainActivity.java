package com.ashin.vplayer;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlightHelper;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;

import com.ashin.vplayer.Okhttp.OkhttpActivity;
import com.ashin.vplayer.WindowsManager.WindowActivity;
import com.ashin.vplayer.barrage.BarrageActivity;
import com.ashin.vplayer.glideLea.GlideActivity;
import com.ashin.vplayer.listLearn.ListActivity;
import com.ashin.vplayer.matrix.MatrixActivity;
import com.ashin.vplayer.openGL.OpenGLActivity;
import com.ashin.vplayer.qrCode.QRCodeActivity;
import com.ashin.vplayer.services.ExoPlayerActivity;
import com.ashin.vplayer.smbnfs.SmbNfsActivity;
import com.ashin.vplayer.syncAdapter.SyncActivity;
import com.ashin.vplayer.utils.HPresenter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "VP-MainActivity";

    private VerticalGridView mButtonListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        //退出时使用
        getWindow().setExitTransition(explode);
        //第一次进入时使用
        getWindow().setEnterTransition(explode);
        //再次进入时使用
        getWindow().setReenterTransition(explode);
        mButtonListView=(VerticalGridView) findViewById(R.id.buttonList);
        init();
        checkPermission();
    }

    private void init(){
        mButtonListView.setNumColumns(3);
        mButtonListView.setVerticalSpacing(30);
        mButtonListView.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() {
            @Override
            public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                super.onChildViewHolderSelected(parent, child, position, subposition);
                Log.d(TAG,"onChildViewHolderSelected position: "+position);
            }

            @Override
            public void onChildViewHolderSelectedAndPositioned(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                super.onChildViewHolderSelectedAndPositioned(parent, child, position, subposition);
                Log.d(TAG,"onChildViewHolderSelectedAndPositioned position: "+position);
            }
        });

        HPresenter presenter =new HPresenter(this);
        ArrayObjectAdapter arrayObjectAdapter = new ArrayObjectAdapter(presenter);
        List<Class> itemList=new ArrayList<>();

        itemList.add(MatrixActivity.class);
        itemList.add(ExoPlayerActivity.class);
        itemList.add(BarrageActivity.class);
        itemList.add(GlideActivity.class);
        itemList.add(ListActivity.class);
        itemList.add(OkhttpActivity.class);
        itemList.add(QRCodeActivity.class);
        itemList.add(WindowActivity.class);
        itemList.add(SmbNfsActivity.class);
        itemList.add(OpenGLActivity.class);
        itemList.add(SyncActivity.class);
        arrayObjectAdapter.addAll(0,itemList);

        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(arrayObjectAdapter);
        mButtonListView.setAdapter(itemBridgeAdapter);
        mButtonListView.requestFocus();
        //设置上焦动画
        FocusHighlightHelper.setupHeaderItemFocusHighlight(itemBridgeAdapter);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
//                intent = new Intent();
//                intent.setAction("mitv.mediaexplorer.action.LOCAL_VIDEO_PLAY");
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("url", "http://localhost:25757/2.mp4");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                intent.putExtra("playInfo", jsonObject.toString());
//                startActivity(intent);
//                break;

    }



    //todo: 权限
    private void checkPermission() {
        Log.i(TAG, "Line ==> 检查读取外部存储的权限......");
        int permissionCode = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCode == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Line ==> 已拥有权限......");
            Toast.makeText(MyApplication.getContextObject(), "Line ==> 已拥有权限......", Toast.LENGTH_LONG).show();
        } else {
            Log.i(TAG, "Line ==> 无权限，向用户申请......");
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            Toast.makeText(MyApplication.getContextObject(), "Line ==> 无权限，向用户申请......", Toast.LENGTH_LONG).show();
        }
    }


}