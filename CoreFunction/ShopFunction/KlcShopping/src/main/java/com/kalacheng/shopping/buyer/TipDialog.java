package com.kalacheng.shopping.buyer;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.CheckDoubleClick;

public class TipDialog extends Dialog {

    public TipDialog(@NonNull Context context, String title, String content, final OnClickListener onClickListener) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_tip);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        ((TextView) findViewById(R.id.titleTv)).setText(title);
        ((TextView) findViewById(R.id.contentTv)).setText(content);

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
                if (onClickListener != null) {
                    onClickListener.onClick();
                }
                dismiss();
            }
        });
        show();
    }

    public TipDialog(@NonNull Context context, String title, String content, String confirm, boolean hideCancel, final OnClickListener onClickListener) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_tip);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        ((TextView) findViewById(R.id.titleTv)).setText(title);
        ((TextView) findViewById(R.id.contentTv)).setText(content);
        if (!TextUtils.isEmpty(confirm)) {
            ((TextView) findViewById(R.id.okTv)).setText(confirm);
        }

        if (hideCancel) {
            findViewById(R.id.cancelTv).setVisibility(View.GONE);
            findViewById(R.id.viewInterval).setVisibility(View.GONE);
        } else {
            findViewById(R.id.cancelTv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    dismiss();
                }
            });
        }

        findViewById(R.id.okTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (onClickListener != null) {
                    onClickListener.onClick();
                }
                dismiss();
            }
        });
        show();
    }

    public interface OnClickListener {

        void onClick();

    }

}
