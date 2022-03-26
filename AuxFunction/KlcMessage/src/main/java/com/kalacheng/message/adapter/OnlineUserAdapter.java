package com.kalacheng.message.adapter;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.commonview.utils.OOOLiveCallUtils;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiUsersLine;
import com.kalacheng.message.R;
import com.kalacheng.message.dialog.MediaDialog;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class OnlineUserAdapter extends RecyclerView.Adapter<OnlineUserAdapter.Holder> {

    private List<ApiUsersLine> list;
    ProcessResultUtil mProcessResultUtil;
    FragmentActivity context;

    public OnlineUserAdapter(FragmentActivity context) {
        this.context = context;
        mProcessResultUtil = new ProcessResultUtil(context);
        this.list = new ArrayList<>();
    }

    public void setList(List<ApiUsersLine> list) {
        this.list.clear();
        if (null != list && list.size() > 0) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void onLoadMore(List<ApiUsersLine> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onlice_user, parent, false);
        return new Holder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final ApiUsersLine usersLine = list.get(position);

        if (payload == null) {
            ImageLoader.display(usersLine.avatar, holder.avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder.avatarIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, usersLine.uid).navigation();

                }
            });
            holder.nameTv.setText(usersLine.userName);
            //holder.ageTv.setText(String.valueOf(usersLine.age));
            SexUtlis.getInstance().setSex(context, holder.layoutSex, usersLine.sex, usersLine.age);

            holder.distanceTv.setText("距离你" + usersLine.distance + "km");
//            if (usersLine.sex == 1) {
//                holder.ageTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_boy_white, 0, 0, 0);
//                holder.ageTv.setBackgroundResource(R.drawable.bg_age_boy);
//            } else if (usersLine.sex == 2) {
//                holder.ageTv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_girl_white, 0, 0, 0);
//                holder.ageTv.setBackgroundResource(R.drawable.bg_age);
//            } else {
//                holder.ageTv.setVisibility(View.GONE);
//            }
            if (TextUtils.isEmpty(usersLine.portrait)) {
                holder.recyclerView.setVisibility(View.GONE);
            } else {
                String[] strs = usersLine.portrait.split(",");
                if (usersLine.role != 1 || TextUtils.isEmpty(usersLine.portrait) || strs.length == 0) {
                    holder.recyclerView.setVisibility(View.GONE);
                } else {
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    holder.adapter.setStrs(strs);
                }
            }
            ImageLoader.display(usersLine.role != 1 ? usersLine.userGradeImg : usersLine.anchorGradeImg, holder.rankIv);
            ImageLoader.display(usersLine.nobleGradeImg, holder.nobleGradeIv);

            holder.messageIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                            .withString(ARouterValueNameConfig.TO_UID, String.valueOf(usersLine.uid))
                            .withString(ARouterValueNameConfig.Name, usersLine.userName)
                            .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                            .navigation();
                }
            });

            holder.iv_media.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaDialog mediaDialog = new MediaDialog();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("info", list.get(position));
                    mediaDialog.setArguments(bundle);
                    mediaDialog.setOnMediaSelectListener(new MediaDialog.OnMediaSelectListener() {
                        @Override
                        public void onVideo() {
                            final ApiUserInfo info = new ApiUserInfo();
                            info.userId = usersLine.uid;
                            LiveConstants.mIsOOOSend = true;
                            info.avatar = usersLine.avatar;
                            info.sex = usersLine.sex;
                            info.username = usersLine.userName;
                            info.role = usersLine.role;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                mProcessResultUtil.requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO
                                }, new Runnable() {
                                    @Override
                                    public void run() {
                                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(context, 1, info, 1);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onVoice() {
                            final ApiUserInfo info = new ApiUserInfo();
                            info.userId = usersLine.uid;
                            LiveConstants.mIsOOOSend = true;
                            info.avatar = usersLine.avatar;
                            info.sex = usersLine.sex;
                            info.username = usersLine.userName;
                            info.role = usersLine.role;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                mProcessResultUtil.requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO
                                }, new Runnable() {
                                    @Override
                                    public void run() {
                                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(context, 0, info, 1);

                                    }
                                });
                            }
                        }
                    });
                    mediaDialog.show(context.getSupportFragmentManager(), "MediaDialog");
                }
            });

            holder.voiceOrLiveIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppHomeHallDTO bean = new AppHomeHallDTO();
                    bean.liveType = usersLine.liveType;
                    bean.roomId = usersLine.roomId;
                    LookRoomUtlis.getInstance().getData(bean, context);
                }
            });

        }
        if (usersLine.role == 1) {
            if (usersLine.status == 1) {
                holder.iv_media.setVisibility(View.GONE);
                holder.voiceOrLiveIv.setImageResource(usersLine.type == 1 ? R.mipmap.live : R.mipmap.yuyin);
                if (ConfigUtil.getBoolValue(R.bool.containLive) || ConfigUtil.getBoolValue(R.bool.containVoice)) {
                    holder.voiceOrLiveIv.setVisibility(View.VISIBLE);
                } else {
                    holder.voiceOrLiveIv.setVisibility(View.GONE);
                }
            } else {
                if (ConfigUtil.getBoolValue(R.bool.containOne2One)) {
                    holder.iv_media.setVisibility(View.VISIBLE);
                } else {
                    holder.iv_media.setVisibility(View.GONE);
                }
                holder.voiceOrLiveIv.setVisibility(View.GONE);
            }
        } else {
            holder.voiceOrLiveIv.setVisibility(View.GONE);
        }

        if (usersLine.userSetOnlineStatus.equals("1")) {
            holder.statusView.setBackgroundResource(R.drawable.bg_status_green);
        } else {
            holder.statusView.setBackgroundResource(R.drawable.bg_message_count);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class Holder extends RecyclerView.ViewHolder {
        RoundedImageView avatarIv;
        TextView nameTv, distanceTv;
        TextView ageTv;
        ImageView rankIv;
        ImageView nobleGradeIv;
        ImageView messageIv;
        ImageView voiceOrLiveIv;
        ImageView iv_media;
        RecyclerView recyclerView;
        Pic3ShowAdapter adapter;
        View statusView;
        LinearLayout layoutSex;


        public Holder(@NonNull View itemView, Context context) {
            super(itemView);
            statusView = itemView.findViewById(R.id.statusView);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            distanceTv = itemView.findViewById(R.id.distanceTv);
            ageTv = itemView.findViewById(R.id.ageTv);
            layoutSex = itemView.findViewById(R.id.layoutSex);
            rankIv = itemView.findViewById(R.id.rankIv);
            messageIv = itemView.findViewById(R.id.messageIv);
            nobleGradeIv = itemView.findViewById(R.id.nobleGradeIv);
            voiceOrLiveIv = itemView.findViewById(R.id.voiceOrLiveIv);
            iv_media = itemView.findViewById(R.id.iv_media);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            adapter = new Pic3ShowAdapter();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new ItemDecoration(context, 0x00000000, 10, 0));
        }
    }


}
