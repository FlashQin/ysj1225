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
 * ????????????
 */
@Route(path = ARouterPath.OrderDetailsActivity)
public class OrderDetailsActivity extends SBaseActivity<ActivityOrderDetailsBinding, OrderDetailsViewModel> {
    @Autowired(name = ARouterValueNameConfig.TYPE)
    public int type = 1;// 1 ???????????????2 ???????????????
    @Autowired(name = ARouterValueNameConfig.orderId)
    public long orderId = 0;

    //???????????????????????????
    private Handler handlerOne;
    private Runnable runnableOne;

    //???????????????????????????
    private Handler handlerTwo;
    private Runnable runnableTwo;
    //????????????????????????
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
            binding.tvInitiate.setText("????????????");
        } else {
            binding.tvInitiate.setText("????????????");
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
        //????????????
        binding.tvBuyerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new TipDialog(mContext, "????????????", "????????????????????????", new TipDialog.OnClickListener() {
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

        //????????????
        binding.tvBuyerChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.AddressListActivity).withLong(ARouterValueNameConfig.addressId, binding.getModel().businessOrder.addressId).navigation(OrderDetailsActivity.this, 1000);
            }
        });

        //????????????
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

        //????????????
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

        //????????????
        binding.tvBuyerRemindShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ToastUtil.show("?????????????????????");
                ImMessageUtil.getInstance().sendGoodsMessage(binding.getModel().anchorId);
            }
        });

        //????????????
        binding.tvBuyerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new TipDialog(OrderDetailsActivity.this, "????????????", "???????????????????????????", new TipDialog.OnClickListener() {
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

        //??????????????????
        binding.tvBuyerCancelApplyRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new TipDialog(OrderDetailsActivity.this, "??????????????????", "?????????????????????????", new TipDialog.OnClickListener() {
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

        //??????????????????
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

        //????????????
        binding.tvSellerDeliverGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.ChooseLogisticsActivity).withLong(ARouterValueNameConfig.orderId, orderId).navigation(OrderDetailsActivity.this, 1001);
            }
        });

        //??????????????????
        binding.tvSellerReviewRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.ReviewRefundActivity).withLong(ARouterValueNameConfig.orderId, orderId).navigation(OrderDetailsActivity.this, 1002);
            }
        });

        //????????????
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

        //???????????????
        binding.layoutRefuseReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new TipDialog(mContext, "?????????????????????", binding.getModel().businessOrder.auditFailureReason, "????????????", true, null);
            }
        });

        //????????????/??????????????????
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

        //????????????/????????????
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

        //????????????
        binding.logisticsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.LogisdticsDetailsActivity).withString(ARouterValueNameConfig.orderNo, binding.getModel().businessOrder.orderNum)
                        .withString(ARouterValueNameConfig.logisticsNumber, binding.getModel().logisticsNum).navigation();
            }
        });

        //????????????
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
     * ??????????????????
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
     * ??????????????????
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
                case 5://????????????
                    binding.LayoutRefundAmount.setVisibility(View.VISIBLE);
                    binding.LayoutRefundTime.setVisibility(View.VISIBLE);
                    if (binding.getModel().businessOrder.refundTime != null) {
                        binding.tvRefundTime.setText(new DateUtil(binding.getModel().businessOrder.refundTime).getDateToFormat("yyyy-MM-dd HH:mm:ss"));
                    }
                    break;
            }
        } else {
            switch (binding.getModel().businessOrder.status) {
                case 1://?????????
                case 5://?????????
                    binding.layoutPayTime.setVisibility(View.GONE);
                    binding.layoutPayMode.setVisibility(View.GONE);
                    binding.layoutPayNum.setVisibility(View.GONE);
                    break;
            }
        }

        //????????????
        if (!TextUtils.isEmpty(binding.getModel().logisticsNum)) {
            binding.logisticsRl.setVisibility(View.VISIBLE);
            binding.logisticsTimeTv.setText(new DateUtil(binding.getModel().sellerLogisticsTime).getDateToFormat("yyyy-MM-dd HH:mm:ss"));
        }

        //????????????
        if (!TextUtils.isEmpty(binding.getModel().refundLogisticsNum)) {
            binding.layoutRefundLogistics.setVisibility(View.VISIBLE);
            binding.tvRefundLogisticsNum.setText(binding.getModel().refundLogisticsName + " " + binding.getModel().refundLogisticsNum);
            binding.tvRefundLogisticsTime.setText(new DateUtil(binding.getModel().buyerLogisticsTime).getDateToFormat("yyyy-MM-dd HH:mm:ss"));
        }
    }

    /**
     * ??????????????????
     */
    private void refreshTitleInfo() {
        //???????????????
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

        //???????????????
        handlerOne.removeCallbacks(runnableOne);
        handlerTwo.removeCallbacks(runnableTwo);

        if (binding.getModel().businessOrder.quitStatus > 0) {
            switch (binding.getModel().businessOrder.quitStatus) {
                case 1://?????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("???????????????");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    String time11 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    timeInfo = " ?????????????????????????????????";
                    if (time11 != null) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time11, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                        handlerTwo.postDelayed(runnableTwo, 1000);
                    } else {
                        binding.tvOrderInfo.setText("?????????????????????");
                    }
                    break;
                case 2://?????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("???????????????");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    String time12 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    timeInfo = " ???????????????????????????????????????????????????";
                    if (time12 != null) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time12, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                        handlerTwo.postDelayed(runnableTwo, 1000);
                    } else {
                        binding.tvOrderInfo.setText("???????????????????????????");
                    }
                    break;
                case 3://?????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("???????????????");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    String time13 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    timeInfo = " ???????????????????????????";
                    if (time13 != null) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time13, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                        handlerTwo.postDelayed(runnableTwo, 1000);
                    } else {
                        binding.tvOrderInfo.setText("?????????????????????????????????");
                    }
                    break;
                case 4://?????????
                case 6://????????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("???????????????");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    if (type == 1) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor("3???????????????", "#C12A2A") + WordUtil.strAddColor(" ????????????????????????????????????", "#ffffff")));
                    } else {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor("3???????????????", "#C12A2A") + WordUtil.strAddColor(" ???????????????????????????????????????", "#ffffff")));
                    }
                    break;
                case 5://????????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("???????????????");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    if (type == 1) {
                        binding.tvOrderInfo.setText("??????????????????????????????????????????????????????");
                    } else {
                        binding.tvOrderInfo.setText("?????????????????????????????????????????????????????????");
                    }
                    binding.ivShopOrderStatus.setVisibility(View.VISIBLE);
                    binding.ivShopOrderStatus.setImageResource(R.mipmap.icon_shop_order_cancel);
                    break;
                case 7://????????????
                    binding.layoutRefuseReason.setVisibility(View.VISIBLE);
                    if (binding.getModel().businessOrder.status == 2) {//?????????
                        binding.tvOrderStatus.setVisibility(View.VISIBLE);
                        binding.tvOrderStatus.setText("??????????????????");
                        binding.tvOrderInfo.setVisibility(View.VISIBLE);
                        String time17 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                        timeInfo = " ??????????????????????????????";
                        if (time17 != null) {
                            binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time17, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                            handlerTwo.postDelayed(runnableTwo, 1000);
                        } else {
                            binding.tvOrderInfo.setText("????????????????????????");
                        }
                    } else if (binding.getModel().businessOrder.status == 3) {//?????????
                        binding.tvOrderStatus.setVisibility(View.VISIBLE);
                        binding.tvOrderStatus.setText("??????????????????");
                        binding.tvOrderInfo.setVisibility(View.VISIBLE);
                        String time172 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                        timeInfo = " ?????????????????????";
                        if (time172 != null) {
                            binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time172, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                            handlerTwo.postDelayed(runnableTwo, 1000);
                        } else {
                            binding.tvOrderInfo.setText("????????????????????????");
                        }
                    } else if (binding.getModel().businessOrder.status == 4) {//?????????
                        binding.tvOrderStatus.setVisibility(View.VISIBLE);
                        binding.tvOrderStatus.setText("???????????????");
                        binding.tvOrderInfo.setVisibility(View.VISIBLE);
                        binding.tvOrderInfo.setText("???????????????");
                        binding.ivShopOrderStatus.setVisibility(View.VISIBLE);
                        binding.ivShopOrderStatus.setImageResource(R.mipmap.icon_shop_order_confirm);
                        binding.rvReturnProcess.setVisibility(View.GONE);
                        binding.layoutRefuseReason.setVisibility(View.GONE);
                    }
                    break;
            }
        } else {
            switch (binding.getModel().businessOrder.status) {
                case 1://?????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setGravity(Gravity.START);
                    binding.tvOrderStatus.setText("??????????????????");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    binding.tvOrderInfo.setGravity(Gravity.START);
                    binding.tvOrderInfo.setText("???????????????????????????????????????");
                    binding.layoutTimeCountdown.setVisibility(View.VISIBLE);
                    String time21 = countTimeOne(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    if (!TextUtils.isEmpty(time21)) {
                        handlerOne.postDelayed(runnableOne, 1000);
                    }
                    break;
                case 2://?????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setGravity(Gravity.START);
                    binding.tvOrderStatus.setText("??????????????????");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    binding.tvOrderInfo.setGravity(Gravity.START);
                    binding.tvOrderInfo.setText("??????????????????????????????????????????");
                    binding.layoutTimeCountdown.setVisibility(View.VISIBLE);
                    String time22 = countTimeOne(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    if (!TextUtils.isEmpty(time22)) {
                        handlerOne.postDelayed(runnableOne, 1000);
                    }
                    break;
                case 3://?????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("??????????????????");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    String time23 = countTimeTwo(new DateUtil(), new DateUtil(binding.getModel().closingDate));
                    timeInfo = " ?????????????????????";
                    if (time23 != null) {
                        binding.tvOrderInfo.setText(WordUtil.strToSpanned(WordUtil.strAddColor(time23, "#C12A2A") + WordUtil.strAddColor(timeInfo, "#ffffff")));
                        handlerTwo.postDelayed(runnableTwo, 1000);
                    } else {
                        binding.tvOrderInfo.setText("????????????????????????");
                    }
                    break;
                case 4://?????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("???????????????");
                    binding.tvOrderInfo.setVisibility(View.VISIBLE);
                    binding.tvOrderInfo.setText("???????????????");
                    binding.ivShopOrderStatus.setVisibility(View.VISIBLE);
                    binding.ivShopOrderStatus.setImageResource(R.mipmap.icon_shop_order_confirm);
                    break;
                case 5://?????????
                    binding.tvOrderStatus.setVisibility(View.VISIBLE);
                    binding.tvOrderStatus.setText("???????????????");
                    binding.ivShopOrderStatus.setVisibility(View.VISIBLE);
                    binding.ivShopOrderStatus.setImageResource(R.mipmap.icon_shop_order_cancel);
                    break;
            }
        }

        //???????????????????????????????????????
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
     * ??????????????????
     */
    private void refreshButtons() {
        //????????????????????????
        binding.layoutBuyer.setVisibility(View.GONE);
        binding.tvBuyerCancel.setVisibility(View.GONE);
        binding.tvBuyerChangeAddress.setVisibility(View.GONE);
        binding.tvBuyerPay.setVisibility(View.GONE);
        binding.tvBuyerApplyRefund.setVisibility(View.GONE);
        binding.tvBuyerRemindShipment.setVisibility(View.GONE);
        binding.tvBuyerConfirm.setVisibility(View.GONE);
        binding.tvBuyerCancelApplyRefund.setVisibility(View.GONE);
        binding.tvBuyerLogisticsNum.setVisibility(View.GONE);

        //????????????????????????
        binding.layoutSeller.setVisibility(View.GONE);
        binding.layoutSellerDeliverGoods.setVisibility(View.GONE);
        binding.tvSellerReviewRefund.setVisibility(View.GONE);
        binding.tvSellerReviewGoods.setVisibility(View.GONE);

        if (type == 1) {//??????
            if (binding.getModel().businessOrder.quitStatus > 0) {
                switch (binding.getModel().businessOrder.quitStatus) {
                    case 1://?????????
                    case 3://?????????
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerCancelApplyRefund.setVisibility(View.VISIBLE);
                        break;
                    case 2://?????????
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerCancelApplyRefund.setVisibility(View.VISIBLE);
                        binding.tvBuyerLogisticsNum.setVisibility(View.VISIBLE);
                        break;
                    case 4://?????????
                        break;
                    case 5://????????????
                        break;
                    case 6://????????????
                        break;
                    case 7://????????????
                        if (binding.getModel().businessOrder.status == 2) {//?????????
                            binding.layoutBuyer.setVisibility(View.VISIBLE);
                            binding.tvBuyerApplyRefund.setVisibility(View.VISIBLE);
                            binding.tvBuyerRemindShipment.setVisibility(View.VISIBLE);
                        } else if (binding.getModel().businessOrder.status == 3) {//?????????
                            binding.layoutBuyer.setVisibility(View.VISIBLE);
                            binding.tvBuyerApplyRefund.setVisibility(View.VISIBLE);
                            binding.tvBuyerConfirm.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            } else {
                switch (binding.getModel().businessOrder.status) {
                    case 1://?????????
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerCancel.setVisibility(View.VISIBLE);
                        binding.tvBuyerChangeAddress.setVisibility(View.VISIBLE);
                        binding.tvBuyerPay.setVisibility(View.VISIBLE);
                        break;
                    case 2://?????????
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerApplyRefund.setVisibility(View.VISIBLE);
                        binding.tvBuyerRemindShipment.setVisibility(View.VISIBLE);
                        break;
                    case 3://?????????
                        binding.layoutBuyer.setVisibility(View.VISIBLE);
                        binding.tvBuyerApplyRefund.setVisibility(View.VISIBLE);
                        binding.tvBuyerConfirm.setVisibility(View.VISIBLE);
                        break;
                    case 4://?????????
                        break;
                    case 5://?????????
                        break;
                }
            }
        } else {//??????
            if (binding.getModel().businessOrder.quitStatus > 0) {
                switch (binding.getModel().businessOrder.quitStatus) {
                    case 1://?????????
                        binding.layoutSeller.setVisibility(View.VISIBLE);
                        binding.tvSellerReviewRefund.setVisibility(View.VISIBLE);
                        break;
                    case 2://?????????
                        break;
                    case 3://?????????
                        binding.layoutSeller.setVisibility(View.VISIBLE);
                        binding.tvSellerReviewGoods.setVisibility(View.VISIBLE);
                        break;
                    case 4://?????????
                        break;
                    case 5://????????????
                        break;
                    case 6://????????????
                        break;
                    case 7://????????????
                        if (binding.getModel().businessOrder.status == 2) {//?????????
                            binding.layoutSeller.setVisibility(View.VISIBLE);
                            binding.layoutSellerDeliverGoods.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            } else {
                switch (binding.getModel().businessOrder.status) {
                    case 1://?????????
                        break;
                    case 2://?????????
                        binding.layoutSeller.setVisibility(View.VISIBLE);
                        binding.layoutSellerDeliverGoods.setVisibility(View.VISIBLE);
                        break;
                    case 3://?????????
                        break;
                    case 4://?????????
                        break;
                    case 5://?????????
                        break;
                }
            }
        }
    }

    /**
     * ????????????????????????
     */
    public void updateOrderAddress(final ShopAddress shopAddress) {
        HttpApiShopOrder.updateOrderAddress(shopAddress.id, binding.getModel().businessOrder.id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show("??????????????????");
                    binding.getModel().businessOrder.addressId = shopAddress.id;
                    binding.nameTv.setText(shopAddress.userName + " " + shopAddress.phoneNum);
                    binding.addressTv.setText(shopAddress.pro + " " + shopAddress.city + " " + shopAddress.area + " " + shopAddress.address);

                }
            }
        });
    }

    /**
     * ??????????????????????????????
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
     * ????????????
     */
    private String countTimeOne(DateUtil dateStart, DateUtil dateEnd) {
        long mss = dateEnd.toDate().getTime() - dateStart.toDate().getTime();
        if (mss > 1000) {
            List<Long> times = DateUtil.getTimeList(mss);
            if (mss > 24 * 60 * 60 * 1000) {//??????1???
                binding.tvTimeCountdown.setVisibility(View.VISIBLE);
                binding.tvTimeCountdownUnit.setVisibility(View.VISIBLE);
                binding.tvTimeCountdown1.setVisibility(View.VISIBLE);
                binding.tvTimeCountdownUnit1.setVisibility(View.VISIBLE);
                binding.tvTimeCountdown2.setVisibility(View.VISIBLE);
                binding.tvTimeCountdownUnit2.setVisibility(View.VISIBLE);

                binding.tvTimeCountdown.setText(times.get(0) + "");
                binding.tvTimeCountdown1.setText(times.get(1) + "");
                binding.tvTimeCountdown2.setText(times.get(2) + "");

                return times.get(0) + "???" + times.get(1) + "???" + times.get(2) + "???";
            } else if (mss > 60 * 60 * 1000) {//??????1??????
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
     * ????????????
     */
    private String countTimeTwo(DateUtil dateStart, DateUtil dateEnd) {
        long mss = dateEnd.toDate().getTime() - dateStart.toDate().getTime();
        if (mss > 1000) {
            List<Long> times = DateUtil.getTimeList(mss);
            if (mss > 24 * 60 * 60 * 1000) {//??????1???
                return times.get(0) + "???" + times.get(1) + "???" + times.get(2) + "???";
            } else if (mss > 60 * 60 * 1000) {//??????1??????
                return times.get(1) + "???" + times.get(2) + "???" + times.get(3) + "???";
            } else if (mss > 60 * 1000) {//??????1??????
                return times.get(2) + "???" + times.get(3) + "???";
            } else {
                return times.get(3) + "???";
            }
        } else {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {//??????????????????
                ShopAddress shopAddress = (ShopAddress) data.getParcelableExtra(ARouterValueNameConfig.addressBean);
                if (shopAddress != null) {
                    updateOrderAddress(shopAddress);
                }
            } else if (requestCode == 1001) {//????????????
                getUserOrderDetail();
            } else if (requestCode == 1002) {//??????????????????
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
