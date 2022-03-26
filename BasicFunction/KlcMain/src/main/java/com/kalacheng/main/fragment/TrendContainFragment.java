package com.kalacheng.main.fragment;

import android.Manifest;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.dynamiccircle.fragment.TrendFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.main.R;
import com.kalacheng.message.fragment.OnlineUserFragment;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.view.ViewPagerIndicator;
import com.kalacheng.commonview.activity.DynamicMakeActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 动态 / 广场
public class TrendContainFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private ViewPagerIndicator indicator;
    private ViewPager viewPager;
    private FragmentAdapter adapter;
    private PermissionsUtil mProcessResultUtil;

    public TrendContainFragment(Context mContext, ViewGroup mParentView) {
        this.mContext = mContext;

    }

    public TrendContainFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trend_contain;
    }

    @Override
    protected void initView() {
        mParentView.findViewById(R.id.tvDynamic).setOnClickListener(this);
        indicator = mParentView.findViewById(R.id.indicator);
        viewPager = mParentView.findViewById(R.id.viewPager);
    }

    @Override
    protected void initData() {
        mProcessResultUtil = new PermissionsUtil(getActivity());

        List<Fragment> fragments = new ArrayList<>();
        List<Class> list;
        List<String> strs;

        if (!ConfigUtil.getBoolValue(R.bool.squareIsOuterLayer)){
            mParentView.findViewById(R.id.ivFill).setVisibility(View.GONE);
            mParentView.findViewById(R.id.tvDynamic).setVisibility(View.GONE);
        }

        indicator.setVisibility(View.VISIBLE);
        list = new ArrayList(Arrays.asList(new Class[]{OnlineUserFragment.class, TrendFragment.class}));
        strs = new ArrayList(Arrays.asList(new String[]{"附近的人", "动态"}));

        indicator.setVisibleChildCount(list.size());
        for (int i = 0; i < list.size(); i++) {
            Class type = list.get(i);
            try {
                fragments.add((Fragment) type.newInstance());
            } catch (Exception e) {
                Log.i("Exception", e.getMessage());
            }
        }

        indicator.setTitles(strs.toArray(new String[]{}));
        indicator.setViewPager(viewPager);

        adapter = new FragmentAdapter(getChildFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvDynamic) {
            mProcessResultUtil.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
            }, new Runnable() {
                @Override
                public void run() {
                    HttpApiAPPAnchor.is_auth(2, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                if ("0".equals(retModel.no_use)) {
                                    DynamicMakeActivity.forward(TrendContainFragment.this.getActivity(), 0, 0, false, 1001);
                                } else if ("1".equals(retModel.no_use)) {
                                    if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.AUTH_IS_SEX, 1) == 0) {
                                        ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                                        if (apiUserInfo != null && apiUserInfo.sex == 2) {
                                            ARouter.getInstance().build(ARouterPath.ApplyAnchorActivity).navigation();
                                        } else {
                                            DialogUtil.showKnowDialog(getContext(), "暂时只支持小姐姐认证哦~", null);
                                        }
                                    } else {
                                        ARouter.getInstance().build(ARouterPath.ApplyAnchorActivity).navigation();
                                    }
                                } else {
                                    if (!TextUtils.isEmpty(msg)) {
                                        ToastUtil.show(msg);
                                    }
                                }
                            } else {
                                if (!TextUtils.isEmpty(msg)) {
                                    ToastUtil.show(msg);
                                }
                            }
                        }
                    });
                }
            });
        }
    }

}
