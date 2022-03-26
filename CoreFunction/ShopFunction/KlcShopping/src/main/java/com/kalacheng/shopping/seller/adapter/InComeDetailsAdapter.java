package com.kalacheng.shopping.seller.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.shopping.R;
import com.kalacheng.shopping.databinding.ItemIncomeDetailsBinding;

public class InComeDetailsAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemIncomeDetailsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_income_details,parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class Holder extends RecyclerView.ViewHolder{
        ItemIncomeDetailsBinding binding;

        public Holder(@NonNull ItemIncomeDetailsBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }


}
