package com.kalacheng.commonview.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.glide.ImageLoader;

public class ImageFragment extends BaseFragment {
    private ImageView imageView;
    private String url;

    public ImageFragment() {

    }

    public ImageFragment(String url) {
        this.url = url;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void initView() {
        imageView = mParentView.findViewById(R.id.imageView);
        try {
            ImageLoader.display(url, imageView, 0, 0, false,
                    null, null, new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            if (resource != null) {
                                if (resource.getIntrinsicWidth() < resource.getIntrinsicHeight()) {
                                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                } else {
                                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                }
                                ImageLoader.display(url, imageView);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {

    }
}
