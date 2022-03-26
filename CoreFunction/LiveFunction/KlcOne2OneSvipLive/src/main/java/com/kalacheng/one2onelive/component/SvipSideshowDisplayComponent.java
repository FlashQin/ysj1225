package com.kalacheng.one2onelive.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOTMCall;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.busooolive.model.OOOVolumeRet;
import com.kalacheng.busooolive.model.OTMAssisRet;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.one2onelive.R;
import com.kalacheng.one2onelive.databinding.SvipSideshowDisplayBinding;
import com.kalacheng.one2onelive.viewmodel.SvipSideshowDisplayViewModel;
import com.kalacheng.util.utils.DpUtil;
import com.klc.bean.OOOLiveHangUpBean;
import com.klc.bean.SwitchBigAndSmallBean;
import com.xuantongyun.livecloud.config.TTTConfigUtils;
import com.xuantongyun.livecloud.protocol.OOOSvipLiveUtils;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.agora.capture.video.camera.CameraVideoManager;

//多人视频 小窗口显示
public class SvipSideshowDisplayComponent extends BaseMVVMViewHolder<SvipSideshowDisplayBinding, SvipSideshowDisplayViewModel> {

    private int surfaceloction = 100;
    private List<Long> mList = new ArrayList<>();

    //视图集合
    private Map<Long, RelativeLayout> mViewList = new HashMap<>();

    //副播加入成功初始化数据
    private OOOReturn mOOOReturn;

    //副播的麦序状态
    private List<OTMAssisRet> otmAssisRets = new ArrayList<>();

    //切换大小图，小图的id
    private long switchID;

