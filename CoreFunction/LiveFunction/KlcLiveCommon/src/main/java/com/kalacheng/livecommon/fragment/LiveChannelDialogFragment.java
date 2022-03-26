package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.httpApi.HttpApiHome;
import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.LiveChannelAdpater;
import com.kalacheng.util.view.ItemDecoration;
import com.klc.bean.OpenLiveBean;
import com.klc.bean.VoiceOpenLiveBean;

import java.util.List;

public class LiveChannelDialogFragment extends BaseDialogFragment {

    private  LiveChannelAdpater adpater;

    List<AppLiveChannel> liveChannelList ;
    //选中的频道数据
    private AppLiveChannel channel;

    private OpenLiveBean  openLivebean ;

    private VoiceOpenLiveBean voiceOpenLiveBean;

    private int LiveType ;
    @Override
    protected int getLayoutId() {
        return R.layout.live_channel_dialog;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LiveType = getArguments().getInt("LiveType");

        if (LiveType ==1 || LiveType == 6){
            openLivebean =  SpUtil.getInstance().<OpenLiveBean>getModel(LiveConstants.LiveOpenValue, OpenLiveBean.class);
        }else if(LiveType == 2){
            voiceOpenLiveBean =  SpUtil.getInstance().<VoiceOpenLiveBean>getModel(LiveConstants.VoiceLiveOpenValue, VoiceOpenLiveBean.class);
        }


        RecyclerView re_channel = mRootView.findViewById(R.id.re_channel);
        GridLayoutManager manager = new GridLayoutManager(mContext,4);
        re_channel.addItemDecoration(new ItemDecoration(mContext,0,10,20));
        re_channel.setLayoutManager(manager);
        adpater = new LiveChannelAdpater(mContext);
        re_channel.setAdapter(adpater);

        adpater.setLiveChannelItmeCallBack(new LiveChannelAdpater.LiveChannelItmeCallBack() {
            @Override
            public void onClick(int position) {
                channel = liveChannelList.get(position);
                adpater.setClickPostion(position);
            }
        });
        getChannel();

        //确定
        TextView live_channel_ok = mRootView.findViewById(R.id.live_channel_ok);
        live_channel_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (channel==null){
                    ToastUtil.show("请选择频道");
                }else {
                    if (LiveType ==1){
                        OpenLiveBean openLive = new OpenLiveBean();
                        openLive.ChannelName = channel.title;
                        openLive.channelId = (int) channel.id;
                        if (openLivebean!=null){
                            openLive.roomTypeName = openLivebean.roomTypeName;
                            openLive.type = openLivebean.type;
                            openLive.typeVal = openLivebean.typeVal;
                            openLive.thumb = openLivebean.thumb;
                            openLive.title = openLivebean.title;
                            openLive.nickname = openLivebean.nickname;
                            openLive.LiveShopChannelName = openLivebean.LiveShopChannelName;
                            openLive.LiveShopChannelID = openLivebean.LiveShopChannelID;
                        }
                        SpUtil.getInstance().putModel(LiveConstants.LiveOpenValue,openLive);
                    }else if(LiveType == 2){
                        VoiceOpenLiveBean voiceopenLive = new VoiceOpenLiveBean();
                        voiceopenLive.ChannelName = channel.title;
                        voiceopenLive.channelId = (int) channel.id;
                        if (openLivebean!=null){
                            voiceopenLive.roomTypeName = voiceOpenLiveBean.roomTypeName;
                            voiceopenLive.type = voiceOpenLiveBean.type;
                            voiceopenLive.typeVal = voiceOpenLiveBean.typeVal;
                            voiceopenLive.thumb = voiceOpenLiveBean.thumb;
                            voiceopenLive.title = voiceOpenLiveBean.title;
                            voiceopenLive.nickname = voiceOpenLiveBean.nickname;
                            voiceopenLive.voiceBgId = voiceOpenLiveBean.voiceBgId;
                            voiceopenLive.voiceBgUrl = voiceOpenLiveBean.voiceBgUrl;
                        }
                        SpUtil.getInstance().putModel(LiveConstants.VoiceLiveOpenValue,voiceopenLive);
                    }else if(LiveType == 6){
                        OpenLiveBean openLive = new OpenLiveBean();
                        openLive.LiveShopChannelName = channel.title;
                        openLive.LiveShopChannelID = (int) channel.id;
                        if (openLivebean!=null){
                            openLive.roomTypeName = openLivebean.roomTypeName;
                            openLive.type = openLivebean.type;
                            openLive.typeVal = openLivebean.typeVal;
                            openLive.thumb = openLivebean.thumb;
                            openLive.title = openLivebean.title;
                            openLive.nickname = openLivebean.nickname;
                            openLive.ChannelName = openLivebean.ChannelName;
                            openLive.channelId = openLivebean.channelId;
                        }
                        SpUtil.getInstance().putModel(LiveConstants.LiveOpenValue,openLive);
                    }

                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceChannelTypeValue,channel);
                    dismiss();
                }
            }
        });
        ImageView dialog_close = mRootView.findViewById(R.id.dialog_close);
        dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    //获取频道
    public void getChannel(){
        HttpApiHome.getLiveChannel(LiveType, new HttpApiCallBackArr<AppLiveChannel>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppLiveChannel> retModel) {
                if (code==1){
                    if (retModel !=null){
                        liveChannelList = retModel;
                        adpater.setAppLiveChannel(retModel);

                        if (LiveType ==1){
                            if (openLivebean!=null){
                                for(int i =0;i<retModel.size();i++){
                                    if (openLivebean.channelId==retModel.get(i).id){
                                        AppLiveChannel appLiveChannel = new AppLiveChannel();
                                        appLiveChannel.id = openLivebean.channelId;
                                        appLiveChannel.title  = openLivebean.ChannelName;
                                        channel = appLiveChannel;
                                        adpater.setClickPostion(i);
                                    }
                                }
                            }
                        }else if(LiveType == 2){
                            if (voiceOpenLiveBean!=null){
                                for(int i =0;i<retModel.size();i++){
                                    if (voiceOpenLiveBean.channelId==retModel.get(i).id){
                                        AppLiveChannel appLiveChannel = new AppLiveChannel();
                                        appLiveChannel.id = voiceOpenLiveBean.channelId;
                                        appLiveChannel.title  = voiceOpenLiveBean.ChannelName;
                                        channel = appLiveChannel;
                                        adpater.setClickPostion(i);
                                    }
                                }
                            }
                        }else if(LiveType == 6){
                            if (openLivebean!=null){
                                for(int i =0;i<retModel.size();i++){
                                    if (openLivebean.LiveShopChannelID==retModel.get(i).id){
                                        AppLiveChannel appLiveChannel = new AppLiveChannel();
                                        appLiveChannel.id = openLivebean.LiveShopChannelID;
                                        appLiveChannel.title  = openLivebean.LiveShopChannelName;
                                        channel = appLiveChannel;
                                        adpater.setClickPostion(i);
                                    }
                                }
                            }
                        }



                    }
                }

            }
        });
    }
}
