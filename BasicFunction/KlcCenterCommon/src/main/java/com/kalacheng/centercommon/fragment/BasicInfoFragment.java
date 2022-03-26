package com.kalacheng.centercommon.fragment;

import android.app.Dialog;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buscommon.model.TabInfoDto;
import com.kalacheng.buscommon.model.TabTypeDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.InterestTagAdpater;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.util.adapter.SimpleTextAdapter;
import com.kalacheng.util.bean.SimpleTextBean;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ProcessResultUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人资料
 */
public class BasicInfoFragment extends BaseFragment implements View.OnClickListener {
    long userId;
    LinearLayout llTag, llAccount;
    ApiUserInfo apiUserInfo;
    RecyclerView recyclerViewPerson;
    SimpleTextAdapter simpleTextAdapter;
    TextView tvPhone, tvPhoneClick, tvWx, tvWxClick;
    ProcessResultUtil mProcessResultUtil;

    public BasicInfoFragment() {

    }

    public BasicInfoFragment(Long userId) {
        this.userId = userId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basic_info;
    }

    @Override
    protected void initView() {
        llTag = mParentView.findViewById(R.id.ll_tag);
        llAccount = mParentView.findViewById(R.id.ll_account);
        tvPhone = mParentView.findViewById(R.id.tv_phone);
        tvPhoneClick = mParentView.findViewById(R.id.tv_phone_click);
        tvWx = mParentView.findViewById(R.id.tv_wx);
        tvWxClick = mParentView.findViewById(R.id.tv_wx_click);
        recyclerViewPerson = mParentView.findViewById(R.id.recyclerView_personinfo);
        recyclerViewPerson.setLayoutManager(new LinearLayoutManager(getContext()));

        mParentView.findViewById(R.id.ll_phone).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_wewhat).setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initData() {
        mProcessResultUtil = new ProcessResultUtil(getActivity());
        HttpApiAppUser.personCenter(-1, -1, userId, new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1 && null != retModel) {
                    apiUserInfo = retModel;
                    List<SimpleTextBean> list = new ArrayList<>();
                    if (TextUtils.isEmpty(retModel.goodnum)) {
                        list.add(new SimpleTextBean("ID:  " + retModel.userId));
                    } else {
                        list.add(new SimpleTextBean("靓号:  " + retModel.goodnum));
                    }
                    if (retModel.height>0){
                        list.add(new SimpleTextBean("身高:  " + retModel.height + "cm"));
                    }else {
                        list.add(new SimpleTextBean("身高:  " + "未设置"));
                    }
                    if (retModel.weight>0){
                        list.add(new SimpleTextBean("体重:  " + retModel.weight + "kg"));
                    }else {
                        list.add(new SimpleTextBean("体重:  " + "未设置"));
                    }

                    list.add(new SimpleTextBean("职业:  " + retModel.vocation));
                    list.add(new SimpleTextBean("星座:  " + retModel.constellation));
                    if (null == simpleTextAdapter) {
                        simpleTextAdapter = new SimpleTextAdapter(list);
                        recyclerViewPerson.setAdapter(simpleTextAdapter);
                    } else {
                        simpleTextAdapter.setData(list);
                    }
                    if (null != retModel.interestList && !retModel.interestList.isEmpty()) {
                        llTag.removeAllViews();
                        for (TabTypeDto tabTypeDto : retModel.interestList) {
                            View tagTitle = LayoutInflater.from(getContext()).inflate(R.layout.layout_right_title, null);
                            LinearLayout llText = tagTitle.findViewById(R.id.ll_text);
                            llText.setBackgroundColor(getResources().getColor(R.color.white));
                            llTag.addView(tagTitle);
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tagTitle.getLayoutParams();
                            layoutParams.height = DpUtil.dp2px(35);
                            TextView text = tagTitle.findViewById(R.id.text);
                            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) text.getLayoutParams();
                            layoutParams1.setMargins(DpUtil.dp2px(10), 0, 0, 0);
                            text.getPaint().setFakeBoldText(true);
                            text.setText(tabTypeDto.name);
                            RecyclerView recyclerView = new RecyclerView(getContext());
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
                            List<TabInfoDto> infoDtoList;
                            if (null == tabTypeDto.tabInfoList) {
                                infoDtoList = new ArrayList<>();
                            } else {
                                infoDtoList = tabTypeDto.tabInfoList;
                            }
                            InterestTagAdpater adpater = new InterestTagAdpater(infoDtoList);
                            recyclerView.setAdapter(adpater);
                            llTag.addView(recyclerView);
                            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
                            layoutParams2.setMargins(DpUtil.dp2px(10), -DpUtil.dp2px(5), 0, 0);
                        }
                    } else {
                        mParentView.findViewById(R.id.tv_tag).setVisibility(View.VISIBLE);
                    }
                    if (ConfigUtil.getBoolValue(R.bool.containOne2One)) {
                        if (retModel.role == 0) {
                            llAccount.setVisibility(View.GONE);
                        } else {
                            //llAccount.setVisibility(View.VISIBLE);
                        }
                        if ("-1".equals(retModel.wechatNo)) {
                            tvWx.setText("未绑定微信号");
                            tvWxClick.setVisibility(View.GONE);
                        } else if (!"-2".equals(retModel.wechatNo) || userId == HttpClient.getUid()) {
                            tvWx.setText("微信号: " + retModel.wechatNo);
                            tvWxClick.setVisibility(View.GONE);
                        }
                        if ("-1".equals(retModel.mobile)) {
                            tvPhone.setText("未绑定手机号");
                            tvPhoneClick.setVisibility(View.GONE);
                        } else if (!"-2".equals(retModel.mobile) || userId == HttpClient.getUid()) {
                            tvPhone.setText("手机号: " + retModel.mobile);
                            tvPhoneClick.setVisibility(View.GONE);
                        }

                    } else {
                        llAccount.setVisibility(View.GONE);
                    }
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ll_phone) {
            if (tvPhoneClick.getVisibility() == View.VISIBLE) {
                showBrowseDialog(1, apiUserInfo.mobileCoin);
            }
        } else if (view.getId() == R.id.ll_wewhat) {
            if (tvWxClick.getVisibility() == View.VISIBLE) {
                showBrowseDialog(2, apiUserInfo.wechatCoin);
            }
        }
    }

    private void showBrowseDialog(final int type, final double coin) {
        String info;
        if (type == 1) {
            info = "获取TA的手机号需支付" + coin + SpUtil.getInstance().getCoinUnit() + ",是否继续支付？";
        } else {
            info = "获取TA的微信号需支付" + coin + SpUtil.getInstance().getCoinUnit() + ",是否继续支付？";
        }
        DialogUtil.showSimpleDialog(getContext(), "提示", info, true, new DialogUtil.SimpleCallback() {
            @Override
            public void onConfirmClick() {
                HttpApiAppUser.payViewContact(type, userId, new HttpApiCallBack<SingleString>() {
                    @Override
                    public void onHttpRet(int code, String msg, SingleString retModel) {
                        if (code == 1) {
                            if (type == 1) {
                                tvPhone.setText("手机号: " + retModel.value);
                                tvPhoneClick.setVisibility(View.GONE);
                            } else if (type == 2) {
                                tvWx.setText("微信号: " + retModel.value);
                                tvWxClick.setVisibility(View.GONE);
                            }
                        } else if (code == -1) {
                            showReChargeDiaLog(coin);
                        } else {
                            if (!TextUtils.isEmpty(msg)) {
                                ToastUtil.show(msg);
                            }
                        }
                    }
                });
            }

            @Override
            public void onConfirmClick(String input) {

            }

            @Override
            public void onCancelClick() {

            }
        });
    }

    private void showReChargeDiaLog(double coin) {
        final Dialog dialog = DialogUtil.getBaseDialog(getContext(), R.style.dialog, R.layout.dialog_homepage_recharge, true, true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);//宽高最大
        TextView textView = dialog.findViewById(R.id.text);
        textView.setText("你的余额不够" + coin + SpUtil.getInstance().getCoinUnit());
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.9f;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        dialog.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
