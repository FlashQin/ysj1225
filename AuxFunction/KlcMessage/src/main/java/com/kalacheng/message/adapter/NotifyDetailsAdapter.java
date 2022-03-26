package com.kalacheng.message.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppSystemNoticeUser;
import com.kalacheng.message.R;
import com.kalacheng.commonview.jguangIm.ImDateUtil;

import java.util.ArrayList;
import java.util.List;

public class NotifyDetailsAdapter extends RecyclerView.Adapter<NotifyDetailsAdapter.Holder> {

    List<AppSystemNoticeUser> list = new ArrayList();


    //加载更多
    public void loadData(List<AppSystemNoticeUser> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    //刷新
    public void refreshData(List<AppSystemNoticeUser> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify1, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        AppSystemNoticeUser noticeUser = list.get(position);
        if (payload == null) {
            holder.timeTv.setText(ImDateUtil.getTimestampString(noticeUser.addtime.getTime()));
            holder.titleTv.setText(noticeUser.title);
            holder.contentTv.setText(noticeUser.content);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView timeTv;
        TextView titleTv;
        TextView contentTv;

        @SuppressLint("CutPasteId")
        Holder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.timeTv);
            titleTv = itemView.findViewById(R.id.titleTv);
            contentTv = itemView.findViewById(R.id.contentTv);
        }
    }

}
