package com.library.base.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

public class MarqueTextView extends android.support.v7.widget.AppCompatTextView {

    public MarqueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarqueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueTextView(Context context) {
        super(context);
    }

    @Override

    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
//        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    /* <com.library.base.view.MarqueTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"

        android:ellipsize="marquee"
        android:marqueeRepeatLimit="marquee_forever"
        android:singleLine="true"
        android:focusable="true"
        android:focusableInTouchMode="true"
        android:scrollHorizontally="true"
        android:padding="10dp"
        android:text="请先进行身份证验证,才能进行信用卡还款,下次申请无需再验证"
        />*/
}