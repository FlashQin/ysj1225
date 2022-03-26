package com.kalacheng.shopping.buyer.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopAttrCompose;
import com.kalacheng.busshop.model.ShopGoods;
import com.kalacheng.busshop.model.ShopGoodsAttrDTO;
import com.kalacheng.busshop.model.ShopGoodsDetailDTO;
import com.kalacheng.commonview.bean.ShareDialogBean;
import com.kalacheng.commonview.dialog.ShareDialog;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.ApiShareConfig;
import com.kalacheng.mob.MobConst;
import com.kalacheng.mob.MobShareUtil;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.buyer.adapter.GoodsAdapter1;
import com.kalacheng.shopping.buyer.adapter.GoodsAdapter2;
import com.kalacheng.shopping.buyer.adapter.GoodsDetailPicAdapter;
import com.kalacheng.shopping.buyer.bean.ShopCarBean;
import com.kalacheng.shopping.buyer.fragment.SelectAttrFragment;
import com.kalacheng.shopping.buyer.viewmodel.GoodsDetailsViewModel;
import com.kalacheng.shopping.databinding.ActivityGoodsDetailsBinding;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import cn.jpush.im.android.api.JMessageClient;

/**
 * 商品详情
 */
@Route(path = ARouterPath.GoodsDetailsActivity)
public class GoodsDetailsActivity extends SBaseActivity<ActivityGoodsDetailsBinding, GoodsDetailsViewModel>
        implements SelectAttrFragment.OnClickTagListener, SelectAttrFragment.OnBuyNowListener, SelectAttrFragment.AddCartlistener {
    @Autowired(name = ARouterValueNameConfig.goodsId)
    long goodsId;

    private GoodsAdapter1 adapter1;
    private GoodsAdapter2 adapter2;
    private GoodsDetailPicAdapter detailPicAdapter;

    private List<ShopGoodsAttrDTO> attrDTOList;
    private List<ShopAttrCompose> composeList;
    private ShopGoods shopGoods;

    private int minIndex = 0;
    private int num = 1;

    private SelectAttrFragment selectAttrFragment;

    private int h = 0;
    private int h2 = 0;
    private int h3 = 0;


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_details;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData() {
        super.initData();
        h = DpUtil.dp2px(300);
        h2 = h / 2;
        h3 = h / 3;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY <= h3) {
                        binding.titleWRl.setAlpha(0);

                        float alpha = 1 - ((float) scrollY) / (float) h2;
                        binding.titleBRl.setAlpha(alpha);
//                        setTitleView(false);
                    } else if (scrollY <= h) {
                        binding.titleBRl.setAlpha(0);

                        float alpha = ((float) scrollY) / (float) h;
//
//                        int y = scrollY - h3;
//                        float alpha = ((float) y) / (float) h3;
                        binding.titleWRl.setAlpha(alpha);
//                        setTitleView(true);
                    } else {
                        binding.titleBRl.setAlpha(0);
                        binding.titleWRl.setAlpha(1);
                    }

//                    if (scrollY<binding.detailsLl.getTop() - DpUtil.getScreenHeight()){
//                        binding.goodsWBtn.setChecked(true);
//                    }
//
//                    if (scrollY>binding.detailsLl.getTop() - DpUtil.getScreenHeight()/3 && scrollY < binding.detailsLl.getTop()){
//                        binding.detailsWBtn.setChecked(true);
//                    }
//
//                    if (scrollY>binding.presentLl.getTop() - DpUtil.getScreenHeight()/3 && scrollY < binding.presentLl.getTop()){
//                        binding.recommendWBtn.setChecked(true);
//                    }

                }
            });
        }
        binding.refreshLayout.setEnableLoadMore(false);
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                getGoodsDetails();
                binding.refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.refreshLayout.finishRefresh();
                    }
                }, 500);

            }
        });

        binding.radioGroupW.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.goodsWBtn) {
                    binding.goodsBBtn.setChecked(true);
                } else if (checkedId == R.id.detailsWBtn) {
                    binding.detailsBBtn.setChecked(true);
                    binding.scrollView.scrollTo(0, binding.detailsLl.getTop());

                } else if (checkedId == R.id.recommendWBtn) {
                    binding.recommendBBtn.setChecked(true);

                }
            }
        });


        binding.goodsWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.goodsWBtn.isChecked()) {
                    binding.scrollView.fullScroll(NestedScrollView.FOCUS_UP);
                }
            }
        });

        binding.detailsWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.detailsWBtn.isChecked()) {
                    binding.scrollView.scrollTo(0, binding.detailsLl.getTop() - DpUtil.dp2px(65));
                }
            }
        });

        binding.recommendWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.recommendWBtn.isChecked()) {
                    binding.scrollView.scrollTo(0, binding.presentLl.getTop() - DpUtil.dp2px(65));
                }
            }
        });

        binding.shopCartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.ShoppingCartActivity).navigation();
            }
        });

        binding.shopTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                BaseApplication.containsActivity("ShopActivity");
                ARouter.getInstance().build(ARouterPath.ShopActivity).withLong(ARouterValueNameConfig.businessId, shopGoods.businessId).navigation();
            }
        });

        binding.kefuTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                        .withString(ARouterValueNameConfig.TO_UID, String.valueOf(binding.getDTO().anchorId))
                        .withString(ARouterValueNameConfig.Name, binding.getDTO().businessName)
                        .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                        .navigation();

            }
        });

        binding.shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ArrayList<ShareDialogBean> otherBeans = new ArrayList<>();
                ShareDialogBean beanCopy = new ShareDialogBean();
                beanCopy.id = 1002;
                beanCopy.src = R.mipmap.icon_invitation_url_copy;
                beanCopy.text = "复制链接";
                otherBeans.add(beanCopy);

                ShareDialog shareDialog = new ShareDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ShareDialog.ShareDialogOtherBeans, otherBeans);
                shareDialog.setArguments(bundle);
                shareDialog.setShareDialogListener(new ShareDialog.ShareDialogListener() {
                    @Override
                    public void onItemClick(long id) {
                        if (id == 1002) {//复制链接
                            HttpApiAppUser.share(1, 3, new HttpApiCallBack<ApiShareConfig>() {
                                @Override
                                public void onHttpRet(int code, String msg, ApiShareConfig retModel) {
                                    if (code == 1) {
                                        WordUtil.copyLink(retModel.url);
                                    } else {
                                        ToastUtil.show(msg);
                                    }
                                }
                            });
                        } else if (id == 1) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.WX);
                        } else if (id == 2) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.WX_PYQ);
                        } else if (id == 3) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.QQ);
                        } else if (id == 4) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.QZONE);
                        }
                    }
                });
                shareDialog.show(getSupportFragmentManager(), "ShareDialog");
            }
        });

        binding.xiaoxiWIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.ConversationListActivity).navigation();
            }
        });

        binding.addCartTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                fragmentShow(1);
            }
        });
        binding.addOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                fragmentShow(2);
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerView.setHasFixedSize(true);
        adapter1 = new GoodsAdapter1();
        binding.recyclerView.setAdapter(adapter1);

        binding.detailRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.detailRecyclerView.setHasFixedSize(true);
        detailPicAdapter = new GoodsDetailPicAdapter();
        binding.detailRecyclerView.setAdapter(detailPicAdapter);


        binding.presentRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        binding.presentRecyclerView.setHasFixedSize(true);
        adapter2 = new GoodsAdapter2();
        binding.presentRecyclerView.setAdapter(adapter2);

        binding.attrLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                fragmentShow(0);
            }
        });

        binding.price2Tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

