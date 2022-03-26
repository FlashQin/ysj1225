package com.kalacheng.tiui.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.tiui.R;

/**
 * Created by Anko on 2018/11/22.
 * Copyright (c) 2018-2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiDesViewHolder extends RecyclerView.ViewHolder {

    public TextView tiTextTV;
    public ImageView tiImageIV;
    public FrameLayout layoutFilterSelector;

    public TiDesViewHolder(View itemView) {
        super(itemView);
        tiTextTV = itemView.findViewById(R.id.tiTextTV);
        tiImageIV = itemView.findViewById(R.id.tiImageIV);
        layoutFilterSelector = itemView.findViewById(R.id.layoutFilterSelector);
    }

}