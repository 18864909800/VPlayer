package com.ashin.vplayer.services;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import com.ashin.vplayer.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;

/**
 * @author ashin
 */
public class ExoPlayerActivity extends Activity {
    private static final String TAG="VP-ExoPlayerActivity";
    private SurfaceView mSurfaceView;
    private SimpleExoPlayer mExoplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exo_player_activity);

        mSurfaceView=(SurfaceView) findViewById(R.id.surface);
        mExoplayer=new SimpleExoPlayer.Builder(this).build();
        mExoplayer.setVideoSurface(mSurfaceView.getHolder().getSurface());

        MediaItem item=MediaItem.fromUri("https://stream7.iqilu.com/10339/upload_transcode/202002/17/20200217021133Eggh6zdlAO.mp4");
        mExoplayer.setMediaItem(item);

        mExoplayer.prepare();
        mExoplayer.play();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TrackGroupArray currentTrackGroups = mExoplayer.getCurrentTrackGroups();
        Log.d(TAG,"length: "+currentTrackGroups.length);
        DefaultTrackSelector defaultTrackSelector=new DefaultTrackSelector(this);
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo!=null){
            Log.d(TAG,currentMappedTrackInfo.toString());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        TrackGroupArray currentTrackGroups = mExoplayer.getCurrentTrackGroups();
        TrackSelector trackSelector = mExoplayer.getTrackSelector();

        Log.d(TAG,"length2: "+currentTrackGroups.length);
        Log.d(TAG,"AAAAA"+currentTrackGroups.get(0).getFormat(0));
       // Log.d(TAG,"AAAAA"+currentTrackGroups.get(0).getFormat(1));
        Log.d(TAG,"BBBBB"+currentTrackGroups.get(1).getFormat(0));
       // Log.d(TAG,"BBBBB"+currentTrackGroups.get(1).getFormat(1));
//        2022-07-31 11:23:45.546 22069-22069/com.ashin.vplayer D/VP-ExoPlayerActivity: AAAAAFormat(1, null, null, video/avc, avc1.4D401F, -1, null, [960, 540, 25.0], [-1, -1])
//        2022-07-31 11:23:45.546 22069-22069/com.ashin.vplayer D/VP-ExoPlayerActivity: BBBBBFormat(2, null, null, audio/mp4a-latm, mp4a.40.2, -1, und, [-1, -1, -1.0], [2, 48000])
        DefaultTrackSelector defaultTrackSelector=new DefaultTrackSelector(this);

        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = defaultTrackSelector.getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo!=null){
            TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(0);
            TrackGroup trackGroup = trackGroups.get(0);
            Log.d(TAG,"CCC"+trackGroup.getFormat(0));
        }

        mExoplayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}