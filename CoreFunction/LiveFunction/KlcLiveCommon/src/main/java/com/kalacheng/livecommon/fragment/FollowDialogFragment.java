package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FollowDialogFragment extends BaseDialogFragment {
    private AppJoinRoomVO apiJoinRoom;
    Disposable timeDisposable;;
    @Override
    protected int getLayoutId() {
        return R.layout.follow_dialog;
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
        params.height =WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.y = DpUtil.dp2px(50);
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiJoinRoom = getArguments().getParcelable("ApiJoinRoom");

        RoundedImageView Follow_AnchorHead = mRootView.findViewById(R.id.Follow_AnchorHead);
        TextView Follow_AnchorName = mRootView.findViewById(R.id.Follow_AnchorName);

        ImageLoader.display(apiJoinRoom.anchorAvatar,Follow_AnchorHead,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        Follow_AnchorName.setText(apiJoinRoom.anchorName);

        TextView Follow_Buttom = mRootView.findViewById(R.id.Follow_Buttom);

        Follow_Buttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFollow();
            }
        });
        timeDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                if (aLong>11){
                    close();
                }
            }
        });
    }

    public void getFollow(){
        HttpApiAppUser.set_atten(1, LiveConstants.ANCHORID, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_UserFollewSusser,null);
                    close();
                }
            }
        });
    }
    public void close(){
        if(timeDisposable!=null){
            timeDisposable.dispose();
        };
        dismiss();
    }
}
