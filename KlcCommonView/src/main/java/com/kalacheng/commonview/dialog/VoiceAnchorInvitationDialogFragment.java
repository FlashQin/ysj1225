package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busvoicelive.httpApi.HttpApiHttpVoice;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class VoiceAnchorInvitationDialogFragment implements LookRoomUtlis.JoinRoomCallBack {
    private Context mContext;
    private Dialog dialog;

    public VoiceAnchorInvitationDialogFragment(Context mContext) {
        this.mContext = mContext;
        dialog = new Dialog(mContext, R.style.dialog2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.setContentView(R.layout.voice_anchor_invitation);
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

    public void show(final AppJoinRoomVO joinRoom, ApiUserInfo inviteInfo, final int isPay) {
        LookRoomUtlis.getInstance().setJoinRoomCallBack(this);
        RoundedImageView VoiceAnchorInvitation_HeadImage = dialog.findViewById(R.id.VoiceAnchorInvitation_HeadImage);
        TextView VoiceAnchorInvitation_Name = dialog.findViewById(R.id.VoiceAnchorInvitation_Name);
        ImageView VoiceAnchorInvitation_Grade = dialog.findViewById(R.id.VoiceAnchorInvitation_Grade);
        ImageView VoiceAnchorInvitation_Gender = dialog.findViewById(R.id.VoiceAnchorInvitation_Gender);

        ImageLoader.display(inviteInfo.avatar, VoiceAnchorInvitation_HeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        VoiceAnchorInvitation_Name.setText(inviteInfo.username);
        ImageLoader.display(inviteInfo.anchorGradeImg, VoiceAnchorInvitation_Grade);

        if (inviteInfo.sex == 1) {
            VoiceAnchorInvitation_Gender.setBackgroundResource(R.mipmap.icon_boy);
        } else {
            VoiceAnchorInvitation_Gender.setBackgroundResource(R.mipmap.icon_girl);
        }

        dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refuse(joinRoom.roomId);
            }
        });

        TextView VoiceAnchorInvitation_refuse = dialog.findViewById(R.id.VoiceAnchorInvitation_refuse);
        VoiceAnchorInvitation_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refuse(joinRoom.roomId);
            }
        });

        TextView VoiceAnchorInvitation_agree = dialog.findViewById(R.id.VoiceAnchorInvitation_agree);
        VoiceAnchorInvitation_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveConstants.VoiceAnchorInvitation = true;
                AppHomeHallDTO appHomeHall = new AppHomeHallDTO();
                appHomeHall.liveType = 2;
                appHomeHall.roomId = joinRoom.roomId;
                appHomeHall.coin = 0;
                appHomeHall.typeVal = joinRoom.roomTypeVal;
                appHomeHall.showid = joinRoom.showid;
                appHomeHall.isPay = isPay;
                LookRoomUtlis.getInstance().getData(appHomeHall, mContext);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void joinSuccess(final AppJoinRoomVO joinRoom) {

        LiveConstants.ROOMID = joinRoom.roomId;
        LiveConstants.ANCHORID = joinRoom.anchorId;
        SpUtil.getInstance().put(SpUtil.ANCHOR_ID, LiveConstants.ANCHORID);
        ARouter.getInstance().build(ARouterPath.JOINVoiceRoom).withParcelable("ApiJoinRoom", joinRoom).withParcelableArrayList("userList", (ArrayList<? extends Parcelable>) joinRoom.userList).withParcelableArrayList("MikeList", (ArrayList<? extends Parcelable>) joinRoom.assistanList).navigation(mContext, new NavigationCallback() {
            @Override
            public void onFound(Postcard postcard) {
                Log.i("aaa", "onFound" + postcard.toString());
            }

            @Override
            public void onLost(Postcard postcard) {
                Log.i("aaa", "onLost" + postcard.toString());
            }

            @Override
            public void onArrival(Postcard postcard) {
                Log.i("aaa", "onArrival" + postcard.toString());
            }

            @Override
            public void onInterrupt(Postcard postcard) {
                Log.i("aaa", "onInterrupt" + postcard.toString());
            }
        });

        // 延迟时间 等待语音直播间View加载 避免View还没加载完成 socket还没注册 后台就已经发了socket导致收不到socket的情况。
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HttpApiHttpVoice.replyAuthorInvt(1, joinRoom.roomId, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            dialog.dismiss();
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }
        }, 500);

    }

    /**
     * 拒绝
     */
    private void refuse(long roomID) {
        HttpApiHttpVoice.replyAuthorInvt(0, roomID, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code != 1) {
                    ToastUtil.show(msg);
                }
                dialog.dismiss();
            }
        });
    }
}
