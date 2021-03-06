package com.kalacheng.shopping.buyer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.busshop.httpApi.HttpApiShopQuiteOrder;
import com.kalacheng.busshop.model.ShopParentOrder;
import com.kalacheng.commonview.dialog.PayMethodSelectDialog;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.base.event.ShopOrderRefreshEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.buyer.TipDialog;
import com.kalacheng.shopping.buyer.activity.OrderManageActivity1;
import com.kalacheng.shopping.buyer.bean.ShopUserOrderBean;
import com.kalacheng.shopping.buyer.dialog.BuyerLogisticsNumDialog;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class OrderManageListAdapter1 extends RecyclerView.Adapter<OrderManageListAdapter1.Holder> {

    private List<ShopUserOrderBean> list;
    private Context context;

    private OrderManageActivity1 activity1;

    public OrderManageListAdapter1() {
        this.list = new ArrayList<>();
    }

    public List<ShopUserOrderBean> getList() {
        return list;
    }

    public void setActivity1(OrderManageActivity1 activity1) {
        this.activity1 = activity1;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == 0) {
            return new TitleHolder(inflater.inflate(R.layout.item_order_title, parent, false));
        } else if (viewType == 1) {
            return new OrderHolder(inflater.inflate(R.layout.item_order_goods, parent, false));
        } else {
            return new EndHolder(inflater.inflate(R.layout.item_order_end_0, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        if (payload == null) {
            holder.setData();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }

    class Holder extends RecyclerView.ViewHolder {
        ShopUserOrderBean bean;

        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        protected void setData() {
            bean = list.get(getBindingAdapterPosition());
        }

    }

    class TitleHolder extends Holder {
        RoundedImageView sellserLogoIv;
        TextView sellserNameTv;
        LinearLayout titleLl;
        TextView tvOrderStatus;

        TitleHolder(@NonNull View itemView) {
            super(itemView);
            sellserLogoIv = itemView.findViewById(R.id.sellserLogoIv);
            sellserNameTv = itemView.findViewById(R.id.sellserNameTv);
            titleLl = itemView.findViewById(R.id.titleLl);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.ShopActivity).withLong(ARouterValueNameConfig.businessId, bean.getBusinessOrder().businessId).navigation();
                }
            });
        }

        @Override
        protected void setData() {
            super.setData();
            ImageLoader.display(bean.getBusinessOrder().businessLogo, sellserLogoIv);
            sellserNameTv.setText(bean.getBusinessOrder().businessName);
            tvOrderStatus.setVisibility(View.VISIBLE);

            if (bean.getBusinessOrder().quitStatus > 0) {
                switch (bean.getBusinessOrder().quitStatus) {
                    case 1://?????????
                    case 2://?????????
                    case 3://?????????
                    case 4://?????????
                    case 6://????????????
                        tvOrderStatus.setTextColor(Color.parseColor("#FC3A3A"));
                        tvOrderStatus.setText("?????????");
                        break;
                    case 5://????????????
                        tvOrderStatus.setTextColor(Color.parseColor("#999999"));
                        tvOrderStatus.setText("?????????");
                        break;
                    case 7://????????????
                        if (bean.getBusinessOrder().status == 2) {//?????????
                            tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                            tvOrderStatus.setText("?????????");
                        } else if (bean.getBusinessOrder().status == 3) {//?????????
                            tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                            tvOrderStatus.setText("?????????");
                        } else if (bean.getBusinessOrder().status == 4) {//?????????
                            tvOrderStatus.setTextColor(Color.parseColor("#999999"));
                            tvOrderStatus.setText("?????????");
                        }
                        break;
                }
            } else {
                switch (bean.getBusinessOrder().status) {
                    case 1://?????????
                        tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                        tvOrderStatus.setText("?????????");
                        break;
                    case 2://?????????
                        tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                        tvOrderStatus.setText("?????????");
                        break;
                    case 3://?????????
                        tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                        tvOrderStatus.setText("?????????");
                        break;
                    case 4://?????????
                        tvOrderStatus.setTextColor(Color.parseColor("#999999"));
                        tvOrderStatus.setText("?????????");
                        break;
                    case 5://?????????
                        tvOrderStatus.setTextColor(Color.parseColor("#999999"));
                        tvOrderStatus.setText("????????????");
                        break;
                }
            }
        }
    }

    class OrderHolder extends Holder {
        RoundedImageView goodsPicIv;
        TextView goodsNameTv;
        TextView skuNameTv;
        TextView goodsPriceTv;
        TextView countTv;

        OrderHolder(@NonNull View itemView) {
            super(itemView);

            goodsPicIv = itemView.findViewById(R.id.goodsPicIv);
            goodsNameTv = itemView.findViewById(R.id.goodsNameTv);
            skuNameTv = itemView.findViewById(R.id.skuNameTv);
            goodsPriceTv = itemView.findViewById(R.id.goodsPriceTv);
            countTv = itemView.findViewById(R.id.countTv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.OrderDetailsActivity).withInt(ARouterValueNameConfig.TYPE, 1)
                            .withLong(ARouterValueNameConfig.orderId, list.get(getBindingAdapterPosition()).getBusinessOrder().id).navigation();
                }
            });
        }

        @Override
        protected void setData() {
            super.setData();
            ImageLoader.display(bean.getSubOrder().goodsPicture.split(",")[0], goodsPicIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            goodsNameTv.setText(bean.getSubOrder().goodsName);
            skuNameTv.setText(bean.getSubOrder().skuName);
            goodsPriceTv.setText("?? " + DecimalFormatUtils.toTwo(bean.getSubOrder().goodsPrice));
            countTv.setText("x" + bean.getSubOrder().goodsNum);
        }
    }

    class EndHolder extends Holder {
        TextView goodsPriceTv;
        TextView countTv;
        LinearLayout endRootLl;
        LinearLayout end1Rl;
        TextView tvOrderCancel;
        TextView tvOrderPay;
        TextView tvCustomerService;
        TextView tvOrderRemind;
        TextView tvOrderLogistics;
        TextView tvOrderConfirm;
        TextView tvBuyAgain;
        TextView tvRefundCancel;
        TextView tvLogisticsNum;

        public EndHolder(@NonNull View itemView) {
            super(itemView);
            goodsPriceTv = itemView.findViewById(R.id.goodsPriceTv);
            countTv = itemView.findViewById(R.id.countTv);
            endRootLl = itemView.findViewById(R.id.endRootLl);
            end1Rl = itemView.findViewById(R.id.end1Rl);
            tvOrderCancel = itemView.findViewById(R.id.tvOrderCancel);
            tvOrderPay = itemView.findViewById(R.id.tvOrderPay);
            tvCustomerService = itemView.findViewById(R.id.tvCustomerService);
            tvOrderRemind = itemView.findViewById(R.id.tvOrderRemind);
            tvOrderLogistics = itemView.findViewById(R.id.tvOrderLogistics);
            tvOrderConfirm = itemView.findViewById(R.id.tvOrderConfirm);
            tvBuyAgain = itemView.findViewById(R.id.tvBuyAgain);
            tvRefundCancel = itemView.findViewById(R.id.tvRefundCancel);
            tvLogisticsNum = itemView.findViewById(R.id.tvLogisticsNum);

            //????????????
            tvOrderCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    new TipDialog(context, "????????????", "????????????????????????", new TipDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            HttpApiShopOrder.cancelOrder(bean.getBusinessOrder().id, new HttpApiCallBack<HttpNone>() {
                                @Override
                                public void onHttpRet(int code, String msg, HttpNone retModel) {
                                    if (code == 1) {
                                        EventBus.getDefault().post(new ShopOrderRefreshEvent());
                                    }
                                    ToastUtil.show(msg);
                                }
                            });
                        }
                    });
                }
            });

            //????????????
            tvOrderPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ShopParentOrder order = new ShopParentOrder();
                    order.nhrAmount = bean.getBusinessOrder().transactionAmount;
                    order.id = bean.getBusinessOrder().payId;
                    goPayMethodSelectDialog(order);
                }
            });

            //????????????
            tvCustomerService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                            .withString(ARouterValueNameConfig.TO_UID, String.valueOf(bean.getAnchorId()))
                            .withString(ARouterValueNameConfig.Name, bean.getBusinessOrder().businessName)
                            .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                            .navigation();
                }
            });

            //????????????
            tvOrderRemind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ToastUtil.show("?????????????????????");
                    ImMessageUtil.getInstance().sendGoodsMessage(bean.getAnchorId());
                }
            });

            //????????????
            tvOrderLogistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.LogisdticsDetailsActivity).withString(ARouterValueNameConfig.orderNo, bean.getBusinessOrder().orderNum)
                            .withString(ARouterValueNameConfig.logisticsNumber, bean.logisticsNum).navigation();
                }
            });

            //????????????
            tvOrderConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    new TipDialog(context, "????????????", "???????????????????????????", new TipDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            HttpApiShopOrder.confirmReceipt(bean.getBusinessOrder().id, new HttpApiCallBack<HttpNone>() {
                                @Override
                                public void onHttpRet(int code, String msg, HttpNone retModel) {
                                    if (code == 1) {
                                        EventBus.getDefault().post(new ShopOrderRefreshEvent());
                                    }
                                    ToastUtil.show(msg);
                                }
                            });
                        }
                    });
                }
            });

            //????????????
            tvBuyAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.ShopActivity).withLong(ARouterValueNameConfig.businessId, bean.getBusinessOrder().businessId).navigation();
                }
            });

            //??????????????????
            tvRefundCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    new TipDialog(context, "??????????????????", "?????????????????????????", new TipDialog.OnClickListener() {
                        @Override
                        public void onClick() {
                            HttpApiShopQuiteOrder.cancelApplyRefund(bean.getBusinessOrder().id, new HttpApiCallBack<HttpNone>() {
                                @Override
                                public void onHttpRet(int code, String msg, HttpNone retModel) {
                                    if (code == 1) {
                                        EventBus.getDefault().post(new ShopOrderRefreshEvent());
                                    }
                                    ToastUtil.show(msg);
                                }
                            });
                        }
                    });
                }
            });

            //??????????????????
            tvLogisticsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    BuyerLogisticsNumDialog buyerLogisticsNumDialog = new BuyerLogisticsNumDialog();
                    Bundle bundle = new Bundle();
                    bundle.putLong("orderId", bean.getBusinessOrder().id);
                    buyerLogisticsNumDialog.setArguments(bundle);
                    buyerLogisticsNumDialog.show(((FragmentActivity) context).getSupportFragmentManager(), "BuyerLogisticsNumDialog");
                    buyerLogisticsNumDialog.setOnBuyerLogisticsNumListener(new BuyerLogisticsNumDialog.OnBuyerLogisticsNumListener() {
                        @Override
                        public void onSuccess() {
                            EventBus.getDefault().post(new ShopOrderRefreshEvent());
                        }
                    });
                }
            });

        }

        @Override
        protected void setData() {
            super.setData();
            countTv.setText("??????" + bean.getGoodsNum() + "?????????  ");
            goodsPriceTv.setText("?? " + DecimalFormatUtils.toTwo(bean.getTotal()));
            goodsPriceTv.setTextColor(0xff333333);

            end1Rl.setVisibility(View.GONE);
            tvOrderCancel.setVisibility(View.GONE);
            tvOrderPay.setVisibility(View.GONE);
            tvCustomerService.setVisibility(View.GONE);
            tvOrderRemind.setVisibility(View.GONE);
            tvOrderLogistics.setVisibility(View.GONE);
            tvOrderConfirm.setVisibility(View.GONE);
            tvBuyAgain.setVisibility(View.GONE);
            tvRefundCancel.setVisibility(View.GONE);
            tvLogisticsNum.setVisibility(View.GONE);
            if (bean.getBusinessOrder().quitStatus > 0) {
                switch (bean.getBusinessOrder().quitStatus) {
                    case 1://?????????
                    case 3://?????????
                        end1Rl.setVisibility(View.VISIBLE);
                        tvRefundCancel.setVisibility(View.VISIBLE);
                        break;
                    case 2://?????????
                        end1Rl.setVisibility(View.VISIBLE);
                        tvRefundCancel.setVisibility(View.VISIBLE);
                        tvLogisticsNum.setVisibility(View.VISIBLE);
                        break;
                    case 4://?????????
                    case 6://????????????
                        end1Rl.setVisibility(View.VISIBLE);
                        tvCustomerService.setVisibility(View.VISIBLE);
                        break;
                    case 5://????????????
                        break;
                    case 7://????????????
                        if (bean.getBusinessOrder().status == 2) {//?????????
                            end1Rl.setVisibility(View.VISIBLE);
                            tvCustomerService.setVisibility(View.VISIBLE);
                            tvOrderRemind.setVisibility(View.VISIBLE);
                        } else if (bean.getBusinessOrder().status == 3) {//?????????
                            end1Rl.setVisibility(View.VISIBLE);
                            tvOrderLogistics.setVisibility(View.VISIBLE);
                            tvOrderConfirm.setVisibility(View.VISIBLE);
                        } else if (bean.getBusinessOrder().status == 4) {//?????????
                            end1Rl.setVisibility(View.VISIBLE);
                            tvBuyAgain.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            } else {
                switch (bean.getBusinessOrder().status) {
                    case 1://?????????
                        end1Rl.setVisibility(View.VISIBLE);
                        tvOrderCancel.setVisibility(View.VISIBLE);
                        tvOrderPay.setVisibility(View.VISIBLE);
                        break;
                    case 2://?????????
                        end1Rl.setVisibility(View.VISIBLE);
                        tvCustomerService.setVisibility(View.VISIBLE);
                        tvOrderRemind.setVisibility(View.VISIBLE);
                        break;
                    case 3://?????????
                        end1Rl.setVisibility(View.VISIBLE);
                        tvOrderLogistics.setVisibility(View.VISIBLE);
                        tvOrderConfirm.setVisibility(View.VISIBLE);
                        break;
                    case 4://?????????
                        end1Rl.setVisibility(View.VISIBLE);
                        tvBuyAgain.setVisibility(View.VISIBLE);
                        break;
                    case 5://?????????
                        break;
                }
            }

        }
    }


    /**
     * ??????????????????????????????
     */
    private void goPayMethodSelectDialog(ShopParentOrder order) {
        if (activity1 == null) return;

        PayMethodSelectDialog fragment = new PayMethodSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("shop", 1);
        bundle.putParcelable("ShopParentOrder", order);
        bundle.putLong("orderId", order.id);
        fragment.setArguments(bundle);
        fragment.show(activity1.getSupportFragmentManager(), "PayMethodSelectDialog");
    }

}
