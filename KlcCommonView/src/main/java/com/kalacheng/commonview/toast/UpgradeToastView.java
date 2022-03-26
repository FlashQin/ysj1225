package com.kalacheng.commonview.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

//升级toast
public class UpgradeToastView extends Toast {
    private static Toast mToast;

    public UpgradeToastView(Context context) {
        super(context);
    }

    @SuppressLint("WrongConstant")
    public static void showToast(Context context, ApiElasticFrame apiElasticFrame) {

        //获取系统的LayoutInflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mRootView = inflater.inflate(R.layout.user_upgrade_dialog, null);

        RelativeLayout UpgradeBg = mRootView.findViewById(R.id.Upgrade_bg);
        if (apiElasticFrame.childType == 1) {
            UpgradeBg.setBackgroundResource(R.mipmap.bg_user_upgrade);
        } else if (apiElasticFrame.childType == 3) {
            UpgradeBg.setBackgroundResource(R.mipmap.bg_wealth);
        }

        RoundedImageView Upgrade_userImage = mRootView.findViewById(R.id.Upgrade_userImage);
        ImageLoader.display(apiElasticFrame.avatar, Upgrade_userImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        TextView Upgrade_userName = mRootView.findViewById(R.id.Upgrade_userName);
        Upgrade_userName.setText(apiElasticFrame.userName);

        TextView Upgrade_Content = mRootView.findViewById(R.id.Upgrade_Content);
        if (apiElasticFrame.childType == 1) {
            Upgrade_Content.setText("升级到用户" + apiElasticFrame.grade + "级");
        } else if (apiElasticFrame.childType == 2) {
            Upgrade_Content.setText("升级到主播" + apiElasticFrame.grade + "级");
        } else if (apiElasticFrame.childType == 3) {
            Upgrade_Content.setText("升级到财富" + apiElasticFrame.grade + "级");

        }
        //实例化toast
        mToast = new Toast(context);
        mToast.setView(mRootView);
        mToast.setDuration(8);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
        mToast.show();
    }
}
