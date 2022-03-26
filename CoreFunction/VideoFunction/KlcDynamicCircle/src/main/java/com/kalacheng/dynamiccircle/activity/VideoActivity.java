package com.kalacheng.dynamiccircle.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.event.DeleteVideoItemEvent;
import com.kalacheng.dynamiccircle.fragment.VideoFragment;
import com.kalacheng.dynamiccircle.listener.FinishCallBack;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.base.activty.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 视频播放
 */
@Route(path = ARouterPath.LookVideo)
public class VideoActivity extends BaseActivity implements FinishCallBack {
    @Autowired(name = ARouterValueNameConfig.VIDEO_TYPE)
    public int videoType;
    @Autowired(name = ARouterValueNameConfig.POSITION)
    public int position;
    @Autowired(name = ARouterValueNameConfig.Beans)
    public ArrayList<ApiUserVideo> videos;
    @Autowired(name = ARouterValueNameConfig.VIDEO_PAGE)
    public int page;
    @Autowired(name = ARouterValueNameConfig.COMMUNITY_TYPE)
    public int communityType;
    @Autowired(name = ARouterValueNameConfig.COMMUNITY_HOT_ID)
    public int communityHotId;
    @Autowired(name = ARouterValueNameConfig.COMMUNITY_UID)
    public long communityUid;
    @Autowired(name = ARouterValueNameConfig.ITEM_POSITION)
    public int itemPosition;
    @Autowired(name = ARouterValueNameConfig.COMMENT_LOCATION)
    public String location;

    private VideoFragment videoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_empty_framelayout);
        initData();
    }

    public void initData() {
        videoFragment = new VideoFragment(videoType, itemPosition, location, position, videos, page, communityType, communityHotId, communityUid);
        showFragment(videoFragment, R.id.fl);

        videoFragment.setShowed(true);
        videoFragment.loadData();
        videoFragment.setFinishCallBack(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoFragment != null) {
            videoFragment.onResumeFragment();
        }
    }

    @Override
    protected void onPause() {
        if (videoFragment != null) {
            videoFragment.onPauseFragment();
        }
        super.onPause();
    }

    @Override
    public void isFinish() {
        finish();
    }

    @Override
    public void deleteList(ApiUserVideo apiUserVideo, int position) {
        EventBus.getDefault().post(DeleteVideoItemEvent.getInstance(apiUserVideo));
    }
}
