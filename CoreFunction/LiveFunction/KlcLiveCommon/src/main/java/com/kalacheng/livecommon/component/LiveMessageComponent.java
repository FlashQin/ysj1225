package com.kalacheng.livecommon.component;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.base.listener.OnItemClickListener;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.buscommon.socketmsg.IMRcvCommonMsgSend;
import com.kalacheng.busdynamiccircle.socketmsg.IMRcvDynamiccircleSend;
import com.kalacheng.busgraderight.socketmsg.IMRcvGradeRightMsgSender;
import com.kalacheng.buslive.socketcontroller.IMApiLive;
import com.kalacheng.buslive.socketcontroller.IMApiLiveMsg;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveMsgSend;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.libuser.model.ApiExitRoom;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.libuser.model.ApiSendMsgRoom;
import com.kalacheng.libuser.model.ApiSendVideoUnReadNumber;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.libuser.model.ApiTimerExitRoom;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.LiveChatAdapter;
import com.kalacheng.livecommon.fragment.LiveInputDialogFragment;
import com.kalacheng.shortvideo.socketmsg.IMRcvShortVideoSend;
import com.kalacheng.livecommon.view.NormalGiftView;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.LruJsonCache;
import com.kalacheng.commonview.view.DanmuViewHolder;
import com.kalacheng.util.view.TopGradual;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import cn.jmessage.support.google.gson.Gson;
import cn.jmessage.support.google.gson.reflect.TypeToken;
import io.reactivex.disposables.Disposable;


public class LiveMessageComponent extends BaseViewHolder implements LiveBundle.onLiveSocket, DanmuViewHolder.ActionListener {
    private RecyclerView mChatRecyclerView;
    private RelativeLayout liveMessage;
    private LiveChatAdapter mLiveChatAdapter;
    private View mGiftTipGroup;
    private TextView mGiftTip;
    private ImageView mGiftImg;
    private Animation mGiftNumAnimator;
    private NormalGiftView[] mNormalGiftViewList = new NormalGiftView[2];
    private int mDp500;
    private MyHandler mHandler;
    private int playGiftIndex = 0;
    private boolean[] mLines;//弹幕的轨道
    private List<DanmuViewHolder> mList;
    private boolean isShowTip;
    private ConcurrentLinkedQueue<ApiGiftSender> mQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<ApiGiftSender> mTipQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<ApiSimpleMsgRoom> mDanMuQueue = new ConcurrentLinkedQueue<>();
    private IMApiLive imApiLive;
    private IMApiLiveMsg imApiLiveMsg;
    private boolean isFristMsg = true;
    //    private DisposableObserver<Long> pauseDisposable;
    private Disposable pauseDisposable;
    private SocketClient mSocketClient;

    private AppJoinRoomVO mJoinRoom;
    private String groupName;

    private LiveInputDialogFragment inputDialogFragment; // 对话输入框

    private LruJsonCache cache;

    public LiveMessageComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_message;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void init() {
        cache = LruJsonCache.get(mContext);
        LiveBundle.getInstance().addLiveSocketListener(this);
        mGiftTipGroup = findViewById(R.id.ll_gift_tip);
        mGiftTip = (TextView) findViewById(R.id.tv_tip);
        mGiftImg = (ImageView) findViewById(R.id.gift);
        liveMessage = (RelativeLayout) findViewById(R.id.liveMessage);

        mDp500 = DpUtil.dp2px(500);

//        Interpolator interpolator = new AccelerateDecelerateInterpolator();
//        mGiftNumAnimator = new ScaleAnimation(1.5f, 0.7f, 1.5f, 0.7f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f);
//        mGiftNumAnimator.setDuration(200);
//        mGiftNumAnimator.setInterpolator(interpolator);
        //聊天栏
        mChatRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_chat);
        mChatRecyclerView.setHasFixedSize(true);
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mChatRecyclerView.addItemDecoration(new TopGradual());
        mLiveChatAdapter = new LiveChatAdapter(mContext);
        mChatRecyclerView.setAdapter(mLiveChatAdapter);

