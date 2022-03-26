package com.kalacheng.videorecord.adpater;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppVideoTopic;
import com.kalacheng.videorecord.R;
import com.kalacheng.videorecord.databinding.ItemSelectTopicBinding;
import com.kalacheng.util.listener.OnBeanCallback;

import java.util.ArrayList;
import java.util.List;

public class TopicSelectAdapter extends RecyclerView.Adapter<TopicSelectAdapter.ViewHolder> {

    private List<AppVideoTopic> mList = new ArrayList<>();
    public long id = -1;

    public TopicSelectAdapter(List<AppVideoTopic> data) {
        mList.clear();
        mList.addAll(data);
    }

    @NonNull
    @Override
    public TopicSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSelectTopicBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_select_topic, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicSelectAdapter.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        if (!TextUtils.isEmpty(mList.get(position).name) && mList.get(position).name.length() > 5) {
            holder.binding.typeLable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else {
            holder.binding.typeLable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        if (id == mList.get(position).id) {
            holder.binding.typeLable.setSelected(true);
        } else {
            holder.binding.typeLable.setSelected(false);
        }
        holder.binding.setCallback(new OnBeanCallback<AppVideoTopic>() {
            @Override
            public void onClick(AppVideoTopic bean) {
                if (id == bean.id) {
                    id = -1;
                } else {
                    id = bean.id;
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSelectTopicBinding binding;

        public ViewHolder(@NonNull ItemSelectTopicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
