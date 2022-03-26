package com.kalacheng.livecommon.component;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.libuser.model.ApiUsersLiveManager;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.LiveAdminListAdpater;

import java.util.List;

@Route(path = ARouterPath.AdminList)
public class AdminListActivity extends BaseActivity {
    @Autowired(name = ARouterValueNameConfig.USERID)
    public long UserID;


    private LiveAdminListAdpater adminListAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_live_admin_list);
        initView();
    }

    @SuppressLint("WrongConstant")
    private void initView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adminListAdpater = new LiveAdminListAdpater(mContext);
        recyclerView.setAdapter(adminListAdpater);
        getAdminList();
    }

    public void getAdminList() {
        HttpApiPublicLive.getLiveManagerList(UserID,0, new HttpApiCallBackArr<ApiUsersLiveManager>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLiveManager> retModel) {
                if (code == 1) {
                    adminListAdpater.getData(retModel);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
