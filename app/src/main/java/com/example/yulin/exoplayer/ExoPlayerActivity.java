package com.example.yulin.exoplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.example.yulin.R;
import com.example.yulin.utils.LogUtil;

public class ExoPlayerActivity extends Activity {

    private ExoPlayerHolder mExoPlayerHolder;
    private String mPlayUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(getClass(), "onCreate()");
        setContentView(R.layout.activity_exo_player);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);

        mExoPlayerHolder = new ExoPlayerHolder(this, surfaceView);
        mPlayUrl = getIntent().getStringExtra("URL");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(getClass(), "onResume()");
        mExoPlayerHolder.setMediaURL(mPlayUrl);
        mExoPlayerHolder.start();
    }

    @Override
    protected void onPause() {
        LogUtil.d(getClass(), "onPause()");
        mExoPlayerHolder.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LogUtil.d(getClass(), "onDestroy()");
        mExoPlayerHolder.release();
        super.onDestroy();
    }
}
