package com.klc.zbydy.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kalacheng.commonview.pay.WxApiWrapper;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;


/**
 * 微信支付的回调页面
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WxApiWrapper.getInstance().getWxApi();
        if (api != null) {
            api.handleIntent(getIntent(), this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (api != null) {
            api.handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        EventBus.getDefault().post(resp);
        finish();
    }

}