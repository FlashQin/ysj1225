package com.kalacheng.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppHotSort;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.ItemSquareSubjectBinding;
import com.kalacheng.util.listener.OnBeanCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 * 方形img
 */

public class SquareImgAdapter extends RecyclerView.Adapter<SquareImgAdapter.ViewHolder> {

    private List<AppHotSort> mList = new ArrayList<>();
    int widthDp;
    int hightDp;
    private OnBeanCallback<AppHotSort> mCallBack;

    public SquareImgAdapter(List<AppHotSort> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void upDataList(List<AppHotSort> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSquareSubjectBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_square_subject,
                        parent, false);
        if (null != mCallBack)
            binding.setCallback(mCallBack);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemSquareSubjectBinding binding;

        public ViewHolder(ItemSquareSubjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setImgWidthHight(int widthDp, int hightDp) {
        this.widthDp = widthDp;
        this.hightDp = hightDp;
    }

    public void setOnItemClickListener(OnBeanCallback<AppHotSort> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
