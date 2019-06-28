package com.example.yulin.domo;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.yulin.domo.view.Domo;

public class DomoService extends Service {
    public DomoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Domo mDomo;
    private WindowManager mWindowManager;

    @Override
    public void onCreate() {
        super.onCreate();
        //Inflate the floating view layout we created
//        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);

        //here is all the science of params
//        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
//                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//                PixelFormat.TRANSLUCENT
//        );

        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;
        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        mDomo = new Domo(this);
        mDomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });
        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mDomo, params);
    }

    @Override
    public void onDestroy() {
        if (mDomo != null) mWindowManager.removeView(mDomo);
        super.onDestroy();
    }
}
