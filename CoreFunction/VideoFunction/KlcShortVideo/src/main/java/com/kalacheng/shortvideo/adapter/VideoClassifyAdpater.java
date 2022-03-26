package com.kalacheng.shortvideo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.base.event.VideoZanEvent;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.databinding.ItemVideoClassifyBinding;
import com.kalacheng.util.utils.TextViewUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class VideoClassifyAdpater extends RecyclerView.Adapter<VideoClassifyAdpater.ViewHolder> {

    private List<ApiShortVideoDto> mList = new ArrayList<>();
    private OnBeanCallback<ApiShortVideoDto> mCallBack;

    public VideoClassifyAdpater() {
    }

    public void setData(List<ApiShortVideoDto> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<ApiShortVideoDto> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void setZanComment(VideoZanEvent event) {
        if (null != event) {
            if (event.getIsZanComment() == 1) {
                this.mList.get(event.getPosition()).likes = event.getZanNumber();
                this.mList.get(event.getPosition()).isLike = event.getIsLike();
            } else if (event.getIsZanComment() == 2) {
                this.mList.get(event.getPosition()).comments = event.getCommentNumber();
            }
        }
        notifyDataSetChanged();
    }

    public List<ApiShortVideoDto> getData() {
        return mList;
    }

    @NonNull
    @Override
    public VideoClassifyAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideoClassifyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_video_classify, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoClassifyAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

        if (mList.get(position).isPay == 0 && mList.get(position).isPrivate == 1) {
            holder.binding.ivImagesShadow.setVisibility(View.VISIBLE);
            ImageLoader.displayBlur(mList.get(position).thumb, holder.binding.ivImagesShadow);
        } else {
            holder.binding.ivImagesShadow.setVisibility(View.GONE);
        }

        if (mList.get(position).isLike == 1) {
            TextViewUtil.setDrawableLeft(holder.binding.tvLike, com.kalacheng.dynamiccircle.R.mipmap.icon_likes);
        } else {
            TextViewUtil.setDrawableLeft(holder.binding.tvLike, com.kalacheng.dynamiccircle.R.mipmap.icon_likes_none);
        }

        if (null != mCallBack)
            holder.binding.setCallback(mCallBack);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemVideoClassifyBinding binding;

        public ViewHolder(@NonNull ItemVideoClassifyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnBeanCallback<ApiShortVideoDto> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
