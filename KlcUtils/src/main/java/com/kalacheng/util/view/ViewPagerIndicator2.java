package com.kalacheng.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.util.R;
import com.kalacheng.util.adapter.ViewPagerIndicatorAdapter;

import java.util.Arrays;
import java.util.List;

public class ViewPagerIndicator2 extends FrameLayout {
    private RecyclerView recyclerView;
    private ViewPagerIndicatorAdapter adapter;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private OnTabClickListener onTabClickListener;

    public ViewPagerIndicator2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_view_pager_indicator, this, true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new ViewPagerIndicatorAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDecoration(getContext(), 0x00000000, 0, 0));

        adapter.setViewPagerIndicatorListener(new ViewPagerIndicatorAdapter.ViewPagerIndicatorListener() {
            @Override
            public void onItemClick(String name, int position) {
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(position, name);
                }
                if (mViewPager != null && mViewPager.getCurrentItem() != position) {
                    mViewPager.setCurrentItem(position, true);
                }
            }
        });
        mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.setSelectPosition(position);
                recyclerView.smoothScrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator2);
        adapter.setNormalColor(a.getColor(R.styleable.ViewPagerIndicator2_normalColor2, Color.BLACK));
        adapter.setLightColor(a.getColor(R.styleable.ViewPagerIndicator2_lightColor2, Color.BLACK));
        adapter.setNormalBold(a.getBoolean(R.styleable.ViewPagerIndicator2_normalBold, false));
        adapter.setLightBold(a.getBoolean(R.styleable.ViewPagerIndicator2_lightBold, false));
        adapter.setNormalTextSize(a.getInteger(R.styleable.ViewPagerIndicator2_normalTextSize, 12));
        adapter.setLightTextSize(a.getInteger(R.styleable.ViewPagerIndicator2_lightTextSize, 12));
        adapter.setPadding(a.getInteger(R.styleable.ViewPagerIndicator2_padding, 1));
        adapter.setIndicatorShow(a.getBoolean(R.styleable.ViewPagerIndicator2_indicatorShow, false));
        adapter.setIndicatorWidth(a.getInteger(R.styleable.ViewPagerIndicator2_indicatorWidth2, 0));
        adapter.setIndicatorHeight(a.getInteger(R.styleable.ViewPagerIndicator2_indicatorHeight2, 0));
        adapter.setIndicatorColor(a.getColor(R.styleable.ViewPagerIndicator2_indicatorColor2, Color.BLACK));
        adapter.setIndicatorMarginBottom(a.getInteger(R.styleable.ViewPagerIndicator2_indicatorMarginBottom, 0));
        a.recycle();

    }

    public void setTitles(String[] titles) {
        adapter.setData(Arrays.asList(titles));
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    public void setSelect(int currentItem) {
        adapter.setSelectPosition(currentItem);
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    public interface OnTabClickListener {
        void onTabClick(int position, String name);
    }

    public List<String> getTitle() {
        return adapter.getTitle();
    }
}
