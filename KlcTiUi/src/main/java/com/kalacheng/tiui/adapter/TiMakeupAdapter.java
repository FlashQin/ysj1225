package com.kalacheng.tiui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hwangjr.rxbus.RxBus;

import java.util.List;

import com.kalacheng.tiui.R;
import com.kalacheng.tiui.model.RxBusAction;
import com.kalacheng.tiui.model.TiMakeup;

/**
 * Created by Anko on 2019-09-05.
 * Copyright (c) 2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiMakeupAdapter extends RecyclerView.Adapter<TiDesViewHolder> {

    private List<TiMakeup> list;

    private int selectedPosition = 0;

    public TiMakeupAdapter(List<TiMakeup> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TiDesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ti_des, parent, false);
        return new TiDesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TiDesViewHolder holder, int position) {
        if (position == 0) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            p.setMargins((int) (holder.itemView.getContext().getResources().getDisplayMetrics().density * 35 + 0.5f),
                    0,
                    (int) (holder.itemView.getContext().getResources().getDisplayMetrics().density * 18 + 0.5f),
                    0);
            holder.itemView.requestLayout();
        } else {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
            p.setMargins(0, 0, 0, 0);
            holder.itemView.requestLayout();
        }
        holder.tiTextTV.setText(list.get(position).getString(holder.itemView.getContext()));
        holder.tiImageIV.setImageDrawable(list.get(position).getImageDrawable(holder.itemView.getContext()));

        if (selectedPosition == position) {
            holder.tiTextTV.setSelected(true);
            holder.tiImageIV.setSelected(true);
        } else {
            holder.tiTextTV.setSelected(false);
            holder.tiImageIV.setSelected(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedPosition = holder.getAdapterPosition();

                switch (selectedPosition) {
                    case 0:
                        RxBus.get().post(RxBusAction.ACTION_MAKEUP_NO);
                        break;
                    case 1:
                        RxBus.get().post(RxBusAction.ACTION_LIP_GLOSS);
                        break;
                    case 2:
                        RxBus.get().post(RxBusAction.ACTION_EYE_SHADOW);
                        break;
                    case 3:
                        RxBus.get().post(RxBusAction.ACTION_BLUSHER);
                        break;
                    case 4:
                        RxBus.get().post(RxBusAction.ACTION_BROW_PENCIL);
                        break;
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}