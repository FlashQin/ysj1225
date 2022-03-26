package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.DpUtil;

/**
 * @author: Administrator
 * @date: 2020/11/3
 * @info: 支付成功或失败的Dialog
 */
public class PaySuccessOrFailDialogFragment extends BaseDialogFragment {

    private PaySuccessListener listener;
    private int type; // type = 1 支付成功    type = 2 支付失败

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_pay_success_fail;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.dp2px(275);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type = getArguments().getInt("type");
        init();
    }

    private void init(){
        ImageView ivPayIcon = mRootView.findViewById(R.id.ivPayIcon);
        TextView tvPayStatus = mRootView.findViewById(R.id.tvPayStatus);
        TextView tvPayContent = mRootView.findViewById(R.id.tvPayContent);
        TextView tvPaySuccessBtn = mRootView.findViewById(R.id.tvPaySuccessBtn);
        LinearLayout llFailed = mRootView.findViewById(R.id.llFailed);

        if (type == 1){
            ivPayIcon.setImageResource(R.mipmap.icon_payment_success);
            tvPayStatus.setText("支付成功！");
            tvPayContent.setText("");
            tvPaySuccessBtn.setVisibility(View.VISIBLE);
            llFailed.setVisibility(View.GONE);
        }else {
            ivPayIcon.setImageResource(R.mipmap.icon_payment_failed);
            tvPayStatus.setText("支付失败！");
            tvPayContent.setText("");
            tvPaySuccessBtn.setVisibility(View.GONE);
            llFailed.setVisibility(View.VISIBLE);
        }

        // close
        mRootView.findViewById(R.id.ivPayClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.close();
                }
                dismiss();
            }
        });

        // 确定
        mRootView.findViewById(R.id.tvPaySuccessBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.success();
                }
                dismiss();
            }
        });

        // 关闭
        mRootView.findViewById(R.id.tvPayClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.close();
                }
                dismiss();
            }
        });

        // 重新支付
        mRootView.findViewById(R.id.tvPayComeBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.backPay();
                }
                dismiss();
            }
        });
    }

    public void setListener(PaySuccessListener listener){
        this.listener = listener;
    }

    public interface PaySuccessListener{
        void close();
        void success();
        void backPay();
    }

}