package com.kalacheng.util.lib.imageview.gesture.transition.internal;

import android.graphics.Rect;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.kalacheng.util.lib.imageview.gesture.animation.ViewPositionAnimator;
import com.kalacheng.util.lib.imageview.gesture.transition.ViewsCoordinator;
import com.kalacheng.util.lib.imageview.gesture.transition.ViewsTracker;
import com.kalacheng.util.lib.imageview.gesture.transition.ViewsTransitionAnimator;

public class FromListViewListener<ID> implements ViewsCoordinator.OnRequestViewListener<ID> {

    private static final Rect locationParent = new Rect();
    private static final Rect locationChild = new Rect();

    private final ListView listView;
    private final ViewsTracker<ID> tracker;
    private final ViewsTransitionAnimator<ID> animator;

    private ID id;
    private boolean scrollHalfVisibleItems;

    public FromListViewListener(@NonNull ListView listView,
                                @NonNull ViewsTracker<ID> tracker,
                                @NonNull ViewsTransitionAnimator<ID> animator) {
        this.listView = listView;
        this.tracker = tracker;
        this.animator = animator;

        this.listView.setOnScrollListener(new ScrollListener());
        this.animator.addPositionUpdateListener(new UpdateListener());
    }

    @Override
    public void onRequestView(@NonNull ID id) {
        // Trying to find requested view on screen. If it is not currently on screen
        // or it is not fully visible then we should scroll to it at first.
        this.id = id;
        int position = tracker.getPositionForId(id);

        if (position == ViewsTracker.NO_POSITION) {
            animator.setFromNone(id);
            return;
        }

        View view = tracker.getViewForPosition(position);
        if (view == null) {
            listView.setSelection(position);
        } else {
            animator.setFromView(id, view);

            if (scrollHalfVisibleItems) {
                listView.getGlobalVisibleRect(locationParent);
                locationParent.left += listView.getPaddingLeft();
                locationParent.right -= listView.getPaddingRight();
                locationParent.top += listView.getPaddingTop();
                locationParent.bottom -= listView.getPaddingBottom();

                view.getGlobalVisibleRect(locationChild);
                if (!locationParent.contains(locationChild)
                        || view.getWidth() > locationChild.width()
                        || view.getHeight() > locationChild.height()) {
                    listView.setSelection(position);
                }
            }
        }
    }

    private class ScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
            if (id == null) {
                return; // Nothing to do
            }
            for (int position = firstVisible; position < firstVisible + visibleCount; position++) {
                if (id.equals(tracker.getIdForPosition(position))) {
                    View from = tracker.getViewForPosition(position);
                    if (from != null) {
                        animator.setFromView(id, from);
                    }
                }
            }
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // No-op
        }
    }

    private class UpdateListener implements ViewPositionAnimator.PositionUpdateListener {
        @Override
        public void onPositionUpdate(float pos, boolean isLeaving) {
            if (pos == 0f && isLeaving) {
                id = null;
            }
            listView.setVisibility(pos == 1f && !isLeaving ? View.INVISIBLE : View.VISIBLE);
            scrollHalfVisibleItems = pos == 1f; // Only scroll if we in full mode
        }
    }

}
