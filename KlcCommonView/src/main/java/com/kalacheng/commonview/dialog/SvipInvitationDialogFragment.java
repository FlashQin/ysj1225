package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOTMCall;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.commonview.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class SvipInvitationDialogFragment {
    Dialog dialog;

    public SvipInvitationDialogFragment(Context mContext) {
        dialog = new Dialog(mContext, R.style.dialog2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.setContentView(R.layout.svip_invitation_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.y = DpUtil.dp2px(70);
        window.setAttributes(params);


    }

    public void show(final ApiUserInfo userInfo, final long feeUid) {
        RoundedImageView UserImage = dialog.findViewById(R.id.UserImage);
        TextView UserName = dialog.findViewById(R.id.UserName);
        TextView title = dialog.findViewById(R.id.title);

        ImageLoader.display(userInfo.avatar, UserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        UserName.setText(userInfo.username);


        ImageView closeImage = dialog.findViewById(R.id.close);
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refuse(feeUid);
            }
        });

        //忽略
        TextView seeyou = dialog.findViewById(R.id.seeyou);
        seeyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refuse(feeUid);
            }
        });

        //加入
        TextView join = dialog.findViewById(R.id.join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpApiOTMCall.replyInviteOTM(feeUid, 1, LiveConstants.mOOOSessionID, new HttpApiCallBack<OOOReturn>() {
                    @Override
                    public void onHttpRet(int code, String msg, OOOReturn retModel) {
                        if (code == 1) {
                            ARouter.getInstance().build(ARouterPath.One2OneSvipSideshowLive).withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel).withLong(ARouterValueNameConfig.OOOLiveJFeeUid, feeUid).withParcelableArrayList(ARouterValueNameConfig.OOOSeekChatLiveSideshow, (ArrayList<? extends Parcelable>) retModel.assisRets).navigation();
                        } else {
                            ToastUtil.show(msg);

                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    public void closeShow() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 拒绝
     */
    private void refuse(long feeUid) {
        HttpApiOTMCall.replyInviteOTM(feeUid, 0, LiveConstants.mOOOSessionID, new HttpApiCallBack<OOOReturn>() {
            @Override
            public void onHttpRet(int code, String msg, OOOReturn retModel) {
                if (code != 1) {
                    ToastUtil.show(msg);
                }
                dialog.dismiss();
            }
        });
    }
}

