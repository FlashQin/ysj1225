package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busfinance.httpApi.HttpApiSupport;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.QRCodeAdpater;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;

import com.kalacheng.libuser.model.InviteDto;
import com.kalacheng.mob.MobConst;
import com.kalacheng.mob.MobShareUtil;
import com.kalacheng.commonview.bean.ShareDialogBean;
import com.kalacheng.commonview.dialog.ShareDialog;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.base.http.HttpApiCallBack;

import java.util.ArrayList;

/**
 * 邀请赚钱
 */
@Route(path = ARouterPath.InvitationCodeActivity)
public class InvitationCodeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvOther;
    private TextView tvTotalCash;
    private TextView tvAmount;
    private TextView tvTotalAmount;
    private RecyclerView recyclerView;
    private TextView tvInviteRule;

    InviteDto inviteDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_code);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInviteCodeInfo();
    }

    private void initView() {
        setTitle("邀请赚钱");
        tvOther = findViewById(R.id.tvOther);
        tvOther.setVisibility(View.VISIBLE);
        tvOther.setOnClickListener(this);
        tvOther.setText("佣金明细");

        tvTotalCash = findViewById(R.id.tvTotalCash);
        tvAmount = findViewById(R.id.tvAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvInviteRule = findViewById(R.id.tvInviteRule);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.tvInvitationGo).setOnClickListener(this);
        findViewById(R.id.tvInvitationConfirm).setOnClickListener(this);
        findViewById(R.id.invitationSort).setOnClickListener(this);
        findViewById(R.id.tvInvitationWithdraw).setOnClickListener(this);
    }

    /**
     * 获取邀请码信息
     */
    private void getInviteCodeInfo() {
        HttpApiSupport.getInviteCodeInfo(new HttpApiCallBack<InviteDto>() {
            @Override
            public void onHttpRet(int code, String msg, InviteDto retModel) {
                if (code == 1 && null != retModel) {
                    inviteDto = retModel;
                    tvAmount.setText(retModel.amount + "");
                    tvTotalCash.setText(retModel.totalCash + "");
                    tvTotalAmount.setText(retModel.totalAmount + "");
                    if (!TextUtils.isEmpty(retModel.inviteRule)) {
                        tvInviteRule.setText(Html.fromHtml(retModel.inviteRule));
                    }
                    if (null != retModel.msg2 && !retModel.msg2.isEmpty()) {
                        QRCodeAdpater qrCodeAdpater = new QRCodeAdpater(retModel.msg2);
                        recyclerView.setAdapter(qrCodeAdpater);
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.tvOther) {
            ARouter.getInstance().build(ARouterPath.WebActivity).withInt(ARouterValueNameConfig.WebActivityType, 1).navigation();
        } else if (view.getId() == R.id.invitationSort) {
            ARouter.getInstance().build(ARouterPath.InvitationRankActivity).navigation();
        } else if (view.getId() == R.id.tvInvitationGo || view.getId() == R.id.tvInvitationConfirm) {//去邀请
            ArrayList<ShareDialogBean> otherBeans = new ArrayList<>();
            ShareDialogBean beanPicture = new ShareDialogBean();
            beanPicture.id = 1001;
            beanPicture.src = R.mipmap.icon_invitation_picture_share;
            beanPicture.text = "图片分享";
            otherBeans.add(beanPicture);
            ShareDialogBean beanCopy = new ShareDialogBean();
            beanCopy.id = 1002;
            beanCopy.src = R.mipmap.icon_invitation_url_copy;
            beanCopy.text = "复制链接";
            otherBeans.add(beanCopy);

            ShareDialog shareDialog = new ShareDialog();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(ShareDialog.ShareDialogOtherBeans, otherBeans);
            shareDialog.setArguments(bundle);
            shareDialog.setShareDialogListener(new ShareDialog.ShareDialogListener() {
                @Override
                public void onItemClick(long id) {
                    if (InvitationCodeActivity.this.inviteDto == null) {
                        return;
                    }
                    if (id == 1001) {//图片分享
                        ARouter.getInstance().build(ARouterPath.SaveInvitationCodeActivity).withString(ARouterValueNameConfig.URL, InvitationCodeActivity.this.inviteDto.inviteUrl).navigation();
                    } else if (id == 1002) {//复制链接
                        WordUtil.copyLink(InvitationCodeActivity.this.inviteDto.inviteUrl);
                    } else if (id == 1) {
                        MobShareUtil.getInstance().shareApp(MobConst.Type.WX);
                    } else if (id == 2) {
                        MobShareUtil.getInstance().shareApp(MobConst.Type.WX_PYQ);
                    } else if (id == 3) {
                        MobShareUtil.getInstance().shareApp(MobConst.Type.QQ);
                    } else if (id == 4) {
                        MobShareUtil.getInstance().shareApp(MobConst.Type.QZONE);
                    }
                }
            });
            shareDialog.show(getSupportFragmentManager(), "ShareDialog");
        } else if (view.getId() == R.id.tvInvitationWithdraw) {//提现
            ARouter.getInstance().build(ARouterPath.InvitationExtractActivity).navigation();
        }
    }
}
