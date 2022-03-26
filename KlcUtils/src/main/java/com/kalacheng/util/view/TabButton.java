package com.kalacheng.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalacheng.util.R;
import com.kalacheng.util.utils.DpUtil;


/**
 * Created by cxf on 2018/9/21.
 */

public class TabButton extends LinearLayout {

    private Context mContext;
    private float mScale;
    private int mSelectedIcon;
    private int mUnSelectedIcon;
    private String mTip;
    private int mIconSize;
    private int mTextSize;
    private int mTextColorSelect;
    private int mTextColorUnSelect;
    private boolean mChecked;
    private ImageView mImg;
    private TextView mText;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mScale = context.getResources().getDisplayMetrics().density;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabButton);
        mSelectedIcon = ta.getResourceId(R.styleable.TabButton_tbn_selected_icon, 0);
        mUnSelectedIcon = ta.getResourceId(R.styleable.TabButton_tbn_unselected_icon, 0);
        mTip = ta.getString(R.styleable.TabButton_tbn_tip);
        mIconSize = (int) ta.getDimension(R.styleable.TabButton_tbn_icon_size, 26);
        mTextSize = (int) ta.getDimension(R.styleable.TabButton_tbn_text_size, 11);
        mTextColorSelect = ta.getColor(R.styleable.TabButton_tbn_text_color_select, 0xff000000);
        mTextColorUnSelect = ta.getColor(R.styleable.TabButton_tbn_text_color_un_select, 0xff000000);
        mChecked = ta.getBoolean(R.styleable.TabButton_tbn_checked, false);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        if (mSelectedIcon != 0 && mUnSelectedIcon != 0) {
            mImg = new ImageView(mContext);
            mImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            if (mChecked) {
                mImg.setImageResource(mSelectedIcon);
            } else {
                mImg.setImageResource(mUnSelectedIcon);
            }
            addView(mImg);
        }
        if (!TextUtils.isEmpty(mTip)) {
            mText = new TextView(mContext);
            if (mChecked) {
                mText.setTextColor(mTextColorSelect);
            } else {
                mText.setTextColor(mTextColorUnSelect);
            }
            LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params2.setMargins(0, DpUtil.dp2px(1), 0, 0);
            mText.setLayoutParams(params2);
            mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            mText.setText(mTip);
            addView(mText);
        }
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
        if (checked) {
            if (mImg != null) {
                mImg.setImageResource(mSelectedIcon);
            }
            if (mText != null) {
                mText.setTextColor(mTextColorSelect);
            }
        } else {
            if (mImg != null) {
                mImg.setImageResource(mUnSelectedIcon);
            }
            if (mText != null) {
                mText.setTextColor(mTextColorUnSelect);
            }
        }
    }

    public void setTextSize(String text) {
        mTip = text;
    }

    public void setCheckedStyle(int textColor, int selectedIcon) {
        mTextColorSelect = textColor;
        mSelectedIcon = selectedIcon;
    }

    public void setUnCheckedStyle(int textColor, int selectedIcon) {
        mTextColorUnSelect = textColor;
        mUnSelectedIcon = selectedIcon;
    }


}
