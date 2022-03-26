package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.widget.CheckBox;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.activty.BaseActivity;

@Route(path = ARouterPath.MsgSetting)
public class MsgSettingActivity extends BaseActivity {

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_setting);

        getInitView();
    }

    public void getInitView(){
        CheckBox msgPushCb_voice = findViewById(R.id.msgPushCb_voice);
        CheckBox msgPushCb = findViewById(R.id.msgPushCb);
    }
}
