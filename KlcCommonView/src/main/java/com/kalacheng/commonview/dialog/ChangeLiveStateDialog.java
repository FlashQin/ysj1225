package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;

public class ChangeLiveStateDialog extends BaseDialogFragment {
    private OnChangeLiveStateListener onChangeLiveStateListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_change_live_state;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRootView.findViewById(R.id.ll_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChangeLiveStateListener != null) {
                    onChangeLiveStateListener.onChange(0);
                }
                dismiss();
            }
        });

        mRootView.findViewById(R.id.ll_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChangeLiveStateListener != null) {
                    onChangeLiveStateListener.onChange(1);
                }
                dismiss();
            }
        });

        mRootView.findViewById(R.id.ll_button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChangeLiveStateListener != null) {
                    onChangeLiveStateListener.onChange(2);
                }
                dismiss();
            }
        });
    }

    public void setOnChangeLiveStateListener(OnChangeLiveStateListener onChangeLiveStateListener) {
        this.onChangeLiveStateListener = onChangeLiveStateListener;
    }

    public interface OnChangeLiveStateListener {
        void onChange(int liveState);
    }
}
