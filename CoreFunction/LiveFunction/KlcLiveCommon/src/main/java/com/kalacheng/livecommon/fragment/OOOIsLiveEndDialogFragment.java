package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.busooolive.httpApi.HttpApiOTMCall;
import com.kalacheng.busooolive.model.OOOHangupReturn;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiCloseLine;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.livecommon.R;
import com.klc.bean.OOOLiveHangUpBean;

public class OOOIsLiveEndDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private TextView live_time;
    private TextView live_canle;
    private TextView live_end;

    private String LiveTime;

    @Override
    protected int getLayoutId() {
        return R.layout.live_is_end_dialog;
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
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LiveTime = getArguments().getString("LiveTime");

//        imApiOOOLive = new IMApiOOOLive();
//        imApiOOOLive.init(IMUtil.getClient());
        live_time = mRootView.findViewById(R.id.live_time);
        live_canle = mRootView.findViewById(R.id.live_canle);
        live_end = mRootView.findViewById(R.id.live_end);

        live_time.setText("通话时长：" + LiveTime);

        live_end.setOnClickListener(this);
        live_canle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.live_canle) {//取消弹框
            dismiss();
        } else if (i == R.id.live_end) {//关闭直播
            HttpApiOTMCall.otmHangup(1, LiveConstants.mOOOSessionID, new HttpApiCallBack<OOOHangupReturn>() {
                @Override
                public void onHttpRet(int code, String msg, OOOHangupReturn retModel) {
                    // ggm 1成功；成功的时候应该跳转到显示通话费用界面。网络正常的时候，还会发送一个结束会话的消息。
                    // ggm  2通话已经结束；retModel是null。这个时候应该是提示一下，
                    // ggm 返回非1,应该要关闭Activity


                    if (code == 1) {
                        OOOLiveHangUpBean bean = new OOOLiveHangUpBean();
                        bean.callTime = retModel.callTime;
                        bean.totalCoin = (int) retModel.totalCoin;
                        bean.sessionID = LiveConstants.mOOOSessionID;
                        bean.uid = retModel.callUpUid;
                        bean.name =retModel.username;
                        bean.vipGradeMsg = retModel.vipGradeMsg;
                        bean.vipCount = retModel.vipCount;
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCallEnd,bean);
                    } else {
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCloseLive, new ApiCloseLine());
                        ToastUtil.show(msg);
                    }
                    LiveConstants.mOOOSessionID=0;
                    dismiss();
                }
            });

        }
    }
}
