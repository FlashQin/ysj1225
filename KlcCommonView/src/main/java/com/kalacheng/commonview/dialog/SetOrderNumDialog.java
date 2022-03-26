package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.ToastUtil;

public class SetOrderNumDialog extends BaseDialogFragment {
    private OnSetOrderNumListener onSetOrderNumListener;
    private EditText orderEt;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_set_order_num;
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

        orderEt = mRootView.findViewById(R.id.orderEt);
        mRootView.findViewById(R.id.btn_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                }
        );
        mRootView.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSetOrderNumListener != null) {
                    if (!TextUtils.isEmpty(orderEt.getText().toString().trim())) {
                        onSetOrderNumListener.onConfirm(Integer.parseInt(orderEt.getText().toString().trim()));
                        dismiss();
                    } else {
                        ToastUtil.show("请设置一个排序号");
                    }
                }
            }
        });
    }

    public void setOnSetOrderNumListener(OnSetOrderNumListener onSetOrderNumListener) {
        this.onSetOrderNumListener = onSetOrderNumListener;
    }

    public interface OnSetOrderNumListener {
        void onConfirm(int num);
    }
}
