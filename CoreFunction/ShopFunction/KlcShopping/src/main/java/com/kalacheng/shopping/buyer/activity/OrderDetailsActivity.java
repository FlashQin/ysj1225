package com.kalacheng.shopping.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.busshop.httpApi.HttpApiShopQuiteOrder;
import com.kalacheng.busshop.model.ShopAddress;
import com.kalacheng.busshop.model.ShopParentOrder;
import com.kalacheng.busshop.model.ShopUserOrderDetailDTO;
import com.kalacheng.commonview.dialog.PayMethodSelectDialog;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.buyer.TipDialog;
import com.kalacheng.shopping.buyer.adapter.OrderDetailsGoodsListAdapter;
import com.kalacheng.shopping.buyer.adapter.OrderReturnProcessAdapter;
import com.kalacheng.shopping.buyer.dialog.BuyerApplyRefundDialog;
import com.kalacheng.shopping.buyer.dialog.BuyerLogisticsNumDialog;
import com.kalacheng.shopping.buyer.viewmodel.OrderDetailsViewModel;
import com.kalacheng.shopping.databinding.ActivityOrderDetailsBinding;
import com.kalacheng.shopping.seller.dialog.ReviewGoodsDialog;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;

import java.util.List;
import java.util.Locale;

/**
 * 订单详情
 */
@Route(path = ARouterPath.OrderDetailsActivity)
public class OrderDetailsActivity extends SBaseActivity<ActivityOrderDetailsBinding, OrderDetailsViewModel> {
    @Autowired(name = ARouterValueNameConfig.TYPE)
    public int type = 1;// 1 买家样式；2 卖家样式；
    @Autowired(name = ARouterValueNameConfig.orderId)
    public long orderId = 0;

    //右上角的倒计时样式
    private Handler handlerOne;
    private Runnable runnableOne;

    //文字中的倒计时样式
    private Handler handlerTwo;
    private Runnable runnableTwo;
    //时间中的文字提示
    private String timeInfo;

    private OrderReturnProcessAdapter orderReturnProcessAdapter;
    OrderDetailsGoodsListAdapter adapter;

    @Override
    protected boolean isStatusBarWhite() {
        return true;
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_order_details;
    }

    @Override
    public void initData() {
        super.initData();
        if (type == 1) {
            binding.tvInitiate.setText("联系卖家");
        } else {
            binding.tvInitiate.setText("发起会话");
        }

        binding.rvReturnProcess.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        orderReturnProcessAdapter = new OrderReturnProcessAdapter(mContext);
        binding.rvReturnProcess.setAdapter(orderReturnProcessAdapter);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new OrderDetailsGoodsListAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new ItemDecoration(this, 0x00000000, 0, 15));

        handlerOne = new Handler();
        runnableOne = new Runnable() {
            @Override
            public void run() {
                String time = countTimeOne(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                if (time != null) {
                    handlerOne.postDelayed(this, 1000);
                } else {
                    getUserOrderDetail();
                }
            }
        };

        handlerTwo = new Handler();
        runnableTwo = new Runnable() {
            @Override
            public void run() {
                String time3 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                if (time3 != null) {
                    binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time3, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                    handlerTwo.postDelayed(runnableTwo, 1000);
                } else {
                    getUserOrderDetail();
                }
            }
        };

        initListeners();

        getUserOrderDetail();
    }

