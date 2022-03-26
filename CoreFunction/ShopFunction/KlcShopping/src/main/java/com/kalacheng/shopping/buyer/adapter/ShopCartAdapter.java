package com.kalacheng.shopping.buyer.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopCar;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopAttrAndComposeDTO;
import com.kalacheng.busshop.model.ShopAttrCompose;
import com.kalacheng.busshop.model.ShopCar;
import com.kalacheng.busshop.model.ShopCarDTO;
import com.kalacheng.busshop.model.ShopGoods;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.buyer.activity.ShoppingCartActivity;
import com.kalacheng.shopping.buyer.fragment.SelectAttrFragment;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.we.swipe.helper.WeSwipeHelper;

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.Holder> {

    Context context;
    ShoppingCartActivity activity;
    List<ShopCarDTO> list;
    OnTodalListener onTodalListener;
    double todal = 0;
    int size = 0;

    public ShopCartAdapter(ShoppingCartActivity activity) {
        this.activity = activity;
        this.list = new ArrayList<>();
    }

    public List<ShopCarDTO> getList() {
        return list;
    }

    public void setShopCarDTOS(List<ShopCarDTO> shopCarDTOS) {
        this.list.clear();
        if (shopCarDTOS != null) {
            this.list.addAll(shopCarDTOS);
        }
        todal();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == 1) {
            return new GoodsHolder(inflater.inflate(R.layout.item_cart_goods, parent, false));
        } else {
            return new TitleHolder(inflater.inflate(R.layout.item_cart_title, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ShopCarDTO shopCarDTO = list.get(position);
        holder.setData(shopCarDTO, holder.getBindingAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).viewType;
    }

    public void setOnTodalListener(OnTodalListener onTodalListener) {
        this.onTodalListener = onTodalListener;
    }

    private void isAllChecked() {
        int isAllChecked = 0;
        int isNoAllChecked = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).checked == 1) {
                isAllChecked++;
            } else {
                isNoAllChecked++;
            }
        }

        if (isAllChecked == list.size()) {
            if (onTodalListener != null) {
                onTodalListener.isAllCheckedListener(true);
            }
        }
        if (isNoAllChecked == list.size()) {
            if (onTodalListener != null) {
                onTodalListener.isAllCheckedListener(false);
            }
        }
    }

    private void todal() {
        todal = 0;
        size = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).viewType == 1 && list.get(i).checked == 1) {
                todal += list.get(i).shopCarList.get(0).goodsPrice * list.get(i).shopCarList.get(0).goodsNum;
                size++;
            }
        }
        if (onTodalListener != null) {
            onTodalListener.onTodalListener(todal, size);
        }
        isAllChecked();
    }

    public void shopCartAll(int checked) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).checked = checked;
        }
        notifyData();
    }


    private void shopAll(long id, int checked) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).businessId == id) {
                list.get(i).checked = checked;
            }
        }
        notifyData();
    }

    private void goodsStatue(int position, int checked) {

        boolean isAll = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(position).businessId == list.get(i).businessId && list.get(i).viewType == 1) {
                if (list.get(i).checked != checked) {
                    isAll = false;
                    break;
                }
            }
        }
        if (isAll) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(position).businessId == list.get(i).businessId && list.get(i).viewType == 0) {
                    list.get(i).checked = checked;
                    notifyData(i);
                }
            }
        } else {
            todal();
        }
    }

    private void notifyData() {
        todal();
        notifyDataSetChanged();
    }

    private void notifyData(int position) {
        todal();
        notifyItemChanged(position);
    }

