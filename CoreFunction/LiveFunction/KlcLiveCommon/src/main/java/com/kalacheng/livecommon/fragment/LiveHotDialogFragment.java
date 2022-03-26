package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.commonview.adapter.UserFragmentAdpater;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播 收益榜（今日收益榜、累计收益榜）点击火力值
 */
public class LiveHotDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().setCanceledOnTouchOutside(true);//设置点击外部Dialog消失
        View view =inflater.inflate(R.layout.live_hot_dialog,null,false);
        List<Fragment> fragmentList = new ArrayList<>();
        ViewPager mViewPager = view.findViewById(R.id.livehot_viewpager);

        ViewPagerIndicator Indicator = view.findViewById(R.id.indicator);

        //今天收益榜
        TodayProfitDialogFragment todayProfitDialogFragment = new TodayProfitDialogFragment();
        fragmentList.add(todayProfitDialogFragment);

        //累计收益榜
        TotalProfitDialogFragment totalProfitDialogFragment = new TotalProfitDialogFragment();
        fragmentList.add(totalProfitDialogFragment);

        Indicator.setTitles(LiveConstants.HOTVALUE);
        Indicator.setViewPager(mViewPager);

        UserFragmentAdpater userFragmentAdpater = new UserFragmentAdpater(getChildFragmentManager());
        userFragmentAdpater.loadData(fragmentList);
        mViewPager.setAdapter(userFragmentAdpater);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog2);

    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

    }
}
