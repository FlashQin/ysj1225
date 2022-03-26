package com.kalacheng.message.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import com.kalacheng.commonview.pay.Constants;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.commonview.view.TextRender;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.commonview.jguangIm.ImUserMsgEvent;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.util.utils.L;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.GroupMemberInfo;
import cn.jpush.im.android.api.model.UserInfo;


public class ConversationAdapter extends RecyclerView.Adapter {
    static Context mContext;

    private List<ImUserMsgEvent> list;

    public ConversationAdapter() {
        list = new ArrayList<ImUserMsgEvent>();
    }

    public void setList() {
        this.list.clear();
        this.list.addAll(ImMessageUtil.getInstance().getConversationUserList());
        notifyDataSetChanged();
    }

    public int getPosition(ImUserMsgEvent userMsgEvent) {
        for (int i = 0, size = list.size(); i < size; i++) {
            L.e("极光", userMsgEvent.getUid() + "  " + list.get(i).getUid());
            if (userMsgEvent.getUid().equals(list.get(i).getUid())) {
                return i;
            }
        }
        return -1;
    }

    public void insertItem(ImUserMsgEvent bean) {
        list.add(0, bean);
        notifyDataSetChanged();
//        notifyItemInserted(0);
    }

    public void updateItem(ImUserMsgEvent event, int position) {
        if (position >= 0 && position < list.size()) {
            list.remove(position);
            if (position == 0) {
                list.add(0, event);
                notifyItemChanged(0, "update");
            } else {
                notifyItemRemoved(position);
                insertItem(event);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_and_notify, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position, @NonNull List payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final ImUserMsgEvent userMsgEvent = list.get(position);
        final ConversationViewHolder h = (ConversationViewHolder) holder;
        int count = userMsgEvent.getUnReadCount();
        if (payload == null) {
            final boolean isSingle = userMsgEvent.getUserInfo() != null;
            if (isSingle) {
                h.groupAvatarRl.setVisibility(View.INVISIBLE);
                h.singAvatarRl.setVisibility(View.VISIBLE);
                String avatarUrlStr = userMsgEvent.getUserInfo().getExtra("avatarUrlStr");
                ImageLoader.display(avatarUrlStr, h.singAvatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                String name = userMsgEvent.getNickName();

                if (TextUtils.isEmpty(name)) {
                    name = userMsgEvent.getUserInfo().getNickname();
                }
                if (TextUtils.isEmpty(name)) {
                    name = userMsgEvent.getUserInfo().getUserName();
                }
                h.nameTv.setText(name);

                JMessageClient.getUserInfo((userMsgEvent.getUserInfo()).getUserName(), new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        ImageLoader.display(userInfo.getExtra("avatarUrlStr"), h.singAvatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        h.nameTv.setText(userInfo.getNickname());
                    }
                });
            } else {
                h.groupAvatarRl.setVisibility(View.VISIBLE);
                h.singAvatarRl.setVisibility(View.INVISIBLE);
                GroupInfo groupInfo = userMsgEvent.getGroupInfo();
                List<GroupMemberInfo> infos1 = new ArrayList<>();
                List<GroupMemberInfo> infos = groupInfo.getGroupMemberInfos();
                if (infos != null && infos.size() > 0) {
                    do {
                        infos1.addAll(infos);
                    } while (infos1.size() < 4);
                }
                if (infos1.size() >= 4) {
                    ImageLoader.display(infos1.get(0).getUserInfo().getExtra("avatarUrlStr"), h.avatarIv1, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    ImageLoader.display(infos1.get(1).getUserInfo().getExtra("avatarUrlStr"), h.avatarIv2, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    ImageLoader.display(infos1.get(2).getUserInfo().getExtra("avatarUrlStr"), h.avatarIv3, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    ImageLoader.display(infos1.get(3).getUserInfo().getExtra("avatarUrlStr"), h.avatarIv4, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(R.mipmap.ic_launcher, h.avatarIv1);
                    ImageLoader.display(R.mipmap.ic_launcher, h.avatarIv2);
                    ImageLoader.display(R.mipmap.ic_launcher, h.avatarIv3);
                    ImageLoader.display(R.mipmap.ic_launcher, h.avatarIv4);
                }
                String name = userMsgEvent.getGroupInfo().getGroupName();
                if (TextUtils.isEmpty(name)) {
                    name = userMsgEvent.getGroupInfo().getGroupID() + "";
                }
                h.nameTv.setText(name);
            }
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ChatRoomActivity.startActivity(userMsgEvent.getUid(), h.nameTv.getText().toString(), isSingle);
                    if (h.unReadCountTv.getVisibility() == View.VISIBLE) {
                        h.itemView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (ImMessageUtil.getInstance().markAllMessagesAsRead(userMsgEvent.getUid())) {
                                    list.get(position).setUnReadCount(0);
                                    userMsgEvent.setUnReadCount(0);
                                    notifyDataSetChanged();
                                }
                            }
                        }, 300);
                    }
                }
            });

            h.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDialog(v, userMsgEvent.getUid(), position, userMsgEvent.getUserInfo() != null);
                    return false;
                }
            });

        }
        h.contentTv.setText(TextRender.renderChatMessage(userMsgEvent.getLastMessage()));
        h.timeTv.setText(userMsgEvent.getLastTime());
        h.unReadCountTv.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
        h.unReadCountTv.setText(count + "");

    }

    @SuppressLint("ResourceType")
    private void showDialog(final View view, final String uid, final int position, final boolean isSingle) {
        view.setAlpha(0.3f);
        DialogUtil.showStringArrayDialog(mContext, new Integer[]{R.string.msg_read,
                R.string.msg_delete}, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                view.setAlpha(1f);
            }
        }, new DialogUtil.StringArrayDialogCallback() {
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.msg_read) {
                    if (ImMessageUtil.getInstance().markAllMessagesAsRead(uid)) {
                        list.get(position).setUnReadCount(0);
                        notifyItemChanged(position, Constants.PAYLOAD);
                    }
                } else if (tag == R.string.msg_delete) {
//                    notifyItemRemoved(position);
                    if (isSingle) {
                        ImMessageUtil.getInstance().removeSingleConversation(uid);
                    } else {
                        ImMessageUtil.getInstance().removeGroupConversation(Long.parseLong(uid));
                    }
                    setList();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ConversationViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout headRl;
        RelativeLayout singAvatarRl;
        RoundedImageView singAvatarIv;
        RelativeLayout groupAvatarRl;
        RoundedImageView avatarIv1;
        RoundedImageView avatarIv2;
        RoundedImageView avatarIv3;
        RoundedImageView avatarIv4;
        TextView nameTv;
        TextView contentTv;
        TextView timeTv;
        TextView unReadCountTv;

        ConversationViewHolder(@NonNull View itemView) {
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
            contentTv = itemView.findViewById(R.id.contentTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            unReadCountTv = itemView.findViewById(R.id.unReadCountTv);
        }
    }

}
