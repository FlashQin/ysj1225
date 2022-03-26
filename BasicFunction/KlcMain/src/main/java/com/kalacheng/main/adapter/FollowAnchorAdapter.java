package com.kalacheng.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.main.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.VoiceAnimationUtlis;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class FollowAnchorAdapter extends RecyclerView.Adapter<FollowAnchorAdapter.Holder> {


    private List<AppHomeHallDTO> mList = new ArrayList<>();

    Context context;

    public void setmList(List<AppHomeHallDTO> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_follow_anchor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position, @NonNull List<Object> payloads) {
        if (payloads.size() < 1) {
            ImageLoader.display(mList.get(position).headImg, holder.avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            String userName = mList.get(position).username;
            if (!TextUtils.isEmpty(userName) && userName.length() > 4) {
                holder.nameTv.setText(userName.substring(0, 4) + ".");
            } else {
                holder.nameTv.setText(userName);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    LookRoomUtlis.getInstance().getData(mList.get(position), context);
                }
            });

            VoiceAnimationUtlis.getInstance().showAnimation(holder.follow_voice);

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        RoundedImageView avatarIv;
        TextView nameTv;
        ImageView follow_voice;

        public Holder(@NonNull View itemView) {
            super(itemView);

            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            follow_voice = itemView.findViewById(R.id.follow_voice);
        }
    }

}