//        getGoodsDetails();
    }

    private void setTitleView(boolean b) {
        binding.backIv.setImageResource(b ? R.mipmap.icon_back_w : R.mipmap.icon_back_b);

        binding.goodsBBtn.setTextColor(b ? 0xff333333 : 0xffffffff);
        binding.detailsBBtn.setTextColor(b ? 0xff333333 : 0xffffffff);
        binding.recommendBBtn.setTextColor(b ? 0xff333333 : 0xffffffff);

        binding.shopcartBIv.setImageResource(b ? R.mipmap.icon_gouwuche_w : R.mipmap.icon_gouwuche_b);
        binding.xiaoxiBIv.setImageResource(b ? R.mipmap.icon_xiaoxi_w : R.mipmap.icon_xiaoxi_b);
        binding.titleBRl.setBackgroundColor(b ? 0xffffffff : 0);

    }

    // 选择规格
    private void fragmentShow(int viewType) {
        selectAttrFragment = null;
        selectAttrFragment = new SelectAttrFragment();
        selectAttrFragment.setListener(this);
        selectAttrFragment.setOnBuyNowListener(this);
        selectAttrFragment.setAddCartlistener(this);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("attrDTOList", (ArrayList<? extends Parcelable>) attrDTOList);
        bundle.putParcelableArrayList("composeList", (ArrayList<? extends Parcelable>) composeList);
        bundle.putInt("minIndex", minIndex);
        bundle.putInt("num", num);
        bundle.putInt("viewType", viewType);
        bundle.putParcelable("shopGoods", shopGoods);
        selectAttrFragment.setArguments(bundle);
        selectAttrFragment.show(getSupportFragmentManager(), "SelectAttrFragment");
    }

    // 如果商品是本人的商品 隐藏掉下方操作布局， 如果不是本人
    private void isVisibility(long ShopId) {
        binding.bottomLl.setVisibility(ShopId == HttpClient.getUid() ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGoodsDetails();
        int count = JMessageClient.getAllUnReadMsgCount();
        if (count == 0) {
            binding.msgNumTv.setVisibility(View.GONE);
            binding.msgNumWTv.setVisibility(View.GONE);
        }
        binding.msgNumTv.setText(String.valueOf(JMessageClient.getAllUnReadMsgCount()));
        binding.msgNumWTv.setText(String.valueOf(JMessageClient.getAllUnReadMsgCount()));

    }

    private void getGoodsDetails() {
        HttpApiShopGoods.getGoodsDetail(goodsId, new HttpApiCallBack<ShopGoodsDetailDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopGoodsDetailDTO retModel) {
                if (code == 1) {

                    isVisibility(retModel.anchorId);

                    binding.setDTO(retModel);
                    ImageLoader.display(retModel.businessLogo, binding.sellserlogoIv);

                    if (retModel.shopGoods != null && retModel.shopGoods.goodsPicture != null) {
                        shopGoods = retModel.shopGoods;
                        initBanner(retModel.shopGoods.goodsPicture.split(","), binding.banner);
                    }

                    if (retModel.shopGoodsDTOS != null && retModel.shopGoodsDTOS.size() > 0) {
                        adapter1.setList(retModel.shopGoodsDTOS);
                    }

                    if (retModel.recommendGoodsDTOS != null && retModel.recommendGoodsDTOS.size() > 0) {
                        adapter2.setList(retModel.recommendGoodsDTOS);
                    }

                    if (retModel.shopGoods != null && retModel.shopGoods.detailPicture != null) {
                        detailPicAdapter.setList(Arrays.asList(retModel.shopGoods.detailPicture.split(",")));
                    }

                    if (retModel.attrDTOList != null) {
                        attrDTOList = retModel.attrDTOList;
                    }

                    if (retModel.composeList != null && retModel.composeList.size() > 0) {
                        composeList = retModel.composeList;
                        double minValue = retModel.composeList.get(0).favorablePrice;
                        for (int i = 0; i < retModel.composeList.size(); i++) {
                            if (i == retModel.composeList.size() - 1) {
                                continue;
                            }
                            double next = retModel.composeList.get(i + 1).favorablePrice;
                            if (minValue > next) {
                                minValue = next;
                                minIndex = i + 1;
                            }
                        }
                        setComposeTv(composeList.get(minIndex), num);
                    }
                    num = 1;
                    if (retModel.composeList == null || retModel.composeList.size() == 0) {
                        if (shopGoods.channelId == 1) {
                            if (shopGoods.favorablePrice > 0) {
                                binding.price1Tv.setText("¥ " + DecimalFormatUtils.toTwo(shopGoods.favorablePrice));
                                binding.price2Tv.setText("¥ " + DecimalFormatUtils.toTwo(shopGoods.price));
                            } else {
                                binding.price1Tv.setText("¥ " + DecimalFormatUtils.toTwo(shopGoods.price));
                            }
                        } else {
                            binding.price1Tv.setText("¥ " + DecimalFormatUtils.toTwo(shopGoods.price));
                        }
                        binding.composeTv.setText(" " + num + "件");
                        binding.attrLl.setVisibility(View.GONE);
                        binding.attrLlLine.setVisibility(View.GONE);
                    } else {
                        setComposeTv(retModel.composeList.get(retModel.composeList.size() - 1), 1);
                        minIndex = retModel.composeList.size() - 1;
                    }

                }
                binding.refreshLayout.finishRefresh();
            }
        });
    }

    private void setComposeTv(ShopAttrCompose compose, int count) {
        binding.composeTv.setText("“" + compose.name1 + "”");
        if (attrDTOList.size() > 1) {
            binding.composeTv.append("  “" + compose.name2 + "”");
        }
        binding.composeTv.append(" " + count + "件");
        if (compose.favorablePrice > 0) {
            binding.price1Tv.setText("¥ " + DecimalFormatUtils.toTwo(compose.favorablePrice));
            binding.price2Tv.setText("¥ " + DecimalFormatUtils.toTwo(compose.price));
        } else {
            binding.price1Tv.setText("¥ " + DecimalFormatUtils.toTwo(compose.price));
            binding.price2Tv.setText("");
        }
    }

    private void initBanner(String[] imagesStr, ConvenientBanner convenientBanner) {
        final List<String> stringList = new ArrayList<>();
        for (int i = 0; i < imagesStr.length; i++) {
            stringList.add(imagesStr[i]);
        }
        if (stringList.size() > 1) {
            convenientBanner.setCanLoop(true);
            convenientBanner.setPointViewVisible(true);
        } else {
            convenientBanner.setCanLoop(false);
            convenientBanner.setPointViewVisible(false);
        }
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, stringList);
        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ImagePreview.getInstance().setContext(mContext).setIndex(position).setImageList(stringList).setShowDownButton(false).start();
            }
        });
        convenientBanner.setPageIndicator(new int[]{R.drawable.banner_indicator_grey, R.drawable.banner_indicator_white});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
