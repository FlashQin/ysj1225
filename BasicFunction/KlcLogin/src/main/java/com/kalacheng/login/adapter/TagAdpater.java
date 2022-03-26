package com.kalacheng.login.adapter;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.TabInfoDto;
import com.kalacheng.login.R;
import com.kalacheng.login.databinding.TagItemBinding;

import java.util.ArrayList;
import java.util.List;

public class TagAdpater extends RecyclerView.Adapter<TagAdpater.ViewHolder> {

    private List<TabInfoDto> mList = new ArrayList<>();

    public TagAdpater(List<TabInfoDto> data) {
        this.mList = data;
    }

    @NonNull
    @Override
    public TagAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TagItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.tag_item, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        if (!TextUtils.isEmpty(mList.get(position).name) && mList.get(position).name.length() > 5) {
            holder.binding.typeLable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else {
            holder.binding.typeLable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        if (mList.get(position).status == 0) {
            holder.binding.typeLable.setSelected(false);
            holder.binding.ivChecked.setVisibility(View.GONE);
        } else {
            holder.binding.typeLable.setSelected(true);
            holder.binding.ivChecked.setVisibility(View.VISIBLE);
        }
        holder.binding.setCallback(new OnBeanCallback<TabInfoDto>() {
            @Override
            public void onClick(TabInfoDto bean) {
                if (bean.status == 0)
                    bean.status = 1;
                else
                    bean.status = 0;
                notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TagItemBinding binding;

        public ViewHolder(@NonNull TagItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
