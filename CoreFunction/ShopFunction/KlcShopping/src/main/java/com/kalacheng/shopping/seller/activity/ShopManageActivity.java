package com.kalacheng.shopping.seller.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.busshop.model.ShopBusiness;
import com.kalacheng.busshop.model.ShopBusinessOrderInfoDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.model.ShopOrderNumDTO;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityShopManageBinding;
import com.kalacheng.shopping.seller.adapter.ManageFunctionAdapter;
import com.kalacheng.shopping.seller.viewmodel.ShopManageViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ToastUtil;

/**
 * 官方小店
 */
@Route(path = ARouterPath.ShopManageActivity)
public class ShopManageActivity extends SBaseActivity<ActivityShopManageBinding, ShopManageViewModel> implements ManageFunctionAdapter.OnItemClickListener {

    private String[] times = new String[]{"今日", "昨日", "本周", "上周", "上月"};
    private ManageFunctionAdapter adapter;

    @Autowired(name = ARouterValueNameConfig.AuditStatus)
    public int auditStatus;
    @Autowired(name = ARouterValueNameConfig.AuditRemake)
    public String remake;

    private ShopBusiness shopBusiness;
    private int dateTime = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_shop_manage;
    }

    @Override
    public void initData() {
        super.initData();
        getOne();
        if (auditStatus != 2) {
            switch (auditStatus) {
                case 1:
                    binding.remakeTv.setText("审核中：" + remake);
                    break;
                case 3:
                    binding.remakeTv.setText("审核被拒：" + remake);
                    break;
                case 4:
                    binding.remakeTv.setText("被冻结：" + remake);
                    break;
                default:
                    break;
            }
            binding.remakeTv.setVisibility(View.VISIBLE);
            binding.remakeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.UpShopDataActivity)
                            .withInt(ARouterValueNameConfig.isUp, 1)
                            .navigation();
                }
            });
        }

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new ManageFunctionAdapter();
        adapter.setListener(this);
        binding.recyclerView.setAdapter(adapter);

        binding.timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showSingleTextPickerDialog(mContext, times, new DialogUtil.SingleTextCallback() {
                    @Override
                    public void onConfirmClick(int id, String date) {
                        binding.timeTv.setText(date);
                        dateTime = id;
                        getBusinessOrderInfo(dateTime);
                    }
                });
            }
        });

        binding.lookAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.OrderManageActivity2).withInt(ARouterValueNameConfig.TYPE, 0).navigation();
            }
        });

        binding.waitPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.OrderManageActivity2).withInt(ARouterValueNameConfig.TYPE, 1).navigation();
            }
        });

        binding.waitDeliverGoodsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.OrderManageActivity2).withInt(ARouterValueNameConfig.TYPE, 2).navigation();
            }
        });

        binding.waitReceiptTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.OrderManageActivity2).withInt(ARouterValueNameConfig.TYPE, 3).navigation();
            }
        });

        binding.afterSalesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.OrderManageActivity2).withInt(ARouterValueNameConfig.TYPE, 4).navigation();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getBusinessOrderNum();
        getBusinessOrderInfo(dateTime);
    }

    @Override
    public void onClick(String function) {
        if (function.equals("商家简介")) {
            ARouter.getInstance().build(ARouterPath.UpShopDataActivity)
                    .withInt(ARouterValueNameConfig.isUp, 1)
                    .navigation();
            return;
        }
        if (auditStatus != 2) {
            if (auditStatus == 1) {
                ToastUtil.show(remake);
            } else if (auditStatus == 3) {
                DialogUtil.showTipsButtonDialog(mContext, "审核被拒：" + remake, "去修改", new DialogUtil.CurrencyCallback() {
                    @Override
                    public void onConfirmClick() {
                        ARouter.getInstance().build(ARouterPath.UpShopDataActivity)
                                .withInt(ARouterValueNameConfig.isUp, 1)
                                .navigation();
                    }
                });
            } else if (auditStatus == 4) {
                ToastUtil.show("被冻结：" + remake);
            }
            return;
        }

        switch (function) {
            case "我的收入":
                ARouter.getInstance().build(ARouterPath.MyIncomeActivity).navigation();
                break;
            case "添加商品":
                ARouter.getInstance().build(ARouterPath.AddGoodsActivity).navigation();
                break;
            case "优惠券":
            case "满减活动":
                ToastUtil.show("此功能暂未开放");
                break;
            case "商品管理":
                ARouter.getInstance().build(ARouterPath.GoodsManageActivity).navigation();
                break;
            case "直播预告":
                ARouter.getInstance().build(ARouterPath.LiveBuyNoticeActivity).navigation();
                break;
            case "小店预览":
                if (shopBusiness != null)
                    ARouter.getInstance().build(ARouterPath.ShopActivity).withLong(ARouterValueNameConfig.businessId, shopBusiness.id).navigation();
                break;
            default:
        }

    }

    private void getBusinessOrderNum() {
        HttpApiShopOrder.getOrderNum(2, new HttpApiCallBack<ShopOrderNumDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopOrderNumDTO retModel) {
                if (code == 1) {
                    binding.setNumDto(retModel);
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
                    shopBusiness = retModel;
                }
            }
        });
    }

    private void getBusinessOrderInfo(int searchDay) {
        HttpApiShopOrder.getBusinessOrderInfo(searchDay, new HttpApiCallBack<ShopBusinessOrderInfoDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopBusinessOrderInfoDTO retModel) {
                if (code == 1) {
                    binding.orderCountTv.setText(retModel.count + "");
                    binding.incomeTv.setText(DecimalFormatUtils.toTwo(retModel.income));
                    binding.priceTv.setText(DecimalFormatUtils.toTwo(retModel.price));
                }
            }
        });
    }


}
