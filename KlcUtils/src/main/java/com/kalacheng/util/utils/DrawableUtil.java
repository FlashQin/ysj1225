package com.kalacheng.util.utils;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

public class DrawableUtil {

        /**
         * 对目标Drawable 进行着色
         *
         * @param drawable 目标Drawable
         * @param color    着色的颜色值
         * @return 着色处理后的Drawable
         */
        public static Drawable tintDrawable(@NonNull Drawable drawable, int color) {
            // 获取此drawable的共享状态实例
            Drawable wrappedDrawable = getCanTintDrawable(drawable);
            // 对 drawable 进行着色
            DrawableCompat.setTint(wrappedDrawable, color);
            return wrappedDrawable;
        }

        /**
         * 对目标Drawable 进行着色
         *
         * @param drawable 目标Drawable
         * @param colors   着色值
         * @return 着色处理后的Drawable
         */
        public static Drawable tintListDrawable(@NonNull Drawable drawable, ColorStateList colors) {
            Drawable wrappedDrawable = getCanTintDrawable(drawable);
            // 对 drawable 进行着色
            DrawableCompat.setTintList(wrappedDrawable, colors);
            return wrappedDrawable;
        }

        /**
         * 获取可以进行tint 的Drawable
         * <p>
         * 对原drawable进行重新实例化  newDrawable()
         * 包装  warp()
         * 可变操作 mutate()
         *
         * @param drawable 原始drawable
         * @return 可着色的drawable
         */
        @NonNull
        private static Drawable getCanTintDrawable(@NonNull Drawable drawable) {
            // 获取此drawable的共享状态实例
            Drawable.ConstantState state = drawable.getConstantState();
            // 对drawable 进行重新实例化、包装、可变操作
            return DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        }

        /**
         * 获得一个选择器Drawable.
         * Android 中 在xml中写的"selector"标签映射对象就是StateListDrawable 对象
         *
         * @param defaultDrawable 默认时显示的Drawable
         * @param pressedDrawable 按下时显示的Drawable
         * @return 选择器Drawable
         */
        public static StateListDrawable getSelectorDrawable(Drawable defaultDrawable, Drawable pressedDrawable) {
            if (defaultDrawable == null) return null;
            if (pressedDrawable == null) pressedDrawable = defaultDrawable;
            int[][] state = {{android.R.attr.state_enabled, -android.R.attr.state_pressed},
                    {android.R.attr.state_enabled, android.R.attr.state_pressed}};
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(state[0], defaultDrawable);
            stateListDrawable.addState(state[1], pressedDrawable);
            return stateListDrawable;
        }

        /**
         * 获得一个选择器Drawable.
         * Android 中 在xml中写的"selector"标签映射对象就是StateListDrawable 对象
         *
         * @param defaultColor 默认时显示的颜色
         * @param pressedColor 按下时显示的颜色
         * @param radius       圆角半径
         * @return 选择器Drawable
         */
        public static StateListDrawable getSelectorDrawable(int defaultColor, int pressedColor, float radius) {

            Drawable defaultDrawable = new Builder(Builder.RECTANGLE).setColor(defaultColor).setCornerRadius(radius).create();
            Drawable pressedDrawable = new Builder(Builder.RECTANGLE).setColor(pressedColor).setCornerRadius(radius).create();

            return getSelectorDrawable(defaultDrawable, pressedDrawable);
        }

        /**
         * 获取建造者
         *
         * @param shape 图形
         * @return {@link Builder}
         */
        public static Builder getBuilder(int shape) {
            return new Builder(shape);
        }

        /**
         * Drawable 建造者，链式结构，方便使用
         */
        public static class Builder {
            /**
             * Shape is a rectangle, possibly with rounded corners
             */
            public static final int RECTANGLE = GradientDrawable.RECTANGLE;

            /**
             * Shape is an ellipse
             */
            public static final int OVAL = GradientDrawable.OVAL;

