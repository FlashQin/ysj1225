package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.TaskDto;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.AudienceTaskItmeBinding;

import java.util.ArrayList;
import java.util.List;

public class AudienceTaskAdpater extends RecyclerView.Adapter<AudienceTaskAdpater.ViewHolder> {
    private List<TaskDto> mList = new ArrayList<>();
    private OnBeanCallback<TaskDto> mCallBack;
    private Context mContext;

    public AudienceTaskAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<TaskDto> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AudienceTaskAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AudienceTaskItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.audience_task_itme, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AudienceTaskAdpater.ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));

        if (mList.get(position).typeCode.equals("1001")) {
            if (mList.get(position).status == 1) {
                holder.binding.tvTaskState.setText("+" + String.valueOf(mList.get(position).point) + "积分");
                holder.binding.tvTaskState.setBackgroundResource(0);
                holder.binding.tvTaskState.setTextColor(mContext.getResources().getColor(R.color.yellow2));
            } else {
                holder.binding.tvTaskState.setText("签到");
                holder.binding.tvTaskState.setBackgroundResource(R.drawable.bg_signin);
                holder.binding.tvTaskState.setTextColor(mContext.getResources().getColor(R.color.white));
            }
            holder.binding.tvTaskPoint.setVisibility(View.GONE);
            holder.binding.tvTaskName.setText("");

        } else {
            if (mList.get(position).status == 1) {
                holder.binding.tvTaskState.setText("+" + String.valueOf(mList.get(position).point) + "积分");
                holder.binding.tvTaskState.setTextColor(mContext.getResources().getColor(R.color.yellow2));
                holder.binding.tvTaskPoint.setVisibility(View.GONE);
            } else {
                holder.binding.tvTaskPoint.setVisibility(View.VISIBLE);
                holder.binding.tvTaskPoint.setText("+" + String.valueOf(mList.get(position).point));
                holder.binding.tvTaskState.setText("未完成");
                holder.binding.tvTaskState.setTextColor(mContext.getResources().getColor(R.color.textColor3));

            }
            holder.binding.tvTaskName.setText(mList.get(position).name);
            holder.binding.tvTaskState.setBackgroundResource(0);
        }

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AudienceTaskItmeBinding binding;

        public ViewHolder(@NonNull AudienceTaskItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCallback(mCallBack);
        }
    }


    public void setOnItemClickListener(OnBeanCallback<TaskDto> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