    //直播视频切换
    private boolean openVideo = true;
    private CameraVideoManager mVideoManager;


    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (otmAssisRets.size() != (mViewList.size() - 1)) {
                        mHandler.sendEmptyMessage(1);
                    } else {
                        //显示副播进入直播间的其他副播和用户麦序的状态
                        for (int i = 0; i < otmAssisRets.size(); i++) {
                            if (otmAssisRets.get(i).assisId != LiveConstants.FEEUID) {
                                if (mViewList.containsKey(otmAssisRets.get(i).assisId)) {//通过id在副播中查找对应的视图
                                    if (otmAssisRets.get(i).isOpenVolumn == 1) {//1打开 0关闭
                                        //获取动态添加RelativeLayout里面的控件
                                        mViewList.get(otmAssisRets.get(i).assisId).getChildAt(1).setBackgroundResource(R.mipmap.svip_openvoice);
                                    } else {
                                        mViewList.get(otmAssisRets.get(i).assisId).getChildAt(1).setBackgroundResource(R.mipmap.svip_closevoice);
                                    }
                                    if (mViewList.get(otmAssisRets.get(i).assisId).getChildAt(3) instanceof TextView) {
                                        TextView name = (TextView) mViewList.get(otmAssisRets.get(i).assisId).getChildAt(3);
                                        name.setText(otmAssisRets.get(i).assisName);
                                    }

                                }
                            }
                        }

                        //主播为小图的时候
                        if (mViewList.containsKey(LiveConstants.ANCHORID)) {//通过id在副播中查找对应的视图
                            if (mOOOReturn.feeOpenVolumn == 1) {//1打开 0关闭
                                //获取动态添加RelativeLayout里面的控件
                                mViewList.get(LiveConstants.ANCHORID).getChildAt(1).setBackgroundResource(R.mipmap.svip_openvoice);
                            } else {
                                mViewList.get(LiveConstants.ANCHORID).getChildAt(1).setBackgroundResource(R.mipmap.svip_closevoice);

                            }
                        }
                    }
                    break;
            }
        }
    };

    public SvipSideshowDisplayComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.svip_sideshow_display;
    }

    @Override
    protected void init() {
        addToParent();
        mVideoManager = ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_CloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLinkTTT, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                mViewList.clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveLinkOK, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                viewModel.oooreturn.set((OOOReturn) o);
                mOOOReturn = (OOOReturn) o;
                if (LiveConstants.FEEUID == HttpClient.getUid()) {
                    intiView();
                }

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //禁止发言
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipEstoppelSpeake, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                OOOVolumeRet oooVolumeRet = (OOOVolumeRet) o;
                ViewUI(oooVolumeRet);
                if (null != mOOOReturn) {
                    mOOOReturn.feeOpenVolumn = oooVolumeRet.feeStatus;
                    mOOOReturn.isOpenVolumn = oooVolumeRet.hostStatus;
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        //副播加入成功
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipJoinSuccess, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                mOOOReturn = (OOOReturn) o;
                otmAssisRets = ((OOOReturn) o).assisRets;
                if (otmAssisRets != null && otmAssisRets.size() > 0) {
                    //-2是减除主播和用户的数量
                    mHandler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //副播退出直播间
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipSSideshowignOut, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                OOOLiveHangUpBean hangupInfo = (OOOLiveHangUpBean) o;
                if (mViewList.containsKey(hangupInfo.uid)) {//退出的副播，判断是否是小窗口
                    surfaceloction = surfaceloction - 160;
                    SideshowsignOutUpData(hangupInfo);
                } else {
                    SwitchBigAndSmallBean bigAndSmallBean = new SwitchBigAndSmallBean();
                    bigAndSmallBean.id = LiveConstants.ANCHORID;
                    TextView name = (TextView) mViewList.get(LiveConstants.ANCHORID).getChildAt(3);
                    bigAndSmallBean.userName = name.getText().toString();
                    bigAndSmallBean.isOut = true;
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipSwitchSmallTOBig, bigAndSmallBean);

                    surfaceloction = surfaceloction - 160;
                    if (mViewList.containsKey(LiveConstants.ANCHORID)) {//通过id在副播中查找对应的视图
                        binding.SvipDispalay.removeView(mViewList.get(LiveConstants.ANCHORID));
                    }
                    mViewList.remove(bigAndSmallBean.id);
                }

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //大小头切换  大图变小
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipSwitchBigTOSmall, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                SwitchBigAndSmallBean bigAndSmallBean = (SwitchBigAndSmallBean) o;

                if (mViewList.containsKey(switchID)) {
                    mViewList.get(switchID).removeAllViews();
                    mViewList.get(switchID).setTag(bigAndSmallBean.id);
                    mViewList.put(bigAndSmallBean.id, mViewList.get(switchID));
                    mViewList.remove(switchID);
                    switchBigAndSmallUpdata(bigAndSmallBean);
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


        //加入直播间
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveTTTEstablish, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(final Object o) {
                RelativeLayout relative = AddRelativeLayout((Long) o);
                if (HttpClient.getUid() == LiveConstants.ANCHORID) {//判断创建是否是主播:主播端显示
                    if ((Long) o == LiveConstants.ANCHORID) {//判断加入房间的是否是主播

                    } else {

                        SurfaceView SideshowView = OOOSvipLiveUtils.getInstance().setupRemoteVideo((Long) o);
                        SideshowView.setZOrderOnTop(true);
                        SideshowView.setZOrderMediaOverlay(true);
                        relative.addView(SideshowView);


                        ImageView voiceImage = new ImageView(mContext);
                        voiceImage.setBackgroundResource(R.mipmap.svip_openvoice);
                        RelativeLayout.LayoutParams voiceparams = new RelativeLayout.LayoutParams(DpUtil.dp2px(30), DpUtil.dp2px(30));
                        voiceparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        voiceparams.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                        voiceImage.setLayoutParams(voiceparams);
                        relative.addView(voiceImage);

                        ImageView closeImage = new ImageView(mContext);
                        closeImage.setBackgroundResource(R.mipmap.icon_live_close);
                        RelativeLayout.LayoutParams closeparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        closeparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        closeparams.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                        closeImage.setLayoutParams(closeparams);
                        relative.addView(closeImage);
                        closeImage.setVisibility(View.GONE);

                        // 如果不是自己 加入名字
//                        if ((Long) o != HttpClient.getUid()) {
                        TextView name = new TextView(mContext);
                        name.setTextColor(mContext.getResources().getColor(R.color.white));
                        name.setTextSize(14);
                        name.setBackgroundColor(mContext.getResources().getColor(R.color.color_66000000));
                        name.setGravity(Gravity.CENTER);
                        if ((Long) o != HttpClient.getUid()) {
                            if ((Long) o == LiveConstants.FEEUID) {
                                name.setText(viewModel.oooreturn.get().feeUserName);
                            }
                        } else {
                            ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                            name.setText(apiUserInfo.username);
                        }
                        RelativeLayout.LayoutParams nameRela = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(30));
                        nameRela.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        name.setLayoutParams(nameRela);
                        relative.addView(name);
//                        }

                        binding.SvipDispalay.addView(relative);
                        surfaceloction = surfaceloction + 160;

                        mViewList.put((Long) o, relative);
                    }
                } else {//:观众端显示
                    if ((Long) o == LiveConstants.ANCHORID) {

                    } else {
                        if ((Long) o == HttpClient.getUid()) {

                            final SurfaceView SideshowView = OOOSvipLiveUtils.getInstance().setupLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0));
                            SideshowView.setZOrderOnTop(true);
                            SideshowView.setZOrderMediaOverlay(true);
                            relative.addView(SideshowView);

                            if (!TTTConfigUtils.getInstance().getConfig().isTTT() && TTTConfigUtils.getInstance().getConfig().getBeautySwitch() == 1) {
                                if (mVideoManager != null) {
                                    mVideoManager.setLocalPreview(SideshowView);
                                    mVideoManager.startCapture();
                                } else {
                                    ((BaseApplication)BaseApplication.getInstance()).initVideoCaptureAsync();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mVideoManager = ((BaseApplication)BaseApplication.getInstance()).getCameraVideoManager();
                                            mVideoManager.setLocalPreview(SideshowView);
                                            mVideoManager.startCapture();
                                        }
                                    }, 500);
                                }
                            }
                        } else {
                            SurfaceView SideshowView = OOOSvipLiveUtils.getInstance().setupRemoteVideo((Long) o);
                            SideshowView.setZOrderOnTop(true);
                            SideshowView.setZOrderMediaOverlay(true);
                            relative.addView(SideshowView);
                        }
                        ImageView voiceImage = new ImageView(mContext);
                        RelativeLayout.LayoutParams voiceparams = new RelativeLayout.LayoutParams(DpUtil.dp2px(30), DpUtil.dp2px(30));
                        voiceparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                        voiceparams.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                        voiceImage.setLayoutParams(voiceparams);
                        relative.addView(voiceImage);

                        //观众 付费
                        if (HttpClient.getUid() == LiveConstants.FEEUID) {
                            if ((Long) o == HttpClient.getUid()) {//判断是否是用户还是副播
                                voiceImage.setBackgroundResource(R.mipmap.bg_video_close);
                                voiceImage.setTag((Long) o);
                                voiceImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        long id = (long) view.getTag();
                                        if (mViewList.containsKey(id)) {
                                            //用户开关摄像头
                                            if (openVideo) {
                                                mViewList.get((Long) o).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_open);
                                                openVideo = false;
                                                PulicUtils.getInstance().disableVideo();//关闭视频
                                            } else {
                                                mViewList.get((Long) o).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_close);
                                                openVideo = true;
                                                PulicUtils.getInstance().enableVideo();//启动视频
                                            }
                                        }
                                    }
                                });
                            } else {
                                voiceImage.setBackgroundResource(R.mipmap.svip_openvoice);
                                voiceImage.setTag((Long) o);
                                voiceImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        long id = (long) view.getTag();
                                        for (int i = 0; i < otmAssisRets.size(); i++) {
                                            if (otmAssisRets.get(i).assisId == id) {
                                                if (mViewList.containsKey(id)) {
                                                    //只有用户才能禁言副播
                                                    HttpApiOTMCall.addOOOShutup(id, LiveConstants.mOOOSessionID, otmAssisRets.get(i).isOpenVolumn, new HttpApiCallBack<HttpNone>() {
                                                        @Override
                                                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                                                            ToastUtil.show(msg);
                                                        }
                                                    });
                                                }
                                            }

                                        }


                                    }
                                });
                            }
                        } else {
                            if ((Long) o == HttpClient.getUid()) {
                                voiceImage.setBackgroundResource(R.mipmap.bg_video_close);
                                voiceImage.setTag((Long) o);
                                voiceImage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        long id = (long) view.getTag();
                                        if (mViewList.containsKey(id)) {
                                            //用户开关摄像头
                                            if (openVideo) {
                                                mViewList.get((Long) o).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_open);
                                                openVideo = false;
                                                PulicUtils.getInstance().disableVideo();//关闭视频
                                            } else {
                                                mViewList.get((Long) o).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_close);
                                                openVideo = true;
                                                PulicUtils.getInstance().enableVideo();//启动视频
                                            }
                                        }
                                    }
                                });
                            } else {
                                voiceImage.setBackgroundResource(R.mipmap.svip_openvoice);
                            }
                        }

                        ImageView closeImage = new ImageView(mContext);
                        closeImage.setBackgroundResource(R.mipmap.icon_live_close);
                        RelativeLayout.LayoutParams closeparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        closeparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        closeparams.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                        closeImage.setLayoutParams(closeparams);
                        relative.addView(closeImage);

                        closeImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //踢人
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipIsKickOut, o);
                            }
                        });
                        //为什么要所有人addview ，因为这个涉及到获取TextView里面的数据，通过RelativeLayout.getChildAt（A）:A是控件在父布局里面的游标值；这个游标根据添加顺序得来的，故所有人addview，为了站位
                        if (HttpClient.getUid() == LiveConstants.FEEUID) {//是用户就隐藏 ，其他人显示
                            if ((Long) o == HttpClient.getUid()) {
                                closeImage.setVisibility(View.GONE);
                            } else {
                                closeImage.setVisibility(View.VISIBLE);
                            }
                        } else {
                            closeImage.setVisibility(View.GONE);
                        }

                        // 如果不是自己 加入名字
