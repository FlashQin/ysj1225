package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busgame.model.GamePrice;
import com.kalacheng.livecommon.R;

import java.util.ArrayList;
import java.util.List;

public class TreasureChestAdpater extends RecyclerView.Adapter<TreasureChestAdpater.TreasureChestViewHolder> {
    private Context mContext;
    private List<GamePrice> mList = new ArrayList<>();
    public TreasureChestAdpater(Context mContext){
        this.mContext = mContext;
    }
    public void getData(List<GamePrice> data){
        this.mList .clear();
        this.mList = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TreasureChestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.treasure_chest_itme,null,false);
        TreasureChestViewHolder holder = new TreasureChestViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TreasureChestViewHolder holder, final int position) {

        holder.TreasureChest_buttom.setText("抽"+mList.get(position).gameNum+"次");
        holder.TreasureChest_coin.setText(String.valueOf(mList.get(position).useCoin));
         holder.TreasureChest_buttom.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 chestCallBack.onClick(position);
             }
         });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TreasureChestViewHolder extends RecyclerView.ViewHolder{
        public TextView TreasureChest_buttom;
        public TextView TreasureChest_coin;
        public TreasureChestViewHolder(@NonNull View itemView) {
            super(itemView);
            TreasureChest_coin = itemView.findViewById(R.id.TreasureChest_coin);
            TreasureChest_buttom = itemView.findViewById(R.id.TreasureChest_buttom);
        }
    }

    private TreasureChestCallBack chestCallBack;
    public void setTreasureChestCallBack(TreasureChestCallBack back){
        this.chestCallBack = back;
    }
    public interface TreasureChestCallBack{
            public void onClick(int poistion);
    }
}
