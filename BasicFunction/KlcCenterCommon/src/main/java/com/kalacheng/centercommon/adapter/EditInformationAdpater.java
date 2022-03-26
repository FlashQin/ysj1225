package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.bean.InformationBean;
import com.kalacheng.centercommon.databinding.ItemEditInformationBinding;
import com.kalacheng.util.listener.OnBeanCallback;

import java.util.ArrayList;
import java.util.List;

public class EditInformationAdpater extends RecyclerView.Adapter<EditInformationAdpater.ViewHolder> {

    private List<InformationBean> mList = new ArrayList<>();

    public EditInformationAdpater(List<InformationBean> data) {
        this.mList = data;
    }

    private OnBeanCallback<InformationBean> mCallBack;

    public List<InformationBean> getData() {
        return mList;
    }

    @NonNull
    @Override
    public EditInformationAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEditInformationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_edit_information, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditInformationAdpater.ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemEditInformationBinding binding;

        public ViewHolder(@NonNull ItemEditInformationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCallback(mCallBack);
        }
    }

    public void setOnItemClickListener(OnBeanCallback<InformationBean> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