    private void initListeners() {
        //取消订单
        binding.tvBuyerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new TipDialog(mContext, "取消订单", "是否确定取消订单", new TipDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        HttpApiShopOrder.cancelOrder(binding.getModel().businessOrder.id, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    getUserOrderDetail();
                                }
                                ToastUtil.show(msg);
                            }
                        });
                    }
                });
            }
        });

        //修改地址
        binding.tvBuyerChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.AddressListActivity).withLong(ARouterValueNameConfig.addressId, binding.getModel().businessOrder.addressId).navigation(OrderDetailsActivity.this, 1000);
            }
        });

        //立即付款
        binding.tvBuyerPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ShopParentOrder order = new ShopParentOrder();
                order.nhrAmount = binding.getModel().businessOrder.transactionAmount;
                order.id = binding.getModel().businessOrder.payId;
                goPayMethodSelectDialog(order);
            }
        });

        //申请退款
        binding.tvBuyerApplyRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                BuyerApplyRefundDialog buyerApplyRefundDialog = new BuyerApplyRefundDialog();
                Bundle bundle = new Bundle();
                bundle.putLong("orderId", orderId);
                bundle.putInt("orderStatus", binding.getModel().businessOrder.status);
                buyerApplyRefundDialog.setArguments(bundle);
                buyerApplyRefundDialog.show(getSupportFragmentManager(), "BuyerApplyRefundDialog");
                buyerApplyRefundDialog.setOnBuyerApplyRefundListener(new BuyerApplyRefundDialog.OnBuyerApplyRefundListener() {
                    @Override
                    public void onSuccess() {
                        getUserOrderDetail();
                    }
                });
            }
        });

        //提醒发货
        binding.tvBuyerRemindShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ToastUtil.show("已提醒商家发货");
                ImMessageUtil.getInstance().sendGoodsMessage(binding.getModel().anchorId);
            }
        });

        //确认收货
        binding.tvBuyerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new TipDialog(OrderDetailsActivity.this, "确认收货", "确认收货并完成订单", new TipDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        HttpApiShopOrder.confirmReceipt(binding.getModel().businessOrder.id, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    getUserOrderDetail();
                                } else {
                                    ToastUtil.show(msg);
                                }
                            }
                        });
                    }
                });
            }
        });

        //撤销退款申请
        binding.tvBuyerCancelApplyRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new TipDialog(OrderDetailsActivity.this, "撤销退款申请", "确认撤销退款申请?", new TipDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        HttpApiShopQuiteOrder.cancelApplyRefund(orderId, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    getUserOrderDetail();
                                }
                                ToastUtil.show(msg);
                            }
                        });
                    }
                });
            }
        });

        //填写物流单号
        binding.tvBuyerLogisticsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                BuyerLogisticsNumDialog buyerLogisticsNumDialog = new BuyerLogisticsNumDialog();
                Bundle bundle = new Bundle();
                bundle.putLong("orderId", orderId);
                buyerLogisticsNumDialog.setArguments(bundle);
                buyerLogisticsNumDialog.show(getSupportFragmentManager(), "BuyerLogisticsNumDialog");
                buyerLogisticsNumDialog.setOnBuyerLogisticsNumListener(new BuyerLogisticsNumDialog.OnBuyerLogisticsNumListener() {
                    @Override
                    public void onSuccess() {
                        getUserOrderDetail();
                    }
                });
            }
        });

        //立即发货
        binding.tvSellerDeliverGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.ChooseLogisticsActivity).withLong(ARouterValueNameConfig.orderId, orderId).navigation(OrderDetailsActivity.this, 1001);
            }
        });

        //审核退款申请
        binding.tvSellerReviewRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.ReviewRefundActivity).withLong(ARouterValueNameConfig.orderId, orderId).navigation(OrderDetailsActivity.this, 1002);
            }
        });

        //货物审核
        binding.tvSellerReviewGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new ReviewGoodsDialog(mContext, orderId, new ReviewGoodsDialog.OnClickListener() {
                    @Override
                    public void onSuccess() {
                        getUserOrderDetail();
                    }
                });
            }
        });

        //未通过原因
        binding.layoutRefuseReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new TipDialog(mContext, "审核未通过原因", binding.getModel().businessOrder.auditFailureReason, "我知道了", true, null);
            }
        });

        //进入商铺/进入买家主页
        binding.titleLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (binding.getModel() != null) {
                    if (type == 1) {
                        ARouter.getInstance().build(ARouterPath.ShopActivity).withLong(ARouterValueNameConfig.businessId, binding.getModel().businessOrder.businessId).navigation();
                    } else {
                        ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, binding.getModel().buyUser.userid).navigation();
                    }
                }
            }
        });

        //联系卖家/联系买家
        binding.tvInitiate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (binding.getModel() != null) {
                    if (type == 1) {
                        ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                                .withString(ARouterValueNameConfig.TO_UID, String.valueOf(binding.getModel().anchorId))
                                .withString(ARouterValueNameConfig.Name, binding.getModel().businessOrder.businessName)
                                .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                                .navigation();
                    } else {
                        ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                                .withString(ARouterValueNameConfig.TO_UID, String.valueOf(binding.getModel().buyUser.userid))
                                .withString(ARouterValueNameConfig.Name, binding.getModel().buyUser.username)
                                .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                                .navigation();
                    }
                }
            }
        });

        //卖家物流
        binding.logisticsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.LogisdticsDetailsActivity).withString(ARouterValueNameConfig.orderNo, binding.getModel().businessOrder.orderNum)
                        .withString(ARouterValueNameConfig.logisticsNumber, binding.getModel().logisticsNum).navigation();
            }
        });

        //买家物流
        binding.layoutRefundLogistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.LogisdticsDetailsActivity).withString(ARouterValueNameConfig.orderNo, binding.getModel().businessOrder.orderNum)
                        .withString(ARouterValueNameConfig.logisticsNumber, binding.getModel().refundLogisticsNum).navigation();
            }
        });
    }

    /**
     * 获取订单详情
     */
    private void getUserOrderDetail() {
        HttpApiShopOrder.getUserOrderDetail(orderId, new HttpApiCallBack<ShopUserOrderDetailDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopUserOrderDetailDTO retModel) {
                if (code == 1) {
                    binding.setModel(retModel);
                    refreshOrderInfo();
                    refreshTitleInfo();
                    refreshButtons();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 刷新订单详情
     */
    private void refreshOrderInfo() {
        if (type == 1) {
            ImageLoader.display(binding.getModel().businessOrder.businessLogo, binding.sellserLogoIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            binding.sellserNameTv.setText(binding.getModel().businessOrder.businessName);
        } else {
            ImageLoader.display(binding.getModel().buyUser.avatar, binding.sellserLogoIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            binding.sellserNameTv.setText(binding.getModel().buyUser.username);
        }
        adapter.setList(binding.getModel().subOrderList);
        binding.addTimeTv.setText(new DateUtil(binding.getModel().businessOrder.addTime).getDateToFormat("yyyy-MM-dd HH:mm:ss"));
        binding.payTimeTv.setText(new DateUtil(binding.getModel().parentOrder.payTime).getDateToFormat("yyyy-MM-dd HH:mm:ss"));

        binding.layoutPayTime.setVisibility(View.VISIBLE);
        binding.layoutPayMode.setVisibility(View.VISIBLE);
        binding.layoutPayNum.setVisibility(View.VISIBLE);
        binding.LayoutRefundAmount.setVisibility(View.GONE);
        binding.LayoutRefundTime.setVisibility(View.GONE);
        if (binding.getModel().businessOrder.quitStatus > 0) {
            switch (binding.getModel().businessOrder.quitStatus) {
                case 5://退款完成
                    binding.LayoutRefundAmount.setVisibility(View.VISIBLE);
                    binding.LayoutRefundTime.setVisibility(View.VISIBLE);
                    if (binding.getModel().businessOrder.refundTime != null) {
                        binding.tvRefundTime.setText(new DateUtil(binding.getModel().businessOrder.refundTime).getDateToFormat("yyyy-MM-dd HH:mm:ss"));
                    }
                    break;
            }
        } else {
            switch (binding.getModel().businessOrder.status) {
                case 1://待付款
                case 5://已取消
                    binding.layoutPayTime.setVisibility(View.GONE);
                    binding.layoutPayMode.setVisibility(View.GONE);
                    binding.layoutPayNum.setVisibility(View.GONE);
                    break;
            }
        }

        //卖家物流
        if (!TextUtils.isEmpty(binding.getModel().logisticsNum)) {
            binding.logisticsRl.setVisibility(View.VISIBLE);
            binding.logisticsTimeTv.setText(new DateUtil(binding.getModel().sellerLogisticsTime).getDateToFormat("yyyy-MM-dd HH:mm:ss"));
        }

        //买家物流
        if (!TextUtils.isEmpty(binding.getModel().refundLogisticsNum)) {
            binding.layoutRefundLogistics.setVisibility(View.VISIBLE);
            binding.tvRefundLogisticsNum.setText(binding.getModel().refundLogisticsName + " " + binding.getModel().refundLogisticsNum);
            binding.tvRefundLogisticsTime.setText(new DateUtil(binding.getModel().buyerLogisticsTime).getDateToFormat("yyyy-MM-dd HH:mm:ss"));
        }
    }

    /**
     * 刷新头部标题
     */
    private void refreshTitleInfo() {
        //退货进度条
        if (binding.getModel().processList != null && binding.getModel().processList.size() > 0) {
            binding.rvReturnProcess.setVisibility(View.VISIBLE);
            orderReturnProcessAdapter.setData(binding.getModel().processList);
        } else {
            binding.rvReturnProcess.setVisibility(View.GONE);
        }

        binding.tvOrderStatus.setVisibility(View.INVISIBLE);
        binding.tvOrderStatus.setGravity(Gravity.CENTER_HORIZONTAL);
        binding.tvOrderInfo.setVisibility(View.INVISIBLE);
        binding.tvOrderInfo.setGravity(Gravity.CENTER_HORIZONTAL);
        binding.ivShopOrderStatus.setVisibility(View.GONE);
        binding.layoutTimeCountdown.setVisibility(View.GONE);
        binding.layoutRefuseReason.setVisibility(View.GONE);

        //取消倒计时
        handlerOne.removeCallbacks(runnableOne);
        handlerTwo.removeCallbacks(runnableTwo);

        if (binding.getModel().businessOrder.quitStatus > 0) {
            switch (binding.getModel().businessOrder.quitStatus) {
                case 1://待审核
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("订单退款中");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    String time11 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    timeInfo = " 后未审核订单将自动退款";
                    if (time11 != null) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time11, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                        handlerTwo.postDelayed(runnableTwo, 1000);
                    } else {
                        binding.tvOrderInfo.setText("稍后将自动退款");
                    }
                    break;
                case 2://待发货
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("订单退款中");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    String time12 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    timeInfo = " 后未填写物流单号订单将自动取消退款";
                    if (time12 != null) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time12, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                        handlerTwo.postDelayed(runnableTwo, 1000);
                    } else {
                        binding.tvOrderInfo.setText("稍后将自动取消退款");
                    }
                    break;
                case 3://待收货
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("订单退款中");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    String time13 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    timeInfo = " 后卖家自动确认收货";
                    if (time13 != null) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time13, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                        handlerTwo.postDelayed(runnableTwo, 1000);
                    } else {
                        binding.tvOrderInfo.setText("稍后卖家将自动确认收货");
                    }
                    break;
                case 4://退款中
                case 6://退款失败
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("订单退款中");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    if (type == 1) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor("3个工作日内", "#C12A2A") + WordUtil.strAddColor(" 款项自动退回您的付款账户", "#ffffff")));
                    } else {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor("3个工作日内", "#C12A2A") + WordUtil.strAddColor(" 款项自动退回买家的付款账户", "#ffffff")));
                    }
                    break;
                case 5://退款完成
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("订单已关闭");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    if (type == 1) {
                        binding.tvOrderInfo.setText("退款成功，款项已原路退回您的支付账户");
                    } else {
                        binding.tvOrderInfo.setText("退款成功，款项已原路退回买家的支付账户");
                    }
                    binding.ivShopOrderStatus.setVisibility(View.VISIBLE);
                    binding.ivShopOrderStatus.setImageResource(R.mipmap.icon_shop_order_cancel);
                    break;
                case 7://审核拒绝
                    binding.layoutRefuseReason.setVisibility(View.VISIBLE);
                    if (binding.getModel().businessOrder.status == 2) {//待发货
                        binding.tvOrderStatus.setVisibility(View.VISIBLE);
                        binding.tvOrderStatus.setText("等待卖家发货");
                        binding.tvOrderInfo.setVisibility(View.VISIBLE);
                        String time17 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                        timeInfo = " 内未发货订单自动取消";
                        if (time17 != null) {
                            binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time17, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                            handlerTwo.postDelayed(runnableTwo, 1000);
                        } else {
                            binding.tvOrderInfo.setText("稍后订单自动取消");
                        }
                    } else if (binding.getModel().businessOrder.status == 3) {//待收货
                        binding.tvOrderStatus.setVisibility(View.VISIBLE);
                        binding.tvOrderStatus.setText("等待买家收货");
                        binding.tvOrderInfo.setVisibility(View.VISIBLE);
                        String time172 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                        timeInfo = " 后自动确认收货";
                        if (time172 != null) {
                            binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time172, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                            handlerTwo.postDelayed(runnableTwo, 1000);
                        } else {
                            binding.tvOrderInfo.setText("稍后自动确认收货");
                        }
                    } else if (binding.getModel().businessOrder.status == 4) {//已完成
                        binding.tvOrderStatus.setVisibility(View.VISIBLE);
                        binding.tvOrderStatus.setText("订单已完成");
                        binding.tvOrderInfo.setVisibility(View.VISIBLE);
                        binding.tvOrderInfo.setText("已确认收货");
                        binding.ivShopOrderStatus.setVisibility(View.VISIBLE);
                        binding.ivShopOrderStatus.setImageResource(R.mipmap.icon_shop_order_confirm);
                        binding.rvReturnProcess.setVisibility(View.GONE);
                        binding.layoutRefuseReason.setVisibility(View.GONE);
                    }
                    break;
            }
        } else {
            switch (binding.getModel().businessOrder.status) {
                case 1://待付款
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setGravity(Gravity.START);
                    binding.tvOrderStatus.setText("等待买家付款");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    binding.tvOrderInfo.setGravity(Gravity.START);
                    binding.tvOrderInfo.setText("指定时间未付款订单自动取消");
                    binding.layoutTimeCountdown.setVisibility(View.VISIBLE);
                    String time21 = countTimeOne(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    if (!TextUtils.isEmpty(time21)) {
                        handlerOne.postDelayed(runnableOne, 1000);
                    }
                    break;
                case 2://待发货
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setGravity(Gravity.START);
                    binding.tvOrderStatus.setText("等待卖家发货");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    binding.tvOrderInfo.setGravity(Gravity.START);
                    binding.tvOrderInfo.setText("指定时间内未发货订单自动取消");
                    binding.layoutTimeCountdown.setVisibility(View.VISIBLE);
                    String time22 = countTimeOne(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    if (!TextUtils.isEmpty(time22)) {
                        handlerOne.postDelayed(runnableOne, 1000);
                    }
                    break;
                case 3://待收货
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("等待买家收货");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    String time23 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    timeInfo = " 后自动确认收货";
                    if (time23 != null) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time23, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                        handlerTwo.postDelayed(runnableTwo, 1000);
                    } else {
                        binding.tvOrderInfo.setText("稍后自动确认收货");
                    }
                    break;
                case 4://已完成
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("订单已完成");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    binding.tvOrderInfo.setText("已确认收货");
                    binding.ivShopOrderStatus.setVisibility(View.VISIBLE);
                    binding.ivShopOrderStatus.setImageResource(R.mipmap.icon_shop_order_confirm);
                    break;
                case 5://已取消
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("订单已关闭");
                    binding.ivShopOrderStatus.setVisibility(View.VISIBLE);
                    binding.ivShopOrderStatus.setImageResource(R.mipmap.icon_shop_order_cancel);
                    break;
            }
        }

        //修改滚动布局顶部的空白高度
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.viewTransparent.getLayoutParams();
                layoutParams.height = binding.layoutTitleYellow.getHeight() - DpUtil.dp2px(12);
                binding.viewTransparent.setLayoutParams(layoutParams);
            }
        }, 300);
    }

    /**
     * 刷新操作按钮
     */
    private void refreshButtons() {
        //隐藏买家操作按钮
        binding.layoutBuyer.setVisibility(View.GONE);
        binding.tvBuyerCancel.setVisibility(View.GONE);
        binding.tvBuyerChangeAddress.setVisibility(View.GONE);
        binding.tvBuyerPay.setVisibility(View.GONE);
        binding.tvBuyerApplyRefund.setVisibility(View.GONE);
        binding.tvBuyerRemindShipment.setVisibility(View.GONE);
        binding.tvBuyerConfirm.setVisibility(View.GONE);
        binding.tvBuyerCancelApplyRefund.setVisibility(View.GONE);
        binding.tvBuyerLogisticsNum.setVisibility(View.GONE);

        //隐藏卖家操作按钮
        binding.layoutSeller.setVisibility(View.GONE);
        binding.layoutSellerDeliverGoods.setVisibility(View.GONE);
        binding.tvSellerReviewRefund.setVisibility(View.GONE);
        binding.tvSellerReviewGoods.setVisibility(View.GONE);

        if (type == 1) {//买家
            if (binding.getModel().businessOrder.quitStatus > 0) {
                switch (binding.getModel().businessOrder.quitStatus) {
                    case 1://待审核
                    case 3://待收货
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerCancelApplyRefund.setVisibility(View.VISIBLE);
                        break;
                    case 2://待发货
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerCancelApplyRefund.setVisibility(View.VISIBLE);
                        binding.tvBuyerLogisticsNum.setVisibility(View.VISIBLE);
                        break;
                    case 4://退款中
                        break;
                    case 5://退款完成
                        break;
                    case 6://退款失败
                        break;
                    case 7://审核拒绝
                        if (binding.getModel().businessOrder.status == 2) {//待发货
                            binding.layoutBuyer.setVisibility(View.VISIBLE);
                            binding.tvBuyerApplyRefund.setVisibility(View.VISIBLE);
                            binding.tvBuyerRemindShipment.setVisibility(View.VISIBLE);
                        } else if (binding.getModel().businessOrder.status == 3) {//待收货
                            binding.layoutBuyer.setVisibility(View.VISIBLE);
                            binding.tvBuyerApplyRefund.setVisibility(View.VISIBLE);
                            binding.tvBuyerConfirm.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            } else {
                switch (binding.getModel().businessOrder.status) {
                    case 1://待付款
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerCancel.setVisibility(View.VISIBLE);
                        binding.tvBuyerChangeAddress.setVisibility(View.VISIBLE);
                        binding.tvBuyerPay.setVisibility(View.VISIBLE);
                        break;
                    case 2://待发货
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerApplyRefund.setVisibility(View.VISIBLE);
                        binding.tvBuyerRemindShipment.setVisibility(View.VISIBLE);
                        break;
                    case 3://待收货
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerApplyRefund.setVisibility(View.VISIBLE);
                        binding.tvBuyerConfirm.setVisibility(View.VISIBLE);
                        break;
                    case 4://已完成
                        break;
                    case 5://已取消
                        break;
                }
            }
        } else {//卖家
            if (binding.getModel().businessOrder.quitStatus > 0) {
                switch (binding.getModel().businessOrder.quitStatus) {
                    case 1://待审核
                        binding.layoutSeller.setVisibility(View.VISIBLE);
                        binding.tvSellerReviewRefund.setVisibility(View.VISIBLE);
                        break;
                    case 2://待发货
                        break;
                    case 3://待收货
                        binding.layoutSeller.setVisibility(View.VISIBLE);
                        binding.tvSellerReviewGoods.setVisibility(View.VISIBLE);
                        break;
                    case 4://退款中
                        break;
                    case 5://退款完成
                        break;
                    case 6://退款失败
                        break;
                    case 7://审核拒绝
                        if (binding.getModel().businessOrder.status == 2) {//待发货
                            binding.layoutSeller.setVisibility(View.VISIBLE);
                            binding.layoutSellerDeliverGoods.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            } else {
                switch (binding.getModel().businessOrder.status) {
                    case 1://待付款
                        break;
                    case 2://待发货
                        binding.layoutSeller.setVisibility(View.VISIBLE);
                        binding.layoutSellerDeliverGoods.setVisibility(View.VISIBLE);
                        break;
                    case 3://待收货
                        break;
                    case 4://已完成
                        break;
                    case 5://已取消
                        break;
                }
            }
        }
    }

    /**
     * 修改订单地址信息
     */
    public void updateOrderAddress(final ShopAddress shopAddress) {
        HttpApiShopOrder.updateOrderAddress(shopAddress.id, binding.getModel().businessOrder.id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show("地址修改成功");
                    binding.getModel().businessOrder.addressId = shopAddress.id;
                    binding.nameTv.setText(shopAddress.userName + " " + shopAddress.phoneNum);
                    binding.addressTv.setText(shopAddress.pro + " " + shopAddress.city + " " + shopAddress.area + " " + shopAddress.address);

                }
            }
        });
    }

    /**
     * 进入支付方式选择弹框
     */
    private void goPayMethodSelectDialog(ShopParentOrder order) {
        PayMethodSelectDialog fragment = new PayMethodSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("shop", 1);
        bundle.putParcelable("ShopParentOrder", order);
        bundle.putLong("orderId", order.id);
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(), "PayMethodSelectDialog");
    }

    /**
     * 计算时间
     */
    private String countTimeOne(DateUtil dateStart, DateUtil dateEnd) {
        long mss = dateEnd.toDate().getTime() - dateStart.toDate().getTime();
        if (mss > 1000) {
            List<Long> times = DateUtil.getTimeList(mss);
            if (mss > 24 * 60 * 60 * 1000) {//大于1天
                binding.tvTimeCountdown.setVisibility(View.VISIBLE);
                binding.tvTimeCountdownUnit.setVisibility(View.VISIBLE);
                binding.tvTimeCountdown1.setVisibility(View.VISIBLE);
                binding.tvTimeCountdownUnit1.setVisibility(View.VISIBLE);
                binding.tvTimeCountdown2.setVisibility(View.VISIBLE);
                binding.tvTimeCountdownUnit2.setVisibility(View.VISIBLE);

                binding.tvTimeCountdown.setText(times.get(0) + "");
                binding.tvTimeCountdown1.setText(times.get(1) + "");
                binding.tvTimeCountdown2.setText(times.get(2) + "");

                return times.get(0) + "天" + times.get(1) + "时" + times.get(2) + "分";
            } else if (mss > 60 * 60 * 1000) {//大于1小时
                binding.tvTimeCountdown.setVisibility(View.VISIBLE);
                binding.tvTimeCountdownUnit.setVisibility(View.GONE);
                binding.tvTimeCountdown1.setVisibility(View.GONE);
                binding.tvTimeCountdownUnit1.setVisibility(View.GONE);
                binding.tvTimeCountdown2.setVisibility(View.GONE);
                binding.tvTimeCountdownUnit2.setVisibility(View.GONE);

                String str = String.format(Locale.CHINA, "%02d:%02d:%02d", times.get(1), times.get(2), times.get(3));
                binding.tvTimeCountdown.setText(str);

                return str;
            } else {
                binding.tvTimeCountdown.setVisibility(View.VISIBLE);
                binding.tvTimeCountdownUnit.setVisibility(View.GONE);
                binding.tvTimeCountdown1.setVisibility(View.GONE);
                binding.tvTimeCountdownUnit1.setVisibility(View.GONE);
                binding.tvTimeCountdown2.setVisibility(View.GONE);
                binding.tvTimeCountdownUnit2.setVisibility(View.GONE);

                String str = String.format(Locale.CHINA, "%02d:%02d", times.get(2), times.get(3));
                binding.tvTimeCountdown.setText(str);

                return str;
            }
        } else {
            binding.tvTimeCountdown.setVisibility(View.VISIBLE);
            binding.tvTimeCountdownUnit.setVisibility(View.GONE);
            binding.tvTimeCountdown1.setVisibility(View.GONE);
            binding.tvTimeCountdownUnit1.setVisibility(View.GONE);
            binding.tvTimeCountdown2.setVisibility(View.GONE);
            binding.tvTimeCountdownUnit2.setVisibility(View.GONE);

            binding.tvTimeCountdown.setText("00:00");

            return null;
        }
    }

    /**
     * 计算时间
     */
    private String countTimeTwo(DateUtil dateStart, DateUtil dateEnd) {
        long mss = dateEnd.toDate().getTime() - dateStart.toDate().getTime();
        if (mss > 1000) {
            List<Long> times = DateUtil.getTimeList(mss);
            if (mss > 24 * 60 * 60 * 1000) {//大于1天
                return times.get(0) + "天" + times.get(1) + "时" + times.get(2) + "分";
            } else if (mss > 60 * 60 * 1000) {//大于1小时
                return times.get(1) + "时" + times.get(2) + "分" + times.get(3) + "秒";
            } else if (mss > 60 * 1000) {//大于1分钟
                return times.get(2) + "分" + times.get(3) + "秒";
            } else {
                return times.get(3) + "秒";
            }
        } else {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {//修改收货地址
                ShopAddress shopAddress = (ShopAddress) data.getParcelableExtra(ARouterValueNameConfig.addressBean);
                if (shopAddress != null) {
                    updateOrderAddress(shopAddress);
                }
            } else if (requestCode == 1001) {//立即发货
                getUserOrderDetail();
            } else if (requestCode == 1002) {//审核退款申请
                getUserOrderDetail();
            }
        }
    }

    @Override
    protected void onDestroy() {
        handlerOne.removeCallbacks(runnableOne);
        handlerTwo.removeCallbacks(runnableTwo);
        super.onDestroy();
    }
}
