package com.kalacheng.live.component.componentlive;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;

import com.kalacheng.buslive.socketcontroller.IMApiLiveAnchorPk;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveAnchorPkSend;
import com.kalacheng.busnobility.model.ApiPkResult;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.libuser.model.ApiBaseEntity;
import com.kalacheng.libuser.model.ApiCloseLine;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.ApiPkResultRoom;
import com.kalacheng.libuser.model.ApiSendMsgUser;
import com.kalacheng.libuser.model.ApiSendPKMsgRoom;

import com.kalacheng.live.component.fragment.LiveLinkMicListDialogFragment;
import com.kalacheng.live.component.viewmodel.LinkMicPKViewModel;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.frame.config.APPProConfig;

import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.live.R;
import com.kalacheng.live.databinding.ViewGamePkBinding;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.view.ProgressTextView2;
import com.wengying666.imsocket.IMApiCallBack;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class LivePkComponent extends BaseMVVMViewHolder<ViewGamePkBinding, LinkMicPKViewModel> implements LiveBundle.onLiveSocket {
    IMApiLiveAnchorPk imApiLive;
    private int bloodsWidth;
    IMApiCallBack<SingleString> respCallback;
    public LiveConstants.LinkMicResponse pkResponse = LiveConstants.LinkMicResponse.NORESPONSE;
    Disposable micPkDisposable, pkDisposable, pushDisposable;
    private ProgressTextView2 tvTime;
    private Dialog dialog;


    public LivePkComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected void init() {
        LiveBundle.getInstance().addLiveSocketListener(this);
        if (null == respCallback) {
            respCallback = new IMApiCallBack<SingleString>() {
                @Override
                public void onImRet(int code, String errMsg, SingleString retModel) {
//                    if (retModel != null && retModel.msg != null)
//                        ToastUtil.show(retModel.msg);
                }
            };
        }
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.FunctionMsg, new MsgListener.SimpleMsgListener<Integer>() {
            @Override
            public void onMsg(Integer integer) {
                if (integer == R.mipmap.icon_live_func_lm) {
                    LiveLinkMicListDialogFragment fragment = new LiveLinkMicListDialogFragment();
                    fragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveLinkMicListDialogFragment");
                }
            }

            @Override
            public void onTagMsg(String tag, Integer integer) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ChangePKValue, new MsgListener.SimpleMsgListener<ApiPkResult>() {
            @Override
            public void onMsg(ApiPkResult apiPkResult) {
                onProgressChanged(apiPkResult);
            }

            @Override
            public void onTagMsg(String tag, ApiPkResult apiPkResult) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ReceiveCloseInteraction, new MsgListener.SimpleMsgListener<ApiCloseLine>() {
            @Override
            public void onMsg(ApiCloseLine apiCloseLine) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, ApiCloseLine apiCloseLine) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener<AppJoinRoomVO>() {
            @Override
            public void onMsg(final AppJoinRoomVO apiJoinRoom) {
                if (null != apiJoinRoom) {
                    if (apiJoinRoom.liveStatus == 4) {
                        clear();
                        addToParent();
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.videoRoot.getLayoutParams();
                        params.setMargins(0, DpUtil.dp2px(130), 0, 0);
                        params.height = APPProConfig.getInstance().getVidowHeight();
                        ApiPkResult apiPkResult = new ApiPkResult();
                        apiPkResult.votesPK = apiJoinRoom.currVotesPk;
                        apiPkResult.toVotesPK = apiJoinRoom.fromVotesPk;
                        onProgressChanged(apiPkResult);
                        pkDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS).take(apiJoinRoom.pkTime + 1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                binding.tvTime.setText(WordUtil.getString(R.string.live_pk_time_1) + " " + StringUtil.getDurationText((apiJoinRoom.pkTime - aLong) * 1000));
                            }
                        });
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, AppJoinRoomVO apiJoinRoom) {

            }
        });
        bloodsWidth = BaseApplication.getInstance().getResources().getDisplayMetrics().widthPixels / 2 - DpUtil.dp2px(10);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_game_pk;
    }

    public void clear() {
        if (null != pushDisposable)
            pushDisposable.dispose();
        if (null != pkDisposable)
            pkDisposable.dispose();
        if (null != micPkDisposable)
            micPkDisposable.dispose();
        binding.result.setVisibility(View.GONE);
        binding.ivRight.setVisibility(View.GONE);
        binding.ivLeft.setVisibility(View.GONE);
        removeFromParent();
    }

    public void end(final int result) {
        if (result == 0) {
            binding.result.setVisibility(View.VISIBLE);
            binding.result.setBackgroundResource(R.mipmap.icon_pingju);
            binding.ivLeft.setVisibility(View.GONE);
            binding.ivRight.setVisibility(View.GONE);
        } else if (result == -1) {
            binding.result.setVisibility(View.GONE);
            binding.ivLeft.setVisibility(View.VISIBLE);
            binding.ivLeft.setBackgroundResource(R.mipmap.icon_lose);
            binding.ivRight.setVisibility(View.VISIBLE);
            binding.ivRight.setBackgroundResource(R.mipmap.icon_win);
        } else if (result == 1) {
            binding.result.setVisibility(View.GONE);
            binding.ivLeft.setVisibility(View.VISIBLE);
            binding.ivLeft.setBackgroundResource(R.mipmap.icon_win);
            binding.ivRight.setVisibility(View.VISIBLE);
            binding.ivRight.setBackgroundResource(R.mipmap.icon_lose);
        }
    }

    @Override
    public void init(String groupName,SocketClient socketClient) {
        imApiLive = new IMApiLiveAnchorPk();
        imApiLive.init(socketClient);
        IMUtil.addReceiver( groupName,new IMRcvLiveAnchorPkSend() {
            @Override
            public void onAnchorPKReq(final ApiSendMsgUser apiSendMsgUser) {
//                final Dialog dialog = new Dialog(context, com.kalacheng.util.R.style.dialog);
//                dialog.setCancelable(true);
//                dialog.setCanceledOnTouchOutside(true);
//                dialog.setContentView(com.kalacheng.util.R.layout.dialog_pk_wait);
                LiveConstants.LinkSessionID = apiSendMsgUser.sessionID;
                dialog = DialogUtil.show2BtnDialog(mContext, R.style.dialog, R.layout.dialog_pk_wait, true, true, new DialogUtil.SimpleCallback() {
                    @Override
                    public void onConfirmClick() {
                        pkResponse = LiveConstants.LinkMicResponse.ACCEPT;
                        imApiLive.invitationAnchorPKResp(1, apiSendMsgUser.userId,LiveConstants.LinkSessionID, respCallback);
                    }

                    @Override
                    public void onConfirmClick(String input) {

                    }

                    @Override
                    public void onCancelClick() {
                        pkResponse = LiveConstants.LinkMicResponse.REFUSE;
                        imApiLive.invitationAnchorPKResp(2, apiSendMsgUser.userId,LiveConstants.LinkSessionID, respCallback);
                    }
                });
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (pkResponse == LiveConstants.LinkMicResponse.NORESPONSE) {
                            imApiLive.invitationAnchorPKResp(3, apiSendMsgUser.userId,LiveConstants.LinkSessionID,respCallback);
                        }
                        if (null != micPkDisposable)
                            micPkDisposable.dispose();
                    }
                });
                tvTime = (ProgressTextView2) (dialog.findViewById(R.id.pk_wait_progress));
                micPkDisposable = Flowable.interval(1000, TimeUnit.MILLISECONDS).take(apiSendMsgUser.line_time + 1).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        tvTime.setProgress((int) (apiSendMsgUser.line_time - aLong));
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        dialog.dismiss();
                    }
                }).subscribe();
            }

            @Override
            public void onAnchorPKResp(final ApiSendPKMsgRoom apiSendPKMsgRoom) {
                if (apiSendPKMsgRoom.status == 1) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_PKStart, apiSendPKMsgRoom);
                    clear();
                    addToParent();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.videoRoot.getLayoutParams();
                    params.setMargins(0, DpUtil.dp2px(110), 0, 0);
                    params.height = APPProConfig.getInstance().getVidowHeight();
                    ApiPkResult apiPkResult = new ApiPkResult();
                    apiPkResult.votesPK = 0;
                    apiPkResult.toVotesPK = 0;
                    onProgressChanged(apiPkResult);
                    pkDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS).take(apiSendPKMsgRoom.pkTime + 1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            binding.tvTime.setText(WordUtil.getString(R.string.live_pk_time_1) + " " + StringUtil.getDurationText((apiSendPKMsgRoom.pkTime - aLong) * 1000));
                        }
                    });
                } else if (apiSendPKMsgRoom.status == 2) {
                    ToastUtil.show(WordUtil.getString(R.string.link_mic_refuse_pk));
                } else if (apiSendPKMsgRoom.status == 3) {
                    ToastUtil.show(WordUtil.getString(R.string.link_mic_noresponse));
                }

                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_IsRequestPK,null);
            }

            @Override
            public void onPKResultResp(final ApiPkResultRoom apiPkResultRoom) {
                if (pkDisposable!=null){
                    pkDisposable.dispose();
                    end(apiPkResultRoom.isWin);
                    pushDisposable = Flowable.interval(1000, TimeUnit.MILLISECONDS).take(apiPkResultRoom.punishTime + 1).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            binding.tvTime.setText(WordUtil.getString(R.string.live_pk_time_2) + " " + StringUtil.getDurationText((apiPkResultRoom.punishTime - aLong) * 1000));
                        }
                    }).doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_PKFinish, null);
                            clear();
                        }
                    }).subscribe();
                }
            }

            @Override
            public void onOtherMsg(Object obj) {

            }
        });

    }

    public void onProgressChanged(ApiPkResult apiPkResult) {
        if (binding.rlBloodstrip.getVisibility() == View.INVISIBLE) {
            binding.rlBloodstrip.setVisibility(View.VISIBLE);
        }
        RelativeLayout.LayoutParams bgLiveparams = (RelativeLayout.LayoutParams) binding.bgLiveBloodstrip.getLayoutParams();
        RelativeLayout.LayoutParams bgPkBloodparams = (RelativeLayout.LayoutParams) binding.bgPkBloodstrip.getLayoutParams();
        binding.bloods.setText(apiPkResult.votesPK + "");
        binding.pkBloods.setText(apiPkResult.toVotesPK + "");
        List<Double> booldList = new ArrayList<>();
        booldList.add(apiPkResult.votesPK);
        booldList.add(apiPkResult.toVotesPK);
        double max = Collections.max(booldList);
        Math.abs(max);
        if (apiPkResult.votesPK > 0) {
            binding.bgLiveBloodstrip.setBackgroundResource(R.drawable.bg_mypk);
        } else {
            binding.bgLiveBloodstrip.setBackgroundResource(R.drawable.bg_mynegativepk);
        }
        if (apiPkResult.toVotesPK > 0) {
            binding.bgPkBloodstrip.setBackgroundResource(R.drawable.bg_yourpk);
        } else {
            binding.bgPkBloodstrip.setBackgroundResource(R.drawable.bg_yournegativepk);
        }
        if (max < 1000) {
            bgLiveparams.width = (int) ((((double) Math.abs(apiPkResult.votesPK)) / 1000) * bloodsWidth);
            bgPkBloodparams.width = (int) ((((double) Math.abs(apiPkResult.toVotesPK)) / 1000) * bloodsWidth);
        } else {
            if (max == apiPkResult.votesPK) {
                bgLiveparams.width = bloodsWidth;
                bgPkBloodparams.width = (int) (((double) Math.abs(apiPkResult.votesPK)) / ((double) Math.abs(apiPkResult.toVotesPK)) * bloodsWidth);
            } else {
                bgPkBloodparams.width = bloodsWidth;
                bgLiveparams.width = (int) (((double) Math.abs(apiPkResult.votesPK)) / ((double) Math.abs(apiPkResult.toVotesPK)) * bloodsWidth);
            }
        }

    }
}
