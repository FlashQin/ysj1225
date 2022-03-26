package com.kalacheng.shopping.buyer.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.busshop.httpApi.HttpApiShopQuiteOrder;
import com.kalacheng.busshop.model.ShopLogisticsDTO;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.buyer.adapter.LogisticsListAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.TextViewUtil;
import com.kalacheng.util.utils.ToastUtil;

/**
 * 买家填写退回物流单号
 */
public class BuyerLogisticsNumDialog extends BaseDialogFragment {
    private long orderId;

    private TextView tvCompanySelect;
    private EditText etCompanyNum;
    private LinearLayout layoutCompany;
    private RecyclerView recyclerView;
    private LogisticsListAdapter adapter;
    private OnBuyerLogisticsNumListener onBuyerLogisticsNumListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_buyer_logistics_num;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderId = getArguments().getLong("orderId", 0);

        initViews();
        initListeners();

        getLogisticsList();
    }

    private void initViews() {
        tvCompanySelect = mRootView.findViewById(R.id.tvCompanySelect);
        etCompanyNum = mRootView.findViewById(R.id.etCompanyNum);
        layoutCompany = mRootView.findViewById(R.id.layoutCompany);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new LogisticsListAdapter(mContext);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new LogisticsListAdapter.OnLogisticsNameSelectListener() {
            @Override
            public void onSelect(String name) {
                tvCompanySelect.setText(name);
                tvCompanySelect.setSelected(false);
                TextViewUtil.setDrawableRight(tvCompanySelect, R.mipmap.icon_shop_arrow_down);
                layoutCompany.setVisibility(View.GONE);
            }
        });
    }

    private void initListeners() {
        tvCompanySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (!tvCompanySelect.isSelected()) {
                    tvCompanySelect.setSelected(true);
                    TextViewUtil.setDrawableRight(tvCompanySelect, R.mipmap.icon_shop_arrow_up);
                    layoutCompany.setVisibility(View.VISIBLE);
                }
            }
        });
        layoutCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                tvCompanySelect.setSelected(false);
                TextViewUtil.setDrawableRight(tvCompanySelect, R.mipmap.icon_shop_arrow_down);
                layoutCompany.setVisibility(View.GONE);
            }
        });
        mRootView.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                dismiss();
            }
        });
        mRootView.findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (TextUtils.isEmpty(tvCompanySelect.getText().toString())) {
                    ToastUtil.show("请选择快递公司");
                    return;
                }
                if (TextUtils.isEmpty(etCompanyNum.getText().toString().trim())) {
                    ToastUtil.show("请填写物流单号");
                    return;
                }
                buyerDeliver(tvCompanySelect.getText().toString().trim(), etCompanyNum.getText().toString().trim());
            }
        });
    }

    /**
     * 获取可用物流列表
     */
    private void getLogisticsList() {
        HttpApiShopOrder.getLogisticsList(new HttpApiCallBack<ShopLogisticsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopLogisticsDTO retModel) {
                if (code == 1) {
                    adapter.setData(retModel.logisticsList);
                }
            }
        });
    }

    /**
     * 买家发货
     */
    private void buyerDeliver(String logisticsName, String logisticsNum) {
        HttpApiShopQuiteOrder.buyerDeliver(orderId, logisticsName, logisticsNum, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (onBuyerLogisticsNumListener != null) {
                        onBuyerLogisticsNumListener.onSuccess();
                    }
                    dismiss();
                }
                ToastUtil.show(msg);
            }
        });
    }

    public void setOnBuyerLogisticsNumListener(OnBuyerLogisticsNumListener onBuyerLogisticsNumListener) {
        this.onBuyerLogisticsNumListener = onBuyerLogisticsNumListener;
    }

    public interface OnBuyerLogisticsNumListener {
        void onSuccess();
    }
}
