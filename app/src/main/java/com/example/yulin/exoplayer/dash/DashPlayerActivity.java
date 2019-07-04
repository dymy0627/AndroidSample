package com.example.yulin.exoplayer.dash;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.example.yulin.R;
import com.example.yulin.utils.LogUtil;

public class DashPlayerActivity extends Activity {

    private DashExoPlayerHolder mDashPlayerHolder;
    private String mPlayUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(getClass(), "onCreate()");
        setContentView(R.layout.activity_exo_player);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);

        mDashPlayerHolder = new DashExoPlayerHolder(this, surfaceView);
        mPlayUrl = getIntent().getStringExtra("URL");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(getClass(), "onResume()");
        mDashPlayerHolder.setMediaURL(mPlayUrl);
        mDashPlayerHolder.start();
    }

    @Override
    protected void onPause() {
        LogUtil.d(getClass(), "onPause()");
        mDashPlayerHolder.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LogUtil.d(getClass(), "onDestroy()");
        mDashPlayerHolder.release();
        super.onDestroy();
    }
}
