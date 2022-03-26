package com.kalacheng.livecommon.fragment;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.busvoicelive.httpApi.HttpApiHttpVoice;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.LiveUserGiftListAdpater;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;
import com.klc.bean.SendGiftPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看用户个人主页
 */
public class LiveUserDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private int mRelation;//自己的角色
    private int mToRelation;//对方的角色

    private RoundedImageView LiveUser_HeadImage;
    //    private TextView LiveUser_Sign;
    private ImageView LiveUser_Grade;
    private ImageView mLevel;
    private ImageView mSex;
    private TextView LiveUser_Name;
    private TextView LiveUser_Id;


    private String mToName;//对方的名字
    private ApiUserInfo userInfo;


    private TextView LiveUser_location;
    private TextView tv_userlevel;
    private ImageView iv_userlevel;
    private TextView tv_userlevel_name;

    private TextView tv_wealthlevel;
    private ImageView iv_wealthlevel;

    private TextView tv_noblelevel;
    private ImageView iv_noblelevel;

    private LinearLayout LiveUser_GiftLin;
    private RecyclerView LiveUser_GiftList;
    private TextView LiveUser_GiftList_title;
    private LiveUserGiftListAdpater adpater;

    private ImageView LiveUser_Setting;
    private TextView LiveUser_Report;

    private TextView LiveUser_Homepage;

    private LinearLayout LiveUser_Button;
    private TextView LiveUser_Follow;

    private TextView LiveUser_Call;

    private TextView LiveUser_GiveGift;

    private LinearLayout LiveUser_Gender;

    private LinearLayout buynoble_lin;
    private LinearLayout noble_line;

    private AppJoinRoomVO apiJoinRoom;

    //数量
    private TextView LiveUser_FansNum;
    private TextView LiveUser_FollowNum;
    private TextView LiveUser_ExpensesNum;
    private TextView LiveUser_ProfitNum;
    private TextView Embrace_Mike;

    private ImageView LiveUser_close;

    private TextView LiveUser_Message;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_live_user_one;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        apiJoinRoom = getArguments().getParcelable("ApiJoinRoom");

        LiveUser_HeadImage = mRootView.findViewById(R.id.LiveUser_HeadImage);
        LiveUser_Name = mRootView.findViewById(R.id.LiveUser_Name);
        LiveUser_Grade = mRootView.findViewById(R.id.LiveUser_Grade);
        LiveUser_Id = mRootView.findViewById(R.id.LiveUser_Id);
