package com.kalacheng.message.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.PopupWindow;

import com.kalacheng.message.R;

public class ChatDialog extends PopupWindow {

    private View mParent;
    private View mContentView;
    private ActionListener mActionListener;
    private int viewHeight;

    public ChatDialog(View parent, final View contentView, boolean needAnim, ActionListener actionListener) {
        mParent = parent;
        mActionListener = actionListener;
        ViewParent viewParent = contentView.getParent();
        if (viewParent != null) {
            ((ViewGroup) viewParent).removeView(contentView);
        }
        mContentView = contentView;
        viewHeight = mContentView.getMeasuredHeight();
        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(false);
        if (needAnim) {
            setAnimationStyle(R.style.bottomToTopAnim);
        }
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ViewParent viewParent = mContentView.getParent();
                if (viewParent != null) {
                    ((ViewGroup) viewParent).removeView(mContentView);
                }
                mContentView = null;
                if (mActionListener != null) {
                    mActionListener.onDialogDismiss();
                }
                mActionListener = null;
            }
        });
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public View getmContentView() {
        return mContentView;
    }

    public void show() {
        showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
    }


    public interface ActionListener {
        void onDialogDismiss();
    }



}
