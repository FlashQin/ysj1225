package com.kalacheng.live.component.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.live.component.adapter.AnchorMoreAdpater;
import com.kalacheng.util.bean.SimpleImageSrcTextBean;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.live.R;
import com.kalacheng.util.view.ItemDecoration;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.ArrayList;
import java.util.List;

// 直播间 更多（主播）
public class AnchorMoreDialogFragment extends BaseDialogFragment {

    private boolean muteAllRemote;
    private int liveFunction = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.anchor_more;
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
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        muteAllRemote = getArguments().getBoolean("muteAllRemote", false);
        liveFunction = getArguments().getInt("liveFunction", 0);

        List<SimpleImageSrcTextBean> list = new ArrayList<>();
//        SimpleImageSrcTextBean bean1 = new SimpleImageSrcTextBean();
//        bean1.text ="分享";
//        bean1.src = R.mipmap.live_share;
//        list.add(bean1);

        // 如果是直播购 加入相应Item
        if (ConfigUtil.getBoolValue(R.bool.containShopping)){
            list.addAll(addShopItem());
        }

        SimpleImageSrcTextBean bean2 = new SimpleImageSrcTextBean();
        bean2.text ="粉丝团";
        bean2.src = R.mipmap.live_fans;
        list.add(bean2);

//        SimpleImageSrcTextBean bean3 = new SimpleImageSrcTextBean();
//        bean3.text ="装扮贴纸";
//        bean3.src = R.mipmap.sticker;
//        list.add(bean3);

        SimpleImageSrcTextBean bean4 = new SimpleImageSrcTextBean();
        bean4.text ="心愿单";
        bean4.src = R.mipmap.wish_list;
        list.add(bean4);

//        SimpleImageSrcTextBean bean5 = new SimpleImageSrcTextBean();
//        bean5.text ="音乐效果";
//        bean5.src = R.mipmap.music_efect;
//        list.add(bean5);

        SimpleImageSrcTextBean bean16= new SimpleImageSrcTextBean();
        bean16.text ="翻转";
        bean16.src = R.mipmap.live_camera ;
        list.add(bean16);

        SimpleImageSrcTextBean bean6 = new SimpleImageSrcTextBean();
        bean6.text ="设置";
        bean6.src = R.mipmap.setting;
        list.add(bean6);

        SimpleImageSrcTextBean bean7 = new SimpleImageSrcTextBean();
        bean7.text ="任务";
        bean7.src = R.mipmap.task;
        list.add(bean7);

//        SimpleImageSrcTextBean bean8 = new SimpleImageSrcTextBean();
//        bean8.text ="红包";
//        bean8.src = R.mipmap.red;
//        list.add(bean8);

        SimpleImageSrcTextBean bean9 = new SimpleImageSrcTextBean();
        bean9.text ="背景音乐";
        bean9.src = R.mipmap.background_music;
        list.add(bean9);

        SimpleImageSrcTextBean bean10 = new SimpleImageSrcTextBean();
        bean10.text ="房间公告";
        bean10.src = R.mipmap.room_notice;
        list.add(bean10);

//        SimpleImageSrcTextBean bean11= new SimpleImageSrcTextBean();
//        bean11.text ="转盘";
//        bean11.src = R.mipmap.turntable;
//        list.add(bean11);

//        SimpleImageSrcTextBean bean12= new SimpleImageSrcTextBean();
//        bean12.text ="录屏(开)";
//        bean12.src = R.mipmap.screen_open;
//        list.add(bean12);

//        SimpleImageSrcTextBean bean13= new SimpleImageSrcTextBean();
//        bean13.text ="录屏(关)";
//        bean13.src = R.mipmap.screen_close;
//        list.add(bean13);

        SimpleImageSrcTextBean bean14= new SimpleImageSrcTextBean();
        bean14.text ="直播时长";
        bean14.src = R.mipmap.live_time;
        list.add(bean14);

