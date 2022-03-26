package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.AppGradePrivilegeAdapter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;

/**
 * 等级特权
 */
@Route(path = ARouterPath.MyPrivilegeActivity)
public class MyPrivilegeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvUserPrivilegeTitle;
    private RecyclerView rvUserPrivilege;
    private TextView tvWealthPrivilegeTitle;
    private RecyclerView rvWealthPrivilege;
    private TextView tvNoblePrivilegeTitle;
    private RecyclerView rvNoblePrivilege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_privilege);
        initView();
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView() {
        setTitle("等级特权");
        TextView tvOther = findViewById(R.id.tvOther);
        tvOther.setVisibility(View.VISIBLE);
        tvOther.setOnClickListener(this);
        tvOther.setText("等级说明");

        tvUserPrivilegeTitle = findViewById(R.id.tvUserPrivilegeTitle);
        rvUserPrivilege = findViewById(R.id.recyclerView_userPrivilege);
        tvWealthPrivilegeTitle = findViewById(R.id.tvWealthPrivilegeTitle);
        rvWealthPrivilege = findViewById(R.id.recyclerView_wealthPrivilege);
        tvNoblePrivilegeTitle = findViewById(R.id.tvNoblePrivilegeTitle);
        rvNoblePrivilege = findViewById(R.id.recyclerView_noblePrivilege);
        rvUserPrivilege.setLayoutManager(new GridLayoutManager(this, 4));
        rvWealthPrivilege.setLayoutManager(new GridLayoutManager(this, 4));
        rvNoblePrivilege.setLayoutManager(new GridLayoutManager(this, 4));
        rvUserPrivilege.setHasFixedSize(true);
        rvUserPrivilege.setNestedScrollingEnabled(false);
        rvWealthPrivilege.setHasFixedSize(true);
        rvWealthPrivilege.setNestedScrollingEnabled(false);
        rvNoblePrivilege.setHasFixedSize(true);
        rvNoblePrivilege.setNestedScrollingEnabled(false);
    }

    private void initData() {
        getMyMember();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvOther) {
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                    + "/api/h5/gradeDesr?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&type=1").navigation();
        }
    }

    /**
     * 我的会员
     */
    private void getMyMember() {
        HttpApiAppUser.getMyMember(new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && retModel != null) {
                    if (retModel.userGradePrivilegeList != null && retModel.userGradePrivilegeList.size() > 0) {
                        tvUserPrivilegeTitle.setVisibility(View.VISIBLE);
                        rvUserPrivilege.setAdapter(new AppGradePrivilegeAdapter(retModel.userGradePrivilegeList));
                    } else {
                        tvUserPrivilegeTitle.setVisibility(View.GONE);
                    }
                    if (retModel.wealthGradePrivilegeList != null && retModel.wealthGradePrivilegeList.size() > 0) {
                        tvWealthPrivilegeTitle.setVisibility(View.VISIBLE);
                        rvWealthPrivilege.setAdapter(new AppGradePrivilegeAdapter(retModel.wealthGradePrivilegeList));
                    } else {
                        tvWealthPrivilegeTitle.setVisibility(View.GONE);
                    }
                    if (retModel.nobleGradePrivilegeList != null && retModel.nobleGradePrivilegeList.size() > 0) {
                        tvNoblePrivilegeTitle.setVisibility(View.VISIBLE);
                        rvNoblePrivilege.setAdapter(new AppGradePrivilegeAdapter(retModel.nobleGradePrivilegeList));
                    } else {
                        tvNoblePrivilegeTitle.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
