package com.kalacheng.shopping.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.busshop.httpApi.HttpApiShopQuiteOrder;
import com.kalacheng.busshop.model.ShopUserOrderDetailDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.buyer.InputDialog;
import com.kalacheng.shopping.buyer.TipDialog;
import com.kalacheng.shopping.databinding.ActivityReviewRefundBinding;
import com.kalacheng.shopping.seller.viewmodel.ReviewRefundModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import cc.shinichi.library.ImagePreview;

/**
 * 审核退款申请
 */
@Route(path = ARouterPath.ReviewRefundActivity)
public class ReviewRefundActivity extends SBaseActivity<ActivityReviewRefundBinding, ReviewRefundModel> {
    @Autowired(name = ARouterValueNameConfig.orderId)
    public long orderId;
    private String[] pictures;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_review_refund;
    }

    @Override
    public void initData() {
        super.initData();

        getUserOrderDetail();
        initListeners();
    }

    private void initListeners() {
        binding.ivRefundPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (pictures != null && pictures.length > 0) {
                    ImagePreview.getInstance().setContext(mContext).setImage(pictures[0]).setShowDownButton(false).start();
                }
            }
        });
        binding.ivRefundPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (pictures != null && pictures.length > 1) {
                    ImagePreview.getInstance().setContext(mContext).setImage(pictures[1]).setShowDownButton(false).start();
                }
            }
        });
        binding.ivRefundPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (pictures != null && pictures.length > 2) {
                    ImagePreview.getInstance().setContext(mContext).setImage(pictures[2]).setShowDownButton(false).start();
                }
            }
        });
        binding.tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new TipDialog(mContext, "同意退款申请", "确认同意退款申请?", new TipDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        refundAudit("", 1);
                    }
                });
            }
        });
        binding.tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                new InputDialog(mContext, "拒绝退款申请", "请填写拒绝原因…", 50, new InputDialog.OnClickListener() {
                    @Override
                    public void onClick(String info) {
                        refundAudit(info, 2);
                    }
                });
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
                if (code == 1 && retModel != null) {
                    if (retModel.businessOrder.refundType == 1) {
                        binding.tvRefundType.setText("仅退款(未收到货)");
                    } else {
                        binding.tvRefundType.setText("退货退款(已收到货)");
                        if (!TextUtils.isEmpty(retModel.businessOrder.refundNotesImages)) {
                            binding.tvRefundPicTitle.setVisibility(View.VISIBLE);
                            binding.layoutRefundPic.setVisibility(View.VISIBLE);

                            pictures = retModel.businessOrder.refundNotesImages.split(",");
                            if (pictures.length > 0) {
                                ImageLoader.display(pictures[0], binding.ivRefundPic1);
                            }
                            if (pictures.length > 1) {
                                ImageLoader.display(pictures[1], binding.ivRefundPic2);
                            }
                            if (pictures.length > 2) {
                                ImageLoader.display(pictures[2], binding.ivRefundPic3);
                            }
                        }
                    }
                    binding.tvRefundReason.setText(retModel.businessOrder.reason);
                    binding.tvRefundAccount.setText("¥" + String.valueOf(retModel.businessOrder.transactionAmount));
                    binding.tvRefundRemark.setText(retModel.businessOrder.refundNotes);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 退款审核
     */
    private void refundAudit(String reason, int state) {
        HttpApiShopQuiteOrder.refundAudit(orderId, reason, state, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                ToastUtil.show(msg);
            }
        });
    }
}
