package com.kalacheng.commonview.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.base.listener.OnItemClickListener;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.VideoChooseAdapter;
import com.kalacheng.commonview.databinding.ActivityVideoChooseBinding;
import com.kalacheng.commonview.viewmodel.VideoChooseModel;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.util.bean.VideoChooseBean;
import com.kalacheng.util.utils.CommonCallback;
import com.kalacheng.util.utils.VideoLocalUtil;
import com.kalacheng.util.view.ItemDecoration;

import java.util.List;

/**
 * 选择本地视频
 */
public class VideoChooseActivity extends BaseMVVMActivity<ActivityVideoChooseBinding, VideoChooseModel> implements OnItemClickListener<VideoChooseBean> {
    private long mMaxDuration;

    private VideoLocalUtil mVideoLocalUtil;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_video_choose;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        mMaxDuration = getIntent().getLongExtra(ARouterValueNameConfig.VIDEO_DURATION, 15000);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false));
        ItemDecoration decoration = new ItemDecoration(mContext, 0x00000000, 3, 3);
        decoration.setOnlySetItemOffsetsButNoDraw(true);
        binding.recyclerView.addItemDecoration(decoration);
        mVideoLocalUtil = new VideoLocalUtil();
        mVideoLocalUtil.getLocalVideoList(new CommonCallback<List<VideoChooseBean>>() {
            @Override
            public void callback(List<VideoChooseBean> videoList) {
                if (videoList == null || videoList.size() == 0) {
                    if (binding.noData != null && binding.noData.getVisibility() != View.VISIBLE) {
                        binding.noData.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                if (binding.recyclerView != null) {
                    VideoChooseAdapter adapter = new VideoChooseAdapter(mContext, videoList);
                    adapter.setOnItemClickListener(VideoChooseActivity.this);
                    binding.recyclerView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position, VideoChooseBean bean) {
        Intent intent = new Intent();
        intent.putExtra(ARouterValueNameConfig.VIDEO_PATH, bean.getVideoPath());
        intent.putExtra(ARouterValueNameConfig.VIDEO_DURATION, bean.getDuration());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }
}
