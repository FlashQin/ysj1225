package com.kalacheng.dynamiccircle.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.busfinance.httpApi.HttpApiSupport;
import com.kalacheng.commonview.bean.ShareDialogBean;
import com.kalacheng.commonview.dialog.ShareDialog;
import com.kalacheng.commonview.utils.WebUtil;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.adpater.HomePageTrendAdapter;
import com.kalacheng.dynamiccircle.databinding.FragmentTrendBinding;
import com.kalacheng.dynamiccircle.dialog.TrendCommentFragmentDialog;
import com.kalacheng.dynamiccircle.event.DeleteVideoItemEvent;
import com.kalacheng.dynamiccircle.utlis.InputPopwindow;
import com.kalacheng.dynamiccircle.viewModel.TrendViewModel;
import com.kalacheng.base.event.VideoZanEvent;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppLogin;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.libuser.model.AppAds;
import com.kalacheng.libuser.model.InviteDto;
import com.kalacheng.mob.MobConst;
import com.kalacheng.mob.MobShareUtil;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc TrendFragment  动态
 */
public class TrendFragment extends BaseMVVMFragment<FragmentTrendBinding, TrendViewModel> implements OnLoadMoreListener, OnRefreshListener {

    private static String TAG = TrendFragment.class.getSimpleName();

    private Context mContext;
    private ViewGroup mParentView;

    private HomePageTrendAdapter adapter;

    //分页
    private int page;

    public int active = 1;//1刷新huoz 2加载

    public TrendFragment() {
    }

    public TrendFragment(Context context, ViewGroup parentView) {
        super(context, parentView);
        mContext = context;
        mParentView = parentView;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_trend;
    }

    /*-------- 分享 复制链接 S ------------------------------------------------------------*/
    InviteDto inviteDto;

    /**
     * 获取邀请码信息
     */
    private void getInviteCodeInfo() {
        HttpApiSupport.getInviteCodeInfo(new HttpApiCallBack<InviteDto>() {
            @Override
            public void onHttpRet(int code, String msg, InviteDto retModel) {
                if (code == 1 && null != retModel) {
                    inviteDto = retModel;
                }
            }
        });
    }
    /*-------- 分享 复制链接 E ------------------------------------------------------------*/

    /**
     * 分享 其他按钮 参数配置用
     */
    private Bundle bundle = new Bundle();

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        EventBus.getDefault().register(this);


        binding.SmartTrend.setOnLoadMoreListener(this);
        binding.SmartTrend.setOnRefreshListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.RecyTrend.setLayoutManager(manager);
        binding.RecyTrend.setHasFixedSize(true);
        binding.RecyTrend.setNestedScrollingEnabled(false);

        adapter = new HomePageTrendAdapter(false);
        adapter.setLocation(TAG);
        binding.RecyTrend.setAdapter(adapter);

