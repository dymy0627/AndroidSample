package com.example.yulin.exoplayer.hls;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.example.yulin.R;
import com.example.yulin.exoplayer.ExoPlayerHolder;
import com.example.yulin.utils.LogUtil;

public class HlsPlayerActivity extends Activity {

    private ExoPlayerHolder mHlsPlayerHolder;
    private String mPlayUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(getClass(), "onCreate()");
        setContentView(R.layout.activity_exo_player);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);

        mHlsPlayerHolder = new HlsExoPlayerHolder(this, surfaceView);
        mPlayUrl = getIntent().getStringExtra("URL");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(getClass(), "onResume()");
        mHlsPlayerHolder.setMediaURL(mPlayUrl);
        mHlsPlayerHolder.start();
    }

    @Override
    protected void onPause() {
        LogUtil.d(getClass(), "onPause()");
        mHlsPlayerHolder.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LogUtil.d(getClass(), "onDestroy()");
        mHlsPlayerHolder.release();
        super.onDestroy();
    }
}
