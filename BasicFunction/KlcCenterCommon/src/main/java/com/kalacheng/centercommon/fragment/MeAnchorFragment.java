package com.kalacheng.centercommon.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.model.TaskDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.model.AppMerchantAgreementDTO;
import com.kalacheng.centercommon.R;
import com.kalacheng.commonview.adapter.MeAnchorAdpater;
import com.kalacheng.base.event.MeFragmentRefreshEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.ApiGradeReWarRe;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 主播任务
 */
public class MeAnchorFragment extends BaseFragment implements View.OnClickListener {
    private SmartRefreshLayout refreshAnchor;
    ImageView ivUncertified;
    private LinearLayout layoutUnCertified;
    private TextView tvUnCertifiedInfo;
    RecyclerView recyclerViewTask;

    private TextView AnchorTask_Sign_Grade;
    private TextView AnchorTask_Sign_EmpiricalValue;
    private TextView AnchorTask_Sign_NextEmpiricalValue;

    private TextView btn_authentication;

    private RelativeLayout AnchorTask_Sign_EmpiricalValuePro_re;
    private ImageView AnchorTask_Sign_EmpiricalValuePro;

    public MeAnchorFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meanchor;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        refreshAnchor = mParentView.findViewById(R.id.refreshAnchor);
        refreshAnchor.setPrimaryColors(Color.parseColor("#00000000"));

        AnchorTask_Sign_Grade = mParentView.findViewById(R.id.AnchorTask_Sign_Grade);
        AnchorTask_Sign_EmpiricalValue = mParentView.findViewById(R.id.AnchorTask_Sign_EmpiricalValue);
        AnchorTask_Sign_NextEmpiricalValue = mParentView.findViewById(R.id.AnchorTask_Sign_NextEmpiricalValue);
        AnchorTask_Sign_EmpiricalValuePro_re = mParentView.findViewById(R.id.AnchorTask_Sign_EmpiricalValuePro_re);
        AnchorTask_Sign_EmpiricalValuePro = mParentView.findViewById(R.id.AnchorTask_Sign_EmpiricalValuePro);

        refreshAnchor.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                checkIsAuth();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        checkIsAuth();
        getAnchorInformation();
    }

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

    //查询用户信息
    public void getAnchorInformation() {
        HttpApiAppUser.userLevelInfo(2, new HttpApiCallBack<ApiGradeReWarRe>() {
            @Override
            public void onHttpRet(int code, String msg, ApiGradeReWarRe retModel) {
                if (code == 1) {
                    getAnchorInformationUI(retModel);
                }
            }
        });
    }

    public void getAnchorInformationUI(ApiGradeReWarRe apiGradeReWarRe) {
        AnchorTask_Sign_Grade.setText("主播等级（LV" + apiGradeReWarRe.currLevel + "）");
        AnchorTask_Sign_EmpiricalValue.setText("当前经验值：" + (apiGradeReWarRe.nextLevelTotalEmpirical - apiGradeReWarRe.nextLevelEmpirical));
        AnchorTask_Sign_NextEmpiricalValue.setText("距离升级：" + apiGradeReWarRe.nextLevelEmpirical);

//        getUserGradePro(apiGradeReWarRe.nextLevelTotalEmpirical, (apiGradeReWarRe.nextLevelTotalEmpirical - apiGradeReWarRe.nextLevelEmpirical));
    }

    /**
     * 查询认证状态
     */
    private void checkIsAuth() {
        HttpApiAppUser.getMyAnchor(new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    if (retModel.role == 1) {
                        if (ConfigUtil.getBoolValue(R.bool.isVideo)) {
                            mParentView.findViewById(R.id.view_identify_video).setVisibility(View.VISIBLE);
                        } else {
                            mParentView.findViewById(R.id.view_identify).setVisibility(View.VISIBLE);
                        }
                        mParentView.findViewById(R.id.view_uncertified).setVisibility(View.GONE);
                        initAnchorView();
                    } else if (retModel.anchorAuditStatus == -1) {
                        initUserView();
                        if (ConfigUtil.getBoolValue(R.bool.isVideo)) {
                            tvUnCertifiedInfo.setText("你还未认证哦\n现在去认证");
                        } else {
                            tvUnCertifiedInfo.setText("你还不是主播哦\n现在去认证");
                        }
                        btn_authentication.setVisibility(View.VISIBLE);
                        btn_authentication.setText("立即认证");
                    } else if (retModel.anchorAuditStatus == 1) {
                        initUserView();
                        tvUnCertifiedInfo.setText("你的身份认证还在审核中\n请耐心等待");
                        btn_authentication.setVisibility(View.GONE);
                    } else if (retModel.anchorAuditStatus == 2) {
                        initUserView();
                        tvUnCertifiedInfo.setText("你的身份认证失败了哦\n请重新认证");
                        btn_authentication.setVisibility(View.VISIBLE);
                        btn_authentication.setText("重新认证");
                    } else {//其它，按未认证处理
                        initUserView();
                        if (ConfigUtil.getBoolValue(R.bool.isVideo)) {
                            tvUnCertifiedInfo.setText("你还未认证哦\n现在去认证");
                        } else {
                            tvUnCertifiedInfo.setText("你还不是主播哦\n现在去认证");
                        }
                        btn_authentication.setVisibility(View.VISIBLE);
                        btn_authentication.setText("立即认证");
                    }
                }
                refreshAnchor.finishRefresh();
            }
        });
    }

    private void initAnchorView() {
        recyclerViewTask = mParentView.findViewById(R.id.recyclerView_task);
        recyclerViewTask.setLayoutManager(new LinearLayoutManager(getActivity()));


        HttpApiAppUser.anchorTaskList(new HttpApiCallBackArr<TaskDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<TaskDto> retModel) {
                if (code == 1 && retModel != null && !retModel.isEmpty()) {
                    MeAnchorAdpater mAdpater = new MeAnchorAdpater(retModel);
                    recyclerViewTask.setAdapter(mAdpater);
                }
            }
        });

    }

    private void initUserView() {
        mParentView.findViewById(R.id.view_uncertified).setVisibility(View.VISIBLE);
        mParentView.findViewById(R.id.view_identify).setVisibility(View.GONE);
        mParentView.findViewById(R.id.view_identify_video).setVisibility(View.GONE);

        ivUncertified = mParentView.findViewById(R.id.iv_uncertified);
        layoutUnCertified = mParentView.findViewById(R.id.layoutUnCertified);
        tvUnCertifiedInfo = mParentView.findViewById(R.id.tvUnCertifiedInfo);
        btn_authentication = mParentView.findViewById(R.id.btn_authentication);
        btn_authentication.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.btn_authentication) {
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
        }
    }

    //设置等级升级进度条
    public void getUserGradePro(int total, int nextGrade) {
        int Frame_height = AnchorTask_Sign_EmpiricalValuePro_re.getWidth();

        int poistion = (int) (((double) nextGrade / (double) total) * Frame_height);

        RelativeLayout.LayoutParams params0 = (RelativeLayout.LayoutParams) AnchorTask_Sign_EmpiricalValuePro.getLayoutParams();
        params0.width = poistion;
        AnchorTask_Sign_EmpiricalValuePro.setLayoutParams(params0);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenInvalidEvent(MeFragmentRefreshEvent event) {
        checkIsAuth();
    }
}
