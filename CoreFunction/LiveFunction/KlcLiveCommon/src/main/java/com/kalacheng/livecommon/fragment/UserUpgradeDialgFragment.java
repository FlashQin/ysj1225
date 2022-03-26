package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalacheng.libuser.model.ApiElasticFrame;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

public class UserUpgradeDialgFragment extends BaseDialogFragment {
    private ApiElasticFrame apiElasticFrame;
    @Override
    protected int getLayoutId() {
        return R.layout.user_upgrade_dialog;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.y = DpUtil.dp2px(70);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiElasticFrame = getArguments().getParcelable("ApiElasticFrame");

        RelativeLayout UpgradeBg = mRootView.findViewById(R.id.Upgrade_bg);
        if (apiElasticFrame.childType ==1){
            UpgradeBg.setBackgroundResource(R.mipmap.bg_user_upgrade);
        }else if(apiElasticFrame.childType == 3){
            UpgradeBg.setBackgroundResource(R.mipmap.bg_wealth);
        }

        RoundedImageView Upgrade_userImage = mRootView.findViewById(R.id.Upgrade_userImage);
        ImageLoader.display(apiElasticFrame.avatar,Upgrade_userImage,R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        TextView Upgrade_userName  = mRootView.findViewById(R.id.Upgrade_userName);
        Upgrade_userName.setText(apiElasticFrame.userName);

        TextView Upgrade_Content = mRootView.findViewById(R.id.Upgrade_Content);
        if (apiElasticFrame.childType ==1){
            Upgrade_Content.setText("升级到用户"+apiElasticFrame.grade+"级");
        }else if(apiElasticFrame.childType == 2){
            Upgrade_Content.setText("升级到主播"+apiElasticFrame.grade+"级");
        }else if(apiElasticFrame.childType == 3){
            Upgrade_Content.setText("升级到财富"+apiElasticFrame.grade+"级");

        }
    }
}
