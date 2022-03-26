package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.SpUtil;

/**
 * 主播需要认证
 */
public class AnchorRequestDialog extends BaseDialogFragment {
    private int anchorAuditStatus = -1;//主播审核状态

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_anchor_request;
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
        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView SingLaloot_title = mRootView.findViewById(R.id.SingLaloot_title);
        if (anchorAuditStatus == 1) {
            SingLaloot_title.setText("审核中，请耐心等待");
        } else if (anchorAuditStatus == 2) {
            SingLaloot_title.setText("审核未通过，请重新提交资料");
        }

        mRootView.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mRootView.findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (anchorAuditStatus != 1) {
                    if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.AUTH_IS_SEX, 1) == 0) {
                        ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                        if (apiUserInfo != null && apiUserInfo.sex == 2) {
                            ARouter.getInstance().build(ARouterPath.ApplyAnchorActivity).navigation();
                        } else {
                            DialogUtil.showKnowDialog(mContext, "暂时只支持小姐姐认证哦~", null);
                        }
                    } else {
                        ARouter.getInstance().build(ARouterPath.ApplyAnchorActivity).navigation();
                    }
                }
                dismiss();
            }
        });
    }

    public void setAnchorAuditStatus(int anchorAuditStatus) {
        this.anchorAuditStatus = anchorAuditStatus;
    }
}
