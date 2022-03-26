package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busgame.model.GameAwards;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TreasureChestGiftListAdpater extends RecyclerView.Adapter<TreasureChestGiftListAdpater.TreasureChestGiftListViewHolder> {
    private Context mContext;
    private List<GameAwards> mList = new ArrayList<>();
    public TreasureChestGiftListAdpater(Context mContext){
        this.mContext = mContext;
    }
    public void getData(List<GameAwards> data){
        this.mList .clear();
        this.mList =data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TreasureChestGiftListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.treasurechestgiftlist_itme,null,false);
        TreasureChestGiftListViewHolder holder = new TreasureChestGiftListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TreasureChestGiftListViewHolder holder, int position) {
        if(mList.get(position).awardsType == 0){
            ImageLoader.display(R.mipmap.icon_money_big,holder.TreasureChestGiftList_Image);
        }else {
            ImageLoader.display(mList.get(position).picture,holder.TreasureChestGiftList_Image,R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TreasureChestGiftListViewHolder extends RecyclerView.ViewHolder{
        public ImageView TreasureChestGiftList_Image;

        public TreasureChestGiftListViewHolder(@NonNull View itemView) {
            super(itemView);
            TreasureChestGiftList_Image = itemView.findViewById(R.id.TreasureChestGiftList_Image);
        }
    }
}
