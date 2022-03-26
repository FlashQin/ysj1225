package com.kalacheng.message.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.libuser.model.ApiUsersLine;
import com.kalacheng.message.R;
import com.kalacheng.util.utils.DecimalFormatUtils;

public class MediaDialog extends BaseDialogFragment {
    private OnMediaSelectListener onMediaSelectListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_media;
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
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ApiUsersLine info = bundle.getParcelable("info");
            if (info != null) {
                ((TextView) mRootView.findViewById(R.id.tv_name)).setText(info.userName);
                if (info.voiceCoin > 0 && !TextUtils.isEmpty(info.coinType)) {
                    ((TextView) mRootView.findViewById(R.id.tv_phone_price)).setText(DecimalFormatUtils.isIntegerDouble(info.voiceCoin) + info.coinType + "/分钟");
                } else {
                    mRootView.findViewById(R.id.tv_phone_price).setVisibility(View.GONE);
                }
                if (info.videoCoin > 0 && !TextUtils.isEmpty(info.coinType)) {
                    ((TextView) mRootView.findViewById(R.id.tv_video_price)).setText(DecimalFormatUtils.isIntegerDouble(info.videoCoin) + info.coinType + "/分钟");
                } else {
                    mRootView.findViewById(R.id.tv_video_price).setVisibility(View.GONE);
                }
            }
        }

        mRootView.findViewById(R.id.ll_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMediaSelectListener != null) {
                    onMediaSelectListener.onVideo();
                }
            }
        });

        mRootView.findViewById(R.id.ll_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMediaSelectListener != null) {
                    onMediaSelectListener.onVoice();
                }
            }
        });
    }

    public void setOnMediaSelectListener(OnMediaSelectListener onMediaSelectListener) {
        this.onMediaSelectListener = onMediaSelectListener;
    }

    public interface OnMediaSelectListener {
        void onVideo();

        void onVoice();
    }
}
