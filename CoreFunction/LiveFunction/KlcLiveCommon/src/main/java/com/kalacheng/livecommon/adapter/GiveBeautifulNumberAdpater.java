package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppLiang;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DecimalFormatUtils;

import java.util.ArrayList;
import java.util.List;

public class GiveBeautifulNumberAdpater extends RecyclerView.Adapter<GiveBeautifulNumberAdpater.GiveBeautifulNumberViewHolder> {
    private Context mContext;
    private List<AppLiang> mList = new ArrayList<>();
    private GiveBeautifulNumberCallBack callBack;
    public GiveBeautifulNumberAdpater(Context mContext){
        this.mContext = mContext;
    }

    public void getData(List<AppLiang> data){
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public GiveBeautifulNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.givebeautifulnumber_itme,null,false);
        GiveBeautifulNumberViewHolder holder = new GiveBeautifulNumberViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GiveBeautifulNumberViewHolder holder, final int position) {
        holder.GiveBeautifulNumber_Num.setText("ID:"+mList.get(position).name);
        holder.GiveBeautifulNumber_Money.setText(DecimalFormatUtils.isIntegerDouble(mList.get(position).coin));

        holder.GiveBeautifulNumber_Re.setOnClickListener(new View.OnClickListener() {
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

    public class GiveBeautifulNumberViewHolder extends RecyclerView.ViewHolder{
        public TextView GiveBeautifulNumber_Num;
        public TextView GiveBeautifulNumber_Money;
        public RelativeLayout GiveBeautifulNumber_Re;
        public GiveBeautifulNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            GiveBeautifulNumber_Num = itemView.findViewById(R.id.GiveBeautifulNumber_Num);
            GiveBeautifulNumber_Money = itemView.findViewById(R.id.GiveBeautifulNumber_Money);
            GiveBeautifulNumber_Re = itemView.findViewById(R.id.GiveBeautifulNumber_Re);
        }
    }

    public void setGiveBeautifulNumberCallBack(GiveBeautifulNumberCallBack beautifulNumberCallBack){
        this.callBack = beautifulNumberCallBack;
    }
    public interface GiveBeautifulNumberCallBack{
        public void onClick(int poistion);
    }
}
