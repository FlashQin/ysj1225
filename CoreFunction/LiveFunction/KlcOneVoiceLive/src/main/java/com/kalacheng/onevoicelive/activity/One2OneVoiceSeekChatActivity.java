package com.kalacheng.onevoicelive.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.onevoicelive.R;
import com.kalacheng.onevoicelive.component.ComponentConfig;
import com.kalacheng.livecommon.component.OOOLiveBaseActivity;
import com.klc.bean.live.LiveBundleKeyName;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.kalacheng.util.utils.aop.SingleClick;
import com.xuantongyun.livecloud.protocol.OOOVoiceUtils;

//求聊被邀请者
@Route(path = ARouterPath.OneVoiceSeekChatLive)
public class One2OneVoiceSeekChatActivity extends OOOLiveBaseActivity {
    @Autowired(name = ARouterValueNameConfig.OOOLiveSvipReceiveJoin)
    OOOReturn info;
    @Autowired(name = ARouterValueNameConfig.OOOLiveJFeeUid)
    long feeUid;

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
        return R.layout.one2oneseekchat;
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
        setComponent(ComponentConfig.One2OneSeekChatCOMPONENT1,frameLayout1);
        setComponent(ComponentConfig.One2OneSeekChatCOMPONENT2,frameLayout2);
        setComponent(ComponentConfig.One2OneSeekChatCOMPONENT3, frameLayout3);
        setComponent(ComponentConfig.One2OneSeekChatCOMPONENT4, frameLayout4);
        setComponent(ComponentConfig.One2OneSeekChatCOMPONENT5, frameLayout5);
        LiveConstants.FEEUID = feeUid;
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
                joinRoom.voiceThumb = info.avatarThumb;
                joinRoom.userAvatar = info.feeAvatar;
                joinRoom.notice = "";
                LiveConstants.ROOMID = info.roomId;
                if (info.role == 3) {
                    LiveConstants.IDENTITY = LiveConstants.IDENTITY.ANCHOR;
                } else if (info.role == 2) {
                    LiveConstants.IDENTITY = LiveConstants.IDENTITY.AUDIENCE;
                } else {
                    LiveConstants.IDENTITY = LiveConstants.IDENTITY.BROADCAST;
                }
                LiveConstants.ANCHORID = info.hostId;

                LiveBundle.getInstance().sendSimpleMsg(LiveBundleKeyName.JoinVoiceRoom, joinRoom);
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

        LiveConstants.mOOOSessionID = 0;
        LiveConstants.LiveAddress = 0;
        LiveConstants.ROOMID = 0;
        VoiceLiveOwnStateBean.IsOpen3T = false;
        LiveConstants.isDisplayRobChat = false;
        OOOVoiceUtils.getInstance().clean();
        finish();
    }
}
