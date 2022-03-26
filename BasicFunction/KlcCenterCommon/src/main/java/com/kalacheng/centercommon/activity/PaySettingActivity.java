package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.adapter.FragmentAdapter;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.fragment.PayPhoneFragment;
import com.kalacheng.centercommon.fragment.PayWxFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.CfgContactViewPrice;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 付费设置
 */
@Route(path = ARouterPath.PaySettingActivity)
public class PaySettingActivity extends BaseActivity implements View.OnClickListener {
    ViewPagerIndicator indicator;
    private View viewDivider0;
    private View viewDivider1;
    ViewPager viewPager;
    TextView tvPhonePrice, tvWxPrice;
    private ImageView ivPhone;
    private ImageView ivWx;
    int statePhone, stateWx;
    double phonePrice, wxPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_setting);
        initView();
        initData();

    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView() {
        viewDivider0 = findViewById(R.id.viewDivider0);
        viewDivider1 = findViewById(R.id.viewDivider1);
        indicator = findViewById(R.id.indicator);
        tvPhonePrice = findViewById(R.id.tv_phone_price);
        tvWxPrice = findViewById(R.id.tv_wx_price);
        viewPager = findViewById(R.id.viewPager);
        ivPhone = findViewById(R.id.ivPhone);
        ivWx = findViewById(R.id.ivWx);
        ivPhone.setOnClickListener(this);
        ivWx.setOnClickListener(this);
        findViewById(R.id.rl_phone).setOnClickListener(this);
        findViewById(R.id.rl_wx).setOnClickListener(this);
        setTitle("付费设置");
    }

    public void initData() {
        getFragment();
        HttpApiAppUser.getViewContactPrice(new HttpApiCallBackArr<CfgContactViewPrice>() {
            @Override
            public void onHttpRet(int code, String msg, List<CfgContactViewPrice> retModel) {
                if (code == 1 && null != retModel) {
                    for (CfgContactViewPrice contactViewPrice : retModel) {
                        if (contactViewPrice.type == 1) {
                            tvPhonePrice.setText(contactViewPrice.price + "");
                            if (contactViewPrice.state == 1) {
                                ivPhone.setImageResource(R.mipmap.icon_switch_open);
                                ivPhone.setSelected(true);
                            }
                            phonePrice = contactViewPrice.price;
                            statePhone = contactViewPrice.state;
                        } else if (contactViewPrice.type == 2) {
                            tvWxPrice.setText(contactViewPrice.price + "");
                            if (contactViewPrice.state == 1) {
                                ivWx.setImageResource(R.mipmap.icon_switch_open);
                                ivWx.setSelected(true);
                            }
                            wxPrice = contactViewPrice.price;
                            stateWx = contactViewPrice.state;
                        }
                    }
                }
            }
        });
    }

    public void getFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        PayPhoneFragment payPhoneFragment = new PayPhoneFragment();
        fragmentList.add(payPhoneFragment);
        PayWxFragment payWxFragment = new PayWxFragment();
        fragmentList.add(payWxFragment);
        indicator.setTitles(LiveConstants.PAYSETTITLE);
        indicator.setViewPager(viewPager);
        FragmentAdapter adpater = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adpater);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    viewDivider0.setVisibility(View.VISIBLE);
                    viewDivider1.setVisibility(View.INVISIBLE);
                } else {
                    viewDivider0.setVisibility(View.INVISIBLE);
                    viewDivider1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivPhone) {
//                 * 设置查看账号的费用
//                        * @param price 联系方式类型 1：手机号  2：微信号， 3：直播回放价格
//                        * @param state 是否开启收费 0：不开启  1：开启
//                        * @param type 联系方式类型 1：手机号  2：微信号， 3：直播回放
            HttpApiAppUser.setViewContactPrice(phonePrice, ivPhone.isSelected() ? 0 : 1, 1, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        ivPhone.setSelected(!ivPhone.isSelected());
                        if (ivPhone.isSelected()) {
                            statePhone = 1;
                            ivPhone.setImageResource(R.mipmap.icon_switch_open);
                        } else {
                            statePhone = 0;
                            ivPhone.setImageResource(R.mipmap.icon_switch_close);
                        }
                    }
                    ToastUtil.show(msg);
                }
            });
        } else if (view.getId() == R.id.ivWx) {
            HttpApiAppUser.setViewContactPrice(wxPrice, ivWx.isSelected() ? 0 : 1, 2, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        ivWx.setSelected(!ivWx.isSelected());
                        if (ivWx.isSelected()) {
                            stateWx = 1;
                            ivWx.setImageResource(R.mipmap.icon_switch_open);
                        } else {
                            ivWx.setImageResource(R.mipmap.icon_switch_close);
                            stateWx = 0;
                        }
                    }
                    ToastUtil.show(msg);
                }
            });
        } else if (view.getId() == R.id.rl_phone) {
            DialogUtil.showSimpleInputDialog(this, "修改获取手机号金额", "", "请输入修改获取手机号金额", true, DialogUtil.INPUT_TYPE_NUMBER, 5, getResources().getColor(R.color.gray3), new DialogUtil.SimpleCallback() {
                @Override
                public void onConfirmClick() {

                }

                @Override
                public void onConfirmClick(final String input) {
                    if (null == input || TextUtils.isEmpty(input.trim())) {
                        ToastUtil.show("输入的金额不能为空");
                        return;
                    }
                    phonePrice = Double.parseDouble(input);
                    HttpApiAppUser.setViewContactPrice(phonePrice, statePhone, 1, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                ToastUtil.show("设置成功");
                                tvPhonePrice.setText(input);
                            } else {
                                ToastUtil.show(msg);
                            }
                        }
                    });
                }

                @Override
                public void onCancelClick() {

                }
            });
        } else if (view.getId() == R.id.rl_wx) {
            DialogUtil.showSimpleInputDialog(this, "修改获取微信号金额", "", "请输入修改获取微信号金额", true, DialogUtil.INPUT_TYPE_NUMBER, 5, getResources().getColor(R.color.gray3), new DialogUtil.SimpleCallback() {
                @Override
                public void onConfirmClick() {

                }

                @Override
                public void onConfirmClick(final String input) {
                    if (null == input || TextUtils.isEmpty(input.trim())) {
                        ToastUtil.show("输入的金额不能为空");
                        return;
                    }
                    wxPrice = Double.parseDouble(input);
                    HttpApiAppUser.setViewContactPrice(wxPrice, stateWx, 2, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                ToastUtil.show("设置成功");
                                tvWxPrice.setText(input);
                            } else {
                                ToastUtil.show(msg);
                            }
                        }
                    });
                }

                @Override
                public void onCancelClick() {

                }
            });
        }
    }
}
