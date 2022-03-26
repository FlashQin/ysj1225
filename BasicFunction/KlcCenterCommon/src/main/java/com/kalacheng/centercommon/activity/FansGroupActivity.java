package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.commonview.adapter.FansGroupRankAdpater;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.FansInfoDto;
import com.kalacheng.libuser.model.RanksDto;
import com.kalacheng.util.utils.DialogUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 粉丝团
 */
@Route(path = ARouterPath.FansGroupActivity)
public class FansGroupActivity extends BaseActivity implements View.OnClickListener {
    RecyclerView recyclerViewRank;
    TextView tvFansNum, tvFansMoney, tvName, tvMoney;
    RefreshLayout refreshLayout;
    FansGroupRankAdpater fansGroupRankdpater;
    int pageIndex;
    double money;

    GroupInfo groupInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_group);
        initView();
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initData() {
        getFansInfo();
        getFansdData(true);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 0;
                getFansdData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getFansdData(false);

            }
        });

        HttpApiAppUser.getUserinfo(HttpClient.getUid(), new HttpApiCallBack<ApiUserInfo>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUserInfo retModel) {
                if (code == 1) {
                    JMessageClient.getGroupInfo(retModel.groupId, new GetGroupInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, GroupInfo groupInfo) {
                            if (groupInfo != null) {
                                FansGroupActivity.this.groupInfo = groupInfo;
//                                tvName.setText(groupInfo.getGroupName());
                            }
                        }
                    });
                }
            }
        });
    }

    private void getFansInfo() {
        HttpApiAPPAnchor.fansTeamInfo(HttpClient.getUid(), new HttpApiCallBack<FansInfoDto>() {
            @Override
            public void onHttpRet(int code, String msg, FansInfoDto retModel) {
                if (code == 1 && null != retModel) {
                    tvFansNum.setText(retModel.fansNum + "");
                    tvFansMoney.setText(DecimalFormatUtils.isIntegerDouble(retModel.totalCoin) + "");
                    tvName.setText(retModel.fansTeamName);
                    tvMoney.setText(DecimalFormatUtils.isIntegerDouble(retModel.coin) + SpUtil.getInstance().getCoinUnit() + "入团");
                    money = retModel.coin;
                }
            }
        });
    }

    private void getFansdData(final boolean isRefresh) {
        HttpApiAPPAnchor.getFansTeamRank(HttpClient.getUid(), pageIndex, HttpConstants.PAGE_SIZE, new HttpApiCallBackArr<RanksDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<RanksDto> retModel) {
                if (isRefresh) {
                    refreshLayout.finishRefresh();
                    fansGroupRankdpater.setRefreshData(retModel);
                } else {
                    refreshLayout.finishLoadMore();
                    fansGroupRankdpater.setLoadData(retModel);
                }
            }
        });

    }

    private void initView() {
        tvFansNum = findViewById(R.id.tv_fans_num);
        tvFansMoney = findViewById(R.id.tv_fans_money);
        tvName = findViewById(R.id.tv_name);
        tvMoney = findViewById(R.id.tv_money);
        recyclerViewRank = findViewById(R.id.recyclerView_rank);
        recyclerViewRank.setLayoutManager(new LinearLayoutManager(this));
        fansGroupRankdpater = new FansGroupRankAdpater(FansGroupActivity.this);
        recyclerViewRank.setAdapter(fansGroupRankdpater);
        findViewById(R.id.rl_name).setOnClickListener(this);
        findViewById(R.id.rl_money).setOnClickListener(this);
        setTitle("粉丝团");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_name) {
            DialogUtil.showSimpleInputDialog(FansGroupActivity.this, "修改粉丝团名称", "", "请输入修改粉丝团名称", true, DialogUtil.INPUT_TYPE_TEXT, 7, getResources().getColor(R.color.gray3), new DialogUtil.SimpleCallback() {
                @Override
                public void onConfirmClick() {
                }

                @Override
                public void onConfirmClick(final String input) {
                    if (TextUtils.isEmpty(input)) {
                        ToastUtil.show("名称不能为空");
                        return;
                    }
                    HttpApiAPPAnchor.setFansTeamInfo(money, input, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                ToastUtil.show("修改成功");
                                getFansInfo();
                                if (groupInfo != null) {
                                    groupInfo.updateName(input, new BasicCallback() {
                                        @Override
                                        public void gotResult(int i, String s) {
                                            Log.e("fans>>>", s);
                                        }
                                    });
                                }
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
        } else if (view.getId() == R.id.rl_money) {
            DialogUtil.showSimpleInputDialog(FansGroupActivity.this, "修改入团金额", "", "请输入入团金额", true, DialogUtil.INPUT_TYPE_NUMBER, 5, getResources().getColor(R.color.gray3), new DialogUtil.SimpleCallback() {
                @Override
                public void onConfirmClick() {
                }

                @Override
                public void onConfirmClick(final String input) {
                    if (TextUtils.isEmpty(input)) {
                        ToastUtil.show("价格不能为空");
                        return;
                    }
                    money = Double.parseDouble(input);
                    HttpApiAPPAnchor.setFansTeamInfo(money, tvName.getText().toString().trim(), new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                ToastUtil.show("修改成功");
                                getFansInfo();
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
