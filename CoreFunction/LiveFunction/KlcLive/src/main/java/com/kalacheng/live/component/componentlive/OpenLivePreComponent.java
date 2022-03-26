package com.kalacheng.live.component.componentlive;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buslive.httpApi.HttpApiHttpLive;
import com.kalacheng.buslive.model_fun.HttpLive_openLive;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAnchorWishList;
import com.kalacheng.libuser.model.ApiFileUploadParams;
import com.kalacheng.libuser.model.ApiUsersLiveWish;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.live.R;
import com.kalacheng.live.component.viewmodel.LiveReadyViewModel;
import com.kalacheng.live.databinding.ViewLiveReadyBinding;
import com.kalacheng.livecommon.adapter.OpenLiveShareAdpater;
import com.kalacheng.livecommon.adapter.OpenLiveWishAdpater;
import com.kalacheng.mob.MobConst;
import com.kalacheng.mob.MobShareUtil;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.util.view.ViewPagerIndicator2;
import com.klc.bean.LiveRoomTypeBean;
import com.klc.bean.OpenLiveBean;
import com.klc.bean.VoiceOpenLiveBean;
import com.xuantongyun.livecloud.config.TTTConfigUtils;
import com.xuantongyun.livecloud.protocol.PulicUtils;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

// 开始直播
public class OpenLivePreComponent extends BaseMVVMViewHolder<ViewLiveReadyBinding, LiveReadyViewModel> implements View.OnClickListener {
    //房间类型
//    private int mLiveType = 0;
    //房间类型值
//    private String mLiveTypeVal="";
    //上传图片的参数
    private ApiFileUploadParams params = new ApiFileUploadParams();
    private ProcessImageUtil mImageUtil;
    private File mAvatarFile;
    private long ChannelID;
    private String ChannelName;
    private String roomTypeName;
    private String LiveShopChannelName;
    private long LiveShopChannelID;
    private ApiUserInfo retModel;
    private OpenLiveWishAdpater adpater;
    private Disposable uploadImgDisposable;
    private OpenLiveBean openLivebean;

    private LiveRoomTypeBean bean;


    private int liveFunction = 0; // 为0时无直播购  1为有直播购

    public OpenLivePreComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_live_ready;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void init() {
        addToParent();
        liveFunction = 0;
        mParentView.findViewById(R.id.btn_start_live).setOnClickListener(this);
        retModel = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);
        openLivebean = SpUtil.getInstance().<OpenLiveBean>getModel(LiveConstants.LiveOpenValue, OpenLiveBean.class);

        bean = new LiveRoomTypeBean();
        binding.btnStartLive.setOnClickListener(this);
        binding.linWish.setOnClickListener(this);
        binding.btnRoomType.setOnClickListener(this);
        binding.btnClose.setOnClickListener(this);
        binding.avatar.setOnClickListener(this);
        binding.btnCamera.setOnClickListener(this);
        binding.location.setOnClickListener(this);
        binding.btnBeauty.setOnClickListener(this);
        binding.liveClass.setOnClickListener(this);
        binding.agreement.setOnClickListener(this);
        binding.liveShopClass.setOnClickListener(this);
        binding.linShop.setOnClickListener(this);
        binding.ivSelectShop.setOnClickListener(this);
        binding.ivAddMore.setOnClickListener(this);

        if (ConfigUtil.getBoolValue(R.bool.containShopping)) {
            binding.OpenLiveIndicator2.setVisibility(View.GONE);
            binding.llShopLayout.setVisibility(View.VISIBLE);
        } else {
            binding.OpenLiveIndicator2.setVisibility(View.GONE);
            binding.llShopLayout.setVisibility(View.GONE);
        }
        initView();
        String[] openLive_label = {"视频直播", "直播购物"};
        binding.OpenLiveIndicator2.setTitles(openLive_label);
        binding.OpenLiveIndicator2.setSelect(0);
        binding.OpenLiveIndicator2.setOnTabClickListener(new ViewPagerIndicator2.OnTabClickListener() {
            @Override
            public void onTabClick(int position, String name) {
                if ("视频直播".equals(name)) {
                    liveFunction = 0;
                    binding.OpenLiveIndicator2.setSelect(0);
                } else if ("直播购物".equals(name)) {
                    liveFunction = 1;
                    binding.OpenLiveIndicator2.setSelect(1);
                }
                initView();

            }
        });

