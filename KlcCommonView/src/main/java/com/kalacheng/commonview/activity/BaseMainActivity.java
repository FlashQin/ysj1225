package com.kalacheng.commonview.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallAdapter;
import com.fm.openinstall.model.AppData;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.base.event.AccountDisableEvent;
import com.kalacheng.base.event.GpsEvent;
import com.kalacheng.base.event.KickOutRoomEvent;
import com.kalacheng.base.event.SignEvent;
import com.kalacheng.base.event.TokenInvalidEvent;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.base.socket.SocketUtils;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busgraderight.httpApi.HttpApiNoble;
import com.kalacheng.busnobility.httpApi.HttpApiNobLiveGift;
import com.kalacheng.busnobility.model.ApiNobLiveGift;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.BigGiftAdapter;
import com.kalacheng.commonview.dialog.AccountDisableDialogFragment;
import com.kalacheng.commonview.dialog.MainStartDialogFragment;
import com.kalacheng.commonview.dialog.SignInDialog;
import com.kalacheng.commonview.dialog.SuspensionFramePermissionDialog;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.commonview.jguangIm.ImPushUtil;
import com.kalacheng.commonview.jguangIm.ImUnReadCountEvent;
import com.kalacheng.commonview.jguangIm.UnReadCountEvent;
import com.kalacheng.commonview.service.DownloadService;
import com.kalacheng.commonview.utils.AllSocketUtlis;
import com.kalacheng.commonview.utils.SmallLiveRoomDialogFragment;
import com.kalacheng.commonview.utils.VersionUtil;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.AdminLoginAward;
import com.kalacheng.libuser.model.ApiNoRead;
import com.kalacheng.libuser.model.ApiSignInDto;
import com.kalacheng.libuser.model.ApiVersion;
import com.kalacheng.libuser.model.SysNotic;
import com.kalacheng.libuser.model.VipPrivilegeDto;
import com.kalacheng.maputils.LocationUtil;
import com.kalacheng.util.bean.TodayIsFirstLoginTagBean;
import com.kalacheng.util.permission.FloatPermissionManager;
import com.kalacheng.util.utils.AppUtil;
import com.kalacheng.util.utils.AssetsUtil;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.GpsUtil;
import com.kalacheng.util.utils.JsonUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.utils.download.DownloadUtil;
import com.kalacheng.util.utils.glide.GlideUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketEventHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.agora.capture.framework.modules.consumers.BaseWindowConsumer.CHANNEL_ID;


public class BaseMainActivity extends BaseActivity {
    private final static String TAG = BaseMainActivity.class.getSimpleName();
    protected ApiUserInfo apiUserInfo;
    protected PermissionsUtil mProcessResultUtil;

    private BaseApplication application;
    private long mLastClickBackTime;//????????????back????????????

    private boolean mPermissionDialogShow;//??????dialog????????????
    private boolean mPermissionAll;//????????????
    private boolean mPermissionRequested;//?????????????????????

    protected int notifyNum = 0;
    protected int msgNum = 0;

    public ApiSignInDto signInDto;//????????????


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //??????activity
        if (application == null) {
            // ??????Application??????
            application = (BaseApplication) getApplication();
        }

        mProcessResultUtil = new PermissionsUtil(this);

        //??????IM
        ImMessageUtil.getInstance().loginJMessage(String.valueOf(HttpClient.getUid()));
        EventBus.getDefault().register(this);
        apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
        initPopup();


        //?????????????????????????????????
        boolean isPermission = FloatPermissionManager.getInstance().applyFloatWindow(mContext);
        //???????????????????????????????????????7.0
        if (!isPermission) {
            //???????????????
            mPermissionDialogShow = true;
            SuspensionFramePermissionDialog.getInstance().show(mContext, new SuspensionFramePermissionDialog.OnPermissionClickListener() {
                @Override
                public void onClick() {
                    mPermissionAll = true;
                }
            });
        } else {
            //getToken();
        }
        //??????socket
        AllSocketUtlis.getInstance().getSocket(getApplicationContext());
        //???????????????????????????????????????
//        AllSocketUtlis.getInstance().setSendLiveAddressSocket();

        AssetsUtil.getInstance(getApplicationContext()).copyAssetsToSD("air", APPProConfig.AIR_PATH);

        //??????Socket???????????????
        SocketUtils.checkSocket(BaseMainActivity.this);

        getBirthday();
        getSignInfo();

        if (ConfigUtil.getIntValue(R.integer.bindingSuperiorType) == 1) {
            getInviteCode();
        }

        initSocketEventHandler();

