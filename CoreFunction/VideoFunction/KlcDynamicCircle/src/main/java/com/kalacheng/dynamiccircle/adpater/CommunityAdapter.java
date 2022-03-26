package com.kalacheng.dynamiccircle.adpater;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.dynamiccircle.R;
import com.kalacheng.base.event.VideoZanEvent;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.Holder> {

    private List<ApiUserVideo> mList = new ArrayList<>();
    private OnItemClickListener<ApiUserVideo> onItemClickListener;

    public void setData(List<ApiUserVideo> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public void loadData(List<ApiUserVideo> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public void setZanComment(VideoZanEvent event){
        if (null != event){
            if (event.getIsZanComment()==1){
                this.mList.get(event.getPosition()).likes = event.getZanNumber();
                this.mList.get(event.getPosition()).isLike = event.getIsLike();
            }else if (event.getIsZanComment()==2){
                this.mList.get(event.getPosition()).comments = event.getCommentNumber();
            }
        }
        notifyDataSetChanged();
    }

    public List<ApiUserVideo> getData() {
        return mList;
    }

    public void setOnItemClickListener(OnItemClickListener<ApiUserVideo> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_community, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position, @NonNull List<Object> payloads) {
        if (payloads.size() < 1) {
            ApiUserVideo userVideo = mList.get(position);

            if (userVideo.isVip == 0) {
                holder.ivVipTag.setVisibility(View.VISIBLE);
            } else {
                holder.ivVipTag.setVisibility(View.GONE);
            }
            ImageLoader.display(userVideo.thumb, holder.coverIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(userVideo.topicName)) {
                builder.append("<font color='#333333' size='15'><b>#" + userVideo.topicName + "#<b></font>");
            }
            builder.append(userVideo.title);
            if (TextUtils.isEmpty(builder.toString())) {
                holder.contentTv.setVisibility(View.GONE);
            }else holder.contentTv.setVisibility(View.VISIBLE);

            holder.contentTv.setText(Html.fromHtml(builder.toString()));
            ImageLoader.display(userVideo.avatar, holder.avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder.nameTv.setText(userVideo.userName);
            holder.numTv.setText(userVideo.likes + "");

            holder.bgFl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, mList.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        FrameLayout bgFl;
        RoundedImageView coverIv;
        TextView contentTv;
        RoundedImageView avatarIv;
        TextView nameTv;
        TextView numTv;
        ImageView ivVipTag;

        Holder(@NonNull View itemView) {
            super(itemView);
            bgFl = itemView.findViewById(R.id.bgFl);
            coverIv = itemView.findViewById(R.id.coverIv);
            contentTv = itemView.findViewById(R.id.contentTv);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            numTv = itemView.findViewById(R.id.numTv);
            ivVipTag = itemView.findViewById(R.id.ivVipTag);
        }
    }


}