        binding.wishList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    binding.linWish.performClick();  //模拟父控件的点击
                }
                return false;
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        binding.wishList.setLayoutManager(manager);
        adpater = new OpenLiveWishAdpater(mContext);
        binding.wishList.setAdapter(adpater);

        mImageUtil = new ProcessImageUtil((FragmentActivity) mContext);

        ImageLoader.display(retModel.liveThumb, binding.avatar);


        String str = "开播默认已阅读并同意<font color='#A515FF'>《主播协议》</font>";
        binding.agreement.setText(Html.fromHtml(str));

        if (openLivebean != null) {

            if (openLivebean.roomTypeName == null) {
                binding.btnRoomType.setText("普通房间");
//                mLiveTypeVal = "";
//                mLiveType = 0;
                bean.id = 0;
                bean.mContent = "";
            } else {
                binding.btnRoomType.setText(openLivebean.roomTypeName);
//                mLiveTypeVal = openLivebean.typeVal;
//                mLiveType = openLivebean.type;
                roomTypeName = openLivebean.roomTypeName;
                bean.id = openLivebean.type;
                bean.mContent = openLivebean.typeVal;
            }
            if (openLivebean.LiveShopChannelName == null) {
                binding.liveShopClass.setText("购物分类");
                LiveShopChannelID = 0;
            } else {
                LiveShopChannelName = openLivebean.LiveShopChannelName;
                LiveShopChannelID = openLivebean.LiveShopChannelID;
                binding.liveShopClass.setText(openLivebean.LiveShopChannelName);
            }

            if (openLivebean.ChannelName == null) {
                binding.liveClass.setText(mContext.getResources().getString(R.string.live_class));
                ChannelID = 0;
            } else {
                ChannelName = openLivebean.ChannelName;
                ChannelID = openLivebean.channelId;
                binding.liveClass.setText(openLivebean.ChannelName);

            }

            binding.editTitle.setText(openLivebean.title);
        }


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
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtil.show("上传失败");
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailure() {

                                }
                            });
