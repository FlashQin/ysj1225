package com.kalacheng.commonview.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiH5OnlineMallController;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.dialog.RewardRecordSelectDialog;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.commonview.pay.AliPayBuilder;
import com.kalacheng.commonview.pay.PayCallback;
import com.kalacheng.commonview.pay.WxPayBuilder;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.view.BubblePopupWindow;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by cxf on 2018/9/25.
 */
@Route(path = ARouterPath.WebActivity)
public class WebViewActivity extends BaseActivity {
    private static String TAG = WebViewActivity.class.getSimpleName();

    @Autowired(name = ARouterValueNameConfig.WEBURL)
    public String Url;
    @Autowired(name = ARouterValueNameConfig.WebTitleHide)
    public boolean mHideTitle;//隐藏WebViewActivity标题栏
    @Autowired(name = ARouterValueNameConfig.WebActivityType)
    public int mActivityType;//0 默认；1 佣金明细；2 商城或背包；3 商家收入明细；4 等级特权
    public boolean showCloseButton;//是否显示关闭按钮（目前只有“贵族中心”显示）

    private TextView tvTitle;
    private TextView tvOther;
    private ImageView ivRight;
    private ProgressBar mProgressBar;
    private WebView mWebView;
    private BubblePopupWindow bubbleView;

    private final int CHOOSE = 100;//Android 5.0以下的
    private final int CHOOSE_ANDROID_5 = 200;//Android 5.0以上的
    private ValueCallback<Uri> mValueCallback;
    private ValueCallback<Uri[]> mValueCallback2;

    private Dialog uploadDialog;
    private ProcessImageUtil mImageUtil;
    private Disposable uploadImgDisposable;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ARouter.getInstance().inject(this);
        initViews();
        initData();
        initListeners();

