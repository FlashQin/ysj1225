package com.kalacheng.message.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.ApiUsersVideoBlack;
import com.kalacheng.message.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.base.http.HttpApiCallBack;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.IntegerCallback;
import cn.jpush.im.android.api.model.GroupInfo;

public class ChatSettingActivity extends BaseActivity {

    private boolean noChat;
    private boolean noVioce;
    private boolean noVideo;
    private int noDisturb;
    private boolean isSingle;

    private ImageView noChatIv;
    private ImageView noVioceIv;
    private ImageView noVideoIv;
    private ImageView noDisturbIv;

    private LinearLayout singleLl;
    private LinearLayout noLl;

    private LinearLayout groupLl;
    private RelativeLayout lookGroupMemberRl;
    private String toUid;
    private long groupID;
    private GroupInfo settingGroupInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_setting);
        setStatusBarWhite(false);
        toUid = getIntent().getStringExtra("uid");
        isSingle = getIntent().getBooleanExtra("isSingle", true);

        singleLl = findViewById(R.id.singleLl);
        noLl = findViewById(R.id.noLl);

        if (ConfigUtil.getBoolValue(R.bool.containOne2One)) {
            noLl.setVisibility(View.VISIBLE);
        } else {
            noLl.setVisibility(View.GONE);
        }

        groupLl = findViewById(R.id.groupLl);

        singleLl.setVisibility(isSingle ? View.VISIBLE : View.GONE);
        groupLl.setVisibility(isSingle ? View.GONE : View.VISIBLE);

        lookGroupMemberRl = findViewById(R.id.lookGroupMemberRl);

        findViewById(R.id.backIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        noChatIv = findViewById(R.id.noChatIv);
        noVioceIv = findViewById(R.id.noVoiceIv);
        noVideoIv = findViewById(R.id.noVideoIv);
        noDisturbIv = findViewById(R.id.noDisturbIv);

        noChatIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNoChat(!noChat, false);
            }
        });
        noVioceIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNoVioce(!noVioce, false);
            }
        });
        noVideoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNoVideo(!noVideo, false);
            }
        });

        // 获取极光 群租详情
        JMessageClient.getGroupInfo(Long.parseLong(toUid), new GetGroupInfoCallback() {
            @Override
            public void gotResult(int i, String s, GroupInfo groupInfo) {
                if (null != groupInfo) {
                    settingGroupInfo = groupInfo;
                    setNoDisturb(groupInfo.getNoDisturb());
                }
            }
        });

        // 通过极光群组详情 设置群组免打扰状态
        noDisturbIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingGroupInfo.setNoDisturb(settingGroupInfo.getNoDisturb() == 0 ? 1 : 0, new IntegerCallback() {
                    @Override
                    public void gotResult(int i, String s, Integer integer) {
                        JMessageClient.getGroupInfo(Long.parseLong(toUid), new GetGroupInfoCallback() {
                            @Override
                            public void gotResult(int i, String s, GroupInfo groupInfo) {
                                if (null != groupInfo) {
                                    settingGroupInfo = groupInfo;
                                    setNoDisturb(groupInfo.getNoDisturb());
                                }
                            }
                        });
                    }
                });
            }
        });

        lookGroupMemberRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                startActivity(new Intent(getBaseContext(), GroupMemberActivity.class).putExtra("uid", toUid));
            }
        });

        getBlockinfo();
    }

    public void setNoChat(boolean noChat, boolean isInit) {
        this.noChat = noChat;
        noChatIv.setImageResource(noChat ? R.mipmap.xiaoxishezhi_kai : R.mipmap.xiaoxishezhi_guan);
        if (ConfigUtil.getBoolValue(R.bool.containOne2One)) {
            noLl.setVisibility(noChat ? View.GONE : View.VISIBLE);
        }
        if (!isInit) {
            blockOperation(0);
            if (noChat) {
                ImMessageUtil.getInstance().addUsersToBlack(toUid);
            } else {
                ImMessageUtil.getInstance().delUsersFromBlack(toUid);
                noVioce = false;
                noVioceIv.setImageResource(R.mipmap.xiaoxishezhi_guan);
                noVideo = false;
                noVideoIv.setImageResource(R.mipmap.xiaoxishezhi_guan);
            }
        }
    }

    public void setNoVioce(boolean noVioce, boolean isInit) {
        this.noVioce = noVioce;
        noVioceIv.setImageResource(noVioce ? R.mipmap.xiaoxishezhi_kai : R.mipmap.xiaoxishezhi_guan);
        if (!isInit) {
            blockOperation(1);
        }
    }

    public void setNoVideo(boolean noVideo, boolean isInit) {
        this.noVideo = noVideo;
        noVideoIv.setImageResource(noVideo ? R.mipmap.xiaoxishezhi_kai : R.mipmap.xiaoxishezhi_guan);
        if (!isInit) {
            blockOperation(2);
        }
    }

    public void setNoDisturb(int noDisturb) {
        this.noDisturb = noDisturb;
        noDisturbIv.setImageResource(noDisturb == 1 ? R.mipmap.xiaoxishezhi_kai : R.mipmap.xiaoxishezhi_guan);
    }

    /**
     * 获取拉黑信息
     */
    private void getBlockinfo() {
        HttpApiChatRoom.getBlockinfo(Long.parseLong(TextUtils.isEmpty(toUid) ? "-1" : toUid), new HttpApiCallBack<ApiUsersVideoBlack>() {
            @Override
            public void onHttpRet(int code, String msg, ApiUsersVideoBlack retModel) {
                if (code == 1 && retModel != null) {
                    setNoChat(retModel.userBlack == 1, true);
                    setNoVioce(retModel.voiceBlack == 1, true);
                    setNoVideo(retModel.videoBlack == 1, true);
                }
            }
        });
    }

    private void blockOperation(int type) {
        String uid = getIntent().getStringExtra("uid");
        uid = TextUtils.isEmpty(uid) ? "-1" : uid;
        HttpApiChatRoom.blockOperation(type, Long.parseLong(uid), new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {

            }
        });
    }

}
