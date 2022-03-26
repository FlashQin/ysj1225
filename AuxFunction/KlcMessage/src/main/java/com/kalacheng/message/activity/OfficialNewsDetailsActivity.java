package com.kalacheng.message.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiOfficialNews;
import com.kalacheng.libuser.model.AppOfficialNewsDTO;
import com.kalacheng.message.R;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.http.HttpApiCallBackArr;

import java.util.List;

/*
* 消息详情
* */
@Route(path = ARouterPath.OfficialNewsDetailsActivity)
public class OfficialNewsDetailsActivity extends BaseActivity {

    @Autowired(name = "id")
    public long id;
    @Autowired(name = "content")
    String content;

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.official_news_details);
        getInitView();
    }

    public void getInitView(){
        setTitle("官方消息详情");

        getLook();
    }

    private void setContent(String content){
        WebView webView = findViewById(R.id.webview);
        webView.loadDataWithBaseURL(null, getHtmlData(content), "text/html", "utf-8", null);
    }

    //查看消息  content是后台返回的h5标签
    public void getLook(){
        HttpApiOfficialNews.messageViewed(id, new HttpApiCallBack<AppOfficialNewsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, AppOfficialNewsDTO retModel) {
                if (code == 1 && null != retModel){
                    setContent(retModel.content);
                }
            }
        });
    }

    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}

