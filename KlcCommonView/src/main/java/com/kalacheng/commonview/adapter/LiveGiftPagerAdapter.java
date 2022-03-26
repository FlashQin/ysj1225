package com.kalacheng.commonview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.NobLiveGift;

import java.util.ArrayList;
import java.util.List;

public class LiveGiftPagerAdapter extends PagerAdapter {
    private List<RecyclerView> mViewList;
    private static final int GIFT_COUNT = 8;//每页10个礼物
    PageGiftClick pageGiftClick;
    int currentPage, lastPage;
    int currentPosition, lastPosition;

    /**
     * 刷新数据信息
     */
    public void refreshNumber() {
        if (mViewList != null && mViewList.size() > 0) {
            for (RecyclerView recyclerView : mViewList) {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @SuppressLint("WrongConstant")
    public LiveGiftPagerAdapter(Context context, List<NobLiveGift> giftList) {
        mViewList = new ArrayList<>();
        int fromIndex = 0;
        int size = giftList.size();
        int pageCount = size / GIFT_COUNT;
        if (size % GIFT_COUNT > 0) {
            pageCount++;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < pageCount; i++) {
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.view_gift_page, null, false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false));
            int endIndex = fromIndex + GIFT_COUNT;
            if (endIndex > size) {
                endIndex = size;
            }
            List<NobLiveGift> list = new ArrayList<>();
            for (int j = fromIndex; j < endIndex; j++) {
                NobLiveGift bean = giftList.get(j);
                bean.page = i;
                list.add(bean);
            }
            LiveGiftAdapter adapter = new LiveGiftAdapter(list);
            recyclerView.setAdapter(adapter);
            adapter.setLastClickListener(new LiveGiftAdapter.GiftClickListener() {
                @Override
                public void onGiftClickListener(int position, NobLiveGift item) {
                    lastPage = currentPage;
                    lastPosition = currentPosition;
                    currentPage = item.page;
                    currentPosition = position;
                    if (lastPage != currentPage || lastPosition != currentPosition) {
                        LiveGiftAdapter adapter1 = (LiveGiftAdapter) mViewList.get(lastPage).getAdapter();
                        adapter1.getList().get(lastPosition).checked = 0;
                        adapter1.notifyItemChanged(lastPosition);
                    }
                    pageGiftClick.onPageGiftClick(item);
                }

            });
            mViewList.add(recyclerView);
            fromIndex = endIndex;
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
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

    public void release() {
        if (mViewList != null) {
            for (RecyclerView recyclerView : mViewList) {
                LiveGiftAdapter adapter = (LiveGiftAdapter) recyclerView.getAdapter();
                if (adapter != null) {
                    adapter.release();
                }
            }
        }
    }

    public void setPageGiftClick(PageGiftClick pageGiftClick) {
        this.pageGiftClick = pageGiftClick;
    }

    public interface PageGiftClick {
        void onPageGiftClick(NobLiveGift item);
    }

}
