package com.kalacheng.one2onelive.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.busooolive.httpApi.HttpApiOTMCall;
import com.kalacheng.busooolive.model.OOOHangupReturn;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.one2onelive.R;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;

//一对一svip踢人
public class SVipIsKickOutDialogFragment extends BaseDialogFragment {

    private long UserID;
    @Override
    protected int getLayoutId() {
        return R.layout.svip_iskickout_dialog;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height =  WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserID = getArguments().getLong("UserID");

        TextView KickOut_no = mRootView.findViewById(R.id.KickOut_no);

        //否
        KickOut_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //是
        TextView KickOut_yes = mRootView.findViewById(R.id.KickOut_yes);
        KickOut_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KickOut();
            }
        });
    }
    //踢人
    public void KickOut(){
        HttpApiOTMCall.kickOutAnch(UserID, 1, LiveConstants.mOOOSessionID, new HttpApiCallBack<OOOHangupReturn>() {
            @Override
            public void onHttpRet(int code, String msg, OOOHangupReturn retModel) {
                ToastUtil.show(msg);
                dismiss();
            }
        });
    }
}
