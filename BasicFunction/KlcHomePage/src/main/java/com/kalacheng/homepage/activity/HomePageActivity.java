package com.kalacheng.homepage.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.fragment.PersonInfoFragment;
import com.kalacheng.centercommon.fragment.PicBrowseFragment;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.homepage.R;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.util.adapter.FragmentPagerAdapter2;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.HomePage)
public class HomePageActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.ANCHORID)
    public long AnchorID;
    private int isFollow;
    private TextView tvFollow;
    private ApiUserInfo apiUserInfo;
    private ProcessResultUtil mProcessResultUtil;
    private TextView tvVoiceMoney;
    private TextView tvVideoMoney;
    private TextView tvVoiceMoneyUnit, tvVideoMoneyUnit;
    private boolean isAnchor;
    private LinearLayout llCall;
    private LinearLayout llLiveCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initView();
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initData() {
        mProcessResultUtil = new ProcessResultUtil(this);
        HttpApiAppUser.personCenter(-1, -1, HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    if (retModel.role == 0)
                        isAnchor = false;
                    else
                        isAnchor = true;
                    HttpApiAppUser.personCenter(-1, -1, AnchorID, new HttpApiCallBack<ApiUserInfo>() {
                        @Override
                        public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                            if (code == 1 && null != retModel) {
                                apiUserInfo = retModel;
                                if (isAnchor && retModel.role == 0) {
                                    tvVoiceMoney.setText("语音通话");
                                    tvVideoMoney.setText("视频通话");
                                    tvVoiceMoneyUnit.setVisibility(View.GONE);
                                    tvVideoMoneyUnit.setVisibility(View.GONE);
                                } else {
                                    if (apiUserInfo.voiceCoin == 0) {
                                        tvVoiceMoney.setText("与TA语音");
                                        tvVoiceMoneyUnit.setVisibility(View.GONE);
                                    } else {
                                        tvVoiceMoney.setText(((int) apiUserInfo.voiceCoin) + "");
                                    }
                                    if (apiUserInfo.videoCoin == 0) {
                                        tvVideoMoney.setText("与TA视频");
                                        tvVideoMoneyUnit.setVisibility(View.GONE);
                                    } else {
                                        tvVideoMoney.setText(((int) apiUserInfo.videoCoin) + "");
                                    }
                                }
                                if (retModel.followStatus == 0) {
                                    tvFollow.setText("+  关注");
                                } else {
                                    tvFollow.setText("已关注");
                                }
                                if (ConfigUtil.getBoolValue(com.kalacheng.centercommon.R.bool.containOne2One)) {
                                    if (AnchorID == HttpClient.getUid()) {
                                        llCall.setVisibility(View.GONE);
                                        llLiveCall.setVisibility(View.GONE);
                                    } else {
                                        if (apiUserInfo.role == 0 && !isAnchor) {
                                            llLiveCall.setVisibility(View.VISIBLE);
                                            llCall.setVisibility(View.GONE);
                                        } else {
                                            llLiveCall.setVisibility(View.GONE);
                                            llCall.setVisibility(View.VISIBLE);
                                        }

                                    }
                                } else {
                                    if (AnchorID == HttpClient.getUid()) {
                                        llCall.setVisibility(View.GONE);
                                        llLiveCall.setVisibility(View.GONE);
                                    } else {
                                        llLiveCall.setVisibility(View.VISIBLE);
                                        llCall.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        tvVoiceMoney = findViewById(R.id.tvVoiceMoney);
        tvVideoMoney = findViewById(R.id.tvVideoMoney);
        tvVoiceMoneyUnit = (TextView) findViewById(R.id.tvVoiceMoneyUnit);
        tvVideoMoneyUnit = (TextView) findViewById(R.id.tvVideoMoneyUnit);
        tvVoiceMoneyUnit.setText(SpUtil.getInstance().getCoinUnit() + "/分钟");
        tvVideoMoneyUnit.setText(SpUtil.getInstance().getCoinUnit() + "/分钟");
        llCall = findViewById(R.id.ll_call);
        llLiveCall = findViewById(R.id.ll_live_call);
        tvFollow = findViewById(R.id.tv_follow);

        findViewById(R.id.ll_follow).setOnClickListener(this);
        findViewById(R.id.ll_voice).setOnClickListener(this);
        findViewById(R.id.ll_video).setOnClickListener(this);
        findViewById(R.id.text_message).setOnClickListener(this);
        findViewById(R.id.ll_live_chat).setOnClickListener(this);

        List<Fragment> mFragments = new ArrayList<>();
        PicBrowseFragment picBrowseFragment = new PicBrowseFragment(AnchorID);
        PersonInfoFragment personInfoFragment = new PersonInfoFragment(AnchorID);
        mFragments.add(picBrowseFragment);
        mFragments.add(personInfoFragment);

        FragmentPagerAdapter2 mAdapter = new FragmentPagerAdapter2(this, mFragments);
        ViewPager2 viewPager = findViewById(R.id.vertical_viewpager);
        viewPager.setAdapter(mAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.ll_follow) {
            setAttention();
        } else if (view.getId() == R.id.ll_voice) {
            final ApiUserInfo info = new ApiUserInfo();
            info.userId = apiUserInfo.userId;
            LiveConstants.mIsOOOSend = true;
            info.avatar = apiUserInfo.avatar;
            info.sex = apiUserInfo.sex;
            info.username = apiUserInfo.username;
            info.role = apiUserInfo.role;
            mProcessResultUtil.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            }, new Runnable() {
                @Override
                public void run() {
                    LookRoomUtlis.getInstance().showDialog(0, info, mProcessResultUtil, mContext, 1);
//                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, 2, info, 1);
                }
            });
        } else if (view.getId() == R.id.ll_video) {
            final ApiUserInfo info = new ApiUserInfo();
            info.userId = apiUserInfo.userId;
            LiveConstants.mIsOOOSend = true;
            info.avatar = apiUserInfo.avatar;
            info.sex = apiUserInfo.sex;
            info.username = apiUserInfo.username;
            info.role = apiUserInfo.role;
            mProcessResultUtil.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            }, new Runnable() {
                @Override
                public void run() {
                    LookRoomUtlis.getInstance().showDialog(1, info, mProcessResultUtil, mContext, 1);

//                        OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(mContext, 1, info, 1);

                }
            });
        } else if (view.getId() == R.id.text_message) {
            if (null != apiUserInfo) {
                if (HttpClient.getUid() != apiUserInfo.userId) {
                    BaseApplication.containsActivity("ChatRoomActivity");
                    ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                            .withString(ARouterValueNameConfig.TO_UID, String.valueOf(apiUserInfo.userId))
                            .withString(ARouterValueNameConfig.Name, apiUserInfo.username)
                            .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                            .navigation();
                } else {
                    ToastUtil.show("不能和自己聊天哦");
                }

            }
        } else if (view.getId() == R.id.ll_live_chat) {
            if (null != apiUserInfo) {
                if (HttpClient.getUid() != apiUserInfo.userId) {
                    BaseApplication.containsActivity("ChatRoomActivity");
                    ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                            .withString(ARouterValueNameConfig.TO_UID, String.valueOf(apiUserInfo.userId))
                            .withString(ARouterValueNameConfig.Name, apiUserInfo.username)
                            .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                            .navigation();
                } else {
                    ToastUtil.show("不能和自己聊天哦");
                }

            }
        }
    }

    /**
     * 关注
     */
    private void setAttention() {
        if (isFollow == 0) {
            isFollow = 1;
        } else {
            isFollow = 0;
        }
        HttpApiAppUser.set_atten(isFollow, AnchorID, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (isFollow == 0) {
                        tvFollow.setText("+  关注");
                    } else {
                        tvFollow.setText("已关注");
                    }
                }
            }
        });
    }
}
