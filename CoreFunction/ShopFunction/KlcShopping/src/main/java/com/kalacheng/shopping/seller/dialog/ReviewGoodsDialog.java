package com.kalacheng.shopping.seller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kalacheng.busshop.httpApi.HttpApiShopQuiteOrder;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.TextViewUtil;
import com.kalacheng.util.utils.ToastUtil;

/**
 * 货物审核dialog
 */
public class ReviewGoodsDialog extends Dialog {
    private long orderId;
    private OnClickListener onClickListener;

    public ReviewGoodsDialog(@NonNull Context context, long orderId, OnClickListener onClickListener) {
        super(context, R.style.dialog);
        this.orderId = orderId;
        this.onClickListener = onClickListener;
        setContentView(R.layout.dialog_review_goods);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        final TextView tvAgree = findViewById(R.id.tvAgree);
        final TextView tvRefuse = findViewById(R.id.tvRefuse);
        final TextView tvInfo = findViewById(R.id.tvInfo);
        final EditText etInfo = findViewById(R.id.etInfo);
        tvAgree.setSelected(true);

        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (!tvAgree.isSelected()) {
                    tvAgree.setSelected(true);
                    tvRefuse.setSelected(false);
                    tvAgree.setTextColor(Color.parseColor("#FC8F3A"));
                    TextViewUtil.setDrawableLeft(tvAgree, R.mipmap.icon_review_goods_reason_select);
                    tvRefuse.setTextColor(Color.parseColor("#666666"));
                    TextViewUtil.setDrawableLeft(tvRefuse, R.mipmap.icon_review_goods_reason_unselect);
                    tvInfo.setVisibility(View.VISIBLE);
                    etInfo.setVisibility(View.GONE);
                }
            }
        });
        tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (!tvRefuse.isSelected()) {
                    tvAgree.setSelected(false);
                    tvRefuse.setSelected(true);
                    tvAgree.setTextColor(Color.parseColor("#666666"));
                    TextViewUtil.setDrawableLeft(tvAgree, R.mipmap.icon_review_goods_reason_unselect);
                    tvRefuse.setTextColor(Color.parseColor("#FC8F3A"));
                    TextViewUtil.setDrawableLeft(tvRefuse, R.mipmap.icon_review_goods_reason_select);
                    tvInfo.setVisibility(View.GONE);
                    etInfo.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.cancelTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                dismiss();
            }
        });

        findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (tvAgree.isSelected()) {
                    sellerReceipt("", 1);
                } else {
                    if (TextUtils.isEmpty(etInfo.getText().toString())) {
                        ToastUtil.show("内容不能为空");
                        return;
                    }
                    sellerReceipt(etInfo.getText().toString().trim(), 2);
                }
            }
        });
        show();
    }

    /**
     * 退款审核
     */
    private void sellerReceipt(String reason, int state) {
        HttpApiShopQuiteOrder.sellerReceipt(orderId, reason, state, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (onClickListener != null) {
                        onClickListener.onSuccess();
                    }
                    dismiss();
                }
                ToastUtil.show(msg);
            }
        });
    }

    public interface OnClickListener {

        void onSuccess();

    }

}