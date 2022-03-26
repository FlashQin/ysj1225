package com.kalacheng.main.fragment;

import android.Manifest;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOLive;
import com.kalacheng.commonview.utils.OOOLiveCallUtils;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne;
import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.LiveChannelAdpater;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.FullScreenVideoView;
import com.kalacheng.util.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 遇见单人
 */
public class MeetSingleFragment extends BaseFragment implements View.OnClickListener {
    PermissionsUtil mProcessResultUtil;
    TextView tvCount, tvNickname, tvAddress, tvVideoPrice, tvVoicePrice, tvCountMany;
    RecyclerView recyclerView;
    ImageView ivAvatar;
    public FullScreenVideoView video;
    protected int mCountDownCount = 10;
    int index = 0;
    private ScaleAnimation animation;
    List<ApiCfgPayCallOneVsOne> apiCfgPayCallOneVsOneList;

    public MeetSingleFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_single;
    }

    @Override
    public void initView() {
        tvCount = mParentView.findViewById(R.id.tv_count);
        tvNickname = mParentView.findViewById(R.id.tv_nickname);
        tvAddress = mParentView.findViewById(R.id.tv_address);
        tvVideoPrice = mParentView.findViewById(R.id.tv_video_price);
        tvVoicePrice = mParentView.findViewById(R.id.tv_voice_price);
        tvCountMany = mParentView.findViewById(R.id.tv_count_many);
        ivAvatar = mParentView.findViewById(R.id.iv_avatar);
        video = mParentView.findViewById(R.id.video_view);

        recyclerView = mParentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.addItemDecoration(new SpaceItemDecoration(DpUtil.dp2px(12), DpUtil.dp2px(5)));
        mParentView.findViewById(R.id.ll_refresh).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_video).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_voice).setOnClickListener(this);

    }

    @Override
    public void initData() {
        mProcessResultUtil = new PermissionsUtil(getActivity());

        HttpApiOOOLive.meetUser((float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT), (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG), new HttpApiCallBackArr<ApiCfgPayCallOneVsOne>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiCfgPayCallOneVsOne> retModel) {
                if (code == 1 && null != retModel) {
                    apiCfgPayCallOneVsOneList = retModel;
                    showUserInfo(retModel.get(0));

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_refresh) {
            if (index < apiCfgPayCallOneVsOneList.size() - 1) {
                ++index;
                showUserInfo(apiCfgPayCallOneVsOneList.get(index));
            }
        } else if (view.getId() == R.id.ll_video) {
            final ApiUserInfo info = new ApiUserInfo();
            info.userId = apiCfgPayCallOneVsOneList.get(index).userId;
            LiveConstants.mIsOOOSend = true;
            info.avatar = apiCfgPayCallOneVsOneList.get(index).liveThumb;
            info.username = apiCfgPayCallOneVsOneList.get(index).userName;
            mProcessResultUtil.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            }, new Runnable() {
                @Override
                public void run() {
                    OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(getActivity(), 1, info, 1);

                }
            });
        } else if (view.getId() == R.id.ll_voice) {
            final ApiUserInfo info = new ApiUserInfo();
            info.userId = apiCfgPayCallOneVsOneList.get(index).userId;
            LiveConstants.mIsOOOSend = true;
            info.avatar = apiCfgPayCallOneVsOneList.get(index).liveThumb;
            info.username = apiCfgPayCallOneVsOneList.get(index).userName;
            mProcessResultUtil.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            }, new Runnable() {
                @Override
                public void run() {
                    OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(getActivity(), 0, info, 1);

                }
            });
        }
    }

    public void showUserInfo(ApiCfgPayCallOneVsOne apiCfgPayCallOneVsOne) {
        mCountDownCount = 10;
        if (apiCfgPayCallOneVsOne.tabIdList != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.tabIdList)) {
            String[] tabIdStr = apiCfgPayCallOneVsOne.tabIdList.split(",");
            List<AppLiveChannel> appLiveChannels = new ArrayList<AppLiveChannel>();
            for (int i = 0; i < tabIdStr.length; i++) {
                String[] strings = tabIdStr[i].split(":");
                AppLiveChannel appLiveChannel = new AppLiveChannel();
                appLiveChannel.title = strings[1];
                appLiveChannels.add(appLiveChannel);
            }
            LiveChannelAdpater liveChannelAdpater = new LiveChannelAdpater(appLiveChannels);
            liveChannelAdpater.setEnable(false);
            recyclerView.setAdapter(liveChannelAdpater);

        }
        if (apiCfgPayCallOneVsOne.liveThumb != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.liveThumb)) {
            ImageLoader.display(apiCfgPayCallOneVsOne.liveThumb, ivAvatar);
        }
        if (apiCfgPayCallOneVsOne.userName != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.userName)) {
            tvNickname.setText(apiCfgPayCallOneVsOne.userName);
        }
        if (apiCfgPayCallOneVsOne.province != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.province)) {
            tvAddress.setText(apiCfgPayCallOneVsOne.province + "," + apiCfgPayCallOneVsOne.distance + " km");
        }
        if (apiCfgPayCallOneVsOne.video != null && !TextUtils.isEmpty(apiCfgPayCallOneVsOne.video)) {
            if (TextUtils.isEmpty(apiCfgPayCallOneVsOne.video)) {
                return;
            }
            Uri uri = Uri.parse(apiCfgPayCallOneVsOne.video);
            video.setVideoURI(uri);
            video.start();
            video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    startCountDown();
                }
            });

        } else {
            startCountDown();
        }
        tvVoicePrice.setText((int) apiCfgPayCallOneVsOne.voiceCoin + SpUtil.getInstance().getCoinUnit() + "/分钟");
        tvVideoPrice.setText((int) apiCfgPayCallOneVsOne.videoCoin + SpUtil.getInstance().getCoinUnit() + "/分钟");

    }

    protected void startCountDown() {
        tvCount.setText(String.valueOf(mCountDownCount));
        animation = new ScaleAnimation(3, 1, 3, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(10);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (index < apiCfgPayCallOneVsOneList.size() - 1) {
                    ++index;
                    showUserInfo(apiCfgPayCallOneVsOneList.get(index));
                } else {
                    tvCount.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mCountDownCount--;
                tvCount.setText(String.valueOf(mCountDownCount));
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvCount.getLayoutParams();
//                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            }
        });
        tvCount.startAnimation(animation);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        video.stopPlayback();
        video.setOnCompletionListener(null);
        video.setOnPreparedListener(null);

    }
}
