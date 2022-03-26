package com.kalacheng.commonview.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.busshop.model.ShopParentOrder;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.PayMethodAdapter;
import com.kalacheng.libuser.model.ApiAppChargeRulesResp;
import com.kalacheng.libuser.model.AppSvipPackage;
import com.kalacheng.libuser.model.AppUsersCharge;
import com.kalacheng.libuser.model.PayConfigDto;
import com.kalacheng.commonview.pay.AliPayBuilder;
import com.kalacheng.commonview.pay.PayCallback;
import com.kalacheng.commonview.pay.WxPayBuilder;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;


import java.util.List;

public class PayMethodSelectDialog extends DialogFragment {
    protected Context mContext;
    protected View mRootView;

    private ImageView ivClose;
    private TextView tvMoney;
    private RecyclerView recyclerView;
    private TextView tvPayMethodNone;
    private int type;// type为1时 是直播购物的状态，
    private long mOrderId;

    private OnBeanCallback<PayConfigDto> onBeanCallback;

    private PayMethodListener listener;
    private DialogInterface.OnDismissListener onDismissListener;
    private PayMethodSelectDialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = this;
        mContext = getActivity();
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_pay_method_select, null);
        Dialog dialog = new Dialog(mContext, R.style.BottomDialogStyle);
        dialog.setContentView(mRootView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ivClose = mRootView.findViewById(R.id.ivClose);
        tvMoney = mRootView.findViewById(R.id.tvMoney);
        TextView tvOriginal = mRootView.findViewById(R.id.tv_original);
        TextView tvNoble = mRootView.findViewById(R.id.tv_noble);
        LinearLayout llNoble = mRootView.findViewById(R.id.ll_noble);
        tvOriginal.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        tvPayMethodNone = mRootView.findViewById(R.id.tvPayMethodNone);

        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("shop");
            mOrderId = bundle.getLong("orderId", 0);
            switch (type) {
                case 0:
                    AppUsersCharge appUsersCharge = bundle.getParcelable("AppUsersCharge");
                    ApiAppChargeRulesResp apiAppChargeRulesResp = bundle.getParcelable("ApiAppChargeRulesResp");
                    if (apiAppChargeRulesResp.isvip == 1) {
                        tvMoney.setText("¥ " + appUsersCharge.discountMoney);
                        tvOriginal.setVisibility(View.GONE);
                        llNoble.setVisibility(View.GONE);
                    } else {
                        llNoble.setVisibility(View.VISIBLE);
                        tvMoney.setText("¥ " + appUsersCharge.discountMoneyVip);
                        tvOriginal.setText("¥ " + appUsersCharge.discountMoney);
                        tvNoble.setText("尊享 " + apiAppChargeRulesResp.nobleGradeName + " 充值折扣" + String.format("%.1f", apiAppChargeRulesResp.rechargeDiscount * 10) + "折");
                    }
                    break;
                case 1:
                    //直播购物
                    ShopParentOrder order = bundle.getParcelable("ShopParentOrder");
                    tvMoney.setText("¥ " + order.nhrAmount);
                    tvOriginal.setVisibility(View.GONE);
                    llNoble.setVisibility(View.GONE);
                    break;
                case 2:
                    //Svip
                    AppSvipPackage appSvipPackage = bundle.getParcelable("appSvipPackage");
                    if (null != appSvipPackage) {
                        tvMoney.setText("¥ " + DecimalFormatUtils.toTwo(appSvipPackage.coin));
                        tvOriginal.setVisibility(View.GONE);
                        llNoble.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
        }

        List<PayConfigDto> payMethodList = SpUtil.getInstance().<PayConfigDto>getModelList(SpUtil.CONFIG_PAY_LIST, PayConfigDto.class);
        if (payMethodList != null && payMethodList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tvPayMethodNone.setVisibility(View.GONE);
            PayMethodAdapter payMethodAdapter = new PayMethodAdapter(payMethodList);
            payMethodAdapter.setOnBeanCallback(new OnBeanCallback<PayConfigDto>() {
                @Override
                public void onClick(PayConfigDto bean) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (bean.id == 1) {//支付宝
                        if (!isAliPayInstalled(mContext)){
                            ToastUtil.show("未安装支付宝，请先安装支付宝！");
                            return;
                        }
                        AliPayBuilder aliPayBuilder = new AliPayBuilder((Activity) mContext);
                        aliPayBuilder.setPayCallback(new PayCallback() {
                            @Override
                            public void onSuccess() {
                                showPayDialog(1);
                            }

                            @Override
                            public void onFailed() {
                                showPayDialog(2);
                            }
                        });
                        switch (type) {
                            case 0:
                                aliPayBuilder.pay(bean.id, mOrderId);
                                break;
                            case 1:
                                aliPayBuilder.shopPay(bean.id, mOrderId);
                                break;
                            case 2: // Svip
                                aliPayBuilder.startPay(bean.id, mOrderId, 5);
                                break;
                            default:
                                break;
                        }
                    } else if (bean.id == 2) {//微信
                        if (!isWeixinAvilible(mContext)){
                            ToastUtil.show("未安装微信，请先安装微信！");
                            return;
                        }
                        WxPayBuilder builder = new WxPayBuilder((Activity) mContext);
                        builder.setPayCallback(new PayCallback() {
                            @Override
                            public void onSuccess() {
                                showPayDialog(1);
                            }

                            @Override
                            public void onFailed() {
                                showPayDialog(2);
                            }
                        });
                        switch (type) {
                            case 0:
                                builder.pay(bean.id, mOrderId);
                                break;
                            case 1:
                                builder.shopPay(bean.id, mOrderId);
                                break;
                            case 2:
                                builder.startPay(bean.id, mOrderId, 5);
                                break;
                            default:
                                break;
                        }
                    }
                    dismiss();
                }
            });
            recyclerView.setAdapter(payMethodAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            tvPayMethodNone.setVisibility(View.VISIBLE);
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void showPayDialog(int type){
        PaySuccessOrFailDialogFragment successOrFailDialogFragment = new PaySuccessOrFailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        successOrFailDialogFragment.setArguments(bundle);
        successOrFailDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "PaySuccessOrFailDialogFragment");
        successOrFailDialogFragment.setListener(new PaySuccessOrFailDialogFragment.PaySuccessListener() {
            @Override
            public void close() {
                dismiss();
            }

            @Override
            public void success() {
                if (null != listener){
                    listener.onSuccess();
                }
            }

            @Override
            public void backPay() {
                // 重新支付
                if (null != dialog){
                    dialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), "PayMethodSelectDialog");
                }
            }
        });
    }

    /**
     * 检测是否安装支付宝
     * @param context
     * @return
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }
    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setOnBeanCallback(OnBeanCallback<PayConfigDto> onBeanCallback) {
        this.onBeanCallback = onBeanCallback;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void setListener(PayMethodListener listener){
        this.listener = listener;
    }

    public interface PayMethodListener{
        void onSuccess();
        void onFailed();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
}
