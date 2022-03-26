package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.ApiUserBasicInfo;
import com.kalacheng.livecommon.R;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class VoiceVipSeatsListAdpater extends RecyclerView.Adapter<VoiceVipSeatsListAdpater.VoiceVipSeatsListViewHolder> {
    private Context mContext;
    private List<ApiUserBasicInfo> mList = new ArrayList<>();

    public VoiceVipSeatsListAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getVipList(List<ApiUserBasicInfo> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoiceVipSeatsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.voicevipseatslist_itme, null, false);
        VoiceVipSeatsListViewHolder holder = new VoiceVipSeatsListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceVipSeatsListViewHolder holder, int position) {
        holder.VoiceVipSeats_UserName.setText(mList.get(position).username);
        holder.VoiceVipSeats_Number.setText(String.valueOf(position + 1));
        ImageLoader.display(mList.get(position).avatar, holder.VoiceVipSeats_UserHead, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        holder.VoiceVipSeats_Money.setText(String.valueOf(mList.get(position).currContValue));

        ImageLoader.display(mList.get(position).userGradeImg, holder.VoiceVipSeats_UserGuard);
        ImageLoader.display(mList.get(position).nobleGradeColor, holder.VoiceVipSeats_VipGuard);
        SexUtlis.getInstance().setSex(mContext, holder.VoiceVipSeats_Sex, mList.get(position).sex, 0);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VoiceVipSeatsListViewHolder extends RecyclerView.ViewHolder {
        public TextView VoiceVipSeats_Money;
        public TextView VoiceVipSeats_UserName;
        public RoundedImageView VoiceVipSeats_UserHead;
        public TextView VoiceVipSeats_Number;
        public LinearLayout VoiceVipSeats_Sex;
        public ImageView VoiceVipSeats_UserGuard;
        public ImageView VoiceVipSeats_VipGuard;

        public VoiceVipSeatsListViewHolder(@NonNull View itemView) {
            super(itemView);
            VoiceVipSeats_Money = itemView.findViewById(R.id.VoiceVipSeats_Money);
            VoiceVipSeats_UserName = itemView.findViewById(R.id.VoiceVipSeats_UserName);
            VoiceVipSeats_UserHead = itemView.findViewById(R.id.VoiceVipSeats_UserHead);
            VoiceVipSeats_Number = itemView.findViewById(R.id.VoiceVipSeats_Number);
            VoiceVipSeats_Sex = itemView.findViewById(R.id.VoiceVipSeats_Sex);
            VoiceVipSeats_UserGuard = itemView.findViewById(R.id.VoiceVipSeats_UserGuard);
            VoiceVipSeats_VipGuard = itemView.findViewById(R.id.VoiceVipSeats_VipGuard);
        }
    }


}
