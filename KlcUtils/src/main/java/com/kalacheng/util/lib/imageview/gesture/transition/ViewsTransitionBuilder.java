package com.kalacheng.util.lib.imageview.gesture.transition;

import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.util.lib.imageview.gesture.transition.internal.FromListViewListener;
import com.kalacheng.util.lib.imageview.gesture.transition.internal.FromRecyclerViewListener;
import com.kalacheng.util.lib.imageview.gesture.transition.internal.IntoViewPagerListener;

public class ViewsTransitionBuilder<ID> {

    private final ViewsTransitionAnimator<ID> animator = new ViewsTransitionAnimator<>();

    public ViewsTransitionBuilder<ID> fromRecyclerView(@NonNull RecyclerView recyclerView,
                                                       @NonNull ViewsTracker<ID> tracker) {
        animator.setFromListener(new FromRecyclerViewListener<>(recyclerView, tracker, animator));
        return this;
    }

    public ViewsTransitionBuilder<ID> fromListView(@NonNull ListView listView,
                                                   @NonNull ViewsTracker<ID> tracker) {
        animator.setFromListener(new FromListViewListener<>(listView, tracker, animator));
        return this;
    }

    public ViewsTransitionBuilder<ID> intoViewPager(@NonNull ViewPager viewPager,
                                                    @NonNull ViewsTracker<ID> helper) {
        animator.setToListener(new IntoViewPagerListener<>(viewPager, helper, animator));
        return this;
    }

    public ViewsTransitionAnimator<ID> build() {
        return animator;
    }

}
