package com.kalacheng.util.view;

import android.text.TextPaint;
import android.text.style.UnderlineSpan;

/**
 * 无下划线的Span
 *
 * @author ysj create at 2016-8-2 上午11:28:13
 */
public class NoUnderlineSpan extends UnderlineSpan {

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }
}
