package com.kalacheng.commonview.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.buscommon.model.GiftWallDto;
import com.kalacheng.commonview.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.httpApi.HttpApiMedal;
import com.kalacheng.util.adapter.SimpleImgText2Adapter;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 礼物墙
 */
@Route(path = ARouterPath.GiftActivity)
public class GiftActivity extends BaseActivity {
    @Autowired(name = ARouterValueNameConfig.USERID)
    public long UserID;

    RecyclerView recyclerViewGift;
    TextView tvGift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        initView();
        initData();
    }

    private void initData() {
        HttpApiMedal.getUserGiftMedal(UserID, new HttpApiCallBackArr<GiftWallDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<GiftWallDto> retModel) {
                List<SimpleImageUrlTextBean> simpleImageUrlTextBeans = new ArrayList<>();
                if (code == 1 && null != retModel) {
                    if (!retModel.isEmpty()) {
                        int totalNum = 0;
                        for (GiftWallDto giftWallDto : retModel) {
                            simpleImageUrlTextBeans.add(new SimpleImageUrlTextBean(giftWallDto.giftId, giftWallDto.gifticon, giftWallDto.giftname, "x" + giftWallDto.totalNum));
                            totalNum += giftWallDto.totalNum;
                        }
                        SimpleImgText2Adapter simpleImgText2Adapter = new SimpleImgText2Adapter(simpleImageUrlTextBeans);
                        recyclerViewGift.setAdapter(simpleImgText2Adapter);

                        tvGift.setText(totalNum + "");
                    }
                }
            }
        });
    }

    private void initView() {
        recyclerViewGift = findViewById(R.id.recyclerView_gift);
        tvGift = findViewById(R.id.tv_gift);
        recyclerViewGift.setLayoutManager(new GridLayoutManager(GiftActivity.this, 4));
        setTitle("礼物墙");
    }
}
