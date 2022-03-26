package com.kalacheng.dynamiccircle.adpater;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.databinding.ItemVideoBinding;
import com.kalacheng.base.event.VideoZanEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<ApiUserVideo> mList = new ArrayList<>();
    private Context mContext;
    private OnVideoCallBack mCallBack;
    private int itemPosition;
    private String location;

    public VideoAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<ApiUserVideo> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void loadData(List<ApiUserVideo> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public List<ApiUserVideo> getList() {
        return mList;
    }

    public void setItemPosition(int position, String location) {
        this.itemPosition = position;
        this.location = location;
    }

    public ApiUserVideo getItem(int position) {
        return mList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVideoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_video, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.binding.setViewModel(mList.get(position));
        holder.binding.executePendingBindings();
        if (!ConfigUtil.getBoolValue(R.bool.videoLocation)) {
            holder.binding.llLocation.setVisibility(View.GONE);
        }
        if (mList.get(position).isAtt == 1 || mList.get(position).uid == HttpClient.getUid()) {
            holder.binding.tvFollow.setVisibility(View.INVISIBLE);
        } else {
            holder.binding.tvFollow.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(mList.get(position).topicName)) {
            if (TextUtils.isEmpty(mList.get(position).title)) {
                holder.binding.title.setText("#" + mList.get(position).topicName + "#");
            } else {
                holder.binding.title.setText("#" + mList.get(position).topicName + "#" + mList.get(position).title);
            }
        } else {
            holder.binding.title.setText(TextUtils.isEmpty(mList.get(position).title) ? "" : mList.get(position).title);
        }

        if (mList.get(position).type == 1) {//视频
            holder.binding.videoView.setVisibility(View.VISIBLE);
            holder.binding.cover.setVisibility(View.VISIBLE);
            holder.binding.btnPlay.setVisibility(View.GONE);
            holder.binding.viewpager.setVisibility(View.GONE);
            holder.binding.tvImage.setVisibility(View.GONE);
            ImageLoader.display(mList.get(position).thumb, holder.binding.cover, 0, 0, false,
                    null, null, new SimpleTarget<BitmapDrawable>() {
                        @Override
                        public void onResourceReady(@NonNull BitmapDrawable resource, @Nullable Transition<? super BitmapDrawable> transition) {
                            if (resource != null) {
                                if (resource.getIntrinsicWidth() < resource.getIntrinsicHeight()) {
                                    holder.binding.cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    holder.binding.cover.setImageBitmap(resource.getBitmap());
                                } else {
                                    holder.binding.cover.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    holder.binding.cover.setImageBitmap(resource.getBitmap());
                                }
                            }
                        }
                    });
        } else {
            holder.binding.videoView.setVisibility(View.GONE);
            holder.binding.cover.setVisibility(View.GONE);
            holder.binding.btnPlay.setVisibility(View.GONE);
            holder.binding.viewpager.setVisibility(View.VISIBLE);
            holder.binding.tvImage.setVisibility(View.VISIBLE);
        }

        holder.binding.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, mList.get(position).uid).navigation();
            }
        });
        holder.binding.tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                final int isFollow;
                if (mList.get(position).isAtt == 0) {
                    isFollow = 1;
                } else {
                    isFollow = 0;
                }
                HttpApiAppUser.set_atten(isFollow, mList.get(position).uid, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            mList.get(position).isAtt = isFollow;
                            if (mList.get(position).isAtt == 0) {
                                holder.binding.tvFollow.setVisibility(View.VISIBLE);
                            } else {
                                holder.binding.tvFollow.setVisibility(View.INVISIBLE);
                            }

                            for (ApiUserVideo dto : mList) {
                                if (dto.uid == mList.get(position).uid) {
                                    dto.isAtt = isFollow;
                                }
                            }
                        }
                    }
                });
            }
        });

        // 动态点赞/取消
        holder.binding.layoutLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                HttpApiAppVideo.videoZan(mList.get(position).id, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            VideoZanEvent event = new VideoZanEvent();
                            if (mList.get(position).isLike == 1) {
                                mList.get(position).isLike = 0;
                                mList.get(position).likes = mList.get(position).likes - 1;
                                holder.binding.btnLike.setImageResource(R.mipmap.icon_video_zan_01);
                            } else {
                                mList.get(position).isLike = 1;
                                mList.get(position).likes = mList.get(position).likes + 1;
                                holder.binding.btnLike.setImageResource(R.mipmap.icon_video_zan_15);
                            }
                            holder.binding.likeNum.setText(mList.get(position).likes + "");

                            // 点赞 评论了 广播通知刷新外层列表
                            event.setIsZanComment(1);
                            event.setPosition(itemPosition);
                            event.setLocation(location);
                            event.setZanNumber(mList.get(position).likes);
                            event.setIsLike(mList.get(position).isLike);
                            EventBus.getDefault().post(event);
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }
        });
        holder.binding.layoutComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (mCallBack != null) {
                    mCallBack.onComment(mList.get(position));
                }
            }
        });
        holder.binding.layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (mCallBack != null) {
                    mCallBack.onShare(mList.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemVideoBinding binding;

        public ViewHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnVideoCallBack(OnVideoCallBack onVideoCallBack) {
        mCallBack = onVideoCallBack;
    }

    public interface OnVideoCallBack {
        void onComment(ApiUserVideo apiUserVideo);

        void onShare(ApiUserVideo apiUserVideo, int position);
    }
}
