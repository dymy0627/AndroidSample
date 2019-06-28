package com.example.yulin.domo.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yulin.R;
import com.example.yulin.domo.HomeActivity;

import java.util.ArrayList;

public class Domo extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener, View.OnLongClickListener {

    private static final boolean DEBUG = false;
    public int mPositionX, mPositionY;
    public int mWidth, mHeight;
    private Context mContx;
    private String dirction = "R";
    private String upDown = "D";
    private boolean wantUD = false;
    private boolean wantRL = true;
    private boolean loopWalking = false;
    private boolean isWalking = false;
    private boolean isLong = false;
    private int footsteps = 15; // 20
    private int walkingSpeed = 400; // 800
    private int xDelta;
    private int yDelta;

    private TalkBubble mTalkBubble;

    private int[] walking = new int[4];
    public Runnable walkingAction = new Runnable() {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public void run() {
            ArrayList<ImageView> foodList = ((HomeActivity) mContx).getFoodList();
            if (!foodList.isEmpty()) {
                isWalking = true;
                Food food = (Food) foodList.get(0);
                final int fW = food.getWidth();
                final int fH = food.getHeight();
                final int fXCenter = food.getLeft() + (fW / 2);
                final int fYCenter = food.getTop() + (fH / 2);
                final int dXCenter = mPositionX + (mWidth / 2);
                final int dYCenter = mPositionY + (mHeight / 2);
                if (dXCenter < fXCenter) {
                    wantRL = true;
                    dirction = "R";
                } else if (dXCenter > fYCenter) {
                    wantRL = true;
                    dirction = "L";
                }
                if (dYCenter < fYCenter) {
                    wantUD = true;
                    upDown = "D";
                } else if (dYCenter > fYCenter) {
                    wantUD = true;
                    upDown = "U";
                } else {
                    wantUD = false;
                }
                final int dMouthX = mPositionX + (mWidth / 4);
                final int dMouthY = mPositionY + (mWidth / 2);
                final int dMouthW = mWidth / 2;
                final int dMouthH = mHeight / 3;
                if (checkRRCollision(dMouthX, dMouthY, dMouthW, dMouthH,
                        food.getLeft(), food.getTop(), fW, fH)) {
                    mWidth += 15;
                    mHeight += 15;
                    foodList.remove(food);
                    ((HomeActivity) mContx).removeView(food);
                    ((HomeActivity) mContx).setFoodList(foodList);
                }
            } else {
                isWalking = true;
            }
            if (isWalking) {
                if (wantRL) {
                    if (dirction.equalsIgnoreCase("R")) {
                        if (loopWalking) {
                            setImageResource(walking[0]);
                        } else {
                            setImageResource(walking[1]);
                        }
                        loopWalking = !loopWalking;
                        mPositionX += footsteps;
                        if (mPositionX + mWidth >= HomeActivity.measuredWidth) {
                            mPositionX -= footsteps;
                            dirction = "L";
                        }
                    } else {
                        if (loopWalking) {
                            setImageResource(walking[2]);
                        } else {
                            setImageResource(walking[3]);
                        }
                        loopWalking = !loopWalking;
                        mPositionX -= footsteps;
                        if (mPositionX + mWidth <= 0) {
                            mPositionX += footsteps;
                            dirction = "R";
                        }
                    }
                }
                if (wantUD) {
                    if (upDown.equalsIgnoreCase("D")) {
                        mPositionY += footsteps;
                        if (mPositionY >= HomeActivity.measuredHeight - getHeight()) {
                            mPositionY -= footsteps;
                            upDown = "U";
                        }
                    } else {
                        mPositionY -= footsteps;
                        if (mPositionY <= 0) {
                            mPositionY += footsteps;
                            upDown = "D";
                        }
                    }
                }
                setLayoutParams();
            } else {
                if (loopWalking) {
                    setImageResource(walking[0]);
                } else {
                    setImageResource(walking[1]);
                }
                loopWalking = !loopWalking;
            }
        }
    };

    public Domo(Context context) {
        super(context);
        mContx = context;
        initDomo();
        setLayoutParams();
        setOnClickListener(this);
        setOnLongClickListener(this);

        mTalkBubble = new TalkBubble(mContx);
        if (mContx instanceof Activity) {
            ((HomeActivity) mContx).addView(mTalkBubble);
        }

        walking[0] = R.drawable.domodance_r_0;
        walking[1] = R.drawable.domodance_r_1;
        walking[2] = R.drawable.domodance_l_0;
        walking[3] = R.drawable.domodance_l_1;

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(walkingSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isLong && mContx instanceof Activity) {
                        ((Activity) mContx).runOnUiThread(walkingAction);
                    }
                }
            }
        }.start();
    }

    public void initDomo() {
        setImageResource(R.drawable.domodance_l_0);
        if (DEBUG) {
            setBackgroundColor(getResources().getColor(R.color.black));
        }
        mPositionX = 200;
        mPositionY = 500;
        mWidth = 100;
        mHeight = 100;
    }

    public void setLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mWidth, mHeight);
        params.leftMargin = mPositionX;
        params.topMargin = mPositionY;
        setLayoutParams(params);
    }

    public void say(String text) {
        mTalkBubble.showBubble(text, mPositionX + mWidth / 2, mPositionY - mHeight / 2);
        if (mWidth > 100 && mHeight > 100) {
            mWidth -= 15;
            mHeight -= 15;
        }
    }

    public int[] getLocationOnScreen() {
        int[] location = new int[2];
        getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        Log.i("Location OnScreen", "X = " + x + "& Y = " + y);
        return location;
    }

    /**
     * Rectangle-Rectangle collision detection
     * <p>
     * The A's left edge is to the left of the B's right edge.
     * The A's right edge is to the right of the B's left edge.
     * The A's bottom edge is below B's top edge.
     * The A's top edge is above B's bottom edge.
     * <p>
     * ----- * -----
     * - B - * - A -
     * ----- * -----
     * *************
     * ----- * -----
     * - A - * - B -
     * ----- * -----
     **/
    public boolean checkRRCollision(int Ax, int Ay, int Aw, int Ah,
                                    int Bx, int By, int Bw, int Bh) {
        return (Ax < Bx + Bw
                && Ax + Aw > Bx
                && Ay < By + Bh
                && Ah + Ay > By);
    }

    @Override
    public void onClick(View v) {
        say("DOMO!");
    }

    @Override
    public boolean onLongClick(View v) {
        isLong = true;
        Vibrator vibrator = (Vibrator) mContx.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
        setImageResource(R.drawable.domodance_2_2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setImageAlpha(125);
        } else {
            setAlpha(125);
        }
        return true;
    }

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
                if (DEBUG) {
                    Log.i("Delta", "xDelta = " + xDelta + ", yDelta = " + yDelta);
                }
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
                    if (DEBUG) {
                        Log.i("---", "--------------moving--------------");
                        getLocationOnScreen();
                        Log.i("getRawX", "rX = " + rX + ", rY = " + rY);
                        Log.i("getRawX-Delta", "aX = " + aX + ", aY = " + aY);
                    }
                    mPositionX = aX;
                    mPositionY = aY;
                    setLayoutParams();
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
