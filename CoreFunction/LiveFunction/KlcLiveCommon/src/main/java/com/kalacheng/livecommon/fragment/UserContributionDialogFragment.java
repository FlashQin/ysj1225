package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.busfinance.httpApi.HttpApiAPPFinance;
import com.kalacheng.libuser.model.RanksDto;
import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.UserContributionAdpater;
import com.kalacheng.livecommon.databinding.UserContributionBinding;
import com.kalacheng.livecommon.viewmodel.UserContributionViewModel;

import java.util.List;


public class UserContributionDialogFragment extends BaseMVVMFragment<UserContributionBinding, UserContributionViewModel> {
    private UserContributionAdpater adpater;

    private  List<RanksDto> mList;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.user_contribution;
    }


    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.contributionRecy.setLayoutManager(manager);

        adpater = new UserContributionAdpater(getContext());
        binding.contributionRecy.setAdapter(adpater);
        adpater.setUserContributionCallBack(new UserContributionAdpater.UserContributionCallBack() {
            @Override
            public void onClick(int poistion) {
                if (mList!=null){
                    LiveConstants.TOUID = mList.get(poistion).userId;
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.Information, null);
                }

            }
        });

        getData();

    }

    public void getData(){
        HttpApiAPPFinance.contributionList(LiveConstants.ANCHORID, 0, 10, 0, new HttpApiCallBackArr<RanksDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<RanksDto> retModel) {
                if (code==1){
                    mList =retModel;
                    adpater.setData(retModel);
                }

            }
        });
    }

}
