package com.kalacheng.money.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppUsersCharge;
import com.kalacheng.money.R;

import java.util.ArrayList;
import java.util.List;

public class LiveRechargeAdpater extends RecyclerView.Adapter<LiveRechargeAdpater.LiveRechargeViewHolder> {

    private List<AppUsersCharge> mList = new ArrayList<>();
    private Context mContext;
    private LiveRechargeItemCallBack callBack;

    public LiveRechargeAdpater (Context mContext){
        this.mContext=mContext;
    }

    public void getData(List<AppUsersCharge> data){
        this.mList.clear();
        this.mList=data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiveRechargeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_recharge_itme,null,false);
        LiveRechargeViewHolder holder = new LiveRechargeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveRechargeViewHolder holder, final int position) {
        if (!mList.get(position).dicountDesr.equals("")){
            holder.Recharge_Discount.setVisibility(View.VISIBLE);
            holder.Recharge_Discount.setText(mList.get(position).dicountDesr);
        }else {
            holder.Recharge_Discount.setVisibility(View.GONE);
        }
        holder.Recharge_Money.setText("¥"+String.valueOf(mList.get(position).discountMoney));
        holder.Recharge_Gold.setText(String.valueOf(mList.get(position).coin));
        // 中间加横线 ， 添加Paint.ANTI_ALIAS_FLAG是线会变得清晰去掉锯齿
        holder.MatchRecharge_Money.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
        holder.MatchRecharge_Money.setText(String.valueOf(mList.get(position).money));
        holder.Rele_Recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class LiveRechargeViewHolder extends RecyclerView.ViewHolder{
        public TextView Recharge_Discount;
        public RelativeLayout Rele_Recharge;
        public TextView Recharge_Money;
        public TextView Recharge_Gold;
        public TextView MatchRecharge_Money;
        public LiveRechargeViewHolder(@NonNull View itemView) {
            super(itemView);
            Recharge_Money =itemView.findViewById(R.id.Recharge_Money);
            Recharge_Discount = itemView.findViewById(R.id.Recharge_Discount);
            Rele_Recharge = itemView.findViewById(R.id.Rele_Recharge);
            Recharge_Gold = itemView.findViewById(R.id.Recharge_Gold);
            MatchRecharge_Money = itemView.findViewById(R.id.MatchRecharge_Money);
        }
    }

    public void setLiveRechargeItemCallBack(LiveRechargeItemCallBack back){
        this.callBack=back;
    }

    public interface LiveRechargeItemCallBack{
        public void onClick(int poistion);
    }
}
