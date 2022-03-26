package com.kalacheng.util.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.R;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.bean.SimpleTextBean;
import com.kalacheng.util.databinding.SimpleText2Binding;
import com.kalacheng.util.utils.DpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class SimpleTextAdapter2 extends RecyclerView.Adapter<SimpleTextAdapter2.ViewHolder> {

    private List<SimpleTextBean> mList = new ArrayList<>();
    int widthDp;
    int hightDp;
    OnBeanCallback itemClickCallback;

    public SimpleTextAdapter2(List<SimpleTextBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setData(List<SimpleTextBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SimpleText2Binding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.simple_text2,
                        parent, false);
        binding.setCallback(new OnBeanCallback<SimpleTextBean>() {
            @Override
            public void onClick(SimpleTextBean bean) {
                if (null != itemClickCallback)
                    itemClickCallback.onClick(bean);
            }
        });
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.binding.text.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) holder.binding.text2.getLayoutParams();
        if (widthDp != 0) {
            layoutParams.width = DpUtil.dp2px(widthDp);
            layoutParams2.width = DpUtil.dp2px(widthDp);
        }
        if (hightDp != 0) {
            layoutParams.height = DpUtil.dp2px(hightDp);
            layoutParams2.height = DpUtil.dp2px(hightDp);

        }
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleText2Binding binding;

        public ViewHolder(SimpleText2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setTextWidthHight(int widthDp, int hightDp) {
        this.widthDp = widthDp;
        this.hightDp = hightDp;
    }

    public void setOnItemClickCallback(OnBeanCallback clickCallback) {
        this.itemClickCallback = clickCallback;
    }
}
