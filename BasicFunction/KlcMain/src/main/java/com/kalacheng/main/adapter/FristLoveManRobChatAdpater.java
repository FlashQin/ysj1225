package com.kalacheng.main.adapter;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiUsersLine;
import com.kalacheng.main.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class FristLoveManRobChatAdpater extends RecyclerView.Adapter<FristLoveManRobChatAdpater.Holder> {
    List<ApiUsersLine> list = new ArrayList<>();
    ProcessResultUtil mProcessResultUtil;

    private Context mContext;

    public FristLoveManRobChatAdpater(Context activity) {
        this.mContext = activity;
        mProcessResultUtil = new ProcessResultUtil((FragmentActivity) activity);
    }

    /**
     * 增加数据
     */
    public void addData(List<ApiUsersLine> list) {
        int count = this.list.size();
        this.list.clear();
        if (count != 0) {
            notifyItemRangeRemoved(0, count);
//            notifyDataSetChanged();
        }
        if (list != null) {
            this.list.addAll(list);
            notifyItemRangeChanged(0, this.list.size());
        }
    }

    public void removed() {
        int count = list.size();
        this.list.clear();
        if (count != 0) {
            notifyItemRangeRemoved(0, count);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fristlove_manrob_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FristLoveManRobChatAdpater.Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull FristLoveManRobChatAdpater.Holder holder, int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final ApiUsersLine pushChat = list.get(position);
        if (payload == null) {
            ImageLoader.display(pushChat.thumb, holder.avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder.tv_name.setText(list.get(position).userName);
            holder.robCahtTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mProcessResultUtil.requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO
                        }, new Runnable() {
                            @Override
                            public void run() {
                                ApiUserInfo info = new ApiUserInfo();
                                info.userId = pushChat.uid;
                                LiveConstants.mIsOOOSend = true;
                                info.avatar = pushChat.avatar;
                                info.sex = pushChat.sex;
                                info.username = pushChat.userName;
                                info.role = pushChat.role;
                                LookRoomUtlis.getInstance().showDialog(1, info, mProcessResultUtil, mContext, 1);

                            }
                        });
                    }
                }
            });

            holder.avatarIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, pushChat.uid).navigation();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        RoundedImageView avatarIv;
        ImageView robCahtTv;
        TextView tv_name;

        public Holder(@NonNull View itemView) {
            super(itemView);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            robCahtTv = itemView.findViewById(R.id.robCahtTv);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

}
