package com.kalacheng.message.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.buschatroom.model.CommonTipsDTO;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buscommon.socketmsg.IMRcvCommonMsgSend;
import com.kalacheng.busdynamiccircle.socketmsg.IMRcvDynamiccircleSend;
import com.kalacheng.busgraderight.socketmsg.IMRcvGradeRightMsgSender;
import com.kalacheng.buslivebas.socketmsg.IMRcvLiveMsgSend;
import com.kalacheng.busliveplugin.socketmsg.IMRcvLiveWishSend;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.component.LiveOutGiftComponent;
import com.kalacheng.commonview.dialog.LiveGiftDialogFragment;
import com.kalacheng.commonview.dialog.WishBillAddDialogFragment;
import com.kalacheng.commonview.jguangIm.ImMessageBean;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.commonview.view.TextRender;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.libuser.httpApi.HttpApiAnchorWishList;
import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.libuser.model.ApiExitRoom;
import com.kalacheng.libuser.model.ApiSendMsgRoom;
import com.kalacheng.libuser.model.ApiSendVideoUnReadNumber;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.libuser.model.ApiTimerExitRoom;
import com.kalacheng.libuser.model.ApiUsersLiveWish;
import com.kalacheng.libuser.model.AppCommonWords;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.NobLiveGift;
import com.kalacheng.libuser.model.OOOLiveTextChatData;
import com.kalacheng.message.R;
import com.kalacheng.message.adapter.ChatMsgListAdapter;
import com.kalacheng.message.adapter.CommonAdapter;
import com.kalacheng.message.dialog.ChatDialog;
import com.kalacheng.message.dialog.ChatImageDialog;
import com.kalacheng.message.util.MediaPlayerUtil;
import com.kalacheng.message.util.view.AudioRecordLayout;
import com.kalacheng.message.util.view.FaceLayout;
import com.kalacheng.message.util.view.MoreLayout;
import com.kalacheng.message.util.view.MyImageView;
import com.kalacheng.shortvideo.socketmsg.IMRcvShortVideoSend;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.KeyBoardHeightUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.klc.bean.SendGiftPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.GroupMemberInfo;
import cn.jpush.im.android.api.model.UserInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ?????? ?????? ??????
 */
