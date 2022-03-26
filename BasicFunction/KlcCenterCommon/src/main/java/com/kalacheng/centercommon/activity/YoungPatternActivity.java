package com.kalacheng.centercommon.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.YoungPatternContentAdpater;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.base.activty.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/*
* 青少年模式
* */
@Route(path = ARouterPath.YoungPatternActivity)
public class YoungPatternActivity extends BaseActivity {
    @Autowired(name = "isOpenYoung")
    int isOpenYoung;

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.young_pattern);

        getInitView();
        getPatternContent();
    }

    public void getInitView(){
        ImageView YoungPattern_Image = findViewById(R.id.YoungPattern_Image);
        TextView YoungPattern_State = findViewById(R.id.YoungPattern_State);
        TextView YoungPattern_Button = findViewById(R.id.YoungPattern_Button);
        if (isOpenYoung == 1){
            setTitle("青少年模式");
            YoungPattern_State.setText("青少年模式 - 已开启");
            YoungPattern_State.setTextColor(Color.parseColor("#8A8DFF"));
            YoungPattern_Image.setBackgroundResource(R.mipmap.icon_young_open);
            YoungPattern_Button.setText("关闭青少年模式");
        }else {
            setTitle("青少年模式");
            YoungPattern_State.setText("青少年模式 - 未开启");
            YoungPattern_State.setTextColor(Color.parseColor("#AAAAAA"));
            YoungPattern_Image.setBackgroundResource(R.mipmap.icon_young_close);
            YoungPattern_Button.setText("开启青少年模式");

        }



        YoungPattern_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ARouterPath.YoungPatternSetPassWordActivity).withInt("isOpenYoung",isOpenYoung).withInt("isforget",1).navigation();
                finish();
            }
        });

    }

    //获取青少年模式的内容
    @SuppressLint("WrongConstant")
    public void getPatternContent(){
        RecyclerView YoungPattern_ContentList = findViewById(R.id.YoungPattern_ContentList);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        YoungPattern_ContentList.setLayoutManager(manager);
        YoungPattern_ContentList.addItemDecoration(new ItemDecoration(mContext,0,0,20));
        List<String> mList = new ArrayList<>();
        mList.add("为呵护未成年人健康成长，我们将针对未成年人推出更优化的内容");
        mList.add("该模式下部分功能无法正常使用");
        mList.add("请监护人主动选择，并设置监护密码。");
        mList.add("部分功能无法使用，包含充值、评论、发布动态、开启直播、送礼物等");
        YoungPatternContentAdpater adpater = new YoungPatternContentAdpater(mContext,mList);
        YoungPattern_ContentList.setAdapter(adpater);
    }
}