            /**
             * Shape is a line
             */
            public static final int LINE = GradientDrawable.LINE;

            private ColorStateList mSolidColors;
            /**
             * The color state list of the stroke
             */
            private ColorStateList mStrokeColors;
            private @ColorInt
            int[] mGradientColors;
            /**
             * The width in pixels of the stroke
             */
            private int mStrokeWidth = -1; // if >= 0 use stroking.
            /**
             * The length in pixels of the dashes, set to 0 to disable dashes
             */
            private float mStrokeDashWidth = 0.0f;
            /**
             * The gap in pixels between dashes
             */
            private float mStrokeDashGap = 0.0f;
            private float mRadius = 0.0f; // use this if mRadiusArray is null
            private float[] mRadiusArray = null;

            GradientDrawable mGradientDrawable;

            public Builder(int shape) {
                mGradientDrawable = new GradientDrawable();
                mGradientDrawable.setShape(shape == RECTANGLE || shape == OVAL || shape == LINE ? shape : RECTANGLE);
            }

            /**
             * Sets the colors used to draw the gradient.
             * <p>
             * Each color is specified as an ARGB integer and the array must contain at
             * least 2 colors.
             * <p>
             * <strong>Note</strong>: changing colors will affect all instances of a
             * drawable loaded from a resource. It is recommended to invoke
             * {@link # GradientDrawable.mutate()} before changing the colors.
             *
             * @param colors an array containing 2 or more ARGB colors
             * @see # mutate()
             * @see #setColor(int)
             */
            public Builder setColors(@Nullable int[] colors) {
                this.mGradientColors = colors;
                return this;
            }

            /**
             * Changes this drawable to use a single color instead of a gradient.
             * <p>
             * <strong>Note</strong>: changing color will affect all instances of a
             * drawable loaded from a resource. It is recommended to invoke
             * {@link # GradientDrawable.mutate()} before changing the color.
             *
             * @param argb The color used to fill the shape
             */
            public Builder setColor(@ColorInt int argb) {
                this.mSolidColors = ColorStateList.valueOf(argb);
                return this;
            }

            /**
             * Changes this drawable to use a single color state list instead of a
             * gradient. Calling this method with a null argument will clear the color
             * and is equivalent to calling {@link #setColor(int)} with the argument
             * {@link Color#TRANSPARENT}.
             * <p>
             * <strong>Note</strong>: changing color will affect all instances of a
             * drawable loaded from a resource. It is recommended to invoke
             * {@link # GradientDrawable.mutate()()} before changing the color.</p>
             *
             * @param colorStateList The color state list used to fill the shape
             */
            public Builder setColor(@Nullable ColorStateList colorStateList) {
                this.mSolidColors = colorStateList;
                return this;
            }


            /**
             * <p>Set the stroke width and color for the drawable. If width is zero,
             * then no stroke is drawn.</p>
             * <p><strong>Note</strong>: changing this property will affect all instances
             * of a drawable loaded from a resource. It is recommended to invoke
             * {@link # mutate()} before changing this property.</p>
             *
             * @param width The width in pixels of the stroke
             * @param color The color of the stroke
             * @see # mutate()
             * @see #setStroke(int, int, float, float)
             */
            public Builder setStroke(int width, @ColorInt int color) {
                this.mStrokeWidth = width;
                this.mStrokeColors = ColorStateList.valueOf(color);
                return this;
            }

            /**
             * <p>Set the stroke width and color state list for the drawable. If width
             * is zero, then no stroke is drawn.</p>
             * <p><strong>Note</strong>: changing this property will affect all instances
             * of a drawable loaded from a resource. It is recommended to invoke
             * {@link # mutate()} before changing this property.</p>
             *
             * @param width          The width in pixels of the stroke
             * @param colorStateList The color state list of the stroke
             * @see #setStroke(int, ColorStateList, float, float)
             */
            public Builder setStroke(int width, ColorStateList colorStateList) {
                this.mStrokeWidth = width;
                this.mStrokeColors = colorStateList;
                return this;
            }