//                        if ((Long) o != HttpClient.getUid()) {
                        TextView name = new TextView(mContext);
                        name.setTextColor(mContext.getResources().getColor(R.color.white));
                        name.setTextSize(14);
                        name.setBackgroundColor(mContext.getResources().getColor(R.color.color_66000000));
                        name.setGravity(Gravity.CENTER);
                        if ((Long) o != HttpClient.getUid()) {
                            if ((Long) o == LiveConstants.FEEUID) {// 观众
                                name.setText(viewModel.oooreturn.get().feeUserName);
                            } else if ((Long) o == LiveConstants.ANCHORID) {// 主播
                                name.setText(viewModel.oooreturn.get().userName);
                            } else { //副播
//                                Log.e("副播加入", ">>>");
                                for (int i = 0; i < otmAssisRets.size(); i++) {
                                    if ((Long) o == otmAssisRets.get(i).assisId) {
                                        name.setText(otmAssisRets.get(i).assisName);
                                    }
                                }
                            }
                        } else {
                            ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                            name.setText(apiUserInfo.username);
                        }
                        RelativeLayout.LayoutParams nameRela = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(30));
                        nameRela.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        name.setLayoutParams(nameRela);
                        relative.addView(name);
//                        }

                        binding.SvipDispalay.addView(relative);
                        surfaceloction = surfaceloction + 160;

                        mViewList.put((Long) o, relative);
                    }

                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }

    public void intiView() {


    }

    //小框口拖动
    private int screenWidth, screenHeight;
    private int lastX, lastY;
    private int endX1, endY1;
    private int startx;
    long startTime;
    long endTime;
    boolean isclick;

    private void getDrag(final RelativeLayout SideshowView) {
        screenWidth = DpUtil.getScreenWidth();//获取屏幕宽度
        screenHeight = DpUtil.getScreenHeight() - DpUtil.getStatusHeight();//屏幕高度-状态栏


        SideshowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        startTime = System.currentTimeMillis();
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();

                        startx = (int) event.getX();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;

                        int top = v.getTop() + dy;


                        int left = v.getLeft() + dx;


                        if (top <= 0) {
                            top = 0;
                        }
                        if (top >= screenHeight - SideshowView.getHeight()) {
                            top = screenHeight - SideshowView.getHeight();
                        }
                        if (left >= screenWidth - SideshowView.getWidth()) {
                            left = screenWidth - SideshowView.getWidth();
                        }

                        if (left <= 0) {
                            left = 0;
                        }

                        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
                        param.leftMargin = left;
                        param.topMargin = top;
                        v.setLayoutParams(param);
//            v.layout(left, top, left+v.getWidth(), top+v.getHeight());

                        v.postInvalidate();

                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();


                        break;
                    case MotionEvent.ACTION_UP:
                        endX1 = (int) event.getX();//记录结束位置
                        endY1 = (int) event.getRawY();

                        endTime = System.currentTimeMillis();
                        //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                        if ((endTime - startTime) > 0.1 * 1000L) {
                            isclick = false;
                        } else {
                            isclick = true;
                        }
                        startTime = 0;
                        endTime = 0;

                        break;
                }
                //响应点击事件
                if (isclick) {
                    isclick = false;
                    long viewid = (long) v.getTag();
                    switchBigAndSmall(viewid);
                }
                return true;

            }
        });


    }

    //动态添加视图
    public RelativeLayout AddRelativeLayout(Long id) {
        RelativeLayout relative = new RelativeLayout(mContext);
        relative.setId(id.intValue());
        RelativeLayout.LayoutParams Sideshowparams = new RelativeLayout.LayoutParams((DpUtil.getScreenWidth() - 40) / 3, (DpUtil.getScreenWidth() - 40) / 3 * 5 / 4);
        Sideshowparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        Sideshowparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        Sideshowparams.setMargins(0, 0, DpUtil.dp2px(10), DpUtil.dp2px(surfaceloction));
        relative.setLayoutParams(Sideshowparams);
        relative.setTag(id);

        getDrag(relative);
        return relative;
    }

    //禁言操作ui
    public void ViewUI(OOOVolumeRet oooReturn) {
        if (null != oooReturn.assisRets && oooReturn.assisRets.size() > 0) {
            otmAssisRets.clear();
            otmAssisRets.addAll(oooReturn.assisRets);

            for (int i = 0; i < oooReturn.assisRets.size(); i++) {

                if (mViewList.containsKey(oooReturn.assisRets.get(i).assisId)) {//通过id在副播中查找对应的视图
                    if (oooReturn.assisRets.get(i).isOpenVolumn == 1) {//1打开 0关闭
                        //获取动态添加RelativeLayout里面的控件
                        mViewList.get(oooReturn.assisRets.get(i).assisId).getChildAt(1).setBackgroundResource(R.mipmap.svip_openvoice);
                    } else {
                        mViewList.get(oooReturn.assisRets.get(i).assisId).getChildAt(1).setBackgroundResource(R.mipmap.svip_closevoice);
                    }
                }
            }

        }

        // 如果我是大图 随意改 列表不包含我
        //用户的麦序状态
//        if (mViewList.containsKey(oooReturn.feeUid)) {//通过id在副播中查找对应的视图
//            if (oooReturn.feeStatus == 1) {//1打开 0关闭
//                //获取动态添加RelativeLayout里面的控件
//                mViewList.get(oooReturn.feeUid).getChildAt(1).setBackgroundResource(R.mipmap.svip_openvoice);
//            } else {
//                mViewList.get(oooReturn.feeUid).getChildAt(1).setBackgroundResource(R.mipmap.svip_closevoice);
//            }
//        }
//        if (mViewList.containsKey(oooReturn.hostUid)) {
//            //主播的麦序
//            if (oooReturn.hostStatus == 1) {//1打开 0关闭
//                //获取动态添加RelativeLayout里面的控件
//                mViewList.get(oooReturn.hostUid).getChildAt(1).setBackgroundResource(R.mipmap.svip_openvoice);
//            } else {
//                mViewList.get(oooReturn.hostUid).getChildAt(1).setBackgroundResource(R.mipmap.svip_closevoice);
//            }
//        }
//
//        // 如果列表包含我 设置成摄像头
//        if (mViewList.containsKey(HttpClient.getUid())) {
//            if (openVideo) {
//                mViewList.get(HttpClient.getUid()).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_close);
//            } else {
//                mViewList.get(HttpClient.getUid()).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_open);
//            }
//        }
//
//        // 单操作人不是主播 也不是消费者  那么是 用户和用户的普通视频
//        if (oooReturn.feeUid != HttpClient.getUid() && oooReturn.hostUid != HttpClient.getUid() && mViewList.containsKey(oooReturn.operateUid)){
//            if (oooReturn.operateStatus == 1) {//1打开 0关闭
//                mViewList.get(oooReturn.operateUid).getChildAt(1).setBackgroundResource(R.mipmap.svip_openvoice);
//            } else {
//                mViewList.get(oooReturn.operateUid).getChildAt(1).setBackgroundResource(R.mipmap.svip_closevoice);
//            }
//        }

        // 如果列表包含我 设置成摄像头
        if (oooReturn.operateUid == HttpClient.getUid() && mViewList.containsKey(oooReturn.operateUid)) {
            if (openVideo) {
                mViewList.get(HttpClient.getUid()).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_close);
            } else {
                mViewList.get(HttpClient.getUid()).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_open);
            }
        } else if (mViewList.containsKey(oooReturn.operateUid)) {
            if (oooReturn.operateStatus == 1) {//1打开 0关闭
                mViewList.get(oooReturn.operateUid).getChildAt(1).setBackgroundResource(R.mipmap.svip_openvoice);
            } else {
                mViewList.get(oooReturn.operateUid).getChildAt(1).setBackgroundResource(R.mipmap.svip_closevoice);
            }
        }

    }

    //副播退出ui刷新
    public void SideshowsignOutUpData(OOOLiveHangUpBean hangupInfo) {
        if (mViewList.containsKey(hangupInfo.uid)) {//通过id在副播中查找对应的视图
            binding.SvipDispalay.removeView(mViewList.get(hangupInfo.uid));
        }
    }

    //切换大小屏
    public void switchBigAndSmall(long id) {
        if (mViewList.get(id) != null) {
            switchID = id;


            SwitchBigAndSmallBean bigAndSmallBean = new SwitchBigAndSmallBean();
            if (mViewList.get(id).getChildAt(0) instanceof SurfaceView) {
                SurfaceView surfaceView = (SurfaceView) mViewList.get(id).getChildAt(0);
                bigAndSmallBean.surfaceView = surfaceView;
            }

            bigAndSmallBean.id = id;
            Log.i("aaa", "" + mViewList.get(id).getChildCount());
            for (int i = 0; i < mViewList.get(id).getChildCount(); i++) {
                if (mViewList.get(id).getChildAt(i) instanceof TextView) {
                    TextView name = (TextView) mViewList.get(id).getChildAt(i);
                    bigAndSmallBean.userName = name.getText().toString();
                }
            }
            bigAndSmallBean.isOut = false;
            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipSwitchSmallTOBig, bigAndSmallBean);
        }

    }

    public void switchBigAndSmallUpdata(final SwitchBigAndSmallBean bigAndSmallBean) {
        RelativeLayout relativeLayout = mViewList.get(bigAndSmallBean.id);
        if (relativeLayout != null) {
            SurfaceView SideshowView;
            if (bigAndSmallBean.id == HttpClient.getUid()) {
                SideshowView = bigAndSmallBean.surfaceView;
            } else {
                SideshowView = bigAndSmallBean.surfaceView;

            }
            RelativeLayout parent = (RelativeLayout) SideshowView.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            relativeLayout.addView(SideshowView);
            SideshowView.setZOrderOnTop(true);
            SideshowView.setZOrderMediaOverlay(true);

            ImageView voiceImage = new ImageView(mContext);
            RelativeLayout.LayoutParams voiceparams = new RelativeLayout.LayoutParams(DpUtil.dp2px(30), DpUtil.dp2px(30));
            voiceparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            voiceparams.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
            voiceImage.setLayoutParams(voiceparams);
            relativeLayout.addView(voiceImage);

            if (bigAndSmallBean.id == LiveConstants.FEEUID) {//判断是否是用户还是副播
                if (HttpClient.getUid() != LiveConstants.FEEUID) {
                    if (HttpClient.getUid() == LiveConstants.ANCHORID) {
                        if (mOOOReturn.isOpenVolumn == 1) {
                            voiceImage.setBackgroundResource(R.mipmap.svip_openvoice);
                        } else {
                            voiceImage.setBackgroundResource(R.mipmap.svip_closevoice);
                        }
                    } else {
                        for (int i = 0; i < otmAssisRets.size(); i++) {
                            if (bigAndSmallBean.id == otmAssisRets.get(i).assisId) {
                                if (otmAssisRets.get(i).isOpenVolumn == 1) {
                                    voiceImage.setBackgroundResource(R.mipmap.svip_openvoice);
                                } else {
                                    voiceImage.setBackgroundResource(R.mipmap.svip_closevoice);
                                }
                            }
                        }
                    }
                }
            } else {
                if (bigAndSmallBean.id == LiveConstants.ANCHORID) {
                    if (mOOOReturn.isOpenVolumn == 1) {
                        voiceImage.setBackgroundResource(R.mipmap.svip_openvoice);
                    } else {
                        voiceImage.setBackgroundResource(R.mipmap.svip_closevoice);
                    }
                } else {
                    if (bigAndSmallBean.id == LiveConstants.FEEUID) {
                        if (mOOOReturn.feeOpenVolumn == 1) {
                            voiceImage.setBackgroundResource(R.mipmap.svip_openvoice);
                        } else {
                            voiceImage.setBackgroundResource(R.mipmap.svip_closevoice);
                        }
                    } else {
                        for (int i = 0; i < otmAssisRets.size(); i++) {
                            if (bigAndSmallBean.id == otmAssisRets.get(i).assisId) {
                                if (otmAssisRets.get(i).isOpenVolumn == 1) {
                                    voiceImage.setBackgroundResource(R.mipmap.svip_openvoice);
                                } else {
                                    voiceImage.setBackgroundResource(R.mipmap.svip_closevoice);
                                }
                            }
                        }
                    }
                }

                voiceImage.setTag(bigAndSmallBean.id);
                voiceImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        long id = (long) view.getTag();
                        for (int i = 0; i < otmAssisRets.size(); i++) {
                            if (otmAssisRets.get(i).assisId == id) {
                                if (mViewList.containsKey(id)) {
                                    //只有用户才能禁言副播
                                    HttpApiOTMCall.addOOOShutup(id, LiveConstants.mOOOSessionID, otmAssisRets.get(i).isOpenVolumn, new HttpApiCallBack<HttpNone>() {
                                        @Override
                                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                                            ToastUtil.show(msg);
                                        }
                                    });
                                }
                            }
                        }
                    }
                });


                ImageView closeImage = new ImageView(mContext);
                closeImage.setBackgroundResource(R.mipmap.icon_live_close);
                RelativeLayout.LayoutParams closeparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                closeparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                closeparams.setMargins(DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5), DpUtil.dp2px(5));
                closeImage.setLayoutParams(closeparams);
                relativeLayout.addView(closeImage);
                closeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //踢人
                        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipIsKickOut, bigAndSmallBean.id);
                    }
                });


                if (HttpClient.getUid() == LiveConstants.FEEUID) {
                    if (bigAndSmallBean.id == LiveConstants.FEEUID || bigAndSmallBean.id == LiveConstants.ANCHORID) {
                        closeImage.setVisibility(View.GONE);
                    } else {
                        closeImage.setVisibility(View.VISIBLE);
                    }
                } else {
                    closeImage.setVisibility(View.GONE);

                }

            }

            // 如果切换的是自己 不显示名字
