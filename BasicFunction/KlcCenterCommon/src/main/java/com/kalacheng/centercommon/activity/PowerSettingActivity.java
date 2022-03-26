package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.busgraderight.httpApi.HttpApiNoble;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ActivityPowerSettingLayoutBinding;
import com.kalacheng.centercommon.viewmodel.PowerSettingViewModel;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.libuser.model.InvisiblePrivilegeDTO;
import com.kalacheng.libuser.model.VipPrivilegeDto;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.base.http.HttpApiCallBack;

/**
 * @author: Administrator
 * @date: 2020/8/19
 * @info: 特权设置
 */
@Route(path = ARouterPath.PowerSetting)
public class PowerSettingActivity extends BaseMVVMActivity<ActivityPowerSettingLayoutBinding, PowerSettingViewModel> {

    private InvisiblePrivilegeDTO model;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_power_setting_layout;
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    public void initData() {
        setTitle(getResources().getString(R.string.mine_power_setting_title));

        if (ConfigUtil.getBoolValue(R.bool.powerSettingLive)){
            binding.ll.setVisibility(View.GONE);
            binding.line.setVisibility(View.GONE);
        }

        getPower();
        getBroadcast();
        //贡献榜
        binding.setCallbackContribute(new OnBeanCallback() {
            @Override
            public void onClick(Object bean) {
                if (model != null && model.hasPrivilege == 0) {
                    ToastUtil.show("【" + model.lowestLv + "】以上才能开启，请先开通贵族");
                    return;
                }
                if (model != null && model.hasPrivilege == -1) {
                    ToastUtil.show("暂无权限修改");
                    return;
                }
                if (model.devoteShow == 0) {
                    shopDialog(model.chargeShow, (int) bean, model.joinRoomShow, getResources().getString(R.string.mine_power_setting_contribute_tips));
                } else {
                    setPower(model.chargeShow, (int) bean, model.joinRoomShow);
                }
            }
        });
        //直播间
        binding.setCallbackLive(new OnBeanCallback() {
            @Override
            public void onClick(Object bean) {
                if (model != null && model.hasPrivilege == 0) {
                    ToastUtil.show("【" + model.lowestLv + "】以上才能开启，请先开通贵族");
                    return;
                }
                if (model != null && model.hasPrivilege == -1) {
                    ToastUtil.show("暂无权限修改");
                    return;
                }
                if (model.joinRoomShow == 0) {
                    shopDialog(model.chargeShow, model.devoteShow, (int) bean, getResources().getString(R.string.mine_power_setting_live_tips));
                } else {
                    setPower(model.chargeShow, model.devoteShow, (int) bean);
                }
            }
        });
        //充值
        binding.setCallbackRecharge(new OnBeanCallback() {
            @Override
            public void onClick(Object bean) {
                if (model != null && model.hasPrivilege == 0) {
                    ToastUtil.show("【" + model.lowestLv + "】以上才能开启，请先开通贵族");
                    return;
                }
                if (model != null && model.hasPrivilege == -1) {
                    ToastUtil.show("暂无权限修改");
                    return;
                }
                if (model.chargeShow == 0) {
                    shopDialog((int) bean, model.devoteShow, model.joinRoomShow, getResources().getString(R.string.mine_power_setting_recharge_tips));
                } else {
                    setPower((int) bean, model.devoteShow, model.joinRoomShow);
                }

            }
        });

        // 关闭全站广播
        binding.setCallbackAllBroadcast(new OnBeanCallback() {
            @Override
            public void onClick(Object bean) {
                if (binding.getVipbean().broadCast == -1) {
                    ToastUtil.show("沒有全站广播特权");
                    return;
                }
                if (binding.getVipbean().broadCast == 1) {
                    shopBroadcastDialog((int) bean, getResources().getString(R.string.mine_power_setting_all_broadcast_close));
                } else {
                    setBroadcast((int) bean);
                }
            }
        });

    }

    private void shopDialog(final int chargeShow, final int devoteShow, final int joinRoomShow, final String content) {
        DialogUtil.showSimpleDialog(mContext, "提示", content, "开启", true, true, new DialogUtil.SimpleCallback() {
            @Override
            public void onConfirmClick() {
                setPower(chargeShow, devoteShow, joinRoomShow);
            }

            @Override
            public void onConfirmClick(String input) {

            }

            @Override
            public void onCancelClick() {

            }
        });
    }

    private void shopBroadcastDialog(final int broadcast, String content) {
        DialogUtil.showSimpleDialog(mContext, "提示", content, "关闭", true, true, new DialogUtil.SimpleCallback() {
            @Override
            public void onConfirmClick() {
                setBroadcast(broadcast);
            }

            @Override
            public void onConfirmClick(String input) {

            }

            @Override
            public void onCancelClick() {

            }
        });
    }

    // 获取特权设置状态
    private void getPower() {
        HttpApiAppUser.vipInvisiblePrivilege(new HttpApiCallBack<InvisiblePrivilegeDTO>() {
            @Override
            public void onHttpRet(int code, String msg, InvisiblePrivilegeDTO retModel) {
                if (code == 1) {
                    if (retModel.hasPrivilege == 0 || retModel.hasPrivilege == -1) {
                        retModel.chargeShow = 0;
                        retModel.devoteShow = 0;
                        retModel.joinRoomShow = 0;
                    }
                    binding.setBean(retModel);
                    model = retModel;
                } else {
                    ToastUtil.show(msg + "");
                }
            }
        });
    }

    // 修改特权状态

    /**
     * 贵族隐身特权修改
     *
     * @param chargeShow   充值隐身 0隐藏1显示
     * @param devoteShow   贡献榜排行隐身 0隐藏1显示
     * @param joinRoomShow 进入直播间隐身 0隐藏1显示
     */
    private void setPower(int chargeShow, int devoteShow, int joinRoomShow) {
        HttpApiAppUser.VipPrivilege(chargeShow, devoteShow, joinRoomShow, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    getPower();
                } else {
                    ToastUtil.show(msg + "");
                }
            }
        });
    }

    // 获取全站广播
    private void getBroadcast() {
        HttpApiNoble.vipPrivilegeShow(new HttpApiCallBack<VipPrivilegeDto>() {
            @Override
            public void onHttpRet(int code, String msg, VipPrivilegeDto retModel) {
                if (code == 1) {
                    binding.setVipbean(retModel);
                } else {
                    ToastUtil.show(msg + "");
                }
            }
        });
    }

    // 设置全站广播
    private void setBroadcast(int broadcast) {
        HttpApiPublicLive.liveVipPrivilege(broadcast, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    getBroadcast();
                } else {
                    ToastUtil.show(msg + "");
                }
            }
        });
    }

}