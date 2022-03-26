package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopLiveGoods;
import com.kalacheng.busshop.model.ShopLiveGoodsDTO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.LiveShoppingAdpater;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;

import java.util.ArrayList;
import java.util.List;

/*
 *直播购讲解商品列表
 * */
public class LiveExplainGoodsListDialogFragment extends BaseDialogFragment {
    private LiveShoppingAdpater adpater;
    private List<ShopLiveGoods> mList = new ArrayList<>();
    private TextView LiveShopping_Nodata;
    private RecyclerView LiveShopping_List;

    @Override
    protected int getLayoutId() {
        return R.layout.live_explain_goodslist;
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
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.y = DpUtil.dp2px(60);
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

  /*      LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LiveSetExplainSuccess, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                for (int i = 0;i<mList.size();i++){
                    if ((long)o == mList.get(i).goodsId){
                        mList.get(i).idExplain =1;
                    }else {
                        mList.get(i).idExplain = 0;
                    }
                }
                adpater.getData(mList);
            }
        });*/


        LiveShopping_List = mRootView.findViewById(R.id.LiveShopping_List);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        LiveShopping_List.addItemDecoration(new ItemDecoration(mContext, 0, 10, 0));
        LiveShopping_List.setLayoutManager(manager);

        LiveShopping_Nodata = mRootView.findViewById(R.id.LiveShopping_Nodata);

        getGoodsList();

        adpater = new LiveShoppingAdpater(mContext);
        LiveShopping_List.setAdapter(adpater);

        adpater.setLiveShoppingCallBack(new LiveShoppingAdpater.LiveShoppingCallBack() {
            @Override
            public void onClick(final int position) {
                HttpApiShopGoods.setExplainStatus(mList.get(position).goodsId, LiveConstants.ROOMID, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            getGoodsList();
//                            adpater.setSelect(position);
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }

            @Override
            public void onDetails(int position) {
                // 如果是连接商品跳转去webView  如果是官方商品跳转去详情
                    if (mList.get(position).channelId==1){
                        ARouter.getInstance().build(ARouterPath.GoodsDetailsActivity).withLong(ARouterValueNameConfig.goodsId, mList.get(position).goodsId).navigation();
                    }else {
                        ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, mList.get(position).productLinks).navigation();
                    }
            }
        });

    }

    public void getGoodsList() {
        HttpApiShopGoods.getLiveGoods(LiveConstants.ANCHORID, new HttpApiCallBack<ShopLiveGoodsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopLiveGoodsDTO retModel) {
                if (code == 1) {
                    upData(retModel);
                } else {
                    LiveShopping_List.setVisibility(View.GONE);
                    LiveShopping_Nodata.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void upData(ShopLiveGoodsDTO retModel) {
        if (retModel.liveGoodsList != null && retModel.liveGoodsList.size() > 0) {
            LiveShopping_Nodata.setVisibility(View.GONE);
            LiveShopping_List.setVisibility(View.VISIBLE);
            mList.addAll(retModel.liveGoodsList);
            adpater.getData(retModel.liveGoodsList);
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).idExplain == 1) {
                    adpater.setSelect(i);
                }
            }
        } else {
            LiveShopping_List.setVisibility(View.GONE);
            LiveShopping_Nodata.setVisibility(View.VISIBLE);
        }

    }
}
