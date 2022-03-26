package com.kalacheng.commonview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.ItemGuardBinding;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.util.utils.WordUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 *
 *  守护Adapter
 */

public class GuardAdapter extends RecyclerView.Adapter<GuardAdapter.ViewHolder> {

    private List<GuardUserDto> mList = new ArrayList<>();
    private GuardListener guardListener;

    public GuardAdapter() {
    }

    //加载更多
    public void setLoadData(List<GuardUserDto> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新数据
    public void setRefreshData(List<GuardUserDto> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGuardBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_guard,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.tvTime1.setText("第" + mList.get(position).guardDay + "天守护TA");
        if (mList.get(position).leftDay <= 0) {
            holder.binding.rlGuard1.setBackgroundResource(R.drawable.bg_homepage_guard_gray);
            if (mList.get(position).userId == HttpClient.getUid()) {
                holder.binding.tvTime2.setText("去续期");
            } else {
                holder.binding.tvTime2.setText("已到期");
            }
        } else {
            holder.binding.rlGuard1.setBackgroundResource(R.drawable.bg_homepage_guard);
            holder.binding.tvTime2.setText(WordUtil.strToSpanned("剩余" + WordUtil.strAddColor(mList.get(position).leftDay + "", "#4557f1") + "天"));
        }
        holder.binding.rlGuard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mList.get(position).userId == HttpClient.getUid() && null != guardListener) {
                    guardListener.onClick(mList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemGuardBinding binding;

        public ViewHolder(ItemGuardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setGuardCallBack(GuardListener guardListener){
        this.guardListener = guardListener;
    }

    public interface GuardListener {
        void onClick(GuardUserDto guardUserDto);
    }

}
