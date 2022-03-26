package com.kalacheng.livecommon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.KeyValueDto;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.FansPrivilegeItmeBinding;

import java.util.List;

public class FansPrivilegeAdpater extends RecyclerView.Adapter<FansPrivilegeAdpater.FansPrivilegeViewHolder> {
    private List<KeyValueDto> mList;
    public FansPrivilegeAdpater(List<KeyValueDto> data){
        this.mList = data;
    }
    @NonNull
    @Override
    public FansPrivilegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FansPrivilegeItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.fans_privilege_itme,parent,false);
        FansPrivilegeViewHolder holder = new FansPrivilegeViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FansPrivilegeViewHolder holder, int position) {
        holder.binding.setViewModel(mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FansPrivilegeViewHolder extends RecyclerView.ViewHolder{
        FansPrivilegeItmeBinding binding ;
        public FansPrivilegeViewHolder(FansPrivilegeItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
