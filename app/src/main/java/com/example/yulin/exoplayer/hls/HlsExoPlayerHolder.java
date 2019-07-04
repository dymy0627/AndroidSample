package com.example.yulin.exoplayer.hls;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.example.yulin.exoplayer.ExoPlayerHolder;
import com.example.yulin.utils.LogUtil;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.DefaultHlsDataSourceFactory;
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public final class HlsExoPlayerHolder extends ExoPlayerHolder {

    public HlsExoPlayerHolder(Context context, SurfaceView surfaceView) {
        super(context, surfaceView);
    }

    @Override
    public void setMediaURL(String url) {
        LogUtil.d(getClass(), "playUrl:" + url);
        mExoPlayer.stop(true);
        if (url.endsWith(".m3u8")) {
            MediaSource mediaSource = buildMediaSource(Uri.parse(url));
            mExoPlayer.prepare(mediaSource, true, false);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        HlsDataSourceFactory hlsDataSourceFactory =
                new DefaultHlsDataSourceFactory(
                        new DefaultHttpDataSourceFactory(
                                Util.getUserAgent(mContext, "Exo2"), new DefaultBandwidthMeter()));
        return new HlsMediaSource.Factory(hlsDataSourceFactory).createMediaSource(uri);
    }
}