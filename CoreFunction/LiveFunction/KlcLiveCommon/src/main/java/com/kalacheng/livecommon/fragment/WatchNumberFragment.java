package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.buscommon.model.ApiUserBasicInfo;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.RanksDto;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.WatchNumberAdpater;
import com.kalacheng.livecommon.databinding.WatchNumberBinding;
import com.kalacheng.livecommon.viewmodel.UserFansViewModel;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.List;

/**
 * 粉丝
 */
public class WatchNumberFragment extends BaseMVVMFragment<WatchNumberBinding, UserFansViewModel> {
    private int roomType = 1;

    public WatchNumberFragment() {

    }

    public WatchNumberFragment(int roomType) {
        this.roomType = roomType;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.watch_number;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.fanRecy.setLayoutManager(manager);
        getWatchNumber();
        getOnwFansRanking();
    }

    public void getWatchNumber() {
        HttpApiPublicLive.getLiveUser(LiveConstants.ANCHORID, roomType, 0,new HttpApiCallBackArr<ApiUserBasicInfo>() {
            @Override
            public void onHttpRet(int code, String msg, final List<ApiUserBasicInfo> retModel) {
                if (code == 1) {
                    WatchNumberAdpater adpater = new WatchNumberAdpater(getContext(), retModel);
                    binding.fanRecy.setAdapter(adpater);

                    adpater.setWatchNumberCallBack(new WatchNumberAdpater.WatchNumberCallBack() {
                        @Override
                        public void onClick(int position) {
                            LiveConstants.TOUID = retModel.get(position).uid;
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.Information, null);
                        }
                    });
                }
            }
        });
    }

    //观众席当前用户所在排行
    public void getOnwFansRanking() {
        HttpApiPublicLive.getLiveUserRank(LiveConstants.ANCHORID, HttpClient.getUid(), new HttpApiCallBack<RanksDto>() {
            @Override
            public void onHttpRet(int code, String msg, RanksDto retModel) {
                if (code == 1) {
                    getUi(retModel);
                } else {
                    binding.OwnFansRanking.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getUi(RanksDto ranksDto) {
        binding.OwnFansRanking.setVisibility(View.VISIBLE);
        binding.contributionNumber.setText(String.valueOf(ranksDto.sort));
        ImageLoader.display(ranksDto.avatar, binding.contributionHeadimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        binding.contributionName.setText(ranksDto.username);
        ImageLoader.display(ranksDto.icon, binding.contributionGrade);
        binding.contributionMoney.setText(DecimalFormatUtils.isIntegerDouble(ranksDto.upperLevelDelta));

        if (null != getActivity()) {
            //此处 和 列表 性别显示方法统一
            SexUtlis.getInstance().setSex(getActivity(), binding.contributionGender, ranksDto.sex, 0);
        }
    }
}
