package com.kalacheng.commonview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.ItemFansGroupRankBinding;
import com.kalacheng.libuser.model.RanksDto;

import java.util.ArrayList;
import java.util.List;

public class FansGroupRankAdpater extends RecyclerView.Adapter {
    private Context mContext;
    private List<RanksDto> mList = new ArrayList<>();

    public FansGroupRankAdpater(Context mContext) {
        this.mContext = mContext;
    }

    //加载更多
    public void setLoadData(List<RanksDto> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新数据
    public void setRefreshData(List<RanksDto> data) {
        if (null != data){
            this.mList.clear();
            mList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFansGroupRankBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_fans_group_rank, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).setData(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFansGroupRankBinding binding;

        public ViewHolder(@NonNull ItemFansGroupRankBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final RanksDto bean, int position) {
            if (mList.size() == 1) {
                binding.viewItemDivider.setVisibility(View.GONE);
            } else {
                if (position == 0) {
                    binding.viewItemDivider.setVisibility(View.GONE);
                } else if (position == (mList.size() - 1)) {
                    binding.viewItemDivider.setVisibility(View.VISIBLE);
                } else {
                    binding.viewItemDivider.setVisibility(View.VISIBLE);
                    binding.layoutListItemBg.setBackgroundColor(Color.WHITE);
                }
            }


            binding.setBean(bean);
            binding.executePendingBindings();

            binding.layoutListItemBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.userId).navigation();
                }
            });


        }
    }
}
