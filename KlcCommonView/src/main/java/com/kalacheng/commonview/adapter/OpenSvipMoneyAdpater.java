package com.kalacheng.commonview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.AppSvipPackage;
import com.kalacheng.util.utils.TextViewUtil;

import java.util.ArrayList;
import java.util.List;

//开通svip适配器w
public class OpenSvipMoneyAdpater extends RecyclerView.Adapter<OpenSvipMoneyAdpater.OpenSvipMoneyViewHolder> {
    private Context mContext;
    private List<AppSvipPackage> mList = new ArrayList<>();
    private int clickedpoistion = 0;

    public OpenSvipMoneyAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<AppSvipPackage> data) {
        this.mList.clear();
        if (data != null && data.size() > 0) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setClick(int poistion) {
        this.clickedpoistion = poistion;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OpenSvipMoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.opensvipmoney_itme, null, false);
        OpenSvipMoneyViewHolder holder = new OpenSvipMoneyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OpenSvipMoneyViewHolder holder, final int position) {
        holder.openSvip_day.setText(String.valueOf(mList.get(position).time) + mList.get(position).timeUnits);
        if (mList.get(position).coinType == 1) {
            TextViewUtil.setDrawableNull(holder.openSvip_moeny);
            holder.openSvip_moeny.setText("¥ " + String.valueOf(mList.get(position).coin));
        } else {
            TextViewUtil.setDrawableLeft(holder.openSvip_moeny, R.mipmap.icon_money);
            holder.openSvip_moeny.setText(String.valueOf(mList.get(position).coin));
        }

        if (clickedpoistion == position) {
            holder.openSvip_moeny_clicked.setVisibility(View.VISIBLE);
        } else {
            holder.openSvip_moeny_clicked.setVisibility(View.GONE);
        }

        holder.openSvip_Rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() != 0 ? mList.size() : 0;
    }

    public class OpenSvipMoneyViewHolder extends RecyclerView.ViewHolder {
        public ImageView openSvip_moeny_clicked;
        public TextView openSvip_moeny;
        public TextView openSvip_day;
        public RelativeLayout openSvip_Rela;

        public OpenSvipMoneyViewHolder(@NonNull View itemView) {
            super(itemView);
            openSvip_moeny_clicked = itemView.findViewById(R.id.openSvip_moeny_clicked);
            openSvip_moeny = itemView.findViewById(R.id.openSvip_moeny);
            openSvip_day = itemView.findViewById(R.id.openSvip_day);
            openSvip_Rela = itemView.findViewById(R.id.openSvip_Rela);
        }

    }

    private OpenSvipMoneyCallBack callBack;

    public void setOpenSvipMoneyCallBack(OpenSvipMoneyCallBack back) {
        this.callBack = back;
    }

    public interface OpenSvipMoneyCallBack {
        public void onClick(int poistion);
    }
}