@Route(path = ARouterPath.ChatRoomActivity)
public class ChatRoomActivity extends BaseActivity implements KeyBoardHeightUtil.KeyBoardHeightChangeListener,
        FaceLayout.OnLayoutClickListener, ChatMsgListAdapter.OnImageClick, AudioRecordLayout.OnAudioComplete,
        ImageResultCallback, LiveGiftDialogFragment.SendGiftSuccessCallBack, ChatMsgListAdapter.OnSendSuccess, View.OnClickListener {

    private ViewGroup root;
    private ImageView giftIv;
    private ImageView moreIv;
    private TextView followTv;

    private boolean showTip;
    private LinearLayout tipsLl;
    private TextView tipsTv;
    private TextView closeTv;

    private View userInd;
    private LinearLayout anchorLl;
    private RoundedImageView head1Iv;
    private RoundedImageView head2Iv;
    private RecyclerView commonRecycler;

    private TextView storyTv;
    private LinearLayout tabListLl;
    private TextView isVideoTv;
    private TextView newVideo;

    private LinearLayout guradLl;
    private RoundedImageView guradAvatarIv;

    private ImageView hotIv;

    private ImageView callVoiceIv;
    private ImageView callVideoIv;

    private ViewFlipper VoiceLive_marquee;

    private KeyBoardHeightUtil mKeyBoardHeightUtil;
    private InputMethodManager imm;
    private ChatDialog chatDialog;
    private ChatImageDialog mChatImageDialog;
    private int keyboardHeight;
    private boolean faceShow = false;
    private boolean moreShow = false;
    private boolean audioRecordShow = false;
    private EditText inputEt;
    private RecyclerView recyclerView;
    private ChatMsgListAdapter adapter;
    private CommonAdapter commonAdapter;
    private ProcessImageUtil imageUtil;
    private TextView titleNameTv;
    private Disposable disposable;

    private ProcessResultUtil processResultUtil;
    @Autowired(name = ARouterValueNameConfig.TO_UID)
    public String toUid;
    @Autowired(name = ARouterValueNameConfig.Name)
    public String name;
    @Autowired(name = ARouterValueNameConfig.IS_SINGLE)
    public boolean isSingle = true;
    //??????ID
    private long mGroupLeaderId;

    private ApiUserInfo userInfo;
    private ApiUserInfo mUserInfo;
    private int SendType = 7;

    private boolean isGiftShow = true;
    private LiveGiftDialogFragment giftDialogFragment;
    private WishBillAddDialogFragment wishBillAddDialogFragment;
    private LiveOutGiftComponent liveOutGiftComponent;
    private SocketClient mSocket;
    boolean isCall = false;
    private int hotNumber = 0;//?????????
    private long wishBillId = 0; //?????????ID


    public static void startActivity(String toUid, String name, boolean isSingle) {
        if (String.valueOf(HttpClient.getUid()).equals(toUid)) {
            ToastUtil.show("????????????????????????");
        } else {
            ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                    .withString(ARouterValueNameConfig.TO_UID, toUid)
                    .withString(ARouterValueNameConfig.Name, name)
                    .withBoolean(ARouterValueNameConfig.IS_SINGLE, isSingle)
                    .navigation();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        setStatusBarWhite(false);
        EventBus.getDefault().register(this);
        initSocket();
        imageUtil = new ProcessImageUtil(this);
        processResultUtil = new ProcessResultUtil(this);
        root = findViewById(R.id.root);

//        toUid = getIntent().getExtras().getString("uid");
//        name = getIntent().getExtras().getString("name");
//        isSingle = getIntent().getExtras().getBoolean("isSingle");
        if (!isSingle) {
            addRoom();
//            LiveConstants.ROOMID = Long.parseLong(toUid);
        } else {
//            LiveConstants.ROOMID = -1;
        }

        mSocket = IMUtil.getClient();
        liveOutGiftComponent = new LiveOutGiftComponent(mContext, root);
        liveOutGiftComponent.init(getLocalClassName(), mSocket);

        ImMessageUtil.getInstance().setSingle(isSingle);
        mUserInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);

        tipsLl = findViewById(R.id.tipsLl);
        tipsTv = findViewById(R.id.tipsTv);
        closeTv = findViewById(R.id.closeTv);
        moreIv = findViewById(R.id.moreIv);
        followTv = findViewById(R.id.followTv);
        head1Iv = findViewById(R.id.head1Iv);
        head2Iv = findViewById(R.id.head2Iv);
        userInd = findViewById(R.id.userDataIld);
        anchorLl = findViewById(R.id.anchorLl);
        storyTv = findViewById(R.id.storyTv);
        tabListLl = findViewById(R.id.tabListLl);
        isVideoTv = findViewById(R.id.isVideoTv);
        newVideo = findViewById(R.id.newVideoTv);
        guradLl = findViewById(R.id.guradLl);
        guradAvatarIv = findViewById(R.id.guradAvatarIv);
        hotIv = findViewById(R.id.hotIv);
        titleNameTv = findViewById(R.id.titleNameTv);
        callVoiceIv = findViewById(R.id.callVoiceIv);
        callVideoIv = findViewById(R.id.callVideoIv);
        inputEt = findViewById(R.id.inputEt);
        giftIv = findViewById(R.id.giftIv);
        VoiceLive_marquee = findViewById(R.id.VoiceLive_marquee);

        findViewById(R.id.backIv).setOnClickListener(this);
        findViewById(R.id.faceIv).setOnClickListener(this);
        findViewById(R.id.pictureIv).setOnClickListener(this);
        findViewById(R.id.audioRecordIv).setOnClickListener(this);

        closeTv.setOnClickListener(this);
        moreIv.setOnClickListener(this);
        followTv.setOnClickListener(this);
        hotIv.setOnClickListener(this);
        callVoiceIv.setOnClickListener(this);
        callVideoIv.setOnClickListener(this);
        inputEt.setOnClickListener(this);
        giftIv.setOnClickListener(this);

        ImageLoader.display(mUserInfo.avatar, head1Iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        titleNameTv.setText(name);

        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new ChatMsgListAdapter(this);
        adapter.setOnImageClick(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnSendSuccess(new ChatMsgListAdapter.OnSendSuccess() {
            @Override
            public void onSuccess(String content, int type) {
                sendMessage(content, type);
            }
        });

        commonRecycler = findViewById(R.id.commonRecycler);
        commonRecycler.setHasFixedSize(true);
        commonRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        commonAdapter = new CommonAdapter(this);
        commonRecycler.setAdapter(commonAdapter);

        inputEt = findViewById(R.id.inputEt);
        inputEt.requestFocus();
        inputEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendBtn();
                    return true;
                }
                return false;
            }
        });

        mKeyBoardHeightUtil = new KeyBoardHeightUtil(this, findViewById(android.R.id.content), this);
        root.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mKeyBoardHeightUtil != null && !ChatRoomActivity.this.isFinishing()) {
                    mKeyBoardHeightUtil.start();
                }
            }
        }, 500);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (keyboardHeight > 0) {
                        if (chatDialog != null && chatDialog.isShowing()) {
                            chatDialog.dismiss();
                            chatDialog = null;
                            faceShow = false;
                            moreShow = false;
                            audioRecordShow = false;
                            onKeyBoardChanged(0);
                        } else {
                            hideSoftInput();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        getMessageData();
        isGroup();
        getCommonData();
        getOooSendMsgText();
    }

    @Override
    public void onClick(View v) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (v.getId() == R.id.backIv) {
            finish();
        } else if (v.getId() == R.id.closeTv) {
            tipsLl.setVisibility(View.GONE);
        } else if (v.getId() == R.id.moreIv) {
            startActivity(new Intent(getBaseContext(), ChatSettingActivity.class).putExtra("isSingle", isSingle).putExtra("uid", toUid));
        } else if (v.getId() == R.id.followTv) {
            setAtten();
        } else if (v.getId() == R.id.hotIv) {
            ToastUtil.show("????????????" + hotNumber);
        } else if (v.getId() == R.id.callVoiceIv) {
            call(0);
        } else if (v.getId() == R.id.callVideoIv) {
            call(1);
        } else if (v.getId() == R.id.inputEt) {
            hideDialogAndShowInput();
        } else if (v.getId() == R.id.faceIv) {
            if (!faceShow) {
                showDialog(initFaceLayout());
            } else {
                hideDialogAndShowInput();
            }
        } else if (v.getId() == R.id.pictureIv) {
            if (!moreShow) {
                showDialog(initMoreLayout());
            } else {
                hideDialogAndShowInput();
            }
        } else if (v.getId() == R.id.audioRecordIv) {
            if (!audioRecordShow) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    processResultUtil.requestPermissions(
                            new String[]{Manifest.permission.RECORD_AUDIO}, new Runnable() {
                                @Override
                                public void run() {
                                    showDialog(initAudioRecordLayout());
                                }
                            }
                    );
                }
            } else {
                hideDialogAndShowInput();
            }
        } else if (v.getId() == R.id.giftIv) {
            if (isGiftShow) {
                initGiftDialog();
            } else {
                initWishBillDialog();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCall) {
            getMessageData();
            isCall = false;
        }
    }


    /**
     * ??????????????????
     */
    private void getUserData() {
        if (isSingle) {
            JMessageClient.getUserInfo(toUid, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                }
            });
        } else {
            JMessageClient.getGroupInfo(Long.parseLong(toUid), new GetGroupInfoCallback() {
                @Override
                public void gotResult(int i, String s, GroupInfo groupInfo) {
                }
            });
        }

        HttpApiAppUser.getUserinfo(HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && retModel != null) {
                    mUserInfo = retModel;
                    ImageLoader.display(mUserInfo.avatar, head1Iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    isMe = true;
                    roleAssess();
                }
            }
        });

        HttpApiAppUser.getUserinfo(Long.parseLong(toUid), new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && retModel != null) {
                    int onlineIcon = retModel.onlineStatus == 0 ? R.drawable.bg_delete_btn : R.drawable.bg_status_green;
                    titleNameTv.setCompoundDrawablesWithIntrinsicBounds(onlineIcon, 0, 0, 0);
                    userInfo = retModel;
                    ImageLoader.display(userInfo.avatar, head2Iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    head2Iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (CheckDoubleClick.isFastDoubleClick()) {
                                return;
                            }
                            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, userInfo.userId).navigation();
                        }
                    });
                    if (userInfo.followStatus == 0) {
                        followTv.setText("??????");
                        followTv.setBackgroundResource(R.drawable.bg_follow);
                    } else {
                        followTv.setText("?????????");
                        followTv.setBackgroundResource(R.drawable.bg_follow_1);
                    }
                    isOther = true;
                    roleAssess();
                }
            }
        });
    }

    /**
     * ??????????????????
     */
    private void isGroup() {
        if (isSingle) {
            getUserData();
        } else {
            userInd.setVisibility(View.GONE);
            hotIv.setVisibility(View.GONE);
            callVideoIv.setVisibility(View.GONE);
            callVoiceIv.setVisibility(View.GONE);
            followTv.setVisibility(View.GONE);
            JMessageClient.getGroupInfo(Long.parseLong(toUid), new GetGroupInfoCallback() {
                @Override
                public void gotResult(int i, String s, GroupInfo groupInfo) {
                    if (i == 0) {
                        if (groupInfo.getGroupMemberInfos() != null && groupInfo.getGroupMemberInfos().size() > 0) {
                            titleNameTv.setText(name + "(" + String.valueOf(groupInfo.getGroupMemberInfos().size()) + ")");
                        } else {
                            titleNameTv.setText(name);
                        }

                        GroupMemberInfo info = groupInfo.getOwnerMemberInfo();
                        String uid = info.getUserInfo().getUserName();
                        mGroupLeaderId = Long.parseLong(uid);
                        getWishList(mGroupLeaderId);
                        if (Long.parseLong(uid) == HttpClient.getUid()) {
                            giftIv.setImageResource(R.mipmap.xinyuandan);
                            isGiftShow = false;
                        }
                    }
                }
            });
        }
    }

    /**
     * ????????????
     */
    private boolean isMe;
    private boolean isOther;

    private void roleAssess() {
        if (!ConfigUtil.getBoolValue(R.bool.containOne2One)) {
            callVideoIv.setVisibility(View.GONE);
            callVoiceIv.setVisibility(View.GONE);
        }
        if (isMe && isOther) {
            // ??????????????????
            if (mUserInfo.role == 1 && userInfo.role == 1) {
                if (ConfigUtil.getBoolValue(R.bool.containOne2One)) {
                    int config = (int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_A_TO_A, 0);
                    callVideoIv.setVisibility(config == 1 ? View.GONE : View.VISIBLE);
                    callVoiceIv.setVisibility(config == 1 ? View.GONE : View.VISIBLE);
                }
                // ??????????????????
            } else if (mUserInfo.role == 0 && userInfo.role == 0) {
                if (ConfigUtil.getBoolValue(R.bool.containOne2One)) {
                    int config = (int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_U_TO_U, 0);
                    callVideoIv.setVisibility(config == 1 ? View.GONE : View.VISIBLE);
                    callVoiceIv.setVisibility(config == 1 ? View.GONE : View.VISIBLE);
                }
                // ???????????? ????????????
            } else {
                // ?????? 0:???????????? 1:??????    ?????????????????? ?????????????????????????????????   ????????????????????? ?????????????????????????????????
                wishBillId = userInfo.userId;
                if (mUserInfo.role == 1) {
                    getWishList(HttpClient.getUid());
                    giftIv.setImageResource(R.mipmap.xinyuandan);
                    isGiftShow = false;
                    showGuard(mUserInfo.guardMyList);
                } else {
                    getWishList(userInfo.userId);
                    showGuard(userInfo.guardMyList);

                    int isNobleChat = (int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIF_NOBLE_CHAT_FREE, 0);
                    if (mUserInfo.vipType == 1 && isNobleChat == 0) {
                        tipsLl.setVisibility(View.GONE);
                    } else {
                        if (userInfo.adminLiveConfig != null) {
                            if (userInfo.adminLiveConfig.privateCoin == 0) {
                                tipsLl.setVisibility(View.GONE);
                            } else {
                                showTip = true;
                                if (!TextUtils.isEmpty(tipsTv.getText().toString())) {
                                    tipsLl.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
                if (ConfigUtil.getBoolValue(R.bool.containOne2One)) {
                    callVideoIv.setVisibility(View.VISIBLE);
                    callVoiceIv.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    /**
     * ????????????
     */
    private void showGuard(List<GuardUserDto> list) {
        if (list != null && list.size() > 0) {
            guradLl.setVisibility(View.VISIBLE);
            ImageLoader.display(list.get(0).userHeadImg, guradAvatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        } else {
            guradLl.setVisibility(View.GONE);
        }
    }

    /**
     * ??????????????????
     */
    private void getMessageData() {
        List<ImMessageBean> list = ImMessageUtil.getInstance().getChatMessageList(toUid);
        adapter.setData(list);
    }

    /**
     * ?????????
     */
    private void getCommonData() {
        HttpApiChatRoom.getCommonWordsList(new HttpApiCallBack<CommonTipsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, CommonTipsDTO retModel) {
                if (code == 1 && retModel != null) {
                    List<AppCommonWords> listWords = new ArrayList<>();
                    if (retModel.commonWordsList != null) {
                        listWords.addAll(retModel.commonWordsList);
                    }
                    Collections.reverse(listWords);
                    commonAdapter.setList(listWords);

                    tipsTv.setText(retModel.privateChatDeductionTips);
                    if (showTip && !TextUtils.isEmpty(retModel.privateChatDeductionTips)) {
                        tipsLl.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    /**
     * ????????????????????????
     */
    private void getOooSendMsgText() {
        HttpApiChatRoom.oooSendMsgText(Long.parseLong(toUid), new HttpApiCallBack<OOOLiveTextChatData>() {
            @Override
            public void onHttpRet(int code, String msg, OOOLiveTextChatData retModel) {
                if (code == 1 && retModel != null) {
                    if (retModel.chatData != null) {
                        storyTv.setText("??????" + retModel.chatData.knowDay + "???  ??????" + retModel.chatData.chatNumber + "???");
                        hotNumber = retModel.chatData.hotNumber;
                        int num = retModel.chatData.hotNumber / 20;
                        hotIv.setImageResource(hot(num));
                    } else {
                        storyTv.setText("?????????????????????");
                    }
                    isVideoTv.setText(retModel.isVideo == 0 ? "TA??????: ?????????" : "TA??????: ?????????");
                    newVideo.setVisibility(retModel.isVideo == 0 ? View.GONE : View.VISIBLE);
                    addTabs(retModel.tabList);
                }
            }
        });
    }

    /**
     * ???????????????
     */
    private void addRoom() {
        HttpApiAppUser.groupJoinRoom(Long.parseLong(toUid), new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {

            }
        });
    }

    /**
     * ???????????????
     */
    private void getWishList(long uid) {
        HttpApiAnchorWishList.getWishList(uid, new HttpApiCallBackArr<ApiUsersLiveWish>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLiveWish> retModel) {
                if (code == 1) {
                    getMarquee(retModel);
                }
            }
        });
    }

    //????????????????????????
    public void getMarquee(List<ApiUsersLiveWish> awList) {
        VoiceLive_marquee.removeAllViews();
        if (null != awList && awList.size() > 0) {
            VoiceLive_marquee.setVisibility(View.VISIBLE);
            for (int i = 0; i < awList.size(); i++) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.will_roll_item, null);
                ((TextView) view.findViewById(R.id.gift_name)).setText(awList.get(i).giftname + " " + awList.get(i).sendNum + "/" + awList.get(i).num);
                RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.gift_image);
                ImageLoader.display(awList.get(i).gifticon, imageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                VoiceLive_marquee.addView(view);
            }
        } else {
            VoiceLive_marquee.setVisibility(View.GONE);
        }
    }

    /**
     * ??????????????????
     */
    private void addTabs(String tabs) {
        tabListLl.removeAllViews();
        if (!TextUtils.isEmpty(tabs)) {
            String[] arrayTabs = tabs.split(",");
            for (String tab : arrayTabs) {
                TextView tabView = (TextView) LayoutInflater.from(getBaseContext()).inflate(R.layout.layout_tab, tabListLl, false);
                tabView.setText(tab);
                tabListLl.addView(tabView);
            }
        }
    }

    /**
     * ????????????
     */
    private int hot(int i) {
        switch (i) {
            case 0:
                return R.mipmap.xiaoxi_haogandu0;
            case 1:
                return R.mipmap.xiaoxi_haogandu1;
            case 2:
                return R.mipmap.xiaoxi_haogandu2;
            case 3:
                return R.mipmap.xiaoxi_haogandu3;
            case 4:
                return R.mipmap.xiaoxi_haogandu4;
            case 5:
                return R.mipmap.xiaoxi_haogandu5;
            case 6:
                return R.mipmap.xiaoxi_haogandu6;
        }
        if (i > 6) {
            return R.mipmap.xiaoxi_haogandu6;
        }
        return R.mipmap.xiaoxi_haogandu0;
    }

    /**
     * ???????????????
     */
    private void setAtten() {
        if (userInfo != null) {
            HttpApiAppUser.set_atten(userInfo.followStatus == 1 ? 0 : 1, Long.parseLong(toUid), new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        userInfo.followStatus = userInfo.followStatus == 1 ? 0 : 1;
                        if (userInfo.followStatus == 0) {
                            followTv.setText("??????");
                            followTv.setBackgroundResource(R.drawable.bg_follow);
                        } else {
                            followTv.setText("?????????");
                            followTv.setBackgroundResource(R.drawable.bg_follow_1);
                        }
                    }
                    ToastUtil.show(msg);
                }
            });
        }
    }

    /**
     * ?????????????????????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImMessageBean(ImMessageBean bean) {
        String uid;
        if (bean.getRawMessage().getTargetType() == ConversationType.single) {
            uid = bean.getRawMessage().getTargetID();
        } else {
            uid = String.valueOf(((GroupInfo) bean.getRawMessage().getTargetInfo()).getGroupID());
        }

        if (uid.equals(toUid)) {
            if (adapter != null) {
                adapter.insertItem(bean);
                if (ImMessageUtil.getInstance().markAllMessagesAsRead(toUid)) {
                    ImMessageUtil.getInstance().refreshConversationLastMsg(bean.getRawMessage());
                }
            }
            getOooSendMsgText();
        }
    }

    /**
     * ??????toBottom?????????
     */
    private void onKeyBoardChanged(int keyboardHeight) {
        if (root != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) root.getLayoutParams();
            params.setMargins(0, 0, 0, keyboardHeight);
            root.setLayoutParams(params);
            this.keyboardHeight = keyboardHeight;
            if (adapter != null) {
                adapter.scrollToBottom();
            }
        }
    }

    /**
     * ???????????????
     */
    private void showSoftInput() {
        if (!((KeyBoardHeightUtil.KeyBoardHeightChangeListener) mContext).isSoftInputShowed() && imm != null && inputEt != null) {
            imm.showSoftInput(inputEt, InputMethodManager.SHOW_FORCED);
            inputEt.requestFocus();
        }
    }

    /**
     * ????????????
     */
    private boolean hideSoftInput() {
        if (((KeyBoardHeightUtil.KeyBoardHeightChangeListener) mContext).isSoftInputShowed() && imm != null && inputEt != null) {
            imm.hideSoftInputFromWindow(inputEt.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    /**
     * ???????????? ??? ????????????
     */
    private void hideDialogAndShowInput() {
        if (chatDialog != null && chatDialog.isShowing()) {
            chatDialog.dismiss();
            chatDialog = null;
            faceShow = false;
            moreShow = false;
            audioRecordShow = false;
        }
        showSoftInput();
    }

    /**
     * ?????????????????????
     */
    private void hideInputAndDialog() {
        if (keyboardHeight > 0) {
            if (chatDialog != null && chatDialog.isShowing()) {
                chatDialog.dismiss();
                chatDialog = null;
                faceShow = false;
                moreShow = false;
                audioRecordShow = false;
                onKeyBoardChanged(0);
            } else {
                hideSoftInput();
            }
        }
    }

    @Override
    public void onKeyBoardHeightChanged(int visibleHeight, int keyboardHeight) {
        if (keyboardHeight == 0 && chatDialog != null) return;
        onKeyBoardChanged(keyboardHeight);
    }

    @Override
    public boolean isSoftInputShowed() {
        if (mKeyBoardHeightUtil != null) {
            return mKeyBoardHeightUtil.isSoftInputShowed();
        }
        return false;
    }

    /**
     * ????????????
     */
    private void showDialog(View view) {
        if (keyboardHeight > 0) {
            if (chatDialog != null && chatDialog.isShowing()) {
                View view1 = chatDialog.getmContentView();
                if (view1 instanceof FaceLayout) {
                    faceShow = false;
                } else if (view1 instanceof MoreLayout) {
                    moreShow = false;
                } else if (view1 instanceof AudioRecordLayout) {
                    audioRecordShow = false;
                }
                chatDialog.dismiss();
                chatDialog = null;
            } else {
                hideSoftInput();
            }
        }
        initDialog(view);

    }

    /**
     * ???????????????
     */
    private void initDialog(final View view) {
        final ChatDialog dialog = new ChatDialog(findViewById(R.id.container), view, true, new ChatDialog.ActionListener() {
            @Override
            public void onDialogDismiss() {
                if (view instanceof AudioRecordLayout) {
                    ((AudioRecordLayout) view).release();
                }
            }
        });
        L.e("toBottom??????22" + dialog.getViewHeight());
        onKeyBoardChanged(dialog.getViewHeight());
        dialog.show();
        chatDialog = dialog;
    }

    /**
     * ??????????????????
     */
    private FaceLayout initFaceLayout() {
        FaceLayout faceLayout = new FaceLayout(this);
        faceLayout.setListener(this);
        faceLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        faceLayout.measure(0, 0);
        faceShow = true;
        return faceLayout;
    }

    /**
     * ???????????????
     */
    @Override
    public void onFaceSelectClick(String str, int res) {
        L.e(str);
        if (inputEt != null) {
            Editable editable = inputEt.getText();
            editable.insert(inputEt.getSelectionStart(), TextRender.getFaceImageSpan(str, res));
        }
    }

    /**
     * ????????????
     */
    @Override
    public void onFaceDeleteClick() {
        if (inputEt != null) {
            int selection = inputEt.getSelectionStart();
            String text = inputEt.getText().toString();
            if (selection > 0) {
                String text2 = text.substring(selection - 1, selection);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[", selection);
                    if (start >= 0) {
                        inputEt.getText().delete(start, selection);
                    } else {
                        inputEt.getText().delete(selection - 1, selection);
                    }
                } else {
                    inputEt.getText().delete(selection - 1, selection);
                }
            }
        }
    }

    /**
     * ????????????????????????????????????
     */
    @Override
    public void sendBtn() {
        final String content = inputEt.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            HttpApiChatRoom.keywordTransform(content, new HttpApiCallBack<SingleString>() {
                @Override
                public void onHttpRet(int code, String msg, SingleString retModel) {
                    if (code == 1) {
                        privateChat(ImMessageBean.TYPE_TEXT, retModel.value, null);
                        inputEt.setText("");
                    } else {
                        ToastUtil.show("????????????");
                    }
                }
            });
        } else {
            ToastUtil.show("???????????????");
        }
    }

    /**
     * ??????????????????
     */
    @Override
    public void onSuccess(String content, int type) {
        sendMessage(content, type);
    }

    /**
     * ???????????????????????????
     */
    private MoreLayout initMoreLayout() {
        MoreLayout moreLayout = new MoreLayout(this);
        moreLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        moreLayout.measure(0, 0);
        moreShow = true;
        return moreLayout;
    }

    @Override
    public void beforeCamera() {
        L.e("?????????????");
    }


    /**
     * ????????????????????????????????????
     */
    @Override
    public void onSuccess(final File file) {
        L.e(file.getAbsolutePath());
        disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext(HuaWeiYun.getInstance().upload(file, HuaWeiYun.IMAGE_PATH));
                UploadUtil.getInstance().uploadPicture(1, file, new PictureUploadCallback() {
                    @Override
                    public void onSuccess(String imgStr) {
                        if (emitter != null) {
                            emitter.onNext(imgStr);
                        }
                    }

                    @Override
                    public void onFailure() {
                        if (emitter != null) {
                            emitter.onNext(null);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull final String url) throws Exception {
                if (!TextUtils.isEmpty(url)) {
                    privateChat(ImMessageBean.TYPE_IMAGE, url, null);
                } else {
                    ToastUtil.show("??????????????????");
                }
            }
        });
    }

    @Override
    public void onFailure() {
        L.e("????????????");
    }

    /**
     * ????????????
     */
    @Override
    public void onImageClick(MyImageView imageView, int x, int y) {
        if (adapter == null || imageView == null) {
            return;
        }
        hideInputAndDialog();
        int msgId = imageView.getMsgId();
        String url = imageView.getUrl();
        mChatImageDialog = new ChatImageDialog(mContext, findViewById(R.id.container));
        mChatImageDialog.show(adapter.getChatImageBean(msgId), url, x, y, imageView.getWidth(), imageView.getHeight(), imageView.getDrawable());
    }


    /**
     * ?????????????????????
     */
    private AudioRecordLayout initAudioRecordLayout() {
        AudioRecordLayout audioRecordLayout = new AudioRecordLayout(this);
        audioRecordLayout.setOnAudioComplete(this);
        audioRecordLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        audioRecordLayout.measure(0, 0);
        audioRecordShow = true;
        return audioRecordLayout;
    }

    /**
     * ??????????????????
     */
    @Override
    public void onAudioComlete(final File audioFile, final long duration) {

        disposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext(HuaWeiYun.getInstance().upload(audioFile, HuaWeiYun.IMAGE_PATH));
                UploadUtil.getInstance().uploadPicture(1, audioFile, new PictureUploadCallback() {
                    @Override
                    public void onSuccess(String imgStr) {
                        emitter.onNext(imgStr);
                    }

                    @Override
                    public void onFailure() {
                        emitter.onNext(null);
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull final String url) throws Exception {
                if (!TextUtils.isEmpty(url)) {
                    L.e("audioUrl = " + url);
                    privateChat(ImMessageBean.TYPE_VOICE, url, String.valueOf(duration));
                } else {
                    ToastUtil.show("????????????");
                }
            }
        });
    }

    /**
     * ??????????????????
     */
    @Override
    public void onVoiceClick(MyImageView imageView) {
        L.e("??????????????????");
        MediaPlayerUtil.getMediaPlayer().player(imageView.getUrl(), imageView);
    }

    private void setDataToGift(ArrayList list) {
        giftDialogFragment = new LiveGiftDialogFragment();
        giftDialogFragment.setSendGiftSuccessCallBack(this);
        Bundle bundle = new Bundle();
//        bundle.putInt(ARouterValueNameConfig.Livetype, SendType);
//        bundle.putString(ARouterValueNameConfig.ShowID, "-1");
//        bundle.putLong(ARouterValueNameConfig.ShortVideoId, -1);
//        bundle.putLong(ARouterValueNameConfig.RoomID, !isSingle ? Long.parseLong(toUid) : -1l);
        bundle.putParcelableArrayList("SendList", (ArrayList<? extends Parcelable>) list);
        bundle.putBoolean("hideRoleTip", true);
//        bundle.putLong(ARouterValueNameConfig.LiveRoomAnchorID, -1);

        giftDialogFragment.setArguments(bundle);
        giftDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "LiveGiftDialogFragment");

    }

    // ???????????????
    private void sendMessage(String content, int type){
        HttpApiChatRoom.oooSendMsg(content, HttpClient.getUid(), Long.parseLong(toUid), type, new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                getOooSendMsgText();
            }
        });
    }

    /**
     * ????????????
     */
    private void initGiftDialog() {
        final ArrayList list = new ArrayList();
        if (isSingle) {
            SendGiftPeopleBean bean = new SendGiftPeopleBean();
            bean.uid = Long.parseLong(toUid);
            bean.name = name;
            if (userInfo != null) {
                bean.headimage = userInfo.avatar;
            }
            bean.shortVideoId = -1;
            bean.liveType = SendType;
            bean.roomID = !isSingle ? Long.parseLong(toUid) : -1l;
            bean.showid = "-1";
            bean.anchorID = -1;
            list.add(bean);
            setDataToGift(list);
        } else {
            JMessageClient.getGroupInfo(Long.parseLong(toUid), new GetGroupInfoCallback() {
                @Override
                public void gotResult(int i, String s, GroupInfo groupInfo) {
                    if (i == 0 && groupInfo != null && groupInfo.getGroupMemberInfos().size() > 0) {
                        for (GroupMemberInfo info : groupInfo.getGroupMemberInfos()) {
                            if (Long.parseLong(info.getUserInfo().getUserName()) != HttpClient.getUid()) {
                                SendGiftPeopleBean bean = new SendGiftPeopleBean();
                                bean.uid = Long.parseLong(info.getUserInfo().getUserName());
                                bean.name = info.getUserInfo().getNickname();
                                bean.headimage = info.getUserInfo().getExtra("avatarUrlStr");
                                bean.shortVideoId = -1;
                                bean.liveType = SendType;
                                bean.roomID = !isSingle ? Long.parseLong(toUid) : -1l;
                                bean.showid = "-1";
                                bean.anchorID = -1;
                                list.add(bean);
                            }
                        }
                        setDataToGift(list);
                    }
                }
            });
        }
    }

    /**
     * ????????????
     */
    public void privateChat(final int type, final String str, final String time) {
        if (isSingle) {
            HttpApiChatRoom.privateChat(Long.parseLong(toUid), new HttpApiCallBack<SingleString>() {
                @Override
                public void onHttpRet(int code, String msg, SingleString retModel) {
                    if (code == 1) {
                        ImMessageBean message = createMsg(type, str, time);
                        if (message == null) {
                            ToastUtil.show("???????????? msg = null");
                            return;
                        }
                        adapter.insertSelfItem(message);
                    } else if (code == 2) {
                        initDialog("????????????", "?????????", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tipsDialog != null) {
                                    tipsDialog.dismiss();
                                }
                                ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
                            }
                        });
                    } else if (code == 3) {
                        initDialog("?????????????????????????????????", "????????????", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tipsDialog != null) {
                                    tipsDialog.dismiss();
                                }
                                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                                        + HttpConstants.URL_NOBLE + "_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
                            }
                        });
                    } else if (code == 4) {
                        ToastUtil.show(msg);
                    } else {
                        ToastUtil.show("????????????:" + code);
                    }
                }
            });
        } else {
            ImMessageBean message = createMsg(type, str, time);
            if (message == null) {
                ToastUtil.show("???????????? msg = null");
                return;
            }
            adapter.insertSelfItem(message);
        }
        getOooSendMsgText();
    }

    /**
     * ????????????
     */
    private ImMessageBean createMsg(int type, final String str, final String time) {
        if (type == ImMessageBean.TYPE_TEXT) {
            return ImMessageUtil.getInstance().createTextMessage(toUid, str);
        } else if (type == ImMessageBean.TYPE_IMAGE) {
            return ImMessageUtil.getInstance().createCustomMessage(toUid, new HashMap<String, String>() {{
                put(ImMessageBean.MESSAGE_TYPE, "0");
                put(ImMessageBean.PIC_URL_STR, str);
            }}, ImMessageBean.TYPE_IMAGE);
        } else if (type == ImMessageBean.TYPE_VOICE) {
            return ImMessageUtil.getInstance().createCustomMessage(toUid, new HashMap<String, String>() {{
                put(ImMessageBean.MESSAGE_TYPE, "2");
                put(ImMessageBean.RECORD_URL, str);
                put(ImMessageBean.TIME, time);
            }}, ImMessageBean.TYPE_VOICE);
        } else {
            return null;
        }
    }

    /**
     * ????????????
     */
    Dialog tipsDialog;

    private void initDialog(String content, String ok, View.OnClickListener listener) {
        if (tipsDialog == null) {
//            tipsDialog = DialogUtil.getBaseDialog(this, R.style.dialog, R.layout.verification_tips_dialog, true, true);
            tipsDialog = new Dialog(this, R.style.dialog);
            tipsDialog.setContentView(R.layout.verification_tips_dialog);
            tipsDialog.setCancelable(true);
            tipsDialog.setCanceledOnTouchOutside(true);
            Window window = tipsDialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }

        TextView title = ((TextView) tipsDialog.findViewById(R.id.title));
        TextView title2 = ((TextView) tipsDialog.findViewById(R.id.title2));
        title2.setVisibility(View.GONE);
        ImageView close = tipsDialog.findViewById(R.id.iv_close);
        close.setVisibility(View.GONE);

        TextView tv_sure = tipsDialog.findViewById(R.id.tv_sure);

        title.setText(content);
        tv_sure.setText(ok);

        tv_sure.setOnClickListener(listener);
        tipsDialog.show();
    }

    /**
     * ??????????????????
     */
    private void initWishBillDialog() {
        wishBillAddDialogFragment = new WishBillAddDialogFragment();
        Bundle bundle = new Bundle();
        if (isSingle) {
            bundle.putLong("RoomID", -1L);
        } else {
            bundle.putLong("RoomID", Long.parseLong(toUid));
        }
        bundle.putLong("UserID", wishBillId);
        wishBillAddDialogFragment.setArguments(bundle);
        wishBillAddDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "WishBillAddDialogFragment");
    }

    /**
     * ??????????????????
     */
    @Override
    public void onSuccess(final NobLiveGift nobLiveGift, final int giftNum, final SendGiftPeopleBean bean) {
        L.e("??????????????????  gift =" + nobLiveGift.gifticon + " fromIcon = " + mUserInfo.avatar);
        ImMessageBean messageBean = ImMessageUtil.getInstance().createCustomMessage(toUid, new HashMap<String, String>() {{
            put(ImMessageBean.MESSAGE_TYPE, "1");
            put(ImMessageBean.GIFT_ICON, nobLiveGift.gifticon);
            put(ImMessageBean.GIFT_COUNT, String.valueOf(giftNum));
            put(ImMessageBean.OWN_ICON, mUserInfo.avatar);
            put(ImMessageBean.OTHER_ICON, bean.headimage);
            put(ImMessageBean.OWN_UID, mUserInfo.userId + "");
            put(ImMessageBean.OTHER_UID, bean.uid + "");
        }}, ImMessageBean.TYPE_GIFT);
        adapter.insertSelfItem(messageBean);
        //sendMessage(content, type);
        //giftDialogFragment.dismiss();
    }

    /**
     * ???????????????????????????
     */
    private void call(final int isVideo) {
        if (userInfo != null) {
            final ApiUserInfo info = new ApiUserInfo();
            info.userId = userInfo.userId;
            LiveConstants.mIsOOOSend = true;
            info.avatar = userInfo.avatar;
            info.sex = userInfo.sex;
            info.username = userInfo.username;
            info.role = userInfo.role;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                processResultUtil.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, new Runnable() {
                    @Override
                    public void run() {
                        LookRoomUtlis.getInstance().showDialog(isVideo, info, processResultUtil, mContext, 1);
//                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, isVideo, info, 1);
                        isCall = true;
                    }
                });
            }
        }
    }


    /**
     * socket
     */
    private void initSocket() {
        IMUtil.addReceiver(getClass().getName(), new IMRcvLiveMsgSend() {

            @Override
            public void onSimpleMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onUserSendMsgRoom(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onSimpleMsgAll(ApiSimpleMsgRoom apiSimpleMsgRoom) {

            }

            @Override
            public void onAppointUserSend(ApiSendMsgRoom apiSendMsgRoom) {

            }

            @Override
            public void onUserNoticMsg(String conetnt) {

            }

            @Override
            public void onUserTimmerRoomRemind(int times) {

            }

            @Override
            public void onTimerExitRoom(ApiTimerExitRoom apiTimerExitRoom) {

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
            public void onUserSendApiJoinRoom(AppJoinRoomVO apiJoinRoom) {

            }

            @Override
            public void onOtherMsg(Object o) {

            }
        });
        IMUtil.addReceiver(getClass().getName(), new IMRcvDynamiccircleSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserVideoCommentCount(ApiSendVideoUnReadNumber apiSendVideoUnReadNumber) {

            }
        });
        IMUtil.addReceiver(getClass().getName(), new IMRcvShortVideoSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserShortVideoCommentCount(ApiSendVideoUnReadNumber apiSendVideoUnReadNumber) {

            }
        });

        IMUtil.addReceiver(getClass().getName(), new IMRcvCommonMsgSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserGetNoReadAll(int count) {

            }
        });
        IMUtil.addReceiver(getClass().getName(), new IMRcvGradeRightMsgSender() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onElasticFrameUpgrade(ApiElasticFrame elasticFrame) {

            }

            @Override
            public void onElasticFrameMedal(ApiElasticFrame elasticFrame) {

            }

            @Override
            public void onElasticFrameFinshTask(ApiElasticFrame elasticFrame) {

            }
        });

        IMUtil.addReceiver(getClass().getName(), new IMRcvLiveWishSend() {
            @Override
            public void onOtherMsg(Object o) {

            }

            @Override
            public void onUserAddWishMsgUser(List<ApiUsersLiveWish> list) {

            }

            @Override
            public void onUserAddWishMsg(List<ApiUsersLiveWish> list) {

            }

            /**
             * ??????????????????????????????
             * */
            @Override
            public void onSendWishUser(List<ApiUsersLiveWish> awList) {
                L.e("???????????????:?????? " + awList.size());
                // ??????????????? ????????????????????? ???????????????????????????????????? ????????????????????????
                if (isSingle) {
                    if (null != awList && awList.size() > 0 && awList.get(0).uid != Long.parseLong(toUid) && awList.get(0).uid != HttpClient.getUid()) {
                        return;
                    }
                } else {
                    if (null != awList && awList.size() > 0 && awList.get(0).uid != mGroupLeaderId && awList.get(0).uid != HttpClient.getUid()) {
                        return;
                    }
                }
                getMarquee(awList);
            }

            @Override
            public void onSendWish(List<ApiUsersLiveWish> awList) {
                L.e("???????????????:?????? " + awList.size());
                // ??????????????? ????????????????????? ???????????????????????????????????? ????????????????????????
                if (isSingle) {
                    if (null != awList && awList.size() > 0 && awList.get(0).uid != Long.parseLong(toUid) && awList.get(0).uid != HttpClient.getUid()) {
                        return;
                    }
                } else {
                    if (null != awList && awList.size() > 0 && awList.get(0).uid != mGroupLeaderId && awList.get(0).uid != HttpClient.getUid()) {
                        return;
                    }
                }
                getMarquee(awList);
            }
        });
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        MediaPlayerUtil.getMediaPlayer().release();
        IMUtil.removeReceiver(getClass().getName());
        LiveBundle.getInstance().removeSocketClient(this.getLocalClassName());
        ImMessageUtil.getInstance().setSingle(true);
        if (liveOutGiftComponent != null) {
            liveOutGiftComponent.clear();
        }
        super.onDestroy();
    }

}
