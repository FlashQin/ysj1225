package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busgame.model.GamePrizeRecord;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

/*
* 中奖礼物列表
* */
public class TreasureChestMyPrizeItmeAdpater extends RecyclerView.Adapter<TreasureChestMyPrizeItmeAdpater.TreasureChestMyPrizeItmeViewHolder> {
    private Context mContext;
    private List<GamePrizeRecord> mList = new ArrayList<>();
    public TreasureChestMyPrizeItmeAdpater(Context mContext){
        this.mContext = mContext;
    }
    public void getData(List<GamePrizeRecord> data){
        this.mList = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TreasureChestMyPrizeItmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.treasurechest_myprizeitme_itme,null,false);
        TreasureChestMyPrizeItmeViewHolder holder = new TreasureChestMyPrizeItmeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TreasureChestMyPrizeItmeViewHolder holder, int position) {
            if (mList.get(position).awardsType == 0){
                ImageLoader.display(R.mipmap.icon_money_big,holder.TreasureChestMyPrizeItme_Image);
                holder.TreasureChestMyPrizeItme_Name.setText(SpUtil.getInstance().getCoinUnit()+"x"+mList.get(position).awardsCoin);

            }else {
                ImageLoader.display(mList.get(position).picture,holder.TreasureChestMyPrizeItme_Image,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                holder.TreasureChestMyPrizeItme_Name.setText(mList.get(position).giftName+"x"+mList.get(position).awardsNum);
            }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TreasureChestMyPrizeItmeViewHolder extends RecyclerView.ViewHolder{
        public ImageView TreasureChestMyPrizeItme_Image;
        public TextView TreasureChestMyPrizeItme_Name;
        public TreasureChestMyPrizeItmeViewHolder(@NonNull View itemView) {
            super(itemView);
            TreasureChestMyPrizeItme_Name = itemView.findViewById(R.id.TreasureChestMyPrizeItme_Name);
            TreasureChestMyPrizeItme_Image = itemView.findViewById(R.id.TreasureChestMyPrizeItme_Image);
        }
    }
}
