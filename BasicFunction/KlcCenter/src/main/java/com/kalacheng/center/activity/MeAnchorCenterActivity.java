package com.kalacheng.center.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.center.CenterConfig;
import com.kalacheng.center.R;
import com.kalacheng.centercommon.adapter.MeAnchorCenterAdapter;
import com.kalacheng.centercommon.databinding.ActivityMeAnchorCenterLayoutBinding;
import com.kalacheng.centercommon.viewmodel.MeAnchorCenterViewModel;
import com.kalacheng.commonview.dialog.ChangeLiveStateDialog;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AnchorVotesDTO;
import com.kalacheng.libuser.model.CfgPayCallOneVsOne;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: Administrator
 * @date: 2020/9/25
 * @info:
 */

@Route(path = ARouterPath.MeAnchorCenter)
public class MeAnchorCenterActivity extends BaseMVVMActivity<ActivityMeAnchorCenterLayoutBinding, MeAnchorCenterViewModel> {

    private MeAnchorCenterAdapter adapter;
    private String dialogTitle = "语音接听收费设置";
    private int isVideo = 1;  // 1：语音接听收费  2：视频接听收费
    private Dialog uploadDialog;
    private PermissionsUtil mProcessResultUtil;
    public double voiceCoin, videoCoin; // 视频通话费用/min,  语音通话费用/min
    public int liveState; // 一对一在线状态  0在线1忙碌2离开3通话中
    private int openState; // 一对一打开状态 0：默认，不打开  1：打开 (是否开启1v1通话)
    private String poster; // 海报
    private String video; // 视频地址
    private String videoImg; // 视频封面地址
    private String voice; // 录音地址;

