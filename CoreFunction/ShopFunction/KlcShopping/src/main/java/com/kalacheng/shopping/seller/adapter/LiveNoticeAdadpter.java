package com.kalacheng.shopping.seller.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ShopLiveAnnouncement;
import com.kalacheng.shopping.R;

import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipeHelper;

public class LiveNoticeAdadpter extends RecyclerView.Adapter<LiveNoticeAdadpter.Holder> {

    private static OnClickDeleteListenes listenes;
    List<ShopLiveAnnouncement> list;

    public void setClickDeleteListenes(OnClickDeleteListenes listenes) {
        this.listenes = listenes;
    }

    public LiveNoticeAdadpter() {
        list = new ArrayList<>();
    }

    public void setList(List<ShopLiveAnnouncement> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_notice, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        try {
            if (!TextUtils.isEmpty(list.get(position).liveDate)) {
                holder.timeTv.setText(list.get(position).liveDate);
                holder.contentTv.setText(list.get(position).title);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        holder.delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenes.onClickDeleteListenes(list.get(position).id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder implements WeSwipeHelper.SwipeLayoutTypeCallBack {
        TextView timeTv;
        TextView contentTv;
        TextView delTv;
        LinearLayout rootView;
        LinearLayout divLl;

        public Holder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.timeTv);
            contentTv = itemView.findViewById(R.id.contentTv);
            delTv = itemView.findViewById(R.id.delTv);
            rootView = itemView.findViewById(R.id.rootView);
            divLl = itemView.findViewById(R.id.divLl);

//            delTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != listenes){
//                        listenes.onClickDeleteListenes(list.get(getAdapterPosition()));
//                    }
//                }
//            });
        }

        @Override
        public float getSwipeWidth() {
            return delTv.getWidth();
        }

        @Override
        public View needSwipeLayout() {
            return rootView;
        }

        @Override
        public View onScreenView() {
            return divLl;
        }
    }

    public interface OnClickDeleteListenes {

        void onClickDeleteListenes(long id);

    }

}
