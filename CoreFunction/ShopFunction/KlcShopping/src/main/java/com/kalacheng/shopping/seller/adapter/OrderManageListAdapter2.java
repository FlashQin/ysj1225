package com.kalacheng.shopping.seller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.event.ShopOrderRefreshEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.buyer.bean.ShopUserOrderBean;
import com.kalacheng.shopping.seller.dialog.ReviewGoodsDialog;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class OrderManageListAdapter2 extends RecyclerView.Adapter<OrderManageListAdapter2.Holder> {

    List<ShopUserOrderBean> list;
    Context context;

    public OrderManageListAdapter2() {
        this.list = new ArrayList<>();
    }

    public List<ShopUserOrderBean> getList() {
        return list;
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
            return new EndHolder(inflater.inflate(R.layout.item_order_end_2, parent, false));
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
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) titleLl.getLayoutParams();
//            params.setMargins(0,0,0,0);
//            titleLl.setLayoutParams(params);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.getBuyUser().userid).navigation();
                }
            });
        }

        @Override
        protected void setData() {
            super.setData();
            ImageLoader.display(bean.getBuyUser().avatar, sellserLogoIv);
            sellserNameTv.setText(bean.getBuyUser().username);
            tvOrderStatus.setVisibility(View.VISIBLE);

            if (bean.getBusinessOrder().quitStatus > 0) {
                switch (bean.getBusinessOrder().quitStatus) {
                    case 1://待审核
                    case 2://待发货
                    case 3://待收货
                    case 4://退款中
                    case 6://退款失败
                        tvOrderStatus.setTextColor(Color.parseColor("#FC3A3A"));
                        tvOrderStatus.setText("退款中");
                        break;
                    case 5://退款完成
                        tvOrderStatus.setTextColor(Color.parseColor("#999999"));
                        tvOrderStatus.setText("已退款");
                        break;
                    case 7://审核拒绝
                        if (bean.getBusinessOrder().status == 2) {//待发货
                            tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                            tvOrderStatus.setText("待发货");
                        } else if (bean.getBusinessOrder().status == 3) {//待收货
                            tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                            tvOrderStatus.setText("待收货");
                        } else if (bean.getBusinessOrder().status == 4) {//已完成
                            tvOrderStatus.setTextColor(Color.parseColor("#999999"));
                            tvOrderStatus.setText("已完成");
                        }
                        break;
                }
            } else {
                switch (bean.getBusinessOrder().status) {
                    case 1://待付款
                        tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                        tvOrderStatus.setText("待付款");
                        break;
                    case 2://待发货
                        tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                        tvOrderStatus.setText("待发货");
                        break;
                    case 3://待收货
                        tvOrderStatus.setTextColor(Color.parseColor("#FC8F3A"));
                        tvOrderStatus.setText("待收货");
                        break;
                    case 4://已完成
                        tvOrderStatus.setTextColor(Color.parseColor("#999999"));
                        tvOrderStatus.setText("已完成");
                        break;
                    case 5://已取消
                        tvOrderStatus.setTextColor(Color.parseColor("#999999"));
                        tvOrderStatus.setText("订单关闭");
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
                    ARouter.getInstance().build(ARouterPath.OrderDetailsActivity).withInt(ARouterValueNameConfig.TYPE, 2)
                            .withLong(ARouterValueNameConfig.orderId, bean.getBusinessOrder().id).navigation();
                }
            });
        }

        @Override
        protected void setData() {
            super.setData();
            ImageLoader.display(bean.getSubOrder().goodsPicture.split(",")[0], goodsPicIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            goodsNameTv.setText(bean.getSubOrder().goodsName);
            skuNameTv.setText(bean.getSubOrder().skuName);
            goodsPriceTv.setText("¥ " + DecimalFormatUtils.toTwo(bean.getSubOrder().goodsPrice));
            countTv.setText("x" + bean.getSubOrder().goodsNum);
        }
    }

    class EndHolder extends Holder {
        TextView goodsPriceTv;
        TextView countTv;
        LinearLayout endRootLl;
        LinearLayout end1Rl;
        TextView tvOrderLogistics;
        TextView tvContactBuyer;
        TextView tvGoodsSend;
        TextView tvReviewRefund;
        TextView tvOrderLogisticsBuyer;
        TextView tvReviewGoods;

        public EndHolder(@NonNull View itemView) {
            super(itemView);
            goodsPriceTv = itemView.findViewById(R.id.goodsPriceTv);
            countTv = itemView.findViewById(R.id.countTv);
            endRootLl = itemView.findViewById(R.id.endRootLl);
            end1Rl = itemView.findViewById(R.id.end1Rl);
            tvOrderLogistics = itemView.findViewById(R.id.tvOrderLogistics);
            tvContactBuyer = itemView.findViewById(R.id.tvContactBuyer);
            tvGoodsSend = itemView.findViewById(R.id.tvGoodsSend);
            tvReviewRefund = itemView.findViewById(R.id.tvReviewRefund);
            tvOrderLogisticsBuyer = itemView.findViewById(R.id.tvOrderLogisticsBuyer);
            tvReviewGoods = itemView.findViewById(R.id.tvReviewGoods);
        }

        @Override
        protected void setData() {
            super.setData();
            countTv.setText("共计" + bean.getGoodsNum() + "件商品  ");
            goodsPriceTv.setText("¥ " + DecimalFormatUtils.toTwo(bean.getTotal()));
            goodsPriceTv.setTextColor(0xff333333);

            end1Rl.setVisibility(View.GONE);
            tvOrderLogistics.setVisibility(View.GONE);
            tvContactBuyer.setVisibility(View.GONE);
            tvGoodsSend.setVisibility(View.GONE);
            tvReviewRefund.setVisibility(View.GONE);
            tvOrderLogisticsBuyer.setVisibility(View.GONE);
            tvReviewGoods.setVisibility(View.GONE);
            if (bean.getBusinessOrder().quitStatus > 0) {
                switch (bean.getBusinessOrder().quitStatus) {
                    case 1://待审核
                        end1Rl.setVisibility(View.VISIBLE);
                        tvContactBuyer.setVisibility(View.VISIBLE);
                        tvReviewRefund.setVisibility(View.VISIBLE);
                        break;
                    case 2://待发货
                        break;
                    case 3://待收货
                        end1Rl.setVisibility(View.VISIBLE);
                        tvOrderLogisticsBuyer.setVisibility(View.VISIBLE);
                        tvReviewGoods.setVisibility(View.VISIBLE);
                        break;
                    case 4://退款中
                        break;
                    case 5://退款完成
                        break;
                    case 6://退款失败
                        break;
                    case 7://审核拒绝
                        if (bean.getBusinessOrder().status == 2) {//待发货
                            end1Rl.setVisibility(View.VISIBLE);
                            tvContactBuyer.setVisibility(View.VISIBLE);
                            tvGoodsSend.setVisibility(View.VISIBLE);
                        } else if (bean.getBusinessOrder().status == 3) {//待收货
                            end1Rl.setVisibility(View.VISIBLE);
                            tvOrderLogistics.setVisibility(View.VISIBLE);
                            tvContactBuyer.setVisibility(View.VISIBLE);
                        } else if (bean.getBusinessOrder().status == 4) {//已完成

                        }
                        break;
                }
            } else {
                switch (bean.getBusinessOrder().status) {
                    case 1://待付款
                        break;
                    case 2://待发货
                        end1Rl.setVisibility(View.VISIBLE);
                        tvContactBuyer.setVisibility(View.VISIBLE);
                        tvGoodsSend.setVisibility(View.VISIBLE);
                        break;
                    case 3://待收货
                        end1Rl.setVisibility(View.VISIBLE);
                        tvOrderLogistics.setVisibility(View.VISIBLE);
                        tvContactBuyer.setVisibility(View.VISIBLE);
                        break;
                    case 4://已完成
                        break;
                    case 5://已取消
                        break;
                }
            }

            //查看物流
            tvOrderLogistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.LogisdticsDetailsActivity).withString(ARouterValueNameConfig.orderNo, bean.getBusinessOrder().orderNum)
                            .withString(ARouterValueNameConfig.logisticsNumber, bean.logisticsNum).navigation();
                }
            });

            //联系买家
            tvContactBuyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                            .withString(ARouterValueNameConfig.TO_UID, String.valueOf(bean.getBusinessOrder().uid))
                            .withString(ARouterValueNameConfig.Name, bean.getBusinessOrder().name)
                            .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                            .navigation();
                }
            });

            //立即发货
            tvGoodsSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    if (bean.getBusinessOrder().status == 2) {
                        ARouter.getInstance().build(ARouterPath.ChooseLogisticsActivity).withLong(ARouterValueNameConfig.orderId, bean.getBusinessOrder().id).navigation();
                    }
                }
            });

            //审核退款申请
            tvReviewRefund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.ReviewRefundActivity).withLong(ARouterValueNameConfig.orderId, bean.getBusinessOrder().id).navigation();
                }
            });

            //查看买家物流
            tvOrderLogisticsBuyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.LogisdticsDetailsActivity).withString(ARouterValueNameConfig.orderNo, bean.getBusinessOrder().orderNum)
                            .withString(ARouterValueNameConfig.logisticsNumber, bean.refundLogisticsNum).navigation();
                }
            });

            //货物审核
            tvReviewGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    new ReviewGoodsDialog(context, bean.getBusinessOrder().id, new ReviewGoodsDialog.OnClickListener() {
                        @Override
                        public void onSuccess() {
                            EventBus.getDefault().post(new ShopOrderRefreshEvent());
                        }
                    });
                }
            });
        }
    }

}
