package com.kalacheng.shopping.buyer;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ToastUtil;

public class InputDialog extends Dialog {

    public InputDialog(@NonNull Context context, String title, String hint, int maxLength, final OnClickListener onClickListener) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_input);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        ((TextView) findViewById(R.id.titleTv)).setText(title);
        final EditText etInfo = findViewById(R.id.etInfo);
        etInfo.setHint(hint);
        etInfo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        findViewById(R.id.cancelTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                dismiss();
            }
        });

        findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (TextUtils.isEmpty(etInfo.getText().toString())) {
                    ToastUtil.show("内容不能为空");
                    return;
                }
                if (onClickListener != null) {
                    onClickListener.onClick(etInfo.getText().toString().trim());
                }
                dismiss();
            }
        });
        show();
    }

    public interface OnClickListener {

        void onClick(String info);

    }

}