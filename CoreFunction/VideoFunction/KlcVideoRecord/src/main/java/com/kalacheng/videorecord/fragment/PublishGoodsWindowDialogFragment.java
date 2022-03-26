package com.kalacheng.videorecord.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.videorecord.R;
import com.kalacheng.videorecord.adpater.PublishGoodsWindowAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 * 直播商品橱窗列表 （直播橱窗管理）
 */
public class PublishGoodsWindowDialogFragment extends BaseDialogFragment {

    private chooseCallBack chooseCallBack;
    private PublishGoodsWindowAdapter adpater;
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
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.getScreenHeight() / 5 * 3;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

        adpater = new PublishGoodsWindowAdapter(mContext);
        LiveGoodsWindow_List.setAdapter(adpater);
        adpater.setLiveGoodsWindowCallBack(new PublishGoodsWindowAdapter.LiveGoodsWindowCallBack() {
            @Override
            public void onEditNumber(final int poistion) {

            }

            @Override
            public void onChecked(final int poistion) {
            }

            @Override
            public void onGoGoodsInfo(int position) {
                if (null != chooseCallBack) {
                    chooseCallBack.onChecked(mList.get(position));
                    dismiss();
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

    public void setListener(chooseCallBack chooseCallBack) {
        this.chooseCallBack = chooseCallBack;
    }

    // 选择商品的回调
    public interface chooseCallBack {
        void onChecked(ShopGoodsDTO shopGoodsDTO);
    }

}
