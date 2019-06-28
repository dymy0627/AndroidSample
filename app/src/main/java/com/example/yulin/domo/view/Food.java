package com.example.yulin.domo.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.example.yulin.R;
import com.example.yulin.domo.HomeActivity;

public class Food extends android.support.v7.widget.AppCompatImageView {

    public boolean isLong = false;
    private Context mContx;
    private int width;
    private int height;

    private int xDelta;
    private int yDelta;
    private int[] food = new int[4];

    public Food(Context context, int touchX, int touchY) {
        super(context);
        mContx = context;
        food[0] = R.drawable.doughnut01;
        food[1] = R.drawable.sweet01;
        food[2] = R.drawable.sweet02;
        int i = (int) (Math.random() * 3);
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), food[i], dimensions);
        width = dimensions.outWidth;
        height = dimensions.outHeight;
        setImageResource(food[i]);
        setOnLongClickListener(longClickListener);
        setLayoutParams(touchX, touchY);
    }

    public void setLayoutParams(int x, int y) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.leftMargin = x - width / 2; // x is centerX of food view
        params.topMargin = y - height / 2;
        setLayoutParams(params);
    }

    private OnLongClickListener longClickListener = new OnLongClickListener() {
        @SuppressWarnings("deprecation")
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public boolean onLongClick(View v) {
            isLong = true;
            Vibrator vibrator = (Vibrator) mContx
                    .getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setImageAlpha(125);
            } else {
                setAlpha(125);
            }
            return true;
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rX = (int) event.getRawX();
        int rY = (int) event.getRawY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // Delta = Distance between First touch point & Left side of
                // the view
                xDelta = rX - getLeft();
                yDelta = rY - getTop();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isLong) {
                    int aX = rX - xDelta;
                    int aY = rY - yDelta;
                    if (aX < 0) {
                        aX = 0;
                    } else if (aX > HomeActivity.measuredWidth - getWidth()) {
                        aX = HomeActivity.measuredWidth - getWidth();
                    }
                    if (aY < 0) {
                        aY = 0;
                    } else if (aY > HomeActivity.measuredHeight - getHeight()) {
                        aY = HomeActivity.measuredHeight - getHeight();
                    }
                    setLayoutParams(aX, aY);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isLong) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        setImageAlpha(255);
                    } else {
                        setAlpha(255);
                    }
                    isLong = false;
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

}
