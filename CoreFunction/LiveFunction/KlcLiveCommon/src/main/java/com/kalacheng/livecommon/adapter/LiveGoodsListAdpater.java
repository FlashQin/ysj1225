package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.model.ShopLiveGoods;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class LiveGoodsListAdpater extends RecyclerView.Adapter<LiveGoodsListAdpater.LiveGoodsListViewHolder> {
    private Context mContext;
    private List<ShopLiveGoods> mList = new ArrayList<>();

    public LiveGoodsListAdpater(Context mContext) {
        this.mContext = mContext;

    }

    public void getGoodsList(List<ShopLiveGoods> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiveGoodsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.livegoodslist_itme, null, false);
        LiveGoodsListViewHolder holder = new LiveGoodsListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveGoodsListViewHolder holder, final int position) {
        if (mList.get(position).goodsPicture.length() > 0 && mList.get(position).goodsPicture.contains(",")) {
            String[] pic = mList.get(position).goodsPicture.split(",");
            ImageLoader.display(pic[0], holder.LiveGoodsList_Image, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        } else {
            ImageLoader.display(mList.get(position).goodsPicture, holder.LiveGoodsList_Image, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        }
        holder.LiveGoodsList_Name.setText(mList.get(position).name);
        if (mList.get(position).channelId == 1) {
            if (mList.get(position).favorablePrice > 0) {
                holder.LiveGoodsList_Money.setText(String.valueOf(mList.get(position).favorablePrice));
            } else {
                holder.LiveGoodsList_Money.setText(String.valueOf(mList.get(position).goodsPrice));
            }
        } else {
            holder.LiveGoodsList_Money.setText(String.valueOf(mList.get(position).goodsPrice));
        }

        holder.LiveGoodsList_Rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mList.get(position).channelId == 1) {
                    ARouter.getInstance().build(ARouterPath.GoodsDetailsActivity).withLong(ARouterValueNameConfig.goodsId, mList.get(position).goodsId).navigation();
                } else {
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, mList.get(position).productLinks).navigation();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LiveGoodsListViewHolder extends RecyclerView.ViewHolder {
        public ImageView LiveGoodsList_Image;
        public TextView LiveGoodsList_Name;
        public TextView LiveGoodsList_Money;
        public TextView LiveGoodsList_Buy;
        public RelativeLayout LiveGoodsList_Rl;

        public LiveGoodsListViewHolder(@NonNull View itemView) {
            super(itemView);
            LiveGoodsList_Image = itemView.findViewById(R.id.LiveGoodsList_Image);
            LiveGoodsList_Name = itemView.findViewById(R.id.LiveGoodsList_Name);
            LiveGoodsList_Money = itemView.findViewById(R.id.LiveGoodsList_Money);
            LiveGoodsList_Buy = itemView.findViewById(R.id.LiveGoodsList_Buy);
            LiveGoodsList_Rl = itemView.findViewById(R.id.LiveGoodsList_Rl);
        }
    }
}
