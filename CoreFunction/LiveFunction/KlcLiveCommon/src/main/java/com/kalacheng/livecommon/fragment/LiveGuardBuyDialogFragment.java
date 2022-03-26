package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiGuard;
import com.kalacheng.libuser.model.ApiGuardEntity;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;

import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.GuardRightAdapter;
import com.kalacheng.util.utils.DialogUtil;

import java.util.List;

/**
 * Created by cxf on 2018/11/6.
 * 直播间购买守护弹窗
 */

public class LiveGuardBuyDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RadioButton[] mRadioBtns;
    private TextView[] mPrices;
    private TextView mCoinNameTextView;
    private TextView mCoin;
    private String mCoinName;
    private View mBtnBuy;
    private GuardRightAdapter mGuardRightAdapter;
    private long mCoinVal;//余额
    private long tid; //守护规则id
    private long anchorid;
    private double icon;
    private ApiGuardEntity mLiveGuardInfo;
    private boolean mBtnEnable;
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_guard_buy;
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

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRadioBtns = new RadioButton[3];
        mRadioBtns[0] = (RadioButton) mRootView.findViewById(R.id.btn_1);
        mRadioBtns[1] = (RadioButton) mRootView.findViewById(R.id.btn_2);
        mRadioBtns[2] = (RadioButton) mRootView.findViewById(R.id.btn_3);
        mPrices = new TextView[3];
        mPrices[0] = (TextView) mRootView.findViewById(R.id.price_1);
        mPrices[1] = (TextView) mRootView.findViewById(R.id.price_2);
        mPrices[2] = (TextView) mRootView.findViewById(R.id.price_3);
        mCoinNameTextView = (TextView) mRootView.findViewById(R.id.coin_name);
        mCoin = (TextView) mRootView.findViewById(R.id.coin);
        mBtnBuy = mRootView.findViewById(R.id.btn_buy);
        mRadioBtns[0].setOnClickListener(this);
        mRadioBtns[1].setOnClickListener(this);
        mRadioBtns[2].setOnClickListener(this);
        mBtnBuy.setOnClickListener(this);
        mCoin.setOnClickListener(this);
        ((RadioGroup) mRootView.findViewById(R.id.rgType)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.btn_1) {
                    mPrices[0].setTextColor(Color.WHITE);
                    mPrices[1].setTextColor(getResources().getColor(R.color.color_shouhu_money));
                    mPrices[2].setTextColor(getResources().getColor(R.color.color_shouhu_money));
                } else if (checkedId == R.id.btn_2) {
                    mPrices[0].setTextColor(getResources().getColor(R.color.color_shouhu_money));
                    mPrices[1].setTextColor(Color.WHITE);
                    mPrices[2].setTextColor(getResources().getColor(R.color.color_shouhu_money));
                } else if (checkedId == R.id.btn_3) {
                    mPrices[0].setTextColor(getResources().getColor(R.color.color_shouhu_money));
                    mPrices[1].setTextColor(getResources().getColor(R.color.color_shouhu_money));
                    mPrices[2].setTextColor(Color.WHITE);
                }
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            anchorid = bundle.getLong("anchorid");
            mCoinNameTextView.setText("我的娱币" + ":");
        }
        HttpApiAPPAnchor.getGuardList(anchorid,new HttpApiCallBack<ApiGuardEntity>() {
            @Override
            public void onHttpRet(int code, String msg, ApiGuardEntity retModel) {
                if (code == 1 && retModel != null) {
                    List<ApiGuard> apiGuardList = retModel.apiGuardList;
                    mCoinVal = retModel.totalCoin;
                    mCoin.setText(mCoinVal + "");
                    for (int i = 0, length = mPrices.length; i < length; i++) {
                        if (i < apiGuardList.size()) {
                            mRadioBtns[i].setText(apiGuardList.get(i).name);
                            mPrices[i].setText(String.valueOf(apiGuardList.get(i).coin));
                            bean1 = retModel.apiGuardList.get(0);
                            bean2 = retModel.apiGuardList.get(1);
                            bean3 = retModel.apiGuardList.get(2);
                        }
                    }
                    tid = retModel.apiGuardList.get(0).tid;
                    icon = retModel.apiGuardList.get(0).coin;
                    mCoinName = retModel.apiGuardList.get(0).name;
                    if (mCoinVal >= retModel.apiGuardList.get(0).coin) {
                        mBtnEnable = true;
                    } else {
                        mBtnEnable = false;
                    }
                    mGuardRightAdapter = new GuardRightAdapter(mContext, bean1);
                    mRecyclerView.setAdapter(mGuardRightAdapter);
                    mGuardRightAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    ApiGuard bean1, bean2, bean3;

    private void refreshList(ApiGuard beans) {
        if (mCoinVal >= beans.coin) {
            mBtnEnable = true;
        } else {
            mBtnEnable = false;
        }
        icon = beans.coin;
        mCoinName = beans.name;
        tid = beans.tid;
        mGuardRightAdapter = new GuardRightAdapter(mContext, beans);
        mRecyclerView.setAdapter(mGuardRightAdapter);
        mGuardRightAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_1) {
            refreshList(bean1);
        } else if (i == R.id.btn_2) {
            refreshList(bean2);
        } else if (i == R.id.btn_3) {
            refreshList(bean3);
        } else if (i == R.id.btn_buy) {
            if (mBtnEnable) {
                clickBuyGuard();
            } else {
                ToastUtil.show("余额不足，请充值");
            }
        } else if (i == R.id.coin) {
            forwardMyCoin();
        }
    }

    /**
     * 跳转到我的钻石
     */
    private void forwardMyCoin() {
        dismiss();
        ARouter.getInstance().build("/center/activity/MyAccountActivity").navigation();
    }

    /**
     * 点击购买守护按钮
     */
    private void clickBuyGuard() {
        String text = "您将花费" + icon + "，为主播开通" + mCoinName + "守护";
        DialogUtil.showAccountBuyDialog(mContext, text, new DialogUtil.CashAccountDeleteCallBack() {
            @Override
            public void onConfirmClick() {
                HttpApiAPPAnchor.guardListBuy(anchorid, tid, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show(msg);
                        }
                        dismiss();
                    }
                });
            }
        });


    }

    @Override
    public void onDestroy() {
        mLiveGuardInfo = null;

        super.onDestroy();
    }
}
