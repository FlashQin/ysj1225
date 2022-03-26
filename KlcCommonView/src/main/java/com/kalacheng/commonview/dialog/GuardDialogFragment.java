package com.kalacheng.commonview.dialog;

import android.content.DialogInterface;
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
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.UserFragmentAdpater;
import com.kalacheng.commonview.fragment.OpenGuardFragment;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.util.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class GuardDialogFragment extends DialogFragment {

    private GuardDialogListener guardDialogListener;
    private int type;//0 直播间内；1 直播间外
    private long anchorId;//主播ID
    private String anchorAvatar;//主播头像
    private String anchorName;//主播昵称

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        type = getArguments().getInt("type");
        anchorId = getArguments().getLong("anchorId");
        anchorAvatar = getArguments().getString("anchorAvatar");
        anchorName = getArguments().getString("anchorName");

        final View view = inflater.inflate(R.layout.guard_dialog, null, false);
        List<Fragment> fragmentList = new ArrayList<>();
        final ViewPager mViewPager = view.findViewById(R.id.fan_contribution_viewpager);

        final ViewPagerIndicator Indicator = view.findViewById(R.id.Indicator);

        if (type == 0) {
            if (anchorId == HttpClient.getUid()) {
                LiveGuardListFragment userContributionDialogFragment = new LiveGuardListFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", type);
                bundle1.putLong("anchorId", anchorId);
                bundle1.putString("anchorAvatar", anchorAvatar);
                bundle1.putString("anchorName", anchorName);
                userContributionDialogFragment.setArguments(bundle1);
                fragmentList.add(userContributionDialogFragment);
                Indicator.setVisibleChildCount(1);
                Indicator.setTitles(LiveConstants.ANCHOR_GUARD);
            } else {
                OpenGuardFragment openGuardFragment = new OpenGuardFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", type);
                bundle.putLong("anchorId", anchorId);
                bundle.putString("anchorAvatar", anchorAvatar);
                bundle.putString("anchorName", anchorName);
                openGuardFragment.setArguments(bundle);
                fragmentList.add(openGuardFragment);

                LiveGuardListFragment userContributionDialogFragment = new LiveGuardListFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", type);
                bundle1.putLong("anchorId", anchorId);
                bundle1.putString("anchorAvatar", anchorAvatar);
                bundle1.putString("anchorName", anchorName);
                userContributionDialogFragment.setArguments(bundle1);
                fragmentList.add(userContributionDialogFragment);
                Indicator.setVisibleChildCount(2);
                Indicator.setTitles(LiveConstants.GUARD);
            }
        } else {
            OpenGuardFragment openGuardFragment = new OpenGuardFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);
            bundle.putLong("anchorId", anchorId);
            bundle.putString("anchorAvatar", anchorAvatar);
            bundle.putString("anchorName", anchorName);
            openGuardFragment.setArguments(bundle);
            fragmentList.add(openGuardFragment);
            Indicator.setVisibleChildCount(1);
            Indicator.setTitles(LiveConstants.OPEN_GUARD);
        }

        Indicator.setViewPager(mViewPager);

        UserFragmentAdpater userFragmentAdpater = new UserFragmentAdpater(getChildFragmentManager());
        userFragmentAdpater.loadData(fragmentList);
        mViewPager.setAdapter(userFragmentAdpater);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog);

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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (null != guardDialogListener){
            guardDialogListener.onDismiss();
        }
    }

    public void GuardDialogListener(GuardDialogListener guardListener){
        this.guardDialogListener = guardListener;
    }

    public interface GuardDialogListener {
        void onDismiss();
    }

}
