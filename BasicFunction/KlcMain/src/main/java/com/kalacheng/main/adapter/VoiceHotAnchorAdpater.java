package com.kalacheng.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.main.R;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class VoiceHotAnchorAdpater extends RecyclerView.Adapter<VoiceHotAnchorAdpater.VoiceHotAnchorViewHolder> {
    private Context mContext;
    private List<AppHomeHallDTO> mList = new ArrayList<>();
    private VoiceHotAnchorCallBack callBack;

    public VoiceHotAnchorAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getUpData(List<AppHomeHallDTO> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoiceHotAnchorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.voice_hot_anchor, null, false);
        VoiceHotAnchorViewHolder viewHolder = new VoiceHotAnchorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceHotAnchorViewHolder holder, final int position) {
        holder.VoiceHot_Name.setText(mList.get(position).username);
        ImageLoader.display(mList.get(position).headImg, holder.VoiceHot_HeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        holder.VoiceHot_Num.setText(String.valueOf(mList.get(position).fireVale));

        holder.VoiceHot_HeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LookRoomUtlis.getInstance().getData(mList.get(position), mContext);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class VoiceHotAnchorViewHolder extends RecyclerView.ViewHolder {
        public TextView VoiceHot_Name;
        public RoundedImageView VoiceHot_HeadImage;
        public TextView VoiceHot_Num;

        public VoiceHotAnchorViewHolder(@NonNull View itemView) {
            super(itemView);
            VoiceHot_HeadImage = itemView.findViewById(R.id.VoiceHot_HeadImage);
            VoiceHot_Name = itemView.findViewById(R.id.VoiceHot_Name);
            VoiceHot_Num = itemView.findViewById(R.id.VoiceHot_Num);
        }
    }

    public void setVoiceHotAnchorCallBack(VoiceHotAnchorCallBack back) {
        this.callBack = back;
    }

    public interface VoiceHotAnchorCallBack {
        public void onClick(long room);
    }
}
