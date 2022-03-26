package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libbas.model.HttpNone;

import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.AppLiang;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.GiveBeautifulNumberAdpater;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;

import java.util.List;

/*
* 赠送靓号
* */
public class GiveBeautifulNumberDialogFragment extends BaseDialogFragment {
    private GiveBeautifulNumberAdpater adpater;

    private  List<AppLiang> mList;

    private AppJoinRoomVO apiJoinRoom;

    public void getApiJoinRoom(AppJoinRoomVO apiJoinRoom){
        this.apiJoinRoom =apiJoinRoom;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.give_beautiful_number;
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
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.getScreenHeight()/2;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RoundedImageView GiveBeautifulNumber_headimage = mRootView.findViewById(R.id.GiveBeautifulNumber_headimage);
        ImageLoader.display(apiJoinRoom.anchorAvatar,GiveBeautifulNumber_headimage,R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        TextView GiveBeautifulNumber_Name = mRootView.findViewById(R.id.GiveBeautifulNumber_Name);
        GiveBeautifulNumber_Name.setText(apiJoinRoom.anchorName);

        RecyclerView GiveBeautifulNumber_NumList = mRootView.findViewById(R.id.GiveBeautifulNumber_NumList);
        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        GiveBeautifulNumber_NumList.setLayoutManager(manager);
        GiveBeautifulNumber_NumList.addItemDecoration(new ItemDecoration(mContext,0,10,10));
        adpater = new GiveBeautifulNumberAdpater(mContext);
        GiveBeautifulNumber_NumList.setAdapter(adpater);
        adpater.setGiveBeautifulNumberCallBack(new GiveBeautifulNumberAdpater.GiveBeautifulNumberCallBack() {
            @Override
            public void onClick(int poistion) {
                if (mList!=null){
                    giveNumber(mList.get(poistion).id);
                }

            }
        });

        getNumberList();
    }

    //获取靓号接口
    public void getNumberList(){
        HttpApiAPPAnchor.getNumberList(0, 12, new HttpApiCallBackArr<AppLiang>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppLiang> retModel) {
                if (code ==1){
                    mList = retModel;
                    adpater.getData(retModel);
                }
            }
        });
    }

    //赠送靓号
    public void giveNumber(long numid){
        HttpApiAPPAnchor.purchaseNumber(LiveConstants.ANCHORID, numid, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code ==1){
                    dismiss();
                }else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
