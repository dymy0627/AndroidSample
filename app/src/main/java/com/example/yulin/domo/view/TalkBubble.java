package com.example.yulin.domo.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yulin.R;


/**
 * Created by andyliu on 2017/11/16.
 */
public class TalkBubble extends FrameLayout {

    private TextView mSpeakText;
    private FrameLayout mBubbleFrameLayout;

    public TalkBubble(Context context) {
        this(context, null);
    }

    public TalkBubble(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TalkBubble(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.frame_layout_talking_bubble, this);
        mSpeakText = (TextView) findViewById(R.id.domoTalk);
        mBubbleFrameLayout = (FrameLayout) findViewById(R.id.domoTalkFrame);
        mBubbleFrameLayout.setVisibility(View.INVISIBLE);
    }

    public void setText(CharSequence text) {
        mSpeakText.setText(text);
    }

    public void showBubble(CharSequence text, int x, int y) {
        setText(text);
        if (getContext() instanceof Activity) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = x;
            params.topMargin = y;
            setLayoutParams(params);
        }
        mBubbleFrameLayout.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBubbleFrameLayout.setVisibility(View.INVISIBLE);
            }
        }, 2000);
    }
}
