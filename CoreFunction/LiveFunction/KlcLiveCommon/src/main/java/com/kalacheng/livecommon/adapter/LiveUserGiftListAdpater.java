package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.GiftWallDto;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class LiveUserGiftListAdpater extends RecyclerView.Adapter<LiveUserGiftListAdpater.LiveUserGiftListViewHolder> {
    private Context mContext;
    private List<GiftWallDto> mList = new ArrayList<>();

    public LiveUserGiftListAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getLiveUserGiftList(List<GiftWallDto> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiveUserGiftListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_usergift_list_itme, null, false);
        LiveUserGiftListViewHolder holder = new LiveUserGiftListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveUserGiftListViewHolder holder, int position) {

        holder.gift_num.setText(String.valueOf(mList.get(position).totalNum));
        ImageLoader.display(mList.get(position).gifticon, holder.gift_image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LiveUserGiftListViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView gift_image;
        public TextView gift_num;

        public LiveUserGiftListViewHolder(@NonNull View itemView) {
            super(itemView);
            gift_image = itemView.findViewById(R.id.gift_image);
            gift_num = itemView.findViewById(R.id.gift_num);

        }
    }
}
