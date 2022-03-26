package com.kalacheng.util.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.R;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.databinding.SimpleImage2Binding;
import com.kalacheng.util.utils.DpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 * 方形img
 */

public class SimpleImgAdapter2 extends RecyclerView.Adapter<SimpleImgAdapter2.ViewHolder> {

    private List<String> mList = new ArrayList<>();
    int widthDp;
    int hightDp;

    public SimpleImgAdapter2(List<String> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void upDataList(List<String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SimpleImage2Binding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.simple_image2,
                        parent, false);
        binding.setCallback(new OnBeanCallback<String>() {
            @Override
            public void onClick(String bean) {

            }
        });
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.binding.icon.getLayoutParams();
        if (widthDp != 0)
            layoutParams.width = DpUtil.dp2px(widthDp);
        if (hightDp != 0)
            layoutParams.height = DpUtil.dp2px(hightDp);
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleImage2Binding binding;

        public ViewHolder(SimpleImage2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void setImgWidthHight(int widthDp, int hightDp) {
        this.widthDp = widthDp;
        this.hightDp = hightDp;
    }
}
