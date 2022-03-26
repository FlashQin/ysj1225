package com.kalacheng.main.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.main.R;
import com.kalacheng.util.utils.CheckDoubleClick;

public class VoiceChatContainFragment extends BaseFragment {
    Context mContext;

    public VoiceChatContainFragment() {

    }

    public VoiceChatContainFragment(Context mContext, ViewGroup mParentView) {
        this.mContext = mContext;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_voice_chat_contain;
    }

    @Override
    protected void initView() {
        mParentView.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                ARouter.getInstance().build(ARouterPath.Search).navigation();
            }
        });
    }

    @Override
    protected void initData() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        VoiceChatFragment voiceChatFragment = new VoiceChatFragment();
        transaction.add(R.id.layoutVcContain, voiceChatFragment);
        transaction.commit();
    }
}
