package com.kalacheng.commonview.component;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.ApiElasticFrame;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.libuser.model.GameUserWinAwardsDTO;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.commonview.utils.IconUtil;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.MagicTextView;
import com.klc.bean.RechargeBean;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/*
 *   开通贵族飘屏
 *   全站广播
 *   充值显示飘屏
 *   全局礼物飘屏
 *   抽奖动画
 **/
public class AllFloatingScreenComponent {

    private static volatile AllFloatingScreenComponent singleton;
    private int Dp;
    private Context mContext;

    private AllFloatingScreenComponent() {
        Dp = DpUtil.dp2px(DpUtil.getScreenWidth());
    }

    public static AllFloatingScreenComponent getInstance() {

        if (singleton == null) {
            synchronized (AllFloatingScreenComponent.class) {
                if (singleton == null) {
                    singleton = new AllFloatingScreenComponent();
                }
            }
        }
        return singleton;
    }


    //开通贵族飘屏
    Dialog OpenVipdialog;
    private ObjectAnimator OpenVIPAnimator;
    private ObjectAnimator OpenVIPAnimatorEnd;

    private boolean OpenVIPAnimatorShow = false;//动画是否在展示
    private List<ApiElasticFrame> openVipList = new ArrayList<>();

    public void getVipData(Context mContext, ApiElasticFrame apiElasticFrame) {
        this.mContext = mContext;
        openVipList.add(apiElasticFrame);
        if (openVipList.size() != 0) {
            if (!OpenVIPAnimatorShow) {
                OpenVIPAnimatorShow = true;
                vipFloatingScreen(openVipList.get(0));
            }
        }
    }

