package com.kalacheng.livecommon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busgame.model.GameUserPrizeDTO;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.view.ItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TreasureChestMyPrizeAdpater extends RecyclerView.Adapter<TreasureChestMyPrizeAdpater.TreasureChestMyPrizeViewHolder> {
    private Context mContext;
    private List<GameUserPrizeDTO> mList = new ArrayList<>();

    public TreasureChestMyPrizeAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(List<GameUserPrizeDTO> data) {
        this.mList.clear();
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TreasureChestMyPrizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.treasurechest_myprize_itme, null, false);
        TreasureChestMyPrizeViewHolder holder = new TreasureChestMyPrizeViewHolder(view);
        return holder;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull TreasureChestMyPrizeViewHolder holder, int position) {
        holder.TreasureChestMyPrize_Time.setText(mList.get(position).luckDrawDate);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        holder.TreasureChestMyPrize_GiftList.setLayoutManager(manager);

        holder.TreasureChestMyPrize_GiftList.addItemDecoration(new ItemDecoration(mContext, 0, 10, 0));
        TreasureChestMyPrizeItmeAdpater adpater = new TreasureChestMyPrizeItmeAdpater(mContext);
        holder.TreasureChestMyPrize_GiftList.setAdapter(adpater);
        adpater.getData(mList.get(position).gamePrizeRecordList);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TreasureChestMyPrizeViewHolder extends RecyclerView.ViewHolder {
        public TextView TreasureChestMyPrize_Time;
        public RecyclerView TreasureChestMyPrize_GiftList;

        public TreasureChestMyPrizeViewHolder(@NonNull View itemView) {
            super(itemView);
            TreasureChestMyPrize_Time = itemView.findViewById(R.id.TreasureChestMyPrize_Time);
            TreasureChestMyPrize_GiftList = itemView.findViewById(R.id.TreasureChestMyPrize_GiftList);
        }
    }
}