//        LiveUser_Sign = mRootView.findViewById(R.id.LiveUser_Sign);
        LiveUser_location = mRootView.findViewById(R.id.LiveUser_location);
        LiveUser_Gender = mRootView.findViewById(R.id.LiveUser_Gender);
        //等级
        tv_userlevel = mRootView.findViewById(R.id.tv_userlevel);
        iv_userlevel = mRootView.findViewById(R.id.iv_userlevel);
        tv_userlevel_name = mRootView.findViewById(R.id.tv_userlevel_name);

        //财富
        tv_wealthlevel = mRootView.findViewById(R.id.tv_wealthlevel);
        iv_wealthlevel = mRootView.findViewById(R.id.iv_wealthlevel);

        //贵族
        tv_noblelevel = mRootView.findViewById(R.id.tv_noblelevel);
        iv_noblelevel = mRootView.findViewById(R.id.iv_noblelevel);
        noble_line = mRootView.findViewById(R.id.noble_line);
        buynoble_lin = mRootView.findViewById(R.id.buynoble_lin);
        buynoble_lin.setOnClickListener(this);

        //设置
        LiveUser_Setting = mRootView.findViewById(R.id.LiveUser_Setting);
        LiveUser_Setting.setOnClickListener(this);

        //举报
        LiveUser_Report = mRootView.findViewById(R.id.LiveUser_Report);
        LiveUser_Report.setOnClickListener(this);

        //私信
        LiveUser_Message = mRootView.findViewById(R.id.LiveUser_Message);
        LiveUser_Message.setOnClickListener(this);

        //个人主页
        LiveUser_Homepage = mRootView.findViewById(R.id.LiveUser_Homepage);
        LiveUser_Homepage.setOnClickListener(this);

        LiveUser_Button = mRootView.findViewById(R.id.LiveUser_Button);
        LiveUser_Follow = mRootView.findViewById(R.id.LiveUser_Follow);
        LiveUser_Follow.setOnClickListener(this);

        //@ta
        LiveUser_Call = mRootView.findViewById(R.id.LiveUser_Call);
        LiveUser_Call.setOnClickListener(this);

        //送礼
        LiveUser_GiveGift = mRootView.findViewById(R.id.LiveUser_GiveGift);
        LiveUser_GiveGift.setOnClickListener(this);

        LiveUser_FansNum = mRootView.findViewById(R.id.LiveUser_FansNum);
        LiveUser_FollowNum = mRootView.findViewById(R.id.LiveUser_FollowNum);
        LiveUser_ExpensesNum = mRootView.findViewById(R.id.LiveUser_ExpensesNum);
        LiveUser_ProfitNum = mRootView.findViewById(R.id.LiveUser_ProfitNum);

        Embrace_Mike = mRootView.findViewById(R.id.Embrace_Mike);
        Embrace_Mike.setOnClickListener(this);

        LiveUser_close = mRootView.findViewById(R.id.LiveUser_close);
        LiveUser_close.setOnClickListener(this);

        LiveUser_GiftLin = mRootView.findViewById(R.id.LiveUser_GiftLin);
        LiveUser_GiftList = mRootView.findViewById(R.id.LiveUser_GiftList);
        LiveUser_GiftList_title = mRootView.findViewById(R.id.LiveUser_GiftList_title);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        LiveUser_GiftList.setLayoutManager(manager);
        LiveUser_GiftList.addItemDecoration(new ItemDecoration(mContext, 0, 10, 0));
        adpater = new LiveUserGiftListAdpater(mContext);
        LiveUser_GiftList.setAdapter(adpater);

        getUserInformation();


    }


    public void getUserInformation() {
        HttpApiAppUser.personCenter(LiveConstants.ANCHORID, apiJoinRoom.liveType, LiveConstants.TOUID, new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1) {
                    if (retModel != null) {
                        showData(retModel);
                    }
                } else {
                    ToastUtil.show(msg);
                }

            }
        });

    }

    private void showData(ApiUserInfo retModel) {
        userInfo = retModel;

        if (apiJoinRoom.liveType == 2) {
            if (HttpClient.getUid() == LiveConstants.ANCHORID && LiveConstants.TOUID != HttpClient.getUid()) {

                Embrace_Mike.setVisibility(View.VISIBLE);
            } else {
                Embrace_Mike.setVisibility(View.GONE);
            }

            //判断是否在麦上
//            for (int i = 0; i < VocieLiveConstant.getInstance().getList().size(); i++) {
//                if (VocieLiveConstant.getInstance().getList().get(i).uid == LiveConstants.TOUID || LiveConstants.ANCHORID == LiveConstants.TOUID) {
//                    LiveUser_GiveGift.setVisibility(View.VISIBLE);
//                }
//            }
        } else {
            Embrace_Mike.setVisibility(View.GONE);
        }


        // 靓号 / id
        if (TextUtils.isEmpty(retModel.goodnum)) {
            LiveUser_Id.setTextColor(Color.parseColor("#999999"));
            LiveUser_Id.setText("ID:" + retModel.userId);
        } else {
            LiveUser_Id.setTextColor(Color.parseColor("#F6B86A"));
            LiveUser_Id.setText("靓号:" + retModel.goodnum);
        }
        mToName = retModel.username;
        LiveUser_Name.setText(mToName);
        ImageLoader.display(retModel.avatar, LiveUser_HeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(retModel.anchorGradeImg, LiveUser_Grade);
        LiveUser_location.setText(retModel.address);
//        LiveUser_Sign.setText("个性签名：" + retModel.signature);
        LiveUser_FansNum.setText(String.valueOf(retModel.fansNum));
        LiveUser_FollowNum.setText(String.valueOf(retModel.followNum));
        LiveUser_ExpensesNum.setText(retModel.totalConsumeCoinStr);
        LiveUser_ProfitNum.setText(retModel.totalIncomeVotesStr);

//        ImageLoader.display(retModel.userGradeImg, iv_userlevel);
        tv_wealthlevel.setText(String.valueOf(retModel.wealthGrade));
//        ImageLoader.display(retModel.wealthGradeImg, iv_wealthlevel);

        String nobleName = "暂无";
        if (!TextUtils.isEmpty(retModel.nobleName)) {
            nobleName = retModel.nobleName;
            nobleName = nobleName.replace("贵族", "");
        }

        tv_noblelevel.setText(nobleName);
//        ImageLoader.display(retModel.nobleGradeImg, iv_noblelevel);


        SexUtlis.getInstance().setSex(mContext, LiveUser_Gender, retModel.sex, retModel.age);

        if (retModel.role == 1) {
            tv_userlevel_name.setText("主播等级");
            tv_userlevel.setText(String.valueOf(retModel.anchorGrade));

        } else {
            tv_userlevel_name.setText("用户等级");
            tv_userlevel.setText(String.valueOf(retModel.userGrade));

        }

//        if (retModel.vipType == 1) {
        buynoble_lin.setVisibility(View.GONE);
        noble_line.setVisibility(View.VISIBLE);
//        } else {
//            buynoble_lin.setVisibility(View.VISIBLE);
//            noble_line.setVisibility(View.GONE);
//        }
        //判断设置
//        当前用户跟直播间的关系1当前直播间主播2管理员3普通用户
        if (retModel.relation == 3) {//自己是观众
            if (retModel.userId == HttpClient.getUid()) {//观众点自己头像
                LiveUser_Report.setVisibility(View.GONE);
            } else {
                LiveUser_Report.setVisibility(View.VISIBLE);
            }
            LiveUser_Setting.setVisibility(View.GONE);
        } else if (retModel.relation == 2) {//自己是管理员
            if (retModel.userId == HttpClient.getUid()) {
                LiveUser_Setting.setVisibility(View.GONE);
                LiveUser_Report.setVisibility(View.GONE);
            } else {
                if (retModel.toRelation == 1) {//对方是主播
                    LiveUser_Setting.setVisibility(View.GONE);
                    LiveUser_Report.setVisibility(View.VISIBLE);
                } else if (retModel.toRelation == 2) {//对方是管理员
                    LiveUser_Setting.setVisibility(View.GONE);
                    LiveUser_Report.setVisibility(View.VISIBLE);
                } else {//对方是观众
                    LiveUser_Setting.setVisibility(View.VISIBLE);
                    LiveUser_Report.setVisibility(View.GONE);
                }
            }
        } else {//自己是主播
            if (retModel.userId == HttpClient.getUid()) {
                LiveUser_Setting.setVisibility(View.GONE);
            } else {
                LiveUser_Setting.setVisibility(View.VISIBLE);
            }
            LiveUser_Report.setVisibility(View.GONE);
        }

        mRelation = retModel.relation;
        mToRelation = retModel.toRelation;

        if (retModel.userId == HttpClient.getUid()) {
            LiveUser_Follow.setTextColor(Color.parseColor("#999999"));
            LiveUser_Follow.setClickable(false);
            LiveUser_Call.setTextColor(Color.parseColor("#999999"));
            LiveUser_Call.setClickable(false);
            LiveUser_GiveGift.setTextColor(Color.parseColor("#999999"));
            LiveUser_GiveGift.setClickable(false);
            LiveUser_Message.setTextColor(Color.parseColor("#999999"));
            LiveUser_Message.setClickable(false);
        } else {
            //是否关注
            if (retModel.followStatus == 0) {
                LiveUser_Follow.setText("+ 关注");
                LiveUser_Follow.setTextColor(Color.parseColor("#A570FE"));
                LiveUser_Follow.setClickable(true);
            } else if (retModel.followStatus == 1) {
                LiveUser_Follow.setText("已关注");
                LiveUser_Follow.setTextColor(Color.parseColor("#999999"));
                LiveUser_Follow.setClickable(false);
            }
            LiveUser_Call.setTextColor(Color.parseColor("#333333"));
            LiveUser_Call.setClickable(true);
            LiveUser_GiveGift.setTextColor(Color.parseColor("#333333"));
            LiveUser_GiveGift.setClickable(true);
            LiveUser_Message.setTextColor(Color.parseColor("#333333"));
            LiveUser_Message.setClickable(true);

        }


        if (retModel.giftWall != null && retModel.giftWall.size() > 0) {
            LiveUser_GiftList.setVisibility(View.VISIBLE);
            adpater.getLiveUserGiftList(retModel.giftWall);
        } else {
            LiveUser_GiftList.setVisibility(View.GONE);
            LiveUser_GiftList_title.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        if (CheckDoubleClick.isFastDoubleClick()) return;
        int id = v.getId();

        //预防 空用户数据进行操作
        if (userInfo == null) {
            ToastUtil.show("对方数据异常");
            return;
        }

        if (id == R.id.LiveUser_Setting) {
            setting();
        } else if (id == R.id.LiveUser_Report) {
            ARouter.getInstance().build(ARouterPath.VideoReport).withLong(ARouterValueNameConfig.USERID, LiveConstants.TOUID).navigation();
        } else if (id == R.id.LiveUser_Homepage) {
            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, LiveConstants.TOUID).navigation();
            dismiss();
        } else if (id == R.id.LiveUser_Follow) {
            setFollow();
        } else if (id == R.id.buynoble_lin) {//购买贵族
//            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
//                    + HttpConstants.URL_NOBLE + "_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
        } else if (id == R.id.LiveUser_close) {
            dismiss();
        } else if (id == R.id.LiveUser_Message) {//私信
            if (userInfo != null) {
                if (HttpClient.getUid() != LiveConstants.TOUID) {
                    ARouter.getInstance().build(ARouterPath.ChatRoomActivity)
                            .withString(ARouterValueNameConfig.TO_UID, String.valueOf(LiveConstants.TOUID))
                            .withString(ARouterValueNameConfig.Name, userInfo.username)
                            .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                            .navigation();
                } else {
                    ToastUtil.show("不能和自己聊天哦");
                }
            }
        } else if (id == R.id.Embrace_Mike) {
            EmbraceMike();
        } else if (id == R.id.LiveUser_Call) {//@Ta
            if (userInfo != null) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OpenChat, userInfo.username);
                dismiss();
            }
        } else if (id == R.id.LiveUser_GiveGift) {//送礼
            if (userInfo != null) {
                SendGiftPeopleBean bean = new SendGiftPeopleBean();
                bean.name = userInfo.username;
                bean.headimage = userInfo.avatar;
                bean.uid = userInfo.userId;
                bean.showid = apiJoinRoom.showid;
                bean.roomID = apiJoinRoom.roomId;
                bean.liveType = apiJoinRoom.liveType;
                bean.anchorID = apiJoinRoom.anchorId;
                bean.shortVideoId = -1;
                List<SendGiftPeopleBean> beanList = new ArrayList<>();
                beanList.add(bean);
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_SendGift, beanList);
                dismiss();
            }
        }
    }

    private void setting() {
        List<Integer> list = new ArrayList<>();

        if (mRelation == 1) {//自己是主播
            if (mToRelation == 2) {//对方是管理员
                list.add(R.string.live_setting_kick);
                if (userInfo.isShutUp == 1){
                    list.add(R.string.live_setting_gap_cancel);
                }else {
                    list.add(R.string.live_setting_gap);
                }
                list.add(R.string.live_setting_admin_cancel);
            } else if (mToRelation == 3) {//对方是观众
                list.add(R.string.live_setting_kick);
                if (userInfo.isShutUp == 1){
                    list.add(R.string.live_setting_gap_cancel);
                }else {
                    list.add(R.string.live_setting_gap);
                }
                list.add(R.string.live_setting_admin);
            }
        } else if (mRelation == 2) {//自己是管理员
            if (mToRelation == 3) {//对方是观众
                list.add(R.string.live_setting_kick);
                if (userInfo.isShutUp == 1){
                    list.add(R.string.live_setting_gap_cancel);
                }else {
                    list.add(R.string.live_setting_gap);
                }
            }
        }

//                list.add(R.string.live_setting_kick);
//                list.add(R.string.live_setting_gap);
////                list.add(R.string.live_setting_gap_list);
//                list.add(R.string.live_setting_admin);
////                list.add(R.string.live_setting_admin_list);

        DialogUtil.showStringArrayDialog(mContext, list.toArray(new Integer[list.size()]), mArrayDialogCallback);
    }

    private DialogUtil.StringArrayDialogCallback mArrayDialogCallback = new DialogUtil.StringArrayDialogCallback() {
        @Override
        public void onItemClick(String text, int tag) {
            if (tag == R.string.live_setting_kick) {
                kick();
            } else if (tag == R.string.live_setting_gap || tag == R.string.live_setting_gap_cancel) {
                setShutUp();
            } else if (tag == R.string.live_setting_gap_list) {
                gapList();
            } else if (tag == R.string.live_setting_admin || tag == R.string.live_setting_admin_cancel) {
                setAdmin();
            } else if (tag == R.string.live_setting_admin_list) {
                adminList();
            } else if (tag == R.string.live_setting_close_live) {
                closeLive();
            } else if (tag == R.string.live_setting_forbid_account) {
                forbidAccount();
            }
        }
    };

    /**
     * 禁言列表
     */
    private void gapList() {
        dismiss();
        LiveGapListDialogFragment liveGapListDialogFragment = new LiveGapListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ARouterValueNameConfig.Livetype, apiJoinRoom.liveType);
        liveGapListDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "LiveAdminListDialogFragment");
    }

    /**
     * 查看管理员列表
     */
    private void adminList() {
        dismiss();
        LiveAdminListDialogFragment liveAdminListDialogFragment = new LiveAdminListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("AnchorId", LiveConstants.ANCHORID);
        bundle.putLong(ARouterValueNameConfig.Livetype, apiJoinRoom.liveType);
        liveAdminListDialogFragment.setArguments(bundle);
        liveAdminListDialogFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), "LiveAdminListDialogFragment");