//    private void notifyDataRemoved(int position) {
//        list.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position,list.size()-1);
//        todal();
//    }

    private void notifyDataRemoved(long id, int position) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).businessId == id) {
                integers.add(i);
            }
        }
        Collections.reverse(integers);
        if (integers.size() == 2) {
            for (int i : integers) {
                list.remove(i);
                L.e("index = " + i);
            }
            notifyItemRangeRemoved(integers.get(1), 2);
            notifyItemRangeChanged(integers.get(1), list.size());
        } else {
            list.remove(position);
            notifyItemRemoved(position);

            if (position == list.size()) {
                notifyItemChanged(list.size() - 1);
            } else {
                int index = position == 0 ? 0 : position - 1;
                notifyItemRangeChanged(position - 1, list.size());
            }
        }
        todal();
    }


    static class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        protected void setData(ShopCarDTO shopCarDTO, int position) {
        }
    }

    class TitleHolder extends Holder {
        RelativeLayout layoutShopInfo;
        TextView checkCb;
        RoundedImageView logoIv;
        TextView nameTv;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            layoutShopInfo = itemView.findViewById(R.id.layoutShopInfo);
            checkCb = itemView.findViewById(R.id.checkCb);
            logoIv = itemView.findViewById(R.id.logoIv);
            nameTv = itemView.findViewById(R.id.nameTv);
        }

        @Override
        protected void setData(final ShopCarDTO shopCarDTO, int position) {
            super.setData(shopCarDTO, position);
            checkCb.setCompoundDrawablesRelativeWithIntrinsicBounds(shopCarDTO.checked == 1 ? R.mipmap.icon_gouxuan_1 : R.mipmap.icon_gouxuan_0, 0, 0, 0);
            ImageLoader.display(shopCarDTO.businessLogo, logoIv);
            nameTv.setText(shopCarDTO.businessName);
            layoutShopInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (shopCarDTO.businessId != 0) {
                        ARouter.getInstance().build(ARouterPath.ShopActivity).withLong(ARouterValueNameConfig.businessId, shopCarDTO.businessId).navigation();
                    }
                }
            });
            checkCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCb.setCompoundDrawablesRelativeWithIntrinsicBounds(shopCarDTO.checked == 1 ? R.mipmap.icon_gouxuan_0 : R.mipmap.icon_gouxuan_1, 0, 0, 0);
                    shopCarDTO.checked = shopCarDTO.checked == 1 ? 0 : 1;
                    shopAll(shopCarDTO.businessId, shopCarDTO.checked);
                }
            });

        }
    }

    class GoodsHolder extends Holder implements WeSwipeHelper.SwipeLayoutTypeCallBack {
        View topLineView;
        LinearLayout layoutGoodInfo;
        TextView checkCb;
        RoundedImageView pictureIv;
        TextView goodsNameTv;
        TextView skuNameTv;
        TextView priceTv;
        TextView countTv;
        TextView reduceTv;
        TextView addTv;
        View bottomLineView;
        TextView delTv;
        LinearLayout item0Ll;
        LinearLayout itemLl;

        public GoodsHolder(@NonNull View itemView) {
            super(itemView);

            topLineView = itemView.findViewById(R.id.topLineView);
            layoutGoodInfo = itemView.findViewById(R.id.layoutGoodInfo);
            checkCb = itemView.findViewById(R.id.checkCb);
            pictureIv = itemView.findViewById(R.id.pictureIv);
            goodsNameTv = itemView.findViewById(R.id.goodsNameTv);
            skuNameTv = itemView.findViewById(R.id.skuNameTv);
            priceTv = itemView.findViewById(R.id.priceTv);
            countTv = itemView.findViewById(R.id.countTv);
            reduceTv = itemView.findViewById(R.id.reduceTv);
            addTv = itemView.findViewById(R.id.addTv);
            bottomLineView = itemView.findViewById(R.id.bottomLineView);
            delTv = itemView.findViewById(R.id.delTv);
            item0Ll = itemView.findViewById(R.id.item0Ll);
            itemLl = itemView.findViewById(R.id.itemLl);
        }

        @Override
        protected void setData(final ShopCarDTO shopCarDTO, final int position) {
            super.setData(shopCarDTO, position);

            boolean b = shopCarDTO.viewType == list.get(position - 1).viewType;
            topLineView.setVisibility(b ? View.VISIBLE : View.GONE);
            checkCb.setCompoundDrawablesRelativeWithIntrinsicBounds(shopCarDTO.checked == 1 ? R.mipmap.icon_gouxuan_1 : R.mipmap.icon_gouxuan_0, 0, 0, 0);
            layoutGoodInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.GoodsDetailsActivity).withLong(ARouterValueNameConfig.goodsId, shopCarDTO.shopCarList.get(0).goodsId).navigation();
                }
            });
            checkCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkCb.setCompoundDrawablesRelativeWithIntrinsicBounds(shopCarDTO.checked == 1 ? R.mipmap.icon_gouxuan_0 : R.mipmap.icon_gouxuan_1, 0, 0, 0);
                    list.get(position).checked = shopCarDTO.checked == 1 ? 0 : 1;
                    goodsStatue(position, shopCarDTO.checked);
                }
            });

            final ShopCar shopCar = shopCarDTO.shopCarList.get(0);
            ImageLoader.display(shopCar.goodsPicture.split(",")[0], pictureIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            goodsNameTv.setText(shopCar.goodsName);
            if (shopCar.composeId != 0) {
                skuNameTv.setVisibility(View.VISIBLE);
                skuNameTv.setText(shopCar.skuName);
                skuNameTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckDoubleClick.isFastDoubleClick()) return;
                        getAttrCompose(shopCar, position);
                    }
                });
            } else {
                skuNameTv.setVisibility(View.INVISIBLE);
            }
            priceTv.setText("¥ " + DecimalFormatUtils.toTwo(shopCar.goodsPrice));
            countTv.setText(String.valueOf(shopCar.goodsNum));

            reduceTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shopCar.goodsNum > 1) {
                        shopCar.goodsNum--;
                        countTv.setText(String.valueOf(shopCar.goodsNum));
                        todal();
                        updateShopCar(shopCar.composeId, shopCar.goodsNum, shopCar.id);
                    }
                }
            });

            addTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shopCar.goodsNum++;
                    countTv.setText(String.valueOf(shopCar.goodsNum));
                    todal();
                    updateShopCar(shopCar.composeId, shopCar.goodsNum, shopCar.id);
                }
            });

            delTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
