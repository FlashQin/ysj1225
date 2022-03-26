package com.kalacheng.message.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.ApiNoRead;
import com.kalacheng.message.R;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.util.utils.L;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewAllFragment extends BaseFragment {

    private int commentType = 0; // 评论类型 0 = 动态   1 = 短视频
    private List<String> strs = new ArrayList(Arrays.asList(new String[]{"动态评论", "短视频评论"}));

    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private TextView allCountTv;

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public ReviewAllFragment() {
    }

    public ReviewAllFragment(int commentType) {
        this.commentType = commentType;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_review_all;
    }

    @Override
    protected void initView() {
        viewPager = mParentView.findViewById(R.id.viewPager);
        allCountTv = mParentView.findViewById(R.id.allCountTv);
    }

    @Override
    public void initData() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ReviewListFragment(1));
        fragments.add(new ReviewListFragment(2));

        initMagicIndicator();

        adapter = new FragmentAdapter(getChildFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                L.e("fragment ->" + position);
                Fragment newFragment = adapter.getItem(position);
                if (newFragment instanceof BaseFragment) {
                    ((BaseFragment) newFragment).onResumeFragment();
                } else if (newFragment instanceof BaseMVVMFragment) {
                    ((BaseMVVMFragment) newFragment).onResumeFragment();
                }

                Fragment oldFragment = adapter.getItem(currentPosition);
                if (oldFragment instanceof BaseFragment) {
                    ((BaseFragment) oldFragment).onPauseFragment();
                } else if (oldFragment instanceof BaseMVVMFragment) {
                    ((BaseMVVMFragment) oldFragment).onPauseFragment();
                }
                currentPosition = position;
                if (currentPosition == 1) {
                    allCountTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (commentType < 2) {
            viewPager.setCurrentItem(commentType);
        }

        getData();

    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = (MagicIndicator) mParentView.findViewById(R.id.indicator);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return strs == null ? 0 : strs.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView simplePagerTitleView = new CommonPagerTitleView(context);
                simplePagerTitleView.setContentView(R.layout.simple_pager_title_layout);
//                simplePagerTitleView.setText(strs.get(index));
//                simplePagerTitleView.setNormalColor(Color.parseColor("#222222"));
//                simplePagerTitleView.setSelectedColor(Color.parseColor("#FD64FB"));
                final TextView titleText = (TextView) simplePagerTitleView.findViewById(R.id.title_text);
                titleText.setText(strs.get(index));

                simplePagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(Color.parseColor("#FD64FB"));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(Color.parseColor("#222222"));

                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 15));
                indicator.setColors(Color.parseColor("#B540FF"));
                return null;
            }

//            @Override
//            public float getTitleWeight(Context context, int index) {
//                if (index == 0) {
//                    return 2.0f;
//                } else if (index == 1) {
//                    return 1.2f;
//                } else {
//                    return 1.0f;
//                }
//            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    /**
     * 动态回复消息列表
     */
    private void getData() {
        HttpApiChatRoom.getAppSystemNoRead(new HttpApiCallBack<ApiNoRead>() {
            @Override
            public void onHttpRet(int code, String msg, ApiNoRead retModel) {
                if (code == 1 && retModel != null) {
                    if (retModel.shortVideoNoRead > 0 && commentType == 0) {
                        allCountTv.setVisibility(View.VISIBLE);
                    } else {
                        allCountTv.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

}
