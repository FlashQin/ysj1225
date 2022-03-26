package com.kalacheng.commonview.pay;

import android.app.Activity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kalacheng.busfinance.httpApi.HttpApiApiPay;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

/**
 * Created by cxf on 2017/9/22.
 */

public class WxPayBuilder {
    private Activity mActivity;
    private PayCallback mPayCallback;

    public WxPayBuilder(Activity activity) {
        mActivity = new WeakReference<>(activity).get();
        WxApiWrapper.getInstance().setAppID((String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_WX_APP_ID, ""));
        EventBus.getDefault().register(this);
    }

    public WxPayBuilder setPayCallback(PayCallback callback) {
        mPayCallback = new WeakReference<>(callback).get();
        return this;
    }

    public void pay(long tag, long ruleId) {
//        String params = "{ruleId:" + ruleId + ",terminal:'android'}";
        HttpApiApiPay.startPay(tag, ruleId, 1, new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                if (code == 1 && null != retModel) {
                    JSONObject obj = JSON.parseObject(retModel.value);
                    String partnerId = obj.getString("partnerid");
                    String prepayId = obj.getString("prepayid");
                    String packageValue = obj.getString("package");
                    String nonceStr = obj.getString("noncestr");
                    String timestamp = obj.getString("timestamp");
                    String appid = obj.getString("appid");
                    String sign = obj.getString("sign");
                    if (TextUtils.isEmpty(partnerId) ||
                            TextUtils.isEmpty(prepayId) ||
                            TextUtils.isEmpty(packageValue) ||
                            TextUtils.isEmpty(nonceStr) ||
                            TextUtils.isEmpty(timestamp) ||
                            TextUtils.isEmpty(sign)) {
                        ToastUtil.show("微信支付信息错误");
                        return;
                    }
                    PayReq req = new PayReq();
                    req.appId = appid;
                    req.partnerId = partnerId;
                    req.prepayId = prepayId;
                    req.packageValue = packageValue;
                    req.nonceStr = nonceStr;
                    req.timeStamp = timestamp;
                    req.sign = sign;
                    IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
                    if (wxApi == null) {
                        ToastUtil.show("支付失败");
                        return;
                    }
                    boolean result = wxApi.sendReq(req);
                    if (!result) {
                        ToastUtil.show("支付失败");
                    }
                }
            }
        });
    }

    public void shopPay(long tag, long orderId) {
//        String params = "{orderId:" + orderId + "}";
        HttpApiApiPay.startPay(tag, orderId, 2, new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                if (code == 1 && null != retModel) {
                    JSONObject obj = JSON.parseObject(retModel.value);
                    String partnerId = obj.getString("partnerid");
                    String prepayId = obj.getString("prepayid");
                    String packageValue = obj.getString("package");
                    String nonceStr = obj.getString("noncestr");
                    String timestamp = obj.getString("timestamp");
                    String appid = obj.getString("appid");
                    String sign = obj.getString("sign");
                    if (TextUtils.isEmpty(partnerId) ||
                            TextUtils.isEmpty(prepayId) ||
                            TextUtils.isEmpty(packageValue) ||
                            TextUtils.isEmpty(nonceStr) ||
                            TextUtils.isEmpty(timestamp) ||
                            TextUtils.isEmpty(sign)) {
                        ToastUtil.show("微信支付信息错误");
                        return;
                    }
                    PayReq req = new PayReq();
                    req.appId = appid;
                    req.partnerId = partnerId;
                    req.prepayId = prepayId;
                    req.packageValue = packageValue;
                    req.nonceStr = nonceStr;
                    req.timeStamp = timestamp;
                    req.sign = sign;
                    IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
                    if (wxApi == null) {
                        ToastUtil.show("支付失败");
                        return;
                    }
                    boolean result = wxApi.sendReq(req);
                    if (!result) {
                        ToastUtil.show("支付失败");
                    }
                }
            }
        });
    }

    /**
     * 购买VIP
     *
     * @param channelId 渠道ID
     * @param vipId     订单ID
     */
    public void buyVip(long channelId, long vipId) {
//        String params = "{vipId:" + vipId + ",terminal:'android'}";
        HttpApiApiPay.startPay(channelId, vipId, 4, new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                if (code == 1 && null != retModel) {
                    JSONObject obj = JSON.parseObject(retModel.value);
                    String partnerId = obj.getString("partnerid");
                    String prepayId = obj.getString("prepayid");
                    String packageValue = obj.getString("package");
                    String nonceStr = obj.getString("noncestr");
                    String timestamp = obj.getString("timestamp");
                    String appid = obj.getString("appid");
                    String sign = obj.getString("sign");
                    if (TextUtils.isEmpty(partnerId) ||
                            TextUtils.isEmpty(prepayId) ||
                            TextUtils.isEmpty(packageValue) ||
                            TextUtils.isEmpty(nonceStr) ||
                            TextUtils.isEmpty(timestamp) ||
                            TextUtils.isEmpty(sign)) {
                        ToastUtil.show("微信支付信息错误");
                        return;
                    }
                    PayReq req = new PayReq();
                    req.appId = appid;
                    req.partnerId = partnerId;
                    req.prepayId = prepayId;
                    req.packageValue = packageValue;
                    req.nonceStr = nonceStr;
                    req.timeStamp = timestamp;
                    req.sign = sign;
                    IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
                    if (wxApi == null) {
                        ToastUtil.show("支付失败");
                        return;
                    }
                    boolean result = wxApi.sendReq(req);
                    if (!result) {
                        ToastUtil.show("支付失败");
                    }
                }
            }
        });
    }

    //  灵活使用 （RMB购买 SVIP）
    public void startPay(long tag, long id, int type) {
//        String params = "{" + jsonKey + ":" + id + ",terminal:'android'}";
        HttpApiApiPay.startPay(tag, id, type, new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                if (code == 1 && null != retModel) {
                    JSONObject obj = JSON.parseObject(retModel.value);
                    String partnerId = obj.getString("partnerid");
                    String prepayId = obj.getString("prepayid");
                    String packageValue = obj.getString("package");
                    String nonceStr = obj.getString("noncestr");
                    String timestamp = obj.getString("timestamp");
                    String appid = obj.getString("appid");
                    String sign = obj.getString("sign");
                    if (TextUtils.isEmpty(partnerId) ||
                            TextUtils.isEmpty(prepayId) ||
                            TextUtils.isEmpty(packageValue) ||
                            TextUtils.isEmpty(nonceStr) ||
                            TextUtils.isEmpty(timestamp) ||
                            TextUtils.isEmpty(sign)) {
                        ToastUtil.show("微信支付信息错误");
                        return;
                    }
                    PayReq req = new PayReq();
                    req.appId = appid;
                    req.partnerId = partnerId;
                    req.prepayId = prepayId;
                    req.packageValue = packageValue;
                    req.nonceStr = nonceStr;
                    req.timeStamp = timestamp;
                    req.sign = sign;
                    IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
                    if (wxApi == null) {
                        ToastUtil.show("支付失败");
                        return;
                    }
                    boolean result = wxApi.sendReq(req);
                    if (!result) {
                        ToastUtil.show("支付失败");
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResponse(BaseResp resp) {
        L.e("resp---微信支付回调---->" + resp.errCode);
        if (mPayCallback != null) {
            if (0 == resp.errCode) {//支付成功
                mPayCallback.onSuccess();
            } else {//支付失败
                mPayCallback.onFailed();
            }
        }
        mActivity = null;
        mPayCallback = null;
        EventBus.getDefault().unregister(this);
    }


}
