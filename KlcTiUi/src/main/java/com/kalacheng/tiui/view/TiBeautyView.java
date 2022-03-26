package com.kalacheng.tiui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.kalacheng.tiui.fragment.TiMaskFragment;
import com.kalacheng.tiui.fragment.TiStickerFragment;
import com.kalacheng.tiui.fragment.TiWatermarkFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiTypeEnum;
import com.kalacheng.tiui.R;
import com.kalacheng.tiui.fragment.TiBeautyFragment;
import com.kalacheng.tiui.fragment.TiDistortionFragment;
import com.kalacheng.tiui.fragment.TiFaceTrimFragment;
import com.kalacheng.tiui.fragment.TiFilterFragment;
import com.kalacheng.tiui.fragment.TiGiftFragment;
import com.kalacheng.tiui.fragment.TiMakeupFragment;
import com.kalacheng.tiui.fragment.TiRockFragment;
import com.kalacheng.tiui.model.RxBusAction;

/**
 * Created by Anko on 2018/5/12.
 * Copyright (c) 2018-2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiBeautyView extends LinearLayout {

    private TiSDKManager tiSDKManager;
    private ScrollIndicatorView tiIndicatorView;

    private TiBarView tiBarView;
//    private TiMakeupView tiMakeupView;

    private LinearLayout tiBeautyLL;
    private LinearLayout tiEnableLL;
    private TextView tiEnableTV;
    private ImageView tiEnableIV;
    private ViewPager tiViewPager;
    private ImageView tiRenderEnableIV;

    private List<TiTypeEnum> tiTabs = new ArrayList<>();

    private boolean isBeautyEnable = false;
    private boolean isFaceTrimEnable = false;

    public TiBeautyView(Context context) {
        super(context);
    }

    public TiBeautyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TiBeautyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TiBeautyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TiBeautyView init(@NonNull TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;

        RxBus.get().register(this);

        initView();

        initData();

        return this;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        RxBus.get().unregister(this);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_ti_beauty, this);

        tiBarView = findViewById(R.id.tiBarView);
        tiBarView.init(tiSDKManager);

//        tiMakeupView = findViewById(R.id.tiMakeupView);
//        tiMakeupView.init(tiSDKManager);

        tiIndicatorView = findViewById(R.id.tiIndicatorView);

        tiBeautyLL = findViewById(R.id.tiBeautyLL);
        tiEnableLL = findViewById(R.id.tiEnableLL);
        tiEnableTV = findViewById(R.id.tiEnableTV);
        tiEnableIV = findViewById(R.id.tiEnableIV);
        tiViewPager = findViewById(R.id.tiViewPager);
        tiRenderEnableIV = findViewById(R.id.tiRenderEnableIV);
    }

    private void initData() {
        tiRenderEnableIV.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        tiSDKManager.renderEnable(false);
                        return true;
                    case MotionEvent.ACTION_UP:
                        tiSDKManager.renderEnable(true);
                        return true;
                }
                return false;
            }
        });

        //屏蔽点击事件
        setOnClickListener(null);

        isBeautyEnable = tiSDKManager.isBeautyEnable();
        isFaceTrimEnable = tiSDKManager.isFaceTrimEnable();

        tiEnableIV.setSelected(isBeautyEnable);
        tiEnableTV.setSelected(isBeautyEnable);
        tiEnableTV.setText(isBeautyEnable ? R.string.ti_beauty_on : R.string.ti_beauty_off);

        tiEnableLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tiViewPager.getCurrentItem()) {
                    case 0://美颜
                        isBeautyEnable = !isBeautyEnable;
                        tiSDKManager.setBeautyEnable(isBeautyEnable);
                        tiEnableIV.setSelected(isBeautyEnable);
                        tiEnableTV.setSelected(isBeautyEnable);
                        tiEnableTV.setText(isBeautyEnable ? R.string.ti_beauty_on : R.string.ti_beauty_off);
                        tiBarView.selectBeauty();

                        break;
                    case 1://美型
                        isFaceTrimEnable = !isFaceTrimEnable;
                        tiSDKManager.setFaceTrimEnable(isFaceTrimEnable);
                        tiEnableIV.setSelected(isFaceTrimEnable);
                        tiEnableTV.setSelected(isFaceTrimEnable);
                        tiEnableTV.setText(isFaceTrimEnable ? R.string.ti_face_trim_on : R.string.ti_face_trim_off);
                        tiBarView.selectFaceTrim();
                        break;
                }
            }
        });

        tiTabs.clear();
        tiTabs.add(TiTypeEnum.Beauty);
        tiTabs.add(TiTypeEnum.FaceTrim);
        tiTabs.add(TiTypeEnum.Sticker);
        tiTabs.add(TiTypeEnum.Gift);
        tiTabs.add(TiTypeEnum.Filter);
        tiTabs.add(TiTypeEnum.Rock);
        tiTabs.add(TiTypeEnum.Distortion);
//        tiTabs.add(TiTypeEnum.Watermark);
//        tiTabs.add(TiTypeEnum.Mask);
//        tiTabs.add(TiTypeEnum.Makeup);

        tiViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position != 9) {
                    tiBeautyLL.setVisibility(VISIBLE);
//                    tiMakeupView.setVisibility(GONE);
                }

                tiIndicatorView.getIndicatorAdapter().notifyDataSetChanged();

                switch (position) {
                    case 0:
                        tiEnableLL.setVisibility(VISIBLE);

                        tiBarView.selectBeauty();

                        tiEnableIV.setSelected(isBeautyEnable);
                        tiEnableTV.setSelected(isBeautyEnable);
                        tiEnableTV.setText(isBeautyEnable ? R.string.ti_beauty_on : R.string.ti_beauty_off);
                        break;
                    case 1:
                        tiEnableLL.setVisibility(VISIBLE);

                        tiBarView.selectFaceTrim();

                        tiEnableIV.setSelected(isFaceTrimEnable);
                        tiEnableTV.setSelected(isFaceTrimEnable);
                        tiEnableTV.setText(isFaceTrimEnable ? R.string.ti_face_trim_on : R.string.ti_face_trim_off);
                        break;
                    case 4:
                        tiEnableLL.setVisibility(GONE);

                        tiBarView.selectFilter();
                        break;
                    default:
                        tiEnableLL.setVisibility(GONE);

                        tiBarView.hideSeekBar();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tiIndicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.ti_selected), getResources().getColor(R.color.ti_unselected)));
        tiIndicatorView.setSplitAuto(false);
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(tiIndicatorView, tiViewPager);
        indicatorViewPager.setPageOffscreenLimit(8);
        IndicatorViewPager.IndicatorFragmentPagerAdapter fragmentPagerAdapter =
                new IndicatorViewPager.IndicatorFragmentPagerAdapter(((FragmentActivity) getContext()).getSupportFragmentManager()) {
                    @Override
                    public int getCount() {
                        return tiTabs.size();
                    }

                    @Override
                    public View getViewForTab(int position, View convertView, ViewGroup container) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ti_tab, container, false);
                        }
                        if (position == 0) {
                            MarginLayoutParams p = (MarginLayoutParams) convertView.getLayoutParams();
                            p.setMargins((int) (convertView.getContext().getResources().getDisplayMetrics().density * 17 + 0.5f), 0, 0, 0);
                            convertView.requestLayout();
                        } else {
                            MarginLayoutParams p = (MarginLayoutParams) convertView.getLayoutParams();
                            p.setMargins(0, 0, 0, 0);
                            convertView.requestLayout();
                        }

                        ((TextView) convertView).setText(tiTabs.get(position).getString(convertView.getContext()));
                        return convertView;
                    }

                    @Override
                    public Fragment getFragmentForPage(int position) {
                        switch (position) {
                            case 0://美颜
                                return new TiBeautyFragment();
                            case 1://美型
                                return new TiFaceTrimFragment();
                            case 2://贴纸
                                return new TiStickerFragment().setTiSDKManager(tiSDKManager);
                            case 3://礼物
                                return new TiGiftFragment().setTiSDKManager(tiSDKManager);
                            case 4://滤镜
                                return new TiFilterFragment();
                            case 5://抖音
                                return new TiRockFragment().setTiSDKManager(tiSDKManager);
                            case 6://哈哈镜
                                return new TiDistortionFragment().setTiSDKManager(tiSDKManager);
                            case 7://水印
                                return new TiWatermarkFragment().setTiSDKManager(tiSDKManager);
                            case 8://面具
                                return new TiMaskFragment().setTiSDKManager(tiSDKManager);
                            case 9://美妆
                                return new TiMakeupFragment();
                        }
                        return null;
                    }
                };

        indicatorViewPager.setAdapter(fragmentPagerAdapter);

        //初始化默认选中美颜功能中的美白
        RxBus.get().post(RxBusAction.ACTION_SKIN_WHITENING);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void showMakeupView(String action) {
        switch (action) {
            case RxBusAction.ACTION_MAKEUP_BACK:
                tiBeautyLL.setVisibility(VISIBLE);
//                tiMakeupView.setVisibility(GONE);
                break;
            case RxBusAction.ACTION_MAKEUP_NO:
                tiSDKManager.enableMakeup(false);
                break;
            case RxBusAction.ACTION_LIP_GLOSS:
            case RxBusAction.ACTION_EYE_SHADOW:
            case RxBusAction.ACTION_BROW_PENCIL:
            case RxBusAction.ACTION_BLUSHER:
                tiSDKManager.enableMakeup(true);
                tiBeautyLL.setVisibility(GONE);
//                tiMakeupView.setVisibility(VISIBLE);
                break;
        }
    }
}
