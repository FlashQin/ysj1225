package com.kalacheng.util.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * ViewPager所需Adapter（可实现循环）
 *
 * @version <android.support.v4.view.ViewPager android:id="@+id/img_advert"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent" />
 * <p>
 * private ViewPager viewPager;
 * private BasePagerAdapter adapter;
 * private List<View> viewList = new ArrayList<View>();
 * <p>
 * adapter = new BasePagerAdapter(viewList);
 * adapter.isCycleFlow(true);
 * viewPager.setAdapter(adapter);
 * viewPager.setCurrentItem(viewPager.getAdapter().getCount() / 2 - viewPager.getAdapter().getCount() / 2 % viewList.size());
 * viewPager.setOnPageChangeListener(new GuidePageChangeListener());
 * class GuidePageChangeListener implements OnPageChangeListener {
 * @Override public void onPageScrollStateChanged(int arg0) {
 * }
 * @Override public void onPageScrolled(int arg0, float arg1, int arg2) {
 * }
 * @Override public void onPageSelected(int arg0) {
 * Toast.makeText(MainActivity.this, adapter.getRealPosition(arg0) + "", Toast.LENGTH_SHORT).show();
 * }
 * }
 * <p>
 * Created by ysj on 2016/11/18.
 */

public class BasePagerAdapter extends PagerAdapter {
    private final List<View> viewCache;
    private boolean isCycleFlow = false;

    public BasePagerAdapter(List<View> views) {
        this.viewCache = views;
    }

    /**
     * 是否循环滚动，默认false
     *
     * @param isCycleFlow
     */
    public void isCycleFlow(boolean isCycleFlow) {
        this.isCycleFlow = isCycleFlow;
    }

    public int getRealCount() {
        return viewCache.size();
    }

    public int getRealPosition(int position) {
        return position % getRealCount();
    }

    @Override
    public int getCount() {
        int count = viewCache.size();
        if (isCycleFlow) {
            if (count > 1) {
                // 做循环时count值不能过大，否则在调用setcurrentItem方法时会假死
                count = Short.MAX_VALUE;
            }
        }
        return count;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewCache.get(getRealPosition(position));
        if (isCycleFlow) {
            ViewGroup p = (ViewGroup) view.getParent();
            if (p != null) {
                p.removeView(view);
            }
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (!isCycleFlow) {
            container.removeView((View) object);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}