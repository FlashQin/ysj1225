package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.ApiUsersLiveManager;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.ItemLiveAdminListBinding;

import java.util.ArrayList;
import java.util.List;

public class LiveAdminListAdpater extends RecyclerView.Adapter<LiveAdminListAdpater.LiveAdminListViewHolder> {
    private List<ApiUsersLiveManager> mList = new ArrayList<>();
    private Context mContext;
    private LiveAdminListCallBack callBack;

    public LiveAdminListAdpater(Context mContext){
        this.mContext=mContext;
    }

    public void getData(List<ApiUsersLiveManager> data){
        this.mList=data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiveAdminListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemLiveAdminListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_live_admin_list,parent,false);
        LiveAdminListViewHolder holder = new LiveAdminListViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveAdminListViewHolder holder, final int position) {
        holder.getData(mList.get(position));
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LiveAdminListViewHolder extends RecyclerView.ViewHolder {
        ItemLiveAdminListBinding binding;
        public LiveAdminListViewHolder(ItemLiveAdminListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            this.binding.executePendingBindings();
        }

        public void getData(ApiUsersLiveManager bean){
            binding.setViewModel(bean);
        }
    }
    public void setLiveAdminListCallBack(LiveAdminListCallBack back){
        this.callBack = back;
    }
    public interface LiveAdminListCallBack{
        public void onClick(int poistion);
    }
}
