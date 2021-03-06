package com.kalacheng.util.lib.imageview.gesture.transition;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.kalacheng.util.lib.imageview.gesture.animation.ViewPosition;
import com.kalacheng.util.lib.imageview.gesture.animation.ViewPositionAnimator;
import com.kalacheng.util.lib.imageview.gesture.animation.ViewPositionAnimator.PositionUpdateListener;
import com.kalacheng.util.lib.imageview.gesture.internal.GestureDebug;
import com.kalacheng.util.lib.imageview.gesture.views.interfaces.AnimatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link #exit(boolean)} animations, keeps track of {@link PositionUpdateListener} listeners
 * and provides correct implementation of {@link #isLeaving()}.
 * <p/>
 * Usage of this class should be similar to {@link ViewPositionAnimator} class.
 */
public class ViewsTransitionAnimator<ID> extends ViewsCoordinator<ID> {

    private static final String TAG = ViewsTransitionAnimator.class.getSimpleName();

    private final List<PositionUpdateListener> listeners = new ArrayList<>();

    private ID enterId;
    private boolean isReady;
    private boolean enterWithAnimation;

    private boolean exitRequested;
    private boolean exitWithAnimation;

    @SuppressWarnings("WeakerAccess") // Public API
    public ViewsTransitionAnimator() {
        addPositionUpdateListener(new PositionUpdateListener() {
            @Override
            public void onPositionUpdate(float position, boolean isLeaving) {
                if (position == 0f && isLeaving) {
                    clear();
                }
            }
        });
    }

    /**
     * Requests 'from' and 'to' views for given id
     * and starts enter animation when both views are ready.
     */
    public void enter(@NonNull ID id, boolean withAnimation) {
        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Enter requested for " + id + ", with animation = " + withAnimation);
        }

        clear();
        enterId = id;
        enterWithAnimation = withAnimation;
        request(id);
    }

    /**
     * Plays exit animation, should only be called after corresponding call to
     *
     * @see #isLeaving()
     */
    public void exit(boolean withAnimation) {
        if (enterId == null) {
            throw new IllegalStateException("You should call enter(...) before calling exit(...)");
        }

        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Exit requested from " + enterId + ", with animation = " + withAnimation);
        }

        exitRequested = true;
        exitWithAnimation = withAnimation;
        exitIfRequested();
    }

    private void exitIfRequested() {
        if (exitRequested && isReady) {
            exitRequested = false;

            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Perform exit from " + enterId);
            }

            getToView().getPositionAnimator().exit(exitWithAnimation);
        }
    }

    /**
     * @return Whether 'enter' was not requested recently or animator is in leaving state.
     * Means that animation direction is from final (to) position back to initial (from) position.
     */
    public boolean isLeaving() {
        return exitRequested || enterId == null || (isReady
                && getToView() != null && getToView().getPositionAnimator().isLeaving());
    }

    @Override
    public void setFromNone(@NonNull ID id) {
        if (enterId == null || !enterId.equals(id)) {
            return;
        }

        super.setFromNone(id);
    }

    @Override
    public void setFromView(@NonNull ID id, @NonNull View fromView) {
        if (enterId == null || !enterId.equals(id)) {
            return;
        }

        boolean wasReady = isReady;

        super.setFromView(id, fromView);

        if (wasReady) {
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Updating 'from' view for " + enterId);
            }
            getToView().getPositionAnimator().update(fromView);
        }
    }

    @Override
    public void setFromPos(@NonNull ID id, @NonNull ViewPosition fromPos) {
        if (enterId == null || !enterId.equals(id)) {
            return;
        }

        boolean wasReady = isReady;

        super.setFromPos(id, fromPos);

        if (wasReady) {
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Updating 'from' pos for " + enterId);
            }
            getToView().getPositionAnimator().update(fromPos);
        }
    }

    @Override
    public void setToView(@NonNull ID id, @NonNull AnimatorView toView) {
        if (enterId == null || !enterId.equals(id)) {
            return;
        }

        AnimatorView old = getToView();

        if (old != toView) {
            if (old != null && isReady) {
                // Animation is in place, we should carefully swap animators
                swapAnimator(old.getPositionAnimator(), toView.getPositionAnimator());
            } else {
                if (old != null) {
                    cleanupAnimator(old.getPositionAnimator());
                }
                initAnimator(toView.getPositionAnimator());
            }
        }

        super.setToView(id, toView);
    }

    @Override
    protected void onViewsReady(@NonNull ID id) {
        if (enterId == null || !enterId.equals(id)) {
            return;
        }

        if (!isReady) {
            isReady = true;

            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Ready to enter for " + enterId);
            }

            if (getFromView() != null) {
                getToView().getPositionAnimator().enter(getFromView(), enterWithAnimation);
            } else if (getFromPos() != null) {
                getToView().getPositionAnimator().enter(getFromPos(), enterWithAnimation);
            } else {
                getToView().getPositionAnimator().enter(enterWithAnimation);
            }

            exitIfRequested();
        }

        super.onViewsReady(id);
    }

    /**
     * Adds listener to the set of position updates listeners that will be notified during
     * any position changes.
     *
     * @see ViewPositionAnimator#addPositionUpdateListener(PositionUpdateListener)
     */
    public void addPositionUpdateListener(@NonNull PositionUpdateListener listener) {
        listeners.add(listener);
        if (isReady) {
            getToView().getPositionAnimator().addPositionUpdateListener(listener);
        }
    }

    /**
     * Removes listener added by {@link #addPositionUpdateListener(PositionUpdateListener)}.
     *
     * @see ViewPositionAnimator#removePositionUpdateListener(PositionUpdateListener)
     */
    @SuppressWarnings("unused") // Public API
    public void removePositionUpdateListener(@NonNull PositionUpdateListener listener) {
        listeners.remove(listener);
        if (isReady) {
            getToView().getPositionAnimator().removePositionUpdateListener(listener);
        }
    }

    private void initAnimator(ViewPositionAnimator animator) {
        for (PositionUpdateListener listener : listeners) {
            animator.addPositionUpdateListener(listener);
        }
    }

    private void cleanupAnimator(ViewPositionAnimator animator) {
        for (PositionUpdateListener listener : listeners) {
            animator.removePositionUpdateListener(listener);
        }

        if (!animator.isLeaving() || animator.getPosition() != 0f) {
            if (GestureDebug.isDebugAnimator()) {
                Log.d(TAG, "Exiting from cleaned animator for " + enterId);
            }

            animator.exit(false);
        }
    }

    /**
     * Replaces old animator with new one preserving state.
     */
    private void swapAnimator(ViewPositionAnimator old, ViewPositionAnimator next) {
        final float position = old.getPosition();
        final boolean isLeaving = old.isLeaving();
        final boolean isAnimating = old.isAnimating();

        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Swapping animator for " + enterId);
        }

        cleanupAnimator(old);

        if (getFromView() != null) {
            next.enter(getFromView(), false);
        } else if (getFromPos() != null) {
            next.enter(getFromPos(), false);
        }

        initAnimator(next);

        next.setState(position, isLeaving, isAnimating);
    }

    private void clear() {
        if (getToView() != null) {
            cleanupAnimator(getToView().getPositionAnimator());
        }
        enterId = null;
        isReady = false;
        exitRequested = false;
        cleanupRequest();
    }

}
