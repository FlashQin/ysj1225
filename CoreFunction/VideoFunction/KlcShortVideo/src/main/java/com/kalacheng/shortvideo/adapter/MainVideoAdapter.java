package com.kalacheng.shortvideo.adapter;

import android.Manifest;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kalacheng.base.event.VideoZanEvent;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.commonview.utils.WebUtil;
import com.kalacheng.commonview.view.TextRender;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiBaseEntity;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.libuser.model.AppHotSort;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.databinding.ItemMainVideoBinding;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.videorecord.adpater.TagAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.kalacheng.util.utils.SpUtil.READ_SHORT_VIDEO_NUMBER;

public class MainVideoAdapter extends RecyclerView.Adapter<MainVideoAdapter.ViewHolder> {

    private List<ApiShortVideoDto> mList = new ArrayList<>();
    private onVideoCallBack mCallBack;
    private Context mContext;
    private ItemDecoration spaceItemDecoration;
    private ProcessResultUtil mProcessResultUtil;
    private String location;

    public MainVideoAdapter(Context context) {
        mContext = context;
        mProcessResultUtil = new ProcessResultUtil((FragmentActivity) mContext);
        spaceItemDecoration = new ItemDecoration(mContext, 0x00000000, 8, 0);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ApiShortVideoDto> getList() {
        return mList;
    }

    public void setData(List<ApiShortVideoDto> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    // 更新部分布局（这里是点赞关注操作 只刷新点赞和关注布局）
    public void updateData(List<ApiShortVideoDto> data, int position) {
        this.mList = data;
        notifyItemChanged(position, "CommentAndLike");
    }

    public void loadData(List<ApiShortVideoDto> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void setItemData(int itemPosition, ApiShortVideoDto itemData) {
        mList.set(itemPosition, itemData);
        notifyItemChanged(itemPosition, itemData);
    }

    public ApiShortVideoDto getItem(int position) {
        return mList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ItemMainVideoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_video, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            holder.binding.commentNum.setText(mList.get(position).comments + "");
            holder.binding.likeNum.setText(mList.get(position).likes + "");
            holder.binding.btnLike.setImageResource(mList.get(position).isLike == 1 ? R.mipmap.icon_video_zan_15 : R.mipmap.icon_video_zan_01);
        } else {
            holder.binding.setBean(mList.get(position));
            holder.binding.executePendingBindings();

//            if (ConfigUtil.getIntValue(R.integer.mainVideoPosition) == 0) {
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.binding.llControl.getLayoutParams();
//                layoutParams.setMargins(0, 0, 0, DpUtil.dp2px(50));
//            }

            holder.binding.llVideoShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mCallBack) {
                        mCallBack.onShop(holder.binding.llVideoShop, mList.get(position).productId);
                    }
                }
            });
            if (ConfigUtil.getBoolValue(R.bool.containShopping) && !TextUtils.isEmpty(mList.get(position).productName) && mList.get(position).productId != 0) {
                holder.binding.llVideoShop.setVisibility(View.VISIBLE);
                holder.binding.tvVideoShopName.setText("视频同款-" + mList.get(position).productName);
            } else {
                holder.binding.llVideoShop.setVisibility(View.GONE);
            }

