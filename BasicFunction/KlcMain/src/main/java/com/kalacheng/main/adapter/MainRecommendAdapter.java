package com.kalacheng.main.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.ItemLiveBuyBinding;
import com.kalacheng.main.databinding.ItemOne2oneSmallBinding;
import com.kalacheng.main.databinding.ItemRecommendDynamicBinding;
import com.kalacheng.main.databinding.ItemRecommendLiveBinding;
import com.kalacheng.main.databinding.ItemRecommendVoiceBinding;
import com.kalacheng.main.databinding.ItemShortVideoBinding;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.VoiceAnimationUtlis;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


public class MainRecommendAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private Disposable timeDisposable;
    private MediaPlayer mediaPlayer;
    private List<AppHomeHallDTO> mList = new ArrayList<>();
    private int lastposition = -1;

    public MainRecommendAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void loadData(List<AppHomeHallDTO> data) {
        if (data != null) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void RefreshData(List<AppHomeHallDTO> data) {
        this.mList.clear();
        if (data != null) {
            this.mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 资源类型：-1:直播购（手动判断） 1 :直播 2:语音 3:1v1 4:电台 5:派对 6:短视频 7:动态
     */

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).liveType == 1 && mList.get(position).liveFunction == 1) {
            return -1;
        }
        return mList.get(position).liveType;
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 2) {//语音
            ItemRecommendVoiceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recommend_voice, parent, false);
            return new VoiceHolder(binding);
        } else if (viewType == 3) {//1v1
            ItemOne2oneSmallBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_one2one_small, parent, false);
            return new One2OneSmallViewHolder(binding);
        } else if (viewType == 6) {//短视频
            ItemShortVideoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_short_video, parent, false);
            return new ShortVideoHolder(binding);
        } else if (viewType == 7) {//动态
            ItemRecommendDynamicBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recommend_dynamic, parent, false);
            return new DynamicHolder(binding);
        } else if (viewType == -1) {//直播购
            ItemLiveBuyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_live_buy, parent, false);
            return new LiveBuyHolder(binding);
        } else {//直播
            ItemRecommendLiveBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recommend_live, parent, false);
            return new LiveViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == 2) {//语音
            ((VoiceHolder) holder).setData(mList.get(position));
        } else if (getItemViewType(position) == 3) {//1v1
            ((One2OneSmallViewHolder) holder).setData(mList.get(position));
            if (mList.get(position).oooVoice == null || mList.get(position).oooVoice.isEmpty()) {
                ((One2OneSmallViewHolder) holder).binding.rlAll.setRatio(1, 1);
                ((One2OneSmallViewHolder) holder).binding.llVoice.setVisibility(View.GONE);
            } else {
                ((One2OneSmallViewHolder) holder).binding.rlAll.setRatio(176, 214);
                ((One2OneSmallViewHolder) holder).binding.llVoice.setVisibility(View.VISIBLE);
            }
//                ((One2OneSmallViewHolder) holder).binding.seekBar.getThumb().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);//设置滑块颜色、样式
//                ((One2OneSmallViewHolder) holder).binding.seekBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);//设置进度条颜色、样式
            if (mList.get(position).isChecked != 1) {
//                    ((One2OneSmallViewHolder) holder).binding.iconVocieWheel.clearAnimation();
                ((One2OneSmallViewHolder) holder).binding.iconStart.setImageResource(R.mipmap.icon_one2one_video_start);
//                    ((One2OneSmallViewHolder) holder).binding.seekBar.setProgress(0);
                if (null != timeDisposable)
                    timeDisposable.dispose();
            }
            ((One2OneSmallViewHolder) holder).binding.iconStart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (mList.get(position).isChecked == 1) {
                        if (null != timeDisposable)
                            timeDisposable.dispose();
//                            ((One2OneSmallViewHolder) holder).binding.iconVocieWheel.clearAnimation();
                        ((One2OneSmallViewHolder) holder).binding.iconStart.setImageResource(R.mipmap.icon_one2one_video_start);
//                            ((One2OneSmallViewHolder) holder).binding.seekBar.setProgress(0);
                        if (null != mediaPlayer) {
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    } else {
                        if (lastposition == -1) {
                            lastposition = position;
                        } else {
                            if (lastposition != position) {
                                mList.get(lastposition).isChecked = 0;
                                notifyItemChanged(lastposition);
                                lastposition = position;
                                if (null != mediaPlayer) {
                                    mediaPlayer.release();
                                    mediaPlayer = null;
                                }
                            }
                        }
                        try {
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.reset();
                            mediaPlayer.setDataSource(mList.get(position).oooVoice);
                            mediaPlayer.prepareAsync();
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
//                                        ((One2OneSmallViewHolder) holder).binding.iconVocieWheel.setAnimation(rotate);
//                                        ((One2OneSmallViewHolder) holder).binding.iconVocieWheel.startAnimation(rotate);
                                    ((One2OneSmallViewHolder) holder).binding.iconStart.setImageResource(R.mipmap.icon_voice_pause);
                                    mediaPlayer.start();
                                    if (0 != mediaPlayer.getDuration()) {
                                        //更新 seekbar 长度
//                                            ((One2OneSmallViewHolder) holder).binding.seekBar.setMax(mediaPlayer.getDuration());
                                        int total = mediaPlayer.getDuration() / 1000 + 1;
                                        timeDisposable = Flowable.interval(1000, TimeUnit.MILLISECONDS).take(total).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
                                            @Override
                                            public void accept(Long aLong) throws Exception {
                                                int currentPosition = mediaPlayer.getCurrentPosition();
                                                //让进度条动起来
//                                                    ((One2OneSmallViewHolder) holder).binding.seekBar.setProgress(currentPosition);
                                            }
                                        }).doOnComplete(new Action() {
                                            @Override
                                            public void run() throws Exception {
//                                                    ((One2OneSmallViewHolder) holder).binding.iconVocieWheel.clearAnimation();
                                                ((One2OneSmallViewHolder) holder).binding.iconStart.setImageResource(R.mipmap.icon_one2one_video_start);
//                                                    ((One2OneSmallViewHolder) holder).binding.seekBar.setProgress(0);
                                                mList.get(position).isChecked = 0;

                                            }
                                        }).subscribe();
                                    }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (mList.get(position).isChecked == 1) {
                        mList.get(position).isChecked = 0;
                    } else {
                        mList.get(position).isChecked = 1;
                    }

                }
            });
        } else if (getItemViewType(position) == 6) {//短视频
            ((ShortVideoHolder) holder).setData(mList.get(position));
        } else if (getItemViewType(position) == 7) {//动态
            ((DynamicHolder) holder).setData(mList.get(position));
        } else if (getItemViewType(position) == -1) {//直播购
            ((LiveBuyHolder) holder).setData(mList.get(position));
        } else {//直播
            ((LiveViewHolder) holder).setData(mList.get(position));
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public class LiveBuyHolder extends RecyclerView.ViewHolder {
        ItemLiveBuyBinding binding;

        public LiveBuyHolder(ItemLiveBuyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final AppHomeHallDTO bean) {
            binding.setViewModel(bean);
            binding.executePendingBindings();

            binding.ReleFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    LookRoomUtlis.getInstance().getData(bean, mContext);
                }
            });
        }
    }


    public class LiveViewHolder extends RecyclerView.ViewHolder {
        ItemRecommendLiveBinding binding;

        public LiveViewHolder(ItemRecommendLiveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // 新修改的
        public void setData(final AppHomeHallDTO bean) {
            binding.setViewModel(bean);
            binding.executePendingBindings();

            binding.ReleFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    LookRoomUtlis.getInstance().getData(bean, mContext);
                }
            });

            // 是否带直播购功能的直播
            if (bean.liveFunction == 1) {
                binding.title.setBackgroundResource(R.drawable.bg_left_tag);
                binding.llLiveState.setVisibility(View.GONE);
                binding.ivShop.setVisibility(View.VISIBLE);
                binding.businessLogoIv.setVisibility(View.VISIBLE);
            } else {
                binding.title.setBackgroundResource(R.drawable.bg_tab_name);
                binding.llLiveState.setVisibility(View.VISIBLE);
                binding.ivShop.setVisibility(View.GONE);
                binding.businessLogoIv.setVisibility(View.GONE);
            }
        }
    }

    public class DynamicHolder extends RecyclerView.ViewHolder {
        ItemRecommendDynamicBinding binding;

        public DynamicHolder(ItemRecommendDynamicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final AppHomeHallDTO bean) {
            binding.setViewModel(bean);
            binding.executePendingBindings();

            binding.llAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//动态
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    //TODO
                }
            });
        }
    }

    public class ShortVideoHolder extends RecyclerView.ViewHolder {
        ItemShortVideoBinding binding;

        public ShortVideoHolder(ItemShortVideoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final AppHomeHallDTO bean) {
            binding.setViewModel(bean);
            binding.executePendingBindings();

            if (bean.dspPay == 0 && bean.isPrivate == 1) {
                binding.ivImagesShadow.setVisibility(View.VISIBLE);
                ImageLoader.displayBlur(bean.sourceCover, binding.ivImagesShadow);
            } else {
                binding.ivImagesShadow.setVisibility(View.GONE);
            }

            binding.layoutShortVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.VideoPlayActivity)
                            .withInt(ARouterValueNameConfig.VIDEO_TYPE, 4)
                            .withInt(ARouterValueNameConfig.VIDEO_WORKS_TYPE, Integer.parseInt(bean.showid))
                            .navigation();
                }
            });
        }
    }

    public class VoiceHolder extends RecyclerView.ViewHolder {
        ItemRecommendVoiceBinding binding;

        public VoiceHolder(ItemRecommendVoiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final AppHomeHallDTO bean) {
            binding.setViewModel(bean);
            binding.executePendingBindings();
            VoiceAnimationUtlis.getInstance().showAnimation(binding.ivVoiceAnimation);
            binding.rlAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    LookRoomUtlis.getInstance().getData(bean, mContext);
                }
            });
        }
    }

    public class One2OneSmallViewHolder extends RecyclerView.ViewHolder {
        ItemOne2oneSmallBinding binding;

        public One2OneSmallViewHolder(ItemOne2oneSmallBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final AppHomeHallDTO bean) {
            binding.setViewModel(bean);
            binding.executePendingBindings();
//            viewModel.sex==2 ? R.mipmap.icon_girl_white : R.mipmap.icon_boy_white
            if (bean.sex == 2) {
                binding.llLevel.setBackgroundResource(R.drawable.bg_sex_girl);
            } else {
                binding.llLevel.setBackgroundResource(R.drawable.bg_sex_boy);
            }

            binding.rlAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.userId).navigation();
                }
            });
            if (bean.sourceState == 0) {
                binding.ivLiveState.setImageResource(R.drawable.darkgrey_oval);
            } else if (bean.sourceState == 1) {
                binding.ivLiveState.setImageResource(R.drawable.red_oval);
            } else if (bean.sourceState == 2) {
                binding.ivLiveState.setImageResource(R.drawable.green_oval);
            } else if (bean.sourceState == 3) {
                binding.ivLiveState.setImageResource(R.drawable.blue_oval);
            } else if (bean.sourceState == 4) {
                binding.ivLiveState.setImageResource(R.drawable.red_oval);
            } else if (bean.sourceState == 5) {
                binding.ivLiveState.setImageResource(R.drawable.red_oval);
            } else if (bean.sourceState == 6) {
                binding.ivLiveState.setImageResource(R.drawable.blue_oval);
            } else {
                binding.ivLiveState.setImageResource(R.drawable.lightgrey_oval);
            }
        }
    }
}
