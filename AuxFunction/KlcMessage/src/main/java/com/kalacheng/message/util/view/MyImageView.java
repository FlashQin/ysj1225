package com.kalacheng.message.util.view;

import android.content.Context;
import android.util.AttributeSet;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by cxf on 2018/6/7.
 */

public class MyImageView extends RoundedImageView {

    private String url;
    private int mMsgId;
    boolean self;

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMsgId() {
        return mMsgId;
    }

    public void setMsgId(int msgId) {
        mMsgId = msgId;
    }

    public boolean isSelf() {
        return self;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }
}
