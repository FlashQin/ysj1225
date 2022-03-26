package com.kalacheng.livecommon.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.kalacheng.buslive.httpApi.HttpApiHttpLive;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*
 * 活动条幅
 * */
public class LiveShopActivityDialogFragment extends BaseDialogFragment{
    private ProcessImageUtil mImageUtil;
    Disposable uploadImgDisposable;
    RoundedImageView LiveShopActivity_Image;
    private String activityImage;
    private TextView bt_delete;
    private ShopLiveBannerListener listener;

    public void getActivityImage(String activityImage) {
        this.activityImage = activityImage;
        setImg();
    }

    public void setListener(ShopLiveBannerListener listener){
        this.listener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_shop_activity;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImageUtil = new ProcessImageUtil((FragmentActivity) mContext);

        LiveShopActivity_Image = mRootView.findViewById(R.id.LiveShopActivity_Image);
        bt_delete = mRootView.findViewById(R.id.bt_delete);

        setImg();

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityImage = "";
                setImg();
                bt_delete.setVisibility(View.GONE);
                // 取消海报图片 置空url
                HttpApiHttpLive.setShopLiveBanner(LiveConstants.ROOMID, "", new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            LiveShopActivity_Image.setImageResource(R.mipmap.bg_shop_activity);
                            bt_delete.setVisibility(View.GONE);
                            if (null != listener){
                                listener.onSuccessUrl("");
                            }
                        }
                    }
                });
            }
        });

        mRootView.findViewById(R.id.LiveShopActivity_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        LiveShopActivity_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAvatar();
            }
        });
        mImageUtil.setImageResultCallback(new ImageResultCallback() {
            @Override
            public void beforeCamera() {

            }

            @Override
            public void onSuccess(final File file) {
                if (file != null) {
//                    uploadDialog.show();
                    uploadImgDisposable = Observable.create(new ObservableOnSubscribe<String>() {
                        @Override
                        public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                            UploadUtil.getInstance().uploadPicture(1, file, new PictureUploadCallback() {
                                @Override
                                public void onSuccess(String imgStr) {
                                    if (!TextUtils.isEmpty(imgStr)) {
                                        emitter.onNext(imgStr);
                                    } else {
                                        ToastUtil.show("上传失败");
                                    }
                                }

                                @Override
                                public void onFailure() {
                                    ToastUtil.show("上传失败");
                                }
                            });
//                            emitter.onNext(HuaWeiYun.getInstance().upload(file, HuaWeiYun.IMAGE_PATH));
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull final String url) throws Exception {
                            if (null != url && !TextUtils.isEmpty(url)) {
//                                uploadDialog.dismiss();
                                HttpApiHttpLive.setShopLiveBanner(LiveConstants.ROOMID, url, new HttpApiCallBack<HttpNone>() {
                                    @Override
                                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                                        if (code == 1) {
                                            ToastUtil.show("上传图片成功");
                                            activityImage = url;
                                            setImg();
                                            if (null != listener){
                                                listener.onSuccessUrl(url);
                                            }
                                        } else {
                                            ToastUtil.show("上传图片失败");
                                        }
                                    }
                                });

                            } else {
                                ToastUtil.show("上传图片失败");
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure() {
            }
        });
    }

    private void setImg() {
        if (null != activityImage && null != LiveShopActivity_Image){
            if (!activityImage.isEmpty()) {
                ImageLoader.display(activityImage, LiveShopActivity_Image);
                bt_delete.setVisibility(View.VISIBLE);
            } else {
                LiveShopActivity_Image.setImageResource(R.mipmap.bg_shop_activity);
                bt_delete.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置头像
     */
    private void setAvatar() {
        DialogUtil.showStringArrayDialog(mContext, new Integer[]{R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.camera) {
                    mImageUtil.getImageByCamera(1, 2);
                } else {
                    mImageUtil.getImageByAlbumCustom(1, 2);
                }
            }
        });
    }

    public interface ShopLiveBannerListener{
        void onSuccessUrl(String url);
    }

}
