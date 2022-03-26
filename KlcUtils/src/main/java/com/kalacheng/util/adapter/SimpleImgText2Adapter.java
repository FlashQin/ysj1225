package com.kalacheng.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalacheng.util.R;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.util.databinding.SimpleImageurlText2Binding;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hgy on 2019/10/10.
 */

public class SimpleImgText2Adapter extends RecyclerView.Adapter<SimpleImgText2Adapter.ViewHolder> {

    private List<SimpleImageUrlTextBean> mList = new ArrayList<>();

    public SimpleImgText2Adapter(List<SimpleImageUrlTextBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SimpleImageurlText2Binding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.simple_imageurl_text2,
                        parent, false);
        binding.setCallback(new OnItemClickCallback<SimpleImageUrlTextBean>() {
            @Override
            public void onClick(View view, SimpleImageUrlTextBean item) {

            }
        });
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleImageurlText2Binding binding;

        public ViewHolder(SimpleImageurlText2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
