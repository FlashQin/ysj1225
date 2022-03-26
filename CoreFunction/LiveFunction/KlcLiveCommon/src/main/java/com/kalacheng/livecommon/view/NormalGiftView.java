package com.kalacheng.livecommon.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.commonview.view.TextRender;
import com.kalacheng.util.utils.glide.ImageLoader;

public class NormalGiftView extends BaseViewHolder {
    private View mRoot;
    private View mBg;
    private View mStar;
    private ImageView mAvatar;
    private TextView mName;
    private TextView mContent;
    private ImageView mGiftIcon;
    private TextView mGiftCount;
    private TextView mGiftGroupCount;
    private TextView mMulSign;//乘号
    private int mDp214;
    private ObjectAnimator mAnimator;
    private Animation mAnimation;//礼物数字执行的放大动画
    private boolean mIdle;//是否空闲
    private boolean mShowed;//展示礼物的控件是否显示出来了
    private String mLastGiftKey;//上次送礼物的信息
    private int mLianCount;//连送礼物的个数
    private Handler mHandler;

    public NormalGiftView(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_normal_gift;
    }

    @Override
    public void init() {
        mRoot = findViewById(R.id.root);
        mBg = findViewById(R.id.bg);
        mStar = findViewById(R.id.star);
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mName = (TextView) findViewById(R.id.name);
        mContent = (TextView) findViewById(R.id.content);
        mGiftIcon = (ImageView) findViewById(R.id.gift_icon);
        mGiftCount = (TextView) findViewById(R.id.gift_count);
        mGiftGroupCount = (TextView) findViewById(R.id.gift_group_count);
        mMulSign = (TextView) findViewById(R.id.mul_sign);
        mDp214 = DpUtil.dp2px(214);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (mBg != null) {
                    mBg.setTranslationX(-mDp214);
                }
                if (mStar != null && mStar.getVisibility() == View.VISIBLE) {
                    mStar.setVisibility(View.INVISIBLE);
                }
                if (mGiftCount != null && mGiftCount.getVisibility() != View.VISIBLE) {
                    mGiftCount.setVisibility(View.VISIBLE);
                    mGiftCount.setText(TextRender.renderGiftCount(mLianCount));
                }
                if (mGiftCount != null) {
                    mGiftCount.clearAnimation();
                    if (mAnimation != null) {
                        mGiftCount.startAnimation(mAnimation);
                    }
                }
            }
        };
        Interpolator interpolator = new AccelerateDecelerateInterpolator();
        mAnimator = ObjectAnimator.ofFloat(mBg, "translationX", 0);
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(interpolator);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mStar != null && mStar.getVisibility() != View.VISIBLE) {
                    mStar.setVisibility(View.VISIBLE);
                }
                if (mHandler != null) {
                    mHandler.sendEmptyMessageDelayed(0, 200);
                }
            }
        });
        mAnimation = new ScaleAnimation(1.5f, 0.7f, 1.5f, 0.7f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f);
        mAnimation.setDuration(200);
        mAnimation.setInterpolator(interpolator);
        mIdle = true;
    }

    /**
     * 显示礼物动画
     */
    public void show(ApiGiftSender bean, boolean isSameUser) {
        mIdle = false;
        boolean lian = true;
        if (!mShowed) {
            mShowed = true;
            if (mRoot != null && mRoot.getVisibility() != View.VISIBLE) {
                mRoot.setVisibility(View.VISIBLE);
            }
        }
        if (!isSameUser) {
            ImageLoader.display(bean.userAvatar, mAvatar, true);
            mName.setText(bean.userName);
            lian = false;
        }
        if (TextUtils.isEmpty(mLastGiftKey) || !mLastGiftKey.equals(String.valueOf(bean.userId + bean.giftId + bean.giftNumber))) {
            ImageLoader.display(bean.gifticon, mGiftIcon);
            mContent.setText(TextRender.renderGiftInfo2(bean.giftName));
            if (bean.giftNumber > 1) {
                mGiftGroupCount.setText("x" + bean.giftNumber);
                mMulSign.setText(R.string.live_gift_send_lian_3);
            } else {
                mGiftGroupCount.setText("");
                mMulSign.setText(R.string.live_gift_send_lian_2);
            }
            lian = false;
            if (mGiftCount != null && mGiftCount.getVisibility() == View.VISIBLE) {
                mGiftCount.setVisibility(View.INVISIBLE);
            }
            if (mAnimator != null) {
                mAnimator.start();
            }
        }
        if (lian) {
            mLianCount++;
        } else {
            mLianCount = 1;
        }
        if (mGiftCount != null && mGiftCount.getVisibility() == View.VISIBLE) {
            mGiftCount.setText(TextRender.renderGiftCount(mLianCount));
        }
        mLastGiftKey = String.valueOf(bean.userId + bean.giftId + bean.giftNumber);
        if (lian && mGiftCount != null && mAnimation != null) {
            mGiftCount.startAnimation(mAnimation);
        }
    }

    public void hide() {
        if (mBg != null) {
            mBg.setTranslationX(-mDp214);
        }
        if (mGiftCount != null && mGiftCount.getVisibility() == View.VISIBLE) {
            mGiftCount.setVisibility(View.INVISIBLE);
        }
        if (mRoot != null && mRoot.getVisibility() == View.VISIBLE) {
            mRoot.setVisibility(View.INVISIBLE);
        }
        mAvatar.setImageDrawable(null);
        mGiftIcon.setImageDrawable(null);
        mIdle = true;
        mShowed = false;
        mLastGiftKey = null;
    }

    /**
     * 是否是空闲的
     */
    public boolean isIdle() {
        return mIdle;
    }

    /**
     * 是否是同一种礼物，人的uid,礼物的id，礼物的个数都相同
     */
    public boolean isSameGift(ApiGiftSender bean) {
        return !TextUtils.isEmpty(mLastGiftKey) && mLastGiftKey.equals(String.valueOf(bean.userId + bean.giftId + bean.giftNumber));

    }

    public void cancelAnimAndHide() {
        cancelAnim();
        hide();
    }

    private void cancelAnim() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        if (mGiftCount != null) {
            mGiftCount.clearAnimation();
        }
    }

    public void release() {
        cancelAnim();
        mContext = null;
        mParentView = null;
        mLastGiftKey = null;
        mHandler = null;
    }

}
