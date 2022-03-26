package com.kalacheng.util.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kalacheng.util.utils.glide.ImageLoader;

public class VoiceGifImageView extends ImageView {
    private boolean isShowing;
    private Handler handler;
    private Runnable runnable;

    public VoiceGifImageView(Context context) {
        super(context);
    }

    public VoiceGifImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VoiceGifImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void start(String gifUrl) {
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    setVisibility(GONE);
                    setImageBitmap(null);
                    isShowing = false;
                }
            };
        }
        if (handler == null) {
            handler = new Handler();
        }

        if (!isShowing) {
            isShowing = true;
            ImageLoader.displayGif(gifUrl, this, new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    setVisibility(GONE);
                    isShowing = false;
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    setVisibility(VISIBLE);
                    handler.postDelayed(runnable, 2300);
                    return false;
                }
            });
        }
    }
}
