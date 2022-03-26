package com.kalacheng.commonview.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

public class MedalToastView extends Toast {
    private static Toast mToast;

    public MedalToastView(Context context) {
        super(context);
    }

    @SuppressLint("WrongConstant")
    public static void showToast(Context context, ApiElasticFrame apiElasticFrame) {

        //获取系统的LayoutInflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mRootView = inflater.inflate(R.layout.user_medal_dialog, null);

        RelativeLayout Re_Medal = mRootView.findViewById(R.id.Re_Medal);
        if (apiElasticFrame.childType == 1) {
            Re_Medal.setBackgroundResource(R.mipmap.medal_one);
        } else if (apiElasticFrame.childType == 2) {
            Re_Medal.setBackgroundResource(R.mipmap.medal_two);
        } else if (apiElasticFrame.childType == 3) {
            Re_Medal.setBackgroundResource(R.mipmap.medal_three);
        }

        RoundedImageView Medal_userImage = mRootView.findViewById(R.id.Medal_userImage);
        ImageLoader.display(apiElasticFrame.avatar, Medal_userImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        TextView Medal_userName = mRootView.findViewById(R.id.Medal_userName);
        Medal_userName.setText("恭喜" + apiElasticFrame.userName);

        TextView Medal_Content = mRootView.findViewById(R.id.Medal_Content);
        Medal_Content.setText("获得" + apiElasticFrame.childTypeName + apiElasticFrame.medalName);

        ImageView Medal_MedalImage = mRootView.findViewById(R.id.Medal_MedalImage);
        ImageLoader.display(apiElasticFrame.medalLogo, Medal_MedalImage);
        //实例化toast
        mToast = new Toast(context);
        mToast.setView(mRootView);
        mToast.setDuration(8);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
        mToast.show();
    }

}
