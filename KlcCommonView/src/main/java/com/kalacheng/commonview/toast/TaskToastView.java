package com.kalacheng.commonview.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.ApiElasticFrame;

public class TaskToastView extends Toast {
    private static Toast mToast;

    public TaskToastView(Context context) {
        super(context);
    }

    @SuppressLint("WrongConstant")
    public static void showToast(Context context, ApiElasticFrame apiElasticFrame) {

        //获取系统的LayoutInflater
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mRootView = inflater.inflate(R.layout.task_complete_dialog, null);

        TextView Task_Content = mRootView.findViewById(R.id.Task_Content);

        TextView Task_Coin = mRootView.findViewById(R.id.Task_Coin);

        Task_Content.setText("恭喜您完成" + apiElasticFrame.taskName);
        String str = "<font color='#666666'>获得经验</font>" + " +" + apiElasticFrame.taskPoint;
        Task_Coin.setText(Html.fromHtml(str));

        //实例化toast
        mToast = new Toast(context);
        mToast.setView(mRootView);
        mToast.setDuration(8);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
        mToast.show();
    }
}
