package com.example.yulin.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.yulin.R;

public class AnimMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_demo);

        Button btnFadeIn = (Button) findViewById(R.id.btnFadeIn);
        Button btnFadeOut = (Button) findViewById(R.id.btnFadeOut);
        Button btnCrossFade = (Button) findViewById(R.id.btnCrossFade);
        Button btnBlink = (Button) findViewById(R.id.btnBlink);
        Button btnZoomIn = (Button) findViewById(R.id.btnZoomIn);
        Button btnZoomOut = (Button) findViewById(R.id.btnZoomOut);
        Button btnRotate = (Button) findViewById(R.id.btnRotate);
        Button btnMove = (Button) findViewById(R.id.btnMove);
        Button btnSlideUp = (Button) findViewById(R.id.btnSlideUp);
        Button btnSlideDown = (Button) findViewById(R.id.btnSlideDown);
        Button btnBounce = (Button) findViewById(R.id.btnBounce);
        Button btnSequential = (Button) findViewById(R.id.btnSequential);
        Button btnTogether = (Button) findViewById(R.id.btnTogether);

        final TextView txtFadeIn = (TextView) findViewById(R.id.txt_fade_in);
        final TextView txtFadeOut = (TextView) findViewById(R.id.txt_fade_out);
        final TextView txtBlink = (TextView) findViewById(R.id.txt_blink);
        final TextView txtZoomIn = (TextView) findViewById(R.id.txt_zoom_in);
        final TextView txtZoomOut = (TextView) findViewById(R.id.txt_zoom_out);
        final TextView txtRotate = (TextView) findViewById(R.id.txt_rotate);
        final TextView txtMove = (TextView) findViewById(R.id.txt_move);
        final TextView txtSlideUp = (TextView) findViewById(R.id.txt_slide_up);
        final TextView txtSlideDown = (TextView) findViewById(R.id.txt_slide_down);
        final TextView txtBounce = (TextView) findViewById(R.id.txt_bounce);
        final TextView txtSeq = (TextView) findViewById(R.id.txt_seq);
        final TextView txtTog = (TextView) findViewById(R.id.txt_tog);
        final TextView txtIn = (TextView) findViewById(R.id.txt_in);
        final TextView txtOut = (TextView) findViewById(R.id.txt_out);

        /*
         * Buttons click events
         */
        // Fade in
        final Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        btnFadeIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtFadeIn.setVisibility(View.VISIBLE);
                txtFadeIn.startAnimation(animFadeIn);
            }
        });

        // Fade out
        final Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        btnFadeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFadeOut.setVisibility(View.VISIBLE);
                txtFadeOut.startAnimation(animFadeOut);
            }
        });

        // Cross fade
        final Animation animCrossFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        final Animation animCrossFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        btnCrossFade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOut.setVisibility(View.VISIBLE);
                // start fade in animation
                txtOut.startAnimation(animCrossFadeIn);
                // start fade out animation
                txtIn.startAnimation(animCrossFadeOut);
            }
        });

        // Blink
        final Animation animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        btnBlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBlink.setVisibility(View.VISIBLE);
                txtBlink.startAnimation(animBlink);
            }
        });

        // Zoom In
        final Animation animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtZoomIn.setVisibility(View.VISIBLE);
                txtZoomIn.startAnimation(animZoomIn);
            }
        });

        // Zoom Out
        final Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtZoomOut.setVisibility(View.VISIBLE);
                txtZoomOut.startAnimation(animZoomOut);
            }
        });

        // Rotate
        final Animation animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtRotate.startAnimation(animRotate);
            }
        });

        // Move
        final Animation animMove = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMove.startAnimation(animMove);
            }
        });

        // Slide Up
        final Animation animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        btnSlideUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSlideUp.startAnimation(animSlideUp);
            }
        });

        // Slide Down
        final Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        btnSlideDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSlideDown.startAnimation(animSlideDown);
            }
        });

        // Bounce
        final Animation animBounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        btnBounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBounce.startAnimation(animBounce);
            }
        });

        // Sequential
        final Animation animSequential = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sequential);
        btnSequential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSeq.startAnimation(animSequential);
            }
        });

        // Together
        final Animation animTogether = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.together);
        btnTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTog.startAnimation(animTogether);
            }
        });

    }
}
