package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.utils.SmallLiveRoomDialogFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiPushChat;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

/*
 * socket抢聊推送的状态
 * */

public class RobChatWowenDialogFragment {
    Dialog dialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    break;
            }
        }
    };

    public void show(final Context mContext, final ApiPushChat apiPushChat) {

        dialog = new Dialog(mContext, R.style.dialog2);
        dialog.setContentView(R.layout.robchatwowen);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.TopToBottomAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.TOP;
        window.setAttributes(params);

        RoundedImageView avatarIv = dialog.findViewById(R.id.avatarIv);
        ImageLoader.display(apiPushChat.avatar, avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        TextView callPicTv = dialog.findViewById(R.id.callPicTv);
        callPicTv.setText(apiPushChat.coin + SpUtil.getInstance().getCoinUnit() + " / 分钟");

        TextView callTypeTv = dialog.findViewById(R.id.callTypeTv);
        callTypeTv.setText(apiPushChat.username);
        if (apiPushChat.chatType == 1) {
            callTypeTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_shipin, 0);
        } else {
            callTypeTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_yuyin, 0);
        }

        TextView robCahtTv = dialog.findViewById(R.id.robCahtTv);
        robCahtTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                HttpApiAPPAnchor.is_auth(3, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1 && null != retModel) {
                            if ("0".equals(retModel.no_use)) {
                                HttpApiOOOCall.robPushChat(apiPushChat.sessionID, apiPushChat.userId, new HttpApiCallBack<OOOReturn>() {
                                    @Override
                                    public void onHttpRet(int code, String msg, final OOOReturn retModel) {
                                        if (code == 1 && retModel != null) {
                                            LiveConstants.mOOOSessionID = retModel.sessionID;

                                            // 1v1 如果是在语音直播间 先关闭掉直播间 在进入1v1
                                            if (LiveConstants.isInsideRoomType == 2 && LiveConstants.ANCHORID != HttpClient.getUid()) {
                                                if (LiveConstants.isSamll) {
                                                    // 是否最小化
                                                    SmallLiveRoomDialogFragment.getInstance().closeRoom();
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (apiPushChat.chatType == 1) {
                                                                ARouter.getInstance().build(ARouterPath.One2OneSeekChatLive)
                                                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                                                        .navigation();
                                                            } else if (apiPushChat.chatType == 2) {
                                                                ARouter.getInstance().build(ARouterPath.OneVoiceSeekChatLive)
                                                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                                                        .navigation();
                                                            }
                                                        }
                                                    }, 500);
                                                    return;
                                                } else {
                                                    // 是否在房间内 在语音房间 先退出语音房间 在进行1v1通话
                                                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
                                                    LiveConstants.isInsideRoomType = 0;
                                                }
                                            }
                                            if (apiPushChat.chatType == 1) {
                                                ARouter.getInstance().build(ARouterPath.One2OneSeekChatLive)
                                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                                        .navigation();
                                            } else if (apiPushChat.chatType == 2) {
                                                ARouter.getInstance().build(ARouterPath.OneVoiceSeekChatLive)
                                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                                        .navigation();
                                            }
                                        } else if (code == 2) {
                                            SingLelootDialogFragment.getInstance().show(mContext);
//                                            SingLelootDialogFragment singLelootDialogFragment = new SingLelootDialogFragment();
//                                            singLelootDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "SingLelootDialogFragment");

                                        } else {
                                            ToastUtil.show(msg);
                                        }
                                    }
                                });

                            } else if ("1".equals(retModel.no_use)) {
                                AnchorAttestationDialogFragment.getInstance().show(mContext);
//                                AnchorAttestationDialogFragment anchorAttestationDialogFragment = new AnchorAttestationDialogFragment();
//                                anchorAttestationDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "AnchorAttestationDialogFragment");
                            } else {
                                if (!TextUtils.isEmpty(msg)) {
                                    ToastUtil.show(msg);
                                }
                            }
                        } else {
                            if (!TextUtils.isEmpty(msg)) {
                                ToastUtil.show(msg);
                            }
                        }
                    }
                });
               /* HttpApiOOOCall.robPushChat(apiPushChat.sessionID, apiPushChat.userId, new HttpApiCallBack<OOOReturn>() {
                    @Override
                    public void onHttpRet(int code, String msg, OOOReturn retModel) {
                        if (code == 1 && retModel != null) {
                            LiveConstants.mOOOSessionID = retModel.sessionID;
                            if (apiPushChat.chatType == 1){
                                ARouter.getInstance().build(ARouterPath.One2OneSeekChatLive)
                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                        .navigation();
                            }else if(apiPushChat.chatType ==2){
                                ARouter.getInstance().build(ARouterPath.OneVoiceSeekChatLive)
                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                        .navigation();
                            }
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });*/
                dismiss();
            }
        });
        mHandler.sendEmptyMessageDelayed(1, 3000);
        dialog.show();

    }

    public void dismiss() {
        dialog.dismiss();
    }

    public interface OnPermissionClickListener {
        void onClick();
    }
}
