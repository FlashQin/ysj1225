package com.kalacheng.dynamiccircle.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.adpater.HomePageTrendAdapter;
import com.kalacheng.dynamiccircle.databinding.DynamicBinding;
import com.kalacheng.dynamiccircle.dialog.TrendCommentFragmentDialog;
import com.kalacheng.dynamiccircle.utlis.InputPopwindow;
import com.kalacheng.dynamiccircle.viewModel.DynamicViewModel;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.ToastUtil;

import java.util.List;

/*
 * 我的动态列表
 * */
@Route(path = "/center/activity/DynamicActivity")
public class DynamicActivity extends BaseMVVMActivity<DynamicBinding, DynamicViewModel> {

    HomePageTrendAdapter adpater;
    PermissionsUtil mProcessResultUtil;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.dynamic;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.RecyMadynamic.setLayoutManager(manager);
        adpater = new HomePageTrendAdapter(false);
        binding.RecyMadynamic.setAdapter(adpater);
        adpater.setDynamicListItemCallBack(new HomePageTrendAdapter.DynamicListItemCallBack() {
            @Override
            public void onShare(ApiUserVideo videoBean, int locationY) {


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

            }

            @Override
            public void onLookMoreComment(ApiUserVideo bean) {
                TrendCommentFragmentDialog commentfragment = new TrendCommentFragmentDialog();
                Bundle Trendbundle = new Bundle();
                Trendbundle.putParcelable(ARouterValueNameConfig.ACTIVITYBEAN, bean);
                commentfragment.setArguments(Trendbundle);
                commentfragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "TrendCommentFragmentDialog");
            }
        });
        getDynamicListData();

    }

    //获取自己动态列表
    public void getDynamicListData() {

        HttpApiAppVideo.getVideoList(0, 0, HttpConstants.PAGE_SIZE, 10, (int) HttpClient.getUid(), new HttpApiCallBackPageArr<ApiUserVideo>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiUserVideo> retModel) {
                if (code == 1) {
                    viewModel.mlist.set(retModel);
                    adpater.RefreshData(viewModel.mlist.get());

                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    //评论
    public void toComment(ApiUserVideo bean, final int positon) {

        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, binding.RecyMadynamic, 1, bean.id, false, "");
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments) {
                viewModel.mlist.get().get(positon).commentList.add(0, comments);
                adpater.notifyDataSetChanged();
//                adpater.RefreshComent();
            }
        });
    }

    //回复
    public void toReply(ApiUserVideo bean, ApiUsersVideoComments comments, final int poistion) {
        InputPopwindow popwindow = new InputPopwindow(mContext);
        popwindow.showShadow(false, binding.RecyMadynamic, 2, comments.commentid, false, comments.userName);
        popwindow.setInputSuccessCallBack(new InputPopwindow.InputSuccessCallBack() {
            @Override
            public void Success(ApiUsersVideoComments comments) {
                viewModel.mlist.get().get(poistion).commentList.add(0, comments);
                adpater.notifyDataSetChanged();
//                adpater.RefreshComent();
            }
        });
    }


}
