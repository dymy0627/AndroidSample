package com.example.yulin.exoplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yulin.R;
import com.example.yulin.exoplayer.dash.DashPlayerActivity;
import com.example.yulin.exoplayer.hls.HlsPlayerActivity;

public class ExoPlayerDemoActivity extends Activity {

    private static final String MP4_URL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    private static final String DASH_URL = "https://bitmovin-a.akamaihd.net/content/playhouse-vr/mpds/105560.mpd";
    private static final String HLS_URL = "https://bitmovin.com/player-content/playhouse-vr/m3u8s/105560.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_demo);

        Button mp4Button = (Button) findViewById(R.id.exo_player_mp4);
        Button dashButton = (Button) findViewById(R.id.exo_player_dash);
        Button hlsButton = (Button) findViewById(R.id.exo_player_hls);

        mp4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ExoPlayerDemoActivity.this, ExoPlayerActivity.class);
                intent.putExtra("URL", MP4_URL);
                startActivity(intent);
            }
        });
        dashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ExoPlayerDemoActivity.this, DashPlayerActivity.class);
                intent.putExtra("URL", DASH_URL);
                startActivity(intent);
            }
        });
        hlsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ExoPlayerDemoActivity.this, HlsPlayerActivity.class);
                intent.putExtra("URL", HLS_URL);
                startActivity(intent);
            }
        });
    }
}
