package com.kalacheng.tiui.adapter;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.tiui.R;

/**
 * Created by Anko on 2018/5/25.
 * Copyright (c) 2018-2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiStickerViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbIV, downloadIV, loadingIV;

    public TiStickerViewHolder(View itemView) {
        super(itemView);
        thumbIV = itemView.findViewById(R.id.thumbIV);
        downloadIV = itemView.findViewById(R.id.downloadIV);
        loadingIV = itemView.findViewById(R.id.loadingIV);
    }

    public void startLoadingAnimation() {
        Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.loading_animation);
        loadingIV.startAnimation(animation);
    }

    public void stopLoadingAnimation() {
        loadingIV.clearAnimation();
    }

}