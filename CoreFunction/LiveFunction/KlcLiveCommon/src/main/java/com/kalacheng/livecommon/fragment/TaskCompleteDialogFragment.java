package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;

//完成任务的dialog
public class TaskCompleteDialogFragment extends BaseDialogFragment {
    private ApiElasticFrame apiElasticFrame;
    @Override
    protected int getLayoutId() {
        return R.layout.task_complete_dialog;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.y = DpUtil.dp2px(70);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiElasticFrame = getArguments().getParcelable("ApiElasticFrame");

        TextView Task_Content = mRootView.findViewById(R.id.Task_Content);

        TextView Task_Coin = mRootView.findViewById(R.id.Task_Coin);

        Task_Content.setText("恭喜您完成"+apiElasticFrame.taskName);
        String str="<font color='#666666'>获得经验</font>"+" +"+apiElasticFrame.taskPoint;
        Task_Coin.setText(Html.fromHtml(str));
    }
}
