package com.kalacheng.main.adapter;


import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.commonview.utils.OOOLiveCallUtils;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.ItemVideoManyPeopleBinding;
import com.kalacheng.util.utils.HttpProxyCacheServerUtils;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.List;


/**
 * Created by dxx on 2017/11/10.
 */

public class ManyPeopleVideoAdapter extends RecyclerView.Adapter<ManyPeopleVideoAdapter.ViewHolder> {

    List<ApiCfgPayCallOneVsOne> apiCfgPayCallOneVsOneList;
    OnItemClickCallback itemClickCallback;
    private Context mContext;
    PermissionsUtil mProcessResultUtil;

    public ManyPeopleVideoAdapter(List<ApiCfgPayCallOneVsOne> apiCfgPayCallOneVsOneList, PermissionsUtil processResultUtil) {
        this.apiCfgPayCallOneVsOneList = apiCfgPayCallOneVsOneList;
        mProcessResultUtil = processResultUtil;
    }

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public void setData(List<ApiCfgPayCallOneVsOne> list) {
        apiCfgPayCallOneVsOneList.clear();
        apiCfgPayCallOneVsOneList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        apiCfgPayCallOneVsOneList.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ItemVideoManyPeopleBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(mContext), R.layout.item_video_many_people,
                        parent, false);
        binding.setCallback(itemClickCallback);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.binding.setBean(apiCfgPayCallOneVsOneList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.tvVoiceCoin.setText(((int) apiCfgPayCallOneVsOneList.get(position).voiceCoin) + SpUtil.getInstance().getCoinUnit() + "/分");
        holder.binding.tvVideoCoin.setText(((int) apiCfgPayCallOneVsOneList.get(position).videoCoin) + SpUtil.getInstance().getCoinUnit() + "/分");
        holder.binding.ivThumb.setVisibility(View.VISIBLE);
        holder.binding.setCallback(new OnItemClickCallback<ApiCfgPayCallOneVsOne>() {
            @Override
            public void onClick(final View view, ApiCfgPayCallOneVsOne item) {
                final ApiUserInfo info = new ApiUserInfo();
                info.userId = apiCfgPayCallOneVsOneList.get(position).userId;
                LiveConstants.mIsOOOSend = true;
                info.avatar = apiCfgPayCallOneVsOneList.get(position).liveThumb;
                info.username = apiCfgPayCallOneVsOneList.get(position).userName;
                mProcessResultUtil.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, new Runnable() {
                    @Override
                    public void run() {
                        if (view.getId() == R.id.iv_video) {
                            OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, 1, info, 1);
                        } else {
                            OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, 0, info, 1);
                        }
                    }
                });
            }
        });
        if (TextUtils.isEmpty(apiCfgPayCallOneVsOneList.get(position).video)) {
            return;
        }

        HttpProxyCacheServer proxy = HttpProxyCacheServerUtils.getInstance().getProxy(mContext);
        String proxyUrl = proxy.getProxyUrl(apiCfgPayCallOneVsOneList.get(position).video);
        holder.binding.videoView.setVideoPath(proxyUrl);
        holder.binding.videoView.start();
//        holder.binding.
        if (!TextUtils.isEmpty(apiCfgPayCallOneVsOneList.get(position).videoImg)) {
            ImageLoader.display(apiCfgPayCallOneVsOneList.get(position).videoImg, holder.binding.ivThumb);
        } else {
            ImageLoader.display(apiCfgPayCallOneVsOneList.get(position).poster, holder.binding.ivThumb);
        }
        holder.binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.start();
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.binding.ivThumb.setVisibility(View.GONE);
                            }
                        }, 500);
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            holder.binding.videoView.setBackgroundColor(Color.TRANSPARENT);
                        }
                        return true;
                    }
                });
            }
        });
        holder.binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                holder.binding.videoView.start();
            }
        });
        holder.binding.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                holder.binding.videoView.stopPlayback(); //播放异常，则停止播放，防止弹窗使界面阻塞
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return apiCfgPayCallOneVsOneList == null ? 0 : apiCfgPayCallOneVsOneList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemVideoManyPeopleBinding binding;

        public ViewHolder(ItemVideoManyPeopleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
