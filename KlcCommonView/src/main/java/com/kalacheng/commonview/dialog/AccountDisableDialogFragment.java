package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalacheng.commonview.R;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.SystemUtils;
import com.kalacheng.util.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * 账号禁用
 * */
public class AccountDisableDialogFragment {


    private Dialog dialog;

    public AccountDisableDialogFragment(Context mContext, String mContent) {
        dialog = new Dialog(mContext, R.style.dialog2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.setContentView(R.layout.account_disable);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        TextView AccountDisable_DayNum = dialog.findViewById(R.id.AccountDisable_DayNum);
        TextView AccountDisable_Content = dialog.findViewById(R.id.AccountDisable_Content);
        TextView AccountDisable_Cancel = dialog.findViewById(R.id.AccountDisable_Cancel);
        TextView AccountDisable_Appeal = dialog.findViewById(R.id.AccountDisable_Appeal);
        ImageView AccountDisable_Close = dialog.findViewById(R.id.AccountDisable_Close);
        ImageView iv_line = dialog.findViewById(R.id.iv_line);

        Log.e("封禁>>>", "mContent:" + mContent);

        try {
//            JSONObject jsonObject = new JSONObject(mContent);
//            String msg = jsonObject.getString("msg");

            JSONObject jt = new JSONObject(mContent);
            String infor = jt.getString("msg");
            AccountDisable_DayNum.setText(infor);
            String reason = jt.getString("reason");
            AccountDisable_Content.setText(reason);
            String userIds = jt.getString("userIds");
            if (!TextUtils.isEmpty(userIds)) {
                iv_line.setVisibility(View.GONE);
                AccountDisable_Appeal.setVisibility(View.GONE);
                String ids[] = userIds.split(",");
                for (int i = 0; i < ids.length; i++) {
                    if (Long.parseLong(ids[i]) == HttpClient.getUid()) {
                        iv_line.setVisibility(View.VISIBLE);
                        AccountDisable_Appeal.setVisibility(View.VISIBLE);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //取消
        AccountDisable_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //申诉
        AccountDisable_Appeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hotLine = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_HOT_LINE, "");
                if (TextUtils.isEmpty(hotLine)) {
                    ToastUtil.show("暂未设置，请稍候拨打");
                } else {
                    SystemUtils.dial(hotLine);
                }
                dialog.dismiss();
            }
        });

        AccountDisable_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
