package com.kalacheng.base.http;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.base.event.AccountDisableEvent;
import com.kalacheng.base.event.TokenInvalidEvent;
import com.kalacheng.base.socket.SocketUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class HttpApiCallBackConvert<T> extends StringCallback {
    //登录界面
    public static final String LOGIN_AROUTER = "/login/LoginActivity";

    HttpApiCallBack<T> m_callback;
    Type m_retType;

    public HttpApiCallBackConvert(HttpApiCallBack<T> callback, Type retType) {
        m_retType = retType;
        m_callback = callback;
    }

    @Override
    public void onError(Response<String> response) {
        if (response.getRawResponse() != null) {
            Log.i("HttpOnError", response.getRawResponse().toString());
        }
        Log.i("HttpOnError", response.getException().toString());
//        ToastUtil.show("网络请求失败");
        m_callback.onHttpRet(44001, "", null);
    }

    @Override
    public void onSuccess(Response<String> response) {
        T _retObj = null;
        HttpRet retObj = null;
        try {
            String strRetJson = response.body();
            if (!TextUtils.isEmpty(strRetJson)){
                retObj = (HttpRet) JSON.parseObject(strRetJson, m_retType);
                Object obj = retObj.getObj();
                if (obj != null) {
                    _retObj = (T) obj;
                }
                if (retObj.getCode() != 1) {
//                ToastUtil.show(retObj.getMsg());
                }
                if (retObj.getCode() == 44003) {
                    SpUtil.getInstance().clearLoginInfo();
                    SocketUtils.stopSocket();
//                ARouter.getInstance().build(LOGIN_AROUTER).navigation();
                    EventBus.getDefault().post(new TokenInvalidEvent());
                    return;
                } else if (retObj.getCode() == 7001) {//账号被禁用
                    AccountDisableEvent accountDisableEvent = new AccountDisableEvent();
                    JSONObject jsonObject = new JSONObject(strRetJson);
                    String msg = jsonObject.getString("msg");
                    accountDisableEvent.obj = msg;
                    EventBus.getDefault().post(accountDisableEvent);
                    return;
                }
                m_callback.onHttpRet(retObj.getCode(), retObj.getMsg(), _retObj);
            }
        } catch (Exception ex) {
            Log.i("HttpOnError", ex.getMessage());
            m_callback.onHttpRet(44002, ex.getMessage(), null);
            return;
        }

    }
}
