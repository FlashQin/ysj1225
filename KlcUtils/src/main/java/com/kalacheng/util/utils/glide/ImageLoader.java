package com.kalacheng.util.utils.glide;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kalacheng.util.utils.ApplicationUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.view.MaskImageView;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class ImageLoader {

    private static String TAG = ImageLoader.class.getSimpleName();

    private static RequestManager sManager;

    static {
        sManager = Glide.with(ApplicationUtil.getInstance());
    }

    /**
     * 显示视频封面缩略图
     */
    public static void displayVideoThumb(String videoPath, ImageView imageView) {
        display(Uri.fromFile(new File(videoPath)), imageView);
    }

    /**
     * 显示模糊的毛玻璃图片，radius取值1-25,值越大图片越模糊
     */
    public static void displayBlur(String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new BlurTransformation(10, 2));
        sManager.load(url).apply(requestOptions).into(imageView);
    }

    /**
     * 显示模糊的毛玻璃图片，radius取值1-25,值越大图片越模糊
     */
    public static void displayBlur(String url, ImageView imageView, int radius, int sampling) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new BlurTransformation(radius, sampling));
        sManager.load(url).apply(requestOptions).into(imageView);
    }

    /**
     * 模糊处理，使图片占满全局 (网络图片地址)
     */
    public static void displayBlur(String url, MaskImageView maskImageView) {
        sManager.load(url)
                .apply(maskImageView.setGaussBlur())//这是重点
                .into(maskImageView);
    }

    public static void display(String url, ImageView imageView) {
        display(url, imageView, 0, 0, false, null, null, null);
    }

    public static void display(String url, ImageView imageView, boolean circleCrop) {
        display(url, imageView, 0, 0, circleCrop, null, null, null);
    }

    public static void display(String url, ImageView imageView, int placeholderRes, int errorRes) {
        display(url, imageView, placeholderRes, errorRes, false, null, null, null);
    }

    public static void display(File file, ImageView imageView) {
        display(file, imageView, 0, 0, false, null, null, null);
    }

    public static void display(File file, ImageView imageView, boolean circleCrop) {
        display(file, imageView, 0, 0, circleCrop, null, null, null);
    }

    public static void display(File file, ImageView imageView, int placeholderRes, int errorRes) {
        display(file, imageView, placeholderRes, errorRes, false, null, null, null);
    }

    public static void display(int res, ImageView imageView) {
        display(res, imageView, 0, 0, false, null, null, null);
    }

    public static void display(int res, ImageView imageView, boolean circleCrop) {
        display(res, imageView, 0, 0, circleCrop, null, null, null);
    }

    public static void display(Uri uri, ImageView imageView) {
        display(uri, imageView, 0, 0, false, null, null, null);
    }

    public static void display(Uri uri, ImageView imageView, boolean circleCrop) {
        display(uri, imageView, 0, 0, circleCrop, null, null, null);
    }

    public static void displayRoundedCorners(String url, ImageView imageView, int placeholderRes, int corners) {
        display(url, imageView, placeholderRes, 0, false, new RoundedCorners(corners), null, null);
    }

    public static void display(String url, ImageView imageView, int placeholderRes, int errorRes, boolean circleCrop, BitmapTransformation bitmapTransformation, RequestListener<Drawable> requestListener, SimpleTarget simpleTarget) {
//        String imgUrl;
//        if (url.contains("https")) {
//            imgUrl = url.replace("https", "http");
//        } else {
//            imgUrl = url;
//        }
        RequestOptions requestOptions = new RequestOptions();
        if (placeholderRes != 0) {
            requestOptions = requestOptions.placeholder(placeholderRes);
        }
        if (errorRes != 0) {
            requestOptions = requestOptions.error(errorRes);
        }
        if (circleCrop) {
            requestOptions = requestOptions.circleCrop();
        }
        if (bitmapTransformation != null) {
            requestOptions = requestOptions.bitmapTransform(bitmapTransformation);
        }
        try {
            if (requestListener != null) {
                if (simpleTarget != null) {
                    sManager.load(url).skipMemoryCache(false).apply(requestOptions).listener(requestListener).into(simpleTarget);
                } else {
                    sManager.load(url).skipMemoryCache(false).apply(requestOptions).listener(requestListener).into(imageView);
                }
            } else {
                if (simpleTarget != null) {
                    sManager.load(url).skipMemoryCache(false).apply(requestOptions).into(simpleTarget);
                } else {
                    sManager.load(url).skipMemoryCache(false).apply(requestOptions).into(imageView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            L.e(TAG, "display  e  " + e.toString());
        }
    }

    public static void display(File file, ImageView imageView, int placeholderRes, int errorRes, boolean circleCrop, BitmapTransformation bitmapTransformation, RequestListener<Drawable> requestListener, SimpleTarget simpleTarget) {
        RequestOptions requestOptions = new RequestOptions();
        if (placeholderRes != 0) {
            requestOptions = requestOptions.placeholder(placeholderRes);
        }
        if (errorRes != 0) {
            requestOptions = requestOptions.error(errorRes);
        }
        if (circleCrop) {
            requestOptions = requestOptions.circleCrop();
        }
        if (bitmapTransformation != null) {
            requestOptions = requestOptions.transform(bitmapTransformation);
        }
        if (requestListener != null) {
            if (simpleTarget != null) {
                sManager.load(file).apply(requestOptions).listener(requestListener).into(simpleTarget);
            } else {
                sManager.load(file).apply(requestOptions).listener(requestListener).into(imageView);
            }
        } else {
            if (simpleTarget != null) {
                sManager.load(file).apply(requestOptions).into(simpleTarget);
            } else {
                sManager.load(file).apply(requestOptions).into(imageView);
            }
        }
    }

    public static void display(int res, ImageView imageView, int placeholderRes, int errorRes, boolean circleCrop, BitmapTransformation bitmapTransformation, RequestListener<Drawable> requestListener, SimpleTarget simpleTarget) {
        RequestOptions requestOptions = new RequestOptions();
        if (placeholderRes != 0) {
            requestOptions = requestOptions.placeholder(placeholderRes);
        }
        if (errorRes != 0) {
            requestOptions = requestOptions.error(errorRes);
        }
        if (circleCrop) {
            requestOptions = requestOptions.circleCrop();
        }
        if (bitmapTransformation != null) {
            requestOptions = requestOptions.transform(bitmapTransformation);
        }
        if (requestListener != null) {
            if (simpleTarget != null) {
                sManager.load(res).apply(requestOptions).listener(requestListener).into(simpleTarget);
            } else {
                sManager.load(res).apply(requestOptions).listener(requestListener).into(imageView);
            }
        } else {
            if (simpleTarget != null) {
                sManager.load(res).apply(requestOptions).into(simpleTarget);
            } else {
                sManager.load(res).apply(requestOptions).into(imageView);
            }
        }
    }

    public static void display(Uri uri, ImageView imageView, int placeholderRes, int errorRes, boolean circleCrop, BitmapTransformation bitmapTransformation, RequestListener<Drawable> requestListener, SimpleTarget simpleTarget) {
        RequestOptions requestOptions = new RequestOptions();
        if (placeholderRes != 0) {
            requestOptions = requestOptions.placeholder(placeholderRes);
        }
        if (errorRes != 0) {
            requestOptions = requestOptions.error(errorRes);
        }
        if (circleCrop) {
            requestOptions = requestOptions.circleCrop();
        }
        if (bitmapTransformation != null) {
            requestOptions = requestOptions.transform(bitmapTransformation);
        }
        if (requestListener != null) {
            if (simpleTarget != null) {
                sManager.load(uri).apply(requestOptions).listener(requestListener).into(simpleTarget);
            } else {
                sManager.load(uri).apply(requestOptions).listener(requestListener).into(imageView);
            }

        } else {
            if (simpleTarget != null) {
                sManager.load(uri).apply(requestOptions).into(simpleTarget);
            } else {
                sManager.load(uri).apply(requestOptions).into(imageView);
            }
        }
    }

    /**
     * 加载GIF动图
     * Glide加载gif动画，切换页面的时候先显示最后一帧再开始播放动画的问题解决方案。设置Glide禁止使用内存缓存即可。
     */
    public static void displayGif(String url, ImageView imageView, RequestListener<Drawable> requestListener) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        if (requestListener != null) {
            sManager.load(url).apply(requestOptions).listener(requestListener).into(imageView);
        } else {
            sManager.load(url).apply(requestOptions).into(imageView);
        }
    }

    /**
     * 下载图片
     */
    public static void loadFile(String url, RequestListener<File> requestListener) {
        sManager.downloadOnly().load(url).listener(requestListener).preload();
    }

    /**
     * 加载第1秒的帧数作为封面
     * url就是视频的地址
     * 版本4+之后才有的功能
     */
    public static void loadCover(ImageView imageView, String url) {
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.frame(1000000).centerCrop();
        sManager.load(url).apply(requestOptions).into(imageView);
    }

}