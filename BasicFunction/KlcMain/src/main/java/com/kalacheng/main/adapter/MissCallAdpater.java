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

import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.utils.OOOLiveCallUtils;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.OOOLiveRoomNoAnswerDto;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.ItemMissCallBinding;
import com.kalacheng.util.utils.ProcessResultUtil;

import java.util.ArrayList;
import java.util.List;

public class MissCallAdpater extends RecyclerView.Adapter<MissCallAdpater.ViewHolder> {

    private List<OOOLiveRoomNoAnswerDto> mList = new ArrayList<>();
    ProcessResultUtil mProcessResultUtil;
    private Context mContext;

    public MissCallAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public MissCallAdpater(Context context, List<OOOLiveRoomNoAnswerDto> data, ProcessResultUtil mProcessResultUtil) {
        this.mContext = context;
        this.mList = data;
        this.mProcessResultUtil = mProcessResultUtil;
    }

    @NonNull
    @Override
    public MissCallAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMissCallBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_miss_call, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MissCallAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.setCallback(new OnBeanCallback<OOOLiveRoomNoAnswerDto>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(OOOLiveRoomNoAnswerDto bean) {
                final ApiUserInfo info = new ApiUserInfo();
                if (mList.get(position).uid != HttpClient.getUid()) {
                    info.userId = mList.get(position).uid;
                } else {
                    info.userId = mList.get(position).audienceId;
                }
                LiveConstants.mIsOOOSend = true;
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMissCallBinding binding;

        public ViewHolder(@NonNull ItemMissCallBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
