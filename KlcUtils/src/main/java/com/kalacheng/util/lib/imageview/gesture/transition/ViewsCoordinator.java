package com.kalacheng.util.lib.imageview.gesture.transition;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kalacheng.util.lib.imageview.gesture.animation.ViewPosition;
import com.kalacheng.util.lib.imageview.gesture.internal.GestureDebug;
import com.kalacheng.util.lib.imageview.gesture.views.interfaces.AnimatorView;

@SuppressWarnings("WeakerAccess") // Public API (methods)
public class ViewsCoordinator<ID> {

    private static final String TAG = ViewsCoordinator.class.getSimpleName();

    private OnRequestViewListener<ID> fromListener;
    private OnRequestViewListener<ID> toListener;
    private OnViewsReadyListener<ID> readyListener;

    private ID requestedId;
    private ID fromId;
    private ID toId;

    private View fromView;
    private ViewPosition fromPos;
    private AnimatorView toView;

    public void setFromListener(@NonNull OnRequestViewListener<ID> listener) {
        fromListener = listener;
    }

    public void setToListener(@NonNull OnRequestViewListener<ID> listener) {
        toListener = listener;
    }

    public void setReadyListener(@Nullable OnViewsReadyListener<ID> listener) {
        readyListener = listener;
    }

    public void request(@NonNull ID id) {
        cleanupRequest();

        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Requesting " + id);
        }

        requestedId = id;
        fromListener.onRequestView(id);
        toListener.onRequestView(id);
    }

    public View getFromView() {
        return fromView;
    }

    public ViewPosition getFromPos() {
        return fromPos;
    }

    public void setFromView(@NonNull ID id, @NonNull View fromView) {
        setFromInternal(id, fromView, null);
    }

    public void setFromPos(@NonNull ID id, @NonNull ViewPosition fromPos) {
        setFromInternal(id, null, fromPos);
    }

    public void setFromNone(@NonNull ID id) {
        setFromInternal(id, null, null);
    }

    private void setFromInternal(@NonNull ID id, View fromView, ViewPosition fromPos) {
        if (requestedId == null || !requestedId.equals(id)) {
            return;
        }
        if (this.fromView == fromView && fromView != null) {
            return; // Already set
        }

        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Setting 'from' view for " + id);
        }

        fromId = id;
        this.fromView = fromView;
        this.fromPos = fromPos;
        notifyWhenReady();
    }

    public AnimatorView getToView() {
        return toView;
    }

    public void setToView(@NonNull ID id, @NonNull AnimatorView toView) {
        if (requestedId == null || !requestedId.equals(id)) {
            return;
        }
        if (this.toView == toView) {
            return; // Already set
        }

        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Setting 'to' view for " + id);
        }

        toId = id;
        this.toView = toView;
        notifyWhenReady();
    }

    private void notifyWhenReady() {
        if (requestedId == null || !requestedId.equals(fromId) || !requestedId.equals(toId)) {
            return;
        }

        onViewsReady(requestedId);
    }

    protected void cleanupRequest() {
        if (requestedId == null) {
            return;
        }

        if (GestureDebug.isDebugAnimator()) {
            Log.d(TAG, "Cleaning up request " + requestedId);
        }

        fromView = null;
        fromPos = null;
        toView = null;
        requestedId = fromId = toId = null;
    }

    /**
     * Called when both 'from' and 'to' views are ready for given index. At this point
     * transition is ready to be started.
     * <p/>
     * Note, that this method will be called each time 'from' or 'to' views are changed.
     *
     * @see #getFromView()
     * @see #getFromPos()
     * @see #getToView()
     */
    protected void onViewsReady(@NonNull ID id) {
        if (readyListener != null) {
            readyListener.onViewsReady(id);
        }
    }


    public interface OnRequestViewListener<ID> {
        /**
         * Implementation should find corresponding {@link View} (or {@link ViewPosition})
         * for given {@code index} and provide it back to {@link ViewsCoordinator}.
         * <p/>
         * Note, that it may not be possible to provide view right now (i.e. because
         * we should scroll source view to reveal correct view), but it should be provided
         * as soon as it's ready.
         */
        void onRequestView(@NonNull ID id);
    }

    public interface OnViewsReadyListener<ID> {
        /**
         * Will be called when both 'from' and 'to' views for given item index are ready.
         * <p/>
         * Note, that this method will be called each time 'from' or 'to' views are changed.
         */
        void onViewsReady(@NonNull ID id);
    }

}
