package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemAppGradePrivilegeBinding;
import com.kalacheng.libuser.model.AppGradePrivilege;

import java.util.ArrayList;
import java.util.List;

public class AppGradePrivilegeAdapter extends RecyclerView.Adapter<AppGradePrivilegeAdapter.ViewHolder> {

    private List<AppGradePrivilege> mList = new ArrayList<>();

    public AppGradePrivilegeAdapter(List<AppGradePrivilege> list) {
        mList.clear();
        mList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAppGradePrivilegeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_app_grade_privilege, parent, false);
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
        ItemAppGradePrivilegeBinding binding;

        public ViewHolder(ItemAppGradePrivilegeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
