package com.kalacheng.shopping.buyer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.busshop.httpApi.HttpApiRestShop;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.model.ShopBusiness;
import com.kalacheng.busshop.model.ShopBusinessDTO;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.buyer.adapter.GoodsAdapter3;
import com.kalacheng.shopping.buyer.adapter.ShopFragmentAdapter;
import com.kalacheng.shopping.buyer.fragment.ShopGoodsListFragment;
import com.kalacheng.shopping.buyer.fragment.ShopSynopsisFragment;
import com.kalacheng.shopping.buyer.viewmodel.ShopViewModel;
import com.kalacheng.shopping.databinding.ActivityShopBinding;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.BubblePopupWindow;

/**
 * 小店预览
 */
@Route(path = ARouterPath.ShopActivity)
public class ShopActivity extends SBaseActivity<ActivityShopBinding, ShopViewModel> {

    @Autowired(name = ARouterValueNameConfig.businessId)
    long id;

    private ShopFragmentAdapter adapter;
    private String phoneNumber;
    private BubblePopupWindow bubbleView;
    private ShopGoodsListFragment goodsListFragment;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_shop;
    }

    @Override
    public void initData() {
        super.initData();

        binding.etShop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    binding.ivTxtClear.setVisibility(View.VISIBLE);
                    searchGoods(id, s.toString().trim());
                } else {
                    binding.ivTxtClear.setVisibility(View.GONE);
                    getData();
                }
            }
        });

        binding.ivTxtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                binding.etShop.setText("");
            }
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.listBtn) {
                    if (binding.viewPager.getCurrentItem() != 0)
                        binding.viewPager.setCurrentItem(0);
                } else if (checkedId == R.id.synopsisBtn) {
                    if (binding.viewPager.getCurrentItem() != 1)
                        binding.viewPager.setCurrentItem(1);
                }
            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.listBtn.setChecked(true);
                        break;
                    case 1:
                        binding.synopsisBtn.setChecked(true);
                        break;
                }
            }
        });

        adapter = new ShopFragmentAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.setCurrentItem(0);

        binding.callIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (!TextUtils.isEmpty(phoneNumber)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        binding.kefuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                        .withString(ARouterValueNameConfig.TO_UID, String.valueOf(binding.getModel().business.anchorId))
                        .withString(ARouterValueNameConfig.Name, binding.getModel().business.name)
                        .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                        .navigation();

            }
        });

        binding.moreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                showPop();
            }
        });

        binding.lookTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                AppHomeHallDTO bean = new AppHomeHallDTO();
                bean.roomId = binding.getModel().roomId;
                bean.liveType = 1;
                LookRoomUtlis.getInstance().getData(bean, mContext);
            }
        });


        if (bubbleView == null) {
            bubbleView = new BubblePopupWindow(this);
            View view = LayoutInflater.from(this).inflate(R.layout.popu_more, bubbleView.getBubbleView(), false);
            TextView tvShoppingCart = view.findViewById(R.id.tv_shopping_cart);
            tvShoppingCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.ShoppingCartActivity).navigation();
                    bubbleView.dismiss();
                }
            });
            TextView tvMsg = view.findViewById(R.id.tv_msg);
            tvMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.ConversationListActivity).navigation();
                    bubbleView.dismiss();
                }
            });
            TextView tvOrder = view.findViewById(R.id.tv_order);
            tvOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.OrderManageActivity1).navigation();
                    bubbleView.dismiss();
                }
            });
            bubbleView.setBubbleView(view);
        }

        // 获取我的商家信息
        getOne();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        HttpApiRestShop.businessCenter(id, new HttpApiCallBack<ShopBusinessDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopBusinessDTO retModel) {
                if (code == 1) {
                    binding.setModel(retModel);

                    phoneNumber = retModel.business.mobile;
                    ImageLoader.display(retModel.business.logo, binding.sellserlogoIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    binding.salesVolumeTv.setText("在售商品 " + retModel.business.effectiveGoodsNum + " | 累计销量" + retModel.business.totalSoldNum + "+");
                    goodsListFragment = (ShopGoodsListFragment) adapter.getList().get(0);
                    goodsListFragment.setList(retModel.goodsDTOList);

                    ShopSynopsisFragment synopsisFragment = (ShopSynopsisFragment) adapter.getList().get(1);
                    synopsisFragment.setShopBusiness(retModel.business);

                    if (retModel.status == 1) {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        binding.recyclerView.setHasFixedSize(true);
                        binding.recyclerView.setAdapter(new GoodsAdapter3(retModel.liveDetailDTO.liveGoodsList));
                    }

                }
            }
        });
    }

    private void searchGoods(long id, String productName) {
        HttpApiRestShop.searchBusinessProduct(id, productName, new HttpApiCallBack<ShopBusinessDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopBusinessDTO retModel) {
                if (code == 1) {
                    goodsListFragment.setList(retModel.goodsDTOList);
                }
            }
        });
    }

    /**
     * 获取商家信息
     */
    public void getOne() {
        HttpApiShopBusiness.getOne(new HttpApiCallBack<ShopBusiness>() {
            @Override
            public void onHttpRet(int code, String msg, ShopBusiness retModel) {
                if (code == 1) {
                    if (retModel.id == id) {
                        binding.callIv.setVisibility(View.GONE);
                        binding.kefuIv.setVisibility(View.GONE);
                    } else {
                        binding.callIv.setVisibility(View.VISIBLE);
                        binding.kefuIv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void showPop() {
        bubbleView.show(binding.moreIv, Gravity.BOTTOM, 375);
    }


}
