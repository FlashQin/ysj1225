package com.kalacheng.shopping.buyer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.LogisticsNodeDTO;
import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class LogisdticsAdapter extends RecyclerView.Adapter<LogisdticsAdapter.Holder> {
    private List<LogisticsNodeDTO> list = new ArrayList<>();

    public void setList(List<LogisticsNodeDTO> list) {
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logisdtics, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        try {
            if (payloads.size() < 1) {
                holder.topLineView.setVisibility(View.VISIBLE);
                holder.bottomLineView.setVisibility(View.VISIBLE);
                if (position == 0) {
                    holder.topLineView.setVisibility(View.INVISIBLE);
                }
                if (position == list.size() - 1) {
                    holder.bottomLineView.setVisibility(View.INVISIBLE);
                }

                if (null != list) {
                    holder.daysTv.setText(new DateUtil(list.get(position).time).getDateToFormat("MM-dd"));
                    holder.timeTv.setText(new DateUtil(list.get(position).time).getDateToFormat("HH:mm"));
                    holder.traceTv.setText(list.get(position).content);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView daysTv, timeTv;
        View topLineView;
        View bottomLineView;
        ImageView enterIv;
        TextView traceTv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.timeTv);
            daysTv = itemView.findViewById(R.id.daysTv);
            topLineView = itemView.findViewById(R.id.topLineView);
            bottomLineView = itemView.findViewById(R.id.bottomLineView);
            enterIv = itemView.findViewById(R.id.enterIv);
            traceTv = itemView.findViewById(R.id.traceTv);
        }
    }

}
