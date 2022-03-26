package com.kalacheng.live.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.kalacheng.live.R;
import com.kalacheng.util.bean.SimpleImageSrcTextBean;

import java.util.ArrayList;
import java.util.List;

public class LiveFunctionPagerAdapter extends PagerAdapter {
    private List<RecyclerView> mViewList;
    private static final int FUNCTION_COUNT = 8;//每页8个功能

    public LiveFunctionPagerAdapter(Context context, List<SimpleImageSrcTextBean> functionList) {
        mViewList = new ArrayList<>();
        int fromIndex = 0;
        int size = functionList.size();
        int pageCount = size / FUNCTION_COUNT;
        if (size % FUNCTION_COUNT > 0) {
            pageCount++;
        }

        LayoutInflater inflater = LayoutInflater.from(context);

        for (int i = 0; i < pageCount; i++) {
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.view_recycleview, null, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 4, RecyclerView.VERTICAL, false));
            int endIndex = fromIndex + FUNCTION_COUNT;
            if (endIndex > size) {
                endIndex = size;
            }
            List<SimpleImageSrcTextBean> list = new ArrayList<>();
            for (int j = fromIndex; j < endIndex; j++) {
                SimpleImageSrcTextBean bean = functionList.get(j);
                list.add(bean);
            }
            LiveFunctionAdapter adapter = new LiveFunctionAdapter(context, list);
            recyclerView.setAdapter(adapter);
            mViewList.add(recyclerView);
            fromIndex = endIndex;
        }

    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }
}
