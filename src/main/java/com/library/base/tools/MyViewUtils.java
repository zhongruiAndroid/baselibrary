package com.library.base.tools;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by Administrator on 2018/1/15.
 */

public class MyViewUtils {
    public static SpannableStringBuilder getTextViewSpan(int startIndex, int endIndex, String content, String color) {
        return getTextViewSpan(startIndex,endIndex,content,Color.parseColor(color));
    }
    public static SpannableStringBuilder getTextViewSpan(int startIndex, int endIndex, String content, @ColorInt int color) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        spannable.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}
