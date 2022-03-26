package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.centercommon.R;
import com.kalacheng.commonview.beauty.BaBaBeautyComponent;
import com.kalacheng.commonview.beauty.DefaultBeautyViewHolder;
import com.kalacheng.commonview.beauty.LiveBeautyComponent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.xuantongyun.livecloud.agora.framework.TiPreprocessor;
import com.xuantongyun.livecloud.config.TTTConfigUtils;
import com.xuantongyun.livecloud.protocol.OtherLiveSetUtlis;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import io.agora.capture.video.camera.CameraVideoManager;

/**
 * 美颜设置
 */
@Route(path = ARouterPath.BeautySettingActivity)
public class BeautySettingActivity extends BaseActivity implements View.OnClickListener {
    RelativeLayout relativeLayout;
    private RelativeLayout rlBeauty;
    private CameraVideoManager mVideoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty);
        initView();
        initData();
    }

    private void initView() {
        relativeLayout = findViewById(R.id.rl_all);
        rlBeauty = findViewById(R.id.rl_beauty);
        findViewById(R.id.btn_camera).setOnClickListener(this);
        findViewById(R.id.btn_beauty).setOnClickListener(this);
        findViewById(R.id.icon_back).setOnClickListener(this);
    }

    private void initData() {
        OtherLiveSetUtlis.getInstance().init(mContext);
        final SurfaceView surfaceView = OtherLiveSetUtlis.getInstance().setupLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0));
        OtherLiveSetUtlis.getInstance().setClientRole(1);
        surfaceView.setZOrderMediaOverlay(false);
        relativeLayout.addView(surfaceView);

        if (!TTTConfigUtils.getInstance().getConfig().isTTT() && TTTConfigUtils.getInstance().getConfig().getBeautySwitch() == 1) {
            TiPreprocessor.mEnabled = true;
            mVideoManager = ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();
            if (mVideoManager != null) {
                mVideoManager.setLocalPreview(surfaceView);
                mVideoManager.startCapture();
            } else {
                ((BaseApplication)BaseApplication.getInstance()).initVideoCaptureAsync();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mVideoManager = ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();
                        mVideoManager.setLocalPreview(surfaceView);
                        mVideoManager.startCapture();
                    }
                }, 500);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.btn_beauty)
            showBeautyDailog();
        else if (view.getId() == R.id.btn_camera) {
            PulicUtils.getInstance().switchCamera();
        } else if (view.getId() == R.id.icon_back) {
            finish();
        }
    }

    private DefaultBeautyViewHolder defaultBeautyViewHolder;
    private LiveBeautyComponent liveBeautyComponent;
    private BaBaBeautyComponent baBaBeautyComponent;

    public void showBeautyDailog() {
        int beautySwitch = (int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY_SWITCH, 0);
        if (beautySwitch == 0) {
            if (null == defaultBeautyViewHolder) {
                defaultBeautyViewHolder = new DefaultBeautyViewHolder(mContext, rlBeauty);
            }
            defaultBeautyViewHolder.show();
        } else if (beautySwitch == 1) {
            if (null == liveBeautyComponent) {
                liveBeautyComponent = new LiveBeautyComponent(mContext, rlBeauty);
            }
            liveBeautyComponent.show();
        } else if (beautySwitch == 2) {
            if (null == baBaBeautyComponent) {
                baBaBeautyComponent = new BaBaBeautyComponent(mContext, rlBeauty);
            }
            baBaBeautyComponent.show();
        }
    }

    @Override
    protected void onDestroy() {
        if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY_SWITCH, 0) == 1) {
//            if (TiSDKManager.getInstance() != null) {
//                TiSDKManager.getInstance().destroy();
//            }
        } else if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.BEAUTY_SWITCH, 0) == 2) {
//            BaBaBeautyUtil.getInstance().clear();
        }
        if (mVideoManager != null) {
            TiPreprocessor.mEnabled = false;
            mVideoManager.stopCapture();
        }
        super.onDestroy();
    }
}
