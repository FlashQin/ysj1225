package com.kalacheng.live.component.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.ApiUsersLiveWish;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.live.R;
import com.kalacheng.live.databinding.ItemWishBillListBinding;
import com.kalacheng.util.view.ItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class LiveWishBillListAdapter extends RecyclerView.Adapter<LiveWishBillListAdapter.RecycleHolder>{
    ListClickCallback callback;
    List<ApiUsersLiveWish> coinList=new ArrayList<>();

    private Context mContext;
    public LiveWishBillListAdapter(Context mContext){
        this.mContext=mContext;
    }

    public interface ListClickCallback {
//        void onClick(LiveUserWish coinItem);
    }

    public void setWishList(final List<ApiUsersLiveWish> list) {
        coinList.clear();
        coinList.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWishBillListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_wish_bill_list,
                parent, false);
        binding.setCallback(callback);
        return new RecycleHolder(binding);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull RecycleHolder holder, int position) {
        holder.binding.setLiveUser(coinList.get(position));
        holder.binding.tvNum.setText("心愿"+(position+1));
        holder.binding.tvDoneCount.setText(WordUtil.strToSpanned(WordUtil.strAddColor(coinList.get(position).sendNum+"", "#333333") + "/" +
                coinList.get(position).num));
        holder.binding.executePendingBindings();

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        holder.binding.rvWishBillSendUsers.setLayoutManager(manager);
        holder.binding.rvWishBillSendUsers.addItemDecoration(new ItemDecoration(mContext,0x00000000,10,0));
        LiveWishBillUserListAdpater adpater = new LiveWishBillUserListAdpater(mContext,coinList.get(position).wishUserList);
        holder.binding.rvWishBillSendUsers.setAdapter(adpater);
    }

    @Override
    public int getItemCount() {
        return coinList == null ?0: coinList.size() ;
    }

    public class RecycleHolder extends RecyclerView.ViewHolder {
        ItemWishBillListBinding binding;
        public RecycleHolder(ItemWishBillListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
