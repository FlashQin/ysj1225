package com.kalacheng.commonview.popupwindow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.DpUtil;

/**
 * @author: Administrator
 * @date: 2020/9/7
 * @info:
 */
public class SexChoicePopupWindow extends PopupWindow {

    private Context context;
    private TextView tv_sex_1, tv_sex_2, tv_sex_3;
    private int sex = 0;
    private OnDismissListener onDismissListener;

    public SexChoicePopupWindow(Context context, final OnDismissListener onDismissListener) {
        this.context = context;
        this.onDismissListener = onDismissListener;
        setContentView(LayoutInflater.from(context).inflate(R.layout.popupwindow_sex_choice_layout, null));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        tv_sex_1 = getContentView().findViewById(R.id.tv_sex_1);
        tv_sex_2 = getContentView().findViewById(R.id.tv_sex_2);
        tv_sex_3 = getContentView().findViewById(R.id.tv_sex_3);

        setFocusable(true);
        setOutsideTouchable(true);
        setClippingEnabled(false);
        ColorDrawable dw = new ColorDrawable(0x33000000);
        setBackgroundDrawable(dw);

        tv_sex_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButton(-1);
            }
        });
        tv_sex_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButton(1);
            }
        });
        tv_sex_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButton(2);
            }
        });

        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (null != onDismissListener){
                    onDismissListener.onDismiss();
                }
            }
        });
    }

    private void setButton(int item) {
        if (null != onDismissListener){
            onDismissListener.onChoiceSex(item);
        }
        this.sex = item;
        tv_sex_1.setBackgroundResource(R.drawable.shape_f4f4f4_50_bg);
        tv_sex_2.setBackgroundResource(R.drawable.shape_f4f4f4_50_bg);
        tv_sex_3.setBackgroundResource(R.drawable.shape_f4f4f4_50_bg);
        tv_sex_1.setTextColor(context.getResources().getColor(R.color.textColor6));
        tv_sex_2.setTextColor(context.getResources().getColor(R.color.textColor6));
        tv_sex_3.setTextColor(context.getResources().getColor(R.color.textColor6));
        Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_boy_tag);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_sex_2.setCompoundDrawables(drawable, null, null, null);
        Drawable drawable2 = context.getResources().getDrawable(R.mipmap.icon_girl_tag);
        drawable2.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_sex_3.setCompoundDrawables(drawable2, null, null, null);
        switch (item) {
            case -1:
                tv_sex_1.setBackgroundResource(R.drawable.bg_gradient_purple3);
                tv_sex_1.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case 1:
                tv_sex_2.setBackgroundResource(R.drawable.bg_gradient_purple3);
                tv_sex_2.setTextColor(context.getResources().getColor(R.color.white));
                Drawable drawableB = context.getResources().getDrawable(R.mipmap.icon_boy_tag_selected);
                drawableB.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_sex_2.setCompoundDrawables(drawableB, null, null, null);
                break;
            case 2:
                tv_sex_3.setBackgroundResource(R.drawable.bg_gradient_purple3);
                tv_sex_3.setTextColor(context.getResources().getColor(R.color.white));
                Drawable drawableG = context.getResources().getDrawable(R.mipmap.icon_girl_tag_selected);
                drawableG.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_sex_3.setCompoundDrawables(drawableG, null, null, null);
                break;
            default:
                break;
        }
        dismiss();
    }

    /**
     * 下拉内容展示
     *
     * @param anchor 控件view
     */
    public void show(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = DpUtil.getScreenHeight() - rect.bottom;
            setHeight(h);
        }
        showAsDropDown(anchor);
    }

    public interface OnDismissListener {
        void onDismiss();

        void onChoiceSex(int item);
    }

}