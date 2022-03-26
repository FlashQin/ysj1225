package com.kalacheng.livecommon.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiCloseLine;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.StringUtil;
import com.klc.bean.OOOLiveHangUpBean;

/*
* 视频结束
* */
public class OOOLiveEndDialogFragment extends BaseDialogFragment {

    private OOOLiveHangUpBean bean;

    //免费时长
    private String freeCallMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.ooo_live_end;
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

    public void getOOOLiveHangUpInformation(OOOLiveHangUpBean bean, String freeCallMsg){
        this.bean =bean;
        this.freeCallMsg = freeCallMsg;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    close();
                    return true; // pretend we've processed it
                } else {
                    return false; // pass on to be processed as normal
                }
            }
        });
        getInitView();
    }


    private void close(){
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCloseLive, new ApiCloseLine());
        dismiss();
    }

    public void getInitView(){
        if (bean == null){
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCloseLive, new ApiCloseLine());
            dismiss();
        }else {
//            LiveBundle.getInstance().removeOtherMsgListener(LiveConstants.LM_OOOCloseLive);
            TextView Live_Exit = mRootView.findViewById(R.id.Live_Exit);
            Live_Exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    close();
                }
            });

            TextView live_time = mRootView.findViewById(R.id.live_time);
            live_time.setText(StringUtil.getDurationText2(bean.callTime));

            TextView live_gold = mRootView.findViewById(R.id.live_gold);
            live_gold.setText(DecimalFormatUtils.isIntegerDouble(bean.totalCoin));

            TextView End_FreeTime = mRootView.findViewById(R.id.End_FreeTime);

            if (!TextUtils.isEmpty(freeCallMsg)) {
                End_FreeTime.setText("含" + freeCallMsg);
                End_FreeTime.setVisibility(View.VISIBLE);
            } else {
                End_FreeTime.setVisibility(View.GONE);
            }

            LinearLayout lin_live_gold = mRootView.findViewById(R.id.lin_live_gold);
            // 是否为 副播
            lin_live_gold.setVisibility(View.VISIBLE);

            // 如果 这个人不是主播 也不是消费者 那么这个人是 副播
            if (LiveConstants.ANCHORID != HttpClient.getUid() && LiveConstants.FEEUID != HttpClient.getUid()){
                lin_live_gold.setVisibility(View.VISIBLE);
            }else if (LiveConstants.ANCHORID == HttpClient.getUid()){
                lin_live_gold.setVisibility(View.VISIBLE);
            }else {
                lin_live_gold.setVisibility(View.GONE);
                End_FreeTime.setVisibility(View.GONE);
            }
        }

    }

}
