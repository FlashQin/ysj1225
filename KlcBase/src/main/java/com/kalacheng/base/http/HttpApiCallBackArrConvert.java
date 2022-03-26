package com.kalacheng.base.http;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.base.event.TokenInvalidEvent;
import com.kalacheng.base.socket.SocketUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.List;


public class HttpApiCallBackArrConvert<T> extends StringCallback {

    HttpApiCallBackArr<T> m_callback;
    Type m_retType;

    public HttpApiCallBackArrConvert(HttpApiCallBackArr<T> callback, Type retType) {
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
        HttpRetArr retArr = null;
        List<T> _retList = null;
        try {
            String strRetJson = response.body();
            if (!TextUtils.isEmpty(strRetJson)){
                retArr = (HttpRetArr) JSON.parseObject(strRetJson, m_retType);
                Object obj = retArr.getRetArr();
                if (retArr.getCode() != 1) {
//                ToastUtil.show(retArr.getMsg());
                }
                if (retArr.getCode() == 44003) {
                    SpUtil.getInstance().clearLoginInfo();
                    SocketUtils.stopSocket();
//                ARouter.getInstance().build(HttpApiCallBackConvert.LOGIN_AROUTER).navigation();
                    EventBus.getDefault().post(new TokenInvalidEvent());
                    return;
                }
                if (obj != null) {
                    _retList = (List<T>) obj;
                }
                m_callback.onHttpRet(retArr.getCode(), retArr.getMsg(), _retList);
            }
        } catch (Exception ex) {
            Log.i("HttpOnError", ex.getMessage());
            m_callback.onHttpRet(44002, ex.getMessage(), null);
            return;
        }
    }
}
