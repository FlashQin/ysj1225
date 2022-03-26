package com.kalacheng.videorecord.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.busshortvideo.model_fun.AppShortVideo_setShortVideo;
import com.kalacheng.commonview.activity.DynamicMakeActivity;
import com.kalacheng.commonview.activity.PictureChooseActivity;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.AppHotSort;
import com.kalacheng.libuser.model.AppVideoTopic;
import com.kalacheng.libuser.model_fun.AppVideo_setVideo;
import com.kalacheng.commonview.adapter.PictureChooseAdapter;
import com.kalacheng.commonview.bean.PictureChooseBean;
import com.kalacheng.commonview.listener.OnPictureChooseItemClickListener;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.utils.aop.SingleClick;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.util.view.SpaceItemDecoration;
import com.kalacheng.videorecord.R;
import com.kalacheng.videorecord.adpater.ShopChooseAdapter;
import com.kalacheng.videorecord.adpater.TagAdapter;
import com.kalacheng.videorecord.adpater.TopicSelectAdapter;
import com.kalacheng.videorecord.camera.MediaPlayerManager;
import com.kalacheng.videorecord.databinding.ActivityVideoPublishBinding;
import com.kalacheng.videorecord.fragment.PublishGoodsWindowDialogFragment;
import com.kalacheng.videorecord.viewmodel.VideoPublishModel;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;
import com.xuantongyun.storagecloud.upload.base.VideoUploadBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 发布动态/发布短视频
 */