//                    notifyDataRemoved(position);
                    notifyDataRemoved(shopCar.businessId, position);
                    delShopCar(shopCar.id);
                }
            });

            boolean c = position == list.size() - 1 || shopCarDTO.viewType != list.get(position + 1).viewType;
            if (c) {
                bottomLineView.setBackgroundResource(R.drawable.bg_cart_goods);
            } else {
                bottomLineView.setBackgroundColor(0xffffffff);
            }
        }

        @Override
        public float getSwipeWidth() {
            return delTv.getWidth();
        }

        @Override
        public View needSwipeLayout() {
            return item0Ll;
        }

        @Override
        public View onScreenView() {
            return itemLl;
        }
    }

    public interface OnTodalListener {

        void onTodalListener(double todal, int size);

        void isAllCheckedListener(boolean b);

    }

    private void getAttrCompose(final ShopCar shopCar, final int position) {
        final Dialog dialog = DialogUtil.loadingDialog(context);
        dialog.show();
        HttpApiShopGoods.getAttrCompose(shopCar.goodsId, new HttpApiCallBack<ShopAttrAndComposeDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopAttrAndComposeDTO retModel) {
                dialog.dismiss();
                if (code == 1) {
                    fragmentShow(shopCar, retModel, position);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    private void fragmentShow(final ShopCar shopCar, ShopAttrAndComposeDTO retModel, final int position) {
        int minIndex = 0;
        for (int i = 0; i < retModel.attrComposeList.size(); i++) {
            if (shopCar.composeId == retModel.attrComposeList.get(i).id) {
                minIndex = i;
            }
        }

        ShopGoods shopGoods = new ShopGoods();
        shopGoods.id = shopCar.goodsId;
        shopGoods.goodsName = shopCar.goodsName;
        shopGoods.goodsPicture = shopCar.goodsPicture;

        SelectAttrFragment selectAttrFragment = new SelectAttrFragment();
        selectAttrFragment.setOnEditListener(new SelectAttrFragment.OnEditListener() {
            @Override
            public void onEditListener(ShopAttrCompose compose, int num) {
                shopCar.composeId = compose.id;
                shopCar.skuName = compose.name1 + "," + compose.name2;
                shopCar.goodsNum = num;
                shopCar.goodsPrice = compose.favorablePrice > 0 ? compose.favorablePrice : compose.price;
                notifyData(position);
                updateShopCar(compose.id, num, shopCar.id);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("attrDTOList", (ArrayList<? extends Parcelable>) retModel.shopGoodsAttrDTOS);
        bundle.putParcelableArrayList("composeList", (ArrayList<? extends Parcelable>) retModel.attrComposeList);
        bundle.putInt("minIndex", minIndex);
        bundle.putInt("num", shopCar.goodsNum);
        bundle.putInt("viewType", 3);
        bundle.putParcelable("shopGoods", shopGoods);
        selectAttrFragment.setArguments(bundle);
        selectAttrFragment.show(activity.getSupportFragmentManager(), "SelectAttrFragment");

    }

    private void updateShopCar(long composeId, int goodsNum, long shopCarId) {

        HttpApiShopCar.updateShopCar(composeId, goodsNum, shopCarId, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {

                }
            }
        });

    }

    private void delShopCar(long shopCarId) {

        HttpApiShopCar.delShopCar(shopCarId, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show("删除成功");
                }
            }
        });

    }

}
