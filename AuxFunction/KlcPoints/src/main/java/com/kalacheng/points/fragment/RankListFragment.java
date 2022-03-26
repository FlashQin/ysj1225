package com.kalacheng.points.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.points.adpater.RankFragmentAdapter;
import com.kalacheng.points.viewmodel.RankListViewModel;
import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.points.R;
import com.kalacheng.points.databinding.FragmentRanklistBinding;
import com.kalacheng.util.utils.ConfigUtil;

import java.util.ArrayList;
import java.util.List;

/*
 * 排行榜
 * */
public class RankListFragment extends BaseMVVMFragment<FragmentRanklistBinding, RankListViewModel> {

    private Context mContext;
    private ViewGroup mParentView;
    private View view;

    private List<Fragment> fragmentList;

    public RankListFragment() {
    }
    public RankListFragment(Context mContext, ViewGroup mParentView) {
        super(mContext, mParentView);
        this.mContext = mContext;
        this.mParentView = mParentView;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_ranklist;
    }

    @Override
    public void initData() {
        getFragment();
    }

    public void getFragment() {
        fragmentList = new ArrayList<>();
        RankProfitListFragment profitListFragment = new RankProfitListFragment(mContext, mParentView);
        fragmentList.add(profitListFragment);
        RankContributionListFragment profitListFragment1 = new RankContributionListFragment(mContext, mParentView);
        fragmentList.add(profitListFragment1);
        if (ConfigUtil.getBoolValue(R.bool.isVisibleRankFamily)){
            RankFamilyListFragment rankFamilyListFragment = new RankFamilyListFragment(mContext, mParentView);
            fragmentList.add(rankFamilyListFragment);
            LiveConstants.RANKTITLE = new String[]{"收益榜", "贡献榜", "家族榜"};
            binding.indicator.setVisibleChildCount(3);
        }
        binding.indicator.setTitles(LiveConstants.RANKTITLE);

        binding.indicator.setViewPager(binding.viewPager);
        RankFragmentAdapter adapter = new RankFragmentAdapter(getChildFragmentManager());
        binding.viewPager.setAdapter(adapter);
        adapter.loadData(fragmentList);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0 || position == 2) {
                    binding.layoutBg.setBackgroundResource(R.mipmap.bg_ranklist);
                } else {
                    binding.layoutBg.setBackgroundResource(R.mipmap.bg_contribution);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}
