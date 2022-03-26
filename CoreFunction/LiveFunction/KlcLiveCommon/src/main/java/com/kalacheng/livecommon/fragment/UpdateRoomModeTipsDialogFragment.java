package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.SpUtil;

/**
 * @author: Administrator
 * @date: 2020/11/5
 * @info: 主播更改房间模式后 用户的倒计时 提示框
 */
public class UpdateRoomModeTipsDialogFragment extends BaseDialogFragment {

    private boolean isCanCancel;
    private UpdateRoomModeTipsListener listener;
    private AppJoinRoomVO joinRoomVO;
    private int time;
    private CountDownTimer timer;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_update_room_mode_tips_layout;
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
            timer.cancel();
        }
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        joinRoomVO = getArguments().getParcelable("AppJoinRoomVO");
        init();
    }

    private void init(){

        final TextView tvTime = mRootView.findViewById(R.id.tvTime);
        TextView tvVideoPayTips = mRootView.findViewById(R.id.tvVideoPayTips);
        TextView tvVideoPayMoney = mRootView.findViewById(R.id.tvVideoPayMoney);

        if (null != joinRoomVO){
            if (joinRoomVO.roomType == 3){
                tvVideoPayTips.setText("此房间更改为 计时房间");
                tvVideoPayMoney.setText(joinRoomVO.roomTypeVal + SpUtil.getInstance().getCoinUnit() + "/分钟");
            }else if (joinRoomVO.roomType == 2){
                tvVideoPayTips.setText("此房间更改为 门票房间");
                tvVideoPayMoney.setText(joinRoomVO.roomTypeVal + SpUtil.getInstance().getCoinUnit() + "金币");
            }else if (joinRoomVO.roomType == 4){
                tvVideoPayTips.setText("此房间更改为 贵族房间");
                tvVideoPayMoney.setText("开通贵族即可观看直播");
            }
        }


        TextView tvContent = mRootView.findViewById(R.id.tvContent);
        tvContent.setText("您的" + SpUtil.getInstance().getCoinUnit() + "余额不足啦！");

        // 下次在看
        mRootView.findViewById(R.id.tvVideoPayClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.close();
                }
            }
        });

        // 付费
        mRootView.findViewById(R.id.tvVideoPayBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.pay();
                }
            }
        });

        timer = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvTime.setText((millisUntilFinished / 1000) + "");
            }

            @Override
            public void onFinish() {
                if (null != listener){
                    listener.close();
                }
                timer.cancel();
            }
        };
        timer.start();
    }

    public void setListener(UpdateRoomModeTipsListener listener){
        this.listener = listener;
    }

    public interface UpdateRoomModeTipsListener{
        void pay();
        void close();
    }

}