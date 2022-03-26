package com.kalacheng.message.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.message.R;
import com.kalacheng.message.adapter.MyJoinGroupAdapter;
import com.kalacheng.message.databinding.ActivityMyJoinGroupBinding;
import com.kalacheng.message.viewmodel.MyJoinGroupViewModel;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.commonview.jguangIm.ImMyJoinGroupInfo;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.util.utils.L;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 我加入的群组
 */
//ARouter.getInstance().build(ARouterPath.MyJoinGroupActivity).navigation();
@Route(path = ARouterPath.MyJoinGroupActivity)
public class MyJoinGroupActivity extends BaseMVVMActivity<ActivityMyJoinGroupBinding, MyJoinGroupViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return R.layout.activity_my_join_group;
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private MyJoinGroupAdapter adapter;

    @Override
    public void initData() {
        setTitle("我加入的群组");


        adapter = new MyJoinGroupAdapter();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);


        ImMessageUtil.getInstance().getGroupList();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImMyJoinGroupInfo(ImMyJoinGroupInfo event) {

        L.e("线程返回   群组数据    " + (null != event ? event.getGroupInfo().getGroupName() : ""));
        if (event != null && binding.recyclerView != null && adapter != null) {
            int i = adapter.getList().size();
            adapter.getList().add(event);
            adapter.notifyDataSetChanged();

        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}