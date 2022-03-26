package com.kalacheng.live.component.componentlive;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalacheng.buslive.socketcontroller.IMApiLiveAnchorLine;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveAnchorLineSend;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.libuser.model.ApiCloseLine;
import com.kalacheng.libuser.model.ApiLinkEntity;
import com.kalacheng.libuser.model.ApiSendLineMsgRoom;
import com.kalacheng.libuser.model.ApiSendMsgUser;
import com.kalacheng.libuser.model.ApiUsableAnchorResp;
import com.kalacheng.libuser.model.ApiUserLineRoom;

import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.live.R;
import com.kalacheng.util.utils.DialogUtil;
import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class AnchorInteractiveComponent extends BaseViewHolder implements LiveBundle.onLiveSocket {

    private IMApiLiveAnchorLine imApiLive;
    private SocketClient mSocket;
    private Disposable micDisposable;

    public LiveConstants.LinkMicResponse linkMicResponse = LiveConstants.LinkMicResponse.NORESPONSE;
    private TextView tvTime;
    private Dialog dialog;


    public AnchorInteractiveComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.null_component;
    }

    @Override
    protected void init() {
        LiveBundle.getInstance().addLiveSocketListener(this);

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                removeFromParent();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LaunchInteraction, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                ApiUsableAnchorResp apiUsableAnchorResp = (ApiUsableAnchorResp) o;
                imApiLive.invitationAnchorLine(apiUsableAnchorResp.user_id, new IMApiCallBack<ApiLinkEntity>() {
                    @Override
                    public void onImRet(int i, String s, ApiLinkEntity apiLinkEntity) {
                        if (!TextUtils.isEmpty(s))
                            ToastUtil.show(s);
                    }
                });
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });
    }

    private void showApplyDialog(final ApiSendMsgUser apiSendMsgUser) {
        if (dialog != null) {
            dialog = null;
        }
        dialog = DialogUtil.show2BtnDialog(mContext, R.style.dialog, R.layout.dialog_link_mic_wait, true, true, new DialogUtil.SimpleCallback() {
            @Override
            public void onConfirmClick() {
                if (micDisposable != null) {
                    micDisposable.dispose();
                }

                linkMicResponse = LiveConstants.LinkMicResponse.ACCEPT;
                imApiLive.invitationAnchorLineResp(1, apiSendMsgUser.userId, LiveConstants.LinkSessionID, new IMApiCallBack<SingleString>() {
                    @Override
                    public void onImRet(int i, String s, SingleString singleString) {
                        if (!TextUtils.isEmpty(s))
                            ToastUtil.show(s);
                    }
                });
            }

            @Override
            public void onConfirmClick(String input) {

            }

            @Override
            public void onCancelClick() {
                if (micDisposable != null) {
                    micDisposable.dispose();
                }
                linkMicResponse = LiveConstants.LinkMicResponse.REFUSE;
                imApiLive.invitationAnchorLineResp(2, apiSendMsgUser.userId, LiveConstants.LinkSessionID, new IMApiCallBack<SingleString>() {
                    @Override
                    public void onImRet(int i, String s, SingleString singleString) {
                        if (!TextUtils.isEmpty(s))
                            ToastUtil.show(s);
                    }
                });
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (null != micDisposable)
                    micDisposable.dispose();
                if (linkMicResponse == LiveConstants.LinkMicResponse.NORESPONSE) {
                    imApiLive.invitationAnchorLineResp(3, apiSendMsgUser.userId, LiveConstants.LinkSessionID, new IMApiCallBack<SingleString>() {
                        @Override
                        public void onImRet(int i, String s, SingleString singleString) {
                            if (!TextUtils.isEmpty(s))
                                ToastUtil.show(s);
                        }
                    });
                }
                linkMicResponse = LiveConstants.LinkMicResponse.NORESPONSE;
                removeFromParent();
            }
        });
        tvTime = (TextView) (dialog.findViewById(R.id.wait_text));
        ImageView imageAvatar = (ImageView) (dialog.findViewById(R.id.avatar));
        TextView tvName = (TextView) (dialog.findViewById(R.id.name));
//        ImageLoader.display(apiSendMsgUser.resp.avatar_thumb, imageAvatar);
        tvName.setText(apiSendMsgUser.userName);
        micDisposable = Flowable.interval(1000, TimeUnit.MILLISECONDS).take(apiSendMsgUser.line_time + 1).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                tvTime.setText(WordUtil.getString(R.string.link_mic_wait) + "(" + (apiSendMsgUser.line_time - aLong) + "s)...");

            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {

                dialog.dismiss();
            }
        }).subscribe();
    }

    @Override
    public void init(String groupName, SocketClient socketClient) {
        mSocket = socketClient;
        imApiLive = new IMApiLiveAnchorLine();
        imApiLive.init(mSocket);
        IMUtil.addReceiver(groupName, new IMRcvLiveAnchorLineSend() {

            @Override
            public void onAnchorLineReq(ApiSendMsgUser apiSendMsgUser) {
                LiveConstants.LinkSessionID = apiSendMsgUser.sessionID;
                showApplyDialog(apiSendMsgUser);
            }

            @Override
            public void onAnchorLineResp(ApiSendLineMsgRoom apiSendLineMsgRoom) {
                if (apiSendLineMsgRoom.status == 1) {
                    if (apiSendLineMsgRoom.toRoomId != LiveConstants.ROOMID) {
                        if (HttpClient.getUid() == LiveConstants.ANCHORID) {
                            ToastUtil.show(WordUtil.getString(R.string.link_mic_accept));
                        }
                        ApiUserLineRoom apiUserLineRoom = new ApiUserLineRoom();
                        apiUserLineRoom.toUid = apiSendLineMsgRoom.toUid;
                        apiUserLineRoom.status = apiSendLineMsgRoom.status;
                        apiUserLineRoom.toRoomId = apiSendLineMsgRoom.toRoomId;
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_StartInteraction, apiUserLineRoom);
                    }

                } else if (apiSendLineMsgRoom.status == 2) {
                    ToastUtil.show(WordUtil.getString(R.string.link_mic_refuse));

                } else if (apiSendLineMsgRoom.status == 3) {
                    ToastUtil.show(WordUtil.getString(R.string.link_mic_noresponse));
                }


            }

            @Override
            public void onAnchorCloseLine(ApiCloseLine apiCloseLine) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ReceiveCloseInteraction, apiCloseLine);
            }

            @Override
            public void onOtherMsg(Object obj) {

            }
        });
    }
}
