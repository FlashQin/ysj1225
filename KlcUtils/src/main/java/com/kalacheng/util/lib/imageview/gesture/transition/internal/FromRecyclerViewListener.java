package com.kalacheng.util.lib.imageview.gesture.transition.internal;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.lib.imageview.gesture.animation.ViewPositionAnimator;
import com.kalacheng.util.lib.imageview.gesture.transition.ViewsCoordinator;
import com.kalacheng.util.lib.imageview.gesture.transition.ViewsTracker;
import com.kalacheng.util.lib.imageview.gesture.transition.ViewsTransitionAnimator;

public class FromRecyclerViewListener<ID> implements ViewsCoordinator.OnRequestViewListener<ID> {

    private static final Rect locationParent = new Rect();
    private static final Rect locationChild = new Rect();

    private final RecyclerView recyclerView;
    private final ViewsTracker<ID> tracker;
    private final ViewsTransitionAnimator<ID> animator;

    private ID id;
    private boolean scrollHalfVisibleItems;

    public FromRecyclerViewListener(@NonNull RecyclerView recyclerView,
                                    @NonNull ViewsTracker<ID> tracker,
                                    @NonNull ViewsTransitionAnimator<ID> animator) {
        this.recyclerView = recyclerView;
        this.tracker = tracker;
        this.animator = animator;

        this.recyclerView.addOnChildAttachStateChangeListener(new ChildStateListener());
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
            recyclerView.smoothScrollToPosition(position);
        } else {
            animator.setFromView(id, view);

            if (scrollHalfVisibleItems) {
                recyclerView.getGlobalVisibleRect(locationParent);
                locationParent.left += recyclerView.getPaddingLeft();
                locationParent.right -= recyclerView.getPaddingRight();
                locationParent.top += recyclerView.getPaddingTop();
                locationParent.bottom -= recyclerView.getPaddingBottom();

                view.getGlobalVisibleRect(locationChild);
                if (!locationParent.contains(locationChild)
                        || view.getWidth() > locationChild.width()
                        || view.getHeight() > locationChild.height()) {
                    recyclerView.smoothScrollToPosition(position);
                }
            }
        }
    }

    private class ChildStateListener implements RecyclerView.OnChildAttachStateChangeListener {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            int position = recyclerView.getChildAdapterPosition(view);
            if (id != null && id.equals(tracker.getIdForPosition(position))) {
                View from = tracker.getViewForPosition(position);
                if (from != null) {
                    animator.setFromView(id, from);
                }
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
            // No-op
        }
    }

    private class UpdateListener implements ViewPositionAnimator.PositionUpdateListener {
        @Override
        public void onPositionUpdate(float pos, boolean isLeaving) {
            if (pos == 0f && isLeaving) {
                id = null;
            }
            recyclerView.setVisibility(pos == 1f && !isLeaving ? View.INVISIBLE : View.VISIBLE);
            scrollHalfVisibleItems = pos == 1f; // Only scroll if we in full mode
        }
    }

}
