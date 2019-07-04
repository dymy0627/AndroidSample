package com.example.yulin.exoplayer;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.example.yulin.utils.LogUtil;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

public class ExoPlayerHolder implements IPlayer, Player.EventListener, VideoListener {

    protected SimpleExoPlayer mExoPlayer;
    protected Context mContext;

    public ExoPlayerHolder(Context context, SurfaceView surfaceView) {
        this.mContext = context;
        this.mExoPlayer = ExoPlayerFactory.newSimpleInstance(context);
        this.mExoPlayer.setVideoSurfaceView(surfaceView);
        this.mExoPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
        this.mExoPlayer.addListener(this);
        this.mExoPlayer.addVideoListener(this);
    }

    @Override
    public void onRenderedFirstFrame() {
        LogUtil.d(getClass(), "onRenderedFirstFrame");
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        LogUtil.d(getClass(), "onVideoSizeChanged: width=" + width + ", height=" + height);
        LogUtil.d(getClass(), "onVideoSizeChanged: unappliedRotationDegrees=" + unappliedRotationDegrees);
        LogUtil.d(getClass(), "onVideoSizeChanged: pixelWidthHeightRatio=" + pixelWidthHeightRatio);
    }

    @Override
    public void onSurfaceSizeChanged(int width, int height) {
        LogUtil.d(getClass(), "onSurfaceSizeChanged: width=" + width + ", height=" + height);
    }

    public DataSource.Factory buildDataSourceFactory(Context context) {
        return new DefaultDataSourceFactory(context, buildHttpDataSourceFactory(context));
    }

    private HttpDataSource.Factory buildHttpDataSourceFactory(Context context) {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "ExoPlayerDemo"));
    }

    @Override
    public void setMediaURL(String url) {
        LogUtil.d(getClass(), "playUrl:" + url);
        mExoPlayer.stop(true);
        Uri uri = Uri.parse(url);
        DataSource.Factory dataSourceFactory = buildDataSourceFactory(mContext);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        mExoPlayer.prepare(mediaSource);
    }

    @Override
    public void release() {
        this.mExoPlayer.release();
    }

    @Override
    public void start() {
        this.mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void stop() {
        this.mExoPlayer.setPlayWhenReady(false);
    }
}
