package com.kalacheng.centercommon.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.commonview.fragment.ImageFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class PicBrowseFragment extends BaseFragment implements View.OnClickListener {
    private long userId;
    private List<ImageView> circularList;
    private int circularNum = 0;
    private LinearLayout llPoint, layoutLiveState, layoutSex;
    private TextView tvName, tvAge, tvSign, tvAddress, tvLiveState;
    private ImageView ivSex, ivGrade, ivWealthGrade, ivNobleGrade, ivEdit, ivLiveState, ivFollow;
    private ViewPager viewPager;
    private ApiUserInfo apiUserInfo;
    boolean first_pic_browse;
    private ProcessResultUtil mProcessResultUtil;
    private int isFollow;

    private LinearLayout ll_more;

    public PicBrowseFragment() {

    }

    public PicBrowseFragment(long userId) {
        this.userId = userId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pic_browse;
    }

    @Override
    protected void initView() {
        first_pic_browse = (boolean) SpUtil.getInstance().getSharedPreference(SpUtil.FIRST_PIC_BROWSE, true);
        if (first_pic_browse) {
            showFirstBrowseDialog();
        }
        llPoint = mParentView.findViewById(R.id.ll_point);
        tvName = mParentView.findViewById(R.id.tv_name);
        ivSex = mParentView.findViewById(R.id.ivSex);
        tvAge = mParentView.findViewById(R.id.tv_age);
        ivGrade = mParentView.findViewById(R.id.iv_grade);
        ivWealthGrade = mParentView.findViewById(R.id.ivWealthGrade);
        ivNobleGrade = mParentView.findViewById(R.id.ivNobleGrade);
        tvSign = mParentView.findViewById(R.id.tv_sgin);
        tvAddress = mParentView.findViewById(R.id.tv_address);
        ivEdit = mParentView.findViewById(R.id.iv_edit);
        ivFollow = mParentView.findViewById(R.id.ivFollow);
        ivFollow.setOnClickListener(this);
        tvLiveState = mParentView.findViewById(R.id.tv_live_state);
        layoutLiveState = mParentView.findViewById(R.id.layoutLiveState);
        layoutSex = mParentView.findViewById(R.id.layoutSex);
        ivLiveState = mParentView.findViewById(R.id.ivLiveState);
        ll_more = mParentView.findViewById(R.id.ll_more);
        ll_more.setOnClickListener(this);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        if (userId == HttpClient.getUid()) {
            ivFollow.setVisibility(View.GONE);
            ivEdit.setVisibility(View.VISIBLE);
            params2.setMargins(DpUtil.dp2px(15), DpUtil.dp2px(15), DpUtil.dp2px(15), DpUtil.dp2px(10));
            mParentView.findViewById(R.id.ll_location).setLayoutParams(params2);
        } else {
            ivFollow.setVisibility(View.VISIBLE);
            ivEdit.setVisibility(View.INVISIBLE);
            params2.setMargins(DpUtil.dp2px(15), DpUtil.dp2px(15), DpUtil.dp2px(15), DpUtil.dp2px(60));
            mParentView.findViewById(R.id.ll_location).setLayoutParams(params2);
        }
        mParentView.findViewById(R.id.icon_back).setOnClickListener(this);
        ivEdit.setOnClickListener(this);

        viewPager = mParentView.findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < circularNum; i++) {
                    if (i == arg0 % circularNum) {
                        circularList.get(i).setImageResource(R.drawable.banner_indicator_white);
                    } else {
                        circularList.get(i).setImageResource(R.drawable.banner_indicator_grey);
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
        mProcessResultUtil = new ProcessResultUtil(getActivity());

    }

    private void showFirstBrowseDialog() {
        final Dialog dialog = DialogUtil.getBaseDialog(getContext(), R.style.dialog, R.layout.dialog_pull_more, true, true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.findViewById(R.id.rl_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        window.setAttributes(params);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SpUtil.getInstance().put(SpUtil.FIRST_PIC_BROWSE, false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {
        HttpApiAppUser.personCenter(-1, -1, userId, new HttpApiCallBack<ApiUserInfo>() {
            @SuppressLint("ResourceType")
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    apiUserInfo = retModel;
                    circularNum = 0;
                    List<Fragment> mFragments = new ArrayList<>();
                    if (!TextUtils.isEmpty(retModel.portrait)) {
                        String[] strings = retModel.portrait.trim().split(",");
                        for (String str : strings) {
                            mFragments.add(new ImageFragment(str));
                            circularNum++;
                        }
                    }
                    if (!TextUtils.isEmpty(retModel.nobleGradeImg)) {
                        ImageLoader.display(retModel.nobleGradeImg, ivNobleGrade);
                    }
                    ImageLoader.display(retModel.wealthGradeImg, ivWealthGrade);
                    if (retModel.role == 0) {
                        ImageLoader.display(retModel.userGradeImg, ivGrade);
                    } else {
                        ImageLoader.display(retModel.anchorGradeImg, ivGrade);
                    }
                    tvSign.setText(!TextUtils.isEmpty(retModel.signature) ? retModel.signature : "这个家伙很懒,什么也没说...");
                    tvName.setText(retModel.username);
                    ivSex.setImageResource(retModel.sex == 2 ? R.mipmap.icon_girl_white : R.mipmap.icon_boy_white);
                    tvAge.setText(retModel.age + "");
                    layoutSex.setBackgroundResource(retModel.sex == 2 ? R.drawable.bg_sex_girl : R.drawable.bg_sex_boy);

                    tvAddress.setText(retModel.city);
                    if (circularNum != 0) {
                        setIndicate();
                        viewPager.setVisibility(View.VISIBLE);
                        mParentView.findViewById(R.id.rl_no_image).setVisibility(View.GONE);
                        FragmentAdapter mAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
                        viewPager.setAdapter(mAdapter);
                    } else {
                        viewPager.setVisibility(View.GONE);
                        mParentView.findViewById(R.id.rl_no_image).setVisibility(View.VISIBLE);
                    }
                    if (retModel.followStatus == 0) {
                        ivFollow.setImageResource(R.mipmap.icon_follow_no);
                    } else {
                        ivFollow.setImageResource(R.mipmap.icon_follow_yes);
                    }
                    if (retModel.showStatus == 0) {
                        layoutLiveState.setVisibility(View.GONE);
                        ivLiveState.setImageResource(R.drawable.darkgrey_oval);
                        tvLiveState.setText("离线");
                    } else if (retModel.showStatus == 1) {
                        layoutLiveState.setVisibility(View.VISIBLE);
                        ivLiveState.setImageResource(R.drawable.red_oval);
                        tvLiveState.setText("忙碌");
                    } else if (retModel.showStatus == 2) {
                        layoutLiveState.setVisibility(View.VISIBLE);
                        ivLiveState.setImageResource(R.drawable.green_oval);
                        tvLiveState.setText("在线");
                    } else if (retModel.showStatus == 3) {
                        layoutLiveState.setVisibility(View.VISIBLE);
                        ivLiveState.setImageResource(R.drawable.blue_oval);
                        tvLiveState.setText("通话中");
                    } else if (retModel.showStatus == 4) {
                        layoutLiveState.setVisibility(View.VISIBLE);
                        ivLiveState.setImageResource(R.drawable.white_oval);
                        tvLiveState.setText("看直播");
                    } else if (retModel.showStatus == 5) {
                        layoutLiveState.setVisibility(View.VISIBLE);
                        ivLiveState.setImageResource(R.drawable.white_oval);
                        tvLiveState.setText("匹配中");
                    } else if (retModel.showStatus == 6) {
                        layoutLiveState.setVisibility(View.VISIBLE);
                        ivLiveState.setImageResource(R.drawable.white_oval);
                        tvLiveState.setText("直播中");
                    } else if (retModel.showStatus == 7) {
                        layoutLiveState.setVisibility(View.VISIBLE);
                        ivLiveState.setImageResource(R.drawable.lightgrey_oval);
                        tvLiveState.setText("离开");
                    } else {
                        layoutLiveState.setVisibility(View.VISIBLE);
                        ivLiveState.setImageResource(R.drawable.red_oval);
                        tvLiveState.setText("忙碌");
                    }
                }
            }
        });
    }

    private void setIndicate() {
        llPoint.removeAllViews();
        circularList = new ArrayList<ImageView>();
        for (int i = 0; i < circularNum; i++) {
            ImageView imageView = new ImageView(getContext());
            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_indicator_white);
            } else {
                imageView.setImageResource(R.drawable.banner_indicator_grey);
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(20, 20);
            params.setMargins(5, 0, 5, 0);
            circularList.add(imageView);
            llPoint.addView(imageView, params);
        }
    }

    /**
     * 关注
     */
    private void setAttention() {
        if (null == apiUserInfo){
            return;
        }
        if (isFollow == 0) {
            isFollow = 1;
        } else {
            isFollow = 0;
        }
        HttpApiAppUser.set_atten(isFollow, apiUserInfo.userId, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (isFollow == 0) {
                        ivFollow.setImageResource(R.mipmap.icon_follow_no);
                    } else {
                        ivFollow.setImageResource(R.mipmap.icon_follow_yes);
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.icon_back) {
            getActivity().finish();
        } else if (view.getId() == R.id.iv_edit) {
            if (null != apiUserInfo) {
                ARouter.getInstance().build(ARouterPath.EidtInformation).withParcelable("UserInfoDto", apiUserInfo).navigation();
            }
        } else if (view.getId() == R.id.ivFollow) {
            setAttention();
        } else if (view.getId() == R.id.ll_more) {
            // 查看更多

        }
    }
}
