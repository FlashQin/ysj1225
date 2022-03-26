package com.kalacheng.main.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.dynamiccircle.fragment.CommunityListFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.AppAds;
import com.kalacheng.libuser.model.AppVideoTopic;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.TopicAdapter;
import com.kalacheng.main.dialog.CommunityPublishDialogFragment;
import com.kalacheng.commonview.activity.PictureChooseActivity;
import com.kalacheng.commonview.activity.VideoChooseActivity;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.commonview.utils.WebUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.commonview.activity.DynamicMakeActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * 社区
 */
public class CommunityFragment extends BaseFragment {

    Context mContext;
    private SmartRefreshLayout refreshCommunity;
    private ViewPager viewPager;
    private List<Fragment> mFragments;
    private LinearLayout layoutBanner;
    private ConvenientBanner convenientBanner;

    LinearLayout topicLl;
    RecyclerView recyclerView;
    TopicAdapter adapter;

    TextView tvFollow;
    TextView tvRecommend;
    TextView tvAll;

    private PermissionsUtil mProcessResultUtil;

    public CommunityFragment(Context mContext, ViewGroup mParentView) {
        this.mContext = mContext;
        this.mParentView = mParentView;
    }

    public CommunityFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_community;
    }

    @Override
    protected void initView() {
        refreshCommunity = mParentView.findViewById(R.id.refreshCommunity);
        viewPager = mParentView.findViewById(R.id.viewPager);
        recyclerView = mParentView.findViewById(R.id.recyclerView);
        topicLl = mParentView.findViewById(R.id.topicLl);
        tvFollow = mParentView.findViewById(R.id.tvFollow);
        tvRecommend = mParentView.findViewById(R.id.tvRecommend);
        tvAll = mParentView.findViewById(R.id.tvAll);
        tvRecommend.setSelected(true);

        adapter = new TopicAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setAdapter(adapter);
        mProcessResultUtil = new PermissionsUtil(Objects.requireNonNull(getActivity()));

        mFragments = new ArrayList<>();
        mFragments.add(new CommunityListFragment(2, 0, false));
        mFragments.add(new CommunityListFragment(1, 0, false));
        mFragments.add(new CommunityListFragment(0, 0, false));
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), mFragments));
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tvFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvFollow.isSelected()) {
                    viewPager.setCurrentItem(0);
                }
            }
        });
        tvRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvRecommend.isSelected()) {
                    viewPager.setCurrentItem(1);
                }
            }
        });
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvAll.isSelected()) {
                    viewPager.setCurrentItem(2);
                }
            }
        });

        layoutBanner = mParentView.findViewById(R.id.layoutBanner);
        convenientBanner = mParentView.findViewById(R.id.convenientBanner);

        mParentView.findViewById(R.id.fabuIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                mProcessResultUtil.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                }, new Runnable() {
                    @Override
                    public void run() {
                        HttpApiAPPAnchor.is_auth(2, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    if ("0".equals(retModel.no_use)) {
                                        CommunityPublishDialogFragment communityPublishDialogFragment = new CommunityPublishDialogFragment();
                                        communityPublishDialogFragment.setChooseCallback(new CommunityPublishDialogFragment.ChooseCallback() {
                                            @Override
                                            public void onVideoClick() {
                                                if (ConfigUtil.getBoolValue(R.bool.isPhotoUpload)) {
                                                    DynamicMakeActivity.forward(getActivity(), -1, 0, false, 1001);
                                                } else {
                                                    Intent intent = new Intent(mContext, VideoChooseActivity.class);
                                                    intent.putExtra(ARouterValueNameConfig.VIDEO_DURATION, 0);
                                                    startActivityForResult(intent, 1004);
                                                }
                                            }

                                            @Override
                                            public void onPicClick() {
                                                if (ConfigUtil.getBoolValue(R.bool.isPhotoUpload)) {
                                                    DynamicMakeActivity.forward(getActivity(), 0, 0, false, 1001);
                                                } else {
                                                    Intent intent = new Intent(mContext, PictureChooseActivity.class);
                                                    intent.putExtra(PictureChooseActivity.PICTURE_CHOOSE_NUM, 9);
                                                    startActivityForResult(intent, 1003);
                                                }
                                            }
                                        });
                                        communityPublishDialogFragment.show(getChildFragmentManager(), "CommunityPublishDialogFragment");
                                    } else if ("1".equals(retModel.no_use)) {
                                        if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.AUTH_IS_SEX, 1) == 0) {
                                            ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                                            if (apiUserInfo != null && apiUserInfo.sex == 2) {
                                                ARouter.getInstance().build(ARouterPath.ApplyAnchorActivity).navigation();
                                            } else {
                                                DialogUtil.showKnowDialog(getContext(), "暂时只支持小姐姐认证哦~", null);
                                            }
                                        } else {
                                            ARouter.getInstance().build(ARouterPath.ApplyAnchorActivity).navigation();
                                        }
                                    } else {
                                        if (!TextUtils.isEmpty(msg)) {
                                            ToastUtil.show(msg);
                                        }
                                    }
                                } else {
                                    if (!TextUtils.isEmpty(msg)) {
                                        ToastUtil.show(msg);
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });

        refreshCommunity.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getAdsList();
                getTopicList();
                for (Fragment fragment : mFragments) {
                    ((BaseFragment) fragment).refreshData();
                }
            }
        });
    }

    /**
     * 更新头部状态
     */
    private void updateTitle(int position) {
        if (position == 0) {
            tvFollow.setSelected(true);
            tvRecommend.setSelected(false);
            tvAll.setSelected(false);
        } else if (position == 1) {
            tvFollow.setSelected(false);
            tvRecommend.setSelected(true);
            tvAll.setSelected(false);
        } else {
            tvFollow.setSelected(false);
            tvRecommend.setSelected(false);
            tvAll.setSelected(true);
        }
    }

    @Override
    protected void initData() {
        getAdsList();
        getTopicList();
    }

    /**
     * 获取广告轮播图
     */
    private void getAdsList() {
        HttpApiAppLogin.adslist(21, 21, new HttpApiCallBackArr<AppAds>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppAds> retModel) {
                if (code == 1 && null != retModel && retModel.size() > 0) {
                    layoutBanner.setVisibility(View.VISIBLE);
                    initBanner(retModel);
                } else {
                    layoutBanner.setVisibility(View.GONE);
                }

                refreshCommunity.finishRefresh();
            }
        });
    }

    /**
     * 获取话题列表
     */
    private void getTopicList() {
        HttpApiAppVideo.getTopicList(0, 6, new HttpApiCallBackArr<AppVideoTopic>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppVideoTopic> retModel) {
                if (code == 1 && retModel != null && retModel.size() > 0) {
                    adapter.setList(retModel);
                    topicLl.setVisibility(View.VISIBLE);
                } else {
                    topicLl.setVisibility(View.GONE);
                }
            }
        });
    }


    private void initBanner(final List<AppAds> mSlideList) {
        List<String> data_banner_string = new ArrayList<>();
        for (AppAds bean : mSlideList) {
            data_banner_string.add(bean.thumb);
        }
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, data_banner_string);

        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                WebUtil.goWeb(getActivity(), mSlideList.get(position).url);
            }
        });
        convenientBanner.setPageIndicator(new int[]{com.kalacheng.dynamiccircle.R.drawable.banner_indicator_grey, com.kalacheng.dynamiccircle.R.drawable.banner_indicator_white});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        convenientBanner.startTurning(3000);
        convenientBanner.setManualPageable(true);
        if (convenientBanner.getViewPager() != null) {
            convenientBanner.getViewPager().setClipToPadding(false);
            convenientBanner.getViewPager().setClipChildren(false);
            try {
                ((RelativeLayout) convenientBanner.getViewPager().getParent()).setClipChildren(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            convenientBanner.getViewPager().setOffscreenPageLimit(3);
        }
    }

    // banner加载图片适配
    public static class NetworkImageHolderView implements Holder<String> {
        private RoundedImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(com.kalacheng.main.R.layout.home_banner_item, null);
            imageView = view.findViewById(R.id.iv_banner_img);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            params.setMarginStart(DpUtil.dp2px(10));
            params.setMarginEnd(DpUtil.dp2px(10));
            imageView.setCornerRadius(DpUtil.dp2px(7));
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            // 图片
            ImageLoader.display(data, imageView);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1003) {
                ARouter.getInstance().build(ARouterPath.VideoPublish).withObject(ARouterValueNameConfig.PICTURE_LIST, data.getParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST)).navigation();
            } else if (requestCode == 1004) {
                ARouter.getInstance().build(ARouterPath.VideoPublish).withString(ARouterValueNameConfig.VIDEO_PATH, data.getStringExtra(ARouterValueNameConfig.VIDEO_PATH))
                        .withInt(ARouterValueNameConfig.VideoType, data.getIntExtra(ARouterValueNameConfig.VideoType, 1))
                        .withLong(ARouterValueNameConfig.VIDEO_TIME_LONG, data.getLongExtra(ARouterValueNameConfig.VIDEO_DURATION, 0)).navigation();
            }
        }
    }
}
