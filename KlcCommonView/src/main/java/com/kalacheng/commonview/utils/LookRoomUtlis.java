package com.kalacheng.commonview.utils;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buslive.httpApi.HttpApiHttpLive;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.busvoicelive.httpApi.HttpApiHttpVoice;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.dialog.SmallJoinRoomTipsDialogFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.libuser.model.ApiLeaveRoom;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.aop.NotNull;
import com.kalacheng.util.utils.aop.NullPointerCheck;
import com.klc.bean.live.VoiceLiveOwnStateBean;
import com.xuantongyun.livecloud.protocol.VoiceLiveCloudUtils;

import java.util.ArrayList;


/**
 * ???????????????
 */
public class LookRoomUtlis {
    private Context mContext;
    private static volatile LookRoomUtlis singleton;
    private SmallJoinRoomTipsDialogFragment smallJoinRoomTipsDialogFragment;

    private AppHomeHallDTO data;

    private LookRoomUtlis() {
    }

    public static LookRoomUtlis getInstance() {
        if (singleton == null) {
            synchronized (LookRoomUtlis.class) {
                if (singleton == null) {
                    singleton = new LookRoomUtlis();
                }
            }
        }
        return singleton;
    }

    @NullPointerCheck
    public void getData(@NotNull AppHomeHallDTO bean, Context mContext) {
        this.mContext = mContext;
        HttpApiHome.getHomeDataInfo(bean.liveType, bean.roomId, new HttpApiCallBack<AppHomeHallDTO>() {
            @Override
            public void onHttpRet(int code, String msg, AppHomeHallDTO retModel) {
                if (code == 1 && retModel != null) {
                    data = retModel;
                    goNext(retModel);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    private void goNext(final AppHomeHallDTO bean) {
        //???????????? 0:???????????? 1:???????????? 2:???????????? 3:???????????? 4:????????????
        switch (bean.roomType) {
            case 0://????????????
                entryRoom(bean.roomId, new entryRoomListener() {
                    @Override
                    public void onGo() {
                        if (bean.liveType == 1) {//??????
                            joinRoom(bean.roomId, bean.roomType, "");
                        } else {//??????
                            VoiceJoinRoom(bean.roomId, bean.roomType, "");
                        }
                    }
                });
                break;
            case 1://????????????
                entryRoom(bean.roomId, new entryRoomListener() {
                    @Override
                    public void onGo() {
                        onLiveTypePwd(bean.roomId, bean.roomType, bean.typeVal);
                    }
                });
                break;
            case 2://????????????
                entryRoom(bean.roomId, new entryRoomListener() {
                    @Override
                    public void onGo() {
                        if (bean.isPay == 0 || bean.freeWatchTime > 0) {
                            if (bean.liveType == 1) {
                                joinRoom(bean.roomId, bean.roomType, String.valueOf(bean.coin));
                            } else {
                                VoiceJoinRoom(bean.roomId, bean.roomType, String.valueOf(bean.coin));
                            }
                        } else {
                            showDiaLog(bean);
                        }
                    }
                });
                break;
            case 3://????????????
                entryRoom(bean.roomId, new entryRoomListener() {
                    @Override
                    public void onGo() {
                        if (bean.freeWatchTime > 0) {
                            if (bean.liveType == 1) {
                                joinRoom(bean.roomId, bean.roomType, String.valueOf(bean.coin));
                            } else {
                                VoiceJoinRoom(bean.roomId, bean.roomType, String.valueOf(bean.coin));
                            }
                        } else {
                            showDiaLog(bean);
                        }
                    }
                });
                break;
            case 4://????????????
                entryRoom(bean.roomId, new entryRoomListener() {
                    @Override
                    public void onGo() {
                        HttpApiAppUser.getUserinfo(HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
                            @Override
                            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                                if (retModel.vipType == 1 || bean.freeWatchTime > 0) {
                                    if (bean.liveType == 1) {
                                        joinRoom(bean.roomId, bean.roomType, "");
                                    } else {
                                        VoiceJoinRoom(bean.roomId, bean.roomType, bean.typeVal);
                                    }
                                } else {
                                    showDiaLog(bean);
                                }
                            }
                        });
                    }
                });
                break;
        }
    }

    //?????????????????????
    private void joinRoom(long roomId, int type, String typeVal) {
        HttpApiHttpLive.joinRoom(roomId, type, typeVal, new HttpApiCallBack<AppJoinRoomVO>() {
            @Override
            public void onHttpRet(int code, String msg, AppJoinRoomVO retModel) {
                if (null != retModel && code == 1) {
                    //1????????????2????????????3?????????4??????5??????6????????????
                    LiveConstants.ROOMID = retModel.roomId;
                    LiveConstants.ANCHORID = retModel.anchorId;
                    SpUtil.getInstance().put(SpUtil.ANCHOR_ID, LiveConstants.ANCHORID);
                    LiveConstants.PULL = retModel.pull;
                    ARouter.getInstance().build(ARouterPath.LiveAudienceActivity).withParcelable("ApiJoinRoom", retModel).withParcelableArrayList("userList", (ArrayList<? extends Parcelable>) retModel.userList).navigation(mContext, new NavigationCallback() {
                        @Override
                        public void onFound(Postcard postcard) {
                            Log.i("aaa", "onFound" + postcard.toString());
                        }

                        @Override
                        public void onLost(Postcard postcard) {
                            Log.i("aaa", "onLost" + postcard.toString());
                        }

                        @Override
                        public void onArrival(Postcard postcard) {
                            Log.i("aaa", "onArrival" + postcard.toString());
                        }

                        @Override
                        public void onInterrupt(Postcard postcard) {
                            Log.i("aaa", "onInterrupt" + postcard.toString());
                        }
                    });


                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    //?????????????????????
    private void VoiceJoinRoom(long roomId, int type, String typeVal) {
        HttpApiHttpVoice.joinVoiceRoom(roomId, type, typeVal, new HttpApiCallBack<AppJoinRoomVO>() {
            @Override
            public void onHttpRet(int code, String msg, final AppJoinRoomVO retModel) {
                if (code == 1) {
                    if (LiveConstants.VoiceAnchorInvitation) {
                        if (LiveConstants.isSamll) {
                            SmallLiveRoomDialogFragment.getInstance().closeRoom();
                        }else {
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                joinRoomCallBack.joinSuccess(retModel);
                            }
                        }, 500);

                    } else {
                        LiveConstants.ROOMID = retModel.roomId;
                        LiveConstants.ANCHORID = retModel.anchorId;
                        SpUtil.getInstance().put(SpUtil.ANCHOR_ID, LiveConstants.ANCHORID);

                        ARouter.getInstance().build(ARouterPath.JOINVoiceRoom).withParcelable("ApiJoinRoom", retModel).
                                navigation(mContext, new NavigationCallback() {
                                    @Override
                                    public void onFound(Postcard postcard) {
                                        Log.i("aaa", "onFound" + postcard.toString());
                                    }

                                    @Override
                                    public void onLost(Postcard postcard) {
                                        Log.i("aaa", "onLost" + postcard.toString());
                                    }

                                    @Override
                                    public void onArrival(Postcard postcard) {
                                        Log.i("aaa", "onArrival" + postcard.toString());
                                    }

                                    @Override
                                    public void onInterrupt(Postcard postcard) {
                                        Log.i("aaa", "onInterrupt" + postcard.toString());
                                    }
                                });
                    }

                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * ????????????
     */
    private void onLiveTypePwd(final long roomId, final int type, String typeVal) {
        final Dialog dialog = DialogUtil.getBaseSystemDialog(mContext, R.style.dialog, R.layout.dialog_join_pwdroom, true, true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        EditText editText = dialog.findViewById(R.id.edit);
        final EditText editText1 = dialog.findViewById(R.id.edit1);
        final EditText editText2 = dialog.findViewById(R.id.edit2);
        final EditText editText3 = dialog.findViewById(R.id.edit3);
        final EditText editText4 = dialog.findViewById(R.id.edit4);
        final EditText editText5 = dialog.findViewById(R.id.edit5);
        final EditText editText6 = dialog.findViewById(R.id.edit6);
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pwd = editable.toString();
                if (pwd.length() == 0) {
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                    editText6.setText("");
                } else if (pwd.length() == 1) {
                    editText1.setText(pwd);
                    editText2.setText("");
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                    editText6.setText("");
                } else if (pwd.length() == 2) {
                    editText2.setText(pwd.substring(1, 2));
                    editText3.setText("");
                    editText4.setText("");
                    editText5.setText("");
                    editText6.setText("");
                } else if (pwd.length() == 3) {
                    editText3.setText(pwd.substring(2, 3));
                    editText4.setText("");
                    editText5.setText("");
                    editText6.setText("");
                } else if (pwd.length() == 4) {
                    editText4.setText(pwd.substring(3, 4));
                    editText5.setText("");
                    editText6.setText("");
                } else if (pwd.length() == 5) {
                    editText5.setText(pwd.substring(4, 5));
                    editText6.setText("");
                } else if (pwd.length() == 6) {
                    editText6.setText(pwd.substring(5, 6));
                    dialog.dismiss();

                    if (data.liveType == 1) {
                        joinRoom(roomId, type, pwd);
                    } else {
                        VoiceJoinRoom(roomId, type, pwd);
                    }

                }
            }
        });
    }

    private void showDiaLog(final AppHomeHallDTO bean) {
        if (bean.roomType == 2) {
            final Dialog dialog = DialogUtil.getBaseSystemDialog(mContext, R.style.dialog, R.layout.dialog_joinroom, true, true);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);
            tvTitle.setText("????????????");
            dialog.findViewById(R.id.tv_content).setVisibility(View.GONE);
            TextView tvPrice = dialog.findViewById(R.id.tv_price);
            TextView tvSure = dialog.findViewById(R.id.tv_sure);
            tvSure.setText("???????????????");
            tvPrice.setVisibility(View.VISIBLE);
            tvPrice.setText(bean.typeVal + SpUtil.getInstance().getCoinUnit() + "/???");
            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.liveType == 1) {
                        joinRoom(bean.roomId, bean.roomType, String.valueOf(bean.coin));
                    } else {
                        VoiceJoinRoom(bean.roomId, bean.roomType, String.valueOf(bean.coin));
                    }
                    dialog.dismiss();
                }
            });
        } else if (bean.roomType == 3) {
            final Dialog dialog = DialogUtil.getBaseSystemDialog(mContext, R.style.dialog, R.layout.dialog_joinroom, true, true);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);
            TextView tvSure = dialog.findViewById(R.id.tv_sure);
            tvSure.setText("???????????????");
            tvTitle.setText("????????????");
            dialog.findViewById(R.id.tv_content).setVisibility(View.GONE);
            TextView tvPrice = dialog.findViewById(R.id.tv_price);
            tvPrice.setVisibility(View.VISIBLE);
            tvPrice.setText(bean.typeVal + SpUtil.getInstance().getCoinUnit() + "/??????");
            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.liveType == 1) {
                        joinRoom(bean.roomId, bean.roomType, String.valueOf(bean.coin));
                    } else {
                        VoiceJoinRoom(bean.roomId, bean.roomType, String.valueOf(bean.coin));
                    }
                    dialog.dismiss();
                }
            });
        } else if (bean.roomType == 4) {
            final Dialog dialog = DialogUtil.getBaseSystemDialog(mContext, R.style.dialog, R.layout.dialog_joinroom, true, true);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            TextView tvTitle = dialog.findViewById(R.id.tv_title);
            tvTitle.setText("????????????");
            dialog.findViewById(R.id.tv_content).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.layoutPrice).setVisibility(View.GONE);
            TextView tvSure = dialog.findViewById(R.id.tv_sure);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvSure.getLayoutParams();
            layoutParams.setMargins(0, DpUtil.dp2px(15), 0, 0);
            tvSure.setText("???????????????");
            dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                            + HttpConstants.URL_NOBLE + "_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
                    dialog.dismiss();
                }
            });
        }
    }


    //isVideo 1 ??????????????? ???2 ???????????????
    public void showDialog(final int isVideo, final ApiUserInfo info, final ProcessResultUtil mProcessResultUtil, final Context context, final int liveType) {
        HttpApiAppUser.getMyAnchor(new HttpApiCallBack<ApiUserInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    if (retModel.anchorAuditStatus == 0 && info.role == 1) {//?????????????????????
                        final Dialog dialog = DialogUtil.getBaseDialog(context, R.style.dialog2, R.layout.dialog_call_charge, true, true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        window.setAttributes(params);
                        ((TextView) dialog.findViewById(R.id.tvText2)).setText("????????????????????????" + SpUtil.getInstance().getCoinUnit() + "???~");
                        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(View view) {

                                mProcessResultUtil.requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO
                                }, new Runnable() {
                                    @Override
                                    public void run() {
                                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(context, isVideo, info, liveType);

                                    }
                                });
                                dialog.dismiss();
                            }
                        });
                    } else {
                        mProcessResultUtil.requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO
                        }, new Runnable() {
                            @Override
                            public void run() {
                                OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(context, isVideo, info, liveType);
                            }
                        });
                    }
                }
            }
        });

    }

    //?????????????????????????????? ??????????????????
    public void closeLive() {
        HttpApiHttpLive.leaveRoomOpt(LiveConstants.ROOMID, new HttpApiCallBack<ApiLeaveRoom>() {
            @Override
            public void onHttpRet(int code, String msg, ApiLeaveRoom retModel) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
            }
        });

    }

    /*
     * ???????????????????????????????????????????????? ??????????????????
     * */
    public void voiceClose() {
        HttpApiHttpVoice.leaveVoiceRoomOpt(LiveConstants.ROOMID, new HttpApiCallBack<ApiLeaveRoom>() {
            @Override
            public void onHttpRet(int code, String msg, ApiLeaveRoom retModel) {
                if (code == 1) {
//                    LiveBundle.getInstance().removeAllListener();
                    LiveConstants.ROOMID = 0;
                    LiveConstants.ANCHORID = 0;
                    SpUtil.getInstance().put(SpUtil.ANCHOR_ID, LiveConstants.ANCHORID);
                    LiveConstants.LiveAddress = 0;
                    LiveConstants.IsRemoteAudio = false;
                    LiveConstants.isSamll = false;
                    LiveConstants.VoiceAnchorInvitation = false;
                    VoiceLiveOwnStateBean.MikeState = 1;
                    VoiceLiveOwnStateBean.IsMike = false;
                    VoiceLiveOwnStateBean.IsMute = false;
                    VoiceLiveCloudUtils.getInstance().clean();

                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, new ApiCloseLive());
                } else {
                    ToastUtil.show("HttpApiHttpVoice" + msg);
                }
            }
        });
    }


    JoinRoomCallBack joinRoomCallBack;

    public void setJoinRoomCallBack(JoinRoomCallBack join) {
        this.joinRoomCallBack = join;
    }

    public interface JoinRoomCallBack {
        public void joinSuccess(AppJoinRoomVO joinRoom);
    }

    /**
     * ???????????????????????????????????????
     */
    public interface entryRoomListener {
        void onGo();
    }

    /**
     * ????????????
     */
    private void entryRoom(long roomId, final entryRoomListener entryRoomListener) {
        if (LiveConstants.isSamll) {
            if (LiveConstants.ANCHORID != HttpClient.getUid()) {
                if (LiveConstants.ROOMID == roomId) {//????????????????????????????????????????????????????????????
                    SmallLiveRoomDialogFragment.getInstance().goBackRoom();
                } else {
                    if (mContext instanceof FragmentActivity) {
                        if (smallJoinRoomTipsDialogFragment != null) {
                            smallJoinRoomTipsDialogFragment.dismiss();
                        }
                        smallJoinRoomTipsDialogFragment = new SmallJoinRoomTipsDialogFragment();
                        smallJoinRoomTipsDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "SmallJoinRoomTipsDialogFragment");
                        smallJoinRoomTipsDialogFragment.setSmallJoinRoomTipsCallBack(new SmallJoinRoomTipsDialogFragment.SmallJoinRoomTipsCallBack() {
                            @Override
                            public void onCanle() {
                                smallJoinRoomTipsDialogFragment.dismiss();
                            }

                            @Override
                            public void onConfirm() {
                                smallJoinRoomTipsDialogFragment.dismiss();
                                SmallLiveRoomDialogFragment.getInstance().closeRoom();

                                if (entryRoomListener != null) {
                                    entryRoomListener.onGo();
                                }
                            }
                        });
                    } else {
                        // ????????? ???????????????????????????  ??????????????? (?????????????????? ??????5??? ???????????? ??????????????????)
                        if (LiveConstants.VoiceAnchorInvitation) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (entryRoomListener != null) {
                                        entryRoomListener.onGo();
                                    }
                                }
                            }, 300);
                        }
                    }
                }
            } else {
                if (LiveConstants.ROOMID == roomId) {//????????????????????????????????????????????????????????????
                    SmallLiveRoomDialogFragment.getInstance().goBackRoom();
                } else {
                    ToastUtil.show("??????????????????????????????????????????");
                }
            }
        } else {
            if (LiveConstants.ROOMID > 0) {
                if (LiveConstants.ANCHORID != HttpClient.getUid()) {
                    if (mContext instanceof FragmentActivity) {
                        if (smallJoinRoomTipsDialogFragment != null) {
                            smallJoinRoomTipsDialogFragment.dismiss();
                        }
                        smallJoinRoomTipsDialogFragment = new SmallJoinRoomTipsDialogFragment();
                        smallJoinRoomTipsDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "SmallJoinRoomTipsDialogFragment");
                        smallJoinRoomTipsDialogFragment.setSmallJoinRoomTipsCallBack(new SmallJoinRoomTipsDialogFragment.SmallJoinRoomTipsCallBack() {
                            @Override
                            public void onCanle() {
                                smallJoinRoomTipsDialogFragment.dismiss();
                            }

                            @Override
                            public void onConfirm() {
                                smallJoinRoomTipsDialogFragment.dismiss();

                                LiveConstants.isSamll = false;
                                LiveConstants.isEndLive = true;
                                HttpApiHttpVoice.leaveVoiceRoomOpt(LiveConstants.ROOMID, new HttpApiCallBack<ApiLeaveRoom>() {
                                    @Override
                                    public void onHttpRet(int code, String msg, ApiLeaveRoom retModel) {
                                        if (code == 1) {
                                            if (!ConfigUtil.getBoolValue(R.bool.useMusicOld)) {
                                                LiveMusicView.getInstance().close();
                                            }
                                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, new ApiCloseLive());

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (entryRoomListener != null) {
                                                        entryRoomListener.onGo();
                                                    }
                                                }
                                            }, 500);
                                        } else {
                                            ToastUtil.show(msg);
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        // ???????????????????????????  ??????????????? (?????????????????? ??????0.3??? ???????????? ??????????????????)
                        if (LiveConstants.VoiceAnchorInvitation) {
                            //LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, new ApiCloseLive());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (entryRoomListener != null) {
                                        entryRoomListener.onGo();
                                    }
                                }
                            }, 500);
                        }
                    }
                } else {
                    ToastUtil.show("??????????????????????????????????????????");
                }
            } else {
                if (entryRoomListener != null) {
                    entryRoomListener.onGo();
                }
            }
        }
    }

}
