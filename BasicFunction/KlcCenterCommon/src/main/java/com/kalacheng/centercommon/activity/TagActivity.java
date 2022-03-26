package com.kalacheng.centercommon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.base.listener.OnItemClickListener;
import com.kalacheng.buscommon.model.TabInfoDto;
import com.kalacheng.buscommon.model.TabTypeDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.InterestTagAdpater;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.util.utils.DpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的标签
 */
@Route(path = ARouterPath.TagActivity)
public class TagActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llTag;
    List<TabInfoDto> infoDtoList;
    List<TabTypeDto> retModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        TextView textView = findViewById(R.id.titleView);
        textView.setText("我的标签");
        TextView rightTextView = findViewById(R.id.tvOther);
        rightTextView.setText(WordUtil.getString(R.string.save));
        rightTextView.setVisibility(View.VISIBLE);
        rightTextView.setOnClickListener(this);
        initView();
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initData() {
        HttpApiAppUser.allTabs(new HttpApiCallBackArr<TabTypeDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<TabTypeDto> retModel) {
                if (code == 1 && retModel != null) {
                    TagActivity.this.retModel = retModel;
                    for (TabTypeDto tabTypeDto : retModel) {
                        View tagTitle = LayoutInflater.from(mContext).inflate(R.layout.layout_right_title, null);
                        llTag.addView(tagTitle);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tagTitle.getLayoutParams();
                        layoutParams.height = DpUtil.dp2px(45);
                        TextView text = tagTitle.findViewById(R.id.text);
                        text.setText(tabTypeDto.name);
                        RecyclerView recyclerView = new RecyclerView(TagActivity.this);
                        recyclerView.setLayoutManager(new GridLayoutManager(TagActivity.this, 4));
                        final InterestTagAdpater adpater;
                        if (null == tabTypeDto.tabInfoList) {
                            infoDtoList = new ArrayList<>();
                        } else {
                            infoDtoList = tabTypeDto.tabInfoList;
                        }
                        adpater = new InterestTagAdpater(infoDtoList);
                        adpater.setOnItemClickListener(new OnItemClickListener<TabInfoDto>() {
                            @Override
                            public void onItemClick(int position, TabInfoDto bean) {
                                if (bean.status == 0)
                                    bean.status = 1;
                                else
                                    bean.status = 0;
                                adpater.notifyItemChanged(position);
                            }
                        });
                        recyclerView.setAdapter(adpater);
                        llTag.addView(recyclerView);
                    }
                }
            }
        });
    }

    private void initView() {
        llTag = findViewById(R.id.ll_tag);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvOther) {
            String tabIdStr = "";
            String tabStrName = "";
            if (retModel != null) {
                for (TabTypeDto tabTypeDto : retModel) {
                    if (null != tabTypeDto.tabInfoList && !tabTypeDto.tabInfoList.isEmpty())
                        for (TabInfoDto tabInfoDto : tabTypeDto.tabInfoList) {
                            if (tabInfoDto.status == 1) {
                                tabIdStr = tabIdStr + tabInfoDto.id + ":" + tabInfoDto.name + ",";
                                tabStrName = tabStrName + " " + tabInfoDto.name;
                            }
                        }
                }
            }
            final String finalTabStrName = tabStrName;
            HttpApiAppUser.updateInterest(tabIdStr, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        ToastUtil.show(WordUtil.getString(R.string.modify_finish));

                        Intent intent = new Intent();
                        intent.putExtra("Name", finalTabStrName);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }
    }
}