    private CfgPayCallOneVsOne oneVsOne;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return com.kalacheng.centercommon.R.layout.activity_me_anchor_center_layout;
    }

    @Override
    public void initData() {
        initAdapter();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getEarnings();
        if (ConfigUtil.getBoolValue(R.bool.containOne2One)) {
            getSetting();
        }
    }

    private void initView() {
        mProcessResultUtil = new PermissionsUtil(MeAnchorCenterActivity.this);
        binding.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.llAnchorCenter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                        + "/static/h5page/index.html#/userRevenue?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
            }
        });

        binding.llAnchorCenter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                        + "/static/h5page/index.html#/userRevenue?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
            }
        });
        uploadDialog = DialogUtil.loadingDialog(mContext, com.kalacheng.centercommon.R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, "上传中");
    }

    private void initAdapter() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setNestedScrollingEnabled(false);
        adapter = new MeAnchorCenterAdapter(mContext);
        binding.recycler.setAdapter(adapter);
        adapter.setOnItemClickCallback(new OnItemClickCallback<SimpleImageUrlTextBean>() {
            @Override
            public void onClick(View view, SimpleImageUrlTextBean item) {
                onItemClick(item.src);
            }
        });

        List<SimpleImageUrlTextBean> tabBottomBeans = new ArrayList<>();
        String[] bottomNames = CenterConfig.getAnchorCenterNames();
        TypedArray bottomIds = CenterConfig.getAnchorCenterIds();
        for (int i = 0; i < bottomNames.length; i++) {
            tabBottomBeans.add(new SimpleImageUrlTextBean(bottomIds.getResourceId(i, 0), bottomNames[i]));
        }
        adapter.setData(tabBottomBeans);

        http();
        //getEarnings();
    }

    // 获取个人头部数据
    private void http() {
        HttpApiAppUser.getMyHeadInfo(new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    if (TextUtils.isEmpty(retModel.goodnum)) {
                        binding.tvIdName.setText("ID号：");
                        binding.tvId.setText(retModel.userId + "");
                    } else {
                        binding.tvIdName.setTextColor(Color.parseColor("#F6B86A"));
                        binding.tvId.setTextColor(Color.parseColor("#F6B86A"));
                        binding.tvIdName.setText("靓号：");
                        binding.tvId.setText(retModel.goodnum);
                    }
                    binding.tvNickname.setText(retModel.username);
                    String signStr = retModel.signature.trim();
                    ImageLoader.display(retModel.wealthGradeImg, binding.ivWealthGrade);
                    if (retModel.role == 1) {
                        ImageLoader.display(retModel.anchorGradeImg, binding.ivGrade);
                    } else {
                        ImageLoader.display(retModel.userGradeImg, binding.ivGrade);
                    }
                    if (!TextUtils.isEmpty(signStr)) {
                        binding.tvSign.setText(signStr);
                    } else {
                        binding.tvSign.setText("这个家伙很懒,什么也没说...");
                    }
                    if (null != retModel.avatar && !TextUtils.isEmpty(retModel.avatar)) {
                        ImageLoader.display(retModel.avatar, binding.ivAvatar, com.kalacheng.center.R.mipmap.ic_launcher, com.kalacheng.center.R.mipmap.ic_launcher);
                    } else {
                        binding.ivAvatar.setImageResource(com.kalacheng.center.R.mipmap.ic_launcher);
                    }
                }
            }
        });
    }

    // 收益
    private void getEarnings() {
        HttpApiAPPFinance.anchorVotes(new HttpApiCallBack<AnchorVotesDTO>() {
            @Override
            public void onHttpRet(int code, String msg, AnchorVotesDTO retModel) {
                if (code == 1 && null != retModel) {
                    // 有公会就显示 公会收益   没有就显示可提现魅力值
                    if (null != binding.tvMoney) {
                        if (retModel.anchorVotes > 0) {
                            binding.tvMoney.setText(DecimalFormatUtils.toTwo(retModel.anchorVotes) + "");
                        } else {
                            binding.tvMoney.setText(DecimalFormatUtils.toTwo(0) + "");
                        }
                        if (retModel.anchorPerc > 0) {
                            binding.tvScale.setText("分成比例：" + DecimalFormatUtils.isIntegerDouble(retModel.anchorPerc) + "%");
                        } else {
                            binding.tvScale.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    // 获取状态
    private void getSetting() {
        HttpApiAppUser.getPayCallOneVsOneCfg(new HttpApiCallBack<CfgPayCallOneVsOne>() {
            @Override
            public void onHttpRet(int code, final String msg, CfgPayCallOneVsOne retModel) {
                if (code == 1 && retModel != null) {
                    oneVsOne = retModel;
                    liveState = retModel.liveState;
                    openState = retModel.openState;
                    poster = retModel.poster;
                    video = retModel.video;
                    videoImg = retModel.videoImg;
                    voice = retModel.voice;
                    voiceCoin = retModel.voiceCoin;
                    videoCoin = retModel.videoCoin;
                    if (null != adapter) {
                        adapter.setStatusAdapter(retModel.openState, retModel.liveState, retModel.voiceCoin, retModel.videoCoin);
                    }
                }
            }
        });
    }

    // 修改话费设置
    private void setSetting() {
        HttpApiAppUser.setPayCallOneVsOne(liveState, openState, poster, video, videoCoin, videoImg, voice, voiceCoin, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                uploadDialog.dismiss();
                if (code == 1) {
                    ToastUtil.show("修改成功");
                    getSetting();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    private void showPhoneSetting(int type) {
        isVideo = type;
        if (type == 1) {
            dialogTitle = "语音接听收费设置";
        } else {
            dialogTitle = "视频接听收费设置";
        }
        DialogUtil.showCallChargesDialog(mContext, dialogTitle, new DialogUtil.InviteCodeCallBack() {
            @Override
            public void onConfirmClick(Dialog dialog, String content) {
                try {
                    if (isVideo == 1) {
                        voiceCoin = Double.parseDouble(content);
                    } else {
                        videoCoin = Double.parseDouble(content);
                    }
                    setSetting();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showLiveStateDialog() {
        ChangeLiveStateDialog changeLiveStateDialog = new ChangeLiveStateDialog();
        changeLiveStateDialog.setOnChangeLiveStateListener(new ChangeLiveStateDialog.OnChangeLiveStateListener() {
            @Override
            public void onChange(int liveState) {
                MeAnchorCenterActivity.this.liveState = liveState;
                setSetting();
            }
        });
        changeLiveStateDialog.show(getSupportFragmentManager(), "ChangeLiveStateDialog");
    }

    private void onItemClick(int src) {
        if (src == com.kalacheng.center.R.mipmap.drawicon_anchor_center_fans) {//粉丝团
            ARouter.getInstance().build(ARouterPath.FansGroupActivity).navigation();
        } else if (src == com.kalacheng.center.R.mipmap.icon_anchor_center_leaderboard) {//贡献榜
            ARouter.getInstance().build(ARouterPath.FansContributionActivity).navigation();
        } else if (src == com.kalacheng.center.R.mipmap.icon_me_live_data) {//直播数据
            ARouter.getInstance().build(ARouterPath.FansLiveDataActivity).navigation();
        } else if (src == com.kalacheng.center.R.mipmap.drawicon_anchor_center_guild) {//我的公会
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                    + "/api/guild/toGuildList?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=10&pageIndex=0&anchorId=" + HttpClient.getUid()).navigation();
        } else if (src == com.kalacheng.center.R.mipmap.drawicon_anchor_center_settings) {//付费设置
            ARouter.getInstance().build(ARouterPath.PaySettingActivity).navigation();
        } else if (src == com.kalacheng.center.R.mipmap.icon_anchor_center_cover) { // 封面设置
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
        } else if (src == com.kalacheng.center.R.mipmap.icon_anchor_center_status) {// 状态设置
            showLiveStateDialog();
        } else if (src == com.kalacheng.center.R.mipmap.icon_anchor_center_video) {// 视频接听收费
            showPhoneSetting(2);
        } else if (src == com.kalacheng.center.R.mipmap.icon_anchor_center_voice) {// 语音接听收费
            showPhoneSetting(1);
        } else if (src == com.kalacheng.center.R.mipmap.icon_anchor_center_open_call) {// 开启通话 通话设置
            if (!TextUtils.isEmpty(video) && !TextUtils.isEmpty(poster)) {
                openState = openState == 0 ? 1 : 0;
                setSetting();
            } else {
                ToastUtil.show("请先设置封面");
            }
        } else if (src == R.mipmap.icon_me_fans) {//粉丝团
            ARouter.getInstance().build(ARouterPath.FansGroupActivity).navigation();
        } else if (src == R.mipmap.icon_me_contribution) {//贡献榜
            ARouter.getInstance().build(ARouterPath.FansContributionActivity).navigation();
        } else if (src == R.mipmap.icon_me_guild) {//我的公会
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                    + "/api/guild/toGuildList?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken() + "&pageSize=10&pageIndex=0&anchorId=" + HttpClient.getUid()).navigation();
        }
    }


}