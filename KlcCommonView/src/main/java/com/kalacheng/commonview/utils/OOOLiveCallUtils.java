package com.kalacheng.commonview.utils;

import android.content.Context;
import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.busooolive.model.OOOInviteRet;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.util.utils.ToastUtil;

//一对一拨打请求
public class OOOLiveCallUtils {


    //    private IMApiOOOLive imApiOOOLive;
    private static class SingletonHolder {
        private static final OOOLiveCallUtils INSTANCE = new OOOLiveCallUtils();
    }

    private OOOLiveCallUtils() {
//        imApiOOOLive = new IMApiOOOLive();
//       imApiOOOLive.init(IMUtil.getClient());
    }

    public static final OOOLiveCallUtils getInstance() {
        return OOOLiveCallUtils.SingletonHolder.INSTANCE;
    }

    /*   //一对一视频邀请
       public void inviteAnchorChat(Context mContext, int isVideo,UserBasicInfo info){
           getVerification(mContext,isVideo,info);
       }*/
    //一对一视频和语音邀请  isVideo 1 视频 2语音
    public void OnetoOneinviteAnchorChat(Context mContext, final int isVideo, final ApiUserInfo info, final int livetype) {
        if (livetype == 1) {//如果是一对一视频和语音邀请 走验证
            getVerification(mContext, isVideo, info, livetype);
        } else {
            //如果求聊 不走验证
            // 1v1 如果是在语音直播间 先关闭掉直播间 在进入1v1
            if (LiveConstants.isInsideRoomType == 2 && LiveConstants.ANCHORID != HttpClient.getUid()) {
                if (LiveConstants.isSamll) {
                    // 是否最小化
                    SmallLiveRoomDialogFragment.getInstance().closeRoom();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isVideo == 1) {//一对一视频
                                ARouter.getInstance().build(ARouterPath.One2OneSvipLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, info).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, HttpClient.getUid()).withInt(ARouterValueNameConfig.OOOLiveType, livetype).navigation();
                            } else {//一对一语音
                                ARouter.getInstance().build(ARouterPath.OneVoiceLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, info).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, HttpClient.getUid()).withInt(ARouterValueNameConfig.OOOLiveType, livetype).navigation();
                            }
                        }
                    }, 500);
                    return;
                }else {
                    // 是否在房间内 在语音房间 先退出语音房间 在进行1v1通话
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
                    LiveConstants.isInsideRoomType = 0;
                }
            }

            // 求聊
            LiveConstants.activeId = -1;
            if (isVideo == 1) {
                ARouter.getInstance().build(ARouterPath.One2OneSvipLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, info).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, HttpClient.getUid()).withInt(ARouterValueNameConfig.OOOLiveType, livetype).navigation();
            } else {
                ARouter.getInstance().build(ARouterPath.OneVoiceLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, info).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, HttpClient.getUid()).withInt(ARouterValueNameConfig.OOOLiveType, livetype).navigation();
            }
        }
    }

    /*
     * livetype 1,正常语音和视频 2 ，求聊
     * */
    //通话验证
    public void getVerification(final Context mContext, final int isVideo, final ApiUserInfo info, final int livetype) {
        // * ，code:1成功；2对方正忙；3对方正在通话！;4对方不在线；9用户余额不足无法邀请通话；10不能向自己发起邀请； 11数据错误12贵族才能通话13用户和用户不能打电话14主播和主播不能打电话15对方开启了勿扰16主播正忙
        HttpApiOOOCall.inviteChat(info.userId, isVideo, new HttpApiCallBack<OOOInviteRet>() {
            @Override
            public void onHttpRet(int code, String msg, final OOOInviteRet retModel) {
                if (code == 1) {
                    LiveConstants.activeId = HttpClient.getUid();

                    // 1v1 如果是在语音直播间 先关闭掉直播间 在进入1v1
                    if (LiveConstants.isInsideRoomType == 2 && LiveConstants.ANCHORID != HttpClient.getUid()) {
                        if (LiveConstants.isSamll) {
                            // 是否最小化
                            SmallLiveRoomDialogFragment.getInstance().closeRoom();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isVideo == 1) {//一对一视频
                                        ARouter.getInstance().build(ARouterPath.One2OneSvipLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, info).withParcelable(ARouterValueNameConfig.OOOInviteRet, retModel).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, 0).withInt(ARouterValueNameConfig.OOOLiveType, livetype).navigation();
                                    } else {//一对一语音
                                        ARouter.getInstance().build(ARouterPath.OneVoiceLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, info).withParcelable(ARouterValueNameConfig.OOOInviteRet, retModel).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, 0).withInt(ARouterValueNameConfig.OOOLiveType, livetype).navigation();
                                    }
                                }
                            }, 500);
                            return;
                        }else {
                            // 是否在房间内 在语音房间 先退出语音房间 在进行1v1通话
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
                            LiveConstants.isInsideRoomType = 0;
                        }
                    }

                    if (isVideo == 1) {
                        ARouter.getInstance().build(ARouterPath.One2OneSvipLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, info).withParcelable(ARouterValueNameConfig.OOOInviteRet, retModel).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, 0).withInt(ARouterValueNameConfig.OOOLiveType, livetype).navigation();
                    } else {
                        ARouter.getInstance().build(ARouterPath.OneVoiceLive).withParcelable(ARouterValueNameConfig.OOOLiveJoinRoom, info).withParcelable(ARouterValueNameConfig.OOOInviteRet, retModel).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, 0).withInt(ARouterValueNameConfig.OOOLiveType, livetype).navigation();
                    }
                } else if (code == 2) {
                    GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 3);
                } else if (code == 3) {
                    GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 4);
                } else if (code == 4) {
                    GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 5);
                } else if (code == 9) {
                    GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 1);
                } else if (code == 10) {
                    GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 8);
                } else if (code == 12) {
                    GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 2);

                } else if (code == 13) {
                    GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 6);

                } else if (code == 14) {
                    GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 7);

                } else if (code == 15) {
                    GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 9);
                } else if (code == 16) {
                    GetIntoRoomVerificationUtlis.getInstance().showTipsDialog(mContext, msg, "知道了");
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    //接听这验证
}

