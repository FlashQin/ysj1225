package com.kalacheng.center.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.center.R;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

public class TabMeBottomAdapter extends RecyclerView.Adapter<TabMeBottomAdapter.ViewHolder> {
    private Context mContext;
    private List<SimpleImageUrlTextBean> mList = new ArrayList<>();
    private int mOrderNum = 0;//订单数量
    private int mShopOrderNum = 0;//官方小店的订单数量
    private boolean mNoDisturb = false;//免打扰
    private int isOpenYoung = 1;
    private int sex;// 1男 2女

    private OnItemClickCallback<SimpleImageUrlTextBean> onItemClickCallback;
    private OnNoDisturbListener onNoDisturbListener;

    public TabMeBottomAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<SimpleImageUrlTextBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setSex(int sex) {
        this.sex = sex;
        notifyDataSetChanged();
    }

    public void setOrderNum(int orderNum) {
        if (this.mOrderNum != orderNum) {
            this.mOrderNum = orderNum;
            notifyDataSetChanged();
        }
    }

    public void setShopOrderNum(int shopOrderNum) {
        if (this.mShopOrderNum != shopOrderNum) {
            this.mShopOrderNum = shopOrderNum;
            notifyDataSetChanged();
        }
    }

    public void setNoDisturb(boolean noDisturb) {
        this.mNoDisturb = noDisturb;
        notifyDataSetChanged();
    }

    //青少年模式
    public void setisOpenYoung(int isOpenYoung) {
        this.isOpenYoung = isOpenYoung;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_me_bottom, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SimpleImageUrlTextBean bean = mList.get(position);
        holder.ivIcon.setImageResource(bean.src);
        holder.tvName.setText(bean.text);
        if (bean.src == R.mipmap.icon_no_disturb) {//免打扰
            holder.ivNoDisturb.setVisibility(View.VISIBLE);
            holder.ivRight.setVisibility(View.GONE);
            if (mNoDisturb) {
                holder.ivNoDisturb.setImageResource(R.mipmap.icon_switch_open);
            } else {
                holder.ivNoDisturb.setImageResource(R.mipmap.icon_switch_close);
            }
        } else {
            holder.ivNoDisturb.setVisibility(View.GONE);
            holder.ivRight.setVisibility(View.VISIBLE);
        }
        if (bean.src == R.mipmap.icon_me_order && mOrderNum > 0) {//购物订单
            holder.tvNum.setText(mOrderNum + "");
            holder.tvNum.setVisibility(View.VISIBLE);
        } else if (bean.src == R.mipmap.icon_me_official_shop && mShopOrderNum > 0) {//官方小店
            holder.tvNum.setText(mShopOrderNum + "");
            holder.tvNum.setVisibility(View.VISIBLE);
        } else {
            holder.tvNum.setVisibility(View.GONE);
        }

        // 是否动态展示 主播中心
        if (bean.src == R.mipmap.icon_me_anchor_center_b) {
            if (ConfigUtil.getIntValue(com.kalacheng.centercommon.R.integer.isVisibleAnchorCenter) == 0) {
                holder.viewDivider.setVisibility(View.VISIBLE);
                holder.layoutItem.setVisibility(View.VISIBLE);
            } else if (ConfigUtil.getIntValue(com.kalacheng.centercommon.R.integer.isVisibleAnchorCenter) == 1) {
                holder.viewDivider.setVisibility(sex == 2 ? View.VISIBLE : View.GONE);
                holder.layoutItem.setVisibility(sex == 2 ? View.VISIBLE : View.GONE);
            } else {
                holder.viewDivider.setVisibility(View.GONE);
                holder.layoutItem.setVisibility(View.GONE);
            }
        } else {
            if (position == 0) {
                holder.viewDivider.setVisibility(View.GONE);
            } else {
                holder.viewDivider.setVisibility(View.VISIBLE);
            }
            holder.layoutItem.setVisibility(View.VISIBLE);
        }

        if (bean.src == R.mipmap.icon_me_anchor_center) {
            if (isOpenYoung == 1) {
                holder.state.setVisibility(View.VISIBLE);
                holder.state.setText("已开启");
            } else {
                holder.state.setVisibility(View.VISIBLE);
                holder.state.setText("未开启");
            }
        } else {
            holder.state.setVisibility(View.GONE);
            holder.state.setText("");
        }

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
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
        holder.ivNoDisturb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (onNoDisturbListener != null) {
                    onNoDisturbListener.onClick();
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
