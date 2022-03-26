package com.kalacheng.shopping.seller.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopGoodsCategory;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityGoodsClassifyBinding;
import com.kalacheng.shopping.seller.adapter.GoodsClassifyAdapter;
import com.kalacheng.shopping.seller.viewmodel.GoodsClassifyViewModel;

import java.util.List;

/**
 * 商品分类
 */
@Route(path = ARouterPath.GoodsClassifyActivity)
public class GoodsClassifyActivity extends SBaseActivity<ActivityGoodsClassifyBinding, GoodsClassifyViewModel> {

    GoodsClassifyAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_classify;
    }

    @Override
    public void initData() {
        super.initData();
        adapter = new GoodsClassifyAdapter();
        adapter.setListener(new GoodsClassifyAdapter.OnItemClickListener() {
            @Override
            public void onClick(ShopGoodsCategory classifyTv) {
                Intent intent = new Intent();
                intent.putExtra("classify", classifyTv);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        getCategoryList();
    }

    private void getCategoryList() {
        HttpApiShopGoods.getCategoryList(new HttpApiCallBackArr<ShopGoodsCategory>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopGoodsCategory> retModel) {
                if (code == 1 && retModel != null) {
                    adapter.getList().addAll(retModel);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
