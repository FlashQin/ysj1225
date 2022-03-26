package com.kalacheng.util.lib.imageview.gesture.views.interfaces;

import com.kalacheng.util.lib.imageview.gesture.animation.ViewPositionAnimator;

/**
 * Common interface for views supporting position animation.
 */
public interface AnimatorView {

    /**
     * @return {@link ViewPositionAnimator} instance to control animation from other view position.
     */
    ViewPositionAnimator getPositionAnimator();

}
