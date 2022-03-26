package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.ApiShutUp;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.ItemLiveGapListBinding;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class LiveGapListAdapter extends RecyclerView.Adapter<LiveGapListAdapter.LiveGapListViewHolder> {

    private List<ApiShutUp> mList = new ArrayList<>();
    private Context mContext;
    private LiveGapListCallBack callBack;
    public LiveGapListAdapter(Context mContext){
        this.mContext=mContext;
    }

    public void getData(List<ApiShutUp> data){
        this.mList=data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiveGapListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemLiveGapListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_live_gap_list,parent,false);
        LiveGapListViewHolder holder = new LiveGapListViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveGapListViewHolder holder, final int position) {
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

    public class LiveGapListViewHolder extends RecyclerView.ViewHolder {
        ItemLiveGapListBinding binding;
        public LiveGapListViewHolder(ItemLiveGapListBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            this.binding.executePendingBindings();
        }

        public void getData(ApiShutUp bean){
            binding.setViewModel(bean);

            ImageLoader.display(bean.avatar,binding.avatar);
        }
    }

    public void setLiveGapListCallBack(LiveGapListCallBack back){
        this.callBack =back;
    }
    public interface LiveGapListCallBack{
        public void onClick(int poistion);
    }
}
