package com.kalacheng.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class FullScreenVideoView extends VideoView {
    private int width;
    private int height;

    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMeasure(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



        if (width<height || width==0|| height==0){
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            //int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }else {
            // 默认高度，为了自动获取到focus
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = width;
// 这个之前是默认的拉伸图像
            if (this.width > 0 && this.height > 0) {
                width = this.width;
                height = this.height;
            }
            setMeasuredDimension(width, height);
        }


    }


}