//        convenientBanner.startTurning(3000);
        convenientBanner.setManualPageable(true);
        if (convenientBanner.getViewPager() != null) {
            convenientBanner.getViewPager().setClipToPadding(false);
            convenientBanner.getViewPager().setClipChildren(false);
            try {
                ((RelativeLayout) convenientBanner.getViewPager().getParent()).setClipChildren(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            convenientBanner.getViewPager().setOffscreenPageLimit(3);
        }

    }

    @Override
    public void onClickTagListener(int index, int num) {
        minIndex = index;
        this.num = num;
        if (composeList != null && composeList.size() > 0) {
            setComposeTv(composeList.get(index), num);
        } else {
            binding.composeTv.setText(" " + num + "件");
        }
    }

    @Override
    public void onBuyNowListener() {
        ArrayList<ShopCarBean> shopCarBeans = new ArrayList<>();
        shopCarBeans.add(createShopCarDTO());
        ARouter.getInstance().build(ARouterPath.SubmitOrderActivity)
                .withParcelableArrayList(ARouterValueNameConfig.shopCarBeans, shopCarBeans)
                .navigation();
    }

    @Override
    public void addCartlistener() {
        getGoodsDetails();
    }


    private ShopCarBean createShopCarDTO() {
        ShopCarBean shopCarBean = new ShopCarBean();
        shopCarBean.businessId = shopGoods.businessId;
        shopCarBean.businessLogo = binding.getDTO().businessLogo;
        shopCarBean.businessName = binding.getDTO().businessName;
        shopCarBean.goodsId = goodsId;
        shopCarBean.goodsName = shopGoods.goodsName;
        String skuName = binding.composeTv.getText().toString();
        shopCarBean.skuName = skuName.substring(0, skuName.length() - 2);
        shopCarBean.goodsNum = num;
        if (!TextUtils.isEmpty(shopGoods.goodsPicture)) {
            shopCarBean.goodsPicture = shopGoods.goodsPicture.split(",")[0];
        } else {
            shopCarBean.goodsPicture = "";
        }
        long id = 0;
        if (composeList != null && composeList.size() > 0) {
            id = composeList.get(minIndex).id;
            if (composeList.get(minIndex).favorablePrice > 0) {
                shopCarBean.goodsPrice = composeList.get(minIndex).favorablePrice;
            } else {
                shopCarBean.goodsPrice = composeList.get(minIndex).price;
            }
        } else {
            if (shopGoods.favorablePrice > 0) {
                shopCarBean.goodsPrice = shopGoods.favorablePrice;
            } else {
                shopCarBean.goodsPrice = shopGoods.price;
            }
        }
        shopCarBean.composeId = id;
        return shopCarBean;
    }

    // banner加载图片适配
    public static class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            // 图片
            ImageLoader.display(data, imageView);
        }
    }


}