//        ((LiveActivity) mContext).openAdminListWindow();
    }

    /**
     * 踢人
     */
    private void kick() {
        dismiss();
        HttpApiPublicLive.addKick(LiveConstants.ANCHORID, (int) apiJoinRoom.liveType,  LiveConstants.TOUID,new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show(msg);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 禁言
     */
    private void setShutUp() {
        HttpApiPublicLive.addShutup(LiveConstants.ANCHORID, (int) apiJoinRoom.liveType, LiveConstants.TOUID, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show(msg);
                    dismiss();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 设置或取消管理员
     */
    private void setAdmin() {
        if (userInfo != null) {
            if (userInfo.toRelation == 2) {
                HttpApiPublicLive.cancelLivemanager((int) apiJoinRoom.liveType, LiveConstants.TOUID, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show(msg);
                            dismiss();
                        } else {
                            ToastUtil.show(msg);
                        }

                    }
                });
            } else {
                HttpApiPublicLive.addLivemanager((int) apiJoinRoom.liveType, LiveConstants.TOUID, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show(msg);
                            dismiss();
                        } else {
                            ToastUtil.show(msg);
                        }

                    }
                });
            }
        }
    }


    /**
     * 超管关闭直播间
     */
    private void closeLive() {
        dismiss();
//        HttpUtil.superCloseRoom(mLiveUid, false, mSuperCloseRoomCallback);
    }

    /**
     * 超管关闭直播间并禁用主播账户
     */
    private void forbidAccount() {
        dismiss();
//        HttpUtil.superCloseRoom(mLiveUid, true, mSuperCloseRoomCallback);
    }

    //关注
    public void setFollow() {
        if (userInfo != null) {
            HttpApiAppUser.set_atten(1, userInfo.userId, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        if (LiveConstants.TOUID == LiveConstants.ANCHORID) {
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_UserFollewSusser, null);
                        }
                        LiveUser_Follow.setText("已关注");
                        LiveUser_Follow.setTextColor(Color.parseColor("#333333"));
                    }
                }
            });
        }
    }

    //报上麦
    public void EmbraceMike() {
        if (userInfo != null) {
            HttpApiHttpVoice.letUserUpAssitan(userInfo.userId, LiveConstants.ROOMID, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    ToastUtil.show(msg);
                    dismiss();
                }
            });
        }
    }
}
