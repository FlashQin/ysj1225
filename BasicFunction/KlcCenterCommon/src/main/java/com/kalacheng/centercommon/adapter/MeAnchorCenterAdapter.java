package com.kalacheng.centercommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.centercommon.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;

import java.util.ArrayList;
import java.util.List;

public class MeAnchorCenterAdapter extends RecyclerView.Adapter<MeAnchorCenterAdapter.ViewHolder> {

    private Context mContext;
    private List<SimpleImageUrlTextBean> mList = new ArrayList<>();

    public double voiceCoin, videoCoin; // 视频通话费用/min,  语音通话费用/min
    public int liveState; // 一对一在线状态  0在线1忙碌2离开3通话中
    private int openState; // 一对一打开状态 0：默认，不打开  1：打开 (是否开启1v1通话)


    private OnItemClickCallback<SimpleImageUrlTextBean> onItemClickCallback;
    private OnNoDisturbListener onNoDisturbListener;

    public MeAnchorCenterAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<SimpleImageUrlTextBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    // 修改状态
    public void setStatusAdapter(int openState, int liveState, double voiceCoin, double videoCoin){
        this.openState = openState;
        this.liveState = liveState;
        this.voiceCoin = voiceCoin;
        this.videoCoin = videoCoin;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_me_anchor_center_bottom, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SimpleImageUrlTextBean bean = mList.get(position);
        holder.ivIcon.setImageResource(bean.src);
        holder.tvName.setText(bean.text);
        holder.ivNoDisturb.setVisibility(View.VISIBLE);
        holder.state.setVisibility(View.GONE);
        if (position == 0) {
            holder.viewDivider.setVisibility(View.GONE);
        } else {
            holder.viewDivider.setVisibility(View.VISIBLE);
        }

        if (bean.src == R.mipmap.icon_anchor_center_open_call) {//开启通话
            holder.ivNoDisturb.setVisibility(View.VISIBLE);
            holder.ivRight.setVisibility(View.GONE);
            if (openState ==1) {
                holder.ivNoDisturb.setImageResource(R.mipmap.icon_switch_open);
            } else {
                holder.ivNoDisturb.setImageResource(R.mipmap.icon_switch_close);
            }
        } else {
            holder.ivNoDisturb.setVisibility(View.GONE);
        }

        if (bean.src == R.mipmap.icon_anchor_center_voice) {//语音接听收费
            holder.ivNoDisturb.setVisibility(View.GONE);
            holder.tvNum.setVisibility(View.GONE);
            holder.state.setVisibility(View.VISIBLE);
            holder.state.setText(DecimalFormatUtils.isIntegerDouble(voiceCoin) + SpUtil.getInstance().getCoinUnit() + "/分钟");
        }

        if (bean.src == R.mipmap.icon_anchor_center_video) {//视频接听收费
            holder.ivNoDisturb.setVisibility(View.GONE);
            holder.tvNum.setVisibility(View.GONE);
            holder.state.setVisibility(View.VISIBLE);
            holder.state.setText(DecimalFormatUtils.isIntegerDouble(videoCoin) + SpUtil.getInstance().getCoinUnit() + "/分钟");
        }

        if (mList.get(position).src == R.mipmap.icon_anchor_center_status){//状态设置
            holder.tvNum.setVisibility(View.VISIBLE);
            holder.state.setVisibility(View.VISIBLE);
            switch (liveState){
                case 0:
                    holder.state.setText("在线");
                    holder.tvNum.setBackgroundResource(R.drawable.bg_status_green);
                    break;
                case 1:
                    holder.state.setText("忙碌");
                    holder.tvNum.setBackgroundResource(R.drawable.bg_status_red);
                    break;
                case 2:
                    holder.state.setText("离开");
                    holder.tvNum.setBackgroundResource(R.drawable.bg_status_gray);
                    break;
                case 3:
                    holder.state.setText("通话中");
                    holder.tvNum.setBackgroundResource(R.drawable.bg_status_orange);
                    break;
                default:
                    break;
            }
        }else {
            holder.tvNum.setVisibility(View.GONE);
        }

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (bean.src != R.mipmap.icon_anchor_center_open_call){
                    if (onItemClickCallback != null) {
                        onItemClickCallback.onClick(v, bean);
                    }
                }

            }
        });
        holder.ivNoDisturb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (onItemClickCallback != null) {
                    onItemClickCallback.onClick(v, bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View viewDivider;
        LinearLayout layoutItem;
        ImageView ivIcon;
        TextView tvName;
        ImageView ivNoDisturb;
        TextView tvNum;
        ImageView ivRight;
        TextView state;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewDivider = itemView.findViewById(R.id.viewDivider);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);
            ivNoDisturb = itemView.findViewById(R.id.ivNoDisturb);
            tvNum = itemView.findViewById(R.id.tvNum);
            ivRight = itemView.findViewById(R.id.ivRight);
            state = itemView.findViewById(R.id.state);
        }
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setOnNoDisturbListener(OnNoDisturbListener onNoDisturbListener) {
        this.onNoDisturbListener = onNoDisturbListener;
    }

    public interface OnNoDisturbListener {
        void onClick();
    }
}
