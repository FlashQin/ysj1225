package com.kalacheng.login.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.model.TabInfoDto;
import com.kalacheng.buscommon.model.TabTypeDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.login.R;
import com.kalacheng.login.adapter.TagAdpater;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.view.ItemDecoration;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.TagSelectActivity)
public class TagSelectActivity extends BaseActivity implements View.OnClickListener {
    public ApiUserInfo apiUserInfo;

    List<TabTypeDto> tabTypeDtos;
    LinearLayout llTag;

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_select);
        initView();
        initData();
    }

    private void initView() {
        llTag = findViewById(R.id.ll_tag);
        findViewById(R.id.btn_next).setOnClickListener(this);
        TextView textView = findViewById(R.id.tvOther);
        textView.setVisibility(View.VISIBLE);
        findViewById(R.id.btn_back).setVisibility(View.GONE);
        textView.setText("跳过");
        textView.setOnClickListener(this);
        setTitle("兴趣标签");
    }

    private void initData() {
        HttpApiAppUser.allTabs(new HttpApiCallBackArr<TabTypeDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<TabTypeDto> retModel) {
                tabTypeDtos = retModel;
                if (null != retModel && !retModel.isEmpty()) {
                    for (TabTypeDto tabTypeDto : retModel) {
                        View tagTitle = LayoutInflater.from(mContext).inflate(R.layout.layout_right_title, null);
                        LinearLayout llText = tagTitle.findViewById(R.id.ll_text);
                        llText.setBackgroundColor(getResources().getColor(R.color.white));
                        llTag.addView(tagTitle);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tagTitle.getLayoutParams();
                        layoutParams.height = DpUtil.dp2px(30);
                        layoutParams.setMargins(0, DpUtil.dp2px(12), 0, 0);
                        TextView text = tagTitle.findViewById(R.id.text);
                        text.getPaint().setFakeBoldText(true);
                        text.setText(tabTypeDto.name);
                        RecyclerView recyclerView = new RecyclerView(TagSelectActivity.this);
                        List<TabInfoDto> infoDtoList;
                        if (null == tabTypeDto.tabInfoList) {
                            infoDtoList = new ArrayList<>();
                        } else {
                            infoDtoList = tabTypeDto.tabInfoList;
                        }
                        recyclerView.setLayoutManager(new GridLayoutManager(TagSelectActivity.this, 3));
                        TagAdpater adpater = new TagAdpater(infoDtoList);
                        recyclerView.setAdapter(adpater);
                        recyclerView.addItemDecoration(new ItemDecoration(mContext, 0x00000000, 8, 7));
                        llTag.addView(recyclerView);
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
                        layoutParams2.setMargins(DpUtil.dp2px(10), 0, DpUtil.dp2px(10), 0);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {
            String tabIdStr = "";
            for (TabTypeDto tabTypeDto : tabTypeDtos) {
                if (null != tabTypeDto.tabInfoList && !tabTypeDto.tabInfoList.isEmpty())
                    for (TabInfoDto tabInfoDto : tabTypeDto.tabInfoList) {
                        if (tabInfoDto.status == 1) {
                            tabIdStr = tabIdStr + tabInfoDto.id + ":" + tabInfoDto.name + ",";
                        }
                    }
            }
            if (tabIdStr.equals("")) {
                ToastUtil.show(" 请先选择兴趣标签");
                return;
            }
            HttpApiAppUser.updateInterest(tabIdStr, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        ToastUtil.show("修改成功");
                        ARouter.getInstance().build(ARouterPath.MainActivity).navigation();
                        finish();
                    }
                }
            });
        } else if (view.getId() == R.id.tvOther) {
            ARouter.getInstance().build(ARouterPath.MainActivity).navigation();
            finish();
        }
    }
}
