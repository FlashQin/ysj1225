package com.kalacheng.shortvideo.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.shortvideo.event.DeleteShortVideoItemEvent;
import com.kalacheng.shortvideo.listener.FinishCallBack;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.fragment.ShortVideoFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 视频播放 （短视频）
 */
@Route(path = ARouterPath.VideoPlayActivity)
public class VideoPlayActivity extends BaseActivity implements FinishCallBack {

    @Autowired(name = ARouterValueNameConfig.VIDEO_TYPE)
    public int videoType;
    @Autowired(name = ARouterValueNameConfig.POSITION)
    public int position;
    @Autowired(name = ARouterValueNameConfig.Beans)
    public ArrayList<ApiShortVideoDto> apiShortVideoDtos;
    @Autowired(name = ARouterValueNameConfig.VIDEO_PAGE)
    public int page;
    @Autowired(name = ARouterValueNameConfig.CLASSIFY_ID)
    public long classifyId;
    @Autowired(name = ARouterValueNameConfig.VIDEO_SORT)
    public long sort;
    @Autowired(name = ARouterValueNameConfig.VIDEO_WORKS_USER_ID)
    public long videoWorksUserId;
    @Autowired(name = ARouterValueNameConfig.VIDEO_WORKS_TYPE)
    public int videoWorksType;

    @Autowired(name = ARouterValueNameConfig.MESSAGE_TYPE)
    public int messageType;
    @Autowired(name = ARouterValueNameConfig.COMMENT_ID)
    public int commentId;
    @Autowired(name = ARouterValueNameConfig.ITEM_POSITION)
    public int itemPosition;
    @Autowired(name = ARouterValueNameConfig.COMMENT_LOCATION)
    public String location;


    private ShortVideoFragment mainVideoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_empty_framelayout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initData();
    }

    private void initData() {
        mainVideoFragment = new ShortVideoFragment(false, videoType, itemPosition, location, position, apiShortVideoDtos, page, classifyId, sort, videoWorksUserId, videoWorksType, commentId, messageType);
        showFragment(mainVideoFragment, R.id.fl);

        mainVideoFragment.setShowed(true);
        mainVideoFragment.loadData();
        mainVideoFragment.setFinishCallBack(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mainVideoFragment != null) {
            mainVideoFragment.onResumeFragment();
        }
    }

    @Override
    protected void onPause() {
        if (mainVideoFragment != null) {
            mainVideoFragment.onPauseFragment();
        }
        super.onPause();
    }

    @Override
    public void isFinish() {
        finish();
    }

    @Override
    public void deleteList(ApiShortVideoDto appShortVideo, int position) {
        EventBus.getDefault().post(DeleteShortVideoItemEvent.getInstance(appShortVideo));
    }
}
