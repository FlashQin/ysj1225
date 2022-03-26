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
import com.kalacheng.busgame.model.GameUserPrizeDTO;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.TreasureChestMyPrizeAdpater;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBackArr;

import java.util.List;

/*
 * 我的奖品
 * */
public class TreasureChestMyPrizeDialogFragment extends BaseDialogFragment {
    private RecyclerView TreasureChestMyPrize_List;
    private TreasureChestMyPrizeAdpater adpater;

    @Override
    protected int getLayoutId() {
        return R.layout.treasurechest_myprize;
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
        params.height = (DpUtil.getScreenHeight() / 3 * 2);
        ;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TreasureChestMyPrize_List = mRootView.findViewById(R.id.TreasureChestMyPrize_List);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        TreasureChestMyPrize_List.setLayoutManager(manager);
        adpater = new TreasureChestMyPrizeAdpater(getActivity());
        TreasureChestMyPrize_List.setAdapter(adpater);

        ImageView close = mRootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getData();
    }

    public void getData() {
        HttpApiGame.getUserPrizeList(1, 0, 20, new HttpApiCallBackArr<GameUserPrizeDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<GameUserPrizeDTO> retModel) {
                if (code == 1) {
                    if(retModel!=null&&retModel.size()>0){
                        adpater.getData(retModel);
                    }
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
