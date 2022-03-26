package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.AppVIPSeats;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;

/*
 * 购买贵宾席
 * */
public class VoiceVipSeatsFragmentDialog extends BaseDialogFragment {
    private AppJoinRoomVO apiJoinRoom;

    @Override
    protected int getLayoutId() {
        return R.layout.voice_vipseats;
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
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            apiJoinRoom = getArguments().getParcelable("apiJoinRoom");

            ImageView close = mRootView.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            RoundedImageView vipSeats_userHead = mRootView.findViewById(R.id.vipSeats_userHead);
            ImageLoader.display(apiJoinRoom.anchorAvatar, vipSeats_userHead, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            TextView vipSeats_Name = mRootView.findViewById(R.id.vipSeats_Name);
            vipSeats_Name.setText("【" + apiJoinRoom.anchorName + "】邀请你上座贵宾席");

            ImageView vipSeats_sex = mRootView.findViewById(R.id.vipSeats_sex);
            if (apiJoinRoom.anchorSex == 1) {
                vipSeats_sex.setBackgroundResource(R.mipmap.icon_boy);
            } else {
                vipSeats_sex.setBackgroundResource(R.mipmap.icon_girl);
            }

            ImageView vipSeats_userGrade = mRootView.findViewById(R.id.vipSeats_userGrade);
            ImageLoader.display(apiJoinRoom.anchorGradeImg, vipSeats_userGrade);
            ImageView vipSeats_vipGrade = mRootView.findViewById(R.id.vipSeats_vipGrade);
            ImageLoader.display(apiJoinRoom.nobleGradeImg, vipSeats_vipGrade);
            ImageView vipSeats_fansGrade = mRootView.findViewById(R.id.vipSeats_fansGrade);
            ImageLoader.display(apiJoinRoom.levelImg, vipSeats_fansGrade);


            TextView vipSeats_money = mRootView.findViewById(R.id.vipSeats_money);

            vipSeats_money.setText(DecimalFormatUtils.isIntegerDouble(Double.parseDouble((String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_VIPSTATESFEE, ""))) + SpUtil.getInstance().getCoinUnit() + "成为直播间大卡司");

            LinearLayout vipSeats_Buy = mRootView.findViewById(R.id.vipSeats_Buy);
            vipSeats_Buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    getBuy();
                }
            });

            TextView vipSeats_OpenVip = mRootView.findViewById(R.id.vipSeats_OpenVip);
            vipSeats_OpenVip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                            + HttpConstants.URL_NOBLE +"_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBuy() {
        HttpApiPublicLive.purchaseVIPSeats(apiJoinRoom.anchorId, Double.parseDouble((String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_VIPSTATESFEE, "")), apiJoinRoom.liveType, new HttpApiCallBack<AppVIPSeats>() {
            @Override
            public void onHttpRet(int code, String msg, AppVIPSeats retModel) {
                if (code == 1) {

                    dismiss();
                    ToastUtil.show(msg);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
