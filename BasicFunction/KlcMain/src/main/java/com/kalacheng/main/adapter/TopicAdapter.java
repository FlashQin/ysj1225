package com.kalacheng.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.AppVideoTopic;
import com.kalacheng.main.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.Holder> {

    List<AppVideoTopic> list = new ArrayList<>();
    Context context;

    public void setList(List<AppVideoTopic> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.size() < 1) {
            final AppVideoTopic topic = list.get(position);
            ImageLoader.display(topic.image, holder.imgIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder.topicTv.setText("#" + topic.name + "#");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.CommunityActivity)
                            .withInt(ARouterValueNameConfig.HOT_ID, (int) topic.id)
                            .withString(ARouterValueNameConfig.Name, topic.name)
                            .navigation();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        RoundedImageView imgIv;
        TextView topicTv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imgIv = itemView.findViewById(R.id.imgIv);
            topicTv = itemView.findViewById(R.id.topicTv);

        }
    }


}