@Route(path = ARouterPath.VideoPublish)
public class VideoPublishActivity extends BaseMVVMActivity<ActivityVideoPublishBinding, VideoPublishModel> implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.VIDEO_PATH)
    public String videoPath;

    @Autowired(name = ARouterValueNameConfig.VideoType)
    public int VideoType;

    @Autowired(name = ARouterValueNameConfig.VIDEO_TIME_LONG)
    public long VideoTime;

    @Autowired(name = ARouterValueNameConfig.PICTURE_LIST)
    public List<PictureChooseBean> mPicture;

    @Autowired(name = ARouterValueNameConfig.SHORT_VIDEO)
    public boolean mIsShortVideo;//是否为短视频

    private int mType;
    private static final int TYPE_VIDEO = 0;
    private static final int TYPE_PICTURE = 1;
    private String videoUrl = "";
    private PictureChooseAdapter pictureChooseAdapter;

    /**
     * player manager
     */
    private MediaPlayerManager playerManager;
    private boolean mPlayStarted;//播放是否开始了
    private int mSaveType;
    private String mVideoTitle;//视频标题

    //视频封面
    private String videoCoverImage = "";

    private Dialog mLoading;

    private File imageFile;
    List<String> pictureChooseStr = new ArrayList<>();
    public static int address = 1008;
    public static int picture = 1;
    private String locateCity;
    private String locateAddress;
    private TagAdapter publishTagAdpater;
    private TopicSelectAdapter topicAdapter;
    private ShopChooseAdapter shopChooseAdapter;
    private PublishGoodsWindowDialogFragment liveGoodsWindowDialogFragment;
    private List<ShopGoodsDTO> mList = new ArrayList<>();
    private long productId;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_video_publish;
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerManager != null)
            playerManager.stopMedia();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mType == TYPE_VIDEO) {
            if (binding.videoView.isAvailable()) {
                if (null != videoPath) {
                    playerManager.playMedia(new Surface(binding.videoView.getSurfaceTexture()), videoPath);
                    mPlayStarted = true;
                }
            } else {
                binding.videoView.setSurfaceTextureListener(listener);
            }
        }
    }

    public void release() {
        mPlayStarted = false;
        if (null != playerManager)
            playerManager.stopMedia();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        ARouter.getInstance().inject(this);

        if (mPicture != null && mPicture.size() > 0) {
            mType = TYPE_PICTURE;
            binding.layoutVideo.setVisibility(View.GONE);
            binding.recyclerViewPicture.setHasFixedSize(true);
            binding.recyclerViewPicture.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
            ItemDecoration decoration = new ItemDecoration(mContext, 0x00000000, 8, 8);
            decoration.setOnlySetItemOffsetsButNoDraw(true);
            binding.recyclerViewPicture.addItemDecoration(decoration);
            if (mPicture.size() < 9) {
                PictureChooseBean pictureChooseBean = new PictureChooseBean();
                pictureChooseBean.path = "";
                pictureChooseBean.num = 0;
                mPicture.add(pictureChooseBean);
            }
            pictureChooseAdapter = new PictureChooseAdapter(mContext, mPicture);
            pictureChooseAdapter.setShowSelect(false);
            binding.recyclerViewPicture.setAdapter(pictureChooseAdapter);
            binding.recyclerViewTag.setHasFixedSize(true);
            binding.recyclerViewPicture.setNestedScrollingEnabled(false);

            pictureChooseAdapter.setOnItemClickListener(new OnPictureChooseItemClickListener<PictureChooseBean>() {
                @Override
                public void onItemClick(PictureChooseBean bean, int position) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (TextUtils.isEmpty(bean.getPath())) {
                        DialogUtil.showStringArrayDialog(mContext, new Integer[]{
                                R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
                            @Override
                            public void onItemClick(String text, int tag) {
                                if (tag == R.string.camera) {
                                    DynamicMakeActivity.forward(VideoPublishActivity.this, 1, 0, false, picture);
                                } else {
                                    Intent intent = new Intent(mContext, PictureChooseActivity.class);
                                    intent.putExtra(PictureChooseActivity.PICTURE_CHOOSE_NUM, 9 - mPicture.size() + 1);
                                    startActivityForResult(intent, picture);
                                }
                            }
                        });
                    } else {
                        ImagePreview.getInstance().setContext(mContext).setImage(bean.getPath()).setShowDownButton(false).start();
                    }
                }

                @Override
                public void onItemSelect(PictureChooseBean bean, int position) {
                }

                @Override
                public void onItemDelete(PictureChooseBean bean, final int position) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    DialogUtil.showStringArrayDialog(mContext, new Integer[]{
                            R.string.delete}, new DialogUtil.StringArrayDialogCallback() {
                        @Override
                        public void onItemClick(String text, int tag) {
                            if (tag == R.string.delete) {
                                mPicture.remove(position);
                                pictureChooseAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            });

        } else {
            mType = TYPE_VIDEO;
            binding.recyclerViewPicture.setVisibility(View.GONE);

            if (TextUtils.isEmpty(videoPath)) {
                return;
            }
            playerManager = MediaPlayerManager.getInstance(getApplication());
        }

        binding.MoneyUnit.setText(SpUtil.getInstance().getCoinUnit());
        binding.llShop.setOnClickListener(this);
        binding.btnPub.setOnClickListener(this);
        binding.tvAddress.setOnClickListener(this);
        binding.input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.num != null) {
                    binding.num.setText(s.length() + "/50");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        locateCity = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CITY, "");
        locateAddress = (String) SpUtil.getInstance().getSharedPreference(SpUtil.ADDRESS, "");
        if (!TextUtils.isEmpty(locateCity) && !TextUtils.isEmpty(locateAddress)) {
            binding.tvAddress.setText(locateCity + " · " + locateAddress);
        } else {
            binding.tvAddress.setText((TextUtils.isEmpty(locateCity) ? "" : locateCity) + (TextUtils.isEmpty(locateAddress) ? "" : locateAddress));
        }
        binding.recyclerViewTag.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerViewTag.addItemDecoration(new SpaceItemDecoration(15, 30));
        binding.recyclerViewTag.setHasFixedSize(true);
        binding.recyclerViewTag.setNestedScrollingEnabled(false);

        binding.recyclerViewTopic.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerViewTopic.addItemDecoration(new SpaceItemDecoration(15, 30));
        binding.recyclerViewTopic.setHasFixedSize(true);
        binding.recyclerViewTopic.setNestedScrollingEnabled(false);

        if (mIsShortVideo) {
            findViewById(R.id.ll_private).setVisibility(View.VISIBLE);
            if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_VIDEO_FEE, 0) == 0) {
                findViewById(R.id.layoutPrice).setVisibility(View.VISIBLE);
            }
            binding.llCb.setOnClickListener(this);

            HttpApiAppShortVideo.getShortVideoClassifyList(new HttpApiCallBackArr<AppHotSort>() {
                @Override
                public void onHttpRet(int code, String msg, List<AppHotSort> retModel) {
                    if (code == 1 && null != retModel) {
                        publishTagAdpater = new TagAdapter(retModel);
                        publishTagAdpater.isPublish = true;
                        binding.recyclerViewTag.setAdapter(publishTagAdpater);

                    }
                }
            });
            if (ConfigUtil.getBoolValue(R.bool.containShopping)) {
                binding.cvChoiceShop.setVisibility(View.VISIBLE);
                initShopAdapter();
            } else {
                binding.cvChoiceShop.setVisibility(View.GONE);
            }
        } else {
            HttpApiAppVideo.getTopicList(0, 30, new HttpApiCallBackArr<AppVideoTopic>() {
                @Override
                public void onHttpRet(int code, String msg, List<AppVideoTopic> retModel) {
                    if (code == 1 && retModel != null && retModel.size() > 0) {
                        binding.layoutTopic.setVisibility(View.VISIBLE);
                        topicAdapter = new TopicSelectAdapter(retModel);
                        binding.recyclerViewTopic.setAdapter(topicAdapter);
                    }
                }
            });
        }

        if (mIsShortVideo) {
            binding.tvBtn.setText("发布短视频");
        } else {
            binding.tvBtn.setText("发布动态");
        }
    }

    // 选择关联的商品
    private void initShopAdapter() {
        binding.recyclerShop.setHasFixedSize(true);
        binding.recyclerShop.setNestedScrollingEnabled(false);
        binding.recyclerShop.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        shopChooseAdapter = new ShopChooseAdapter();
        binding.recyclerShop.setAdapter(shopChooseAdapter);
        shopChooseAdapter.setListener(new ShopChooseAdapter.deleteCallBack() {
            @Override
            public void onDelete() {
                mList.clear();
                shopChooseAdapter.setData(mList);
                binding.cbShop.setChecked(false);
                binding.recyclerShop.setVisibility(View.GONE);
            }
        });
    }

    // show出选择商品dialog
    private void showShopDialog() {
        liveGoodsWindowDialogFragment = new PublishGoodsWindowDialogFragment();
        liveGoodsWindowDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "LiveGoodsWindowDialogFragment");
        liveGoodsWindowDialogFragment.getGoodsList();
        liveGoodsWindowDialogFragment.setListener(new PublishGoodsWindowDialogFragment.chooseCallBack() {
            @Override
            public void onChecked(ShopGoodsDTO shopGoodsDTO) {
                if (null != mList && mList.size() > 0) {
                    mList.clear();
                }
                mList.add(shopGoodsDTO);
                shopChooseAdapter.setData(mList);
                if (mList.size() > 0) {
                    binding.recyclerShop.setVisibility(View.VISIBLE);
                    binding.cbShop.setChecked(true);
                    productId = mList.get(0).goodsId;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        DialogUtil.showSimpleDialog(mContext, WordUtil.getString(R.string.video_give_up_pub), "", false, new DialogUtil.SimpleCallback() {
            @Override
            public void onConfirmClick() {
                if (mSaveType == ARouterValueNameConfig.VIDEO_SAVE_PUB) {
                    if (!TextUtils.isEmpty(videoPath)) {
                        File file = new File(videoPath);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
                release();
                VideoPublishActivity.super.onBackPressed();
            }

            @Override
            public void onConfirmClick(String input) {

            }

            @Override
            public void onCancelClick() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        release();
        super.onDestroy();
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_pub) {
            if (mType == TYPE_PICTURE) {
                if (mPicture.size() <= 1) {
                    if (mIsShortVideo || !ConfigUtil.getBoolValue(R.bool.canPublishWord)) {
                        ToastUtil.show("发布图片不能为空");
                    } else {
                        wordsDynamic();
                    }
                } else {
                    publishPicture();
                }
            } else {
                publishVideoImage();
            }
        } else if (v.getId() == R.id.tv_address) {
            ARouter.getInstance().build(ARouterPath.MapActivity).navigation(VideoPublishActivity.this, address);
        } else if (v.getId() == R.id.ll_cb) {
            if (binding.cbPrivate.isChecked())
                binding.cbPrivate.setChecked(false);
            else
                binding.cbPrivate.setChecked(true);
        } else if (v.getId() == R.id.ll_shop) {
            //直播购 关联商品
            showShopDialog();
        }
    }

    //发布文字动态
    public void wordsDynamic() {
        String title = binding.input.getText().toString().trim();
        mVideoTitle = title;
        mLoading = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, WordUtil.getString(R.string.video_pub_ing));
        mLoading.show();
        AppVideo_setVideo bean = new AppVideo_setVideo();
        bean.videoTime = "";
        bean.thumb = "";
        bean.href = "";
        bean.city = locateCity;
        bean.address = locateAddress;
        bean.images = "";
        bean.lat = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT);
        bean.lng = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG);
        bean.musicId = 0;
        bean.title = mVideoTitle;
        bean.type = 0;

        HttpApiAppVideo.setVideo(bean, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                mLoading.dismiss();
                if (code == 1) {
                    ToastUtil.show(msg);
                    finish();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 发布图片
     */
    public void publishPicture() {
        String title = binding.input.getText().toString().trim();
        mVideoTitle = title;
        mLoading = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, WordUtil.getString(R.string.video_pub_ing));
        mLoading.show();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
//                for (int i = 0; i < mPicture.size(); i++) {
//                    if (!mPicture.get(i).getPath().equals("")) {
//                        pictureChooseStr.add(mPicture.get(i).getPath());
//                        emitter.onNext(new SimpleImgBean(i, HuaWeiYun.getInstance().upload(new File(mPicture.get(i).getPath()), HuaWeiYun.IMAGE_PATH)));
//                    }
//                }
                List<File> files = new ArrayList<>();
                for (int i = 0; i < mPicture.size(); i++) {
                    if (!mPicture.get(i).getPath().equals("")) {
                        pictureChooseStr.add(mPicture.get(i).getPath());
                        files.add(new File(mPicture.get(i).getPath()));
                    }
                }
                UploadUtil.getInstance().uploadPictures(2, files, new PictureUploadCallback() {
                    @Override
                    public void onSuccess(String imgStr) {
                        if (!TextUtils.isEmpty(imgStr)) {
                            emitter.onNext(imgStr);
                        } else {
                            if (mLoading != null && mLoading.isShowing()) {
                                mLoading.dismiss();
                            }
                            ToastUtil.show("上传失败");
                        }
                    }

                    @Override
                    public void onFailure() {
                        if (mLoading != null && mLoading.isShowing()) {
                            mLoading.dismiss();
                        }
                        ToastUtil.show("上传失败");
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .compose(VideoPublishActivity.this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String imgStr) {
                        mLoading.dismiss();
                        saveUploadPictureInfo(imgStr);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoading.dismiss();
                        ToastUtil.show("上传图片失败,请稍后重试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 把图片上传后的信息保存在服务器
     */
    private void saveUploadPictureInfo(String imgStr) {
        if (mIsShortVideo) {
            AppShortVideo_setShortVideo bean = new AppShortVideo_setShortVideo();
            bean.videoTime = 0;
            bean.thumb = "";
            bean.href = "";
            bean.city = locateCity;
            bean.address = locateAddress;
            bean.content = mVideoTitle;
            bean.images = imgStr;
            if (binding.cbShop.isChecked()) {
                bean.productId = productId;
            } else {
                bean.productId = 0;
            }
            if (binding.cbPrivate.isChecked()) {
                double coin = TextUtils.isEmpty(binding.etPrice.getText().toString().trim()) ? 0 : Double.parseDouble(binding.etPrice.getText().toString().trim());
                if (coin > 0) {
                    bean.coin = coin;
                    bean.isPrivate = 1;
                } else {
                    bean.coin = 0;
                    bean.isPrivate = 0;
                }
            } else {
                bean.coin = 0;
                bean.isPrivate = 0;
            }
            bean.lat = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT);
            bean.lng = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG);
            bean.type = 2;
            if (null != publishTagAdpater) {
                bean.classifyId = publishTagAdpater.getSelectIds();
            }
            HttpApiAppShortVideo.setShortVideo(bean, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    mLoading.dismiss();
                    if (code == 1) {
                        ToastUtil.show(msg);
                        finish();
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        } else {
            AppVideo_setVideo bean = new AppVideo_setVideo();
            bean.videoTime = "";
            bean.thumb = "";
            bean.href = "";
            bean.city = locateCity;
            bean.address = locateAddress;
            bean.images = imgStr;
            bean.lat = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT);
            bean.lng = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG);
            bean.musicId = 0;
            bean.title = mVideoTitle;
            bean.type = 2;
            if (topicAdapter != null && topicAdapter.id != -1) {
                bean.topicId = topicAdapter.id;
            }
            HttpApiAppVideo.setVideo(bean, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    mLoading.dismiss();
                    if (code == 1) {
                        ToastUtil.show(msg);
                        finish();
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        }
    }

    /**
     * 获取视频封面
     */
    private void publishVideoImage() {
        String title = binding.input.getText().toString().trim();
        //产品要求把视频描述判断去掉
        mVideoTitle = title;
        if (TextUtils.isEmpty(videoPath)) {
            return;
        }
        mLoading = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, WordUtil.getString(R.string.video_pub_ing));
        mLoading.show();
        Bitmap bitmap = null;
        //生成视频封面图
        MediaMetadataRetriever mmr = null;
        try {
            mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videoPath);
            bitmap = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mmr != null) {
                mmr.release();
            }
        }
        String coverImagePath = videoPath.replace(".mp4", ".jpg");
        imageFile = new File(coverImagePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            imageFile = null;
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (bitmap == null || imageFile == null) {
            ToastUtil.show(R.string.video_cover_img_failed);
            return;
        }
        if (bitmap != null) {
            bitmap.recycle();
        }
        VideoUploadBean bean = new VideoUploadBean(new File(videoPath), imageFile);
        UploadUtil.getInstance().uploadVideo(3, bean, new com.xuantongyun.storagecloud.upload.base.VideoUploadCallback() {
            @Override
            public void onSuccess(VideoUploadBean videoUploadBean) {
                videoUrl = videoUploadBean.getResultVideoUrl();
                videoCoverImage = videoUploadBean.getResultImageUrl();
                saveUploadVideoInfo();
            }

            @Override
            public void onFailure() {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                ToastUtil.show("上传失败");
            }
        });
    }

    /**
     * 把视频上传后的信息保存在服务器
     */
    private void saveUploadVideoInfo() {
        if (mIsShortVideo) {
            AppShortVideo_setShortVideo bean = new AppShortVideo_setShortVideo();
            bean.videoTime = (int) VideoTime;
            bean.thumb = videoCoverImage;
            bean.href = videoUrl;
            bean.city = locateCity;
            bean.address = locateAddress;
            bean.content = mVideoTitle;
            bean.images = "";
            if (binding.cbShop.isChecked()) {
                bean.productId = productId;
            } else {
                bean.productId = 0;
            }
            if (binding.cbPrivate.isChecked()) {
                double coin = TextUtils.isEmpty(binding.etPrice.getText().toString().trim()) ? 0 : Double.parseDouble(binding.etPrice.getText().toString().trim());
                if (coin > 0) {
                    bean.coin = coin;
                    bean.isPrivate = 1;
                } else {
                    bean.coin = 0;
                    bean.isPrivate = 0;
                }
            } else {
                bean.coin = 0;
                bean.isPrivate = 0;
            }
            bean.lat = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT);
            bean.lng = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG);
            bean.type = 1;
            if (null != publishTagAdpater) {
                bean.classifyId = publishTagAdpater.getSelectIds();
            }
            HttpApiAppShortVideo.setShortVideo(bean, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    mLoading.dismiss();
                    if (code == 1) {
                        ToastUtil.show(msg);
                        finish();
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        } else {
            AppVideo_setVideo bean = new AppVideo_setVideo();
            bean.videoTime = String.valueOf(VideoTime);
            bean.thumb = videoCoverImage;
            bean.href = videoUrl;
            bean.city = locateCity;
            bean.address = locateAddress;
            bean.images = "";
            bean.lat = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT);
            bean.lng = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG);
            bean.musicId = 0;
            bean.title = mVideoTitle;
            bean.type = 1;
            if (topicAdapter != null && topicAdapter.id != -1) {
                bean.topicId = topicAdapter.id;
            }
            HttpApiAppVideo.setVideo(bean, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    mLoading.dismiss();
                    if (code == 1) {
                        ToastUtil.show(msg);
                        finish();
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == picture) {
                mPicture.remove(mPicture.size() - 1);
                mPicture.addAll(data.<PictureChooseBean>getParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST));
                if (mPicture.size() < 9) {
                    PictureChooseBean pictureChooseBean = new PictureChooseBean();
                    pictureChooseBean.path = "";
                    pictureChooseBean.num = 0;
                    mPicture.add(pictureChooseBean);
                }
                pictureChooseAdapter.notifyDataSetChanged();
            } else if (requestCode == address) {
                locateCity = data.getStringExtra(ARouterValueNameConfig.CITY);
                locateAddress = data.getStringExtra(ARouterValueNameConfig.ADDRESS);
                if (!TextUtils.isEmpty(locateCity) && !TextUtils.isEmpty(locateAddress)) {
                    binding.tvAddress.setText(locateCity + " · " + locateAddress);
                } else {
                    binding.tvAddress.setText((TextUtils.isEmpty(locateCity) ? "" : locateCity) + (TextUtils.isEmpty(locateAddress) ? "" : locateAddress));
                }
            }
        }
    }

    /**
     * camera回调监听
     */
    private TextureView.SurfaceTextureListener listener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            if (videoPath != null) {
                playerManager.playMedia(new Surface(texture), videoPath);
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }
    };

}
