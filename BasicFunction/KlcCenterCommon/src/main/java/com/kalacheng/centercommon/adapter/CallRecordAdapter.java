package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemCallRecordBinding;
import com.kalacheng.libuser.model.CallRecordDto;
import com.kalacheng.util.utils.WordUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class CallRecordAdapter extends RecyclerView.Adapter<CallRecordAdapter.ViewHolder> {

    private List<CallRecordDto> mList = new ArrayList<>();

    public CallRecordAdapter() {
    }

    //加载更多
    public void setLoadData(List<CallRecordDto> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新数据
    public void setRefreshData(List<CallRecordDto> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCallRecordBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_call_record,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.tvNum.setText(WordUtil.strToSpanned("第" + WordUtil.strAddColor(mList.get(position).num + "", "#FF1A1A") + "次聊天"));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCallRecordBinding binding;

        public ViewHolder(ItemCallRecordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
