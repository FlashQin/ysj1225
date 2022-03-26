package com.kalacheng.one2onelive.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.busooolive.model.OTMAssisRet;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.one2onelive.R;
import com.kalacheng.one2onelive.component.ComponentConfig;
import com.kalacheng.livecommon.component.OOOLiveBaseActivity;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.util.utils.aop.SingleClick;
import com.xuantongyun.livecloud.protocol.OOOSvipLiveUtils;

import java.util.List;

//svip被邀请者
@Route(path = ARouterPath.One2OneSvipSideshowLive)
public class One2OneSvipSideshowActivity extends OOOLiveBaseActivity {
    @Autowired(name = ARouterValueNameConfig.OOOLiveSvipReceiveJoin)
    OOOReturn info;
    @Autowired(name = ARouterValueNameConfig.OOOLiveJFeeUid)
    long feeUid;
    @Autowired(name = ARouterValueNameConfig.OOOSeekChatLiveSideshow)
    List<OTMAssisRet> otmAssisRetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initParams() {
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_CloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                close();

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_BackHome, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                close();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                close();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //向左滑，显示
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LIFT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                frameLayout3.setVisibility(View.VISIBLE);
                frameLayout4.setVisibility(View.VISIBLE);
                frameLayout5.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //向右滑，消失
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_RIGHT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                frameLayout3.setVisibility(View.GONE);
                frameLayout4.setVisibility(View.GONE);
                frameLayout5.setVisibility(View.GONE);

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.one2onesviplive;
    }
    FrameLayout frameLayout1;
    FrameLayout frameLayout2;
    FrameLayout frameLayout3;
    FrameLayout frameLayout4;
    FrameLayout frameLayout5;

    protected void initComponent() {
        frameLayout1 = (FrameLayout) findViewById(R.id.fl_root1);
        frameLayout2= (FrameLayout) findViewById(R.id.fl_root2);
        frameLayout3 = (FrameLayout) findViewById(R.id.fl_root3);
        frameLayout4 = (FrameLayout) findViewById(R.id.fl_root4);
        frameLayout5 = (FrameLayout) findViewById(R.id.fl_root5);
        setComponent(ComponentConfig.One2OneSideshowLiveCOMPONENT1,frameLayout1);
        setComponent(ComponentConfig.One2OneSideshowLiveCOMPONENT2,frameLayout2);
        setComponent(ComponentConfig.One2OneSideshowLiveCOMPONENT3, frameLayout3);
        setComponent(ComponentConfig.One2OneSideshowLiveCOMPONENT4, frameLayout4);
        setComponent(ComponentConfig.One2OneSideshowLiveCOMPONENT5, frameLayout5);

        LiveConstants.ROOMID = 0;
        LiveConstants.LiveAddress = 2;
        LiveConstants.isDisplayRobChat = true;
        //接通视频
        AppJoinRoomVO joinRoom = new AppJoinRoomVO();
        joinRoom.anchorId = info.hostId;
        joinRoom.anchorName = info.userName;
        joinRoom.anchorAvatar = info.avatar;
        joinRoom.role = info.role;
        joinRoom.roomId = info.roomId;
        joinRoom.isFollow = info.isAtten;
        joinRoom.liveType = info.type;
        joinRoom.noticeMsg = info.noticeMsg;
        joinRoom.showid = info.showid;
        joinRoom.notice="";

        LiveConstants.ROOMID = info.roomId;
        if (info.role == 3) {
            LiveConstants.IDENTITY = LiveConstants.IDENTITY.ANCHOR;
        } else if (info.role == 2) {
            LiveConstants.IDENTITY = LiveConstants.IDENTITY.AUDIENCE;
        } else {
            LiveConstants.IDENTITY = LiveConstants.IDENTITY.BROADCAST;
        }
        LiveConstants.ANCHORID = info.hostId;
        LiveConstants.FEEUID = info.feeId;
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLinkTTT, joinRoom);
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OpenLiveMsg, joinRoom);
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.RoomInfoList, joinRoom);

        info.assisRets =otmAssisRetList;
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveLinkOK, info);
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OneFeeCall,info.freeCallMsg);

    }


    @SingleClick
    @Override
    public void onBackPressed() {
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOEndRequestGetTime,null);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void close(){
        frameLayout1.removeAllViews();
        frameLayout2.removeAllViews();
        frameLayout3.removeAllViews();
        frameLayout4.removeAllViews();
        frameLayout5.removeAllViews();

        LiveBundle.getInstance().removeAllListener();

        LiveBundle.getInstance().removeSocketClient(this.getLocalClassName());

        LiveConstants.mOOOSessionID = 0;
        LiveConstants.LiveAddress = 0;
        LiveConstants.ROOMID = 0;
        VoiceLiveOwnStateBean.IsMute = false;
        VoiceLiveOwnStateBean.IsOpen3T = false;
        LiveConstants.isDisplayRobChat = false;
        OOOSvipLiveUtils.getInstance().leaveChannel();
        finish();
    }
}
