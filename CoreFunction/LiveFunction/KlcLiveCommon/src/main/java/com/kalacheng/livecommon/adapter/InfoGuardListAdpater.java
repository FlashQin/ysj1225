package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.libuser.model.ApiGuardEntityChild;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.InfoGuardListItmeBinding;

import java.util.ArrayList;
import java.util.List;

public class InfoGuardListAdpater extends RecyclerView.Adapter<InfoGuardListAdpater.InfoGuardListViewHolder> {
    private Context mContext;
    //type = 1 首页显示 type=2 守护列表显示
    private int type;
    private GuardListCallBack back;
    private List<GuardUserDto> mList = new ArrayList<>();

    public InfoGuardListAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getGuardList(List<GuardUserDto> data, int type) {
        this.type = type;
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InfoGuardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        InfoGuardListItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.info_guard_list_itme, null, false);
        InfoGuardListViewHolder holder = new InfoGuardListViewHolder(binding);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InfoGuardListViewHolder holder, final int position) {
        holder.binding.setViewModel(mList.get(position));
        holder.binding.executePendingBindings();

        holder.binding.guardlistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mList.size() > 2) {
            if (type == 1) {
                return 2;
            } else {
                return mList.size();
            }

        } else {
            return mList.size();
        }

    }

    public class InfoGuardListViewHolder extends RecyclerView.ViewHolder {

        public InfoGuardListItmeBinding binding;

        public InfoGuardListViewHolder(@NonNull InfoGuardListItmeBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }

        public void setData(ApiGuardEntityChild bean) {

        }
    }


    public void setGuardListCallBack(GuardListCallBack callBack) {
        this.back = callBack;
    }

    public interface GuardListCallBack {
        public void onClick(int poistion);
    }
}
