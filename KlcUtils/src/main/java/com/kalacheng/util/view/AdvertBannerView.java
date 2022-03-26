package com.kalacheng.util.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.util.R;
import com.kalacheng.util.adapter.BasePagerAdapter;
import com.kalacheng.util.utils.DpUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <com.yy.view.YYAdvertBannerView
 * android:id="@+id/advertBannerView"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent" />
 * <p>
 * <p>
 * List<View> viewList = new ArrayList<>();
 * ImageView imageView = new ImageView(MainActivity.this);
 * imageView.setScaleType(ScaleType.FIT_XY);
 * imageView.setImageResource(R.mipmap.icon_main_banner);
 * viewList.add(imageView);
 * advertBannerView.addViews(viewList);
 * <p>
 * <p>
 * 广告横幅控件
 * Created by ysj on 2017/2/23.
 */

public class AdvertBannerView extends FrameLayout {
    /**
     * 广告位
     */
    private ViewPager viewPagerAdvert;
    /**
     * 标点位
     */
    private LinearLayout layoutDots;
    /**
     * 广告位Adapter
     */
    private BasePagerAdapter adapter;
    /**
     * 广告位图片
     */
    private final List<View> viewList = new ArrayList<>();
    /**
     * 图片真实索引号
     */
    private int picItem;
    /**
     * 蓝色小点索引
     */
    private int dotItem = 0;
    /**
     * 滚动间隔时间
     */
    private int durationTime = 5000;
    /**
     * Context
     */
    private Context context;

    public AdvertBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_advert_banner, this, true);

        viewPagerAdvert = (ViewPager) findViewById(R.id.viewPagerAdvert);
        layoutDots = (LinearLayout) findViewById(R.id.layoutDots);

        //滑屏速度控制
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(context);
            scroller.setDuration(1000);
            mScroller.set(viewPagerAdvert, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加显示内容
     */
    public void addViews(List<View> views) {
        stopAd();
        viewList.clear();
        viewList.addAll(views);
        adapter = new BasePagerAdapter(viewList);
        adapter.isCycleFlow(true);
        viewPagerAdvert.setAdapter(adapter);
        picItem = viewPagerAdvert.getAdapter().getCount() / 2 - viewPagerAdvert.getAdapter().getCount() / 2 % viewList.size();
        viewPagerAdvert.setCurrentItem(picItem);
        viewPagerAdvert.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                picItem = position;
                dotItem = adapter.getRealPosition(picItem);
                showDots();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerAdvert.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    stopAd();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    stopAd();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startAd();
                }
                return false;
            }
        });
        dotItem = adapter.getRealPosition(picItem);
        showDots();
        startAd();
    }

    /**
     * 设置滚动间隔时间
     */
    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    /**
     * 显示广告位小点
     */
    private void showDots() {
        layoutDots.removeAllViews();
        if (viewList.size() <= 1) {
            return;
        }
        for (int i = 0; i < viewList.size(); i++) {
            View view = new View(context);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(DpUtil.dp2px(6), DpUtil.dp2px(6));
            p.leftMargin = DpUtil.dp2px(3);
            p.rightMargin = DpUtil.dp2px(3);
            if (i == dotItem) {
                view.setBackgroundResource(R.drawable.dot_focused);
            } else {
                view.setBackgroundResource(R.drawable.dot_normal);
            }
            layoutDots.addView(view, p);
        }
    }

    /**
     * 实现广告位循环播放
     */
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 要做的事情
            picItem++;
            if (picItem > Short.MAX_VALUE - 2) {
                picItem = viewPagerAdvert.getAdapter().getCount() / 2 - viewPagerAdvert.getAdapter().getCount() / 2 % viewList.size();
            }
            viewPagerAdvert.setCurrentItem(picItem);
            dotItem = adapter.getRealPosition(picItem);
            showDots();
            handler.postDelayed(this, durationTime);// 有此语句则循环执行,否则只执行一次.
        }
    };

    /**
     * 开始广告播放
     */
    private void startAd() {
        handler.postDelayed(runnable, durationTime);
    }

    /**
     * 结束广告播放
     */
    private void stopAd() {
        handler.removeCallbacks(runnable);
    }

    /**
     * 滑屏速度控制
     */
    public class FixedSpeedScroller extends Scroller {
        /**
         * 滑动时间
         */
        private int mDuration = 0;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        /**
         * 设置滑动时间
         */
        public void setDuration(int duration) {
            this.mDuration = duration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            if (mDuration != 0) {
                super.startScroll(startX, startY, dx, dy, mDuration);
            } else {
                super.startScroll(startX, startY, dx, dy);
            }
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            if (mDuration != 0) {
                super.startScroll(startX, startY, dx, dy, mDuration);
            } else {
                super.startScroll(startX, startY, dx, dy, duration);
            }
        }
    }
}
