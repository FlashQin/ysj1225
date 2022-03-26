package com.kalacheng.one2onelive.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.busooolive.model.OOOInviteRet;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.livecommon.component.OOOLiveBaseActivity;
import com.kalacheng.one2onelive.R;
import com.kalacheng.one2onelive.component.ComponentConfig;
import com.kalacheng.util.utils.aop.SingleClick;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.xuantongyun.livecloud.agora.framework.TiPreprocessor;
import com.xuantongyun.livecloud.protocol.OOOSvipLiveUtils;


@Route(path = ARouterPath.One2OneSvipLive)
public class One2OneSvipLiveAnchorActivity extends OOOLiveBaseActivity {


    @Autowired(name = ARouterValueNameConfig.OOOLiveJoinRoom)
    ApiUserInfo info;
    @Autowired(name = ARouterValueNameConfig.OOOLiveJFeeUid)
    long feeUid;
    @Autowired(name = ARouterValueNameConfig.OOOLiveType)
    int liveType;//1 正常语音和视频 ，2 求聊
    @Autowired(name = ARouterValueNameConfig.OOOInviteRet)
    OOOInviteRet oooInviteRet;// 邀请用户响应

    ObjectAnimator giftAnimatorEnd2;
    ObjectAnimator giftAnimator2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TiPreprocessor.mEnabled = true;
        LiveConstants.isInsideRoomType = 0;
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
                giftAnimator2 = ObjectAnimator.ofFloat(frameLayout2, "translationX", 1500, 0);
                giftAnimator2.setDuration(500);
                giftAnimator2.setInterpolator(new LinearInterpolator());
                giftAnimator2.start();

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
                giftAnimatorEnd2 = ObjectAnimator.ofFloat(frameLayout2, "translationX", 1500);
                giftAnimatorEnd2.setDuration(500);
                giftAnimatorEnd2.setInterpolator(new LinearInterpolator());
                giftAnimatorEnd2.start();

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
        frameLayout2 = (FrameLayout) findViewById(R.id.fl_root2);
        frameLayout3 = (FrameLayout) findViewById(R.id.fl_root3);
        frameLayout4 = (FrameLayout) findViewById(R.id.fl_root4);
        frameLayout5 = (FrameLayout) findViewById(R.id.fl_root5);

        if (liveType == 1) {
            setComponent(ComponentConfig.One2OneLiveCOMPONENT1, frameLayout1);
            setComponent(ComponentConfig.One2OneLiveCOMPONENT2, frameLayout2);
            setComponent(ComponentConfig.One2OneLiveCOMPONENT3, frameLayout3);
            setComponent(ComponentConfig.One2OneLiveCOMPONENT4, frameLayout4);
            setComponent(ComponentConfig.One2OneLiveCOMPONENT5, frameLayout5);


        } else if (liveType == 2) {
            setComponent(ComponentConfig.One2OneSeekChatANCHORCOMPONENT1, frameLayout1);
            setComponent(ComponentConfig.One2OneSeekChatANCHORCOMPONENT2, frameLayout2);
            setComponent(ComponentConfig.One2OneSeekChatANCHORCOMPONENT3, frameLayout3);
            setComponent(ComponentConfig.One2OneSeekChatANCHORCOMPONENT4, frameLayout4);
            setComponent(ComponentConfig.One2OneSeekChatANCHORCOMPONENT5, frameLayout5);

        }

        LiveConstants.FEEUID = feeUid;
        LiveConstants.ROOMID = 0;
        LiveConstants.LiveAddress = 2;
        LiveConstants.isDisplayRobChat = true;

        // 下面是加入直播间的事件
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveJoinRoom, info);
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveType, oooInviteRet);
    }


    @SingleClick
    @Override
    public void onBackPressed() {
        if (LiveConstants.ROOMID == 0) {
            if (LiveConstants.mIsOOOSend == true) {//撤销通话
                HttpApiOOOCall.cancelInvite(LiveConstants.mOOOSessionID, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        // ggm 这个地方应该关闭Activity或者根据UI来
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);
                    }
                });
            } else {
                if (liveType == 1) {
                    HttpApiOOOCall.replyInvite(0, LiveConstants.mOOOSessionID, new HttpApiCallBack<OOOReturn>() {
                        @Override
                        public void onHttpRet(int code, String msg, OOOReturn retModel) {
                            // ggm 你拒绝了别人应该 转到别的地方去。

                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);

                        }
                    });
                } else if (liveType == 2) {
                    HttpApiOOOCall.exitPushChat(new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, null);
                        }
                    });
                }

            }
        } else {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOEndRequestGetTime, null);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void close() {
        frameLayout1.removeAllViews();
        frameLayout2.removeAllViews();
        frameLayout3.removeAllViews();
        frameLayout4.removeAllViews();
        frameLayout5.removeAllViews();



        LiveBundle.getInstance().removeAllListener();

        LiveBundle.getInstance().removeSocketClient(this.getLocalClassName());
//        LiveConstants.mOOOSessionID = 0;
        VoiceLiveOwnStateBean.IsMute = false;
        VoiceLiveOwnStateBean.IsOpen3T = false;
        LiveConstants.isDisplayRobChat = false;
        OOOSvipLiveUtils.getInstance().leaveChannel();
        LiveConstants.LiveAddress = 0;
        LiveConstants.ROOMID = 0;

        finish();
    }
}
