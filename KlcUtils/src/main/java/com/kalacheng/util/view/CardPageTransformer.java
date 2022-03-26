package com.kalacheng.util.view;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by wpp on 2017/4/1.
 * 卡片式滑动viewpage
 */

public class CardPageTransformer implements ViewPager.PageTransformer {

    private float minScale;
    private float parallaxSpeed;

    /**
     * 设置参数
     *
     * @param minScale      纵向高度
     * @param parallaxSpeed 横向长度
     */
    public CardPageTransformer(float minScale, float parallaxSpeed) {
        this.minScale = minScale;
        this.parallaxSpeed = parallaxSpeed;
    }

    //    public void transformPage(View view, float position) {
//
//        if (position < -1 ) {
//            view.setTranslationX(0);
//            view.setScaleX(1);
//            view.setScaleY(1);
//            return;
//        }
//
//        if (position >= -1 && position <= 0) {
//            return;
//        }
//
//        if (position > 0 && position <= 1) {
//            view.setScaleX(minScale + (1 - minScale) * (1 - Math.abs(position)));
//            view.setScaleY(minScale + (1 - minScale) * (1 - Math.abs(position)));
////            view.setPivotX(view.getWidth() / 2);
////            view.setPivotY(view.getHeight() / 2);
//            view.setTranslationX(view.getWidth() * -position * parallaxSpeed);
//        }
//        if (position>1){
//            view.setScaleX(minScale + (1 - minScale) * (2 - Math.abs(position)));
//            view.setScaleY(minScale + (1 - minScale) * (2 - Math.abs(position)));
////            view.setPivotX(view.getWidth() / 2);
////            view.setPivotY(view.getHeight() / 2);
//            view.setTranslationX(view.getWidth() * (position-1) * parallaxSpeed);
//
//        }
//
//    }
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(1);
        } else if (position >= 0 && position <= 2) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = minScale + (1 - minScale) * (1 - Math.abs(position));
            view.setTranslationX(parallaxSpeed * pageWidth * -position);

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha(1);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(1);
        }
    }


}

