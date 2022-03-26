package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.buscommon.model.TaskDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.adapter.MeAnchorAdpater;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;

import java.util.List;

public class AnchorTaskDialogFragment extends BaseDialogFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.anchor_task;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = (DpUtil.getScreenHeight() / 2);
        ;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    RecyclerView anchorTask_list;

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        anchorTask_list = mRootView.findViewById(R.id.anchorTask_list);
        anchorTask_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTask();

        ImageView anchorTask_close = mRootView.findViewById(R.id.anchorTask_close);
        anchorTask_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void getTask() {
        HttpApiAppUser.anchorTaskList(new HttpApiCallBackArr<TaskDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<TaskDto> retModel) {
                if (code == 1 && retModel != null && !retModel.isEmpty()) {
                    MeAnchorAdpater adpater = new MeAnchorAdpater(retModel);
                    anchorTask_list.setAdapter(adpater);
                }

            }
        });
    }
}
