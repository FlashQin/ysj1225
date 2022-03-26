package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopLiveGoodsDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.LiveGoodsListAdpater;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpApiCallBack;

/*
 * 直播购商品列表
 * */
public class LiveGoodsListDialogFragment extends BaseDialogFragment {
    private LiveGoodsListAdpater adpater;
    private ImageView ShopStore_Image;
    private TextView ShopStore_Name;
    private TextView LiveGoodsList_Nodata;
    private RecyclerView LiveGoodsList_Recy;
    private long businessId;//商家id

    @Override
    protected int getLayoutId() {
        return R.layout.live_goods_list;
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
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.getScreenHeight() / 5 * 3;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ShopStore_Image = mRootView.findViewById(R.id.ShopStore_Image);
        ShopStore_Name = mRootView.findViewById(R.id.ShopStore_Name);
        LiveGoodsList_Nodata = mRootView.findViewById(R.id.LiveGoodsList_Nodata);
        mRootView.findViewById(R.id.ShopStore_Name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (businessId != 0) {
                    ARouter.getInstance().build(ARouterPath.ShopActivity).withLong(ARouterValueNameConfig.businessId, businessId).navigation();
                }
            }
        });
        mRootView.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        LiveGoodsList_Recy = mRootView.findViewById(R.id.LiveGoodsList_Recy);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        LiveGoodsList_Recy.setLayoutManager(manager);

        adpater = new LiveGoodsListAdpater(mContext);
        LiveGoodsList_Recy.setAdapter(adpater);
        getGoodsList();
    }

    public void getGoodsList() {
        HttpApiShopGoods.getLiveGoods(LiveConstants.ANCHORID, new HttpApiCallBack<ShopLiveGoodsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopLiveGoodsDTO retModel) {
                if (code == 1) {
                    if (retModel.liveGoodsList != null && retModel.liveGoodsList.size() > 0) {
                        LiveGoodsList_Nodata.setVisibility(View.GONE);
                        LiveGoodsList_Recy.setVisibility(View.VISIBLE);
                        adpater.getGoodsList(retModel.liveGoodsList);
                        ImageLoader.display(retModel.businessLogo, ShopStore_Image, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        ShopStore_Name.setText(retModel.businessName);
                        businessId = retModel.businessId;
                    } else {
                        LiveGoodsList_Nodata.setVisibility(View.VISIBLE);
                        LiveGoodsList_Recy.setVisibility(View.GONE);
                    }

                } else {
                    LiveGoodsList_Nodata.setVisibility(View.VISIBLE);
                    LiveGoodsList_Recy.setVisibility(View.GONE);
                }
            }
        });
    }
}
