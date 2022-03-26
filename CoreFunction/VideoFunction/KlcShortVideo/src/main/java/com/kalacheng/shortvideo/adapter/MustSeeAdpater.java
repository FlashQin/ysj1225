package com.kalacheng.shortvideo.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.databinding.ItemMustSeeBinding;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MustSeeAdpater extends RecyclerView.Adapter<MustSeeAdpater.ViewHolder> {

    private List<ApiShortVideoDto> mList = new ArrayList<>();
    private OnBeanCallback<ApiShortVideoDto> mCallBack;

    public MustSeeAdpater() {
    }

    public void setData(List<ApiShortVideoDto> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void loadData(List<ApiShortVideoDto> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public List<ApiShortVideoDto> getData() {
        return mList;
    }

    @NonNull
    @Override
    public MustSeeAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMustSeeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_must_see, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MustSeeAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

        holder.binding.tvLooks.setText(StringUtil.toWan(mList.get(position).looks) + "观看");

        if (mList.get(position).isPrivate == 1 && mList.get(position).isPay == 0){
            ImageLoader.displayBlur(mList.get(position).thumb, holder.binding.ivThumb);
        }else {
            ImageLoader.display(mList.get(position).thumb, holder.binding.ivThumb);
        }

        if (null != mCallBack)
            holder.binding.setCallback(mCallBack);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMustSeeBinding binding;

        public ViewHolder(@NonNull ItemMustSeeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnBeanCallback<ApiShortVideoDto> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
