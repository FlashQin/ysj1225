package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.fragment.CallRecordFragment;
import com.kalacheng.centercommon.fragment.CallSettingFragment;
import com.kalacheng.base.activty.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 封面设置
 */
@Route(path = ARouterPath.One2OneCallActivity)
public class One2OneCallActivity extends BaseActivity implements View.OnClickListener {

    private List<Fragment> mFragments = new ArrayList<>();
    TextView tvCallSet, tvCallRecord;
    private CallSettingFragment callSettingFragment;
    private CallRecordFragment callRecordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1v1call);
        initView();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }


    private void initView() {
//        setTitle("话费设置");
        setTitle("封面设置");
        callSettingFragment = new CallSettingFragment();
        callRecordFragment = new CallRecordFragment();
        mFragments.add(callSettingFragment);
        mFragments.add(callRecordFragment);
        addFragment(mFragments.get(0), R.id.fl_fragment);

        tvCallSet = findViewById(R.id.tv_call_set);
        tvCallRecord = findViewById(R.id.tv_call_record);
        tvCallSet.setOnClickListener(this);
        tvCallRecord.setOnClickListener(this);
        tvCallSet.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_call_set) {
            tvCallSet.setSelected(true);
            tvCallRecord.setSelected(false);
            showFragment(mFragments.get(0), R.id.fl_fragment);
        } else if (view.getId() == R.id.tv_call_record) {
            tvCallSet.setSelected(false);
            tvCallRecord.setSelected(true);
            showFragment(mFragments.get(1), R.id.fl_fragment);
        }
    }
}
