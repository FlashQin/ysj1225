package com.kalacheng.util.lib.imageview.gesture.views.interfaces;

import android.graphics.RectF;

import androidx.annotation.Nullable;

public interface ClipView {

    /**
     * Clips view so only {@code rect} part (modified by view's state) will be drawn.
     * <p/>
     * Pass {@code null} to turn clipping off.
     */
    void clipView(@Nullable RectF rect, float rotation);

}
