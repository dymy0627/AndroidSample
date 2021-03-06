package com.example.yulin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yulin.animation.AnimMainActivity;
import com.example.yulin.domo.WelcomeActivity;
import com.example.yulin.exoplayer.ExoPlayerDemoActivity;
import com.example.yulin.facebook.FBMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button exoPlayerButton = (Button) findViewById(R.id.main_exo_btn);
        Button domoButton = (Button) findViewById(R.id.main_domo_btn);
        Button animButton = (Button) findViewById(R.id.main_anim_btn);
        Button facebookButton = (Button) findViewById(R.id.main_facebook_btn);

        exoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ExoPlayerDemoActivity.class);
                startActivity(intent);
            }
        });
        domoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        animButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AnimMainActivity.class);
                startActivity(intent);
            }
        });
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FBMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
