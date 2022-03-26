package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.MaskImageView;

/**
 * @author: Administrator
 * @date: 2020/11/3
 * @info: 付费提示 （计时房间付费提示） （门票房间付费提示）
 */
public class RoomPayTipsDialogFragment extends BaseDialogFragment {

    private boolean isCanCancel;
    private VideoPayTipsChoiceListener listener;
    private AppJoinRoomVO joinRoomVO;
    private int type; // 1是进入房间的提示   2是更改房间的提示
    private CountDownTimer timer;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_room_pay_tips_layout;
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
    public void onStop() {
        super.onStop();
        if (null != timer){
            timer = null;
        }
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
        joinRoomVO = getArguments().getParcelable("AppJoinRoomVO");
        type = getArguments().getInt("type");
        init();
    }

    private void init(){

        TextView tvVideoPayBtn = mRootView.findViewById(R.id.tvVideoPayBtn);

        LinearLayout llRoomPay = mRootView.findViewById(R.id.llRoomPay);
        TextView tvVideoPayTitle = mRootView.findViewById(R.id.tvVideoPayTitle);
        TextView tvVideoPayTips = mRootView.findViewById(R.id.tvRoomPayTips);
        TextView tvVideoPayMoney = mRootView.findViewById(R.id.tvRoomPayMoney);

        LinearLayout llRoomUpdate = mRootView.findViewById(R.id.llRoomUpdate);
        TextView tvRoomUpdateTips = mRootView.findViewById(R.id.tvRoomUpdateTips);
        final TextView tvRoomUpdateMoney = mRootView.findViewById(R.id.tvRoomUpdateMoney);

        MaskImageView ivBg = mRootView.findViewById(R.id.ivBg);
        if (!TextUtils.isEmpty(joinRoomVO.liveThumb)){
            ImageLoader.displayBlur(joinRoomVO.liveThumb, ivBg);
        }else {
            ImageLoader.displayBlur(joinRoomVO.anchorAvatar, ivBg);
        }
        //ImageLoader.displayBlur(R.mipmap.icon_hall_shadow, ivBg);

        if (type == 1){
            if (null != joinRoomVO){
                if (joinRoomVO.roomType == 1){
                    tvVideoPayTitle.setText("房间模式更改提示");
                    tvRoomUpdateTips.setText("此房间更改为 密码房间");
                    tvRoomUpdateMoney.setText("3s后您将自动退出房间");
                    mRootView.findViewById(R.id.tvVideoPayBtn).setVisibility(View.GONE);
                    llRoomPay.setVisibility(View.GONE);
                    llRoomUpdate.setVisibility(View.VISIBLE);
                }else if (joinRoomVO.roomType == 3){
                    tvVideoPayTitle.setText("计时房间付费提示");
                    tvVideoPayTips.setText("观看Ta直播需收费 ");
                    tvVideoPayMoney.setText(joinRoomVO.roomTypeVal + SpUtil.getInstance().getCoinUnit() + "/分钟");
                    llRoomPay.setVisibility(View.VISIBLE);
                }else if (joinRoomVO.roomType == 2){
                    tvVideoPayTitle.setText("门票房间付费提示");
                    tvVideoPayTips.setText("观看Ta直播需收门票 ");
                    tvVideoPayMoney.setText(joinRoomVO.roomTypeVal + SpUtil.getInstance().getCoinUnit());
                    llRoomPay.setVisibility(View.VISIBLE);
                }else if (joinRoomVO.roomType == 4){
                    tvVideoPayTitle.setText("贵族房间提示");
                    tvVideoPayTips.setText("观看Ta直播需 ");
                    tvVideoPayMoney.setText("开通贵族");
                    tvVideoPayBtn.setText("开通贵族");
                    llRoomPay.setVisibility(View.VISIBLE);
                }
            }
        }else if (type == 2){
            tvVideoPayTitle.setText("房间模式更改提示");
            if (joinRoomVO.roomType == 1){
                tvRoomUpdateTips.setText("此房间更改为 密码房间");
                tvRoomUpdateMoney.setText("3s后您将自动退出房间");
                mRootView.findViewById(R.id.tvVideoPayBtn).setVisibility(View.GONE);
            } else if (joinRoomVO.roomType == 3){
                tvRoomUpdateTips.setText("此房间更改为 计时房间");
                tvRoomUpdateMoney.setText(joinRoomVO.roomTypeVal + SpUtil.getInstance().getCoinUnit() + "/分钟");
            }else if (joinRoomVO.roomType == 2){
                tvRoomUpdateTips.setText("此房间更改为 门票房间");
                tvRoomUpdateMoney.setText(joinRoomVO.roomTypeVal + SpUtil.getInstance().getCoinUnit());
            }else if (joinRoomVO.roomType == 4){
                tvRoomUpdateTips.setText("此房间更改为 贵族房间");
                tvRoomUpdateMoney.setText("开通贵族即可观看直播");
                tvVideoPayBtn.setText("开通贵族");
            }
            llRoomUpdate.setVisibility(View.VISIBLE);
        }

        // 下次再看
        mRootView.findViewById(R.id.tvVideoPayClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.close();
                }
                dismiss();
            }
        });

        // 付费观看
        mRootView.findViewById(R.id.tvVideoPayBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.pey();
                }
            }
        });

        // 点击背景  dismiss
        mRootView.findViewById(R.id.ivBg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCanCancel){
                    dismiss();
                }
            }
        });

        if (joinRoomVO.roomType == 1){
            timer = new CountDownTimer(3000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    tvRoomUpdateMoney.setText((millisUntilFinished / 1000) + "s后您将自动退出房间");
                }

                @Override
                public void onFinish() {
                    if (null != listener){
                        listener.close();
                    }
                    timer.cancel();
                    dismiss();
                }
            };
            timer.start();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                window.setLayout(width, height);
            }
        }
    }

    public void setListener(VideoPayTipsChoiceListener listener){
        this.listener = listener;
    }

    public interface VideoPayTipsChoiceListener{
        void close();
        void pey();
    }

}