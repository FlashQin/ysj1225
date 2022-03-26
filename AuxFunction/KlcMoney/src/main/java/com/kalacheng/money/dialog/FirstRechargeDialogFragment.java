package com.kalacheng.money.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.libuser.model.AdminGiftPack;
import com.kalacheng.money.adapter.FirstRechargeAdpater;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.money.R;
import com.kalacheng.util.view.ItemDecoration;

import java.util.List;

// 首充奖励
public class FirstRechargeDialogFragment extends BaseDialogFragment {
    public RecyclerView first_recharhe_list;
    private FirstRechargeAdpater adpater;
    private OnFirstRechargeCancelListener onFirstRechargeCancelListener;

    @Override
    protected int getLayoutId() {
        return R.layout.first_recharge;
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
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        first_recharhe_list = mRootView.findViewById(R.id.first_recharhe_list);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        first_recharhe_list.addItemDecoration(new ItemDecoration(mContext, 0, 10, 0));
        first_recharhe_list.setLayoutManager(manager);
        adpater = new FirstRechargeAdpater(mContext);
        first_recharhe_list.setAdapter(adpater);
        getFirstRecharge();

        TextView first_recharhe_rech = mRootView.findViewById(R.id.first_recharhe_rech);
        first_recharhe_rech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择支付方式
                dismiss();
                if (onFirstRechargeCancelListener != null) {
                    onFirstRechargeCancelListener.onCancel();
                } else {
                    LiveRechargeDialogFragment liveRechargeDialogFragment = new LiveRechargeDialogFragment();
                    liveRechargeDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveRechargeDialogFragment");
                }
            }
        });
    }

    public void getFirstRecharge() {
        HttpApiAPPFinance.first_recharge(new HttpApiCallBackArr<AdminGiftPack>() {
            @Override
            public void onHttpRet(int code, String msg, List<AdminGiftPack> retModel) {
                if (code == 1) {
                    adpater.setData(retModel);
                }
            }
        });
    }

    public void setOnFirstRechargeCancelListener(OnFirstRechargeCancelListener onFirstRechargeCancelListener) {
        this.onFirstRechargeCancelListener = onFirstRechargeCancelListener;
    }

    /**
     * 直接取消关闭窗口
     */
    public interface OnFirstRechargeCancelListener {
        void onCancel();
    }
}
