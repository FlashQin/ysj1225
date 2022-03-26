package com.kalacheng.shortvideo.activity;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.libuser.model.ApiMyShortVideo;
import com.kalacheng.shortvideo.fragment.VideoListFragment;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.shortvideo.R;
import com.kalacheng.util.utils.CheckDoubleClick;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的视图 fragment (作品 / 喜欢 / 购买)
 */
public class MyViewFragment extends BaseFragment implements View.OnClickListener {
    private long toUid;
    private boolean enableRefresh;

    private TextView tvWorks;
    private TextView tvLikes;
    private TextView tvPayment;
    private View lineWorks;
    private View lineLikes;
    private View linePay;
    private ViewPager viewPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();

    public MyViewFragment() {

    }

    public MyViewFragment(Long toUid) {
        this.toUid = toUid;
    }

    public MyViewFragment(Long toUid, boolean enableRefresh) {
        this.toUid = toUid;
        this.enableRefresh = enableRefresh;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_view;
    }

    @Override
    protected void initView() {
        tvWorks = mParentView.findViewById(R.id.tv_works);
        tvLikes = mParentView.findViewById(R.id.tv_likes);
        tvPayment = mParentView.findViewById(R.id.tv_payment);
        lineWorks = mParentView.findViewById(R.id.line_works);
        lineLikes = mParentView.findViewById(R.id.line_likes);
        linePay = mParentView.findViewById(R.id.line_payment);
        viewPager = mParentView.findViewById(R.id.viewPager);
        tvWorks.setSelected(true);
        mParentView.findViewById(R.id.ll_works).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_likes).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_payment).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getUserVideo();

        mFragments.add(new VideoListFragment(1, toUid, enableRefresh));
        mFragments.add(new VideoListFragment(2, toUid, enableRefresh));
        mFragments.add(new VideoListFragment(3, toUid, enableRefresh));
        mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                changeVideoTitle(arg0);
                for (int i = 0; i < mFragments.size(); i++) {
                    if (i == arg0) {
                        if (mFragments.get(i) instanceof BaseFragment) {
                            ((BaseFragment) mFragments.get(i)).setShowed(true);
                        }
                    } else {
                        if (mFragments.get(i) instanceof BaseFragment) {
                            ((BaseFragment) mFragments.get(i)).setShowed(false);
                        }
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.ll_works) {
            if (!tvWorks.isSelected()) {
                changeVideoTitle(0);
                viewPager.setCurrentItem(0);
            }
        } else if (view.getId() == R.id.ll_likes) {
            if (!tvLikes.isSelected()) {
                changeVideoTitle(1);
                viewPager.setCurrentItem(1);
            }
        } else if (view.getId() == R.id.ll_payment) {
            if (!tvPayment.isSelected()) {
                changeVideoTitle(2);
                viewPager.setCurrentItem(2);
            }
        }
    }

    /**
     * 改变作品状况样式
     */
    private void changeVideoTitle(int position) {
        if (position == 0) {
            tvWorks.setSelected(true);
            tvLikes.setSelected(false);
            tvPayment.setSelected(false);
            lineWorks.setVisibility(View.VISIBLE);
            lineLikes.setVisibility(View.INVISIBLE);
            linePay.setVisibility(View.INVISIBLE);
        } else if (position == 1) {
            tvWorks.setSelected(false);
            tvLikes.setSelected(true);
            tvPayment.setSelected(false);
            lineWorks.setVisibility(View.INVISIBLE);
            lineLikes.setVisibility(View.VISIBLE);
            linePay.setVisibility(View.INVISIBLE);
        } else if (position == 2) {
            tvWorks.setSelected(false);
            tvLikes.setSelected(false);
            tvPayment.setSelected(true);
            lineWorks.setVisibility(View.INVISIBLE);
            lineLikes.setVisibility(View.INVISIBLE);
            linePay.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取作品数
     */
    private void getUserVideo() {
        HttpApiAppShortVideo.getUserShortVideoList(20, toUid, new HttpApiCallBack<ApiMyShortVideo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiMyShortVideo retModel) {
                if (code == 1 && retModel != null) {
                    tvWorks.setText("作品 " + retModel.myNumber);
                    tvLikes.setText("喜欢 " + retModel.likeNumber);
                    tvPayment.setText("购买 " + retModel.buyNumber);
                }
            }
        });
    }
}
