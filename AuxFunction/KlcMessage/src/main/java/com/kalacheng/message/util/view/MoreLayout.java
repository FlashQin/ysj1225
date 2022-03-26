package com.kalacheng.message.util.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.kalacheng.message.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.commonview.utils.ProcessImageUtil;

public class MoreLayout extends FrameLayout {

    public MoreLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MoreLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MoreLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        final ProcessImageUtil imageUtil =new  ProcessImageUtil((FragmentActivity) context);
        imageUtil.setImageResultCallback((ImageResultCallback) context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_more, this, false);
        TextView cameraTv = view.findViewById(R.id.cameraTv);
        TextView imgTv = view.findViewById(R.id.imgTv);
        cameraTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageUtil.getImageByCamera(false);
                }
            }
        });
        imgTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageUtil.getImageByAlbumCustom(false);
                }
            }
        });
        addView(view);
    }

}
