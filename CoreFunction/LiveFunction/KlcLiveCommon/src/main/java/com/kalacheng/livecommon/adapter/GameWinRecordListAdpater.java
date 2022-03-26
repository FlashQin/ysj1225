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

public class GameWinRecordListAdpater extends RecyclerView.Adapter<GameWinRecordListAdpater.GameWinRecordListViewHolder> {
    private Context mContext;
    private List<GamePrizeRecord> mList= new ArrayList<>();
    public GameWinRecordListAdpater(Context mContext){
        this.mContext = mContext;
    }
    public void getData(List<GamePrizeRecord> data){
        this.mList .clear();
        this.mList = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public GameWinRecordListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gamewinrecordlist_itme,null,false);
        GameWinRecordListViewHolder holder = new GameWinRecordListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameWinRecordListViewHolder holder, int position) {
        if (mList.get(position).awardsType == 0){
            holder.GameWinRecord_GiftName.setText(SpUtil.getInstance().getCoinUnit()+"x"+mList.get(position).awardsCoin);
            ImageLoader.display(R.mipmap.icon_money_big,holder.GameWinRecord_GiftImage);

        }else {
            holder.GameWinRecord_GiftName.setText(mList.get(position).giftName+"x"+mList.get(position).awardsNum);
            ImageLoader.display(mList.get(position).picture,holder.GameWinRecord_GiftImage,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class GameWinRecordListViewHolder extends RecyclerView.ViewHolder{
        public ImageView GameWinRecord_GiftImage;
        public TextView GameWinRecord_GiftName;
        public GameWinRecordListViewHolder(@NonNull View itemView) {
            super(itemView);
            GameWinRecord_GiftName = itemView.findViewById(R.id.GameWinRecord_GiftName);
            GameWinRecord_GiftImage = itemView.findViewById(R.id.GameWinRecord_GiftImage);
        }
    }
}
