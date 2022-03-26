package com.kalacheng.center.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.httpApi.HttpApiShopOrder;
import com.kalacheng.busshop.model.AppMerchantAgreementDTO;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.center.CenterConfig;
import com.kalacheng.center.R;
import com.kalacheng.center.adapter.TabMeBottomAdapter;
import com.kalacheng.centercommon.dialog.ReadMeRequireDialog;
import com.kalacheng.commonview.activity.BaseMainActivity;
import com.kalacheng.commonview.dialog.AnchorRequestDialog;
import com.kalacheng.commonview.dialog.OpenSVipDialogFragment;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.base.event.MeFragmentRefreshEvent;
import com.kalacheng.base.event.SignEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiMyShortVideo;
import com.kalacheng.libuser.model.ApiUserIndexResp;
import com.kalacheng.libuser.model.ShopOrderNumDTO;
import com.kalacheng.util.adapter.SimpleImgTextAdapter;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.SystemUtils;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * 个人中心
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private SmartRefreshLayout refreshMe;
    private ImageView ivAvatar, ivGrade, ivWealthGrade, ivNobleGrade;
    private TextView tvNickName, tvFollow, tvZan, tvZanInfo, tvFans, tvSign, tvIdName, tvId;
    private TextView tvRead;
    private TextView tvEyeRed;
    private LinearLayout layoutSex;
    //操作栏1
    private RecyclerView recyclerViewTabMe1;
    private RecyclerView recyclerViewTabMeBottom;
    private TabMeBottomAdapter tabMeBottomAdapter;
    private ImageView ivSignIn;//签到
    private View ivSingInView;

    /**
     * 谁看过我是否需要贵族开关0需要1不需要
     */
    private int isVipSee;
    /**
     * 是否为VIP
     */
    private boolean isVip;
    private int mRole;//是否为主播
    private int mAnchorAuditStatus;//主播审核状态
    private PermissionsUtil mProcessResultUtil;
    private int mIsNotDisturb;

    //是否开启青少年模式
    private int isOpenYoung = 2;//1开启 2未开启

    public MeFragment(Context mContext, ViewGroup mParentView) {
        this.mContext = mContext;

    }

    public MeFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_me;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
        mProcessResultUtil = new PermissionsUtil(getActivity());
        ivAvatar = mParentView.findViewById(R.id.iv_avatar);
        tvIdName = mParentView.findViewById(R.id.tvIdName);
        tvId = mParentView.findViewById(R.id.tvId);
        tvNickName = mParentView.findViewById(R.id.tv_nickname);
        tvZan = mParentView.findViewById(R.id.tv_zan_num);
        tvZanInfo = mParentView.findViewById(R.id.tv_zan_info);
        if (ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
            tvZanInfo.setText("我的视图");
        }
        tvFollow = mParentView.findViewById(R.id.tv_follow_num);
        tvFans = mParentView.findViewById(R.id.tv_fans_num);
        tvRead = mParentView.findViewById(R.id.tv_read_num);
        tvSign = mParentView.findViewById(R.id.tv_sign);
        ivGrade = mParentView.findViewById(R.id.iv_grade);
        layoutSex = mParentView.findViewById(R.id.layoutSex);
        ivWealthGrade = mParentView.findViewById(R.id.ivWealthGrade);
        ivNobleGrade = mParentView.findViewById(R.id.ivNobleGrade);
        tvEyeRed = mParentView.findViewById(R.id.tvEyeRed);
        refreshMe = mParentView.findViewById(R.id.refreshMe);

        mParentView.findViewById(R.id.layoutMeTitle).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_follow).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_fans).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_zan).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_browse).setOnClickListener(this);

        if (!ConfigUtil.getBoolValue(R.bool.showMeFragmentAdd)) {
            mParentView.findViewById(R.id.ivOpenMedia).setVisibility(View.GONE);
        }

        mParentView.findViewById(R.id.ivOpenMedia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                BaseMainActivity activity = (BaseMainActivity) getActivity();
                activity.startOnClick(v);
            }
        });

        refreshMe.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                EventBus.getDefault().post(new MeFragmentRefreshEvent());
                getMyHeadInfo();
                isVisit();
                if (ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
                    getUserVideo();
                }
                if (ConfigUtil.getBoolValue(R.bool.containShopping)) {
                    getOrderCount();
                }
            }
        });

        recyclerViewTabMe1 = mParentView.findViewById(R.id.recyclerViewTabMe1);
        recyclerViewTabMe1.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerViewTabMe1.setHasFixedSize(true);
        recyclerViewTabMe1.setNestedScrollingEnabled(false);
        List<SimpleImageUrlTextBean> tabBeans1 = new ArrayList<>();
        String[] names = getResources().getStringArray(R.array.me_top_names);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.me_top_ids);
        if (names.length == typedArray.length()) {
            for (int i = 0; i < names.length; i++) {
                tabBeans1.add(new SimpleImageUrlTextBean(typedArray.getResourceId(i, 0), names[i]));
            }
        }
        SimpleImgTextAdapter tabAdapter1 = new SimpleImgTextAdapter(tabBeans1);
        tabAdapter1.setImgWidthHeight(40, 40);
        tabAdapter1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        tabAdapter1.setPadding(0, 0, 0, 6);
        tabAdapter1.setTextColor("#666666");
        tabAdapter1.setTextSize(12);
        recyclerViewTabMe1.setAdapter(tabAdapter1);
        recyclerViewTabMe1.addItemDecoration(new ItemDecoration(getActivity(), 0x00000000, 0, 10));
        tabAdapter1.setSex(apiUserInfo.sex);
        tabAdapter1.setOnItemClickCallback(new OnBeanCallback<SimpleImageUrlTextBean>() {
            @Override
            public void onClick(SimpleImageUrlTextBean bean) {
                onItemClick(bean.src);
            }
        });

        recyclerViewTabMeBottom = mParentView.findViewById(R.id.recyclerViewTabMeBottom);
        recyclerViewTabMeBottom.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewTabMeBottom.setHasFixedSize(true);
        recyclerViewTabMeBottom.setNestedScrollingEnabled(false);
        List<SimpleImageUrlTextBean> tabBottomBeans = new ArrayList<>();
        String[] bottomNames = CenterConfig.getBottomNames();
        TypedArray bottomIds = CenterConfig.getBottomIds();
        if (bottomNames.length == bottomIds.length()) {
            for (int i = 0; i < bottomNames.length; i++) {
                tabBottomBeans.add(new SimpleImageUrlTextBean(bottomIds.getResourceId(i, 0), bottomNames[i]));
            }
        }
        tabMeBottomAdapter = new TabMeBottomAdapter(mContext);
        tabMeBottomAdapter.setData(tabBottomBeans);
        recyclerViewTabMeBottom.setAdapter(tabMeBottomAdapter);
        tabMeBottomAdapter.setSex(apiUserInfo.sex);
        tabMeBottomAdapter.setOnItemClickCallback(new OnItemClickCallback<SimpleImageUrlTextBean>() {
            @Override
            public void onClick(View view, SimpleImageUrlTextBean item) {
                onItemClick(item.src);
            }
        });
        tabMeBottomAdapter.setOnNoDisturbListener(new TabMeBottomAdapter.OnNoDisturbListener() {
            @Override
            public void onClick() {
                final int type;
                if (mIsNotDisturb == 0) {
                    type = 1;
                } else {
                    type = 0;
                }
                HttpApiAppUser.isNotDisturb(type, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            mIsNotDisturb = type;
                            if (mIsNotDisturb == 0) {
                                if (tabMeBottomAdapter != null) {
                                    tabMeBottomAdapter.setNoDisturb(false);
                                }
                                ToastUtil.show("修改成功");
                            } else {
                                tabMeBottomAdapter.setNoDisturb(true);
                                DialogUtil.showKnowDialog(getContext(), "你已经开启了免打扰模式" + "\n" + "将暂时收不到通话提醒哦~", null);
                            }
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });

            }
        });

        ivSignIn = mParentView.findViewById(R.id.ivSignIn);
        ivSingInView = mParentView.findViewById(R.id.ivSingInView);
        ivSignIn.setOnClickListener(this);
        BaseMainActivity baseMainActivity = (BaseMainActivity) getActivity();
        if (baseMainActivity != null && baseMainActivity.signInDto != null && baseMainActivity.signInDto.isSign == 0) {
            ivSingInView.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        HttpApiAppUser.info_index(new HttpApiCallBack<ApiUserIndexResp>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserIndexResp retModel) {
                if (code == 1 && null != retModel) {
                    SpUtil.getInstance().putModel("ApiUserIndexResp", retModel);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getMyHeadInfo();
        isVisit();
        if (ConfigUtil.getBoolValue(R.bool.containShopping)) {
            getOrderCount();
        }
        if (ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
            getUserVideo();
        }

    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.layoutMeTitle) {
            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, HttpClient.getUid()).navigation();
        } else if (view.getId() == R.id.ll_follow) {//关注
            ARouter.getInstance().build(ARouterPath.Follow).withLong(ARouterValueNameConfig.USERID, HttpClient.getUid()).navigation();
        } else if (view.getId() == R.id.ll_fans) {
            ARouter.getInstance().build(ARouterPath.Fans).withLong(ARouterValueNameConfig.USERID, HttpClient.getUid()).withInt(ARouterValueNameConfig.TYPE, 0).navigation();
        } else if (view.getId() == R.id.ll_zan) {
            if (ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
                //我的视图
                ARouter.getInstance().build(ARouterPath.MyViewActivity).navigation();
            }
        } else if (view.getId() == R.id.ll_browse) {
            if (isVip || isVipSee == 1) {
                ARouter.getInstance().build(ARouterPath.Fans).withInt(ARouterValueNameConfig.TYPE, 1).navigation();
                HttpApiAppUser.delVisit(new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {

                    }
                });
            } else {
                ReadMeRequireDialog readMeRequireDialog = new ReadMeRequireDialog();
                readMeRequireDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "ReadMeRequireDialog");
            }
        } else if (view.getId() == R.id.ivSignIn) {//签到
            BaseMainActivity baseMainActivity = (BaseMainActivity) getActivity();
            if (baseMainActivity != null) {
                baseMainActivity.showSignInDialog();
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 获取头部信息
     */
    private void getMyHeadInfo() {
        HttpApiAppUser.getMyHeadInfo(new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    if (TextUtils.isEmpty(retModel.goodnum)) {
                        tvIdName.setTextColor(Color.parseColor("#999999"));
                        tvId.setTextColor(Color.parseColor("#999999"));
                        tvIdName.setText("ID号：");
                        tvId.setText(retModel.userId + "");
                    } else {
                        tvIdName.setTextColor(Color.parseColor("#F6B86A"));
                        tvId.setTextColor(Color.parseColor("#F6B86A"));
                        tvIdName.setText("靓号：");
                        tvId.setText(retModel.goodnum);
                    }
                    tvNickName.setText(retModel.username);
                    tvFollow.setText(retModel.followNum + "");
                    tvFans.setText(retModel.fansNum + "");
                    if (!ConfigUtil.getBoolValue(R.bool.containShortVideo)) {
                        tvZan.setText(retModel.likeNum + "");
                    }
                    tvRead.setText(retModel.readMeUsersNumber + "");
                    String signStr = retModel.signature.trim();
                    SexUtlis.getInstance().setSex(mContext, layoutSex, retModel.sex, retModel.age);
                    mRole = retModel.role;
                    mAnchorAuditStatus = retModel.anchorAuditStatus;
                    ImageLoader.display(retModel.wealthGradeImg, ivWealthGrade);
                    if (retModel.role == 1) {
                        ImageLoader.display(retModel.anchorGradeImg, ivGrade);
                    } else {
                        ImageLoader.display(retModel.userGradeImg, ivGrade);
                    }
                    if (retModel.nobleExpireDay <= 0) {
                        isVip = false;
                        ivNobleGrade.setVisibility(View.GONE);
                    } else {
                        isVip = true;
                        ivNobleGrade.setVisibility(View.VISIBLE);
                        ImageLoader.display(retModel.nobleGradeImg, ivNobleGrade);
                    }
                    if (!TextUtils.isEmpty(signStr)) {
                        tvSign.setText(signStr);
                    } else {
                        tvSign.setText("这个家伙很懒,什么也没说...");
                    }
                    if (null != retModel.avatar && !TextUtils.isEmpty(retModel.avatar)) {
                        ImageLoader.display(retModel.avatar, ivAvatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    } else {
                        ivAvatar.setImageResource(R.mipmap.ic_launcher);
                    }

                    isVipSee = retModel.isVipSee;

                    ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                    apiUserInfo.isSvip = retModel.isSvip;
                    SpUtil.getInstance().putModel(SpUtil.USER_INFO, apiUserInfo);

                }

                refreshMe.finishRefresh();
            }
        });
        HttpApiAppUser.personCenter(-1, -1, HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    mIsNotDisturb = retModel.isNotDisturb;
                    if (retModel.isNotDisturb == 0) {
                        if (tabMeBottomAdapter != null) {
                            tabMeBottomAdapter.setNoDisturb(false);
                        }
                    } else {
                        if (tabMeBottomAdapter != null) {
                            tabMeBottomAdapter.setNoDisturb(true);
                        }
                    }
                    isOpenYoung = retModel.isYouthModel;
                    tabMeBottomAdapter.setisOpenYoung(isOpenYoung);
                }
            }
        });
    }

    /**
     * 是否有最新访问信息，显示红点
     */
    private void isVisit() {
        HttpApiAppUser.isVisit(new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1 && retModel != null) {
                    if ("0".equals(retModel.no_use)) {
                        tvEyeRed.setVisibility(View.GONE);
                    } else {
                        tvEyeRed.setVisibility(View.VISIBLE);
                        tvEyeRed.setText(retModel.no_use);
                    }
                }
            }
        });
    }

    /**
     * 获取短视频数量
     */
    private void getUserVideo() {
        HttpApiAppShortVideo.getUserShortVideoList(20, HttpClient.getUid(), new HttpApiCallBack<ApiMyShortVideo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiMyShortVideo retModel) {
                if (code == 1 && retModel != null) {
                    tvZan.setText((retModel.myNumber + retModel.likeNumber + retModel.buyNumber) + "");
                }
            }
        });
    }

    /**
     * 官方小店是否入驻
     */
    private void settleIn() {
        final Dialog dialog = DialogUtil.loadingDialog(getContext());
        dialog.show();
        HttpApiShopBusiness.settleIn(new HttpApiCallBack<AppMerchantAgreementDTO>() {
            @Override
            public void onHttpRet(int code, String msg, AppMerchantAgreementDTO retModel) {
                if (code == 1) {
                    if (retModel.status == 0) {
                        ARouter.getInstance().build(ARouterPath.MoveInAgreeActivity)
                                .withString(ARouterValueNameConfig.TITLE_NAME, retModel.postTitle)
                                .withString(ARouterValueNameConfig.POST, retModel.postExcerpt)
                                .navigation();
                    } else {
                        ARouter.getInstance().build(ARouterPath.ShopManageActivity)
                                .withString(ARouterValueNameConfig.AuditRemake, retModel.remake)
                                .withInt(ARouterValueNameConfig.AuditStatus, retModel.status)
                                .navigation();
                    }

                }
                dialog.dismiss();
            }
        });

    }

    private void getOrderCount() {
        HttpApiShopOrder.getOrderNum(1, new HttpApiCallBack<ShopOrderNumDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopOrderNumDTO retModel) {
                if (code == 1 && retModel != null) {
                    tabMeBottomAdapter.setOrderNum(retModel.toBePayNum + retModel.toBeDeliveredNum + retModel.toBeReceivedNum + retModel.cancelGoodsNum);
                }
            }
        });
        HttpApiShopOrder.getOrderNum(2, new HttpApiCallBack<ShopOrderNumDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopOrderNumDTO retModel) {
                if (code == 1 && retModel != null) {
                    tabMeBottomAdapter.setShopOrderNum(retModel.toBeDeliveredNum + retModel.cancelGoodsNum);
                }
            }
        });
    }

    /**
     * 是否为主播
     */
    private boolean isAnchor() {
        if (mRole == 1) {
            return true;
        } else {
            AnchorRequestDialog anchorRequestDialog = new AnchorRequestDialog();
            anchorRequestDialog.setAnchorAuditStatus(mAnchorAuditStatus);
            anchorRequestDialog.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "AnchorRequestDialog");
            return false;
        }
    }

    private void onItemClick(int src) {
        if (src == R.mipmap.icon_me_code) {//邀请赚钱
            ARouter.getInstance().build(ARouterPath.InvitationCodeActivity).navigation();
        } else if (src == R.mipmap.icon_me_shop) {//装扮中心
            ARouter.getInstance().build(ARouterPath.DressingCenterActivity).navigation();

//            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
//                    + "/static/h5page/index.html#/byDress?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=10&pageIndex=0&anchorId=" + HttpClient.getUid())
//                    .withInt(ARouterValueNameConfig.WebActivityType, 2).navigation();
        } else if (src == R.mipmap.icon_me_privilege) {//等级特权
//            ARouter.getInstance().build(ARouterPath.MyPrivilegeActivity).navigation();
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                    + "/static/h5page/index.html#/privilege?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).
                    withInt(ARouterValueNameConfig.WebActivityType, 4).navigation();
        } else if (src == R.mipmap.icon_me_account) {//充值中心
            ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
        } else if (src == R.mipmap.icon_me_noble) {//贵族中心
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                    + HttpConstants.URL_NOBLE + "_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
        } else if (src == R.mipmap.icon_me_rank) {//排行榜
            ARouter.getInstance().build(ARouterPath.RankListActivity).navigation();
        } else if (src == R.mipmap.icon_me_mission_center) {//任务中心
            ARouter.getInstance().build(ARouterPath.UserTaskCenterActivity).navigation();
        } else if (src == R.mipmap.icon_profit) {//收益中心
            if (isAnchor()) {
                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                        + "/static/h5page/index.html#/userRevenue?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
            }
        } else if (src == R.mipmap.icon_me_fans) {//粉丝团
            if (isAnchor()) {
                ARouter.getInstance().build(ARouterPath.FansGroupActivity).navigation();
            }
        } else if (src == R.mipmap.icon_me_contribution) {//贡献榜
            if (isAnchor()) {
                ARouter.getInstance().build(ARouterPath.FansContributionActivity).navigation();
            }
        } else if (src == R.mipmap.icon_me_live_data) {//直播数据
            if (isAnchor()) {
                ARouter.getInstance().build(ARouterPath.FansLiveDataActivity).navigation();
            }
        } else if (src == R.mipmap.icon_me_guild) {//我的公会
            if (isAnchor()) {
                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                        + "/api/guild/toGuildList?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=10&pageIndex=0&anchorId=" + HttpClient.getUid()).navigation();
            }
        } else if (src == R.mipmap.icon_me_1vs1) {//话费设置
            if (isAnchor()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mProcessResultUtil.requestPermissions(new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO
                    }, new Runnable() {
                        @Override
                        public void run() {
                            ARouter.getInstance().build(ARouterPath.One2OneCallActivity).navigation();
                        }
                    });
                }
            }
        } else if (src == R.mipmap.icon_me_pay_set) {//付费设置
            if (isAnchor()) {
                ARouter.getInstance().build(ARouterPath.PaySettingActivity).navigation();
            }
        } else if (src == R.mipmap.icon_me_official_shop) {//官方小店
            if (isAnchor()) {
                settleIn();
            }
        } else if (src == R.mipmap.icon_no_disturb) {//免打扰

        } else if (src == R.mipmap.icon_me_svip) {//SVip
            OpenSVipDialogFragment openSVipDialogFragment = new OpenSVipDialogFragment();
            openSVipDialogFragment.show(getChildFragmentManager(), "OpenSVipDialogFragment");
        } else if (src == R.mipmap.icon_me_order) {//购物订单
            ARouter.getInstance().build(ARouterPath.OrderManageActivity1).navigation();
        } else if (src == R.mipmap.icon_me_time_axis) {//我的时间轴
            ARouter.getInstance().build(ARouterPath.MyTimeAxisActivity).navigation();
        } else if (src == R.mipmap.icon_me_beauty_setting) {//美颜设置
            mProcessResultUtil.requestPermissions(new String[]{
                    Manifest.permission.CAMERA
            }, new Runnable() {
                @Override
                public void run() {
                    ARouter.getInstance().build(ARouterPath.BeautySettingActivity).navigation();
                }
            });
        } else if (src == R.mipmap.icon_me_customer_service) {//客服
            ARouter.getInstance().build(ARouterPath.CustomerServeActivity)
                    .navigation();

           /* String hotLine = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_HOT_LINE, "");
            if (TextUtils.isEmpty(hotLine)) {
                ToastUtil.show("暂未设置，请稍候拨打");
            } else {
                SystemUtils.dial(hotLine);
            }*/
        } else if (src == R.mipmap.icon_me_setting) {//设置
            // settingStyle 设置界面样式，0 接口获取；1 APP自定义
            ARouter.getInstance().build(ARouterPath.SettingApp)
                    .navigation();
        } else if (src == R.mipmap.icon_me_anchor_center) {//青少年模式
            ARouter.getInstance().build(ARouterPath.YoungPatternActivity).withInt("isOpenYoung", isOpenYoung).navigation();
        } else if (src == R.mipmap.icon_me_power_setting) {//特权设置
            ARouter.getInstance().build(ARouterPath.PowerSetting).navigation();
        } else if (src == R.mipmap.icon_me_anchor_center_b) {//主播中心
            if (isAnchor()) {
                ARouter.getInstance().build(ARouterPath.MeAnchorCenter).navigation();
            }
        } else if (src == R.mipmap.icon_me_service_phone) { // 365客服热线
            final String hotLine = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_HOT_LINE, "");
            if (TextUtils.isEmpty(hotLine)) {
                ToastUtil.show("暂未设置，请稍候拨打");
            } else {
                SystemUtils.dial(hotLine);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSignEvent(SignEvent event) {
        ivSingInView.setVisibility(View.GONE);
    }
}
