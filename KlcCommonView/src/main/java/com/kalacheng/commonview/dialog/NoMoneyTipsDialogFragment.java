package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.MaskImageView;

import org.greenrobot.eventbus.EventBus;

/**
 * @author: Administrator
 * @date: 2020/11/5
 * @info: 余额不足提示 可用在所有位置（当前用在直播间）
 */
public class NoMoneyTipsDialogFragment extends BaseDialogFragment {

    private NoMoneyTipsListener listener;
    private AppJoinRoomVO joinRoomVO;
    private double money;
    private boolean isCanCancel;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_no_money_tips_layout;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected boolean canCancel() {
        return isCanCancel;
    }

    public void setCanCancel(boolean isCanCancel){
        this.isCanCancel = isCanCancel;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null != getArguments()){
            joinRoomVO = getArguments().getParcelable("AppJoinRoomVO");
            money = getArguments().getDouble("money");
        }
        init();
    }

    private void init() {

        TextView tvContent = mRootView.findViewById(R.id.tvContent);
        MaskImageView ivBg = mRootView.findViewById(R.id.ivBg);
        tvContent.setText("您的" + SpUtil.getInstance().getCoinUnit() + "余额不足啦！");

        if (money > 0) {
            ivBg.setVisibility(View.GONE);
        }else {
            if (null != joinRoomVO){
                ivBg.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(joinRoomVO.liveThumb)){
                    ImageLoader.displayBlur(joinRoomVO.liveThumb, ivBg);
                }else {
                    ImageLoader.displayBlur(joinRoomVO.anchorAvatar, ivBg);
                }
            }
        }

        // 关闭
        mRootView.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.close();
                }
            }
        });

        // 去充值
        mRootView.findViewById(R.id.tvGoRecharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.goRecharge();
                }
            }
        });
    }

    public void setListener(NoMoneyTipsListener listener) {
        this.listener = listener;
    }

    public interface NoMoneyTipsListener {
        void goRecharge();

        void close();
    }

}