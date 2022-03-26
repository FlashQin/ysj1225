package com.kalacheng.live.component.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.viewpager.widget.ViewPager;

import com.kalacheng.live.component.adapter.LiveFunctionPagerAdapter;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.live.R;
import com.kalacheng.live.component.ComponentConfig;
import com.kalacheng.util.utils.DpUtil;

/**
 * Created by cxf on 2018/10/9.
 */
//@Route(path = ARouterPath.LiveFunctionDialogFragment)
public class LiveFunctionDialogFragment extends BaseDialogFragment {
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private LiveFunctionPagerAdapter liveFunctionPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_live_function;
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
        params.y = DpUtil.dp2px(60);
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mRadioGroup != null) {
                    ((RadioButton) mRadioGroup.getChildAt(position)).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.radio_group);
        liveFunctionPagerAdapter = new LiveFunctionPagerAdapter(mContext, ComponentConfig.FUNCTIONMENU);
        mViewPager.setAdapter(liveFunctionPagerAdapter);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i = 0, size = liveFunctionPagerAdapter.getCount(); i < size; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.view_gift_indicator, mRadioGroup, false);
            radioButton.setBackgroundResource(R.drawable.bg_live_function_indicator);
            radioButton.setId(i + 20000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            mRadioGroup.addView(radioButton);
        }
        if (liveFunctionPagerAdapter.getCount() > 1) {
            mRadioGroup.setVisibility(View.VISIBLE);
        } else {
            mRadioGroup.setVisibility(View.GONE);
        }

        LiveBundle.getInstance().addSimpleMsgListener("LM_DialogDismiss", new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                dismissAllowingStateLoss();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