//            if (bigAndSmallBean.id != HttpClient.getUid()) {
            // 名字
            TextView name = new TextView(mContext);
            name.setTextColor(mContext.getResources().getColor(R.color.white));
            name.setTextSize(14);
            name.setBackgroundColor(mContext.getResources().getColor(R.color.color_66000000));
            name.setGravity(Gravity.CENTER);
            if (bigAndSmallBean.id != HttpClient.getUid()) {
                name.setText(bigAndSmallBean.userName);
            } else {
                ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                name.setText(apiUserInfo.username);
            }
            RelativeLayout.LayoutParams nameRela = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(30));
            nameRela.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            name.setLayoutParams(nameRela);
            relativeLayout.addView(name);
//            }

            if (bigAndSmallBean.id == HttpClient.getUid()) {
                if (openVideo) {
                    voiceImage.setBackgroundResource(R.mipmap.bg_video_close);
                } else {
                    voiceImage.setBackgroundResource(R.mipmap.bg_video_open);
                }
                voiceImage.setTag(bigAndSmallBean.id);
                voiceImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        long id = (long) view.getTag();
                        if (mViewList.containsKey(id)) {
                            //用户开关摄像头
                            if (openVideo) {
                                mViewList.get(bigAndSmallBean.id).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_open);
                                openVideo = false;
                                PulicUtils.getInstance().disableVideo();//关闭视频
                            } else {
                                mViewList.get(bigAndSmallBean.id).getChildAt(1).setBackgroundResource(R.mipmap.bg_video_close);
                                openVideo = true;
                                PulicUtils.getInstance().enableVideo();//启动视频
                            }
                        }
                    }
                });
            }
        }
    }

    public void clean() {
        mViewList.clear();
        otmAssisRets.clear();
        removeFromParent();
        mHandler.removeMessages(1);
    }
}
