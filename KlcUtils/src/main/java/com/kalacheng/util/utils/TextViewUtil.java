package com.kalacheng.util.utils;

import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;
import android.widget.TextView;

/**
 * TextView和EditText工具类
 * Created by ysj on 2016/11/18.
 */

public class TextViewUtil {
    /**
     * 删除TextView周围图片
     */
    public static void setDrawableNull(TextView view) {
        view.setCompoundDrawables(null, null, null, null);
    }

    /**
     * 删除EditText周围图片
     */
    public static void setDrawableNull(EditText view) {
        view.setCompoundDrawables(null, null, null, null);
    }

    /**
     * 在TextView左侧放置图片
     */
    public static void setDrawableLeft(TextView view, int resId) {
        Drawable drawable = ApplicationUtil.getInstance().getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 在EditText左侧放置图片
     */
    public static void setDrawableLeft(EditText view, int resId) {
        Drawable drawable = ApplicationUtil.getInstance().getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 在TextView右侧放置图片
     */
    public static void setDrawableRight(TextView view, int resId) {
        Drawable drawable = ApplicationUtil.getInstance().getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 在EditText右侧放置图片
     */
    public static void setDrawableRight(EditText view, int resId) {
        Drawable drawable = ApplicationUtil.getInstance().getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 在TextView顶部放置图片
     */
    public static void setDrawableTop(TextView view, int resId) {
        Drawable drawable = ApplicationUtil.getInstance().getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 在EditText顶部放置图片
     */
    public static void setDrawableTop(EditText view, int resId) {
        Drawable drawable = ApplicationUtil.getInstance().getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 限制EditText最大输入长度
     */
    public static void limitEditTextMaxLength(EditText et, int maxLength) {
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * 将光标移至EditText末尾
     */
    public static void cursorAtEditTextEnd(EditText et) {
        CharSequence text = et.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * 设置EditText只读
     */
    public static void setEditReadOnly(EditText view, boolean bReadOnly) {
        if (view != null) {
            view.setCursorVisible(!bReadOnly); // 设置输入框中的光标不可见
            view.setFocusable(!bReadOnly); // 无焦点
            view.setFocusableInTouchMode(!bReadOnly); // 触摸时也得不到焦点
        }
    }

    /**
     * 判断EditText是否为只读
     */
    public static boolean isEditReadOnly(EditText view) {
        if (view == null)
            return false;
        return (!view.isFocusable() && !view.isFocusableInTouchMode());
    }
}
