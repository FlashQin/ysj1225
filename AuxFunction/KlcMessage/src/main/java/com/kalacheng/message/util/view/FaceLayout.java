package com.kalacheng.message.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.message.R;
import com.kalacheng.message.adapter.ChatFaceAdapter;
import com.kalacheng.message.adapter.ChatFacePagerAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;

public class FaceLayout extends FrameLayout {
    Context context;
    ViewPager viewPager;
    RadioGroup radioGroup;
    View view;
    OnLayoutClickListener onLayoutClickListener;

    public FaceLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FaceLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FaceLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.layout_face, this, false);
        radioGroup = view.findViewById(R.id.radio);

        ChatFaceAdapter.OnFaceClickListener listener = new ChatFaceAdapter.OnFaceClickListener() {
            @Override
            public void onClickFace(String str, int res) {
                if (onLayoutClickListener != null){
                    onLayoutClickListener.onFaceSelectClick(str,res);
                }
            }

            @Override
            public void onClickDelete() {
                if (onLayoutClickListener != null){
                    onLayoutClickListener.onFaceDeleteClick();
                }
            }
        };

        ChatFacePagerAdapter adapter = new ChatFacePagerAdapter(context, listener);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(9);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) (radioGroup.getChildAt(position))).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(adapter);
        for (int i = 0, pageCount = adapter.getCount(); i < pageCount; i++) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.layout_face_indicator, radioGroup, false);

            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
        view.findViewById(R.id.sendBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (onLayoutClickListener != null){
                    onLayoutClickListener.sendBtn();
                }
            }
        });
        addView(view);
    }

    public void setListener(final OnLayoutClickListener listener) {
       this.onLayoutClickListener = listener;
    }

    public interface OnLayoutClickListener {

        void onFaceSelectClick(String str, int res);

        void onFaceDeleteClick();

        void sendBtn();

    }


}
