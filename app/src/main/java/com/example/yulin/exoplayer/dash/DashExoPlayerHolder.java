package com.example.yulin.exoplayer.dash;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.example.yulin.exoplayer.ExoPlayerHolder;
import com.example.yulin.utils.LogUtil;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;

public final class DashExoPlayerHolder extends ExoPlayerHolder {

    public DashExoPlayerHolder(Context context, SurfaceView surfaceView) {
        super(context, surfaceView);
    }

    @Override
    public void setMediaURL(String url) {
        LogUtil.d(getClass(), "playUrl:" + url);
        mExoPlayer.stop(true);
        if (url.endsWith(".mpd")) {
            Uri uri = Uri.parse(url);
            MediaSource dashMediaSource = new DashMediaSource.Factory(buildDataSourceFactory(mContext)).createMediaSource(uri);
            mExoPlayer.prepare(dashMediaSource);
        }
    }
}