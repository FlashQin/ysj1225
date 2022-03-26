package com.kalacheng.commonview.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.OpenGuardAdpater;
import com.kalacheng.commonview.databinding.OpenGuardBinding;
import com.kalacheng.commonview.viewmodel.OpenGuardViewModel;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiGuardEntity;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.SpacesItemDecoration;

import java.util.HashMap;

/**
 * 开通守护
 */
public class OpenGuardFragment extends BaseMVVMFragment<OpenGuardBinding, OpenGuardViewModel> {
    private int type;//0 直播间内；1 直播间外
    private long anchorId;//主播ID
    private String anchorAvatar;//主播头像
    private String anchorName;//主播昵称

    private long GuardBuyID = 0;
    private double ColdNum;
    private OpenGuardAdpater adpater;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.open_guard;
    }

    @Override
    public void initData() {
        type = getArguments().getInt("type");
        anchorId = getArguments().getLong("anchorId");
        anchorAvatar = getArguments().getString("anchorAvatar");
        anchorName = getArguments().getString("anchorName");

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        binding.guardRecy.setLayoutManager(manager);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(SpacesItemDecoration.TOP_DECORATION, 10);//上下间距
        stringIntegerHashMap.put(SpacesItemDecoration.RIGHT_DECORATION, 10);
        binding.guardRecy.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));

        adpater = new OpenGuardAdpater(mContext);
        binding.guardRecy.setAdapter(adpater);
        getGuard();

    }

    public void getGuard() {
        HttpApiAPPAnchor.getGuardList(anchorId, new HttpApiCallBack<ApiGuardEntity>() {
            @Override
            public void onHttpRet(int code, String msg, ApiGuardEntity retModel) {
                if (code == 1) {
                    viewModel.bean.set(retModel);
                    getUI();
                }
            }
        });
    }

    public void getUI() {
        if (viewModel.bean.get().dayNumber > 0) {
            ApiUserInfo info = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);

            ImageLoader.display(anchorAvatar, binding.AnchorHeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            ImageLoader.display(info.avatar, binding.AnchorUserImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            binding.guardDaynum.setText("第" + String.valueOf(viewModel.bean.get().dayNumber) + "天守护TA");
            binding.guardDaynum.setTextColor(getResources().getColor(R.color.white));
            binding.AnchorGuardLin.setBackgroundResource(R.mipmap.guard_bg);

        } else {
            ImageLoader.display(anchorAvatar, binding.AnchorHeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            binding.AnchorUserImage.setImageDrawable(getResources().getDrawable(R.mipmap.bg_null));
            binding.guardDaynum.setText("给TA打CALL，快去守护主播吧");
            binding.guardDaynum.setTextColor(getResources().getColor(R.color.textColor9));
            binding.AnchorGuardLin.setBackgroundResource(0);
        }

        GuardBuyID = viewModel.bean.get().apiGuardList.get(0).tid;
        ColdNum = viewModel.bean.get().apiGuardList.get(0).coin;
        adpater.getApiGuard(viewModel.bean.get().apiGuardList);
        adpater.getPoistion(0);
        adpater.setOnItmeCallBack(new OpenGuardAdpater.OnItmeCallBack() {
            @Override
            public void onClick(int poistion) {
                adpater.getPoistion(poistion);
                ColdNum = viewModel.bean.get().apiGuardList.get(poistion).coin;
                GuardBuyID = viewModel.bean.get().apiGuardList.get(poistion).tid;
            }
        });


        //为TA守护
        binding.guardBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGuardBuy();
            }
        });
    }

    /**
     * 开通守护
     */
    public void getGuardBuy() {
        if (GuardBuyID == 0)
            return;
        HttpApiAPPAnchor.guardListBuy(anchorId, GuardBuyID, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.show(msg);
                }

                if (type == 0) {
                    if (code == 1) {
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddFansGroupSusser, null);
                        getGuard();
                    } else if (code == -1) {
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_NoMoney, ColdNum);
                    }
                } else {
                    if (code == 1) {
                        getGuard();
                    }
                }
            }
        });
    }
}
