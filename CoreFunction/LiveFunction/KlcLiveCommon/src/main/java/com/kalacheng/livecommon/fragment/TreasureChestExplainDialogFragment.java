package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalacheng.busgame.httpApi.HttpApiGame;
import com.kalacheng.busgame.model.GameKind;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;

/*
* 玩法说明
* */
public class TreasureChestExplainDialogFragment extends BaseDialogFragment {
    private TextView TreasureChestExplain_content;
    @Override
    protected int getLayoutId() {
        return R.layout.treasurechest_explain;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TreasureChestExplain_content = mRootView.findViewById(R.id.TreasureChestExplain_content);

        ImageView TreasureChestExplain_close = mRootView.findViewById(R.id.TreasureChestExplain_close);
        TreasureChestExplain_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getData();

    }

    public void getData(){
        HttpApiGame.getGameKind(1, new HttpApiCallBack<GameKind>() {
            @Override
            public void onHttpRet(int code, String msg, GameKind retModel) {
                if (code ==1){
                    TreasureChestExplain_content.setText(retModel.gameExplain);
                }
            }
        });
    }
}
