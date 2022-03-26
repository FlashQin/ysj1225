package com.kalacheng.money.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.commonview.dialog.PayMethodSelectDialog;
import com.kalacheng.libuser.model.ApiAppChargeRulesResp;
import com.kalacheng.libuser.model.AppUsersCharge;
import com.kalacheng.money.adapter.LiveRechargeAdpater;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.money.R;

import java.util.ArrayList;
import java.util.List;

// 充值  充值规则
public class LiveRechargeDialogFragment extends BaseDialogFragment {

    private RecyclerView Recharge_list;
    private ImageView close;
    private LiveRechargeAdpater adpater;
    private ApiAppChargeRulesResp apiAppChargeRulesResp;


    @Override
    protected int getLayoutId() {
        return R.layout.live_recharge_dialog;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Recharge_list = mRootView.findViewById(R.id.Recharge_list);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        Recharge_list.setLayoutManager(manager);
        adpater = new LiveRechargeAdpater(mContext);
        Recharge_list.setAdapter(adpater);

        //选择充值金额
        adpater.setLiveRechargeItemCallBack(new LiveRechargeAdpater.LiveRechargeItemCallBack() {
            @Override
            public void onClick(final int poistion) {
                //选择支付方式
                PayMethodSelectDialog fragment = new PayMethodSelectDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable("AppUsersCharge", list.get(poistion));
                bundle.putParcelable("ApiAppChargeRulesResp", apiAppChargeRulesResp);
                bundle.putLong("orderId", list.get(poistion).id);
                fragment.setArguments(bundle);
                fragment.show(((FragmentActivity) getActivity()).getSupportFragmentManager(), "PayMethodSelectDialog");

//                PayUtilsDialog.getInstance().PayUtilsDialogShow(mContext,list.get(poistion).id);

            }
        });


        close = mRootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        //获取充值列表
        getRecharge();
    }

    private List<AppUsersCharge> list;
    private int isFirstRecharge;

    public void getRecharge() {
        if (list != null) {
            list.clear();
        }
        HttpApiAPPFinance.rules_list(new HttpApiCallBack<ApiAppChargeRulesResp>() {
            @Override
            public void onHttpRet(int code, String msg, ApiAppChargeRulesResp retModel) {
                if (code == 1) {
                    apiAppChargeRulesResp = retModel;
                    isFirstRecharge = retModel.isFirstRecharge;
                    list = new ArrayList<>();
                    list = retModel.appChargeRules;
                    adpater.getData(list);
                }

            }
        });
    }

}
