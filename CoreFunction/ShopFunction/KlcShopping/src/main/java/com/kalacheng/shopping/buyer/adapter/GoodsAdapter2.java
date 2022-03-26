package com.kalacheng.shopping.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.activty.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class GoodsAdapter2 extends RecyclerView.Adapter<GoodsAdapter2.Holder> {

    List<ShopGoodsDTO> list = new ArrayList<>();


    Context mContext;

    public void setList(List<ShopGoodsDTO> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_goods2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        Object obj = payloads.size() > 0 ? payloads.get(0) : null;
        final ShopGoodsDTO dto = list.get(position);
        if (obj == null) {
            String[] picture = dto.goodsPicture.split(",");
            ImageLoader.display(picture.length > 0 ? picture[0] : "", holder.goodIv);
            holder.goodsNameTv.setText(dto.goodsName);
            if (dto.channelId == 1) {
                if (dto.favorablePrice > 0) {
                    holder.goodsPriceTv.setText("¥ " + DecimalFormatUtils.toTwo(dto.favorablePrice));
                } else {
                    holder.goodsPriceTv.setText("¥ " + DecimalFormatUtils.toTwo(dto.price));
                }
            } else {
                holder.goodsPriceTv.setText("¥ " + DecimalFormatUtils.toTwo(dto.price));
            }
            holder.countTv.setText("已售 " + dto.soldNum + " 件");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    if (dto.channelId == 1) {
                        BaseApplication.containsActivity("GoodsDetailsActivity");
                        ARouter.getInstance().build(ARouterPath.GoodsDetailsActivity).withLong(ARouterValueNameConfig.goodsId, dto.goodsId).navigation();
                    } else {
                        if (!TextUtils.isEmpty(dto.productLinks)) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(dto.productLinks);
                            intent.setData(content_url);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return  null != list? list.size() : 0;
    }

    static class Holder extends RecyclerView.ViewHolder {
        RoundedImageView goodIv;
        TextView goodsNameTv;
        TextView goodsPriceTv;
        TextView countTv;


        public Holder(@NonNull View itemView) {
            super(itemView);
            goodIv = itemView.findViewById(R.id.goodIv);
            goodsNameTv = itemView.findViewById(R.id.goodsNameTv);
            goodsPriceTv = itemView.findViewById(R.id.goodsPriceTv);
            countTv = itemView.findViewById(R.id.countTv);
        }
    }

}