    //开通贵族飘屏
    @SuppressLint("ObjectAnimatorBinding")
    public void vipFloatingScreen(ApiElasticFrame apiElasticFrame) {
        OpenVipdialog = new Dialog(mContext, R.style.dialog2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            OpenVipdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            OpenVipdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        OpenVipdialog.setContentView(R.layout.vip_floating_screen);
        OpenVipdialog.setCancelable(false);
        OpenVipdialog.setCanceledOnTouchOutside(true);

        Window window = OpenVipdialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.y = DpUtil.getScreenHeight() - DpUtil.dp2px(100);
        window.setAttributes(params);

        RelativeLayout Re_OpenVIP = OpenVipdialog.findViewById(R.id.Re_OpenNobleGift);
        RoundedImageView OpenVIP_userImage = OpenVipdialog.findViewById(R.id.OpenNoble_userImage);
        TextView OpenVIP_userName = OpenVipdialog.findViewById(R.id.OpenNobleGift_userName);
        TextView OpenVIP_Type = OpenVipdialog.findViewById(R.id.OpenNobleGift_Type);

        ImageLoader.display(apiElasticFrame.avatar, OpenVIP_userImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        OpenVIP_userName.setText("【" + apiElasticFrame.userName + "】");
        OpenVIP_Type.setText("开通了 【" + apiElasticFrame.vipName + "】");

        OpenVIPAnimator = ObjectAnimator.ofFloat(Re_OpenVIP, "translationX", Dp / 2, 0);
        OpenVIPAnimator.setDuration(1000);
        OpenVIPAnimator.setInterpolator(new LinearInterpolator());

        OpenVIPAnimatorEnd = ObjectAnimator.ofFloat(Re_OpenVIP, "translationX", -Dp / 2);
        OpenVIPAnimatorEnd.setDuration(1000);
        OpenVIPAnimatorEnd.setInterpolator(new LinearInterpolator());
        OpenVIPAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mHandler.sendEmptyMessageDelayed(1, 5000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        OpenVIPAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (OpenVIPAnimator != null) {
                    OpenVIPAnimator.cancel();
                }
                OpenVIPAnimator = null;
                if (OpenVIPAnimatorEnd != null) {
                    OpenVIPAnimatorEnd.cancel();
                }
                OpenVIPAnimatorEnd = null;

                if (OpenVipdialog != null) {
                    OpenVipdialog.dismiss();
                }

                if (openVipList.size() != 0) {
                    openVipList.remove(0);
                    OpenVIPAnimatorShow = false;
                    if (openVipList.size() != 0) {
                        if (!OpenVIPAnimatorShow) {
                            vipFloatingScreen(openVipList.get(0));
                            OpenVIPAnimatorShow = true;
                        }
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        OpenVIPAnimator.start();
        OpenVipdialog.show();
    }


    // 全站广播
    Dialog broadCastDialog;
    private ObjectAnimator broadCastAnimator;
    private ObjectAnimator broadCastAnimatorEnd;

    private boolean broadCastAnimatorShow = false;//动画是否在展示
    private List<ApiSimpleMsgRoom> broadCastList = new ArrayList<>();

    public void getBroadCastData(Context mContext, ApiSimpleMsgRoom apiSimpleMsgRoom) {
        this.mContext = mContext;
        broadCastList.add(apiSimpleMsgRoom);
        if (broadCastList.size() != 0) {
            if (!broadCastAnimatorShow) {
                broadCastAnimatorShow = true;
                broadCastFloatingScreen(broadCastList.get(0));
            }
        }
    }

    //全站广播
    @SuppressLint("ObjectAnimatorBinding")
    public void broadCastFloatingScreen(ApiSimpleMsgRoom apiSimpleMsgRoom) {
        broadCastDialog = new Dialog(mContext, R.style.dialog2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            broadCastDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            broadCastDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        broadCastDialog.setContentView(R.layout.broadcast_floating_screen);
        broadCastDialog.setCancelable(false);
        broadCastDialog.setCanceledOnTouchOutside(true);

        Window window = broadCastDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.y = DpUtil.getScreenHeight() - DpUtil.dp2px(100);
        window.setAttributes(params);

        RelativeLayout rl_broadcast = broadCastDialog.findViewById(R.id.rl_broadcast);
        RoundedImageView rl_broadcast_userImage = broadCastDialog.findViewById(R.id.rl_broadcast_userImage);
        ImageView iv_broadcast_tag = broadCastDialog.findViewById(R.id.iv_broadcast_tag);
        TextView rl_broadcast_userName = broadCastDialog.findViewById(R.id.rl_broadcast_userName);
        TextView rl_broadcast_content = broadCastDialog.findViewById(R.id.rl_broadcast_content);

        ImageLoader.display(apiSimpleMsgRoom.avatar, rl_broadcast_userImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiSimpleMsgRoom.nobleGrade, iv_broadcast_tag, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        rl_broadcast_userName.setText(apiSimpleMsgRoom.uname + "：");
        rl_broadcast_content.setText(apiSimpleMsgRoom.content + "");

        broadCastAnimator = ObjectAnimator.ofFloat(rl_broadcast, "translationX", Dp / 2, 0);
        broadCastAnimator.setDuration(1000);
        broadCastAnimator.setInterpolator(new LinearInterpolator());

        broadCastAnimatorEnd = ObjectAnimator.ofFloat(rl_broadcast, "translationX", -Dp / 2);
        broadCastAnimatorEnd.setDuration(1000);
        broadCastAnimatorEnd.setInterpolator(new LinearInterpolator());
        broadCastAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mHandler.sendEmptyMessageDelayed(5, 5000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        broadCastAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (broadCastAnimator != null) {
                    broadCastAnimator.cancel();
                }
                broadCastAnimator = null;
                if (broadCastAnimatorEnd != null) {
                    broadCastAnimatorEnd.cancel();
                }
                broadCastAnimatorEnd = null;

                if (broadCastDialog != null) {
                    broadCastDialog.dismiss();
                }

                if (broadCastList.size() != 0) {
                    broadCastList.remove(0);
                    broadCastAnimatorShow = false;
                    if (broadCastList.size() != 0) {
                        if (!broadCastAnimatorShow) {
                            broadCastFloatingScreen(broadCastList.get(0));
                            broadCastAnimatorShow = true;
                        }
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        broadCastAnimator.start();
        broadCastDialog.show();
    }

    //充值显示飘屏
    Dialog Rechargedialog;
    private ObjectAnimator RechargeAnimator;
    private ObjectAnimator RechargeAnimatorEnd;
    private boolean RechargeAnimatorShow = false;//动画是否在展示
    private List<RechargeBean> RechargeList = new ArrayList<>();

    public void getRecharge(Context mContext, RechargeBean info) {
        this.mContext = mContext;
        RechargeList.add(info);
        if (RechargeList.size() != 0) {
            if (!RechargeAnimatorShow) {
                RechargeAnimatorShow = true;
                getRechargeShow(RechargeList.get(0));
            }
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    public void getRechargeShow(RechargeBean info) {
        Rechargedialog = new Dialog(mContext, R.style.dialog2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Rechargedialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            Rechargedialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        Rechargedialog.setContentView(R.layout.recharge_floating_screen);
        Rechargedialog.setCancelable(false);
        Rechargedialog.setCanceledOnTouchOutside(true);

        Window window = Rechargedialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.y = DpUtil.getScreenHeight() - DpUtil.dp2px(180);
        window.setAttributes(params);

        RelativeLayout Re_Recharge = Rechargedialog.findViewById(R.id.Re_Recharge);
        RoundedImageView Recharge_userImage = Rechargedialog.findViewById(R.id.Recharge_userImage);
        TextView Recharge_userName = Rechargedialog.findViewById(R.id.Recharge_userName);

        Recharge_userName.setText(info.apiUserInfo.username);
        ImageLoader.display(info.apiUserInfo.avatar, Recharge_userImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        MagicTextView Recharge_Money = Rechargedialog.findViewById(R.id.Recharge_Money);
        Recharge_Money.setText(DecimalFormatUtils.isIntegerDouble(info.coin) + "元");

        RechargeAnimator = ObjectAnimator.ofFloat(Re_Recharge, "translationX", Dp / 2, 0);
        RechargeAnimator.setDuration(1000);
        RechargeAnimator.setInterpolator(new LinearInterpolator());

        RechargeAnimatorEnd = ObjectAnimator.ofFloat(Re_Recharge, "translationX", -Dp / 2);
        RechargeAnimatorEnd.setDuration(1000);
        RechargeAnimatorEnd.setInterpolator(new LinearInterpolator());
        RechargeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mHandler.sendEmptyMessageDelayed(3, 5000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        RechargeAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (RechargeAnimator != null) {
                    RechargeAnimator.cancel();
                }
                RechargeAnimator = null;
                if (RechargeAnimatorEnd != null) {
                    RechargeAnimatorEnd.cancel();
                }
                RechargeAnimatorEnd = null;

                if (Rechargedialog != null) {
                    Rechargedialog.dismiss();
                }
                if (RechargeList.size() != 0) {
                    RechargeList.remove(0);
                    RechargeAnimatorShow = false;
                    if (RechargeList.size() != 0) {
                        if (!RechargeAnimatorShow) {
                            getRechargeShow(RechargeList.get(0));
                            RechargeAnimatorShow = true;
                        }
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        RechargeAnimator.start();
        Rechargedialog.show();
    }


    //全局礼物飘屏
    Dialog AllGiftdialog;
    private ObjectAnimator AllGiftAnimator;
    private ObjectAnimator AllGiftAnimatorEnd;
    private boolean Overallgiftshow = false;
    private boolean OverallgiftshowEnd = true;
    private List<ApiGiftSender> AllGiftList = new ArrayList<>();
    private int OverallSituationNumber = 0;
    private MagicTextView OverallSituationGift_number;

    public void getAllGift(Context mContext, ApiGiftSender apiGiftSender) {
        this.mContext = mContext;
        if (Overallgiftshow) {
            if (AllGiftList.get(0).userId == apiGiftSender.userId && AllGiftList.get(0).giftId == apiGiftSender.giftId) {
                Overallgiftshow = true;
                geOverallSituationNumber(apiGiftSender.giftNumber);
            } else {
                if (AllGiftList.size() > 0) {
                    boolean joinList = true;
                    for (int i = 0; i < AllGiftList.size(); i++) {
                        if (AllGiftList.get(i).userId == apiGiftSender.userId && AllGiftList.get(i).giftId == apiGiftSender.giftId) {
                            AllGiftList.get(i).giftNumber = AllGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                            joinList = false;
                            break;
                        }
                    }
                    if (joinList) {
                        AllGiftList.add(apiGiftSender);
                    }
                }
            }
        } else {
            if (AllGiftList.size() > 0) {
                boolean joinList = true;
                for (int i = 0; i < AllGiftList.size(); i++) {
                    if (AllGiftList.get(i).userId == apiGiftSender.userId && AllGiftList.get(i).giftId == apiGiftSender.giftId) {
                        AllGiftList.get(i).giftNumber = AllGiftList.get(i).giftNumber + apiGiftSender.giftNumber;
                        joinList = false;
                        break;
                    }
                }
                if (joinList) {
                    AllGiftList.add(apiGiftSender);
                }
            } else {
                AllGiftList.add(apiGiftSender);
            }
            if (OverallgiftshowEnd) {
                OverallSituationGiftAnimation(AllGiftList.get(0));
                OverallgiftshowEnd = false;
            }
        }

    }

    private void geOverallSituationNumber(int giftNumber) {
        OverallSituationNumber = (OverallSituationNumber + giftNumber);
        OverallSituationGift_number.setText("x" + OverallSituationNumber);
//        OverallSituationGift_number("x"+renderGiftCount(mContext,OverallSituationNumber));
    }

    public void OverallSituationGiftAnimation(ApiGiftSender apiGiftSender) {
        AllGiftdialog = new Dialog(mContext, R.style.dialog2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AllGiftdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            AllGiftdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        AllGiftdialog.setContentView(R.layout.all_floating_screen);
        AllGiftdialog.setCancelable(false);
        AllGiftdialog.setCanceledOnTouchOutside(false);

        Window window = AllGiftdialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.y = DpUtil.getScreenHeight() - DpUtil.dp2px(140);
        window.setAttributes(params);

        RelativeLayout Re_OverallSituationGift = AllGiftdialog.findViewById(R.id.Re_OverallSituationGift);
        RoundedImageView OverallSituationGift_userImage = AllGiftdialog.findViewById(R.id.OverallSituationGift_userImage);
        RoundedImageView OverallSituationGift_anchorImage = AllGiftdialog.findViewById(R.id.OverallSituationGift_anchorImage);
        ImageView OverallSituationGift_giftImage = AllGiftdialog.findViewById(R.id.OverallSituationGift_giftImage);
        OverallSituationGift_number = AllGiftdialog.findViewById(R.id.OverallSituationGift_number);
        OverallSituationNumber = 0;
        ImageLoader.display(apiGiftSender.userAvatar, OverallSituationGift_userImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiGiftSender.anchorAvatar, OverallSituationGift_anchorImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiGiftSender.gifticon, OverallSituationGift_giftImage);

        geOverallSituationNumber(apiGiftSender.giftNumber);
        AllGiftAnimator = ObjectAnimator.ofFloat(Re_OverallSituationGift, "translationX", Dp / 2, 0);
        AllGiftAnimator.setDuration(1000);
        AllGiftAnimator.setInterpolator(new LinearInterpolator());

        AllGiftAnimatorEnd = ObjectAnimator.ofFloat(Re_OverallSituationGift, "translationX", -Dp / 2);
        AllGiftAnimatorEnd.setDuration(1000);
        AllGiftAnimatorEnd.setInterpolator(new LinearInterpolator());

        AllGiftAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Overallgiftshow = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(2, 5000);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        AllGiftAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Overallgiftshow = false;
                AllGiftList.remove(0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (AllGiftAnimator != null) {
                    AllGiftAnimator.cancel();
                }
                if (AllGiftAnimatorEnd != null) {
                    AllGiftAnimatorEnd.cancel();
                }
                AllGiftAnimator = null;
                AllGiftAnimatorEnd = null;
                if (AllGiftdialog != null) {
                    AllGiftdialog.dismiss();
                }
                OverallgiftshowEnd = true;
                if (AllGiftList.size() != 0) {
                    if (OverallgiftshowEnd) {
                        OverallSituationGiftAnimation(AllGiftList.get(0));
                        OverallgiftshowEnd = false;
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        AllGiftAnimator.start();
        AllGiftdialog.show();
    }


    //抽奖动画
    Dialog luckDrawdialog;
    private ObjectAnimator luckDrawAnimator;
    private ObjectAnimator luckDrawAnimatorEnd;

    private boolean luckDrawAnimatorShow = false;//动画是否在展示
    private List<GameUserWinAwardsDTO> luckDrawList = new ArrayList<>();

    public void getLuckDrawData(Context mContext, GameUserWinAwardsDTO gameUserWinAwardsDTO) {
        this.mContext = mContext;
        luckDrawList.add(gameUserWinAwardsDTO);

        if (luckDrawList.size() != 0) {
            if (!luckDrawAnimatorShow) {
                luckDrawAnimatorShow = true;
                LuckDrawFloatingScreen(luckDrawList.get(0));
            }
        }

    }

    @SuppressLint("ObjectAnimatorBinding")
    public void LuckDrawFloatingScreen(GameUserWinAwardsDTO gameUserWinAwardsDTO) {
        luckDrawdialog = new Dialog(mContext, R.style.dialog2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            luckDrawdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            luckDrawdialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        luckDrawdialog.setContentView(R.layout.luckdraw_floating_screen);
        luckDrawdialog.setCancelable(false);
        luckDrawdialog.setCanceledOnTouchOutside(true);

        Window window = luckDrawdialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.y = DpUtil.getScreenHeight() - DpUtil.dp2px(150);
        window.setAttributes(params);

        RelativeLayout LuckDraw = luckDrawdialog.findViewById(R.id.LuckDraw_Re);
        TextView LuckDraw_Name = luckDrawdialog.findViewById(R.id.LuckDraw_Name);
        TextView LuckDraw_GameName = luckDrawdialog.findViewById(R.id.LuckDraw_GameName);
        TextView LuckDraw_GiftName = luckDrawdialog.findViewById(R.id.LuckDraw_GiftName);
        MagicTextView LuckDraw_Giftnumber = luckDrawdialog.findViewById(R.id.LuckDraw_Giftnumber);

        LuckDraw_Name.setText(gameUserWinAwardsDTO.userName);
        LuckDraw_GameName.setText(gameUserWinAwardsDTO.gameName);
        LuckDraw_GiftName.setText(gameUserWinAwardsDTO.awardsName);
        LuckDraw_Giftnumber.setText("x" + gameUserWinAwardsDTO.awardsNum);

        luckDrawAnimator = ObjectAnimator.ofFloat(LuckDraw, "translationX", Dp / 2, 0);
        luckDrawAnimator.setDuration(1000);
        luckDrawAnimator.setInterpolator(new LinearInterpolator());

        luckDrawAnimatorEnd = ObjectAnimator.ofFloat(LuckDraw, "translationX", -Dp / 2);
        luckDrawAnimatorEnd.setDuration(1000);
        luckDrawAnimatorEnd.setInterpolator(new LinearInterpolator());
        luckDrawAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mHandler.sendEmptyMessageDelayed(4, 5000);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        luckDrawAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (luckDrawAnimator != null) {
                    luckDrawAnimator.cancel();
                }
                luckDrawAnimator = null;
                if (luckDrawAnimatorEnd != null) {
                    luckDrawAnimatorEnd.cancel();
                }
                luckDrawAnimatorEnd = null;
                if (luckDrawdialog != null) {
                    luckDrawdialog.dismiss();
                }
                if (luckDrawList.size() != 0) {
                    luckDrawList.remove(0);
                    luckDrawAnimatorShow = false;
                    if (luckDrawList.size() != 0) {
                        if (!luckDrawAnimatorShow) {
                            LuckDrawFloatingScreen(luckDrawList.get(0));
                            luckDrawAnimatorShow = true;
                        }
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        luckDrawAnimator.start();
        luckDrawdialog.show();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (OpenVIPAnimatorEnd != null) {
                        OpenVIPAnimatorEnd.start();
                    }
                    break;
                case 2:
                    if (AllGiftAnimatorEnd != null) {
                        AllGiftAnimatorEnd.start();
                    }
                    break;
                case 3:
                    if (RechargeAnimatorEnd != null) {
                        RechargeAnimatorEnd.start();
                    }
                    break;
                case 4:
                    if (luckDrawAnimatorEnd != null) {
                        luckDrawAnimatorEnd.start();
                    }
                    break;
                case 5:
                    if (broadCastAnimatorEnd != null) {
                        broadCastAnimatorEnd.start();
                    }
                    break;
            }
        }
    };


    public void clean() {
        openVipList.clear();
        if (OpenVipdialog != null) {
            OpenVipdialog.dismiss();
        }
        if (Rechargedialog != null) {
            Rechargedialog.dismiss();
        }
        if (AllGiftdialog != null) {
            AllGiftdialog.dismiss();
        }
        if (OpenVIPAnimator != null) {
            OpenVIPAnimator.cancel();
            OpenVIPAnimator = null;
        }
        if (OpenVIPAnimatorEnd != null) {
            OpenVIPAnimatorEnd.cancel();
            OpenVIPAnimatorEnd = null;
        }
        if (RechargeAnimator != null) {
            RechargeAnimator.cancel();
            RechargeAnimator = null;
        }
        if (RechargeAnimatorEnd != null) {
            RechargeAnimatorEnd.cancel();
            RechargeAnimatorEnd = null;
        }
        if (AllGiftAnimator != null) {
            AllGiftAnimator.cancel();
            AllGiftAnimator = null;
        }
        if (AllGiftAnimatorEnd != null) {
            AllGiftAnimatorEnd.cancel();
            AllGiftAnimatorEnd = null;
        }

    }

    public static SpannableStringBuilder renderGiftCount(Context mContext, int count) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String s = String.valueOf(count);
        builder.append(s);
        for (int i = 0, length = s.length(); i < length; i++) {
            String c = String.valueOf(s.charAt(i));
            if (StringUtil.isInt(c)) {
                int icon = IconUtil.getGiftCountIcon(Integer.parseInt(c));
                Drawable drawable = ContextCompat.getDrawable(mContext, icon);
                if (drawable != null) {
                    drawable.setBounds(0, 0, DpUtil.dp2px(24), DpUtil.dp2px(32));
                    builder.setSpan(new ImageSpan(drawable), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }
}
