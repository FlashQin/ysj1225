package com.kalacheng.commonview.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.buscommon.model.GuardUserDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.GuardListAdpater;
import com.kalacheng.commonview.databinding.LiveGuardListBinding;
import com.kalacheng.commonview.viewmodel.LiveGuardListViewModel;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;

import java.util.List;

public class LiveGuardListFragment extends BaseMVVMFragment<LiveGuardListBinding, LiveGuardListViewModel> {

    private int type;//0 直播间内；1 直播间外
    private long anchorId;//主播ID
    private String anchorAvatar;//主播头像
    private String anchorName;//主播昵称

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.live_guard_list;
    }

    @Override
    public void initData() {
        type = getArguments().getInt("type");
        anchorId = getArguments().getLong("anchorId");
        anchorAvatar = getArguments().getString("anchorAvatar");
        anchorName = getArguments().getString("anchorName");

        getGuardMyList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 标记
        isCreated = true;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    protected boolean isCreated = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
            getGuardMyList();
        }

    }

    public void getGuardMyList() {
        HttpApiAppUser.getGuardMyList(0, 10, anchorId, new HttpApiCallBackArr<GuardUserDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<GuardUserDto> retModel) {
                if (code == 1) {
                    viewModel.bean.set(retModel);
                    getUI();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    public void getUI() {
        ImageLoader.display(anchorAvatar, binding.AnchorHeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        binding.AnchorName.setText(anchorName);

        if (viewModel.bean.get() != null && viewModel.bean.get().size() > 0) {
            if (viewModel.bean.get().size() == 1) {
                binding.guardlistLin.setVisibility(View.GONE);
                binding.guardlistNull.setVisibility(View.VISIBLE);
                ImageLoader.display(viewModel.bean.get().get(0).userHeadImg, binding.UserHeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                binding.UserName.setText(viewModel.bean.get().get(0).username);
            } else {
                binding.guardlistLin.setVisibility(View.VISIBLE);
                binding.guardlistNull.setVisibility(View.GONE);
                ImageLoader.display(viewModel.bean.get().get(0).userHeadImg, binding.UserHeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                binding.UserName.setText(viewModel.bean.get().get(0).username);

                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(OrientationHelper.HORIZONTAL);
                binding.guardlistRe.setLayoutManager(manager);
                binding.guardlistRe.addItemDecoration(new ItemDecoration(getContext(), 0, 5, 0));
                GuardListAdpater adpater = new GuardListAdpater(getContext());
                binding.guardlistRe.setAdapter(adpater);
                List<GuardUserDto> data = viewModel.bean.get().subList(1, viewModel.bean.get().size());
                adpater.getGuardList(data, 2);
                adpater.setGuardListCallBack(new GuardListAdpater.GuardListCallBack() {
                    @Override
                    public void onClick(int poistion) {

                    }
                });
            }
        } else {
            binding.guardlistLin.setVisibility(View.GONE);
            binding.guardlistNull.setVisibility(View.VISIBLE);
        }

    }
}
