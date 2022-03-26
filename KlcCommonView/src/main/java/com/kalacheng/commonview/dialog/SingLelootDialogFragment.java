package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.utils.AllSocketUtlis;

/*
* 单被抢走了
* */
public class SingLelootDialogFragment  {
    private static volatile SingLelootDialogFragment singleton;

    public static SingLelootDialogFragment getInstance() {

        if (singleton == null) {
            synchronized (AllSocketUtlis.class) {
                if (singleton == null) {
                    singleton = new SingLelootDialogFragment();
                }
            }
        }
        return singleton;
    }

    public void show(Context mContext){

        final Dialog  dialog = new Dialog(mContext, R.style.dialog);
        dialog.setContentView(R.layout.sing_leloot);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.TopToBottomAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags =  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        TextView SingLaloot_True = dialog.findViewById(R.id.SingLaloot_True);
        SingLaloot_True.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
