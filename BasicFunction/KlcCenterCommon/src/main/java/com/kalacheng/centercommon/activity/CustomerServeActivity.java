package com.kalacheng.centercommon.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.util.utils.QQUtil;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.SystemUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;

/*
* 客户服务
* */
@Route(path = ARouterPath.CustomerServeActivity)
public class CustomerServeActivity extends BaseActivity {

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_serve);
        getInitView();
    }

    public void getInitView(){
        setTitle("联系客服");

        //手机
        RelativeLayout CustomerServe_phoneRe = findViewById(R.id.CustomerServe_phoneRe);
        TextView CustomerServe_phoneNum = findViewById(R.id.CustomerServe_phoneNum);
        final String hotLine = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_HOT_LINE, "");
        CustomerServe_phoneNum.setText(hotLine);
        CustomerServe_phoneRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(hotLine)) {
                    ToastUtil.show("暂未设置，请稍候拨打");
                } else {
                    SystemUtils.dial(hotLine);
                }
            }
        });

        //qq
        RelativeLayout CustomerServe_qqRe = findViewById(R.id.CustomerServe_qqRe);
        TextView CustomerServe_qqNum = findViewById(R.id.CustomerServe_qqNum);

        final String qq = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_HOT_QQ, "");
        CustomerServe_qqNum.setText(qq);

        CustomerServe_qqRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(qq)) {
                    ToastUtil.show("暂未设置");
                } else {
                    QQUtil.jumpToQQ((Activity) mContext,qq);
                }
            }
        });

        //WX
        TextView CustomerServe_wxNum = findViewById(R.id.CustomerServe_wxNum);
        ImageView CustomerServe_Image = findViewById(R.id.CustomerServe_Image);
        RelativeLayout CustomerServe_wxRe = findViewById(R.id.CustomerServe_wxRe);

        final String wx = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_HOT_WX, "");
        final String wxcode = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_HOT_WXCODE, "");
        CustomerServe_wxNum.setText(wx);
        ImageLoader.display(wxcode,CustomerServe_Image,R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        CustomerServe_wxRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringUtil.CopyText(mContext, wxcode);
            }
        });
    }
}
