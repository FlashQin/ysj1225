package com.kalacheng.live.component.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.live.component.adapter.AnchorMoreAdpater;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.live.R;
import com.kalacheng.util.bean.SimpleImageSrcTextBean;
import com.kalacheng.util.view.ItemDecoration;
import com.xuantongyun.livecloud.protocol.PulicUtils;
import com.xuantongyun.livecloud.protocol.VideoLiveUtils;

import java.util.ArrayList;
import java.util.List;

// 直播间 更多（观众）
public class AudienceMoreDialogFragment extends BaseDialogFragment {
    @Override
    protected int getLayoutId() {
        return  R.layout.anchor_more;
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
        final List<SimpleImageSrcTextBean> list = new ArrayList<>();
        SimpleImageSrcTextBean bean1 = new SimpleImageSrcTextBean();
        bean1.text ="分享";
        bean1.src = R.mipmap.live_share;
        list.add(bean1);

        /*SimpleImageSrcTextBean bean2 = new SimpleImageSrcTextBean();
        bean2.text ="心愿单";
        bean2.src = R.mipmap.wish_list;
        list.add(bean2);*/

       /* SimpleImageSrcTextBean bean3 = new SimpleImageSrcTextBean();
        bean3.text ="红包";
        bean3.src = R.mipmap.red;
        list.add(bean3);
*/
        SimpleImageSrcTextBean bean4 = new SimpleImageSrcTextBean();
        bean4.text ="静音";
        if (LiveConstants.IsRemoteAudio){
            bean4.src = R.mipmap.live_voice_close;
        }else {
            bean4.src = R.mipmap.voice_open;
        }

        list.add(bean4);

        SimpleImageSrcTextBean bean5 = new SimpleImageSrcTextBean();
        bean5.text ="粉丝团";
        bean5.src = R.mipmap.live_fans;
        list.add(bean5);

        SimpleImageSrcTextBean bean6 = new SimpleImageSrcTextBean();
        bean6.text ="任务";
        bean6.src = R.mipmap.task;
        list.add(bean6);

        RecyclerView anchor_more = mRootView.findViewById(R.id.anchor_more);
        GridLayoutManager manager = new GridLayoutManager(mContext,5);
        anchor_more.setLayoutManager(manager);
        anchor_more.addItemDecoration(new ItemDecoration(mContext,0,10,20));
        final AnchorMoreAdpater adpater = new AnchorMoreAdpater(mContext,list);
        anchor_more.setAdapter(adpater);

        //更多监听
        adpater.setAnchorMoreItmeCallBack(new AnchorMoreAdpater.AnchorMoreItmeCallBack() {
            @Override
            public void onClick(String name, int position) {
                if (name.equals("任务")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AudienceTask,null);
                }else if(name.equals("分享")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LiveShare,null);
                }else if(name.equals("静音")){

                    if (LiveConstants.IsRemoteAudio){
                        list.get(position).src = R.mipmap.voice_open;
                        LiveConstants.IsRemoteAudio = false;
                        if (LiveConstants.liveMicStatus != 0){
                            PulicUtils.getInstance().muteAllRemoteAudioStreams(false);
                        }else {
                            VideoLiveUtils.getInstance().setVolume(0.5f);
                        }
                    }else {
                        list.get(position).src = R.mipmap.live_voice_close;
                        LiveConstants.IsRemoteAudio = true;
                        if (LiveConstants.liveMicStatus != 0){
                            PulicUtils.getInstance().muteAllRemoteAudioStreams(true);
                        }else {
                            VideoLiveUtils.getInstance().setVolume(0f);
                        }
                    }
                    adpater.notifyDataSetChanged();

                }else if(name.equals("粉丝团")){
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddFansGroup,null);
                }
            }
        });
   }
}
