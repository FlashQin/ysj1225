package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busgame.model.GameLuckDraw;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

public class TreasureChestRecordAdpater extends RecyclerView.Adapter<TreasureChestRecordAdpater.TreasureChestRecordViewHolder> {
    public Context mContext;
    private List<GameLuckDraw> mList = new ArrayList<>();
    public TreasureChestRecordAdpater(Context mContext){
        this.mContext = mContext;
    }
    public void getList(List<GameLuckDraw> data){
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TreasureChestRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.treasurechestrecord_itme,null,false);
        TreasureChestRecordViewHolder holder = new TreasureChestRecordViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TreasureChestRecordViewHolder holder, int position) {

        holder.TreasureChestRecord_gameName.setText(mList.get(position).gameName);
        holder.TreasureChestRecord_gameTime.setText(mList.get(position).addTime);
        holder.TreasureChestRecord_Money.setText("-"+String.valueOf(mList.get(position).gameCoin)+ SpUtil.getInstance().getCoinUnit());
        if (mList.get(position).isAwards==1){
            holder.TreasureChestRecord_state.setText("已中奖");
            holder.TreasureChestRecord_state.setTextColor(Color.parseColor("#FF4A43"));
        }else {
            holder.TreasureChestRecord_state.setText("未中奖");
            holder.TreasureChestRecord_state.setTextColor(Color.parseColor("#666666"));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TreasureChestRecordViewHolder extends RecyclerView.ViewHolder{
        public TextView TreasureChestRecord_gameName;
        public TextView TreasureChestRecord_gameTime;
        public TextView TreasureChestRecord_Money;
        public TextView TreasureChestRecord_state;
        public TreasureChestRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            TreasureChestRecord_gameName = itemView.findViewById(R.id.TreasureChestRecord_gameName);
            TreasureChestRecord_gameTime = itemView.findViewById(R.id.TreasureChestRecord_gameTime);
            TreasureChestRecord_Money = itemView.findViewById(R.id.TreasureChestRecord_Money);
            TreasureChestRecord_state = itemView.findViewById(R.id.TreasureChestRecord_state);

        }
    }
}
