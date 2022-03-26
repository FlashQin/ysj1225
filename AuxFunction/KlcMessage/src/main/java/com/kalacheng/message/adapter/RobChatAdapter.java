package com.kalacheng.message.adapter;

import android.Manifest;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.busooolive.model.OOOReturn;
import com.kalacheng.commonview.utils.SmallLiveRoomDialogFragment;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.ApiPushChat;
import com.kalacheng.message.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class RobChatAdapter extends RecyclerView.Adapter<RobChatAdapter.Holder> {


    List<ApiPushChat> list;
    ProcessResultUtil mProcessResultUtil;

    public RobChatAdapter(FragmentActivity activity) {
        list = new ArrayList<>();
        mProcessResultUtil = new ProcessResultUtil(activity);
    }

    /**
     * 增加数据
     */
    public void addData(List<ApiPushChat> list) {
        int count = list.size();
        this.list.clear();
        if (count != 0) {
            notifyItemRangeRemoved(0, count);
//            notifyDataSetChanged();
        }
        this.list.addAll(list);
        notifyItemRangeChanged(0, this.list.size());
    }

    public void removed() {
        int count = list.size();
        this.list.clear();
        if (count != 0) {
            notifyItemRangeRemoved(0, count);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rob_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final ApiPushChat pushChat = list.get(position);
        if (payload == null) {
            ImageLoader.display(pushChat.avatar, holder.avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            if (pushChat.chatType == 1) {
                holder.callTypeTv.setText("视频通话");
                holder.callTypeTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_shipin, 0);
                holder.robCahtTv.setBackgroundResource(R.drawable.bg_message_count);
            } else {
                holder.callTypeTv.setText("语音通话");
                holder.callTypeTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_yuyin, 0);
                holder.robCahtTv.setBackgroundResource(R.drawable.bg_oval_blue);
            }
            holder.callPicTv.setText(pushChat.coin + SpUtil.getInstance().getCoinUnit()+"/分钟");
            holder.robCahtTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mProcessResultUtil.requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO
                        }, new Runnable() {
                            @Override
                            public void run() {
                                HttpApiOOOCall.robPushChat(pushChat.sessionID, pushChat.userId, new HttpApiCallBack<OOOReturn>() {
                                    @Override
                                    public void onHttpRet(int code, String msg, final OOOReturn retModel) {
                                        if (code == 1 && retModel != null) {
                                            LiveConstants.mOOOSessionID = retModel.sessionID;
                                            // 1v1 如果是在语音直播间 先关闭掉直播间 在进入1v1
                                            if (LiveConstants.isInsideRoomType == 2 && LiveConstants.ANCHORID != HttpClient.getUid()) {
                                                if (LiveConstants.isSamll) {
                                                    // 是否最小化
                                                    SmallLiveRoomDialogFragment.getInstance().closeRoom();
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (pushChat.chatType == 1){
                                                                ARouter.getInstance().build(ARouterPath.One2OneSeekChatLive)
                                                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                                                        .navigation();
                                                            }else if(pushChat.chatType ==2){
                                                                ARouter.getInstance().build(ARouterPath.OneVoiceSeekChatLive)
                                                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                                                        .navigation();
                                                            }
                                                        }
                                                    }, 500);
                                                    return;
                                                }else {
                                                    // 是否在房间内 在语音房间 先退出语音房间 在进行1v1通话
                                                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ExitRoom, null);
                                                    LiveConstants.isInsideRoomType = 0;
                                                }
                                            }
                                            if (pushChat.chatType == 1){
                                                ARouter.getInstance().build(ARouterPath.One2OneSeekChatLive)
                                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                                        .navigation();
                                            }else if(pushChat.chatType ==2){
                                                ARouter.getInstance().build(ARouterPath.OneVoiceSeekChatLive)
                                                        .withParcelable(ARouterValueNameConfig.OOOLiveSvipReceiveJoin, retModel)
                                                        .withLong(ARouterValueNameConfig.OOOLiveJFeeUid, retModel.feeId)
                                                        .navigation();
                                            }
                                        } else {
                                            ToastUtil.show(msg);
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        RoundedImageView avatarIv;
        TextView callTypeTv;
        TextView callPicTv;
        TextView robCahtTv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            callTypeTv = itemView.findViewById(R.id.callTypeTv);
            callPicTv = itemView.findViewById(R.id.callPicTv);
            robCahtTv = itemView.findViewById(R.id.robCahtTv);

        }
    }

}
