package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.commonview.dialog.SetOrderNumDialog;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.LiveGoodsWindowAdpater;
import com.kalacheng.util.utils.DpUtil;

import java.util.ArrayList;
import java.util.List;

/*
 * 直播商品橱窗列表
 * */
public class LiveGoodsWindowDialogFragment extends BaseDialogFragment {
    LiveGoodsWindowAdpater adpater;
    private List<ShopGoodsDTO> mList = new ArrayList<>();
    private TextView LiveGoodsWindow_Nodata;
    private RecyclerView LiveGoodsWindow_List;

    @Override
    protected int getLayoutId() {
        return R.layout.live_goods_window;
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

        LiveGoodsWindow_List = mRootView.findViewById(R.id.LiveGoodsWindow_List);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        LiveGoodsWindow_List.setLayoutManager(manager);

        LiveGoodsWindow_Nodata = mRootView.findViewById(R.id.LiveGoodsWindow_Nodata);

        adpater = new LiveGoodsWindowAdpater(mContext);
        LiveGoodsWindow_List.setAdapter(adpater);
        adpater.setLiveGoodsWindowCallBack(new LiveGoodsWindowAdpater.LiveGoodsWindowCallBack() {
            @Override
            public void onEditNumber(final int poistion) {
                SetOrderNumDialog setOrderNumDialog = new SetOrderNumDialog();
                setOrderNumDialog.setOnSetOrderNumListener(new SetOrderNumDialog.OnSetOrderNumListener() {
                    @Override
                    public void onConfirm(final int num) {
                        HttpApiShopGoods.updateLiveGoodsSort(mList.get(poistion).goodsId, num, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    mList.get(poistion).sort = num;
                                    adpater.notifyDataSetChanged();
                                }
                            }
                        });


                    }
                });
                setOrderNumDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "SetOrderNumDialog");
            }

            @Override
            public void onChecked(final int poistion) {
                HttpApiShopGoods.saveLiveGoods(mList.get(poistion).goodsId, mList.get(poistion).sort, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            if (mList.get(poistion).checked == 1) {
                                mList.get(poistion).checked = 0;
                            } else {
                                mList.get(poistion).checked = 1;
                            }
                            adpater.notifyDataSetChanged();
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }

            @Override
            public void onGoGoodsInfo(int position) {
                if (mList.get(position).channelId == 1) {
                    ARouter.getInstance().build(ARouterPath.GoodsDetailsActivity).withLong(ARouterValueNameConfig.goodsId, mList.get(position).goodsId).navigation();
                } else {
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, mList.get(position).productLinks).navigation();
                }
            }
        });
        getGoodsList();

        ImageView LiveGoodsWindow_close = mRootView.findViewById(R.id.LiveGoodsWindow_close);
        LiveGoodsWindow_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void getGoodsList() {
        HttpApiShopGoods.getLiveGoodsList(0, 10, 2, new HttpApiCallBackArr<ShopGoodsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<ShopGoodsDTO> retModel) {
                if (code == 1) {
                    if (retModel != null && retModel.size() > 0) {
                        LiveGoodsWindow_Nodata.setVisibility(View.GONE);
                        LiveGoodsWindow_List.setVisibility(View.VISIBLE);
                        mList.addAll(retModel);
                        adpater.getData(retModel);
                    } else {
                        LiveGoodsWindow_Nodata.setVisibility(View.VISIBLE);
                        LiveGoodsWindow_List.setVisibility(View.GONE);
                    }

                } else {
                    LiveGoodsWindow_Nodata.setVisibility(View.VISIBLE);
                    LiveGoodsWindow_List.setVisibility(View.GONE);
                }
            }
        });
    }
}
