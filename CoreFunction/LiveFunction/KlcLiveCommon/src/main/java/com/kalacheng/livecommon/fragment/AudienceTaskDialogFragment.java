package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.TaskDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.AudienceTaskAdpater;
import com.kalacheng.util.view.ItemDecoration;

import java.util.List;

public class AudienceTaskDialogFragment extends BaseDialogFragment {
    private AudienceTaskAdpater adpater;

    @Override
    protected int getLayoutId() {
        return R.layout.audience_task;
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
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        RecyclerView audiencetask_list = mRootView.findViewById(R.id.audiencetask_list);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        audiencetask_list.setLayoutManager(manager);
        audiencetask_list.addItemDecoration(new ItemDecoration(mContext, 0, 10, 0));
        adpater = new AudienceTaskAdpater(mContext);
        audiencetask_list.setAdapter(adpater);
        adpater.setOnItemClickListener(new OnBeanCallback<TaskDto>() {
            @Override
            public void onClick(TaskDto bean) {
                if (bean.typeCode.equals("1001")) {
                    getSignIn();
                }
            }
        });

        getAudienceTask();
    }

    public void getAudienceTask() {
        HttpApiAppUser.userTaskList(new HttpApiCallBackArr<TaskDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<TaskDto> retModel) {
                if (code == 1 && retModel != null) {
                    if (retModel != null && !retModel.isEmpty()) {
                        adpater.setData(retModel);
                    }
                }
            }
        });
    }

    //签到
    public void getSignIn() {
        HttpApiAppUser.sign(new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show(msg);
                    dismiss();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
