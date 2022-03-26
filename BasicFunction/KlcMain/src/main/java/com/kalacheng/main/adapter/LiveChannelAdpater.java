package com.kalacheng.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.TypeLableItemBinding;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveChannelAdpater extends RecyclerView.Adapter<LiveChannelAdpater.ViewHolder> {

    private List<AppLiveChannel> mList = new ArrayList<>();
    private OnBeanCallback<AppLiveChannel> mCallBack;
    public long channelId;
    boolean isEnable = true;
    Context mContext;
    boolean isBuy = false;
    private String[] titles;
    private Map map = new HashMap();

    public LiveChannelAdpater(List<AppLiveChannel> data) {
        mList.clear();
        if (data != null) {
            mList.addAll(data);
        }
    }

    public LiveChannelAdpater(List<AppLiveChannel> data, boolean b) {
        this.isBuy = b;
        mList.clear();
        if (data != null) {
            mList.addAll(data);
        }
    }

    public void setData(List<AppLiveChannel> data) {
        mList.clear();
        if (data != null) {
            mList.addAll(data);
        }
        if (mList.size() > 0) {
            channelId = mList.get(0).id;
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }


    @NonNull
    @Override
    public LiveChannelAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        TypeLableItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.type_lable_item, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveChannelAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.typeLable.setEnabled(isEnable);
        if (isBuy) {
            holder.binding.typeLable.setTextSize(15);
//            holder.binding.typeLable.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.binding.typeLable.setBackgroundResource(0);
            holder.binding.typeLable.setTextColor(ContextCompat.getColorStateList(mContext, R.color.color_type_label_live_buy));
        }
        if (ConfigUtil.getBoolValue(R.bool.tabNewBg)) {
            holder.binding.typeLable.setBackground(mContext.getResources().getDrawable(R.drawable.type_lable_new_background));
            holder.binding.typeLable.setTextColor(ContextCompat.getColorStateList(mContext, R.color.color_type_label_new));
        }
        if (mList.get(position).isChecked == 1) {
            holder.binding.typeLable.setSelected(true);
        } else {
            holder.binding.typeLable.setSelected(false);
        }
        if (null != mCallBack) {
            holder.binding.setCallback(new OnBeanCallback<AppLiveChannel>() {
                @Override
                public void onClick(AppLiveChannel bean) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    for (AppLiveChannel appLiveChannel : mList) {
                        appLiveChannel.isChecked = 0;
                    }
                    bean.isChecked = 1;
                    channelId = bean.id;
                    mCallBack.onClick(bean);
                    notifyDataSetChanged();
                }
            });
        }

        if (mList.get(position).title.contains(":") || mList.get(position).title.contains(",")) {
            holder.binding.typeLable.setText(StringUtil.getChinese(mList.get(position).title));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TypeLableItemBinding binding;

        public ViewHolder(@NonNull TypeLableItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnBeanCallback<AppLiveChannel> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
