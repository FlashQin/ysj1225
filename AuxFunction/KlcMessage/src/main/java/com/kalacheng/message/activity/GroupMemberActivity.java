package com.kalacheng.message.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.message.R;
import com.kalacheng.message.adapter.GroupMemberAdapter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.DialogUtil;
import com.klc.bean.SendGiftPeopleBean;

import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.GroupMemberInfo;

public class GroupMemberActivity extends BaseActivity {

    RecyclerView recyclerView;
    GroupMemberAdapter adapter;
    String toUid;
    ArrayList<SendGiftPeopleBean> list;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarWhite(false);
        setContentView(R.layout.activity_group_member);

        findViewById(R.id.backIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toUid = getIntent().getStringExtra("uid");
        adapter = new GroupMemberAdapter();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        dialog = DialogUtil.loadingDialog(getBaseContext());
        getData();
    }

    private void getData(){
        list = new ArrayList();
//        dialog.show();
        JMessageClient.getGroupInfo(Long.parseLong(toUid), new GetGroupInfoCallback() {
            @Override
            public void gotResult(int i, String s, GroupInfo groupInfo) {
                if (i == 0) {
                    for (GroupMemberInfo info : groupInfo.getGroupMemberInfos()) {
                        SendGiftPeopleBean bean = new SendGiftPeopleBean();
                        bean.uid = Long.parseLong(info.getUserInfo().getUserName());
                        bean.name = info.getUserInfo().getNickname();
                        bean.headimage = info.getUserInfo().getExtra("avatarUrlStr");

                        list.add(bean);
                    }
                    adapter.setList(list);
                }
//                dialog.dismiss();
            }
        });


    }

}
