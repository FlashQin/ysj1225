package com.kalacheng.centercommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.libuser.model.ApiGradeReList;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.List;

public class NextTaskAdapter extends RecyclerView.Adapter<NextTaskAdapter.ViewHolder> {
    private Context mContext;
    private List<ApiGradeReList> mList;

    public NextTaskAdapter(Context context, List<ApiGradeReList> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_next_task, null, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (mList.get(position).type == 1) {//金币
            ImageLoader.display(com.kalacheng.commonview.R.mipmap.icon_money_big, holder.ivGift);
            holder.ivDayTag.setVisibility(View.GONE);
            holder.tvPrice.setText(mList.get(position).number + SpUtil.getInstance().getCoinUnit());
        } else if (mList.get(position).type == 2) {//礼物
            ImageLoader.display(mList.get(position).img, holder.ivGift);
            holder.ivDayTag.setVisibility(View.GONE);
            holder.tvPrice.setText(mList.get(position).title + "(*" + mList.get(position).number + ")");
        } else if (mList.get(position).type == 3) {//坐骑
            ImageLoader.display(mList.get(position).img, holder.ivGift);
            holder.ivDayTag.setVisibility(View.VISIBLE);
            holder.tvPrice.setText(mList.get(position).title + "(" + mList.get(position).number + "天)");
        } else if (mList.get(position).type == 4) {//短视频
            ImageLoader.display(com.kalacheng.commonview.R.mipmap.icon_video_free_give, holder.ivGift);
            holder.ivDayTag.setVisibility(View.GONE);
            holder.tvPrice.setText("私密视频(*" + mList.get(position).number + ")");
        } else {
            ImageLoader.display(mList.get(position).img, holder.ivGift);
            holder.ivDayTag.setVisibility(View.GONE);
            holder.tvPrice.setText(mList.get(position).title);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivGift;
        public ImageView ivDayTag;
        public TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGift = itemView.findViewById(com.kalacheng.commonview.R.id.iv_gift);
            ivDayTag = itemView.findViewById(com.kalacheng.commonview.R.id.ivDayTag);
            tvPrice = itemView.findViewById(com.kalacheng.commonview.R.id.tv_price);
        }
    }
}
