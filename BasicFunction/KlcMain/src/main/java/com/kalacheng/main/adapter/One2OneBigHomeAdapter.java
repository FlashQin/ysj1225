package com.kalacheng.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne;
import com.kalacheng.libuser.model.HomeO2OData;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.ItemOne2oneBigBinding;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;

import java.util.ArrayList;
import java.util.List;


public class One2OneBigHomeAdapter extends RecyclerView.Adapter {
    private static Context mContext;
    private List<HomeO2OData> mList = new ArrayList<>();
    public boolean isOne2OneBig;
    PermissionsUtil mPermissionsUtil;
    private static TXVodPlayConfig config;
    private static TXVodPlayer mVodPlayer;

    public One2OneBigHomeAdapter(Context mContext, boolean isOne2OneBig, PermissionsUtil mPermissionsUtil) {
        this.mContext = mContext;
        this.isOne2OneBig = isOne2OneBig;
        this.mPermissionsUtil = mPermissionsUtil;
        config = new TXVodPlayConfig();
        config.setCacheFolderPath(mContext.getCacheDir().getAbsolutePath());
        config.setMaxCacheItems(15);
    }

    public void loadData(List<HomeO2OData> data) {
        stopPlayer();
        if (data != null) {
            mList.addAll(data);
        }
        notifyItemRangeInserted(mList.size() - data.size() - 1, data.size());
    }

