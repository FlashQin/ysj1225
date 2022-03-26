package com.kalacheng.commonview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.ItemLiveGiftBinding;
import com.kalacheng.libuser.model.NobLiveGift;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.SpUtil;

import java.util.List;

/**
 * Created by cxf on 2018/10/12.
 */

public class LiveGiftAdapter extends RecyclerView.Adapter<LiveGiftAdapter.ViewHolder> {

    private List<NobLiveGift> mList;
    private int mCurrentPosition = 0;
    private ScaleAnimation mAnimation;
    GiftClickListener giftClickListener;

    public LiveGiftAdapter(List<NobLiveGift> list) {
        mList = list;
        mAnimation = new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimation.setDuration(400);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setRepeatCount(-1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLiveGiftBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_live_gift, parent, false);
        binding.setCallback(new OnItemClickCallback<NobLiveGift>() {
            @Override
            public void onClick(View view, NobLiveGift item) {
                //礼物点击事件
                mCurrentPosition = mList.indexOf(item);
                if (item.checked == 0) {
                    item.checked = 1;
                } else {
                    item.checked = 0;
                }
                notifyItemChanged(mCurrentPosition);
                giftClickListener.onGiftClickListener(mCurrentPosition, item);
            }
        });
        return new ViewHolder(binding);
    }


    public void release() {
        mList.get(mCurrentPosition).checked = 0;
        notifyItemChanged(mCurrentPosition);
        if (mList != null) {
            mList.clear();
        }
        mCurrentPosition = 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        if (mList.get(position).checked == 1)
            holder.binding.icon.startAnimation(mAnimation);
        else
            holder.binding.icon.clearAnimation();

        if (mList.get(position).checked == 0) {
            holder.binding.radioButton.setChecked(false);
        } else {
            holder.binding.radioButton.setChecked(true);
        }

        if (mList.get(position).backid == -1 || mList.get(position).backid == 0) {
            holder.binding.price.setVisibility(View.VISIBLE);
            holder.binding.number.setVisibility(View.GONE);
            holder.binding.price.setText(DecimalFormatUtils.isIntegerDouble(mList.get(position).needcoin) + SpUtil.getInstance().getCoinUnit());


        } else {
            holder.binding.price.setVisibility(View.GONE);
            holder.binding.number.setVisibility(View.VISIBLE);

            if (mList.get(position).type == 4) {
                holder.binding.number.setText("x" + String.valueOf(mList.get(position).number));
            } else {
                holder.binding.number.setText(DecimalFormatUtils.isIntegerDouble(mList.get(position).needcoin) + SpUtil.getInstance().getCoinUnit() + "x" + String.valueOf(mList.get(position).number));
            }
        }


        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLiveGiftBinding binding;

        public ViewHolder(ItemLiveGiftBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public List<NobLiveGift> getList() {
        return mList;
    }

    public void setLastClickListener(GiftClickListener giftClickListener) {
        this.giftClickListener = giftClickListener;
    }

    interface GiftClickListener {
        void onGiftClickListener(int position, NobLiveGift item);
    }
}
