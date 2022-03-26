package com.kalacheng.message.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.message.R;
import com.kalacheng.message.adapter.ConversationAdapter;
import com.kalacheng.message.event.ReadMsgEvent;
import com.kalacheng.commonview.jguangIm.ImGetConversationEvent;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.commonview.jguangIm.ImUserMsgEvent;
import com.kalacheng.base.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ConversationListFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ConversationAdapter adapter;

    public ConversationListFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_conversation_list;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        recyclerView = mParentView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ConversationAdapter();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        getListData();
    }

    /**
     * 获取 私信
     */
    private void getListData() {
        EventBus.getDefault().post(new ImGetConversationEvent());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImGetConversationEvent(ImGetConversationEvent event){
        if (adapter != null) {
            adapter.setList();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImUserMsgEvent(ImUserMsgEvent event){
        if (event != null && recyclerView != null && adapter != null) {
            int i = adapter.getPosition(event);
            if (i < 0 ){
                adapter.insertItem(event);
            }else {
                adapter.updateItem(event,i);
            }
        }
    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        getListData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReadMsgEvent(ReadMsgEvent event){
        ImMessageUtil.getInstance().markAllConversationAsRead();
        adapter.setList();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
