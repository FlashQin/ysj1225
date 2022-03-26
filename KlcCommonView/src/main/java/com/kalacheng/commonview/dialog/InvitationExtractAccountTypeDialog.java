package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kalacheng.commonview.R;
import com.kalacheng.base.base.BaseDialogFragment;

/**
 * 账户类型选择
 */
public class InvitationExtractAccountTypeDialog extends BaseDialogFragment {
    private AccountTypeSelectListener accountTypeSelectListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_invitation_extract_account_type;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.BottomDialogStyle;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRootView.findViewById(R.id.tvTypeAlipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountTypeSelectListener != null) {
                    accountTypeSelectListener.onItemSelect("支付宝");
                }
                dismiss();
            }
        });
        mRootView.findViewById(R.id.tvTypeWx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountTypeSelectListener != null) {
                    accountTypeSelectListener.onItemSelect("微信");
                }
                dismiss();
            }
        });
        mRootView.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setAccountTypeSelectListener(AccountTypeSelectListener accountTypeSelectListener) {
        this.accountTypeSelectListener = accountTypeSelectListener;
    }

    public interface AccountTypeSelectListener {
        void onItemSelect(String type);
    }
}
