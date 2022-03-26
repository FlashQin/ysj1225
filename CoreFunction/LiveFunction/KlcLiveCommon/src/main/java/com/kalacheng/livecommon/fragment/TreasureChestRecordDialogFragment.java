package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busgame.httpApi.HttpApiGame;
import com.kalacheng.busgame.model.GameLuckDraw;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.TreasureChestRecordAdpater;
import com.kalacheng.util.utils.DpUtil;

import java.util.List;

/*
* 抽奖纪录
* */
public class TreasureChestRecordDialogFragment extends BaseDialogFragment {
    private TreasureChestRecordAdpater treasureChestRecordAdpater;
    @Override
    protected int getLayoutId() {
        return R.layout.treasurechest_record;
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
        params.height = (DpUtil.getScreenHeight()/3*2);;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView treasureChestRecord_List = mRootView.findViewById(R.id.treasureChestRecord_List);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        treasureChestRecord_List.setLayoutManager(manager);
        treasureChestRecordAdpater = new TreasureChestRecordAdpater(getActivity());
        treasureChestRecord_List.setAdapter(treasureChestRecordAdpater);

        ImageView treasureChestRecord_close = mRootView.findViewById(R.id.treasureChestRecord_close);
        treasureChestRecord_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getData();
    }
    public void getData(){
        HttpApiGame.getUserLuckDrawList(1, 0, 20, new HttpApiCallBackArr<GameLuckDraw>() {
            @Override
            public void onHttpRet(int code, String msg, List<GameLuckDraw> retModel) {
                if (code == 1){
                    treasureChestRecordAdpater.getList(retModel);
                }
            }
        });
    }
}