    public void RefreshData(List<HomeO2OData> data) {
        stopPlayer();
        this.mList.clear();
        if (data != null) {
            this.mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public static void stopPlayer() {
        if (null != mVodPlayer) {
            mVodPlayer.stopPlay(false);
            mVodPlayer = null;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOne2oneBigBinding bigBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_one2one_big, parent, false);
        return new One2OneBigViewHolder(bigBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((One2OneBigViewHolder) holder).setData(mList.get(position));
        if (position == 0) {
            ((One2OneBigViewHolder) holder).setActive();
        }
    }

    private static class One2OneBigViewHolder extends RecyclerView.ViewHolder implements AutoPlayItem {
        ItemOne2oneBigBinding binding;
        HomeO2OData bean;

        public One2OneBigViewHolder(ItemOne2oneBigBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final HomeO2OData bean) {
            this.bean = bean;

            binding.setViewModel(bean);
            binding.executePendingBindings();
            if (!TextUtils.isEmpty(bean.oooVideoImg)) {
                ImageLoader.display(bean.oooVideoImg, binding.ivThumb);
            } else {
                ImageLoader.display(bean.poster, binding.ivThumb);
            }
//            if (!TextUtils.isEmpty(bean.oooVideo)) {
//                binding.ivThumb.setVisibility(View.VISIBLE);
//                binding.cardVideo.setVisibility(View.VISIBLE);
//            } else {
            binding.cardVideo.setVisibility(View.GONE);
//            }
            binding.rlAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.userId).navigation();
                }
            });
            if (bean.isvip == 1) {
                binding.tvNobleName.setText("开通贵族尊享");
                binding.tvNobleDiscount.setText(String.format("%.1f", bean.rechargeDiscount * 10) + "折");
                binding.llNoble.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (CheckDoubleClick.isFastDoubleClick()) {
                            return;
                        }
                        ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                                + HttpConstants.URL_NOBLE + "_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
                    }
                });
            } else {
                binding.tvNobleName.setText(bean.nobleGradeName + "享");
                binding.tvNobleDiscount.setText(String.format("%.1f", bean.rechargeDiscount * 10) + "折");
            }
            binding.tvVideo.setText(String.valueOf((int) bean.videoCoin));
            binding.tvVoice.setText(String.valueOf((int) bean.voiceCoin));
            binding.tvVideoUnit.setText(SpUtil.getInstance().getCoinUnit() + "/分钟");
            binding.tvVoiceUnit.setText(SpUtil.getInstance().getCoinUnit() + "/分钟");

            if (bean.sourceState == 0) {
                binding.ivLiveState.setImageResource(R.drawable.darkgrey_oval);
                binding.tvLiveState.setText("离线");
            } else if (bean.sourceState == 1) {
                binding.ivLiveState.setImageResource(R.drawable.red_oval);
                binding.tvLiveState.setText("忙碌");
            } else if (bean.sourceState == 2) {
                binding.ivLiveState.setImageResource(R.drawable.green_oval);
                binding.tvLiveState.setText("在线");
            } else if (bean.sourceState == 3) {
                binding.ivLiveState.setImageResource(R.drawable.blue_oval);
                binding.tvLiveState.setText("通话中");
            } else if (bean.sourceState == 4) {
                binding.ivLiveState.setImageResource(R.drawable.red_oval);
                binding.tvLiveState.setText("看直播");
            } else if (bean.sourceState == 5) {
                binding.ivLiveState.setImageResource(R.drawable.red_oval);
                binding.tvLiveState.setText("匹配中");
            } else if (bean.sourceState == 6) {
                binding.ivLiveState.setImageResource(R.drawable.blue_oval);
                binding.tvLiveState.setText("直播中");
            } else {
                binding.ivLiveState.setImageResource(R.drawable.lightgrey_oval);
                binding.tvLiveState.setText("离开");
            }
            binding.videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ApiCfgPayCallOneVsOne apiCfgPayCallOneVsOne = new ApiCfgPayCallOneVsOne();
                    apiCfgPayCallOneVsOne.tabIdList = bean.tabName;
                    apiCfgPayCallOneVsOne.voice = bean.oooVoice;
                    apiCfgPayCallOneVsOne.distance = bean.distance;
                    apiCfgPayCallOneVsOne.lng = String.valueOf(bean.lng);
                    apiCfgPayCallOneVsOne.city = bean.city;
                    if (bean.sourceState == 2)
                        apiCfgPayCallOneVsOne.openState = 1;
                    else
                        apiCfgPayCallOneVsOne.openState = 0;
                    apiCfgPayCallOneVsOne.videoCoin = bean.videoCoin;
                    apiCfgPayCallOneVsOne.liveThumb = bean.headImg;
                    apiCfgPayCallOneVsOne.video = bean.oooVideo;
                    apiCfgPayCallOneVsOne.userName = bean.username;
                    apiCfgPayCallOneVsOne.userId = bean.userId;
                    apiCfgPayCallOneVsOne.voiceCoin = bean.voiceCoin;
                    apiCfgPayCallOneVsOne.poster = bean.poster;
                    apiCfgPayCallOneVsOne.lat = String.valueOf(bean.lat);
                    ARouter.getInstance().build(ARouterPath.MeetAudienceSingleActivity).withParcelable(ARouterValueNameConfig.ApiCfgPayCallOneVsOne, apiCfgPayCallOneVsOne)
                            .withString(ARouterValueNameConfig.TITLE_NAME, "视频").navigation();
                }
            });
        }

        @Override
        public void setActive() {
            if (!TextUtils.isEmpty(bean.oooVideo)) {
                binding.cardVideo.setVisibility(View.VISIBLE);
                startVideo();
            }
        }

        private void startVideo() {
            if (TextUtils.isEmpty(bean.oooVideo)) {
                return;
            }
            stopPlayer();
            binding.ivThumb.setVisibility(View.GONE);
            mVodPlayer = new TXVodPlayer(mContext);
            mVodPlayer.setConfig(config);
            mVodPlayer.setAutoPlay(true);
            mVodPlayer.setLoop(true);
            mVodPlayer.setMute(true);
            //关联 player 对象与界面 view
            mVodPlayer.setPlayerView(binding.videoView);
            mVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
            mVodPlayer.startPlay(bean.oooVideo);
            mVodPlayer.setVodListener(new ITXVodPlayListener() {
                @Override
                public void onPlayEvent(TXVodPlayer txVodPlayer, int e, Bundle bundle) {
                    switch (e) {
                        case TXLiveConstants.PLAY_EVT_PLAY_BEGIN://加载完成，开始播放的回调
                            Log.e("TXVodPlayer>>>", "加载完成，开始播放的回调");
                            binding.ivThumb.setVisibility(View.GONE);
                            break;
                        case TXLiveConstants.PLAY_EVT_PLAY_LOADING: //开始加载的回调
                            Log.e("TXVodPlayer>>>", "开始加载的回调");
                            break;
                        case TXLiveConstants.PLAY_EVT_PLAY_END://获取到视频播放完毕的回调
                            Log.e("TXVodPlayer>>>", "获取到视频播放完毕的回调");
                            break;
                        case TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME://获取到视频首帧回调
                            Log.e("TXVodPlayer>>>", "获取到视频首帧回调");
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

                }
            });
        }

        private void pauseVideo() {
            if (null != mVodPlayer) {
                mVodPlayer.pause();
                Log.e("TXVodPlayer>>>", "pause");
            }
        }

        private void ResumeVideo() {
            if (null != mVodPlayer) {
                mVodPlayer.resume();
                Log.e("TXVodPlayer>>>", "resume");
            }
        }

        @Override
        public void deactivate() {
            stopPlayer();
            binding.cardVideo.setVisibility(View.GONE);
//            if (!TextUtils.isEmpty(bean.oooVideo)) {
//                binding.ivThumb.setVisibility(View.VISIBLE);
//            }
        }

        @Override
        public void onPause() {
            pauseVideo();
        }

        @Override
        public void onResume() {
            ResumeVideo();
        }

        @Override
        public View getAutoplayView() {
            return binding.videoView;
        }
    }

}
