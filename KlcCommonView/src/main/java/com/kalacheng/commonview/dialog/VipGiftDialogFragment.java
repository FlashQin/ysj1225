package com.kalacheng.commonview.dialog;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.MagicTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: Administrator
 * @date: 2020/9/1
 * @info: 送礼特效
 */
public class VipGiftDialogFragment extends BaseDialogFragment {

    private RoundedImageView ivPic;
    private MagicTextView tvNumber;
    private TextView tvName, tvGift;
    private ImageView ivGift;

    private Timer timer;
    private final int timeControl = 4;
    private int time;
    private String again = "";
    private int giftNumb = 0;
    private ApiGiftSender apiGiftSender;
    private ScaleAnimation mAnimation, anim;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_view_gift_layout;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.DialogTransparent;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        window.setWindowAnimations(R.style.DialogTransparent);
        setCancelable(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ivPic = mRootView.findViewById(R.id.vip_gift_pic);
        tvName = mRootView.findViewById(R.id.vip_gift_name);
        ivGift = mRootView.findViewById(R.id.vip_gift_content_gift);
        tvGift = mRootView.findViewById(R.id.vip_gift_tag);
        tvNumber = mRootView.findViewById(R.id.vip_gift_number);

        apiGiftSender = getArguments().getParcelable("apiGiftSender");
        setData(apiGiftSender);
        startTime();
        initAnimation();
    }

    private void initAnimation() {
        mAnimation = new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimation.setDuration(400);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setRepeatCount(-1);
        ivGift.startAnimation(mAnimation);

        anim = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    }


    public void setData(ApiGiftSender apiGiftSender) {
        if (!TextUtils.isEmpty(again) && again.equals(String.valueOf(apiGiftSender.userId) + String.valueOf(apiGiftSender.anchorId) + String.valueOf(apiGiftSender.giftId))) {
            giftNumb += apiGiftSender.giftNumber;
        } else {
            giftNumb = 1;
        }
        time = timeControl;
        again = String.valueOf(apiGiftSender.userId) + String.valueOf(apiGiftSender.anchorId) + String.valueOf(apiGiftSender.giftId);
        ImageLoader.display(apiGiftSender.userAvatar, ivPic, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(apiGiftSender.gifticon, ivGift);
        tvName.setText("【" + apiGiftSender.userName + "】");
        tvGift.setText("【" + apiGiftSender.giftname + "】");
        tvNumber.setText("x" + giftNumb);
        //AnimationUtils.followAnimation(tvNumber, 100);
        if (null != anim && null != tvNumber) {
            anim.setDuration(100);
            tvNumber.startAnimation(anim);
        }
    }

    private void startTime() {
        if (0 < time) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        time--;
                        if (time <= 0) {
                            remove();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 1000);
        } else {
            remove();
        }
    }

    private void remove() {
        again = "";
        timer.cancel();
        time = timeControl;
        dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mAnimation.cancel();
        mAnimation = null;
    }
}