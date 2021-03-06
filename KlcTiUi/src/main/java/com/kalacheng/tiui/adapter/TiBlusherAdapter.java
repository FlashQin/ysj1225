package com.kalacheng.tiui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiMakeup;
import cn.tillusory.sdk.bean.TiMakeupEnum;
import com.kalacheng.tiui.R;

/**
 * Created by Anko on 2019-09-08.
 * Copyright (c) 2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiBlusherAdapter extends RecyclerView.Adapter<TiMakeupItemViewHolder> {

    private int selectedPosition = 0;

    private List<TiMakeup> list;
    private TiSDKManager tiSDKManager;

    public TiBlusherAdapter(List<TiMakeup> list, TiSDKManager tiSDKManager) {
        this.list = list;
        this.tiSDKManager = tiSDKManager;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TiMakeupItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ti_makeup, parent, false);
        return new TiMakeupItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TiMakeupItemViewHolder holder, int position) {
        if (position == 0) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            p.setMargins((int) (holder.itemView.getContext().getResources().getDisplayMetrics().density * 15 + 0.5f),
                    0,
                    0,
                    0);
            holder.itemView.requestLayout();
        } else {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            p.setMargins(0, 0, 0, 0);
            holder.itemView.requestLayout();
        }

        TiMakeup tiMakeup = list.get(holder.getAdapterPosition());

        //显示封面
        if (tiMakeup == TiMakeup.NO_MAKEUP) {
            holder.thumbIV.setImageResource(R.drawable.ic_ti_none);
            holder.nameTV.setText(R.string.makeup_no);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(list.get(position).getImagePath(holder.itemView.getContext(), TiMakeupEnum.BLUSHER_MAKEUP))
                    .into(holder.thumbIV);
            holder.nameTV.setText(list.get(position).getName());
        }

        if (selectedPosition == position) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                tiSDKManager.enableMakeup(true);
                tiSDKManager.setBlusher(list.get(holder.getAdapterPosition()).getName());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
