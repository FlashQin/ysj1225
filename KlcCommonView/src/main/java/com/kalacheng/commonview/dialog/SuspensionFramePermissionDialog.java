package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.base.base.SuspensionFramePermission;
import com.kalacheng.commonview.R;

// 权限弹窗
public class SuspensionFramePermissionDialog {

    private static class SingletonHolder {
        private static final SuspensionFramePermissionDialog INSTANCE = new SuspensionFramePermissionDialog();
    }

    private SuspensionFramePermissionDialog() {

    }

    public static final SuspensionFramePermissionDialog getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void show(final Context mContext, final OnPermissionClickListener onPermissionClickListener) {
        final Dialog dialog = new Dialog(mContext, R.style.dialog2);
        dialog.setContentView(R.layout.suspension_frame_permission);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

       /* TextView canle = dialog.findViewById(R.id.canle);
        canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });*/
        TextView open = dialog.findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuspensionFramePermission.getInstance().onpenPermission(mContext);
                if (onPermissionClickListener != null) {
                    onPermissionClickListener.onClick();
                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public interface OnPermissionClickListener {
        void onClick();
    }
}
