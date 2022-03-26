package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.ApiKick;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.KickListItmeBinding;

import java.util.ArrayList;
import java.util.List;

public class KickListAdpater extends RecyclerView.Adapter<KickListAdpater.KickListViewHolder> {
    private List<ApiKick> mList = new ArrayList<>();
    private Context mContext;
    private KickListViewCallBack back;

    public KickListAdpater(Context mContext){
        this.mContext=mContext;
    }

    public void getData(List<ApiKick> data){
        this.mList=data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KickListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        KickListItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.kick_list_itme,parent,false);
        KickListViewHolder holder = new KickListViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KickListViewHolder holder, final int position) {
        holder.getData(mList.get(position));
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class KickListViewHolder extends RecyclerView.ViewHolder {
        KickListItmeBinding binding;
        public KickListViewHolder(KickListItmeBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            this.binding.executePendingBindings();
        }

        public void getData(ApiKick bean){
            binding.setViewModel(bean);
        }
    }

    public void setKickListViewCallBack(KickListViewCallBack callBack){
        this.back = callBack;
    }
    public interface KickListViewCallBack{
        public void onClick(int poistion);
    }
}
