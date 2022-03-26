package com.kalacheng.util.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kalacheng.util.R;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.io.File;
import java.io.FileInputStream;

public class MImageGetter implements Html.ImageGetter {
    private Context c;
    private TextView container;

    public MImageGetter(TextView text, Context c) {
        this.c = c;
        this.container = text;
    }

    @Override
    public Drawable getDrawable(String source) {
        if (source.equals("1") || source.equals("2") || source.equals("3") || source.equals("4") || source.equals("5") || source.equals("6")) {//色子的点数
            Drawable drawable;
            switch (source) {
                case "1":
                    drawable = ApplicationUtil.getInstance().getResources().getDrawable(R.mipmap.icon_one);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    break;
                case "2":
                    drawable = ApplicationUtil.getInstance().getResources().getDrawable(R.mipmap.icon_two);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    break;
                case "3":
                    drawable = ApplicationUtil.getInstance().getResources().getDrawable(R.mipmap.icon_three);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    break;
                case "4":
                    drawable = ApplicationUtil.getInstance().getResources().getDrawable(R.mipmap.icon_four);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    break;
                case "5":
                    drawable = ApplicationUtil.getInstance().getResources().getDrawable(R.mipmap.icon_five);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    break;
                case "6":
                    drawable = ApplicationUtil.getInstance().getResources().getDrawable(R.mipmap.icon_six);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + source);
            }
            return drawable;
        } else {
            final LevelListDrawable drawable = new LevelListDrawable();
            ImageLoader.loadFile(source, new RequestListener<File>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                    try {
                        FileInputStream fis = new FileInputStream(resource);
                        Bitmap bmp = BitmapFactory.decodeStream(fis);
                        Bitmap bitmap = zoomImg(bmp, DpUtil.dp2px(32), DpUtil.dp2px(15));
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                        drawable.addLevel(1, 1, bitmapDrawable);
                        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        drawable.setLevel(1);
                        container.invalidate();
                        container.setText(container.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
            return drawable;
        }


    }

    public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