        initPush();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUnReadCount();
        ImPushUtil.getInstance().resumePush();
        if (mPermissionAll && !mPermissionRequested) {
            mPermissionRequested = true;
            requestAllPermission();
        }
        if (!mPermissionDialogShow && !mPermissionRequested) {
            mPermissionRequested = true;
            requestPartPermission();
        }

        if (ConfigUtil.getIntValue(R.integer.bindingSuperiorType) == 0) {
            getClipboardData();
        }
        if (ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
            getReadShortVideoNumber();
        }
    }

    private void initPush() {
//        updateListViewReceiver = new MsgReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(MessageReceiver.UPDATE_LISTVIEW_ACTION);
//        registerReceiver(updateListViewReceiver, intentFilter);
//        XGPushConfig.enableDebug(this, true);
//
//        // 1.????????????Token
////        XGPushConfig.getToken(this);
//
//        //?????????????????????
//        XGPushConfig.enableOtherPush(this, true);
//        //????????????
//        XGPushConfig.setMiPushAppId(this, "2882303761518802630"); // AppID
//        XGPushConfig.setMiPushAppKey(this, "5161880210630");// AppKey
//        //???????????? (?????????????????????)
//        XGPushConfig.setMzPushAppId(this, "");
//        XGPushConfig.setMzPushAppKey(this, "");
//        // ???????????????????????? Oppo ??? AppKey?????????AppId
//        XGPushConfig.setOppoPushAppId(getApplicationContext(), "789fc86d0ef64b8ab40ce37f53c4a757"); // AppKey
//        // ???????????????????????? Oppo ??? AppSecret????????? AppKey
//        XGPushConfig.setOppoPushAppKey(getApplicationContext(), "027e003649d04f1db51351a6631d7278"); // AppSecret
//        // ??????????????????
//        XGPushManager.createNotificationChannel(this.getApplicationContext(), "default_message", "????????????", true, true, true, null);
//
//        // ????????????  ????????????
//        XGPushManager.registerPush(getApplicationContext(),
//                new XGIOperateCallback() {
//                    @Override
//                    public void onSuccess(final Object data, int flag) {
//                        Log.w("xgtest", "+++ . token:" + data);
//
//                        //????????????
//                        XGPushManager.setTag(getApplicationContext(), HttpClient.getUid() + "_" + (String) data, new XGIOperateCallback() {
//                            @Override
//                            public void onSuccess(Object o, int i) {
//                                pushRegister(8, HttpClient.getUid() + "_" + (String) data, "");
//                            }
//
//                            @Override
//                            public void onFail(Object o, int i, String s) {
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFail(Object data, int errCode, String msg) {
//                        Log.w("xgtest",
//                                "+++ register push fail. token:" + data
//                                        + ", errCode:" + errCode + ",msg:"
//                                        + msg);
//                    }
//                });

//         // ????????????
//        ApiUserInfo userInfo = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);
//        if (null != userInfo) {
//            XGPushManager.bindAccount(getApplicationContext(), userInfo.mobile, XGPushManager.AccountType.PHONE_NUMBER.getValue(), new XGIOperateCallback() {
//                @Override
//                public void onSuccess(Object o, int i) {
//                    Log.e("TPush>>>", o.toString());
//                }
//                @Override
//                public void onFail(Object o, int i, String s) {
//
//                }
//            });
//        }
    }

    // TPNS??????????????????token ????????????
    private void pushRegister(int pushPlatform, final String pushRegisterId, String token) {
        // 8 ?????????TPNS
        HttpApiAppUser.upUserPushInfo(pushPlatform, pushRegisterId, token, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    L.e(TAG, "-----------------------------------------------------------------------------------------------");
                    L.e(TAG, "???????????????  upUserPushInfo   ????????????  push_id  " + pushRegisterId);
                    L.e(TAG, "???????????????  upUserPushInfo   code  " + code + "  msg  " + msg);
                    L.e(TAG, "-----------------------------------------------------------------------------------------------");
                }
            }
        });
    }

    //?????????????????????
    private void getClipboardData() {
        //????????????????????????????????????1 ?????????2?????????
        int isPid = (int) SpUtil.getInstance().getSharedPreference(SpUtil.USER_IS_PID, 0);
        if (isPid == 1) {
            this.getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    String code = WordUtil.getClipboardContent();
                    if (code != null) {
                        if (!TextUtils.isEmpty(code) && code.startsWith("channel-")) {
                            code = code.substring(code.indexOf("-") + 1);
                            HttpApiAppUser.binding(code, new HttpApiCallBack<HttpNone>() {
                                @Override
                                public void onHttpRet(int code, String msg, HttpNone retModel) {
                                    if (code == 1) {
                                        SpUtil.getInstance().put(SpUtil.USER_IS_PID, 2);
                                    }
//                                    ToastUtil.show(msg);
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    /**
     * ??????openInstall??????
     */
    private void getInviteCode() {
        //????????????????????????????????????1 ?????????2?????????
        int isPid = (int) SpUtil.getInstance().getSharedPreference(SpUtil.USER_IS_PID, 0);
        if (isPid == 1) {
            OpenInstall.getInstall(new AppInstallAdapter() {
                @Override
                public void onInstall(AppData appData) {
                    //??????????????????
//                String channelCode = appData.getChannel();
                    //?????????????????????
                    String bindData = appData.getData();

                    JSONObject obj = JSON.parseObject(bindData);
                    if (obj != null) {
                        String inviteCode = obj.getString("code");
                        if (!TextUtils.isEmpty(inviteCode)) {
                            HttpApiAppUser.binding(inviteCode, new HttpApiCallBack<HttpNone>() {
                                @Override
                                public void onHttpRet(int code, String msg, HttpNone retModel) {
                                    if (code == 1) {
                                        SpUtil.getInstance().put(SpUtil.USER_IS_PID, 2);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        if (curTime - mLastClickBackTime > 2000) {
            mLastClickBackTime = curTime;
            ToastUtil.show(R.string.main_click_next_exit);
            return;
        }
        AllSocketUtlis.getInstance().delete();
        //?????????????????????app ,??????????????????
        if (LiveConstants.isSamll) {
            SmallLiveRoomDialogFragment.getInstance().closeRoom();
        }
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        LocationUtil.getInstance().stopLocation();
        super.onDestroy();
    }

    /**
     * ???????????????
     */
    private void initPopup() {
        //??????????????????????????????1 ???;2 ??????
        int isFirstLogin = (int) SpUtil.getInstance().getSharedPreference(SpUtil.USER_IS_FIRST_LOGIN, 0);
        if (isFirstLogin == 1) {
            showBigGift();
        }
        getContinueLogin();
        getSysNotic();
        checkVersion();

        getMuisc();
    }

    /**
     * ???????????????
     */
    private void showBigGift() {
        if (apiUserInfo.packList != null && apiUserInfo.packList.size() > 0) {
            SpUtil.getInstance().put(SpUtil.USER_IS_FIRST_LOGIN, 2);

            final Dialog bigGiftdialog = DialogUtil.getBaseDialog(this, R.style.dialog, R.layout.dialog_big_gift, true, true);
            Window window = bigGiftdialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(80);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            RecyclerView recyclerView = bigGiftdialog.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(new BigGiftAdapter(mContext, apiUserInfo.packList));
            recyclerView.addItemDecoration(new ItemDecoration(mContext, 0x00000000, (DpUtil.px2dp(DpUtil.getScreenWidth()) - 314) / 2, 0));

            bigGiftdialog.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bigGiftdialog.dismiss();
                }
            });
            bigGiftdialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bigGiftdialog.dismiss();
                }
            });
        }
    }

    /**
     * ????????????
     */
    private void showConsecutiveLogin(int day, int coin) {
        final Dialog dialog = DialogUtil.getBaseDialog(this, R.style.dialog, R.layout.dialog_consecutive_login, true, true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(100);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        TextView tvLogin = dialog.findViewById(R.id.tv_login);
        TextView tvPrice = dialog.findViewById(R.id.tv_price);
        tvLogin.setText("???" + day + "?????????");
        tvPrice.setText("+" + coin);
        dialog.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    /**
     * ????????????
     */
    private void showSystem(final SysNotic sysNotic) {
        final Dialog dialog = DialogUtil.getBaseDialog(this, R.style.dialog, R.layout.dialog_system_login, true, true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(80);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        if (sysNotic.shape == 1) {//??????
            TextView tvSystem = dialog.findViewById(R.id.tv_system);
            tvSystem.setText(sysNotic.content);
        } else {//??????
            dialog.findViewById(R.id.ivSysNoticeTag).setVisibility(View.GONE);
            dialog.findViewById(R.id.layoutTxt).setVisibility(View.GONE);
            dialog.findViewById(R.id.layoutPicture).setVisibility(View.VISIBLE);
            ImageView ivSysNotice = dialog.findViewById(R.id.ivSysNotice);
            ImageLoader.display(sysNotic.imageUrl, ivSysNotice);
        }
        dialog.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(sysNotic.url)) {
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, sysNotic.url).navigation();
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * ????????????????????????
     */
    protected void getContinueLogin() {
        HttpApiAppUser.getContinueLogin(new HttpApiCallBack<AdminLoginAward>() {
            @Override
            public void onHttpRet(int code, String msg, AdminLoginAward retModel) {
                if (code == 1 && retModel != null) {
                    showConsecutiveLogin(retModel.day, retModel.coin);
                }
            }
        });
    }

    /**
     * ??????????????????
     */
    private void getSysNotic() {
        HttpApiAppUser.getSysNotic(new HttpApiCallBack<SysNotic>() {
            @Override
            public void onHttpRet(int code, String msg, SysNotic retModel) {
                if (code == 1 && retModel != null) {
                    if (retModel.showType == 1) {
                        showSystem(retModel);
                    } else {
                        String string = (String) SpUtil.getInstance().getSharedPreference(SpUtil.SYS_NOTICE_SHOWED_DATA, "");
                        String date = new DateUtil().getDateToFormat("yyyyMMdd");
                        if (TextUtils.isEmpty(string) || !string.equals(retModel.id + date)) {
                            SpUtil.getInstance().put(SpUtil.SYS_NOTICE_SHOWED_DATA, retModel.id + date);
                            showSystem(retModel);
                        }
                    }
                }
            }
        });
    }

    /**
     * ??????????????????
     */
    private void checkVersion() {
        HttpApiAppUser.version_control(1, AppUtil.getVersionCode(), new HttpApiCallBack<ApiVersion>() {
            @Override
            public void onHttpRet(int code, String msg, ApiVersion retModel) {
                if (code == 1) {
                    if (retModel.isConstraint == 0) {
                        VersionUtil.showAppUpdateDialog(mContext, retModel.versionNo, retModel.des, retModel.url, false);
                    } else if (retModel.isConstraint == 1) {
                        VersionUtil.showAppUpdateDialog(mContext, retModel.versionNo, retModel.des, retModel.url, true);
                    }
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    private void getMuisc() {
        File dir = new File(APPProConfig.MUSIC_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        DownloadUtil.getInstance().download(HttpConstants.DOWNLOAD_MUSIC, dir, "seekchat" + ".mp3", (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_MUISC, ""), new DownloadUtil.Callback() {

            @Override
            public void onSuccess(File file) {
               /* ApiMusic apiMusic = new ApiMusic();
                apiMusic.id ="seekchat";
                apiMusic.name="seekchat";
                apiMusic.authors="";
                apiMusic.playUrl = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_MUISC,"");
                MusicDbManager.getInstace().save(apiMusic);*/

                String voiceLocalPath = file.getAbsolutePath();
                SpUtil.getInstance().put("Muisc", file.getAbsolutePath());
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    /**
     * ???????????? ???????????????
     */
    private void getUnReadCount() {
        HttpApiChatRoom.getAppSystemNoRead(new HttpApiCallBack<ApiNoRead>() {
            @Override
            public void onHttpRet(int code, String msg, ApiNoRead retModel) {
                if (code == 1) {
                    EventBus.getDefault().post(new UnReadCountEvent(retModel.totalNoRead, retModel.systemNoRead, retModel.videoNoRead, retModel.shortVideoNoRead, retModel.officialNewsNoRead));
                }
            }
        });
    }

    /**
     * ??????????????????
     */
    private void requestPartPermission() {
        mProcessResultUtil.requestPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, new Runnable() {
            @Override
            public void run() {
                loadGiftList();
            }
        });
        mProcessResultUtil.requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        }, new Runnable() {
            @Override
            public void run() {
                LocationUtil.getInstance().startLocation();
            }
        });
    }

    /**
     * ??????????????????
     */
    private void requestAllPermission() {
        mProcessResultUtil.requestPermissions(new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, new Runnable() {
            @Override
            public void run() {
                loadGiftList();
                LocationUtil.getInstance().startLocation();
                gotoNotificationSetting();
                //???????????????
//                PushRegisterSet.setIsExpired(false);
//                PushRegisterSet.applicationInit(getApplication());
//                getToken();
            }
        });
    }

    //?????????????????????
    public void gotoNotificationSetting() {
        ToastUtil.show("???????????????????????????????????????????????????~");
        try {
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //????????????????????? API 26, ???8.0??????8.0??????????????????
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, CHANNEL_ID);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //????????????????????? API21??????25?????? 5.0??????7.1 ???????????????????????????
                intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra("app_package", getPackageName());
                intent.putExtra("app_uid", CHANNEL_ID);
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // ??????????????????????????????????????????
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

//    /*----- ???????????? S ------------------------------------------------------------------*/
//
//    private void getToken() {
//
//        String manufacturer = android.os.Build.MANUFACTURER;
//        L.e("get token: manufacturer    " + manufacturer);
//
//
////        //????????????token
//        PushRegisterSet.registerInitPush(getApplication(), HttpClient.getUid());
//
//
//    }
//
//
//    //??????????????????
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onRegisterEvent(RegisterEvent register) {
//
//        if (register.isSuss()) {
//            String alias = PushRegisterSet.setLoginAlias(getApplication(), HttpClient.getUid());
//            String registerId = PushRegisterSet.getRegId(getApplication());
//            EventBus.getDefault().post(AliasEvent.getInstance(PushRegisterSet.getUsePushIndex(), alias, registerId));
//        }
//
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAliasEvent(AliasEvent alias) {
//
//        L.e(TAG, "-----------------------------------------------------------------------------------------------");
//        L.e(TAG, "???????????????????????? ????????? newAlias  " + (null != alias && !TextUtils.isEmpty(alias.getAlias()) ? alias.getAlias() : ""));
//        L.e(TAG, "???????????????????????? ????????? registerId  " + (null != alias && !TextUtils.isEmpty(alias.getRegisterId()) ? alias.getRegisterId() : ""));
//        L.e(TAG, "???????????????????????? ????????? ???????????? 1:?????? 2:?????? 3:vivo 4:oppo 5:apns 6:?????? 7:miApns");
//        L.e(TAG, "???????????????????????? ?????????????????????????????????  " + (null != alias ? alias.getPushPlatform() : -1));
//        L.e(TAG, "???????????????????????? ????????????  " + PushRegisterSet.isExpired());
//        L.e(TAG, "???????????????????????? ???????????????????????????  " + PushRegisterSet.getUsePushIndex());
//
//        L.e(TAG, "-----------------------------------------------------------------------------------------------");
//
//        if (null != alias && null != alias.getAlias() && alias.getAlias().length() > 0) {
//            SpUtil.getInstance().putModel(SpUtil.USER_ALIAS, alias);
//
//            String push_id = "";
//            if (alias.getPushPlatform() == 2 || alias.getPushPlatform() == 3 || alias.getPushPlatform() == 4) {
//                push_id = alias.getRegisterId();
//            } else {
//                push_id = alias.getAlias();
//            }
//
//            final String finalPush_id = push_id;
//            HttpApiAppUser.upUserPushInfo(alias.getPushPlatform(), push_id, new HttpApiCallBack<HttpNone>() {
//                @Override
//                public void onHttpRet(int code, String msg, HttpNone retModel) {
//                    L.e(TAG, "-----------------------------------------------------------------------------------------------");
//                    L.e(TAG, "???????????????  upUserPushInfo   ????????????  push_id  " + finalPush_id);
//                    L.e(TAG, "???????????????  upUserPushInfo   code  " + code + "  msg  " + msg);
//
//                    L.e(TAG, "-----------------------------------------------------------------------------------------------");
//                }
//            });
//        }
//
//    }
//
//
//    /**
//     * ?????? ???????????? Intent ??????
//     *
//     * @param intent
//     */
//    private void getIntentData(Intent intent) {
//        if (null != intent) {
//
//            Uri uri = intent.getData();
//            if (uri == null) {
//                L.e(TAG, "getData null");
//                return;
//            }
//
//            // ??????????????????????????????????????????????????????????????????
//            String msgid = intent.getStringExtra("_push_msgid");
//            String cmdType = intent.getStringExtra("_push_cmd_type");
//            int notifyId = intent.getIntExtra("_push_notifyid", -1);
//            Bundle bundle = intent.getExtras();
//            if (bundle != null) {
//                for (String key : bundle.keySet()) {
//                    String content = bundle.getString(key);
//                    L.e(TAG, "receive data from push, key = " + key + ", content = " + content);
//                }
//            }
//            L.e(TAG, "receive data from push, msgId = " + msgid + ", cmd = " + cmdType + ", notifyId = " + notifyId);
//        } else {
//            L.e(TAG, "intent is null");
//        }
//    }
//
//    /*----- ???????????? E ------------------------------------------------------------------*/

    private void loadGiftList() {
        HttpApiNobLiveGift.getGiftList(-1, new HttpApiCallBackArr<ApiNobLiveGift>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiNobLiveGift> retModel) {
                if (null != retModel) {
                    Intent intent = new Intent(getApplicationContext(), DownloadService.class);
                    intent.putParcelableArrayListExtra("NobLiveGift", (ArrayList<? extends Parcelable>) retModel.get(0).giftList);
                    mContext.startService(intent);
                }
            }
        });
    }

    // ????????????????????????
    private void getBirthday() {
        HttpApiNoble.vipPrivilegeShow(new HttpApiCallBack<VipPrivilegeDto>() {
            @Override
            public void onHttpRet(int code, String msg, VipPrivilegeDto retModel) {
                if (code == 1) {
                    if (retModel.birthday == 1) {
                        setBirthday();
                    }
                }
            }
        });
    }

    //?????????
    private void setBirthday() {
        try {
            try {
                ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                if (new DateUtil().getDateToFormat("MM-dd").equals(new DateUtil(apiUserInfo.birthday, "yyyy-MM-dd").getDateToFormat("MM-dd"))) {
                    String birthday = (String) SpUtil.getInstance().getSharedPreference(SpUtil.BIRTHDAY, "");
                    if (birthday.equals(apiUserInfo.birthday)) {
                        return;
                    }
                    SpUtil.getInstance().put(SpUtil.BIRTHDAY, apiUserInfo.birthday);
                    showBirthday(apiUserInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ?????????
     */
    private void showBirthday(final ApiUserInfo apiUserInfo) {
        final Dialog dialog = DialogUtil.getBaseDialog(this, R.style.dialog, R.layout.fragment_birthday_welcome, false, false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.getScreenWidth();
        params.height = DpUtil.getScreenHeight();
        window.setAttributes(params);

        RoundedImageView avatar = dialog.findViewById(R.id.avatar);
        ImageView ivGif = dialog.findViewById(R.id.iv_gif);

        Typeface mtypeface = Typeface.createFromAsset(getAssets(), "ArialBlackRegular.ttf");
        TextView tv1 = dialog.findViewById(R.id.tv_1);
        TextView tv2 = dialog.findViewById(R.id.tv_2);
        tv1.setTypeface(mtypeface);
        tv2.setTypeface(mtypeface);

        Typeface mtypeface2 = Typeface.createFromAsset(getAssets(), "YouSheBiaoTiHei.ttf");
        TextView birthdayContent = dialog.findViewById(R.id.birthday_name);
        TextView birthdayName = dialog.findViewById(R.id.birthday_content);
        birthdayContent.setTypeface(mtypeface2);
        birthdayName.setTypeface(mtypeface2);

        birthdayName.setText(apiUserInfo.username);
        birthdayContent.setText("????????????!");
        ImageLoader.display(apiUserInfo.avatar, avatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        GlideUtils.loadOneTimeGif(this, R.mipmap.birthday_gif, ivGif, 1, new GlideUtils.GifListener() {
            @Override
            public void gifPlayComplete() {
                dialog.dismiss();
            }
        });

        ivGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenInvalidEvent(TokenInvalidEvent e) {
        ImMessageUtil.getInstance().logoutEMClient();
        ARouter.getInstance().build(ARouterPath.LoginActivity).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                navigation(BaseMainActivity.this, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {

                    }

                    @Override
                    public void onLost(Postcard postcard) {

                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        finish();
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {

                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onImUnReadCountEvent(ImUnReadCountEvent e) {
        if (e != null) {
            msgNum = Integer.parseInt(e.getUnReadCount());
        }
        onAllUnReadCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnReadCountEvent(UnReadCountEvent e) {
        if (e != null) {
            notifyNum = e.getTotalNoRead();
        }
        onAllUnReadCount();
    }

    //????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAccountDisableEvent(AccountDisableEvent accountDisableEvent) {
        if (accountDisableEvent != null) {
            new AccountDisableDialogFragment(getApplicationContext(), accountDisableEvent.obj);
        }
    }

    protected void onAllUnReadCount() {

    }

    public void startOnClick(View v) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (ConfigUtil.getIntValue(R.integer.mainStartType) == 1) {
            HttpApiAppUser.personCenter(-1, -1, HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
                @Override
                public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                    if (code == 1 && retModel != null) {
                        if (retModel.role == 0) {
                            ARouter.getInstance().build(ARouterPath.MeetAudienceManyActivity).navigation();
                        } else {
                            ARouter.getInstance().build(ARouterPath.MeetAnchorActivity).navigation();
                        }
                    }
                }
            });
        } else if (ConfigUtil.getIntValue(R.integer.mainStartType) == 2) {
            HttpApiAppUser.personCenter(-1, -1, HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
                @Override
                public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                    if (code == 1 && retModel != null) {
                        if (retModel.sex == 1) {
                            ARouter.getInstance().build(ARouterPath.MeetAudienceManyActivity).navigation();
                        } else {
                            ARouter.getInstance().build(ARouterPath.MeetAnchorActivity).navigation();
                        }
                    }
                }
            });
        } else if (ConfigUtil.getIntValue(R.integer.mainStartType) == 3) {
            voiceClick();
        } else if (ConfigUtil.getIntValue(R.integer.mainStartType) == 4) {
            liveClick();
        } else {
            MainStartDialogFragment dialogFragment = new MainStartDialogFragment();
            dialogFragment.setMainStartChooseCallback(mMainStartChooseCallback);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(dialogFragment, this.getClass().getSimpleName());
            ft.commitAllowingStateLoss();
        }
    }

    private MainStartDialogFragment.MainStartChooseCallback mMainStartChooseCallback = new MainStartDialogFragment.MainStartChooseCallback() {
        @Override
        public void onLiveClick() {
            liveClick();
        }

        @Override
        public void onVoiceClick() {
            voiceClick();
        }

        @Override
        public void onPicClick() {
            // ?????????
            auth(4, new onCallBack() {
                @Override
                public void callBack(String no_use) {
                    DynamicMakeActivity.forward(BaseMainActivity.this, 1, 0, false, 1002);
                }
            });
        }

        @Override
        public void onVideoClick() {
            // ?????????
            auth(4, new onCallBack() {
                @Override
                public void callBack(String no_use) {
                    DynamicMakeActivity.forward(BaseMainActivity.this, 2, 0, false, 1002);
                }
            });
        }

        @Override
        public void onPublishClick() {
            // ?????????
            auth(2, new onCallBack() {
                @Override
                public void callBack(String no_use) {
                    DynamicMakeActivity.forward(BaseMainActivity.this, 0, 0, false, 1001);
                }
            });
        }
    };

    public void auth(final int type, final onCallBack onCallBack) {
        mProcessResultUtil.requestPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        }, new Runnable() {
            @Override
            public void run() {
                HttpApiAPPAnchor.is_auth(type, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1 && null != retModel) {
                            if ("0".equals(retModel.no_use)) {
                                onCallBack.callBack(retModel.no_use);
                            } else if ("1".equals(retModel.no_use)) {
                                if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.AUTH_IS_SEX, 1) == 0) {
                                    ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                                    if (apiUserInfo != null && apiUserInfo.sex == 2) {
                                        ARouter.getInstance().build(ARouterPath.ApplyAnchorActivity).navigation();
                                    } else {
                                        DialogUtil.showKnowDialog(BaseMainActivity.this, "?????????????????????????????????~", null);
                                    }
                                } else {
                                    ARouter.getInstance().build(ARouterPath.ApplyAnchorActivity).navigation();
                                }
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
            }
        });
    }

    /**
     * ??????????????????
     */
    private void liveClick() {
        if (LiveConstants.isSamll) {
            if (LiveConstants.ANCHORID == HttpClient.getUid()) {
                ToastUtil.show("??????????????????");
            } else {
                SmallLiveRoomDialogFragment.getInstance().closeRoom();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        auth(1, new onCallBack() {
                            @Override
                            public void callBack(String no_use) {
                                ARouter.getInstance().build(ARouterPath.LiveAnchorActivity).navigation();
                            }
                        });
                    }
                }, 500);
            }
        } else {
            auth(1, new onCallBack() {
                @Override
                public void callBack(String no_use) {
                    ARouter.getInstance().build(ARouterPath.LiveAnchorActivity).navigation();
                }
            });
        }
    }

    /**
     * ??????????????????
     */
    public void voiceClick() {
        if (LiveConstants.isSamll) {
            if (LiveConstants.ANCHORID == HttpClient.getUid()) {
                ToastUtil.show("??????????????????");
            } else {
                SmallLiveRoomDialogFragment.getInstance().closeRoom();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        auth(5, new onCallBack() {
                            @Override
                            public void callBack(String no_use) {
                                ARouter.getInstance().build(ARouterPath.VoiceLive).navigation();
                            }
                        });
                    }
                }, 500);
            }
        } else {
            auth(5, new onCallBack() {
                @Override
                public void callBack(String no_use) {
                    ARouter.getInstance().build(ARouterPath.VoiceLive).navigation();
                }
            });
        }
    }

    public interface onCallBack {
        void callBack(String no_use);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                if (data.getIntExtra(ARouterValueNameConfig.DYNAMIC_RESULT_TYPE, 0) == 0) {
                    ARouter.getInstance().build(ARouterPath.VideoPublish).withObject(ARouterValueNameConfig.PICTURE_LIST, data.getParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST)).navigation();
                } else {
                    ARouter.getInstance().build(ARouterPath.VideoPublish).withString(ARouterValueNameConfig.VIDEO_PATH, data.getStringExtra(ARouterValueNameConfig.VIDEO_PATH))
                            .withInt(ARouterValueNameConfig.VideoType, data.getIntExtra(ARouterValueNameConfig.VideoType, 1))
                            .withLong(ARouterValueNameConfig.VIDEO_TIME_LONG, data.getLongExtra(ARouterValueNameConfig.VIDEO_TIME_LONG, 0)).navigation();
                }
            } else if (requestCode == 1002) {
                if (data.getIntExtra(ARouterValueNameConfig.DYNAMIC_RESULT_TYPE, 0) == 0) {
                    ARouter.getInstance().build(ARouterPath.VideoPublish).withObject(ARouterValueNameConfig.PICTURE_LIST, data.getParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST))
                            .withBoolean(ARouterValueNameConfig.SHORT_VIDEO, true).navigation();
                } else {
                    ARouter.getInstance().build(ARouterPath.VideoPublish).withString(ARouterValueNameConfig.VIDEO_PATH, data.getStringExtra(ARouterValueNameConfig.VIDEO_PATH))
                            .withInt(ARouterValueNameConfig.VideoType, data.getIntExtra(ARouterValueNameConfig.VideoType, 1))
                            .withLong(ARouterValueNameConfig.VIDEO_TIME_LONG, data.getLongExtra(ARouterValueNameConfig.VIDEO_TIME_LONG, 0))
                            .withBoolean(ARouterValueNameConfig.SHORT_VIDEO, true).navigation();
                }
            }
        }

        if (requestCode == 10031 && GpsUtil.isOPen(this)) {//??????GPS??????
            EventBus.getDefault().post(new GpsEvent());
        }
    }

    /**
     * ????????????????????????????????????
     */
    private void getReadShortVideoNumber() {
        HttpApiAppShortVideo.isReadShortVideoNumber(new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    SpUtil.getInstance().put(SpUtil.READ_SHORT_VIDEO_NUMBER, Integer.parseInt(retModel.no_use));
                }
            }
        });
    }

    /**
     * ??????????????????socket
     */
    private void initSocketEventHandler() {
        IMUtil.setHandler(new SocketEventHandler() {
            @Override
            public void onIOConnect(String s) {

            }

            @Override
            public void onConnect(boolean b) {
                // ?????????????????????????????? ??????b == true ????????????????????????????????????????????????????????????????????????????????????
                Log.e("onConnect>>>", "" + b);
                if (b) {
                    EventBus.getDefault().post(new KickOutRoomEvent());
                }
            }

            @Override
            public void onDisConnect(String s) {

            }

            @Override
            public void onTokenInvalid(String s) {

            }

            @Override
            public void onKeyExpire(String s) {

            }

            @Override
            public void onDataErr(String s) {

            }
        });
    }

    /**
     * ??????????????????
     */
    private void getSignInfo() {
        HttpApiAppUser.getSignInfo(new HttpApiCallBack<ApiSignInDto>() {
            @Override
            public void onHttpRet(int code, String msg, ApiSignInDto retModel) {
                if (code == 1 && retModel != null) {
                    signInDto = retModel;
                    if (signInDto.isSign == 1) {
                        EventBus.getDefault().post(new SignEvent());
                    }
                    //???????????????????????????
                    userIsFirstLogin();
                }
            }
        });
    }

    /**
     * ???????????????????????????
     */
    private void userIsFirstLogin() {
        //?????? ?????????????????????????????????????????????""???
        String uid = String.valueOf(HttpClient.getUid());
        String todayTime = uid + new DateUtil().getDateToFormat("yyyy-MM-dd");
        String isFirstLogin = "";

        String user_today_is_first_login = (String) SpUtil.getInstance().getSharedPreference(SpUtil.USER_TODAY_IS_FIRST_LOGIN, "");
        List<TodayIsFirstLoginTagBean> list = new ArrayList<>();

        try {
            if (user_today_is_first_login.length() > 0) {
                list = JsonUtil.objBeanToList(user_today_is_first_login, TodayIsFirstLoginTagBean.class);
                if (null != list && list.size() > 0) {
                    for (TodayIsFirstLoginTagBean tag : list) {
                        if (tag.getTag().equals(uid)) {
                            isFirstLogin = tag.getValue();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isFirstLogin.equals(todayTime)) {
            isFirstLogin = "";
            list.add(list.size(), new TodayIsFirstLoginTagBean(uid));
        }
        if (isFirstLogin.length() == 0) {

            //?????????????????????
            showSignInDialog();

            for (TodayIsFirstLoginTagBean tag1 : list) {
                if (tag1.getTag().equals(uid)) {
                    tag1.setValue(todayTime);
                }
            }

            String newTag = JsonUtil.toJson(list);
            SpUtil.getInstance().put(SpUtil.USER_TODAY_IS_FIRST_LOGIN, newTag);
        }
    }

    /**
     * ?????? ???????????????
     */
    public void showSignInDialog() {
        SignInDialog dialog = new SignInDialog(mContext, signInDto, new SignInDialog.SignInListener() {
            @Override
            public void signIn() {
                getSignInfo();
            }
        });
        dialog.show();
    }

}
