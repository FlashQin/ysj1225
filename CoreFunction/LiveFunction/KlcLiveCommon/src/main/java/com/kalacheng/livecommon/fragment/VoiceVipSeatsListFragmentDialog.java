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

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.buscommon.model.ApiUserBasicInfo;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.VoiceVipSeatsListAdpater;
import com.kalacheng.util.utils.DpUtil;

import java.util.List;

/*
 * 贵宾席列表
 * */
public class VoiceVipSeatsListFragmentDialog extends BaseDialogFragment {
    private AppJoinRoomVO apiJoinRoom;
    private VoiceVipSeatsListAdpater adpater;
    private TextView voiceVipSeats_Tips;
    RecyclerView voiceVipSeats_List;

    @Override
    protected int getLayoutId() {
        return R.layout.voice_vip_seatslist;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    public void getInfotmation(AppJoinRoomVO apiJoinRoom) {
        this.apiJoinRoom = apiJoinRoom;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.getScreenHeight() / 2;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        voiceVipSeats_Tips = mRootView.findViewById(R.id.voiceVipSeats_Tips);
        ImageView close = mRootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        voiceVipSeats_List = mRootView.findViewById(R.id.voiceVipSeats_List);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        voiceVipSeats_List.setLayoutManager(manager);
        adpater = new VoiceVipSeatsListAdpater(mContext);
        voiceVipSeats_List.setAdapter(adpater);

        getData();
    }

    //查看贵宾席列表
    public void getData() {
        HttpApiPublicLive.userVIPSeatsList(LiveConstants.ANCHORID, apiJoinRoom.liveType, new HttpApiCallBackArr<ApiUserBasicInfo>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUserBasicInfo> retModel) {
                if (code == 1) {
                    if (retModel == null || retModel.size() == 0) {
                        voiceVipSeats_Tips.setVisibility(View.VISIBLE);
                        voiceVipSeats_List.setVisibility(View.GONE);
                    } else {
                        voiceVipSeats_List.setVisibility(View.VISIBLE);
                        voiceVipSeats_Tips.setVisibility(View.GONE);
                        adpater.getVipList(retModel);
                    }

                }
            }
        });
    }
}
