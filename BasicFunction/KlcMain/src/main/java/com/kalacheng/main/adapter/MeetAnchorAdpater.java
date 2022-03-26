package com.kalacheng.main.adapter;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.utils.OOOLiveCallUtils;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.ItemAnchorMeetBinding;
import com.kalacheng.util.utils.ProcessResultUtil;

import java.util.ArrayList;
import java.util.List;

public class MeetAnchorAdpater extends RecyclerView.Adapter<MeetAnchorAdpater.ViewHolder> {

    private List<ApiUserInfo> mList = new ArrayList<>();
    boolean isStateVisibility;
    ProcessResultUtil mProcessResultUtil;
    private Context mContext;

    public MeetAnchorAdpater(List<ApiUserInfo> data, boolean isStateVisibility, ProcessResultUtil mProcessResultUtil) {
        this.mList = data;
        this.isStateVisibility = isStateVisibility;
        this.mProcessResultUtil = mProcessResultUtil;
    }

    @NonNull
    @Override
    public MeetAnchorAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ItemAnchorMeetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_anchor_meet, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetAnchorAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.setStateVisibility(isStateVisibility);
        holder.binding.setCallback(new OnBeanCallback<ApiUserInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(ApiUserInfo bean) {
                if (isStateVisibility) {
                    final ApiUserInfo info = new ApiUserInfo();
                    info.userId = mList.get(position).userId;
                    LiveConstants.mIsOOOSend = true;
                    info.avatar = mList.get(position).liveThumb;
                    info.username = mList.get(position).username;
                    mProcessResultUtil.requestPermissions(new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO
                    }, new Runnable() {
                        @Override
                        public void run() {
                            OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, 1, info, 1);

                        }
                    });
                } else {
                    BaseApplication.containsActivity("ChatRoomActivity");
                    ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                            .withString(ARouterValueNameConfig.TO_UID, String.valueOf(bean.userId))
                            .withString(ARouterValueNameConfig.Name, bean.username)
                            .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                            .navigation();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAnchorMeetBinding binding;

        public ViewHolder(@NonNull ItemAnchorMeetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
