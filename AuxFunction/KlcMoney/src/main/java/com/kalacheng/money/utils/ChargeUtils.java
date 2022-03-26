package com.kalacheng.money.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.libuser.model.ApiAppChargeRulesResp;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.money.dialog.FirstRechargeDialogFragment;
import com.kalacheng.money.dialog.LiveRechargeDialogFragment;

public class ChargeUtils {
    private Context mContext;
    private FirstRechargeDialogFragment firstRechargeDialogFragment;
    private LiveRechargeDialogFragment liveRechargeDialogFragment;

    private static class SingletonHolder {
        private static final ChargeUtils INSTANCE = new ChargeUtils();
    }

    private ChargeUtils() {
    }

    public static final ChargeUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ChargeUtils init(Context context) {
        mContext = context;
        return this;
    }

    public void showChargeDialog() {
        HttpApiAPPFinance.rules_list(new HttpApiCallBack<ApiAppChargeRulesResp>() {
            @Override
            public void onHttpRet(int code, String msg, ApiAppChargeRulesResp retModel) {
                if (code == 1) {
                    if (retModel.isFirstRecharge == 1) {
                        firstRechargeDialogFragment = new FirstRechargeDialogFragment();
                        firstRechargeDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "FirstRechargeDialogFragment");
                    } else {
                        liveRechargeDialogFragment = new LiveRechargeDialogFragment();
                        liveRechargeDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveRechargeDialogFragment");
                    }

                }
            }
        });
    }

    public void dismiss() {
        if (firstRechargeDialogFragment != null)
            firstRechargeDialogFragment.dismiss();
        if (liveRechargeDialogFragment != null)
            liveRechargeDialogFragment.dismiss();
    }
}