            /**
             * <p>Set the stroke width and color for the drawable. If width is zero,
             * then no stroke is drawn. This method can also be used to dash the stroke.</p>
             * <p><strong>Note</strong>: changing this property will affect all instances
             * of a drawable loaded from a resource. It is recommended to invoke
             * {@link # mutate()} before changing this property.</p>
             *
             * @param width     The width in pixels of the stroke
             * @param color     The color of the stroke
             * @param dashWidth The length in pixels of the dashes, set to 0 to disable dashes
             * @param dashGap   The gap in pixels between dashes
             * @see #setStroke(int, int)
             */
            public Builder setStroke(int width, @ColorInt int color, float dashWidth, float dashGap) {
                this.mStrokeWidth = width;
                this.mStrokeColors = ColorStateList.valueOf(color);
                this.mStrokeDashWidth = dashWidth;
                this.mStrokeDashGap = dashGap;
                return this;
            }

            /**
             * <p>Set the stroke width and color state list for the drawable. If width
             * is zero, then no stroke is drawn. This method can also be used to dash
             * the stroke.</p>
             * <p><strong>Note</strong>: changing this property will affect all instances
             * of a drawable loaded from a resource. It is recommended to invoke
             * {@link # mutate()} before changing this property.</p>
             *
             * @param width          The width in pixels of the stroke
             * @param colorStateList The color state list of the stroke
             * @param dashWidth      The length in pixels of the dashes, set to 0 to disable dashes
             * @param dashGap        The gap in pixels between dashes
             * @see #setStroke(int, ColorStateList)
             */
            public Builder setStroke(int width, ColorStateList colorStateList, float dashWidth, float dashGap) {
                this.mStrokeWidth = width;
                this.mStrokeColors = colorStateList;
                this.mStrokeDashWidth = dashWidth;
                this.mStrokeDashGap = dashGap;
                return this;
            }

            public Builder setCornerRadius(float radius) {
                if (radius < 0) {
                    radius = 0;
                }
                this.mRadius = radius;
                this.mRadiusArray = null;
                return this;
            }

            /**
             * Specifies radii for each of the 4 corners. For each corner, the array
             * contains 2 values, <code>[X_radius, Y_radius]</code>. The corners are
             * ordered top-left, top-right, bottom-right, bottom-left. This property
             * is honored only when the shape is of type {@link #RECTANGLE}.
             * <p>
             * <strong>Note</strong>: changing this property will affect all instances
             * of a drawable loaded from a resource. It is recommended to invoke
             * {@link # mutate()} before changing this property.
             *
             * @param radii an array of length >= 8 containing 4 pairs of X and Y
             *              radius for each corner, specified in pixels
             * @see #setCornerRadius(float)
             */
            public Builder setCornerRadii(@Nullable float[] radii) {
                this.mRadiusArray = radii;
                if (radii == null) {
                    this.mRadius = 0;
                }
                return this;
            }

            public Drawable create() {
                if (mRadiusArray != null) {
                    mGradientDrawable.setCornerRadii(mRadiusArray);

                } else if (mRadius > 0.0f) {
                    mGradientDrawable.setCornerRadius(mRadius);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mGradientDrawable.setColor(mSolidColors);
                    //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
                    mGradientDrawable.setStroke(mStrokeWidth, mStrokeColors, mStrokeDashWidth, mStrokeDashGap);
                } else {
                    mGradientDrawable.setColor(mSolidColors.getDefaultColor());
                    //显示一条虚线，破折线的宽度为dashWith，破折线之间的空隙的宽度为dashGap，当dashGap=0dp时，为实线
                    mGradientDrawable.setStroke(mStrokeWidth, mStrokeColors.getDefaultColor(), mStrokeDashWidth, mStrokeDashGap);
                }
                return mGradientDrawable;
            }
        }

}
