package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.AppUserIncomeRankingDto;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemInvitationIncomeBinding;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class InvitationSortAdapter extends RecyclerView.Adapter<InvitationSortAdapter.InvitationSortViewHolder> {
    private List<AppUserIncomeRankingDto> mList = new ArrayList<>();

    public InvitationSortAdapter() {
    }

    public void setData(List<AppUserIncomeRankingDto> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void loadData(List<AppUserIncomeRankingDto> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InvitationSortAdapter.InvitationSortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInvitationIncomeBinding itemInvitationIncomeBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_invitation_income,
                        parent, false);
        return new InvitationSortViewHolder(itemInvitationIncomeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitationSortAdapter.InvitationSortViewHolder holder, int position) {
        holder.setLabel(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class InvitationSortViewHolder extends RecyclerView.ViewHolder {
        ItemInvitationIncomeBinding binding;

        public InvitationSortViewHolder(@NonNull ItemInvitationIncomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setLabel(int position) {
            binding.setBean(mList.get(position));
            binding.tvSortIndex.setText((int) mList.get(position).serialNumber + "");
            binding.tvTotalAmount.setText("获得" + String.format("%.2f", mList.get(position).totalAmount) + "元");
            ImageLoader.display(mList.get(position).avatar, binding.ivAvatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        }
    }
}
