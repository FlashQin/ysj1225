package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.buslivebas.entity.LiveRoomType;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.ConfigUtil;
import com.klc.bean.LiveRoomTypeBean;
import com.klc.bean.OpenLiveBean;
import com.klc.bean.VoiceOpenLiveBean;

import java.util.List;

// 直播间 设置的 Dialog
public class AnchorSettingDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private AppJoinRoomVO apiJoinRoom;
    private TextView administrators_roomtype;

    @Override
    protected int getLayoutId() {
        return R.layout.anchor_setting_dialog;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiJoinRoom = getArguments().getParcelable("ApiJoinRoom");
        //房间模式
        RelativeLayout room_mode = mRootView.findViewById(R.id.room_mode);
        room_mode.setOnClickListener(this);

        //管理员列表
        RelativeLayout administrators_list = mRootView.findViewById(R.id.administrators_list);
        administrators_list.setOnClickListener(this);
        //禁言列表
        RelativeLayout forbidden_list = mRootView.findViewById(R.id.forbidden_list);
        forbidden_list.setOnClickListener(this);
        //踢人列表
        RelativeLayout kick_list = mRootView.findViewById(R.id.kick_list);
        kick_list.setOnClickListener(this);

        ImageView set_close = mRootView.findViewById(R.id.set_close);
        set_close.setOnClickListener(this);

        administrators_roomtype = mRootView.findViewById(R.id.administrators_roomtype);
        if (apiJoinRoom.roomType == 1) {
            //房间类型1是私密直播，2是收费直播，3是计时直播
            administrators_roomtype.setText("私密直播");
        } else if (apiJoinRoom.roomType == 2) {
            administrators_roomtype.setText("收费直播");
        } else if (apiJoinRoom.roomType == 3) {
            administrators_roomtype.setText("计时直播");
        } else if (apiJoinRoom.roomType == 4) {
            administrators_roomtype.setText("贵族房间");
        } else {
            administrators_roomtype.setText("普通房间");
        }

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ChoiceLiveTypeSusser, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                int type = 0;
                if (o instanceof OpenLiveBean){
                    OpenLiveBean openLiveBean = (OpenLiveBean) o;
                    type = openLiveBean.type;
                }else if (o instanceof VoiceOpenLiveBean){
                    VoiceOpenLiveBean voiceOpenLiveBean = (VoiceOpenLiveBean) o;
                    type = voiceOpenLiveBean.type;
                }
                if (type == 1) {
                    //房间类型1是私密直播，2是收费直播，3是计时直播
                    administrators_roomtype.setText("私密直播");
                } else if (type == 2) {
                    administrators_roomtype.setText("收费直播");
                } else if (type == 3) {
                    administrators_roomtype.setText("计时直播");
                } else if (type == 4) {
                    administrators_roomtype.setText("贵族房间");
                } else {
                    administrators_roomtype.setText("普通房间");
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        RelativeLayout rl = mRootView.findViewById(R.id.rl_is_anchor_shop);
        CheckBox is_anchor_shop = mRootView.findViewById(R.id.is_anchor_shop);
        if (apiJoinRoom.liveFunction == 1) {
            is_anchor_shop.setChecked(true);
        } else {
            is_anchor_shop.setChecked(false);
        }

        if (ConfigUtil.getBoolValue(R.bool.containShopping) && LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR) {
            rl.setVisibility(View.VISIBLE);
        } else {
            rl.setVisibility(View.GONE);
        }
        // 更换直播 普通直播 - 带货直播
        is_anchor_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (apiJoinRoom.liveFunction == 0) {
                    apiJoinRoom.liveFunction = 1;
                } else {
                    apiJoinRoom.liveFunction = 0;
                }
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_UPDATE_LIVE_STATUS, apiJoinRoom.liveFunction);
                dismiss();
            }
        });
    }

    //获取房间模式
    private void getSetRoomStatus(int liveType, String showId) {
        HttpApiPublicLive.getLiveRoomType(liveType, showId, new HttpApiCallBackArr<LiveRoomType>() {
            @Override
            public void onHttpRet(int code, String msg, List<LiveRoomType> retModel) {
                if (code==1 && null != retModel && retModel.size()>0){

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.room_mode) {
            LiveRoomTypeBean bean = new LiveRoomTypeBean();
            bean.mContent = apiJoinRoom.roomTypeVal;
            bean.id = apiJoinRoom.roomType;
            bean.LiveType = apiJoinRoom.liveType;
            bean.type = 1;
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceLiveType, bean);
            dismiss();
        } else if (id == R.id.administrators_list) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveAdministrators, null);
            dismiss();
        } else if (id == R.id.forbidden_list) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveForbidden, null);
            dismiss();
        } else if (id == R.id.kick_list) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_KickList, null);
            dismiss();
        } else if (id == R.id.set_close) {
            dismiss();
        }
    }
}
