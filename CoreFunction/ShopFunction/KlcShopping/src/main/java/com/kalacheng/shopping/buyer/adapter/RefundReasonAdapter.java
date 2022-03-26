package com.kalacheng.shopping.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ApplyRefundReasonDTO;
import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.TextViewUtil;

import java.util.ArrayList;
import java.util.List;

public class RefundReasonAdapter extends RecyclerView.Adapter<RefundReasonAdapter.ViewHolder> {
    private Context mContext;
    private List<ApplyRefundReasonDTO> mList = new ArrayList<>();
    private int mSelectIndex = -1;
    private OnReasonListener onReasonListener;

    public RefundReasonAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ApplyRefundReasonDTO> list) {
        mList.clear();
        if (list != null) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取退款原因ID
     */
    public long getReasonId() {
        if (mSelectIndex == -1) {
            return -1;
        } else if (mList.size() > mSelectIndex) {
            return mList.get(mSelectIndex).id;
        } else {
            return -1;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_refund_reason, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvRefundReason.setText(mList.get(position).reason);
        if (position == mSelectIndex) {
            TextViewUtil.setDrawableRight(holder.tvRefundReason, R.mipmap.icon_refund_reason_select);
        } else {
            TextViewUtil.setDrawableNull(holder.tvRefundReason);
        }

        holder.tvRefundReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                mSelectIndex = position;
                notifyDataSetChanged();
                if (onReasonListener != null) {
                    onReasonListener.onSelect(mList.get(position).reason);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRefundReason;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRefundReason = itemView.findViewById(R.id.tvRefundReason);
        }
    }

    public void setOnReasonListener(OnReasonListener onReasonListener) {
        this.onReasonListener = onReasonListener;
    }

    public interface OnReasonListener {
        void onSelect(String reason);
    }
}
