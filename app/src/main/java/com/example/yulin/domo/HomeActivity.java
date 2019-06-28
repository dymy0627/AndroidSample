package com.example.yulin.domo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yulin.R;
import com.example.yulin.domo.view.Domo;
import com.example.yulin.domo.view.Food;

import java.util.ArrayList;

public class HomeActivity extends Activity {

    private static final String TAG = "HomeActivity";
    private static final boolean DEBUG = false;

    private ArrayList<ImageView> domoFoodList = new ArrayList<ImageView>();
    private RelativeLayout mHomeBackground;
    public static int measuredWidth = 0;
    public static int measuredHeight = 0;

    private Domo mDomo;
    private EditText mTalkingEditText;

    private DomoService mDomoService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domo_home);
        initWindowLayout();
        mHomeBackground = (RelativeLayout) findViewById(R.id.domoLayout);
        if (DEBUG) {
            mHomeBackground.setBackgroundColor(getResources().getColor(R.color.white));
        }
        LayoutParams layoutParams = (LayoutParams) mHomeBackground.getLayoutParams();
        layoutParams.height = measuredHeight;
        layoutParams.width = measuredWidth;
        mHomeBackground.setLayoutParams(layoutParams);

        mDomo = new Domo(this);

        mTalkingEditText = (EditText) findViewById(R.id.talk_edt);

        Button freeDomoButton = (Button) findViewById(R.id.go_out_btn);
        freeDomoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                startService(new Intent(HomeActivity.this, DomoService.class));
                finish();
            }
        });

        Button sendButton = (Button) findViewById(R.id.send_btn);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mTalkingEditText.getText().toString();
                if (!text.isEmpty()) {
                    mDomo.say(text);
                    mTalkingEditText.setText("");
                }
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            // Android 6.0以上
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
            } else {
                if (mDomo != null) {
                    mHomeBackground.addView(mDomo);
                }
            }
        } else {
            // Android 6.0以下，不用动态声明权限
            if (mDomo != null) {
                mHomeBackground.addView(mDomo);
            }
        }

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    if (mDomo != null) {
                        mHomeBackground.addView(mDomo);
                    }
                }
            }
        }
    }

    private static final int REQUEST_CODE = 1;

    public ArrayList<ImageView> getFoodList() {
        return domoFoodList;
    }

    public void setFoodList(ArrayList<ImageView> foodList) {
        domoFoodList = foodList;
    }


    // make sure mHomeBackground extend full screen
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void initWindowLayout() {
        WindowManager w = getWindowManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            w.getDefaultDisplay().getSize(size);
            measuredWidth = size.x;
            measuredHeight = size.y;
        } else {
            Display d = w.getDefaultDisplay();
            measuredWidth = d.getWidth();
            measuredHeight = d.getHeight();
        }
    }

    public void addView(View view) {
        mHomeBackground.addView(view);
    }

    public void removeView(View view) {
        mHomeBackground.removeView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rX = (int) event.getX();
        int rY = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (rX - mDomo.mWidth / 2 > 0
                        && rX + mDomo.mWidth / 2 < measuredWidth
                        && rY - mDomo.mHeight / 2 > 0
                        && rY + mDomo.mHeight / 2 < measuredHeight) {
                    Food domoFood = new Food(this, rX, rY);
                    mHomeBackground.addView(domoFood);
                    domoFoodList.add(domoFood);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    class CustomView extends View {

        Paint paint;
        int X;
        int Y;

        public CustomView(Context context, int x, int y) {
            super(context);
            X = x;
            Y = y;
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Style.FILL);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(X, Y, 10, paint);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