        setResult(99);
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews() {
        uploadDialog = DialogUtil.loadingDialog(this, R.style.dialog2, R.layout.dialog_loading, false, false, "上传中");
        mImageUtil = new ProcessImageUtil(this);
        tvTitle = findViewById(R.id.titleView);
        tvOther = findViewById(R.id.tvOther);
        ivRight = findViewById(R.id.iv_right);
        mProgressBar = findViewById(R.id.progressbar);
        mProgressBar.setProgressDrawable(getDrawable(R.drawable.bg_horizontal_progressbar));

        mWebView = new WebView(WebViewActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);
        ((LinearLayout) findViewById(R.id.rootView)).addView(mWebView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaScriptObject(this), "android");
        mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                tvTitle.setText(view.getTitle());
                if (mActivityType == 2) {
                    if (view.getTitle().equals("我的背包")) {
                        tvOther.setVisibility(View.VISIBLE);
                        tvOther.setTextColor(Color.parseColor("#925eff"));
                        tvOther.setText("购买更多");
                    } else {
                        tvOther.setVisibility(View.GONE);
                    }
                }
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setProgress(newProgress);
                }
            }

            //以下是在各个Android版本中 WebView调用文件选择器的方法
            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                openImageChooserActivity(valueCallback);
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                openImageChooserActivity(valueCallback);
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                openImageChooserActivity(valueCallback);
            }

            // For Android >= 5.0
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mValueCallback2 = filePathCallback;
                Intent intent = fileChooserParams.createIntent();
                startActivityForResult(intent, CHOOSE_ANDROID_5);
                return true;
            }

        });

        mImageUtil.setImageResultCallback(new ImageResultCallback() {
            @Override
            public void beforeCamera() {

            }

            @Override
            public void onSuccess(final File file) {
                upImage(file);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    private void initData() {
        L.e(TAG, "Url == " + Url);

        if (mHideTitle) {
            findViewById(R.id.layoutWebViewTitle).setVisibility(View.GONE);
        }
        if (mActivityType == 1) {
            tvOther.setVisibility(View.VISIBLE);
            String tvOtherStr = "";
            String changeTypeStr = "";
            if (ConfigUtil.getIntValue(R.integer.commissionSelectType) == 0 || ConfigUtil.getIntValue(R.integer.commissionSelectType) == 1) {
                tvOtherStr = "注册佣金";
                changeTypeStr = "&changeType=50";
            } else if (ConfigUtil.getIntValue(R.integer.commissionSelectType) == 2) {
                tvOtherStr = "充值佣金";
                changeTypeStr = "&changeType=51";
            }

            tvOther.setText(tvOtherStr);
            Url = HttpClient.getInstance().getUrl() + "/api/h5/rewardRecord?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=15&pageIndex=0&anchorId=" + HttpClient.getUid() + changeTypeStr;
        } else if (mActivityType == 3) {
            ivRight.setImageResource(R.mipmap.icon_income_more);
            ivRight.setVisibility(View.VISIBLE);
            Url = createUrl("1");
        } else if (mActivityType == 4) {
            tvOther.setVisibility(View.VISIBLE);
            tvOther.setText("等级说明");
        }
        synCookies(Url);
        Log.e("klc>>>WebView:", Url);
        mWebView.loadUrl(Url);
        showCloseButton = Url.contains(HttpConstants.URL_NOBLE);
    }

    private void initListeners() {
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivityType == 1) {
                    RewardRecordSelectDialog rewardRecordSelectDialog = new RewardRecordSelectDialog();
                    rewardRecordSelectDialog.setRewardRecordSelectListener(new RewardRecordSelectDialog.RewardRecordSelectListener() {
                        @Override
                        public void onSelect(int type, String name) {
                            tvOther.setText(name);
                            Url = HttpClient.getInstance().getUrl() + "/api/h5/rewardRecord?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=15&pageIndex=0&anchorId=" + HttpClient.getUid() + "&changeType=" + type;
                            mWebView.loadUrl(Url);
                        }
                    });
                    rewardRecordSelectDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "RewardRecordSelectDialog");
                } else if (mActivityType == 2) {
                    mWebView.loadUrl(HttpClient.getInstance().getUrl() + "/api/h5/getAppUserVip?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=10&pageIndex=0&anchorId=" + HttpClient.getUid());
                } else if (mActivityType == 4) {
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                            + "/api/h5/gradeDesr?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&type=1").navigation();
                }
            }
        });

        if (mActivityType == 3) {
            initBubbleView();
            ivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    showPop();
                }
            });

        }
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

    private void openImageChooserActivity(ValueCallback<Uri> valueCallback) {
        mValueCallback = valueCallback;
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, WordUtil.getString(R.string.choose_flie)), CHOOSE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case CHOOSE://5.0以下选择图片后的回调
                processResult(resultCode, intent);
                break;
            case CHOOSE_ANDROID_5://5.0以上选择图片后的回调
                processResultAndroid5(resultCode, intent);
                break;
        }
    }

    private void processResult(int resultCode, Intent intent) {
        if (mValueCallback == null) {
            return;
        }
        if (resultCode == RESULT_OK && intent != null) {
            Uri result = intent.getData();
            mValueCallback.onReceiveValue(result);
        } else {
            mValueCallback.onReceiveValue(null);
        }
        mValueCallback = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void processResultAndroid5(int resultCode, Intent intent) {
        if (mValueCallback2 == null) {
            return;
        }
        if (resultCode == RESULT_OK && intent != null) {
            mValueCallback2.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
        } else {
            mValueCallback2.onReceiveValue(null);
        }
        mValueCallback2 = null;
    }

    @Override
    public void onBackPressed() {
//        if (showCloseButton) {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            setResult(RESULT_OK);
            finish();
        }
//        } else {
//            setResult(RESULT_OK);
//            finish();
//        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
        }
        if (uploadImgDisposable != null) {
            uploadImgDisposable.dispose();
        }
        super.onDestroy();
    }

    private String createUrl(String type) {
        return HttpClient.getInstance().getUrl() + "/api/business/incomeList?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=15&pageIndex=0&type=" + type;
    }

    private void initBubbleView() {
        if (bubbleView == null) {
            bubbleView = new BubblePopupWindow(this);
            View view = LayoutInflater.from(this).inflate(R.layout.popu_more_income, bubbleView.getBubbleView(), false);
            TextView tvAll = view.findViewById(R.id.tv_all);
            tvAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Url = createUrl("1");
                    mWebView.loadUrl(Url);
                    bubbleView.dismiss();
                }
            });
            TextView tvDay7 = view.findViewById(R.id.tv_day_7);
            tvDay7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Url = createUrl("1");
                    mWebView.loadUrl(Url);
                    bubbleView.dismiss();
                }
            });
            TextView tvDay15 = view.findViewById(R.id.tv_day_15);
            tvDay15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Url = createUrl("1");
                    mWebView.loadUrl(Url);
                    bubbleView.dismiss();
                }
            });
            TextView tvMonth1 = view.findViewById(R.id.tv_month_1);
            tvMonth1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Url = createUrl("1");
                    mWebView.loadUrl(Url);
                    bubbleView.dismiss();
                }
            });
            TextView tvMonth3 = view.findViewById(R.id.tv_month_3);
            tvMonth3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Url = createUrl("1");
                    mWebView.loadUrl(Url);
                    bubbleView.dismiss();
                }
            });
            bubbleView.setBubbleView(view);
        }
    }

    private void showPop() {
        bubbleView.show(ivRight, Gravity.BOTTOM, 320);
    }


    /**
     * 上传图片
     */
    private void upImage(final File imgFile) {
        if (imgFile == null) {
            return;
        }
        if (uploadDialog != null) {
            uploadDialog.show();
        }
        uploadImgDisposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                UploadUtil.getInstance().uploadPicture(1, imgFile, new PictureUploadCallback() {
                    @Override
                    public void onSuccess(String imgStr) {
                        if (!TextUtils.isEmpty(imgStr)) {
                            emitter.onNext(imgStr);
                        } else {
                            if (uploadDialog != null && uploadDialog.isShowing()) {
                                uploadDialog.dismiss();
                            }
                            ToastUtil.show("上传失败");
                        }
                    }

                    @Override
                    public void onFailure() {
                        if (uploadDialog != null && uploadDialog.isShowing()) {
                            uploadDialog.dismiss();
                        }
                        ToastUtil.show("上传失败");
                    }
                });
//                emitter.onNext(HuaWeiYun.getInstance().upload(imgFile, HuaWeiYun.IMAGE_PATH));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String url) throws Exception {
                if (uploadDialog != null && uploadDialog.isShowing()) {
                    uploadDialog.dismiss();
                }
                if (!TextUtils.isEmpty(url)) {
                    mWebView.evaluateJavascript("callbackImages(\"" + url + "\")", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                } else {
                    ToastUtil.show("上传图片失败");
                }
            }
        });
    }

    /**
     * JS回调
     */
    public class JavaScriptObject {
        private Context context;

        public JavaScriptObject(Activity activity) {
            context = activity;
        }

        @JavascriptInterface
        public void jsCallAndroid() {//我的账户
            if (CheckDoubleClick.isFastDoubleClick()) {
                return;
            }
            ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
        }

        @JavascriptInterface
        public void AddImg(final boolean params) {//选择图片
            if (CheckDoubleClick.isFastDoubleClick()) {
                return;
            }
            DialogUtil.showStringArrayDialog(context, new Integer[]{
                    R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onItemClick(String text, int tag) {
                    if (tag == R.string.camera) {
                        mImageUtil.getImageByCamera(params);
                    } else if (tag == R.string.alumb) {
                        mImageUtil.getImageByAlbumCustom(params);
                    }
                }
            });
        }

        @JavascriptInterface
        public void paymentVip(String params) {//购买贵族
            if (CheckDoubleClick.isFastDoubleClick()) {
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(params);
                String payChannel = jsonObject.getString("payChannel");
                long id = jsonObject.getLong("id");
                int grade = jsonObject.getInt("grade");

                if ("1".equals(payChannel)) {//支付宝
                    AliPayBuilder aliPayBuilder = new AliPayBuilder((Activity) mContext);
                    aliPayBuilder.setPayCallback(new PayCallback() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.show("支付成功");
                            if (mWebView != null && !TextUtils.isEmpty(Url)) {
                                mWebView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mWebView.loadUrl(Url);
                                        //EventBus.getDefault().post("vipSuccess");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailed() {
                            ToastUtil.show("支付失败");
                        }
                    });
                    aliPayBuilder.buyVip(Long.parseLong(payChannel), id);
                } else if ("2".equals(payChannel)) {//微信
                    WxPayBuilder builder = new WxPayBuilder((Activity) mContext);
                    builder.setPayCallback(new PayCallback() {
                        @Override
                        public void onSuccess() {
                            ToastUtil.show("支付成功");
                            if (mWebView != null && !TextUtils.isEmpty(Url)) {
                                mWebView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mWebView.loadUrl(Url);
                                        //EventBus.getDefault().post("vipSuccess");
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailed() {
                            ToastUtil.show("支付失败");
                        }
                    });
                    builder.buyVip(Long.parseLong(payChannel), id);
                } else if ("4".equals(payChannel)) {//金币
                    HttpApiH5OnlineMallController.buyNoble(grade, id, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                ToastUtil.show("支付成功");
                                if (mWebView != null && !TextUtils.isEmpty(Url)) {
                                    mWebView.loadUrl(Url);
                                    //EventBus.getDefault().post("vipSuccess");
                                }
                            } else {
                                ToastUtil.show(msg);
                            }
                        }
                    });
                } else {
                    ToastUtil.show("请选择支付方式");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
