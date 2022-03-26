package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.SetListAdpater;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiUserIndexNode;
import com.kalacheng.libuser.model.ApiUserIndexResp;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.listener.OnBeanCallback;

import cn.jmessage.support.google.gson.Gson;

/**
 * 关于我们
 */
@Route(path = ARouterPath.AboutUsActivity)
public class AboutUsActivity extends BaseActivity {


    private RecyclerView recyclerViewSet;
    private SetListAdpater mAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }


    public void initData() {
        recyclerViewSet = findViewById(R.id.recyclerView_set);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerViewSet.setLayoutManager(manager);
        getData();
    }

    public void getData() {
        ApiUserIndexResp apiUserIndexResp = SpUtil.getInstance().<ApiUserIndexResp>getModel("ApiUserIndexResp", ApiUserIndexResp.class);
        Log.e(">>>",  ""+ new Gson().toJson(apiUserIndexResp));
        if (apiUserIndexResp != null) {
            for (int i = 0; i < apiUserIndexResp.setList.size(); i++) {
                String name = apiUserIndexResp.setList.get(i).name;
                if (name.equals("修改密码") || name.equals("清除缓存") || name.equals("检查更新")) {
                    apiUserIndexResp.setList.remove(i);
                    i--;
                }
            }

            mAdpater = new SetListAdpater(apiUserIndexResp.setList);
            recyclerViewSet.setAdapter(mAdpater);
            mAdpater.setOnItemCallBack(new OnBeanCallback<ApiUserIndexNode>() {
                @Override
                public void onClick(ApiUserIndexNode bean) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, bean.app_url).navigation();
                }
            });
        }
    }


}
