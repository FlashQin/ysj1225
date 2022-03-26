package com.kalacheng.centercommon.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ActivitySettingAppBinding;
import com.kalacheng.centercommon.viewmodel.SettingAppViewModel;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.commonview.utils.SmallLiveRoomDialogFragment;
import com.kalacheng.commonview.utils.VersionUtil;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.libuser.model.ApiVersion;
import com.kalacheng.base.socket.SocketUtils;
import com.kalacheng.util.utils.AppUtil;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.GlideCatchUtil;

import java.io.File;

/**
 * 设置 【APP自定义】
 */
@Route(path = ARouterPath.SettingApp)
public class SettingAppActivity extends BaseMVVMActivity<ActivitySettingAppBinding, SettingAppViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_setting_app;
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();


        binding.phoneTv.setText((StringUtil.isEmpty(apiUserInfo.mobile) || (!StringUtil.isEmpty(apiUserInfo.mobile) && apiUserInfo.mobile.equals("-1"))) ? "" : apiUserInfo.mobile);
    }

    ApiUserInfo apiUserInfo;

    @Override
    public void initData() {
        apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);

        if (ConfigUtil.getBoolValue(R.bool.showDistanceCb)) {
            //差异显示 【男朋友】显示 “是否显示距离”
            binding.distanceCb.setVisibility(View.VISIBLE);
            binding.distanceCb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpApiAppUser.updateLocation(binding.distanceCb.isChecked() ? 0 : 1, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (!TextUtils.isEmpty(msg)) {
                                ToastUtil.show(msg);
                            }
                            if (code == 1) {

                            } else {
                                binding.distanceCb.setChecked(!binding.distanceCb.isChecked());
                            }
                        }
                    });
                }
            });

            HttpApiAppUser.getLocation(new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        binding.distanceCb.setChecked(retModel.no_use.equals("0"));
                    }
                }
            });
        }


        //是否隐藏位置
        binding.positioningCb.setChecked(apiUserInfo.whetherEnablePositioningShow == 1);
        binding.positioningCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否开启定位显示 0:未开启 1:开启
                HttpApiAppUser.upPositioningShow(binding.positioningCb.isChecked() ? 1 : 0, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtil.show(msg);
                        }

                        if (code == 1) {
                            apiUserInfo.whetherEnablePositioningShow = binding.positioningCb.isChecked() ? 1 : 0;

                            SpUtil.getInstance().putModel(SpUtil.USER_INFO, apiUserInfo);
                        } else {
                            binding.positioningCb.setChecked(!binding.positioningCb.isChecked());
                        }
                    }
                });
            }
        });


        binding.msgSettingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.MsgSetting).navigation();
            }
        });

        binding.aboutUsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.AboutUsActivity).navigation();
            }
        });

        binding.cacheSizeTv.setText(getCacheSize());
        binding.cacheSizeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                clearCache();
            }
        });

        //账号注销
        binding.accountCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.AccountCancellationActivity).navigation();

            }
        });

        binding.versionNameTv.setText(AppUtil.getVersionName());
        binding.versionNameRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                getUpData();
            }
        });


        //绑定手机号 或者 修改手机号
        binding.phoneRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;


                ARouter.getInstance().build(ARouterPath.RegisterActivity)
                        .withInt(ARouterValueNameConfig.FindPwdOrRegister, isBindingPhone() ? APPProConfig.MODIFY_PHONE : APPProConfig.BINDING_PHONE)
                        .withString(ARouterValueNameConfig.FROM_PAGE, ARouterPath.SettingApp)
                        .navigation();
            }
        });

        binding.changePasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                ARouter.getInstance().build(ARouterPath.ChangePassword).navigation();
            }
        });


    }

    /**
     * 是否 绑定 手机号
     *
     * @return true 已经绑定
     */
    private boolean isBindingPhone() {
        ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);

        if (apiUserInfo == null) {
            apiUserInfo = new ApiUserInfo();
        }

        return !StringUtil.isEmpty(apiUserInfo.mobile) && (StringUtil.isEmpty(apiUserInfo.mobile) || !apiUserInfo.mobile.equals("-1"));
    }


    /**
     * 获取缓存
     */
    private String getCacheSize() {
        return GlideCatchUtil.getInstance().getCacheSize();
    }

    /**
     * 清除缓存
     */
    private Handler mHandler;

    private void clearCache() {
        final Dialog dialog = DialogUtil.loadingDialog(mContext, com.kalacheng.centercommon.R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, getString(com.kalacheng.centercommon.R.string.setting_clear_cache_ing));
        dialog.show();
        GlideCatchUtil.getInstance().clearImageAllCache();
        File gifGiftDir = new File(APPProConfig.GIF_PATH);
        if (gifGiftDir.exists() && gifGiftDir.length() > 0) {
            gifGiftDir.delete();
        }
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                binding.cacheSizeTv.setText(getCacheSize());
                ToastUtil.show(com.kalacheng.centercommon.R.string.setting_clear_cache);
            }
        }, 2000);
    }

    /*
     * 判断是否需要更新
     * */
    public void getUpData() {
        HttpApiAppUser.version_control(1, AppUtil.getVersionCode(), new HttpApiCallBack<ApiVersion>() {
            @Override
            public void onHttpRet(int code, String msg, ApiVersion retModel) {
                if (code == 1) {
                    if (retModel.isConstraint == 0) {
                        VersionUtil.showAppUpdateDialog(mContext, retModel.versionNo, retModel.des, retModel.url, false);
                    } else if (retModel.isConstraint == 1) {
                        VersionUtil.showAppUpdateDialog(mContext, retModel.versionNo, retModel.des, retModel.url, true);
                    } else {
                        ToastUtil.show("暂无更新");
                    }
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    //退出登录
    public void OnClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == com.kalacheng.centercommon.R.id.tv_login_out) {
            logout();
        }
    }

    /**
     * 退出登录
     */
    private void logout() {

        HttpApiAppUser.logout(new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                if (code == 1) {
                    SpUtil.getInstance().clearLoginInfo();
                    SocketUtils.stopSocket();
                    ImMessageUtil.getInstance().logoutEMClient();
                    if (LiveConstants.isSamll) {
                        SmallLiveRoomDialogFragment.getInstance().closeRoom();
                    }

//                    /*------ 推送(退出登入注销对应推送别名) S -----------------------------------------------------------------*/
//                    //退出登录 注销推送别名
//                    AliasEvent alias = SpUtil.getInstance().getModel(SpUtil.USER_ALIAS, AliasEvent.class);
//                    if (null != alias && null != alias.getAlias() && alias.getAlias().length() > 0) {
//                        //先注销 之前的别名
//                        PushRegisterSet.unsetAlias(BaseApplication.getInstance(), alias.getAlias());
//                    }
//                    /*------ 推送(退出登入注销对应推送别名) E -----------------------------------------------------------------*/

                    //SocketService.stopService(SettingActivity.this);
                    ARouter.getInstance().build(ARouterPath.LoginActivity).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                            navigation(SettingAppActivity.this, new NavigationCallback() {
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
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
