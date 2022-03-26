package com.kalacheng.commonview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.GuardListItmeBinding;
import com.kalacheng.libuser.model.ApiGuardEntityChild;

import java.util.ArrayList;
import java.util.List;

public class GuardListAdpater extends RecyclerView.Adapter<GuardListAdpater.GuardListViewHolder> {
    private Context mContext;
    //type = 1 首页显示 type=2 守护列表显示
    private int type;
    private GuardListCallBack back;
    private List<GuardUserDto> mList = new ArrayList<>();

    public GuardListAdpater(Context mContext) {
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
    public GuardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        GuardListItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.guard_list_itme, null, false);
        GuardListViewHolder holder = new GuardListViewHolder(binding);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuardListViewHolder holder, final int position) {
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

    public class GuardListViewHolder extends RecyclerView.ViewHolder {

        public GuardListItmeBinding binding;

        public GuardListViewHolder(@NonNull GuardListItmeBinding itemView) {
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