            holder.binding.layoutLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    HttpApiAppShortVideo.shortVideoZan(mList.get(position).id, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {

                            if (mList.get(position).isLike == 0)
                                mList.get(position).isLike = 1;
                            else
                                mList.get(position).isLike = 0;
                            if (mList.get(position).isLike == 0) {
                                holder.binding.btnLike.setImageResource(R.mipmap.icon_video_zan_01);
                                --mList.get(position).likes;
                            } else {
                                holder.binding.btnLike.setImageResource(R.mipmap.icon_video_zan_15);
                                ++mList.get(position).likes;
                            }
                            holder.binding.likeNum.setText(mList.get(position).likes + "");


                            // 点赞 评论了 广播通知刷新外层列表
                            if (!TextUtils.isEmpty(location)) {
                                VideoZanEvent event = new VideoZanEvent();
                                event.setIsZanComment(1);
                                event.setPosition(position);
                                event.setLocation(location);
                                event.setZanNumber(mList.get(position).likes);
                                event.setIsLike(mList.get(position).isLike);
                                EventBus.getDefault().post(event);
                            }
                        }
                    });
                }
            });
            holder.binding.layoutComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (null != mCallBack)
                        mCallBack.onComment(mList.get(position), position);
                }
            });
            holder.binding.layoutShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (null != mCallBack)
                        mCallBack.onShare(mList.get(position), position);
                }
            });
            if (mList.get(position).userId != HttpClient.getUid()) {
                holder.binding.layoutGift.setVisibility(View.VISIBLE);
                holder.binding.layoutGift.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (CheckDoubleClick.isFastDoubleClick()) {
                            return;
                        }
                        if (null != mCallBack)
                            mCallBack.onGift(mList.get(position));
                    }
                });
            } else {
                holder.binding.layoutGift.setVisibility(View.GONE);
            }
            if (mList.get(position).isFollow == 1 || mList.get(position).userId == HttpClient.getUid()) {
                holder.binding.tvFollow.setVisibility(View.INVISIBLE);
            } else {
                holder.binding.tvFollow.setVisibility(View.VISIBLE);
            }
            if (mList.get(position).adsType == 2) {
                holder.binding.title.setVisibility(View.VISIBLE);
                TextRender.renderEnd(holder.binding.title, mList.get(position).content, R.mipmap.icon_ads_tag);
//            holder.binding.title.setText(mList.get(position).content);
            } else {
                if (!TextUtils.isEmpty(mList.get(position).content)) {
                    holder.binding.title.setVisibility(View.VISIBLE);
                    holder.binding.title.setText(mList.get(position).content);
                } else {
                    holder.binding.title.setVisibility(View.GONE);
                }
            }

            holder.binding.tvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    final int isFollow;
                    if (mList.get(position).isFollow == 0) {
                        isFollow = 1;
                    } else {
                        isFollow = 0;
                    }
                    HttpApiAppUser.set_atten(isFollow, mList.get(position).userId, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                mList.get(position).isFollow = isFollow;
                                if (mList.get(position).isFollow == 0) {
                                    holder.binding.tvFollow.setVisibility(View.VISIBLE);
                                } else {
                                    holder.binding.tvFollow.setVisibility(View.INVISIBLE);
                                }

                                for (ApiShortVideoDto dto : mList) {
                                    if (dto.userId == mList.get(position).userId) {
                                        dto.isFollow = isFollow;
                                    }
                                }
                            }
                        }
                    });
                }
            });
            holder.binding.recyclerViewTag.removeItemDecoration(spaceItemDecoration);
            holder.binding.recyclerViewTag.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            holder.binding.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, mList.get(position).userId).navigation();
                }
            });
            if (null != mList.get(position).classifyName && !TextUtils.isEmpty(mList.get(position).classifyName)) {
                String[] strings = mList.get(position).classifyName.split(",");
                String[] ids = mList.get(position).classifyId.split(",");
                List<AppHotSort> appHotSorts = new ArrayList<>();
                for (int i = 0; i < strings.length; i++) {
                    AppHotSort appHotSort = new AppHotSort();
                    appHotSort.name = strings[i];
                    if (i < ids.length) {
                        appHotSort.id = Long.parseLong(ids[i]);
                    }
                    appHotSorts.add(appHotSort);
                }
                TagAdapter tagAdpater = new TagAdapter(appHotSorts);
                tagAdpater.setOnBeanCallback(new OnBeanCallback<AppHotSort>() {
                    @Override
                    public void onClick(AppHotSort bean) {
                        ARouter.getInstance().build(ARouterPath.VideoClassifyActivity).withParcelable(ARouterValueNameConfig.AppHotSort, bean).navigation();
                    }
                });
                holder.binding.recyclerViewTag.setAdapter(tagAdpater);
                holder.binding.recyclerViewTag.addItemDecoration(spaceItemDecoration);
            }

            if (ConfigUtil.getBoolValue(R.bool.containOne2One) && mList.get(position).userId != HttpClient.getUid()) {
                holder.binding.llVoice.setVisibility(View.VISIBLE);
                holder.binding.llVoice.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        if (CheckDoubleClick.isFastDoubleClick()) {
                            return;
                        }
                        if (null != mList.get(position)) {
                            final ApiUserInfo info = new ApiUserInfo();
                            info.userId = mList.get(position).userId;
                            LiveConstants.mIsOOOSend = true;
                            info.avatar = mList.get(position).avatar;
//                        info.sex = mList.get(position).sex;
                            info.username = mList.get(position).username;
                            info.role = mList.get(position).role;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                mProcessResultUtil.requestPermissions(new String[]{
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO
                                }, new Runnable() {
                                    @Override
                                    public void run() {
                                        LookRoomUtlis.getInstance().showDialog(1, info, mProcessResultUtil, mContext, 1);

                                    }
                                });
                            }
                        }
                    }
                });
            } else {
                holder.binding.llVoice.setVisibility(View.GONE);
            }

            if (mList.get(position).adsType == 2) {//广告
                holder.binding.layoutAds.setVisibility(View.VISIBLE);
                if (mList.get(position).userId > 0) {
                    holder.binding.llInfo.setVisibility(View.VISIBLE);
                    holder.binding.layoutOperation.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.llInfo.setVisibility(View.GONE);
                    holder.binding.layoutOperation.setVisibility(View.GONE);
                }
                holder.binding.tvAdsText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckDoubleClick.isFastDoubleClick()) {
                            return;
                        }
                        if (!TextUtils.isEmpty(mList.get(position).adsUrl)) {
                            WebUtil.goExternalWeb(mContext, mList.get(position).adsUrl);
                        }
                    }
                });
                if (TextUtils.isEmpty(mList.get(position).adsText) && TextUtils.isEmpty(mList.get(position).adsTitle)) {
                    holder.binding.layoutAds.setVisibility(View.GONE);
                } else {
                    holder.binding.layoutAds.setVisibility(View.VISIBLE);
                    holder.binding.tvTitle.setVisibility(TextUtils.isEmpty(mList.get(position).adsTitle) ? View.GONE : View.VISIBLE);
                    if (TextUtils.isEmpty(mList.get(position).adsText) && TextUtils.isEmpty(mList.get(position).adsUrl)) {
                        holder.binding.tvAdsText.setVisibility(View.GONE);
                    } else if (TextUtils.isEmpty(mList.get(position).adsText) && !TextUtils.isEmpty(mList.get(position).adsUrl)) {
                        holder.binding.tvAdsText.setVisibility(View.VISIBLE);
                        holder.binding.tvAdsText.setText("立即前往");
                    }
                }
            } else {
                holder.binding.layoutAds.setVisibility(View.GONE);
                holder.binding.llInfo.setVisibility(View.VISIBLE);
                holder.binding.layoutOperation.setVisibility(View.VISIBLE);
            }

            if (mList.get(position).type == 1) {//视频
                //holder.binding.videoView.setVisibility(View.VISIBLE);
                holder.binding.cover.setVisibility(View.VISIBLE);
                holder.binding.btnPlay.setVisibility(View.GONE);
                holder.binding.viewpager.setVisibility(View.GONE);
                holder.binding.tvImage.setVisibility(View.GONE);
                if (mList.get(position).adsType == 0 || TextUtils.isEmpty(mList.get(position).adsTitle)) {
                    holder.binding.sb.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.sb.setVisibility(View.GONE);
                }

                if (TextUtils.isEmpty(mList.get(position).thumb)) {
                    // 如果没有封面图  取视频第一秒的帧数作为封面
                    ImageLoader.loadCover(holder.binding.cover, mList.get(position).videoUrl);
                } else {
                    ImageLoader.display(mList.get(position).thumb, holder.binding.cover, 0, 0, false,
                            null, null, new SimpleTarget<BitmapDrawable>() {
                                @Override
                                public void onResourceReady(@NonNull BitmapDrawable resource, @Nullable Transition<? super BitmapDrawable> transition) {
                                    if (resource != null) {
                                        if (resource.getIntrinsicWidth() < resource.getIntrinsicHeight()) {
                                            holder.binding.cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                        } else {
                                            holder.binding.cover.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                        }
                                        holder.binding.cover.setImageBitmap(resource.getBitmap());
                                    }
                                }
                            });
                }
            } else {
                //holder.binding.videoView.setVisibility(View.GONE);
                holder.binding.sb.setVisibility(View.GONE);
                holder.binding.cover.setVisibility(View.GONE);
                //holder.binding.btnPlay.setVisibility(View.GONE);
                holder.binding.viewpager.setVisibility(View.GONE);
                holder.binding.tvImage.setVisibility(View.GONE);
            }

            if (mList.get(position).isPrivate == 1 && mList.get(position).isPay == 0) {
                holder.binding.ivImagesShadow.setVisibility(View.VISIBLE);
                holder.binding.btnPay.setVisibility(View.VISIBLE);
                // 私密
                if (mList.get(position).type == 1) {
                    ImageLoader.displayBlur(!TextUtils.isEmpty(mList.get(position).thumb) ? mList.get(position).thumb : mList.get(position).avatar, holder.binding.ivImagesShadow);
                } else if (mList.get(position).type == 2) {
                    String[] strings = mList.get(position).images.trim().split(",");
                    if (strings.length > 0) {
                        ImageLoader.displayBlur(strings[0], holder.binding.ivImagesShadow);
                    }
                }
            } else {
                //  免费/已付费/免费次数观看过
                holder.binding.ivImagesShadow.setVisibility(View.GONE);
                holder.binding.btnPay.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemMainVideoBinding binding;

        public ViewHolder(@NonNull ItemMainVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(onVideoCallBack onItemClickListener) {
        mCallBack = onItemClickListener;
    }

    public interface onVideoCallBack {
        void onGift(ApiShortVideoDto ApiShortVideoDto);

        void onComment(ApiShortVideoDto ApiShortVideoDto, int position);

        void onShare(ApiShortVideoDto ApiShortVideoDto, int position);

        void onShop(View view, long GoodsId);
    }

    /**
     * 购买短视频
     */
    private void useReadShortVideoNumber(final long shortVideoId, int type) {
        HttpApiAppShortVideo.useReadShortVideoNumber(shortVideoId, type, new HttpApiCallBack<ApiBaseEntity>() {
            @Override
            public void onHttpRet(int code, String msg, ApiBaseEntity retModel) {
                switch (code) {
                    case 1:
//                        ToastUtil.show("购买成功");
                        break;
                    case 2:
                        SpUtil.getInstance().put(READ_SHORT_VIDEO_NUMBER, 0);

                        ToastUtil.show("观影次数不足");
                        break;
                    case 3:
                        DialogUtil.showTipsButtonDialog(mContext, retModel.msg, "立即充值", new DialogUtil.CurrencyCallback() {
                            @Override
                            public void onConfirmClick() {
                                ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });
    }

}

