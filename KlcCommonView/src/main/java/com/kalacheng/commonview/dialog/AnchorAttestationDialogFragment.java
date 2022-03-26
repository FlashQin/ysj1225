package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.utils.AllSocketUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.SpUtil;

// 未认证主播 请去认证
public class AnchorAttestationDialogFragment {
    private static volatile AnchorAttestationDialogFragment singleton;

    public static AnchorAttestationDialogFragment getInstance() {

        if (singleton == null) {
            synchronized (AllSocketUtlis.class) {
                if (singleton == null) {
                    singleton = new AnchorAttestationDialogFragment();
                }
            }
        }
        return singleton;
    }

    public void show(final Context mContext) {
        final Dialog dialog = new Dialog(mContext, R.style.dialog);
        dialog.setContentView(R.layout.anchor_attestation);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.TopToBottomAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        TextView AnchorAttestation_canle = dialog.findViewById(R.id.AnchorAttestation_canle);
        AnchorAttestation_canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView AnchorAttestation_true = dialog.findViewById(R.id.AnchorAttestation_true);
        AnchorAttestation_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
