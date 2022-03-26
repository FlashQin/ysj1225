package com.kalacheng.shopping.buyer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.shopping.R;
import com.kalacheng.shopping.buyer.bean.ShopCarBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SubmitOrderAdapter extends RecyclerView.Adapter<SubmitOrderAdapter.Holder> {

    ArrayList<ShopCarBean> list;

    public SubmitOrderAdapter() {
        this.list = new ArrayList<>();
    }

    public void setShopCarDTOS(List<ShopCarBean> shopCarDTOS) {
        this.list.addAll(shopCarDTOS);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 2) {
            return new EndHolder(inflater.inflate(R.layout.item_order_end_0, parent, false));
        } else if (viewType == 1) {
            return new GoodsHolder(inflater.inflate(R.layout.item_order_goods, parent, false));
        } else {
            return new TitleHolder(inflater.inflate(R.layout.item_order_title, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ShopCarBean shopCarBean = list.get(position);
        holder.setData(shopCarBean, position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).viewType;
    }

    static class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        protected void setData( ShopCarBean shopCarBean, int position) {

        }

    }

    static class TitleHolder extends Holder {
        RoundedImageView sellserLogoIv;
        TextView sellserNameTv;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            sellserLogoIv = itemView.findViewById(R.id.sellserLogoIv);
            sellserNameTv = itemView.findViewById(R.id.sellserNameTv);
        }

        @Override
        protected void setData(final  ShopCarBean shopCarBean, int position) {
            super.setData(shopCarBean, position);
            ImageLoader.display(shopCarBean.businessLogo, sellserLogoIv);
            sellserNameTv.setText(shopCarBean.businessName);
        }
    }

    static class GoodsHolder extends Holder {
        RoundedImageView goodsPicIv;
        TextView goodsNameTv;
        TextView skuNameTv;
        TextView goodsPriceTv;
        TextView countTv;

        public GoodsHolder(@NonNull View itemView) {
            super(itemView);

            goodsPicIv = itemView.findViewById(R.id.goodsPicIv);
            goodsNameTv = itemView.findViewById(R.id.goodsNameTv);
            skuNameTv = itemView.findViewById(R.id.skuNameTv);
            goodsPriceTv = itemView.findViewById(R.id.goodsPriceTv);
            countTv = itemView.findViewById(R.id.countTv);
        }

        @Override
        protected void setData(final  ShopCarBean shopCarBean, int position) {
            super.setData(shopCarBean, position);
            ImageLoader.display(shopCarBean.goodsPicture, goodsPicIv,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
            goodsNameTv.setText(shopCarBean.goodsName);
            skuNameTv.setText(shopCarBean.skuName);
            goodsPriceTv.setText("¥ " + DecimalFormatUtils.toTwo(shopCarBean.goodsPrice));
            countTv.setText("x"+shopCarBean.goodsNum);
        }
    }

    static class EndHolder extends Holder {
        TextView goodsPriceTv;
        TextView countTv;

        public EndHolder(@NonNull View itemView) {
            super(itemView);

            goodsPriceTv = itemView.findViewById(R.id.goodsPriceTv);
            countTv = itemView.findViewById(R.id.countTv);
        }

        @Override
        protected void setData(final  ShopCarBean shopCarBean, int position) {
            super.setData(shopCarBean, position);
            countTv.setText("共 "+shopCarBean.count+" 件商品   合计");
            goodsPriceTv.setText("¥ "+DecimalFormatUtils.toTwo(shopCarBean.total));
        }
    }


}
