package com.kalacheng.shopping.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopAttrCompose;
import com.kalacheng.busshop.model.ShopAttrValue;
import com.kalacheng.busshop.model.ShopGoodsAttr;
import com.kalacheng.busshop.model.ShopGoodsAttrComposite;
import com.kalacheng.busshop.model.ShopGoodsAttrDTO;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.AttributeAndValueBean;
import com.kalacheng.shopping.base.AttributeBean;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityGoodsAttributeBinding;
import com.kalacheng.shopping.seller.adapter.AttributeValueAdapter;
import com.kalacheng.shopping.seller.viewmodel.GoodsAttributeViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 商品属性
 */
@Route(path = ARouterPath.GoodsAttributeActivity)
public class GoodsAttributeActivity extends SBaseActivity<ActivityGoodsAttributeBinding, GoodsAttributeViewModel> {
    @Autowired(name = ARouterValueNameConfig.shopGoods)
    public ShopGoodsDTO goods;

    List<AttributeBean> list;
    List<ShopGoodsAttrDTO> dtos;

    private int attributeCount = 0;
    private long goodsId = 0;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_attribute;
    }

    @Override
    public void initData() {
        super.initData();
        list = new ArrayList<>();

        binding.addAttributeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (list.size() < 2) {
                    addItem(null);
                } else {
                    ToastUtil.show("最多可添加2个属性");
                }
            }
        });

        binding.editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;

                if (isRepeat()) {
                    ToastUtil.show("属性规格不可重复");
                    return;
                }
                if (goodsId == 0) {
                    createAttribute();
                } else {
                    updateAttribute();
                }
            }
        });

        if (goods != null) {
            goodsId = goods.goodsId;
            getArrDetailList();
        } else {
            addItem(null);
        }
    }

    private void addItem(ShopGoodsAttrDTO attrDTO) {
        attributeCount++;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_attribure, binding.containerLl, false);
        TextView addAttributeTv;
        EditText addAttributeEt;
        RecyclerView recyclerView;
        AttributeValueAdapter adapter;

        adapter = new AttributeValueAdapter();
        addAttributeTv = view.findViewById(R.id.addAttributeTv);
        addAttributeEt = view.findViewById(R.id.addAttributeEt);
        recyclerView = view.findViewById(R.id.recyclerView);

        addAttributeTv.setText("属性" + attributeCount + ":");
        if (attrDTO != null) {
            addAttributeEt.setText(attrDTO.attrName);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
        if (attrDTO != null) {
            adapter.setList(attrDTO.attrValueList);
        }

        list.add(new AttributeBean(addAttributeEt, adapter));
        binding.containerLl.addView(view);

    }


    private boolean isRepeat() {

        for (int i = 0; i < list.size(); i++) {
            HashSet<String> hashSet = new HashSet<String>();
            if (list.get(i).getAttributeValueAdapter().getList().size() > 0) {
                for (int j = 0; j < list.get(i).getAttributeValueAdapter().getList().size() - 1; j++) {
                    AttributeAndValueBean valueBean = list.get(i).getAttributeValueAdapter().getList().get(j);
                    hashSet.add(valueBean.editText.getText().toString().trim());
                }
                return hashSet.size() != list.get(i).getAttributeValueAdapter().getList().size() - 1;
            }
        }
        return false;

    }

    public List<ShopGoodsAttrComposite> getAttributes() {
        List<ShopGoodsAttrComposite> goodsPropertyValues = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String attributes = list.get(i).getEditText().getText().toString().trim();
            if (!TextUtils.isEmpty(attributes) && list.get(i).getAttributeValueAdapter().getList().size() > 1) {
                ShopGoodsAttrComposite shopGoodsAttrComposite = new ShopGoodsAttrComposite();

                shopGoodsAttrComposite.shopGoodsAttr = new ShopGoodsAttr();
                shopGoodsAttrComposite.shopGoodsAttr.name = attributes;

                if (dtos != null && i < dtos.size()) {
                    shopGoodsAttrComposite.shopGoodsAttr.goodsId = goods.goodsId;
                    shopGoodsAttrComposite.shopGoodsAttr.id = dtos.get(i).attrId;
                }
                shopGoodsAttrComposite.shopAttrValues = new ArrayList<>();

                for (int j = 0; j < list.get(i).getAttributeValueAdapter().getList().size(); j++) {
                    AttributeAndValueBean valueBean = list.get(i).getAttributeValueAdapter().getList().get(j);
                    if (valueBean.editText != null && !TextUtils.isEmpty(valueBean.editText.getText().toString().trim())) {
                        ShopAttrValue shopAttrValue = new ShopAttrValue();
                        shopAttrValue.name = valueBean.editText.getText().toString().trim();
                        if (dtos != null && i < dtos.size() && j < dtos.get(i).attrValueList.size() && dtos.get(i).attrValueList.get(j).name.equals(shopAttrValue.name)) {
                            shopAttrValue.goodsId = goods.goodsId;
                            shopAttrValue.attributeId = dtos.get(i).attrId;
                            shopAttrValue.id = dtos.get(i).attrValueList.get(j).id;
                        }
                        shopGoodsAttrComposite.shopAttrValues.add(shopAttrValue);
                    }
                }
                goodsPropertyValues.add(shopGoodsAttrComposite);
            }
        }
        return goodsPropertyValues;
    }

    private void createAttribute() {
        List<ShopGoodsAttrComposite> composites = getAttributes();
        if (composites == null || composites.size() < 1) {
            ToastUtil.show("请添加属性");
            return;
        }
        HttpApiShopGoods.createAttribute(goodsId, composites, new HttpApiCallBackArr<ShopAttrCompose>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopAttrCompose> retModel) {
                if (code == 1 && retModel != null && retModel.size() > 0) {
                    goodsId = retModel.get(0).goodsId;
                    ARouter.getInstance().build(ARouterPath.EditPriceActivity).withParcelableArrayList(ARouterValueNameConfig.ShopAttrList, (ArrayList<? extends Parcelable>) retModel).navigation(GoodsAttributeActivity.this, 999);
                }
            }
        });
    }

    private void updateAttribute() {
        List<ShopGoodsAttrComposite> composites = getAttributes();
        if (composites == null || composites.size() < 1) {
            ToastUtil.show("请添加属性");
            return;
        }

        HttpApiShopGoods.updateAttribute(goodsId, composites, new HttpApiCallBackArr<ShopAttrCompose>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopAttrCompose> retModel) {
                if (code == 1 && retModel != null && retModel.size() > 0) {
                    ARouter.getInstance().build(ARouterPath.EditPriceActivity).withParcelableArrayList(ARouterValueNameConfig.ShopAttrList, (ArrayList<? extends Parcelable>) retModel).navigation(GoodsAttributeActivity.this, 999);
                }
            }
        });
    }

    private void getArrDetailList() {
        HttpApiShopGoods.getArrDetailList(goods.goodsId, new HttpApiCallBackArr<ShopGoodsAttrDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopGoodsAttrDTO> retModel) {
                if (code == 1) {
                    dtos = retModel;
                    for (ShopGoodsAttrDTO attrDTO : retModel) {
                        addItem(attrDTO);
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            if (resultCode == -1 && data != null) {
                setResult(RESULT_OK, data);
                finish();
            }
        }

    }
}
