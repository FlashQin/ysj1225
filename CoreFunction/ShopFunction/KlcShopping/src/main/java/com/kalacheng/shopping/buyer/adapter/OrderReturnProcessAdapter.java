package com.kalacheng.shopping.buyer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ShopOrderReturnProcessDTO;
import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.DpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 退款节点样式
 */
public class OrderReturnProcessAdapter extends RecyclerView.Adapter<OrderReturnProcessAdapter.ViewHolder> {
    private Context mContext;
    private List<ShopOrderReturnProcessDTO> mList = new ArrayList<>();
    private int mWidth;

    public OrderReturnProcessAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ShopOrderReturnProcessDTO> list) {
        mList.clear();
        if (list != null) {
            mList.addAll(list);
        }
        if (mList.size() > 0) {
            mWidth = DpUtil.getScreenWidth() / mList.size();
        } else {
            mWidth = DpUtil.getScreenWidth();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_return_process, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.layoutProcess.getLayoutParams();
        layoutParams.width = mWidth;
        holder.layoutProcess.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParamName = (LinearLayout.LayoutParams) holder.tvNodeName.getLayoutParams();
        layoutParamName.width = mWidth;
        holder.tvNodeName.setLayoutParams(layoutParamName);

        holder.tvNodeName.setText(mList.get(position).nodeName);
        if (mList.get(position).operateStatus == 0) {//待操作
            holder.tvUpTime.setText("");
        } else {//已操作
            if (mList.get(position).upTime != null) {
                DateUtil dateUtil = new DateUtil(mList.get(position).upTime);
                holder.tvUpTime.setText(dateUtil.getDateToFormat("yyyy-MM-dd"));
            } else {
                holder.tvUpTime.setText("");
            }
        }

        if (position == 0) {
            holder.viewLeft.setVisibility(View.INVISIBLE);
            holder.viewRight.setVisibility(View.VISIBLE);
            holder.tvNodeName.setTextColor(Color.parseColor("#C12A2A"));
            if (mList.size() > 1) {
                if (mList.get(1).operateStatus == 0) {//待操作
                    holder.ivCenter.setImageResource(R.mipmap.icon_shop_refund_progress_ing);
                    holder.viewRight.setBackgroundColor(Color.parseColor("#ffffff"));
                } else {
                    holder.ivCenter.setImageResource(R.mipmap.icon_shop_refund_progress_done);
                    holder.viewRight.setBackgroundColor(Color.parseColor("#C12A2A"));
                }
            } else {
                holder.ivCenter.setImageResource(R.mipmap.icon_shop_refund_progress_done);
                holder.viewRight.setBackgroundColor(Color.parseColor("#C12A2A"));
            }
        } else if (position == mList.size() - 1) {
            holder.viewLeft.setVisibility(View.VISIBLE);
            holder.viewRight.setVisibility(View.INVISIBLE);
            if (mList.get(position).operateStatus == 0) {//待操作
                holder.ivCenter.setImageResource(R.mipmap.icon_shop_refund_progress_future);
                holder.viewLeft.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.tvNodeName.setTextColor(Color.parseColor("#ffffff"));
            } else {
                holder.ivCenter.setImageResource(R.mipmap.icon_shop_refund_progress_ing);
                holder.viewLeft.setBackgroundColor(Color.parseColor("#C12A2A"));
                holder.tvNodeName.setTextColor(Color.parseColor("#C12A2A"));
            }
        } else {
            holder.viewLeft.setVisibility(View.VISIBLE);
            holder.viewRight.setVisibility(View.VISIBLE);
            if (mList.get(position).operateStatus == 0) {//待操作
                holder.ivCenter.setImageResource(R.mipmap.icon_shop_refund_progress_future);
                holder.viewLeft.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.viewRight.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.tvNodeName.setTextColor(Color.parseColor("#ffffff"));
            } else {
                holder.viewLeft.setBackgroundColor(Color.parseColor("#C12A2A"));
                holder.tvNodeName.setTextColor(Color.parseColor("#C12A2A"));
                if (mList.size() > position + 1) {
                    if (mList.get(position + 1).operateStatus == 0) {//待操作
                        holder.ivCenter.setImageResource(R.mipmap.icon_shop_refund_progress_ing);
                        holder.viewRight.setBackgroundColor(Color.parseColor("#ffffff"));
                    } else {
                        holder.ivCenter.setImageResource(R.mipmap.icon_shop_refund_progress_done);
                        holder.viewRight.setBackgroundColor(Color.parseColor("#C12A2A"));
                    }
                } else {
                    holder.ivCenter.setImageResource(R.mipmap.icon_shop_refund_progress_done);
                    holder.viewRight.setBackgroundColor(Color.parseColor("#C12A2A"));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNodeName;
        TextView tvUpTime;
        FrameLayout layoutProcess;
        View viewLeft;
        View viewRight;
        ImageView ivCenter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNodeName = itemView.findViewById(R.id.tvNodeName);
            tvUpTime = itemView.findViewById(R.id.tvUpTime);
            layoutProcess = itemView.findViewById(R.id.layoutProcess);
            viewLeft = itemView.findViewById(R.id.viewLeft);
            viewRight = itemView.findViewById(R.id.viewRight);
            ivCenter = itemView.findViewById(R.id.ivCenter);
        }
    }
}
