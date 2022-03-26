package com.kalacheng.commonview.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.ApiBeautifulNumber;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

//赠送toast
public class BeautifulNumToastView extends Toast {
    private static Toast mToast;

    public BeautifulNumToastView(Context context) {
        super(context);
    }

    @SuppressLint("WrongConstant")
    public static void showToast(Context context, ApiBeautifulNumber apiElasticFrame) {

        //获取系统的LayoutInflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mRootView = inflater.inflate(R.layout.beautiful_num_dialog, null);


        RoundedImageView BeautifulNum_userImage = mRootView.findViewById(R.id.BeautifulNum_userImage);
        ImageLoader.display(apiElasticFrame.avatar, BeautifulNum_userImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        TextView BeautifulNum_userName = mRootView.findViewById(R.id.BeautifulNum_userName);
        BeautifulNum_userName.setText("靓号：" + apiElasticFrame.number);


        //实例化toast
        mToast = new Toast(context);
        mToast.setView(mRootView);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }
}
