package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.FansInfoDto;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.FansGroupListAdpater;
import com.kalacheng.livecommon.adapter.FansPrivilegeAdpater;
import com.kalacheng.util.utils.glide.ImageLoader;

public class UserFansGroupDialogFragment extends BaseDialogFragment {
    private RoundedImageView fansgroup_headimage;
    private AppJoinRoomVO joinRoom;
    private RecyclerView fansgroup_list;
    private TextView fansgroup_gold;
    private LinearLayout fansgroup_add;
    private RecyclerView fansgroup_privilege;
    private FansGroupListAdpater adpater;
    private double needCoin;
    @Override
    protected int getLayoutId() {
        return R.layout.user_fans_group;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        joinRoom = getArguments().getParcelable("ApiJoinRoom");
        fansgroup_list = mRootView.findViewById(R.id.fansgroup_list);
        fansgroup_headimage = mRootView.findViewById(R.id.fansgroup_headimage);
        fansgroup_gold = mRootView.findViewById(R.id.fansgroup_gold);
        fansgroup_add = mRootView.findViewById(R.id.fansgroup_add);
        fansgroup_privilege = mRootView.findViewById(R.id.fansgroup_privilege);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        fansgroup_list.setLayoutManager(manager);
        adpater = new FansGroupListAdpater(mContext);
        fansgroup_list.setAdapter(adpater);
        getAddFansGroup();

        //加入粉丝团
        fansgroup_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    HttpApiAPPAnchor.joinFansTeam(LiveConstants.ANCHORID, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code==1){
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddFansGroupSusser,1);
                                dismiss();
                            }else if(code ==-1){
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_NoMoney,needCoin);
                            }
                            ToastUtil.show(msg);
                        }
                    });
                }


        });
    }

    //获取信息
    public void getAddFansGroup(){
        HttpApiAPPAnchor.liveFansTeamInfo(LiveConstants.ANCHORID, new HttpApiCallBack<FansInfoDto>() {
            @Override
            public void onHttpRet(int code, String msg, FansInfoDto retModel) {
                if (code ==1){
                    setUI(retModel);
                }
            }
        });
    }

    public void setUI(FansInfoDto retModel){
        needCoin = retModel.coin;
        ImageLoader.display(joinRoom.anchorAvatar,fansgroup_headimage,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        fansgroup_gold.setText(DecimalFormatUtils.isIntegerDouble(retModel.coin));

        adpater.getFansGroupList(retModel.avatars);

        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        fansgroup_privilege.setLayoutManager(manager);

        FansPrivilegeAdpater fansPrivilegeAdpater = new FansPrivilegeAdpater(retModel.privileges);
        fansgroup_privilege.setAdapter(fansPrivilegeAdpater);

    }
}