//                            emitter.onNext(HuaWeiYun.getInstance().upload(file, HuaWeiYun.IMAGE_PATH));
                        }
                    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull final String url) throws Exception {
                            if (null != url && !TextUtils.isEmpty(url)) {
//                                uploadDialog.dismiss();
                                HttpApiAppUser.user_update(null, null, null, null, null, -1, -1, url, -1, null, -1, null, null, null, null, -1, new HttpApiCallBack<HttpNone>() {
                                    @Override
                                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                                        if (code == 1) {
                                            ToastUtil.show("上传图片成功");

                                            ImageLoader.display(file, binding.avatar);
                                            ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);
                                            apiUserInfo.liveThumb = url;
                                            SpUtil.getInstance().putModel(SpUtil.USER_INFO, apiUserInfo);
                                            if (OpenLivePreComponent.this.retModel != null) {
                                                OpenLivePreComponent.this.retModel.liveThumb = url;
                                            }
//                                            VideoLiveUtils.getInstance().enableLocalVideo(true);
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
                if (file != null) {
                    if (mAvatarFile == null) {
                        binding.coverText.setText(WordUtil.getString(R.string.live_cover_2));
                    }
                }
            }

            @Override
            public void onFailure() {
            }
        });


        //获取直播类型值
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ChoiceLiveTypeValue, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
//                mLiveTypeVal = ((LiveRoomTypeBean) o).mContent;
//                mLiveType = ((LiveRoomTypeBean) o).id;
                bean.mContent = ((LiveRoomTypeBean) o).mContent;
                bean.id = ((LiveRoomTypeBean) o).id;
                roomTypeName = ((LiveRoomTypeBean) o).name;
                binding.btnRoomType.setText(((LiveRoomTypeBean) o).name);

                openLivebean = SpUtil.getInstance().<OpenLiveBean>getModel(LiveConstants.LiveOpenValue, OpenLiveBean.class);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //获取直播频道数据
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ChoiceChannelTypeValue, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                ChannelID = ((AppLiveChannel) o).id;
                ChannelName = ((AppLiveChannel) o).title;
                binding.liveClass.setText(ChannelName);
                if (liveFunction == 1) {
                    LiveShopChannelID = ((AppLiveChannel) o).id;
                    LiveShopChannelName = ((AppLiveChannel) o).title;
                    binding.liveShopClass.setText(LiveShopChannelName);
                }
                openLivebean = SpUtil.getInstance().<OpenLiveBean>getModel(LiveConstants.LiveOpenValue, OpenLiveBean.class);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //心愿单回调
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_AddWishListSuccess, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
//                List<ApiUsersLiveWish> liveWishes = (List<ApiUsersLiveWish>) o;
//                if (liveWishes.size() > 0) {
//                    binding.wishList.setVisibility(View.VISIBLE);
//                    binding.btnRoomWish.setVisibility(View.GONE);
//                    binding.btnRoomWishImage.setBackgroundResource(R.mipmap.bg_wish_set);
//                    adpater.setWish(liveWishes);
//                } else {
//                    binding.wishList.setVisibility(View.GONE);
//                    binding.btnRoomWish.setVisibility(View.VISIBLE);
//                    binding.btnRoomWishImage.setBackgroundResource(R.mipmap.icon_live_ready_room_wish);
//                }

                getWishList();

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        // 切换普通直播和带货直播 type
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_UPDATE_LIVE_STATUS, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                try {
                    liveFunction = (int) o;
                    startLive();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //地图定位
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_GetMapAddress, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                String city = (String) SpUtil.getInstance().getSharedPreference(SpUtil.LIVE_CITY, "");
                String address = (String) SpUtil.getInstance().getSharedPreference(SpUtil.LIVEADDRESS, "");
                if (TextUtils.isEmpty(city) || TextUtils.isEmpty(address)) {
                    city = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CITY, "");
                    address = (String) SpUtil.getInstance().getSharedPreference(SpUtil.ADDRESS, "");
                }
                binding.location.setText(city + "·" + address);
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //修改房间成功刷新数据
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ChoiceLiveTypeSusser, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (o instanceof OpenLiveBean){
                    OpenLiveBean openLiveBean = (OpenLiveBean) o;
                    bean.id = openLiveBean.type;
                    bean.mContent = openLiveBean.typeVal;
                }else if (o instanceof VoiceOpenLiveBean){
                    VoiceOpenLiveBean voiceOpenLiveBean = (VoiceOpenLiveBean) o;
                    bean.id = voiceOpenLiveBean.type;
                    bean.mContent = voiceOpenLiveBean.typeVal;
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {
            }
        });

        getShare();
        getWishList();
    }

    //ui初始化
    public void initView() {
        if (liveFunction == 0) {
            binding.openLin.setVisibility(View.VISIBLE);
            binding.liveShopClass.setVisibility(View.GONE);
            binding.linWish.setVisibility(View.VISIBLE);
            binding.linShop.setVisibility(View.GONE);
            binding.linShopLabel.setVisibility(View.GONE);
        } else {
            binding.openLin.setVisibility(View.GONE);
//            binding.liveShopClass.setVisibility(View.VISIBLE);
            binding.linWish.setVisibility(View.GONE);
            binding.linShop.setVisibility(View.VISIBLE);
            binding.linShopLabel.setVisibility(View.VISIBLE);
        }
    }

    // 开始直播
    public void startLive() {
        if (LiveConstants.isSamll) {
            ToastUtil.show("请先退出语音房间");
            return;
        }
        final HttpLive_openLive liveLive = new HttpLive_openLive();
        final ApiUserInfo userBean = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);

        if (binding.editTitle.getText().toString() == null || binding.editTitle.getText().toString().equals("")) {
            ToastUtil.show("请选择直播标题");
            return;
        }

        liveLive.nickname = userBean.username;
        liveLive.title = binding.editTitle.getText().toString();
        liveLive.liveType = 1;
        if (ChannelID == 0) {
            ToastUtil.show("请选择频道");
            return;
        }
        liveLive.roomType = bean.id;
        liveLive.roomTypeVal = bean.mContent;
        liveLive.shopRoomLabel = "";
        liveLive.channelId = (int) ChannelID;

        if (ConfigUtil.getBoolValue(R.bool.containShopping)) {
            if (liveFunction == 0) {
                binding.ivSelectShop.setChecked(false);
                if (binding.ivSelectShop.isChecked()) {
                    liveLive.liveFunction = 1;
                } else {
                    liveLive.liveFunction = 0;
                }
            } else {
                liveLive.liveFunction = 1;
            }
        } else {
            liveLive.liveFunction = 0;
        }

        liveLive.thumb = retModel.liveThumb;
        liveLive.pull = TTTConfigUtils.getInstance().getTTTPull() + "/" + HttpClient.getUid();
        liveLive.lat = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT);
        liveLive.lng = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG);
        HttpApiHttpLive.openLive(liveLive, new HttpApiCallBack<AppJoinRoomVO>() {
            @Override
            public void onHttpRet(int code, String msg, AppJoinRoomVO retModel) {
                if (code == 1 && null != retModel) {
                    clear();
                    LiveConstants.ROOMID = retModel.roomId;
                    LiveConstants.PUSH = retModel.push;
                    LiveConstants.IsRemoteAudio = false;
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OpenLiveMsg, retModel);
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.RoomInfoList, retModel);


                    OpenLiveBean openLiveBean = new OpenLiveBean();
                    openLiveBean.ChannelName = ChannelName;
                    openLiveBean.roomTypeName = roomTypeName;
                    openLiveBean.channelId = (int) ChannelID;
                    openLiveBean.nickname = userBean.username;
                    openLiveBean.title = binding.editTitle.getText().toString();
                    openLiveBean.type = bean.id;
                    openLiveBean.typeVal = bean.mContent;
                    openLiveBean.thumb = retModel.liveThumb;
                    openLiveBean.LiveShopChannelID = LiveShopChannelID;
                    openLiveBean.LiveShopChannelName = LiveShopChannelName;

                    openLiveBean.lat = 0;
                    openLiveBean.lng = 0;

                    SpUtil.getInstance().putModel(LiveConstants.LiveOpenValue, openLiveBean);

                    //分享
                    ShareAction();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });


    }


    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        int i = view.getId();
        if (i == R.id.btn_start_live) { // 开启直播
            startLive();
        } else if (i == R.id.lin_wish) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddWishList, R.mipmap.icon_live_func_wish);
        } else if (i == R.id.btn_room_type) {
            if (openLivebean == null) {
                bean.mContent = "";
                bean.id = 0;
            } else {
                bean.mContent = openLivebean.typeVal;
                bean.id = openLivebean.type;
            }

            bean.type = 2;
            bean.LiveType = 1;
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceLiveType, bean);

        } else if (i == R.id.btn_close) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
        } else if (i == R.id.avatar) {
            setAvatar();
        } else if (i == R.id.btn_camera) {
            PulicUtils.getInstance().switchCamera();
        } else if (i == R.id.location) {
            ARouter.getInstance().build(ARouterPath.MapActivity).withInt(ARouterValueNameConfig.TYPE, 1).navigation();
        } else if (i == R.id.btn_beauty) {
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_BeautyShow, null);
        } else if (i == R.id.live_class) {//直播频道模式
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceChannelType, 1);
        } else if (i == R.id.agreement) {
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl() + "/api/login/appSite?type=5").navigation();
        } else if (i == R.id.liveShop_class) {//购物选择频道
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceChannelType, 6);

        } else if (i == R.id.lin_shop) {//直播购添加商品
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGoodsWindow, null);
        } else if (i == R.id.iv_select_shop) {
            //  liveFunction 是否开启直播卖货  1是有直播购物
            if (binding.ivSelectShop.isChecked()) {
                liveFunction = 1;
                binding.ivSelectShop.setChecked(true);
//                binding.liveShopClass.setVisibility(View.VISIBLE);
            } else {
                liveFunction = 0;
                binding.ivSelectShop.setChecked(false);
//                binding.liveShopClass.setVisibility(View.GONE);
            }
        } else if (i == R.id.iv_add_more) {
            // 添加直播商品
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGoodsWindow, null);
        }

    }

    /**
     * 设置头像
     */
    private void setAvatar() {
        DialogUtil.showStringArrayDialog(mContext, new Integer[]{
                R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.camera) {
                    mImageUtil.getImageByCamera();
                } else {
                    mImageUtil.getImageByAlbumCustom();
                }
            }
        });
    }

    OpenLiveShareAdpater Shareadapter;
    private String ShareID = "";

    @SuppressLint("WrongConstant")
    public void getShare() {
        String shareType = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_SHARE_TYPE, "");
        if (!TextUtils.isEmpty(shareType)) {
            //分享方式 1QQ2qq空间3微信4微信朋友圈
            String[] share = shareType.split(",");
            final List<String> shareTypeList = Arrays.asList(share);
            if (shareTypeList != null) {
                Shareadapter = new OpenLiveShareAdpater(mContext, shareTypeList);

                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(OrientationHelper.HORIZONTAL);
                binding.LiveShareRecyclerView.setLayoutManager(manager);
                binding.LiveShareRecyclerView.addItemDecoration(new ItemDecoration(mContext, 0, 10, 0));
                binding.LiveShareRecyclerView.setAdapter(Shareadapter);
                Shareadapter.setOnItemClickListener(new OpenLiveShareAdpater.OpenLiveShareCallBack() {
                    @Override
                    public void onClick(int position, int lastposition) {
                        if (position == lastposition) {
                            Shareadapter.getSelection(-1);
                            ShareID = "";
                        } else {
                            Shareadapter.getSelection(position);
                            ShareID = shareTypeList.get(position).toString();
                        }


                    }

                });
//                Shareadapter.getSelection(0);
            }
        }
    }

    //分享操作
    public void ShareAction() {
        switch (ShareID) {
            case "1":
                MobShareUtil.getInstance().shareLive(1, 1, MobConst.Type.WX);
                break;
            case "2":
                MobShareUtil.getInstance().shareLive(1, 1, MobConst.Type.WX_PYQ);
                break;
            case "3":
                MobShareUtil.getInstance().shareLive(1, 1, MobConst.Type.QQ);
                break;
            case "4":
                MobShareUtil.getInstance().shareLive(1, 1, MobConst.Type.QZONE);
                break;
        }
    }

    //获取心愿单
    public void getWishList() {
        HttpApiAnchorWishList.getWishList(HttpClient.getUid(), new HttpApiCallBackArr<ApiUsersLiveWish>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLiveWish> retModel) {
                if (code == 1) {
                    if (retModel.size() > 0) {
                        binding.wishList.setVisibility(View.VISIBLE);
                        binding.btnRoomWish.setVisibility(View.GONE);
                        adpater.setWish(retModel);
                    } else {
                        binding.wishList.setVisibility(View.GONE);
                        binding.btnRoomWish.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void clear() {
        removeFromParent();
    }
}
