package com.kalacheng.shopping.seller.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopGoods;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.busshop.model_fun.ShopGoods_updateGoods;
import com.kalacheng.commonview.dialog.SetOrderNumDialog;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.databinding.ItemGoodsManage0Binding;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GoodsManageListAdapter extends RecyclerView.Adapter<GoodsManageListAdapter.Holder> {

    List<ShopGoodsDTO> list;
    Context context;
    int pageStatus = 0;

    OnClickListener listener;

    public GoodsManageListAdapter() {
        this.list = new ArrayList<>();
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public List<ShopGoodsDTO> getList() {
        return list;
    }

    public void setStatus(int status) {
        this.pageStatus = status;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemGoodsManage0Binding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_goods_manage0, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final ShopGoodsDTO goods = list.get(position);
        if (payload == null) {
            holder.binding.setModel(goods);
            holder.binding.executePendingBindings();

            if (goods.channelId == 1) {
                if (goods.favorablePrice > 0) {
                    holder.binding.originalPriceTv.setVisibility(View.VISIBLE);
                    holder.binding.originalPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.binding.originalPriceTv.setText("¥" + DecimalFormatUtils.toTwo(goods.price));
                    holder.binding.priceTv.setText("¥" + goods.favorablePrice);
                } else {
                    holder.binding.originalPriceTv.setVisibility(View.GONE);
                    holder.binding.priceTv.setText("¥" + goods.price);
                }
            } else {
                holder.binding.priceTv.setText("¥" + goods.price);
            }

            ImageLoader.display(list.get(position).goodsPicture.split(",")[0], holder.binding.goodsPic);

            holder.binding.goodsPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    if (goods.channelId == 1) {
                        ARouter.getInstance().build(ARouterPath.GoodsDetailsActivity).withLong(ARouterValueNameConfig.goodsId, goods.goodsId).navigation();
                    } else {
                        if (!TextUtils.isEmpty(goods.productLinks)) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(goods.productLinks);
                            intent.setData(content_url);
                            context.startActivity(intent);
                        } else {
                            ToastUtil.show("非商品链接");
                        }
                    }
                }
            });
            holder.binding.upShelfTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    upAndLower(holder.getBindingAdapterPosition(), (int) goods.goodsId, goods.status == 1 ? 2 : 1);
                }
            });
            holder.binding.editGoodsTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.AddGoodsActivity).withParcelable(ARouterValueNameConfig.shopGoods, goods).navigation();
                }
            });
            holder.binding.delTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    delGoods(holder.getBindingAdapterPosition(), goods.goodsId);
                }
            });

            holder.binding.editOrderTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    setGoodsOrder(goods.goodsId, holder.getBindingAdapterPosition());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        ItemGoodsManage0Binding binding;

        public Holder(@NonNull ItemGoodsManage0Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    private void upAndLower(final int position, long id, final int status) {
        HttpApiShopGoods.upAndLower(id, status, new HttpApiCallBack<ShopGoods>() {
            @Override
            public void onHttpRet(int code, String msg, ShopGoods retModel) {
                if (code == 1) {
                    switch (pageStatus) {
                        case 0:
                            list.get(position).status = retModel.status;
                            notifyItemChanged(position);
                            break;
                        case 1:
                        case 2:
                            list.remove(position);
                            notifyItemRemoved(position);
                            break;
                    }
                }
                ToastUtil.show(msg);
            }
        });

    }

    private void delGoods(final int position, long id) {
        HttpApiShopGoods.delGoods(id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    list.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });

    }

    private void setGoodsOrder(final long id, final int position) {
        SetOrderNumDialog setOrderNumDialog = new SetOrderNumDialog();
        setOrderNumDialog.setOnSetOrderNumListener(new SetOrderNumDialog.OnSetOrderNumListener() {
            @Override
            public void onConfirm(int num) {
                ToastUtil.show("排序号" + num);
                ShopGoods_updateGoods updateGoods = new ShopGoods_updateGoods();
                updateGoods.goodsId = id;
                updateGoods.sort = num;
                updateGoods(updateGoods, position);
            }
        });
        setOrderNumDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "SetOrderNumDialog");

//
//        final Dialog dialog = new Dialog(context, R.style.dialog);
//        dialog.setContentView(R.layout.dialog_set_order);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//        orderEt = dialog.findViewById(R.id.orderEt);
//        dialog.findViewById(com.kalacheng.util.R.id.btn_cancel).setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                }
//        );
//        dialog.findViewById(com.kalacheng.util.R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!TextUtils.isEmpty(orderEt.getText().toString().trim())) {
//
//                    dialog.dismiss();
//                } else {
//                    ToastUtil.show("请设置一个排序号");
//                }
//            }
//        });
//        dialog.show();
    }

    private void updateGoods(final ShopGoods_updateGoods _mdl, final int position) {
        HttpApiShopGoods.updateGoods(_mdl, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    //msg = "商品修改成功";
                    list.get(position).sort = _mdl.sort;
                    notifyItemChanged(position);
                    ToastUtil.show("商品修改成功");
//                    if (listener != null)
//                    listener.updateGoods();
                } else {
                    //msg = "商品修改失败";
                    ToastUtil.show(msg);
                }

            }
        });

    }

    public interface OnClickListener {

        void updateGoods();

    }


}
