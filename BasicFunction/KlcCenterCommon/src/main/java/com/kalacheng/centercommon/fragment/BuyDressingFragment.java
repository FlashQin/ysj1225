package com.kalacheng.centercommon.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import com.kalacheng.centercommon.R;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.http.HttpClient;

/*
 * 购买装扮
 * */
public class BuyDressingFragment extends BaseFragment {
    private int type = 1;
    private String Url;
    private WebView mWebView;

    public BuyDressingFragment() {

    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.buy_dressing;
    }

    @Override
    protected void initView() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
        mWebView = mParentView.findViewById(R.id.webview);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        getInitView();
    }

    public void getInitView() {

        if (type == 1) {
            Url = HttpClient.getInstance().getUrl() + "/static/h5page/index.html#/byDress?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken();
        } else {
            Url = HttpClient.getInstance().getUrl() + "/static/h5page/index.html#/useDress?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken();
        }
        synCookies(Url);
        mWebView.loadUrl(Url);
    }

    /**
     * 同步Cookie
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void synCookies(String url) {
        try {
            long uid = HttpClient.getUid();
            String token = "" + HttpClient.getToken();

            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);

            String uidCookie = "_uid_=" + uid + ";path=/";
            cookieManager.setCookie(url, uidCookie);

            String tokenCookie = "_token_=" + token + ";path=/";
            cookieManager.setCookie(url, tokenCookie);

            cookieManager.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setShowed(boolean showed) {
        super.setShowed(showed);
        if (showed && mWebView != null) {
            mWebView.reload();
        }
    }
}
