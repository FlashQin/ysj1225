package com.kalacheng.commonview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;


/**
 * Created by cxf on 2017/8/25.
 * 弹幕
 */

public class DanmuViewHolder extends BaseViewHolder {

    private static final float SPEED = 0.2f;//弹幕的速度，这个值越小，弹幕走的越慢
    private static final int MARGIN_TOP = DpUtil.dp2px(150);
    private static final int SPACE = DpUtil.dp2px(50);
    private static final int DP_15 = DpUtil.dp2px(15);
    private RoundedImageView mAvatar;
    private TextView mName;
    private TextView mContent;
    private int mScreenWidth;//屏幕宽度
    private int mWidth;//控件的宽度
    private ValueAnimator mAnimator;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;
    private boolean mCanNext;//是否可以有下一个
    private boolean mIdle;//是否空闲
    private ActionListener mActionListener;
    private int mLineNum;

    public DanmuViewHolder(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_gift_danmu;
    }

    @Override
    public void init() {
        mAvatar = (RoundedImageView) findViewById(R.id.avatar);
        mName = (TextView) findViewById(R.id.name);
        mContent = (TextView) findViewById(R.id.content);
        mScreenWidth = DpUtil.getScreenWidth();
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                mContentView.setX(v);
                if (!mCanNext && v <= mScreenWidth - mWidth - DP_15) {
                    mCanNext = true;
                    if (mActionListener != null) {
                        mActionListener.onCanNext(mLineNum);
                    }
                }
            }

        };
        mAnimatorListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeFromParent();
                mIdle = true;
                if (mActionListener != null) {
                    mActionListener.onAnimEnd(DanmuViewHolder.this);
                }
            }
        };
    }

    public void show(ApiSimpleMsgRoom bean, int lineNum) {
        mLineNum = lineNum;
        ImageLoader.display(bean.avatar, mAvatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        mName.setText(bean.uname);
//        LevelBean levelBean = AppConfig.getInstance().getLevel(bean.getLevel());
//        if (levelBean != null) {
//            mName.setTextColor(Color.parseColor(levelBean.getColor()));
//        }
//        mName.setTextColor(Color.GREEN);
        mContent.setText(bean.content);
        mCanNext = false;
        mContentView.measure(0, 0);
        mWidth = mContentView.getMeasuredWidth();
        mContentView.setX(mScreenWidth);
        mContentView.setY(MARGIN_TOP + lineNum * SPACE);
        addToParent();
        mAnimator = ValueAnimator.ofFloat(mScreenWidth, -mWidth);
        mAnimator.addUpdateListener(mUpdateListener);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration((int) ((mScreenWidth + mWidth) / SPEED));
        mAnimator.addListener(mAnimatorListener);
        mAnimator.start();
    }

    public boolean isIdle() {
        return mIdle;
    }

    public void setIdle(boolean idle) {
        mIdle = idle;
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public void release() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        removeFromParent();
        mActionListener = null;
    }

    public interface ActionListener {
        void onCanNext(int lineNum);

        void onAnimEnd(DanmuViewHolder vh);
    }
}
