package com.kalacheng.dynamiccircle.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.adpater.TrendShareAdpater;
import com.kalacheng.dynamiccircle.listener.OnTrendItemShareListener;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.SpUtil;

import java.util.Arrays;
import java.util.List;

public class TrendShareDialogFragment extends BaseDialogFragment {
    private OnTrendItemShareListener mOnTrendItemShareListener;
    private int locationY;
    private boolean showDown;

    private RecyclerView trend_share_recy;

    public void setOnTrendItemShareListener(OnTrendItemShareListener onTrendItemShareListener) {
        this.mOnTrendItemShareListener = onTrendItemShareListener;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_trend_share_fragment;
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
        if (locationY != 0) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.END | Gravity.TOP;
            params.x = DpUtil.dp2px(9);
            if (locationY > DpUtil.dp2px(180)) {
                params.y = locationY - DpUtil.dp2px(120);
            } else {
                showDown = true;
                params.y = locationY - DpUtil.dp2px(10);
            }
            window.setAttributes(params);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (showDown) {
            LinearLayout layoutTrendShare = mRootView.findViewById(R.id.layoutTrendShare);
            layoutTrendShare.setBackgroundResource(R.mipmap.icon_share_bg_down);
            layoutTrendShare.setPadding(0, DpUtil.dp2px(8), 0, 0);
        }
        trend_share_recy = mRootView.findViewById(R.id.trend_share_recy);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        trend_share_recy.setLayoutManager(manager);
        String shareType = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_SHARE_TYPE, "");
        if (!TextUtils.isEmpty(shareType)) {
            String[] share = shareType.split(",");
            final List<String> shareTypeList = Arrays.asList(share);
            if (shareTypeList != null) {
                TrendShareAdpater adpater = new TrendShareAdpater(mContext);
                trend_share_recy.setAdapter(adpater);
                adpater.getData(shareTypeList);
                adpater.setTrendShareItmeOnClick(new TrendShareAdpater.TrendShareItmeOnClick() {
                    @Override
                    public void onClick(int poistion) {
                        if (shareTypeList.get(poistion).equals("1")) {
                            if (mOnTrendItemShareListener != null) {
                                mOnTrendItemShareListener.onQQ();
                            }
                        } else if (shareTypeList.get(poistion).equals("2")) {
                            if (mOnTrendItemShareListener != null) {
                                mOnTrendItemShareListener.onQQCircle();
                            }
                        } else if (shareTypeList.get(poistion).equals("3")) {
                            if (mOnTrendItemShareListener != null) {
                                mOnTrendItemShareListener.onWeixin();
                            }
                        } else if (shareTypeList.get(poistion).equals("4")) {
                            if (mOnTrendItemShareListener != null) {
                                mOnTrendItemShareListener.onWeixinCircle();
                            }
                        }
                        dismiss();
                    }
                });
            }
        }
    }
}
