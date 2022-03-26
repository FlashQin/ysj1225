package com.kalacheng.centercommon.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.busfinance.httpApi.HttpApiSupport;
import com.kalacheng.centercommon.R;
import com.kalacheng.commonview.bean.ShareDialogBean;
import com.kalacheng.commonview.dialog.ShareDialog;
import com.kalacheng.dynamiccircle.adpater.HomePageTrendAdapter;
import com.kalacheng.dynamiccircle.dialog.TrendCommentFragmentDialog;
import com.kalacheng.dynamiccircle.event.DeleteVideoItemEvent;
import com.kalacheng.dynamiccircle.utlis.InputPopwindow;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.libuser.model.InviteDto;
import com.kalacheng.mob.MobConst;
import com.kalacheng.mob.MobShareUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DynamicInfoFragment extends BaseFragment {
    private static String TAG = DynamicInfoFragment.class.getSimpleName();

    private long userId;
    private RecyclerView recyclerViewDynamic;
    private HomePageTrendAdapter adpater;
    private int pageIndex = 0;
    private SmartRefreshLayout refreshHomePage;

    public DynamicInfoFragment() {

    }

    public DynamicInfoFragment(Long userId) {
        this.userId = userId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dynamic_info;
    }

    @Override
    public void onResume() {
        super.onResume();

        getInviteCodeInfo();
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

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);


        refreshHomePage = mParentView.findViewById(R.id.refreshHomePage);
        refreshHomePage.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                getDynamicListData(true);
            }
        });
        refreshHomePage.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getDynamicListData(false);
            }
        });
        recyclerViewDynamic = mParentView.findViewById(R.id.recyclerView_dynamic);
        recyclerViewDynamic.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDynamic.setHasFixedSize(true);
        recyclerViewDynamic.setNestedScrollingEnabled(false);

        adpater = new HomePageTrendAdapter(true);
        recyclerViewDynamic.setAdapter(adpater);
        adpater.setDynamicListItemCallBack(new HomePageTrendAdapter.DynamicListItemCallBack() {
            @Override
            public void onShare(final ApiUserVideo videoBean, int locationY) {
                ShareDialog shareDialog = new ShareDialog();

                /*-------- 分享 其他按钮 S ------------------------------------------------------------*/
                ArrayList<ShareDialogBean> otherBeans = new ArrayList<>();

                ShareDialogBean beanCopy = new ShareDialogBean();
                beanCopy.id = 1002;
                beanCopy.src = com.kalacheng.dynamiccircle.R.mipmap.icon_invitation_url_copy;
                beanCopy.text = "复制链接";
                otherBeans.add(beanCopy);

                //判断 当前 查看动态 是否为自己的动态 （通过uid判断）
                if (videoBean.uid == HttpClient.getUid()) {
                    ShareDialogBean beanPicture = new ShareDialogBean();
                    beanPicture.id = 1001;
                    beanPicture.src = com.kalacheng.dynamiccircle.R.mipmap.icon_share_delete;
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
                shareDialog.show(getChildFragmentManager(), "ShareDialog");
            }

            @Override
            public void onComment(ApiUserVideo bean, int positon) {
                toComment(bean, positon);
            }

            @Override
            public void onReply(ApiUserVideo bean, ApiUsersVideoComments comments, int poistion) {
                toReply(bean, comments, poistion);
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
                        adpater.notifyDataSetChanged();
                    }
                });
                commentfragment.show(getChildFragmentManager(), "TrendCommentFragmentDialog");
            }
        });

        getDynamicListData(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteVideoItemEvent(DeleteVideoItemEvent message) {
        int id = null != message && null != message.apiUserVideo ? message.apiUserVideo.id : 0;
        L.e(TAG, "onDeleteVideoItemEvent  列表");
        L.e(TAG, "onDeleteVideoItemEvent  " + id);

        deleteItem(id);


    }

    /**
     * 删除 对应 item
     *
     * @param id
     */
    private void deleteItem(int id) {
        if (adpater != null && id != 0) {
            int deleteIndex = -1;

            //通过 动态的id获取相对列表中的索引位置
            for (int i = 0; i < adpater.getData().size(); i++) {
                if (adpater.getData().get(i).id == id) {
                    deleteIndex = i;
                }
            }

            if (deleteIndex != -1) {
                adpater.getData().remove(deleteIndex);
                adpater.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    protected void initData() {

    }

    //评论
    public void toComment(ApiUserVideo bean, final int positon) {

        InputPopwindow popwindow = new InputPopwindow(getContext());
        popwindow.showShadow(false, recyclerViewDynamic, 1, bean.id, false, "");
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments) {
                adpater.getData().get(positon).commentList.add(0, comments);
                adpater.notifyDataSetChanged();
            }
        });
    }

    //回复
    public void toReply(ApiUserVideo bean, ApiUsersVideoComments comments, final int poistion) {
        InputPopwindow popwindow = new InputPopwindow(getContext());
        popwindow.showShadow(false, recyclerViewDynamic, 2, comments.commentid, false, comments.userName);
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments) {
                adpater.getData().get(poistion).commentList.add(0, comments);
                adpater.notifyDataSetChanged();
            }
        });
    }

    //喜欢操作
    public void setLike(final int postion) {
        HttpApiAppVideo.videoZan(adpater.getData().get(postion).id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (adpater.getData().get(postion).isLike == 1) {
                        adpater.getData().get(postion).isLike = 0;
                        adpater.getData().get(postion).likes = (adpater.getData().get(postion).likes - 1);
                    } else {
                        adpater.getData().get(postion).isLike = 1;
                        adpater.getData().get(postion).likes = (adpater.getData().get(postion).likes + 1);
                    }
                    adpater.notifyDataSetChanged();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    //获取他人动态列表
    public void getDynamicListData(final boolean isRefresh) {
        HttpApiAppVideo.getVideoList(0, pageIndex, HttpConstants.PAGE_SIZE, userId, 0, new HttpApiCallBackPageArr<ApiUserVideo>() {

            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiUserVideo> retModel) {
                if (code == 1) {
                    if (isRefresh) {
                        if (retModel != null && retModel.size() > 0) {
                            mParentView.findViewById(R.id.tvDynamicNone).setVisibility(View.GONE);
                        } else {
                            mParentView.findViewById(R.id.tvDynamicNone).setVisibility(View.VISIBLE);
                        }
                        adpater.RefreshData(retModel);
                        refreshHomePage.finishRefresh();
                    } else {
                        adpater.loadData(retModel);
                        refreshHomePage.finishLoadMore();
                    }
                } else {
                    if (isRefresh) {
                        refreshHomePage.finishRefresh();
                    } else {
                        refreshHomePage.finishLoadMore();
                    }
                }
            }
        });
    }
}