        adapter.setDynamicListItemCallBack(new HomePageTrendAdapter.DynamicListItemCallBack() {
            @Override
            public void onShare(final ApiUserVideo videoBean, final int locationY) {
                ShareDialog shareDialog = new ShareDialog();

                /*-------- 分享 其他按钮 S ------------------------------------------------------------*/
                ArrayList<ShareDialogBean> otherBeans = new ArrayList<>();

                ShareDialogBean beanCopy = new ShareDialogBean();
                beanCopy.id = 1002;
                beanCopy.src = R.mipmap.icon_invitation_url_copy;
                beanCopy.text = "复制链接";
                otherBeans.add(beanCopy);

                //判断 当前 查看动态 是否为自己的动态 （通过uid判断）
                if (videoBean.uid == HttpClient.getUid()) {
                    ShareDialogBean beanPicture = new ShareDialogBean();
                    beanPicture.id = 1001;
                    beanPicture.src = R.mipmap.icon_share_delete;
                    beanPicture.text = "删除";
                    otherBeans.add(beanPicture);
                }

                bundle.putParcelableArrayList(ShareDialog.ShareDialogOtherBeans, otherBeans);
                shareDialog.setArguments(bundle);
                /*-------- 分享 其他按钮 E ------------------------------------------------------------*/

                shareDialog.setShareDialogListener(new ShareDialog.ShareDialogListener() {
                    @Override
                    public void onItemClick(long id) {
                        if (id == 1) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.WX);
                        } else if (id == 2) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.WX_PYQ);
                        } else if (id == 3) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.QQ);
                        } else if (id == 4) {
                            MobShareUtil.getInstance().shareApp(MobConst.Type.QZONE);
                        } else if (id == 1002) {//复制链接
                            if (inviteDto == null) {
                                return;
                            }

                            WordUtil.copyLink(inviteDto.inviteUrl);
                        } else if (id == 1001) { //删除
                            HttpApiAppVideo.videoDel(videoBean.id, new HttpApiCallBack<HttpNone>() {
                                @Override
                                public void onHttpRet(int code, String msg, HttpNone retModel) {
                                    if (!TextUtils.isEmpty(msg)) {
                                        ToastUtil.show(msg);
                                    }

                                    if (code == 1) {
                                        deleteItem(videoBean.id);
                                    }
                                }

                            });
                        }
                    }
                });
                shareDialog.show(getActivity().getSupportFragmentManager(), "ShareDialog");
            }

            @Override
            public void onComment(ApiUserVideo bean, int positon) {
                toComment(bean, positon);
            }


            @Override
            public void onReply(ApiUserVideo bean, ApiUsersVideoComments comments, int position) {
                toReply(bean, comments, position);
            }

            @Override
            public void onLike(int poistion) {
                setLike(poistion);
            }

            @Override
            public void onLookMoreComment(final ApiUserVideo bean) {
                TrendCommentFragmentDialog commentfragment = new TrendCommentFragmentDialog();
                Bundle Trendbundle = new Bundle();
                Trendbundle.putParcelable(ARouterValueNameConfig.ACTIVITYBEAN, bean);
                commentfragment.setArguments(Trendbundle);
                commentfragment.setOnCommentNumChangeListener(new TrendCommentFragmentDialog.OnCommentNumChangeListener() {
                    @Override
                    public void onChange(int commentNum) {
                        bean.comments = commentNum;
                        adapter.notifyDataSetChanged();
                    }
                });
                commentfragment.show(getActivity().getSupportFragmentManager(), "TrendCommentFragmentDialog");
            }
        });

        getAdsList();
        getDynamicListData();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteVideoItemEvent(DeleteVideoItemEvent message) {
        int id = null != message && null != message.apiUserVideo ? message.apiUserVideo.id : 0;
        L.e(TAG, "onDeleteVideoItemEvent  列表");
        L.e(TAG, "onDeleteVideoItemEvent  " + id);
        deleteItem(id);
    }

    //修改 评论点赞
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setZanCommentEvent(VideoZanEvent event) {
        if (null != event && event.getLocation().equals(TAG)) {
            adapter.setZanComment(event);
        }
    }

    /**
     * 删除 对应 item
     *
     * @param id
     */
    private void deleteItem(int id) {
        if (adapter != null && id != 0) {
            int deleteIndex = -1;

            //通过 动态的id获取相对列表中的索引位置
            for (int i = 0; i < adapter.getData().size(); i++) {
                if (adapter.getData().get(i).id == id) {
                    deleteIndex = i;
                }
            }

            if (deleteIndex != -1) {
                adapter.getData().remove(deleteIndex);
                adapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        getInviteCodeInfo();
    }

    /**
     * 获取广告轮播图
     */
    private void getAdsList() {
        HttpApiAppLogin.adslist(21, 21, new HttpApiCallBackArr<AppAds>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppAds> retModel) {
                if (code == 1 && null != retModel && retModel.size() > 0) {
                    initBanner(retModel);
                    binding.layoutBanner.setVisibility(View.VISIBLE);
                } else {
                    binding.layoutBanner.setVisibility(View.GONE);
                }
            }
        });
    }

    //获取动态列表
    public void getDynamicListData() {

        HttpApiAppVideo.getVideoList(0, page, HttpConstants.PAGE_SIZE, 0, 0, new HttpApiCallBackPageArr<ApiUserVideo>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiUserVideo> retModel) {
                if (code == 1) {
                    viewModel.bean.set(retModel);
                    if (active == 1) {
                        adapter.RefreshData(retModel);
                        binding.SmartTrend.finishRefresh();
                    } else {
                        adapter.loadData(retModel);
                        binding.SmartTrend.finishLoadMore();
                    }

                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    //喜欢操作
    public void setLike(final int postion) {
        HttpApiAppVideo.videoZan(adapter.getData().get(postion).id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (adapter.getData().get(postion).isLike == 1) {
                        adapter.getData().get(postion).isLike = 0;
                        adapter.getData().get(postion).likes = (adapter.getData().get(postion).likes - 1);
                    } else {
                        adapter.getData().get(postion).isLike = 1;
                        adapter.getData().get(postion).likes = (adapter.getData().get(postion).likes + 1);

                    }

                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        active = 2;
        getDynamicListData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        active = 1;
        getAdsList();
        getDynamicListData();
    }

    private void initBanner(final List<AppAds> mSlideList) {
        List<String> data_banner_string = new ArrayList<>();
        for (AppAds bean : mSlideList) {
            data_banner_string.add(bean.thumb);
        }
        binding.convenientBanner.setPages(new CBViewHolderCreator<HomePageTrendAdapter.NetworkImageHolderView>() {
            @Override
            public HomePageTrendAdapter.NetworkImageHolderView createHolder() {
                return new HomePageTrendAdapter.NetworkImageHolderView();
            }
        }, data_banner_string);

        binding.convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                WebUtil.goWeb(getActivity(), mSlideList.get(position).url);
            }
        });
        binding.convenientBanner.setPageIndicator(new int[]{com.kalacheng.dynamiccircle.R.drawable.banner_indicator_grey, com.kalacheng.dynamiccircle.R.drawable.banner_indicator_white});
        binding.convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        binding.convenientBanner.startTurning(3000);
        binding.convenientBanner.setManualPageable(true);
        if (binding.convenientBanner.getViewPager() != null) {
            binding.convenientBanner.getViewPager().setClipToPadding(false);
            binding.convenientBanner.getViewPager().setClipChildren(false);
            try {
                ((RelativeLayout) binding.convenientBanner.getViewPager().getParent()).setClipChildren(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            binding.convenientBanner.getViewPager().setOffscreenPageLimit(3);
        }
    }

    //评论
    public void toComment(ApiUserVideo bean, final int postion) {
        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, binding.RecyTrend, 1, bean.id, false, "");
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments) {
                adapter.getData().get(postion).commentList.add(0, comments);
                adapter.notifyDataSetChanged();
//                adpater.RefreshComent();
            }
        });
    }

    //回复
    public void toReply(ApiUserVideo bean, ApiUsersVideoComments comments, final int postion) {
        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, binding.RecyTrend, 2, comments.commentid, false, comments.userName);
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments) {
                adapter.getData().get(postion).commentList.add(0, comments);
                adapter.notifyDataSetChanged();
//                adpater.RefreshComent();
            }
        });
    }
}