        mLiveChatAdapter.setOnItemClickListener(new OnItemClickListener<ApiSimpleMsgRoom>() {
            @Override
            public void onItemClick(int position, ApiSimpleMsgRoom bean) {
                LiveConstants.TOUID = bean.uid;
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.Information, null);
//                LiveUserDialogFragment dialogUser = new LiveUserDialogFragment();
//                Bundle bundle = new Bundle();
//                bundle.putLong(ARouterValueNameConfig.LIVE_UID, LiveConstants.ANCHORID);
//                bundle.putLong(ARouterValueNameConfig.TO_UID, bean.uid);
//                bundle.putLong(ARouterValueNameConfig.Livetype, 2);
//                dialogUser.setArguments(bundle);
//                dialogUser.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveUserDialogFragment");
            }
        });

        mLines = new boolean[]{true, true, true};
        mList = new LinkedList<>();

        mNormalGiftViewList = new NormalGiftView[2];
        mHandler = new MyHandler(this);
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OpenChat, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                String name = "";
                if (null != o) {
                    name = (String) o;
                }
                inputDialogFragment = new LiveInputDialogFragment();
                inputDialogFragment.setSocket(mSocketClient, mJoinRoom.roomId, name, mJoinRoom.gzdmPrivilege, mJoinRoom.liveType);
                inputDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveInputDialogFragment");

                mLiveChatAdapter.scrollToBottom();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_GiftMsg, new MsgListener.SimpleMsgListener<ApiSimpleMsgRoom>() {
            @Override
            public void onMsg(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onTagMsg(String tag, ApiSimpleMsgRoom apiSimpleMsgRoom) {
                if (LiveMessageComponent.this.groupName.equals(tag)) {
                    mLiveChatAdapter.insertItem(apiSimpleMsgRoom);
                }
            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener<AppJoinRoomVO>() {
            @Override
            public void onMsg(AppJoinRoomVO apiJoinRoom) {
                mContentView.setTag("LiveMessageComponent");
                addToParent();

                mJoinRoom = apiJoinRoom;
                if (apiJoinRoom.liveType != 3 && apiJoinRoom.anchorId != HttpClient.getUid() && TextUtils.isEmpty(cache.getAsString(LiveConstants.Cache_SmallStatus))) {
                    HttpApiPublicLive.joinRoomData(apiJoinRoom.liveType, LiveConstants.ROOMID, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {

                        }
                    });
                }

                if (!TextUtils.isEmpty(cache.getAsString(LiveConstants.Cache_SmallMessage))) {
                    String json = cache.getAsString(LiveConstants.Cache_SmallMessage);
                    List<ApiSimpleMsgRoom> msgRooms = new Gson().fromJson(json, new TypeToken<List<ApiSimpleMsgRoom>>() {
                    }.getType());
                    mLiveChatAdapter.addDataList(msgRooms);
                    mChatRecyclerView.scrollToPosition(msgRooms.size() - 1);
                }else {
                    if (null != apiJoinRoom && !TextUtils.isEmpty(apiJoinRoom.noticeMsg))
                        insertMsg(13, apiJoinRoom.noticeMsg);
                    if (null != apiJoinRoom && !TextUtils.isEmpty(apiJoinRoom.notice)) {
                        insertMsg(13, apiJoinRoom.notice);
                    }
                }
            }

            @Override
            public void onTagMsg(String tag, AppJoinRoomVO apiJoinRoom) {

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
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_CloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (LiveConstants.isInsideRoomType == 0) {
                    clear();
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //一对一关闭
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                clear();

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_MsgContent, new MsgListener.SimpleMsgListener<String>() {
            @Override
            public void onMsg(String content) {
                insertMsg(13, content);
            }

            @Override
            public void onTagMsg(String tag, String s) {

            }
        });

        //房间pk改变尺寸
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_VoiceRoomPK, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(150));
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.setMargins(DpUtil.dp2px(10), 0, DpUtil.dp2px(130), DpUtil.dp2px(60));
                mChatRecyclerView.setLayoutParams(layoutParams);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //房间pk结束改变尺寸
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_VoiceRoomCancelPK, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(200));
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.setMargins(DpUtil.dp2px(10), 0, DpUtil.dp2px(130), DpUtil.dp2px(60));
                mChatRecyclerView.setLayoutParams(layoutParams);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //激情团战pk改变尺寸
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_VoicePkMacthTeam, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(150));
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.setMargins(DpUtil.dp2px(10), 0, DpUtil.dp2px(130), DpUtil.dp2px(60));
                mChatRecyclerView.setLayoutParams(layoutParams);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //激情团战pk结束改变尺寸
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_VoiceTeamCancelPK, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(200));
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.setMargins(DpUtil.dp2px(10), 0, DpUtil.dp2px(130), DpUtil.dp2px(60));
                mChatRecyclerView.setLayoutParams(layoutParams);
            }


            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    static class MyHandler extends Handler {
        WeakReference<LiveMessageComponent> mActivityReference;

        MyHandler(LiveMessageComponent liveMessageComponent) {
            mActivityReference = new WeakReference<LiveMessageComponent>(liveMessageComponent);
        }

        @Override
        public void handleMessage(Message msg) {

        }
    }

    /**
     * 显示弹幕的方法
     */
    public void showDanmu(ApiSimpleMsgRoom bean) {
        int lineNum = -1;
        for (int i = 0; i < mLines.length; i++) {
            if (mLines[i]) {
                mLines[i] = false;
                lineNum = i;
                break;
            }
        }
        if (lineNum == -1) {
            mDanMuQueue.offer(bean);
            return;
        }
        DanmuViewHolder danmuHolder = null;
        for (DanmuViewHolder holder : mList) {
            if (holder.isIdle()) {
                holder.setIdle(false);
                danmuHolder = holder;
                break;
            }
        }
        if (danmuHolder == null) {
            danmuHolder = new DanmuViewHolder(mContext, (ViewGroup) findViewById(R.id.fl_danmu));
            danmuHolder.setActionListener(this);
            mList.add(danmuHolder);
        }
        danmuHolder.show(bean, lineNum);
    }

    /**
     * 获取下一个弹幕
     */
    private void getNextDanmu() {
        ApiSimpleMsgRoom bean = mDanMuQueue.poll();
        if (bean != null) {
            showDanmu(bean);
        }
    }

    public void reset() {
        if (mLines != null) {
            for (boolean line : mLines) {
                line = true;
            }
        }
    }

    @Override
    public void onCanNext(int lineNum) {
        mLines[lineNum] = true;
        getNextDanmu();
    }

    @Override
    public void onAnimEnd(DanmuViewHolder vh) {
        if (mQueue.size() == 0) {
            if (vh != null) {
                vh.release();
                if (mList != null) {
                    mList.remove(vh);
                }
            }
        }
    }


    public void insertMsg(int type, String msg) {
        ApiSimpleMsgRoom apiSimpleMsgRoom = new ApiSimpleMsgRoom();
        apiSimpleMsgRoom.type = type;
        apiSimpleMsgRoom.content = msg;
        if (!TextUtils.isEmpty(msg)) {
            mLiveChatAdapter.insertItem(apiSimpleMsgRoom);
        }
    }

    @Override
    public void init(String groupName, SocketClient socketClient) {
        this.groupName = groupName;
        imApiLive = new IMApiLive();
        imApiLive.init(socketClient);
        imApiLiveMsg = new IMApiLiveMsg();
        imApiLiveMsg.init(socketClient);
        mSocketClient = socketClient;
        IMUtil.addReceiver(groupName, new IMRcvLiveMsgSend() {

            @Override
            public void onTimerExitRoom(ApiTimerExitRoom apiTimerExitRoom) {
                Log.i("aaa", "onUserSendMsgRoom");
            }

            @Override
            public void onRoomBan(long sessionID, String banInfo) {

            }

            @Override
            public void onUserBan(long sessionID, String banInfo) {

            }

            @Override
            public void onUserUpLiveTypeExitRoom(ApiExitRoom apiExitRoom) {

            }

            @Override
            public void onUserTimmerRoomRemind(int times) {

            }

            @Override
            public void onSimpleMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                if (apiSimpleMsgRoom.type == 14) {
                    showDanmu(apiSimpleMsgRoom);
                }
                if (!TextUtils.isEmpty(apiSimpleMsgRoom.content)) {
                    mLiveChatAdapter.insertItem(apiSimpleMsgRoom);
                }
                if (apiSimpleMsgRoom.type == 9) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_GuardNumber, null);
                }
                /*if (apiSimpleMsgRoom.type == 1&&LiveConstants.ANCHORID== HttpClient.getUid()){
                    imApiLiveMsg.sendMsgRoom("欢迎"+apiSimpleMsgRoom.uname+"进入直播间", 1, (int) LiveConstants.ANCHORID, new IMApiCallBack<ApiBaseEntity>() {
                        @Override
                        public void onImRet(int i, String s, ApiBaseEntity apiBaseEntity) {

                        }
                    });
                }*/

            }


            @Override
            public void onUserNoticMsg(String conetnt) {
                if (conetnt != null && !conetnt.equals("")) {
                    insertMsg(13, conetnt);
                }
            }

            @Override
            public void onUserSendMsgRoom(ApiSendMsgRoom ApiSimpleMsgRoom) {
                Log.i("aaa", "onUserSendMsgRoom");
            }

            @Override
            public void onUserSendApiJoinRoom(AppJoinRoomVO apiJoinRoom) {

            }


            @Override
            public void onSimpleMsgAll(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                Log.i("aaa", "onUserSendMsgRoom");
//                mLiveChatAdapter.insertItem(apiSimpleMsgRoom);
            }

            @Override
            public void onAppointUserSend(ApiSendMsgRoom ApiSimpleMsgRoom) {
                Log.i("aaa", "onUserSendMsgRoom");
            }


            @Override
            public void onOtherMsg(Object obj) {
                Log.i("aaa", "onUserSendMsgRoom");
            }
        });

        IMUtil.addReceiver(groupName, new IMRcvDynamiccircleSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserVideoCommentCount(ApiSendVideoUnReadNumber apiSendVideoUnReadNumber) {

            }
        });

        IMUtil.addReceiver(groupName, new IMRcvShortVideoSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserShortVideoCommentCount(ApiSendVideoUnReadNumber apiSendVideoUnReadNumber) {

            }
        });
        IMUtil.addReceiver(groupName, new IMRcvCommonMsgSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserGetNoReadAll(int count) {

            }
        });
        IMUtil.addReceiver(groupName, new IMRcvGradeRightMsgSender() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onElasticFrameFinshTask(ApiElasticFrame elasticFrame) {

            }

            @Override
            public void onElasticFrameUpgrade(ApiElasticFrame elasticFrame) {

            }

            @Override
            public void onElasticFrameMedal(ApiElasticFrame elasticFrame) {

            }
        });

    }

    private void clear() {
        if (!LiveConstants.isSamll) {
            cache.remove(LiveConstants.Cache_SmallMessage);
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }

        if (mGiftNumAnimator != null)
            mGiftNumAnimator.cancel();
        if (mList != null) {
            for (DanmuViewHolder vh : mList) {
                vh.release();
            }
            mList.clear();
        }
        if (mQueue != null) {
            mQueue.clear();
        }
        if (mDanMuQueue != null) {
            mDanMuQueue.clear();
        }
        if (mTipQueue != null) {
            mDanMuQueue.clear();
        }
        if (pauseDisposable != null)
            pauseDisposable.dispose();
//        IMUtil.removeReceiver(groupName);

        removeFromParent();
    }
}
