package com.kalacheng.live.component.compactivity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.live.R;
import com.kalacheng.live.component.componentlive.LiveInfoComponent;
import com.kalacheng.live.component.viewmodel.ApiCloseLiveViewModel;
import com.kalacheng.live.databinding.ViewLiveEndBinding;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

@Route(path = ARouterPath.LiveEndActivity)
public class CloseLiveActivity extends BaseMVVMActivity<ViewLiveEndBinding, ApiCloseLiveViewModel> {
    @Autowired(name = "ApiJoinRoom")
    AppJoinRoomVO apiJoinRoom;
    @Autowired(name = "ApiCloseLive")
    ApiCloseLive apiCloseLive;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.view_live_end;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);

        viewModel.apijoinroom.set(apiJoinRoom);
        viewModel.bean.set(apiCloseLive);

        closeLive(apiCloseLive);
    }
    public void closeLive(final ApiCloseLive apiCloseLive) {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageLoader.display(viewModel.apijoinroom.get().liveThumb,binding.CloseRe);
        if (apiCloseLive.anchorId == HttpClient.getUid()){
            binding.closeAnchorFollow.setVisibility(View.GONE);
            binding.linAnchor.setVisibility(View.VISIBLE);
        }else {
            binding.closeAnchorFollow.setVisibility(View.VISIBLE);
            binding.linAnchor.setVisibility(View.GONE);
            if (LiveInfoComponent.isFollow == 1){
                binding.closeAnchorFollow.setText("已关注");
                binding.closeAnchorFollow.setBackgroundResource(R.drawable.bg_live_end_follwed);
            }else {
                binding.closeAnchorFollow.setText("+  关注");
                binding.closeAnchorFollow.setBackgroundResource(R.drawable.bg_live_end_btn);
                binding.closeAnchorFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpApiAppUser.set_atten(1, LiveConstants.ANCHORID, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    binding.closeAnchorFollow.setText("已关注");
                                    binding.closeAnchorFollow.setBackgroundResource(R.drawable.bg_live_end_follwed);
                                }
                            }
                        });
                    }
                });
            }
        }

        try {
            binding.getViewModel().bean.set(apiCloseLive);
            binding.duration.setText(StringUtil.getDurationText(apiCloseLive.duration * 1000));
            binding.thisfieldNumber.setText(DecimalFormatUtils.isIntegerDouble(apiCloseLive.votes));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_CloseLive, apiCloseLive);
    }
}
