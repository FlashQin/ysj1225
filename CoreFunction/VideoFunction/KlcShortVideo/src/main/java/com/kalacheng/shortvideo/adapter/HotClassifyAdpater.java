package com.kalacheng.shortvideo.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppHotSort;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.databinding.ItemVideoHotBinding;
import com.kalacheng.util.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class HotClassifyAdpater extends RecyclerView.Adapter<HotClassifyAdpater.ViewHolder> {

    private List<AppHotSort> mList = new ArrayList<>();
    private OnBeanCallback<AppHotSort> mCallBack;

    public HotClassifyAdpater() {
    }

    public void setData(List<AppHotSort> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void loadData(List<AppHotSort> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HotClassifyAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideoHotBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_video_hot, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotClassifyAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

        holder.binding.tvType.setText(StringUtil.toWan(mList.get(position).number) + " 热门作品");
        if (null != mList.get(position).name && !TextUtils.isEmpty(mList.get(position).name))
            holder.binding.tvName.setText("#" + mList.get(position).name + "#");
        if (null != mCallBack)
            holder.binding.setCallback(mCallBack);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemVideoHotBinding binding;

        public ViewHolder(@NonNull ItemVideoHotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnBeanCallback<AppHotSort> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
