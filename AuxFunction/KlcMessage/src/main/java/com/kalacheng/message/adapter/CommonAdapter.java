package com.kalacheng.message.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppCommonWords;
import com.kalacheng.message.R;
import com.kalacheng.message.activity.ChatRoomActivity;
import com.kalacheng.commonview.jguangIm.ImMessageBean;

import java.util.ArrayList;
import java.util.List;

public class CommonAdapter extends RecyclerView.Adapter {

    private List<AppCommonWords> list = new ArrayList();
    private ChatRoomActivity activity;

    public CommonAdapter(ChatRoomActivity activity) {
        this.activity = activity;
    }

    public void setList(List<AppCommonWords> list) {
        if (list != null) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        final String common = list.get(position).name;
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        if (payload == null) {
            TextView commonTv = (TextView) holder.itemView;
            commonTv.setText(common);
            commonTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.privateChat(ImMessageBean.TYPE_TEXT, common, null);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Vh extends RecyclerView.ViewHolder {

        public Vh(@NonNull View itemView) {
            super(itemView);
        }
    }
}
