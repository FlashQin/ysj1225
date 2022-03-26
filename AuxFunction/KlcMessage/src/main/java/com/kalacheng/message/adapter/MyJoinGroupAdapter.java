package com.kalacheng.message.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.message.R;
import com.kalacheng.message.activity.ChatRoomActivity;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.commonview.jguangIm.ImMyJoinGroupInfo;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.GroupMemberInfo;

/**
 * 我加入的群组
 */
public class MyJoinGroupAdapter extends RecyclerView.Adapter {
    static Context mContext;

    private List<ImMyJoinGroupInfo> list = new ArrayList<ImMyJoinGroupInfo>();

    public MyJoinGroupAdapter() {

    }


    public List<ImMyJoinGroupInfo> getList() {
        return this.list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_join_group, parent, false);
        return new MyJoinGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position, @NonNull List payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final ImMyJoinGroupInfo userMsgEvent = list.get(position);
        final MyJoinGroupViewHolder h = (MyJoinGroupViewHolder) holder;

        if (payload == null) {

            h.groupAvatarRl.setVisibility(View.VISIBLE);
            h.singAvatarRl.setVisibility(View.INVISIBLE);
            GroupInfo groupInfo = userMsgEvent.getGroupInfo();
            List<GroupMemberInfo> infos1 = new ArrayList<>();
            List<GroupMemberInfo> infos = groupInfo.getGroupMemberInfos();
            do {
                infos1.addAll(infos);
            } while (infos1.size() < 4);

            ImageLoader.display(infos1.get(0).getUserInfo().getExtra("avatarUrlStr"), h.avatarIv1, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            ImageLoader.display(infos1.get(1).getUserInfo().getExtra("avatarUrlStr"), h.avatarIv2, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            ImageLoader.display(infos1.get(2).getUserInfo().getExtra("avatarUrlStr"), h.avatarIv3, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            ImageLoader.display(infos1.get(3).getUserInfo().getExtra("avatarUrlStr"), h.avatarIv4, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            String name = userMsgEvent.getGroupInfo().getGroupName();
            if (TextUtils.isEmpty(name)) {
                name = userMsgEvent.getGroupInfo().getGroupID() + "";
            }
            h.nameTv.setText(name);
//            }
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ChatRoomActivity.startActivity(String.valueOf(userMsgEvent.getGroupInfo().getGroupID()), h.nameTv.getText().toString(), false);

                }
            });

            //不显示 未读消息数
            h.unReadCountTv.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyJoinGroupViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout headRl;
        RelativeLayout singAvatarRl;
        RoundedImageView singAvatarIv;
        RelativeLayout groupAvatarRl;
        RoundedImageView avatarIv1;
        RoundedImageView avatarIv2;
        RoundedImageView avatarIv3;
        RoundedImageView avatarIv4;
        TextView nameTv;
        TextView timeTv;
        TextView unReadCountTv;

        MyJoinGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            headRl = itemView.findViewById(R.id.headRl);
            singAvatarRl = itemView.findViewById(R.id.singAvatarRl);
            singAvatarIv = itemView.findViewById(R.id.singAvatarIv);
            groupAvatarRl = itemView.findViewById(R.id.groupAvatarRl);
            avatarIv1 = itemView.findViewById(R.id.avatarIv1);
            avatarIv2 = itemView.findViewById(R.id.avatarIv2);
            avatarIv3 = itemView.findViewById(R.id.avatarIv3);
            avatarIv4 = itemView.findViewById(R.id.avatarIv4);
            nameTv = itemView.findViewById(R.id.nameTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            unReadCountTv = itemView.findViewById(R.id.unReadCountTv);
        }
    }

}
