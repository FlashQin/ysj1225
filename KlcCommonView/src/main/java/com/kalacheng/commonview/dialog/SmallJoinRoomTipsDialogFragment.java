package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.commonview.R;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.util.utils.LruJsonCache;

public class SmallJoinRoomTipsDialogFragment extends BaseDialogFragment {
    private SmallJoinRoomTipsCallBack callBack;

    public SmallJoinRoomTipsDialogFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.small_joinroom_tips;
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
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView canle = mRootView.findViewById(R.id.canle);
        canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onCanle();
                }
            }
        });

        TextView confirm = mRootView.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    LruJsonCache.get(mContext).remove(LiveConstants.Cache_SmallMessage);
                    callBack.onConfirm();
                }
            }
        });
    }

    public void setSmallJoinRoomTipsCallBack(SmallJoinRoomTipsCallBack back) {
        this.callBack = back;
    }

    public interface SmallJoinRoomTipsCallBack {
        public void onCanle();

        public void onConfirm();

    }
}
