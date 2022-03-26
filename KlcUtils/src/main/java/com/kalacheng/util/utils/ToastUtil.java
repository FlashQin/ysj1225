package com.kalacheng.util.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;


/**
 * 弹出提示信息（展示时间统一）
 * Created by ysj on 2016/11/18.
 */

public class ToastUtil {

    public static void show(int res) {
        show(WordUtil.getString(res));
    }

    public static void show(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        Toast toast = Toast.makeText(ApplicationUtil.getInstance(), s, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
