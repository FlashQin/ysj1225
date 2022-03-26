package com.kalacheng.commonview.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;

public class SexUtlis {
    private static volatile SexUtlis singleton;

    private SexUtlis() {

    }

    public static SexUtlis getInstance() {

        if (singleton == null) {
            synchronized (SexUtlis.class) {
                if (singleton == null) {
                    singleton = new SexUtlis();
                }
            }
        }
        return singleton;
    }

    public void setSex(Context mContext, LinearLayout linearLayout, int sex, int age) {
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        if (ConfigUtil.getBoolValue(R.bool.sexNormal)) {
            ImageView imageView = new ImageView(mContext);
            if (sex == 1) {
                imageView.setBackgroundResource(R.mipmap.icon_boy_white);
            } else {
                imageView.setBackgroundResource(R.mipmap.icon_girl_white);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    30, 30);
            layoutParams.rightMargin = 3;
            imageView.setLayoutParams(layoutParams);

            linearLayout.addView(imageView);

            if (age != 0) {
                TextView textView = new TextView(mContext);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(10);
                textView.setText(String.valueOf(age));
                textView.setPadding(3, 0, 0, 0);
                linearLayout.addView(textView);

                LinearLayout.LayoutParams layoutPa = new LinearLayout.LayoutParams(
                        DpUtil.dp2px(31), DpUtil.dp2px(15));
                linearLayout.setLayoutParams(layoutPa);

            }
            linearLayout.setBackgroundResource(sex == 2 ? R.drawable.bg_sex_girl : R.drawable.bg_sex_boy);

        } else {
            ImageView imageView = new ImageView(mContext);

            if (sex == 3) {
                imageView.setBackgroundResource(R.mipmap.icon_sex_2);
            } else if (sex == 4) {
                imageView.setBackgroundResource(R.mipmap.icon_sex_1);
            } else if (sex == 1) {
                imageView.setBackgroundResource(R.mipmap.icon_sex_3);
            } else if (sex == 5) {
                imageView.setBackgroundResource(R.mipmap.icon_sex_4);
            } else {
                imageView.setBackgroundResource(R.mipmap.icon_sex_4);
            }

            linearLayout.addView(imageView);

            linearLayout.setBackgroundResource(0);
        }


    }
}
