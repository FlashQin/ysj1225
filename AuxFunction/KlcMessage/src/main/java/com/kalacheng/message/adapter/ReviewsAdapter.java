package com.kalacheng.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.ApiCommentsMsg;
import com.kalacheng.message.R;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    List<ApiCommentsMsg> list;
    Context context;
    OnClick onClick;

    public ReviewsAdapter() {
        list = new ArrayList();
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    //加载更多
    public void loadData(List<ApiCommentsMsg> data) {
        list.addAll(data);
        notifyDataSetChanged();
    }

    //刷新
    public void refreshData(List<ApiCommentsMsg> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    //删除
    public void delete(int poistion) {
        list.remove(poistion);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reviews, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewsViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, final int position, @NonNull List payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final ApiCommentsMsg apiCommentsMsg = list.get(position);
        if (payload == null) {
            ImageLoader.display(apiCommentsMsg.avatar, holder.headIv);
            holder.nameTv.setText(apiCommentsMsg.userName);
            holder.reviewsTv.setText(apiCommentsMsg.type == 1 ? "评论了你" : "点赞了你");
            holder.praiseIv.setVisibility(apiCommentsMsg.type == 1 ? View.GONE : View.VISIBLE);
            holder.contentTv.setText(apiCommentsMsg.type == 1 ? apiCommentsMsg.content : "");
            ImageLoader.display(apiCommentsMsg.url, holder.dynamicIv);
            holder.playIv.setVisibility(apiCommentsMsg.sourceType == 1 ? View.VISIBLE : View.GONE);
            DateUtil startTime = new DateUtil(apiCommentsMsg.addtime);
            holder.tvTime.setText(startTime.getDateToFormat("MM-dd HH:mm:ss"));
            holder.tvDelete.setVisibility(apiCommentsMsg.type == 1 ? View.VISIBLE : View.GONE);

            // 点进详情查看
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClick != null) {
                        onClick.onClick(apiCommentsMsg);
                        apiCommentsMsg.isRead = 0;
                        notifyItemChanged(position);
                    }
                }
            });

            // 删除
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onClick) {
                        onClick.onDelete(apiCommentsMsg, position);
                    }
                }
            });

            // 回复
            holder.tvReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != onClick) {
                        onClick.onReply(apiCommentsMsg);
                    }
                }
            });
        }
        holder.nameTv.setTextColor(
                apiCommentsMsg.isRead == 1
                        ? ContextCompat.getColor(context, R.color.message_color1)
                        : ContextCompat.getColor(context, R.color.textColor6));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ReviewsViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView headIv;
        TextView nameTv;
        TextView reviewsTv;
        TextView tvTime;
        ImageView praiseIv;
        TextView contentTv;
        RoundedImageView dynamicIv;
        ImageView playIv;
        TextView tvReply, tvDelete; // 回复和删除

        ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            headIv = itemView.findViewById(R.id.headIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            reviewsTv = itemView.findViewById(R.id.reviewsTv);
            praiseIv = itemView.findViewById(R.id.praiseIv);
            contentTv = itemView.findViewById(R.id.contentTv);
            dynamicIv = itemView.findViewById(R.id.dynamicIv);
            playIv = itemView.findViewById(R.id.playIv);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvReply = itemView.findViewById(R.id.tvReply);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

    public interface OnClick {

        void onClick(ApiCommentsMsg noticeUser);

        void onReply(ApiCommentsMsg noticeUser);

        void onDelete(ApiCommentsMsg noticeUser, int position);

    }
}
