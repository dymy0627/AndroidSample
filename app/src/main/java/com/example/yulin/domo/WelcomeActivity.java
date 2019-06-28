package com.example.yulin.domo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.yulin.R;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends Activity {

    private static final int GOTO_MAIN_ACTIVITY = 0;
    private static final int SPLASH_TIME_OUT = 1000; // 1秒跳轉

    private Handler mHandler = new MsgHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domo_welcome);
        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, SPLASH_TIME_OUT);
    }

    private static class MsgHandler extends Handler {
        private WeakReference<WelcomeActivity> mActivity;

        MsgHandler(WelcomeActivity activity) {
            mActivity = new WeakReference<WelcomeActivity>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            WelcomeActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == GOTO_MAIN_ACTIVITY) {
                    Intent intent = new Intent();
                    intent.setClass(activity, HomeActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        }
    }
}
