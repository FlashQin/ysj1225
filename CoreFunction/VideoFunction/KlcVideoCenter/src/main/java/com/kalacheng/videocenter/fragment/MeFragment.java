package com.kalacheng.videocenter.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.event.MeFragmentRefreshEvent;
import com.kalacheng.base.event.SignEvent;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.centercommon.dialog.ReadMeRequireDialog;
import com.kalacheng.commonview.activity.BaseMainActivity;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiMyShortVideo;
import com.kalacheng.libuser.model.ApiUserIndexResp;
import com.kalacheng.shortvideo.fragment.VideoListFragment;
import com.kalacheng.util.adapter.SimpleImgTextAdapter;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.aop.SingleClick;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.kalacheng.videocenter.R;
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
    Context mContext;
    private SmartRefreshLayout refreshMe;
    ImageView ivAvatar;
    private TextView tvNickName, tvFollow, tvZan, tvFans, tvSign, tvIdName, tvId, tvWorks, tvLikes, tvPayment;
    private LinearLayout layoutSex;
    private ImageView ivGrade;
    private ImageView ivWealthGrade;
    private ImageView ivNobleGrade;
    private TextView tvRead;
    private TextView tvEyeRed;
    //操作栏1
    private RecyclerView recyclerViewTabMe1;

    /**
     * 谁看过我是否需要贵族开关0需要1不需要
     */
    private int isVipSee;
    /**
     * 是否为VIP
     */
    private boolean isVip;
    View lineWorks, lineLikes, linePay;

    private ViewPager viewPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private ImageView ivSignIn;//签到
    private View ivSingInView;

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
        ivAvatar = mParentView.findViewById(R.id.iv_avatar);
        tvIdName = mParentView.findViewById(R.id.tvIdName);
        tvId = mParentView.findViewById(R.id.tvId);
        tvNickName = mParentView.findViewById(R.id.tv_nickname);
        tvZan = mParentView.findViewById(R.id.tv_zan_num);
        tvFollow = mParentView.findViewById(R.id.tv_follow_num);
        tvFans = mParentView.findViewById(R.id.tv_fans_num);
        tvRead = mParentView.findViewById(R.id.tv_read_num);
        tvSign = mParentView.findViewById(R.id.tv_sign);
        layoutSex = mParentView.findViewById(R.id.layoutSex);
        tvEyeRed = mParentView.findViewById(R.id.tvEyeRed);
        tvWorks = mParentView.findViewById(R.id.tv_works);
        tvLikes = mParentView.findViewById(R.id.tv_likes);
        tvPayment = mParentView.findViewById(R.id.tv_payment);
        tvWorks.setSelected(true);
        refreshMe = mParentView.findViewById(R.id.refreshMe);
        lineWorks = mParentView.findViewById(R.id.line_works);
        lineLikes = mParentView.findViewById(R.id.line_likes);
        linePay = mParentView.findViewById(R.id.line_payment);
        ivGrade = mParentView.findViewById(R.id.iv_grade);
        ivWealthGrade = mParentView.findViewById(R.id.ivWealthGrade);
        ivNobleGrade = mParentView.findViewById(R.id.ivNobleGrade);

        mParentView.findViewById(R.id.layoutMeTitle).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_follow).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_fans).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_views).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_browse).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_works).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_likes).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_payment).setOnClickListener(this);

        refreshMe.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                EventBus.getDefault().post(new MeFragmentRefreshEvent());
                getMyHeadInfo();
                isVisit();
                getUserVideo();
                for (Fragment fragment : mFragments) {
                    ((BaseFragment) fragment).refreshData();
                }
            }
        });
        recyclerViewTabMe1 = mParentView.findViewById(R.id.recyclerViewTabMe1);
        recyclerViewTabMe1.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerViewTabMe1.setHasFixedSize(true);
        recyclerViewTabMe1.setNestedScrollingEnabled(false);
        List<SimpleImageUrlTextBean> tabBeans1 = new ArrayList<>();
        tabBeans1.add(new SimpleImageUrlTextBean(R.mipmap.icon_me_account, "充值中心"));
        tabBeans1.add(new SimpleImageUrlTextBean(R.mipmap.icon_me_noble, "贵族中心"));
        tabBeans1.add(new SimpleImageUrlTextBean(R.mipmap.icon_me_mission_center, "任务中心"));
        tabBeans1.add(new SimpleImageUrlTextBean(R.mipmap.icon_me_code, "邀请赚钱"));
        tabBeans1.add(new SimpleImageUrlTextBean(R.mipmap.icon_profit, "收益中心"));
        tabBeans1.add(new SimpleImageUrlTextBean(R.mipmap.icon_anchor_center, "认证中心"));
        tabBeans1.add(new SimpleImageUrlTextBean(R.mipmap.icon_me_medal, "设置"));
        SimpleImgTextAdapter tabAdapter1 = new SimpleImgTextAdapter(tabBeans1);
        tabAdapter1.setImgWidthHeight(40, 40);
        tabAdapter1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        tabAdapter1.setPadding(0, 0, 0, 6);
        tabAdapter1.setTextColor("#666666");
        tabAdapter1.setTextSize(12);
        recyclerViewTabMe1.setAdapter(tabAdapter1);
        recyclerViewTabMe1.addItemDecoration(new ItemDecoration(getActivity(), 0x00000000, 0, 10));
        tabAdapter1.setOnItemClickCallback(new OnBeanCallback<SimpleImageUrlTextBean>() {
            @Override
            public void onClick(SimpleImageUrlTextBean bean) {
                if (bean.src == R.mipmap.icon_me_account) {//我的账户
                    ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
                } else if (bean.src == R.mipmap.icon_me_privilege) {//我的特权
                    ARouter.getInstance().build(ARouterPath.MyPrivilegeActivity).navigation();
                } else if (bean.src == R.mipmap.icon_me_noble) {//贵族中心
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                            + HttpConstants.URL_NOBLE + "_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
                } else if (bean.src == R.mipmap.icon_profit) {//收益中心
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                            + "/static/h5page/index.html#/userRevenue?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
                } else if (bean.src == R.mipmap.icon_me_mission_center) {//任务中心
                    ARouter.getInstance().build(ARouterPath.TaskCenterActivity).navigation();

                } else if (bean.src == R.mipmap.icon_me_medal) {//设置
                    ARouter.getInstance().build(ARouterPath.SettingApp).navigation();
                } else if (bean.src == R.mipmap.icon_anchor_center) {//主播中心
                    ARouter.getInstance().build(ARouterPath.AnchorCenterActivity).navigation();

                } else if (bean.src == R.mipmap.icon_me_code) {//邀请码
                    ARouter.getInstance().build(ARouterPath.InvitationCodeActivity).navigation();
                }
            }
        });

        viewPager = mParentView.findViewById(R.id.viewPager);
        mFragments.add(new VideoListFragment(1, -1, false));
        mFragments.add(new VideoListFragment(2, -1, false));
        mFragments.add(new VideoListFragment(3, -1, false));
        mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                changeVideoTitle(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

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
        getUserVideo();
    }

    public void getUserVideo() {
        HttpApiAppShortVideo.getUserShortVideoList(20, HttpClient.getUid(), new HttpApiCallBack<ApiMyShortVideo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiMyShortVideo retModel) {
                if (code == 1 && retModel != null) {
                    tvWorks.setText("作品 " + retModel.myNumber);
                    tvLikes.setText("喜欢 " + retModel.likeNumber);
                    tvPayment.setText("购买 " + retModel.buyNumber);
                    tvZan.setText((retModel.myNumber + retModel.likeNumber + retModel.buyNumber) + "");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getMyHeadInfo();
        isVisit();
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.layoutMeTitle) {
            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, HttpClient.getUid()).navigation();
        } else if (view.getId() == R.id.ll_follow) {
            ARouter.getInstance().build(ARouterPath.Follow).withLong(ARouterValueNameConfig.USERID, HttpClient.getUid()).navigation();
        } else if (view.getId() == R.id.ll_fans) {
            ARouter.getInstance().build(ARouterPath.Fans).withLong(ARouterValueNameConfig.USERID, HttpClient.getUid()).withInt(ARouterValueNameConfig.TYPE, 0).navigation();
        } else if (view.getId() == R.id.ll_views) {//我的视图
            ARouter.getInstance().build(ARouterPath.MyViewActivity).navigation();
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
        } else if (view.getId() == R.id.ll_works) {
            if (!tvWorks.isSelected()) {
                changeVideoTitle(0);
                viewPager.setCurrentItem(0);
            }
        } else if (view.getId() == R.id.ll_likes) {
            if (!tvLikes.isSelected()) {
                changeVideoTitle(1);
                viewPager.setCurrentItem(1);
            }
        } else if (view.getId() == R.id.ll_payment) {
            if (!tvPayment.isSelected()) {
                changeVideoTitle(2);
                viewPager.setCurrentItem(2);
            }
        } else if (view.getId() == R.id.ivSignIn) {//签到
            BaseMainActivity baseMainActivity = (BaseMainActivity) getActivity();
            if (baseMainActivity != null) {
                baseMainActivity.showSignInDialog();
            }
        }
    }

    /**
     * 改变作品状况样式
     */
    private void changeVideoTitle(int position) {
        if (position == 0) {
            tvWorks.setSelected(true);
            tvLikes.setSelected(false);
            tvPayment.setSelected(false);
            lineWorks.setVisibility(View.VISIBLE);
            lineLikes.setVisibility(View.INVISIBLE);
            linePay.setVisibility(View.INVISIBLE);
        } else if (position == 1) {
            tvWorks.setSelected(false);
            tvLikes.setSelected(true);
            tvPayment.setSelected(false);
            lineWorks.setVisibility(View.INVISIBLE);
            lineLikes.setVisibility(View.VISIBLE);
            linePay.setVisibility(View.INVISIBLE);
        } else if (position == 2) {
            tvWorks.setSelected(false);
            tvLikes.setSelected(false);
            tvPayment.setSelected(true);
            lineWorks.setVisibility(View.INVISIBLE);
            lineLikes.setVisibility(View.INVISIBLE);
            linePay.setVisibility(View.VISIBLE);
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
                    tvRead.setText(retModel.readMeUsersNumber + "");
                    String signStr = retModel.signature.trim();
                    SexUtlis.getInstance().setSex(mContext, layoutSex, retModel.sex, retModel.age);
                    if (retModel.role == 1) {
                        ImageLoader.display(retModel.anchorGradeImg, ivGrade);
                    } else {
                        ImageLoader.display(retModel.userGradeImg, ivGrade);
                    }
                    ImageLoader.display(retModel.wealthGradeImg, ivWealthGrade);
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
                        ImageLoader.display(retModel.avatar, ivAvatar);
                    } else {
                        ivAvatar.setImageResource(R.mipmap.ic_launcher);
                    }

                    ((TextView) mParentView.findViewById(R.id.tv_userlevel)).setText(retModel.userGrade + "");
                    ((TextView) mParentView.findViewById(R.id.tv_wealthlevel)).setText(retModel.wealthGrade + "");
                    isVipSee = retModel.isVipSee;
                }

                refreshMe.finishRefresh();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSignEvent(SignEvent event) {
        ivSingInView.setVisibility(View.GONE);
    }
}
