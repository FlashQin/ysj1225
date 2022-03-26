package com.kalacheng.commonview.dialog;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.ToastUtil;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

public class DatePickerDialog extends BaseDialogFragment {
    private OnDataPickerListener onDataPickerListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_date_picker;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatePicker datePicker = (DatePicker) mRootView.findViewById(R.id.datePicker);
        final Calendar c = Calendar.getInstance();
        setDatePickerDividerColor(datePicker);
        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(year, month, dayOfMonth);
            }
        });

        mRootView.findViewById(com.kalacheng.util.R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDataPickerListener != null) {
                    if (c.getTime().getTime() > new Date().getTime()) {
                        ToastUtil.show(com.kalacheng.util.R.string.edit_profile_right_date);
                    } else {
                        String result = DateFormat.format("yyyy-MM-dd", c).toString();
                        onDataPickerListener.onConfirm(result);
                        dismiss();
                    }
                } else {
                    dismiss();
                }
            }
        });
    }

    /**
     * 设置时间选择器的分割线颜色
     */
    private static void setDatePickerDividerColor(DatePicker datePicker) {
        // Divider changing:

        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(Color.parseColor("#FF5EC6")));//设置分割线颜色
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public void setOnDataPickerListener(OnDataPickerListener onDataPickerListener) {
        this.onDataPickerListener = onDataPickerListener;
    }

    public interface OnDataPickerListener {
        void onConfirm(String date);
    }
}
