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
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.klc.bean.SendGiftPeopleBean;

import java.util.ArrayList;
import java.util.List;

/*
 * 语音选择送礼对象
 * */
public class VoiceSendGiftChoseAdpater extends RecyclerView.Adapter<VoiceSendGiftChoseAdpater.VoiceSendGiftChoseViewHolder> {

    private Context mContext;
    private List<SendGiftPeopleBean> mList = new ArrayList<>();
    private OnItmeClickBack mClickBack;
    private boolean mHideRoleTip;//隐藏“主播”提示

    public VoiceSendGiftChoseAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void setHideRoleTip(boolean hideRoleTip) {
        mHideRoleTip = hideRoleTip;
    }

    @NonNull
    @Override
    public VoiceSendGiftChoseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.voicesendgiftchose, null, false);
        VoiceSendGiftChoseViewHolder holder = new VoiceSendGiftChoseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceSendGiftChoseViewHolder holder, final int i) {
        if (mHideRoleTip) {
            holder.VoiceSendGiftChose_Name.setText(mList.get(i).name);
        } else {
            if (LiveConstants.ANCHORID == mList.get(i).uid){
                holder.VoiceSendGiftChose_Name.setText("主播  " + mList.get(i).name);
            }else {
                holder.VoiceSendGiftChose_Name.setText("副播  " + mList.get(i).name);
            }
        }

        ImageLoader.display(mList.get(i).headimage, holder.VoiceSendGiftChose_HeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        holder.VoiceSendGiftChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickBack.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VoiceSendGiftChoseViewHolder extends RecyclerView.ViewHolder {
        public ImageView VoiceSendGiftChose_HeadImage;
        public TextView VoiceSendGiftChose_Name;
        public RelativeLayout VoiceSendGiftChose;

        public VoiceSendGiftChoseViewHolder(@NonNull View itemView) {
            super(itemView);
            VoiceSendGiftChose_HeadImage = itemView.findViewById(R.id.VoiceSendGiftChose_HeadImage);
            VoiceSendGiftChose_Name = itemView.findViewById(R.id.VoiceSendGiftChose_Name);
            VoiceSendGiftChose = itemView.findViewById(R.id.VoiceSendGiftChose);

        }
    }

    public void setOnItmeClickBack(OnItmeClickBack back) {
        this.mClickBack = back;
    }

    public interface OnItmeClickBack {
        public void onClick(int poistion);
    }

    public void getData(List<SendGiftPeopleBean> data) {
        this.mList = data;
        notifyDataSetChanged();
    }
}

