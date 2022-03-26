package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.livecommon.adapter.RoomModeAdapter;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.buslivebas.entity.LiveRoomType;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.livecommon.R;
import com.klc.bean.LiveRoomTypeBean;
import com.klc.bean.OpenLiveBean;
import com.klc.bean.VoiceOpenLiveBean;

import java.util.List;

// 修改房间模式
public class RoomModeDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private ImageView room_mode_close;
    private TextView room_mode_type, room_mode_set;

    private int SelectionID;
    private String mContent = "";
    private String Name;
    private String showId;

    private OpenLiveBean openLivebean;
    private VoiceOpenLiveBean voiceOpenLiveBean;
    private LiveRoomTypeBean liveRoomTypeBean;

    private RecyclerView recycler;
    private RoomModeAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.room_mode_dialog;
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
        liveRoomTypeBean = getArguments().getParcelable("LiveRoomTypeBean");
        showId = getArguments().getString("showId");
        initView();
        initAdapter();
    }

    public void initView() {
        room_mode_close = mRootView.findViewById(R.id.room_mode_close);
        room_mode_type = mRootView.findViewById(R.id.room_mode_type);
        room_mode_set = mRootView.findViewById(R.id.room_mode_set);

        room_mode_close.setOnClickListener(this);
        room_mode_set.setOnClickListener(this);

        if (liveRoomTypeBean.LiveType == 1) {
            openLivebean = SpUtil.getInstance().<OpenLiveBean>getModel(LiveConstants.LiveOpenValue, OpenLiveBean.class);
            if (null != openLivebean && null != openLivebean.roomTypeName) {
                room_mode_type.setText("当前房间模式：" + openLivebean.roomTypeName);
                SelectionID = openLivebean.type;
                mContent = openLivebean.typeVal;
                Name = openLivebean.roomTypeName;
            }
        } else {
            voiceOpenLiveBean = SpUtil.getInstance().<VoiceOpenLiveBean>getModel(LiveConstants.VoiceLiveOpenValue, VoiceOpenLiveBean.class);
            if (null != voiceOpenLiveBean && null != voiceOpenLiveBean.roomTypeName) {
                room_mode_type.setText("当前房间模式：" + voiceOpenLiveBean.roomTypeName);
                SelectionID = voiceOpenLiveBean.type;
                mContent = voiceOpenLiveBean.typeVal;
                Name = voiceOpenLiveBean.roomTypeName;
            }
        }
    }

    private void initAdapter() {
        recycler = mRootView.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new RoomModeAdapter(mContext);
        recycler.setAdapter(adapter);
        adapter.setListener(new RoomModeAdapter.itemListener() {
            @Override
            public void onChoice(LiveRoomType bean, String content, int position) {
                SelectionID = bean.roomType;
                mContent = content;
                Name = bean.roomName;
            }
        });
        getSetRoomStatus(liveRoomTypeBean.LiveType, showId);
    }

    //获取房间模式
    private void getSetRoomStatus(int liveType, String showId) {
        HttpApiPublicLive.getLiveRoomType(liveType, showId, new HttpApiCallBackArr<LiveRoomType>() {
            @Override
            public void onHttpRet(int code, String msg, List<LiveRoomType> retModel) {
                if (code == 1 && null != retModel && retModel.size() > 0) {
//                    Name = retModel.get(0).roomName;
//                    SelectionID = retModel.get(0).roomType;
//                    adapter.setEdit(0, mContent);
                    boolean isNull = true;
                    for (int i = 0; i < retModel.size(); i++) {
                        if (SelectionID == retModel.get(i).roomType) {
                            Name = retModel.get(i).roomName;
                            SelectionID = retModel.get(i).roomType;
                            adapter.setEdit(i, mContent);
                            isNull = false;
                        }
                    }
                    if (isNull){
                        Name = retModel.get(0).roomName;
                        SelectionID = retModel.get(0).roomType;
                        adapter.setEdit(0, mContent);
                    }
                    adapter.getData(retModel);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.room_mode_close) {
            dismiss();
        } else if (id == R.id.room_mode_set) {
            mContent = adapter.getEdit();
            if (SelectionID == 1 && TextUtils.isEmpty(mContent)) {
                ToastUtil.show("密码为空");
                return;
            }
            if (SelectionID == 1 && mContent.length() != 6) {
                ToastUtil.show("请输入6位数密码");
                return;
            }
            if (SelectionID == 2 && (TextUtils.isEmpty(mContent) || Double.parseDouble(mContent) <= 0)) {
                ToastUtil.show("收费为空");
                return;
            }
            if (SelectionID == 3 && (TextUtils.isEmpty(mContent) || Double.parseDouble(mContent) <= 0)) {
                ToastUtil.show("计时为空");
                return;
            }
            final LiveRoomTypeBean bean = new LiveRoomTypeBean();
            bean.id = SelectionID;
            bean.mContent = mContent;
            bean.name = Name;
            if (liveRoomTypeBean.LiveType == 1) {
                if (liveRoomTypeBean.type == 1) {
                    HttpApiPublicLive.updateLiveType(LiveConstants.ROOMID, SelectionID, mContent, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                OpenLiveBean openLive = new OpenLiveBean();
                                openLive.roomTypeName = Name;
                                openLive.type = SelectionID;
                                openLive.typeVal = mContent;
                                if (openLivebean != null) {
                                    openLive.thumb = openLivebean.thumb;
                                    openLive.ChannelName = openLivebean.ChannelName;
                                    openLive.channelId = openLivebean.channelId;
                                    openLive.title = openLivebean.title;
                                    openLive.nickname = openLivebean.nickname;
                                }
                                SpUtil.getInstance().putModel(LiveConstants.LiveOpenValue, openLive);
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceLiveTypeSusser, openLive);
                                ToastUtil.show(msg);
                                dismiss();
                            } else {
                                ToastUtil.show(msg);
                            }
                        }
                    });
                } else {
                    OpenLiveBean openLive = new OpenLiveBean();
                    openLive.roomTypeName = Name;
                    openLive.type = SelectionID;
                    openLive.typeVal = mContent;
                    if (openLivebean != null) {
                        openLive.thumb = openLivebean.thumb;
                        openLive.ChannelName = openLivebean.ChannelName;
                        openLive.channelId = openLivebean.channelId;
                        openLive.title = openLivebean.title;
                        openLive.nickname = openLivebean.nickname;
                    }
                    SpUtil.getInstance().putModel(LiveConstants.LiveOpenValue, openLive);
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceLiveTypeValue, bean);
                    dismiss();
                }
            } else {
                if (liveRoomTypeBean.type == 1) {
                    HttpApiPublicLive.updateLiveType(LiveConstants.ROOMID, SelectionID, mContent, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                VoiceOpenLiveBean voiceopenLive = new VoiceOpenLiveBean();
                                voiceopenLive.roomTypeName = Name;
                                voiceopenLive.type = SelectionID;
                                voiceopenLive.typeVal = mContent;
                                if (voiceOpenLiveBean != null) {
                                    voiceopenLive.thumb = voiceOpenLiveBean.thumb;
                                    voiceopenLive.ChannelName = voiceOpenLiveBean.ChannelName;
                                    voiceopenLive.channelId = voiceOpenLiveBean.channelId;
                                    voiceopenLive.title = voiceOpenLiveBean.title;
                                    voiceopenLive.nickname = voiceOpenLiveBean.nickname;
                                    voiceopenLive.voiceBgId = voiceOpenLiveBean.voiceBgId;
                                    voiceopenLive.voiceBgUrl = voiceOpenLiveBean.voiceBgUrl;
                                }
                                SpUtil.getInstance().putModel(LiveConstants.VoiceLiveOpenValue, voiceopenLive);
                                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceLiveTypeSusser, voiceopenLive);
                                ToastUtil.show(msg);
                                dismiss();
                            } else {
                                ToastUtil.show(msg);
                            }
                        }
                    });
                } else {
                    VoiceOpenLiveBean voiceopenLive = new VoiceOpenLiveBean();
                    voiceopenLive.roomTypeName = Name;
                    voiceopenLive.type = SelectionID;
                    voiceopenLive.typeVal = mContent;
                    if (voiceOpenLiveBean != null) {

                        voiceopenLive.thumb = voiceOpenLiveBean.thumb;
                        voiceopenLive.ChannelName = voiceOpenLiveBean.ChannelName;
                        voiceopenLive.channelId = voiceOpenLiveBean.channelId;
                        voiceopenLive.title = voiceOpenLiveBean.title;
                        voiceopenLive.nickname = voiceOpenLiveBean.nickname;
                        voiceopenLive.voiceBgId = voiceOpenLiveBean.voiceBgId;
                        voiceopenLive.voiceBgUrl = voiceOpenLiveBean.voiceBgUrl;
                    }
                    SpUtil.getInstance().putModel(LiveConstants.VoiceLiveOpenValue, voiceopenLive);
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChoiceLiveTypeValue, bean);
                    dismiss();
                }
            }

        }
    }
}
