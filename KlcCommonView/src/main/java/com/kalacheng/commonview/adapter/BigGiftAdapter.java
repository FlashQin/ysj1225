package com.kalacheng.commonview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.AdminGiftPack;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.List;

public class BigGiftAdapter extends RecyclerView.Adapter<BigGiftAdapter.ViewHolder> {

    private Context mContext;
    private List<AdminGiftPack> mList;

    public BigGiftAdapter(Context mContext, List<AdminGiftPack> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_big_gift, null, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(mList.get(position).action)) {
            if (mList.get(position).action.equals("coin")) {//金币
                ImageLoader.display(R.mipmap.icon_money_big, holder.ivGift);
                holder.ivDayTag.setVisibility(View.GONE);
                holder.tvPrice.setText(mList.get(position).typeVal + SpUtil.getInstance().getCoinUnit());
            } else if (mList.get(position).action.equals("gift")) {//礼物
                ImageLoader.display(mList.get(position).gifticon, holder.ivGift);
                holder.ivDayTag.setVisibility(View.GONE);
                holder.tvPrice.setText(mList.get(position).name + "(*" + mList.get(position).typeVal + ")");
            } else if (mList.get(position).action.equals("car")) {//坐骑
                ImageLoader.display(mList.get(position).gifticon, holder.ivGift);
                holder.ivDayTag.setVisibility(View.VISIBLE);
                holder.tvPrice.setText(mList.get(position).name + "(" + mList.get(position).typeVal + "天)");
            } else if (mList.get(position).action.equals("video")) {//短视频
                ImageLoader.display(R.mipmap.icon_video_free_give, holder.ivGift);
                holder.ivDayTag.setVisibility(View.GONE);
                holder.tvPrice.setText("私密视频(*" + mList.get(position).typeVal + ")");
            } else {
                ImageLoader.display(mList.get(position).gifticon, holder.ivGift);
                holder.ivDayTag.setVisibility(View.GONE);
                holder.tvPrice.setText(mList.get(position).name);
            }
        } else {
            ImageLoader.display(mList.get(position).gifticon, holder.ivGift);
            holder.ivDayTag.setVisibility(View.GONE);
            holder.tvPrice.setText(mList.get(position).name);
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivGift;
        public ImageView ivDayTag;
        public TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGift = itemView.findViewById(R.id.iv_gift);
            ivDayTag = itemView.findViewById(R.id.ivDayTag);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

}
