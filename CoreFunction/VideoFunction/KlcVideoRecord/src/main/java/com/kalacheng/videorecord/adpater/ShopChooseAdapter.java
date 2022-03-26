package com.kalacheng.videorecord.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.videorecord.R;
import com.kalacheng.videorecord.databinding.ItemShopChooseLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class ShopChooseAdapter extends RecyclerView.Adapter<ShopChooseAdapter.ViewHolder> {

    private deleteCallBack deleteCallBack;
    private List<ShopGoodsDTO> mList = new ArrayList<>();

    public ShopChooseAdapter() {
    }

    public void setData(List<ShopGoodsDTO> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShopChooseLayoutBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_shop_choose_layout,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.binding.setBean(mList.get(position));
//        holder.binding.executePendingBindings();

        if (mList.get(position).goodsPicture.length() > 0) {
            String[] pic = mList.get(position).goodsPicture.split(",");
            ImageLoader.display(pic[0], holder.binding.ivPic, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        }
        holder.binding.tvName.setText(mList.get(position).goodsName);
        if (mList.get(position).favorablePrice > 0) {
            holder.binding.tvPrice.setText("¥" + DecimalFormatUtils.toTwo(mList.get(position).favorablePrice));
        } else {
            holder.binding.tvPrice.setText("¥" + DecimalFormatUtils.toTwo(mList.get(position).price));
        }
        holder.binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != deleteCallBack) {
                    deleteCallBack.onDelete();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemShopChooseLayoutBinding binding;

        public ViewHolder(ItemShopChooseLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setListener(deleteCallBack deleteCallBack) {
        this.deleteCallBack = deleteCallBack;
    }

    // 选择商品的回调
    public interface deleteCallBack {
        void onDelete();
    }

}