        SimpleImageSrcTextBean bean15= new SimpleImageSrcTextBean();
        bean15.text ="美颜";
        bean15.src = R.mipmap.live_beauty;
        list.add(bean15);

//        SimpleImageSrcTextBean bean17 = new SimpleImageSrcTextBean();
//        bean17.text ="音乐";
//        bean17.src = R.mipmap.live_muice_icon;
//        list.add(bean17);


        RecyclerView anchor_more = mRootView.findViewById(R.id.anchor_more);
        GridLayoutManager manager = new GridLayoutManager(mContext,5);
        anchor_more.setLayoutManager(manager);
        anchor_more.addItemDecoration(new ItemDecoration(mContext,0,10,20));
        AnchorMoreAdpater adpater = new AnchorMoreAdpater(mContext,list);
        anchor_more.setAdapter(adpater);

        //更多监听
        adpater.setAnchorMoreItmeCallBack(new AnchorMoreAdpater.AnchorMoreItmeCallBack() {
            @Override
            public void onClick(String name,int poistion) {
                if (name.equals("分享")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LiveShare,null);
                }else if (name.equals("设置")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveSetting,null);
                }else if(name.equals("心愿单")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddWishList,null);
                }else if(name.equals("任务")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AnchorTask,null);
                }else if(name.equals("房间公告")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ModifyRoomNotice,null);
                }else if(name.equals("直播时长")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveTime,null);
                }else if(name.equals("背景音乐")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_Music,null);
                }else if(name.equals("美颜")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_BeautyShow, null);
                }else if(name.equals("粉丝团")){
                    ARouter.getInstance().build(ARouterPath.FansGroupActivity).navigation();
                }else if(name.equals("翻转")){
                    PulicUtils.getInstance().switchCamera();
                }
//                else if(name.equals("音乐效果")) {
//
//                }else if(name.equals("音乐")) {
//                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_Music,null);
//                }

                // 添加 直播购业务
                if (ConfigUtil.getBoolValue(R.bool.containShopping)){
                    isShopItem(name);
                }

                dismiss();
            }
        });
    }

    // 直播购无需求跳转
    private void isShopItem(String name){
        switch (name){
            case "打开mic":
                PulicUtils.getInstance().muteLocalAudioStream(true);
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OPEN_CLOSE_MIC, false);
                break;
            case "关闭mic":
                PulicUtils.getInstance().muteLocalAudioStream(false);
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OPEN_CLOSE_MIC, true);
                break;
            case "商品橱窗":
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGoodsWindow, null);
                break;
            case "海报":
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveGoodsActivity, null);
                break;
            default:
                break;
        }
    }

    // 添加直播购item 业务需求
    private List<SimpleImageSrcTextBean> addShopItem(){
        List<SimpleImageSrcTextBean> list = new ArrayList<>();

        // 麦克风状态
//        if (!muteAllRemote){
//            SimpleImageSrcTextBean bean1= new SimpleImageSrcTextBean();
//            bean1.text ="关闭mic";
//            bean1.src = R.mipmap.icon_close_mic;
//            list.add(bean1);
//        }else {
//            SimpleImageSrcTextBean bean2= new SimpleImageSrcTextBean();
//            bean2.text ="打开mic";
//            bean2.src = R.mipmap.icon_open_mic;
//            list.add(bean2);
//        }

        // 如果是主播
        if (LiveConstants.IDENTITY == LiveConstants.IDENTITY.ANCHOR && liveFunction ==1){
            SimpleImageSrcTextBean bean3= new SimpleImageSrcTextBean();
            bean3.text ="商品橱窗";
            bean3.src = R.mipmap.icon_shop_window;
            list.add(bean3);

            SimpleImageSrcTextBean bean4= new SimpleImageSrcTextBean();
            bean4.text ="海报";
            bean4.src = R.mipmap.icon_push_poster;
            list.add(bean4);
        }


//        SimpleImageSrcTextBean bean5= new SimpleImageSrcTextBean();
//        bean5.text ="";
//        bean5.src = 0;
//        list.add(bean5);

        return list;
    }

}
