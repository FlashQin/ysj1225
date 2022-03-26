package com.kalacheng.tiui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.tiui.R;

/**
 * Created by Anko on 2019-09-05.
 * Copyright (c) 2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiMakeupItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbIV;
    public TextView nameTV;

    public TiMakeupItemViewHolder(View itemView) {
        super(itemView);
        thumbIV = itemView.findViewById(R.id.thumbIV);
        nameTV = itemView.findViewById(R.id.nameTV);
    }
}