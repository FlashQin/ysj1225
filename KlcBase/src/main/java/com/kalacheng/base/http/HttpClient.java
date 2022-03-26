package com.kalacheng.base.http;


import android.app.Application;

import com.kalacheng.util.utils.AppUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by cxf on 2018/9/17.
 */

public class HttpClient {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final int TIMEOUT = 20000;
    private static HttpClient sInstance;
    private OkHttpClient mOkHttpClient;
    private String mLanguage;//语言
    private String mUrl;
    public static final String LANGUAGE = "language";

    public static long getUid() {
        return (long) SpUtil.getInstance().getSharedPreference(SpUtil.UID, 0l);
    }

    public static String getToken() {
        return (String) SpUtil.getInstance().getSharedPreference(SpUtil.TOKEN, "");
    }


    public static HttpClient getInstance() {
        if (sInstance == null) {
            synchronized (HttpClient.class) {
                if (sInstance == null) {
                    sInstance = new HttpClient();
                }
            }
        }
        return sInstance;
    }

    public void init(Application app) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        builder.retryOnConnectionFailure(true);
        //信任所有服务器地址
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                //设置为true
                return true;
            }
        });
        //创建管理器
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        }};
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            //为OkHttpClient设置sslSocketFactory
            builder.sslSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }

        //输出HTTP请求 响应信息
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("http");
        loggingInterceptor.setColorLevel(Level.INFO);
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        mOkHttpClient = builder.build();

        OkGo.getInstance().init(app)
                .setOkHttpClient(mOkHttpClient)
                .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(0);


    }

    public GetRequest get(String url, String tag) {
        return OkGo.get(url)
                .headers("Connection", "keep-alive")
                .tag(tag)
                .params(HttpClient.LANGUAGE, mLanguage);
    }

    public PostRequest post(String serviceName, String tag) {
        return OkGo.post(mUrl + serviceName)
                .headers("Connection", "keep-alive")
                .tag(tag)
                .params(HttpClient.LANGUAGE, mLanguage);
    }

    public PostRequest postJson(String serviceName, String json, String tag) {

        RequestBody body = RequestBody.create(JSON, json);

        return OkGo.post(mUrl + serviceName)
                .headers("Connection", "keep-alive")
                .tag(tag)
                .upRequestBody(body)
                .params(HttpClient.LANGUAGE, mLanguage);
    }

    public void cancel(String tag) {
        OkGo.cancelTag(mOkHttpClient, tag);
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public static String getOSType() {
        return "android";
    }

    public static String getOSVersion() {
        return AppUtil.getSystemVersion();
    }

    public static String getOSInfo() {
        return "";
    }

    public static String urlEncode(String paramString) {
        if (paramString == null || paramString.equals("")) {
            L.e("toURLEncoded error:" + paramString);
            return "";
        }
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception localException) {
            L.e("toURLEncoded error:" + paramString);
        }

        return "";
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
}
