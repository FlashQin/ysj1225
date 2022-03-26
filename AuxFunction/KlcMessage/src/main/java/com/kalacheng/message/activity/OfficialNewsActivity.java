package com.kalacheng.message.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.httpApi.HttpApiOfficialNews;
import com.kalacheng.libuser.model.AppOfficialNewsDTO;
import com.kalacheng.message.R;
import com.kalacheng.message.adapter.OfficialNewsAdpater;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;

import java.util.List;

/*
 * 官方消息列表
 * */
@Route(path = ARouterPath.OfficialNewsActivity)
public class OfficialNewsActivity extends BaseActivity {
    private RecyclerView OfficialNews_List;
    private OfficialNewsAdpater adpater;

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.official_news);
        getInitView();
        getData();
    }

    public void getInitView() {
        setTitle("官方消息");

        OfficialNews_List = findViewById(R.id.OfficialNews_List);
        OfficialNews_List.setHasFixedSize(true);
        OfficialNews_List.addItemDecoration(new ItemDecoration(mContext, 0, 0, 10));
        OfficialNews_List.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adpater = new OfficialNewsAdpater(mContext);
        OfficialNews_List.setAdapter(adpater);

    }

    public void getData() {
        HttpApiOfficialNews.getOfficialNewsList(0, 30, new HttpApiCallBackArr<AppOfficialNewsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppOfficialNewsDTO> retModel) {
                if (code == 1) {
                    adpater.setData(retModel);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });

        //清空“官方消息”未读数
        HttpApiChatRoom.clearNoticeMsg(-1, 5, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {

            }
        });
    }
}
