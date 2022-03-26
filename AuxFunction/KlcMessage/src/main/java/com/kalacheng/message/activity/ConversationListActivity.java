package com.kalacheng.message.activity;

import android.os.Bundle;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.message.R;
import com.kalacheng.message.fragment.ConversationListFragment;
import com.kalacheng.base.activty.BaseActivity;

@Route(path = ARouterPath.ConversationListActivity)
public class ConversationListActivity extends BaseActivity {
    ConversationListFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        fragment = (ConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.conversationListFragment);

    }

    @Override
    protected void onResume() {
        super.onResume();
        fragment.onResumeFragment();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }
}
