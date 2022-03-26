package com.kalacheng.live.component.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.httpApi.HttpApiAnchorWishList;
import com.kalacheng.libuser.model.ApiUsersLiveWish;
import com.kalacheng.live.component.adapter.LiveWishBillListAdapter;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.live.R;

import java.util.ArrayList;
import java.util.List;

public class LiveWishBillListDialogFragment extends BaseDialogFragment {
    LiveWishBillListAdapter liveWishBillListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_wish_bill_list;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = mRootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        liveWishBillListAdapter = new LiveWishBillListAdapter(mContext);
        recyclerView.setAdapter(liveWishBillListAdapter);
        //心愿单接口
        HttpApiAnchorWishList.getWishList(LiveConstants.ANCHORID, new HttpApiCallBackArr<ApiUsersLiveWish>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLiveWish> retModel) {
                List<ApiUsersLiveWish> liveUserWishes = new ArrayList<>();
                liveUserWishes = retModel;
                liveWishBillListAdapter.setWishList(liveUserWishes);
            }
        });

        mRootView.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


}
