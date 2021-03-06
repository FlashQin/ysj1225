package com.kalacheng.commonview.jguangIm;

import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.util.R;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.WordUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.enums.MessageStatus;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;


public class ImMessageUtil {

    private static final String TAG = "??????IM";
    private static final String PWD_SUFFIX = "JMessage";//????????????IM???????????????????????????id+"PUSH"?????????????????????
    //????????????uid??????????????????????????????
    public static final String PREFIX = "message";
    public static final String IM_NAME = "imName";
    public static final String IM_AVATAR = "imAvatar";
    private Map<String, Long> mMap;
    //???????????????????????????????????????
    private MessageSendingOptions mOptions;
    private SimpleDateFormat mSimpleDateFormat;
    private String mImageString;
    private String mVoiceString;
    private String mGiftString;
    private String mCallVoiceString;
    private String mCallVideoString;
    private String mShopOrderString;
    private String mLocationString;
    private String mCpString;
    private boolean isLoginIm;
    private boolean isSingle = true;

    private static ImMessageUtil sInstance;

    private ImMessageUtil() {
        mMap = new HashMap<>();
        mOptions = new MessageSendingOptions();
        mOptions.setShowNotification(true);//??????????????????????????????????????????????????????????????????????????????????????????
        mSimpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        mImageString = WordUtil.getString(R.string.im_type_image);
        mVoiceString = WordUtil.getString(R.string.im_type_voide);
        mGiftString = WordUtil.getString(R.string.im_type_gift);
        mCallVoiceString = WordUtil.getString(R.string.im_type_call_voice);
        mCallVideoString = WordUtil.getString(R.string.im_type_call_video);
        mShopOrderString = WordUtil.getString(R.string.im_type_shop_order);

        isLoginIm = false;
    }

    public static ImMessageUtil getInstance() {
        if (sInstance == null) {
            synchronized (ImMessageUtil.class) {
                if (sInstance == null) {
                    sInstance = new ImMessageUtil();
                }
            }
        }
        return sInstance;
    }


    public void init() {
        //??????????????????
        if (JMessageClient.init(BaseApplication.getInstance(), false)) {
            L.e(TAG, "???????????????");
        } else {
            L.e(TAG, "???????????????");
        }
        ImPushUtil.getInstance().init(BaseApplication.getInstance());
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public boolean isLoginIm() {
        return isLoginIm;
    }

    public void setLoginIm(boolean loginIm) {
        isLoginIm = loginIm;
    }

    /**
     * ????????????IM conversation ????????????Id
     */
    private String getAppUid(Conversation conversation) {
        if (conversation == null) {
            return "";
        }
        Object targetInfo = conversation.getTargetInfo();
        if (targetInfo == null) {
            return "";
        }
        if (targetInfo instanceof UserInfo) {
            String userName = ((UserInfo) targetInfo).getUserName() + "";
            return userName;
        } else if (targetInfo instanceof GroupInfo) {
            String userName = ((GroupInfo) targetInfo).getGroupID() + "";
            return userName;
        }
        return "";
    }


    /**
     * ????????????IM???????????? ??????App?????????uid
     */
    private String getAppUid(Message msg) {
        if (msg == null) {
            return "";
        }
        UserInfo userInfo = msg.getFromUser();
        if (userInfo == null) {
            return "";
        }
        String userName = userInfo.getUserName();
        return userName;
    }

    /**
     * ????????????IM
     */
    public void loginJMessage(final String uid) {
        JMessageClient.login(uid, uid + PWD_SUFFIX, new BasicCallback() {
            @Override
            public void gotResult(int code, String msg) {
                L.e(TAG, "uid: " + uid);
                L.e(TAG, "??????????????????---gotResult--->code: " + code + " msg: " + msg);
                if (code == 801003 || code == 871504) {//???????????????
                    L.e(TAG, "???????????????????????????");
                    registerAndLoginJMessage(uid);
                } else if (code == 871201) {
                    L.e(TAG, "???????????????????????????????????????");
                    loginJMessage(uid);
                } else if (code == 0) {
                    L.e(TAG, "??????IM????????????");
                    ApiUserInfo apiUserInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);
                    UserInfo userInfo = JMessageClient.getMyInfo();
                    userInfo.setNickname(apiUserInfo.username);
                    JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            L.e(TAG, "????????????????????????????????????---gotResult--->code: " + i + " msg: " + s);

                            ApiUserInfo apiUserInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);
                            UserInfo userInfo = JMessageClient.getMyInfo();
                            userInfo.setUserExtras("avatarUrlStr", apiUserInfo.avatar);
                            JMessageClient.updateMyInfo(UserInfo.Field.extras, userInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    L.e("??????IM", "????????????????????????????????????---gotResult--->code: " + i + " msg: " + s);
                                }
                            });
                        }
                    });

                    isLoginIm = true;
                    JMessageClient.registerEventReceiver(ImMessageUtil.this);
                    EventBus.getDefault().post(new ImLoginEvent(true));
                    refreshAllUnReadMsgCount();
                } else {
                    loginJMessage(uid);
                }
            }
        });

    }

    /**
     * ?????????????????????IM
     */
    private void registerAndLoginJMessage(final String uid) {


        String imName = (String) SpUtil.getInstance().getSharedPreference(IM_NAME, "");
        String imAvatar = (String) SpUtil.getInstance().getSharedPreference(IM_AVATAR, "");

        ApiUserInfo userInfo = SpUtil.getInstance().getModel("UserInfo", ApiUserInfo.class);
        if (TextUtils.isEmpty(imName)) {
            imName = userInfo.username;
        }
        if (TextUtils.isEmpty(imAvatar)) {
            imAvatar = userInfo.avatar;
        }

        RegisterOptionalUserInfo optionalUserInfo = new RegisterOptionalUserInfo();
        optionalUserInfo.setNickname(imName);
        final String finalImAvatar = imAvatar;
        optionalUserInfo.setExtras(new HashMap<String, String>() {{
            put("avatarUrlStr", finalImAvatar);
        }});

        JMessageClient.register(uid, uid + PWD_SUFFIX, optionalUserInfo, new BasicCallback() {

            @Override
            public void gotResult(int code, String msg) {
                L.e(TAG, "??????????????????---gotResult--->code: " + code + " msg: " + msg);
                if (code == 0) {
                    L.e(TAG, "??????IM????????????");
                    loginJMessage(uid);

                }
            }
        });
    }

    /**
     * ????????????IM
     */
    public void logoutEMClient() {
        JMessageClient.unRegisterEventReceiver(ImMessageUtil.this);
        JMessageClient.logout();
        ImPushUtil.getInstance().logout();
        isLoginIm = false;
        //EventBus.getDefault().post(new ImLoginEvent(false));
        L.e(TAG, "??????IM??????");
    }

    /**
     * ????????????????????????????????????
     */
    public void onEvent(NotificationClickEvent event) {
        Message message = event.getMessage();
        if (message.getTargetType() == ConversationType.single) {
            ARouter.getInstance().build(ARouterPath.ChatRoomActivity).withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .withString(ARouterValueNameConfig.TO_UID, message.getTargetID())
                    .withString(ARouterValueNameConfig.Name, message.getTargetName())
                    .withBoolean(ARouterValueNameConfig.IS_SINGLE, true)
                    .navigation();
        } else {
            ARouter.getInstance().build(ARouterPath.ChatRoomActivity).withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .withString(ARouterValueNameConfig.TO_UID, message.getTargetID())
                    .withString(ARouterValueNameConfig.Name, message.getTargetName())
                    .withBoolean(ARouterValueNameConfig.IS_SINGLE, false)
                    .navigation();
        }
        ImMessageUtil.getInstance().markAllMessagesAsRead(message.getTargetID());
        EventBus.getDefault().post(new ImGetConversationEvent());
    }

    /**
     * ????????????????????????
     */
    public List<ImUserMsgEvent> getConversationUserList() {
        List<Conversation> conversationList = JMessageClient.getConversationList();
        List<ImUserMsgEvent> userList = new ArrayList<>();
        if (conversationList != null) {
            L.e("?????????????????? size = " + conversationList.size());
            for (Conversation conversation : conversationList) {
                final ImUserMsgEvent imUserMsgEvent = new ImUserMsgEvent();
                L.e("JSON =" + conversation.toJsonString());
                Object objInfo = conversation.getTargetInfo();
                if (conversation.getType() == ConversationType.group) {
                    imUserMsgEvent.setGroupInfo((GroupInfo) objInfo);
                } else {
                    imUserMsgEvent.setUserInfo((UserInfo) objInfo);
                    imUserMsgEvent.setNickName(conversation.getTitle());
                }
                Message msg = conversation.getLatestMessage();
                imUserMsgEvent.setLastMessage(getMessageString(msg));
                imUserMsgEvent.setLastTime(getMessageTimeString(msg));
                L.e(TAG, getAppUid(conversation));
                imUserMsgEvent.setUid(getAppUid(conversation));
                imUserMsgEvent.setUnReadCount(conversation.getUnReadMsgCnt());
                userList.add(imUserMsgEvent);
            }
            refreshAllUnReadMsgCount();
        }
        return userList;
    }

    /**
     * ??????????????????
     */
    public List<ImMessageBean> getChatMessageList(String toUid) {
        List<ImMessageBean> result = new ArrayList<>();

        Conversation conversation = isSingle ? JMessageClient.getSingleConversation(toUid) : JMessageClient.getGroupConversation(Long.parseLong(toUid));
        if (conversation == null) {
            return result;
        }
        List<Message> msgList = conversation.getAllMessage();
        if (msgList == null) {
            return result;
        }
        L.e("????????????msgList" + msgList.size());
        int size = msgList.size();
        if (size < 20) {
            Message latestMessage = conversation.getLatestMessage();
            if (latestMessage == null) {
                return result;
            }
            List<Message> list = conversation.getMessagesFromNewest(latestMessage.getId(), 20 - size);
            if (list == null) {
                return result;
            }
            list.addAll(msgList);
            msgList = list;
        }
        String uid = String.valueOf(HttpClient.getUid());
        for (Message msg : msgList) {
            String from = getAppUid(msg);
            int type = getMessageType(msg);
            if (!TextUtils.isEmpty(from) && type != 0) {
                boolean self = from.equals(uid);
                ImMessageBean bean = new ImMessageBean(from, msg, type, self);
                if (self && msg.getServerMessageId() == 0 || msg.getStatus() == MessageStatus.send_fail) {
                    bean.setSendFail(true);
                }
                result.add(bean);
            }
        }
        L.e("????????????result" + result.size());
        return result;
    }

    /**
     * ????????????????????????????????????
     */
    public int getUnReadMsgCount(String uid) {
        Conversation conversation = JMessageClient.getSingleConversation(uid);
        if (conversation != null) {
            return conversation.getUnReadMsgCnt();
        } else {
            conversation = JMessageClient.getGroupConversation(Long.parseLong(uid));
            if (conversation != null) {
                return conversation.getUnReadMsgCnt();
            }
        }
        return 0;
    }

    /**
     * ?????????????????????????????????
     */
    public void refreshAllUnReadMsgCount() {
        EventBus.getDefault().post(new ImUnReadCountEvent(String.valueOf(JMessageClient.getAllUnReadMsgCount())));
    }

    /**
     * ?????????????????????????????????
     */
    public String getAllUnReadMsgCount() {
        int unReadCount = JMessageClient.getAllUnReadMsgCount();
        L.e(TAG, "??????????????????----->" + unReadCount);
        String res = "";
        if (unReadCount > 99) {
            res = "99+";
        } else if (unReadCount > 0) {
            res = String.valueOf(unReadCount);
        } else {
            res = "";
        }
        return res;
    }

    /**
     * ????????????????????????????????????
     *
     * @param toUid ??????uid
     */
    public boolean markAllMessagesAsRead(String toUid) {
        if (!TextUtils.isEmpty(toUid)) {
            Conversation conversation = JMessageClient.getSingleConversation(toUid);
            if (conversation != null) {
                conversation.resetUnreadCount();
                refreshAllUnReadMsgCount();
                return true;
            } else {
                Conversation groupConversation = JMessageClient.getGroupConversation(Long.parseLong(toUid));
                if (groupConversation != null) {
                    groupConversation.resetUnreadCount();
                    refreshAllUnReadMsgCount();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * ?????? ????????????????????????
     */
    public void getGroupList() {
        JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage,
                                  List<Long> groupIDList) {
                if (responseCode == 0) {

                    for (Long groupID : groupIDList) {
                        JMessageClient.getGroupInfo(groupID, new GetGroupInfoCallback() {
                            @Override
                            public void gotResult(int i, String s, GroupInfo groupInfo) {
                                if (i == 0) {
                                    EventBus.getDefault().post(new ImMyJoinGroupInfo(groupInfo));
                                }

                            }
                        });
                    }


                } else {
//                    tvDisplay.setText("????????????!\nresponseCode:" + responseCode + "\nresponseMsg" + responseMessage);
                }

            }
        });
    }

    /**
     * ???????????????????????????  ?????????????????????
     */
    public void markAllConversationAsRead() {
        List<Conversation> list = JMessageClient.getConversationList();
        if (list == null) {
            return;
        }
        for (Conversation conversation : list) {
            conversation.resetUnreadCount();
        }
        refreshAllUnReadMsgCount();
    }

    /**
     * ???????????? ??????????????????????????????
     */
    public void onEvent(MessageEvent event) {
        //????????????
        Message msg = event.getMessage();
        L.e(TAG, "????????????--->" + msg.toJson());

        int type = getMessageType(msg);
        if (type == 0) {
            return;
        }
        String uid = getAppUid(msg);
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        refreshConversationLastMsg(msg);
        if (msg.getTargetType() == ConversationType.single && HttpClient.getUid() == Long.parseLong(msg.getFromID())) {
            EventBus.getDefault().post(new ImMessageBean(uid, msg, type, true));
        } else {
            EventBus.getDefault().post(new ImMessageBean(uid, msg, type, false));
        }
    }

    /**
     * ???????????? ??????????????????????????????
     */
    public void onEvent(LoginStateChangeEvent event) {
        L.e(TAG, "?????????????????? ??????" + event.getReason());
        if (isLoginIm) {
            loginJMessage(event.getMyInfo().getUserName());
        }
    }

    /***
     * ????????????????????????????????????
     * */
    public void refreshConversationLastMsg(Message message) {
        ImUserMsgEvent imUserMsgEvent = new ImUserMsgEvent();
        imUserMsgEvent.setUid(getMessageUid(message));
        imUserMsgEvent.setLastMessage(getMessageString(message));
        imUserMsgEvent.setUnReadCount(getUnReadMsgCount(getMessageUid(message)));
        imUserMsgEvent.setLastTime(getMessageTimeString(message));
        if (message.getTargetType() == ConversationType.single) {
            imUserMsgEvent.setUserInfo((UserInfo) message.getTargetInfo());
            imUserMsgEvent.setNickName(message.getTargetName());
        } else if (message.getTargetType() == ConversationType.group) {
            imUserMsgEvent.setGroupInfo((GroupInfo) message.getTargetInfo());
        }
        EventBus.getDefault().post(imUserMsgEvent);
        refreshAllUnReadMsgCount();
    }

    /**
     * ??????????????????
     */
    public void onEvent(OfflineMessageEvent event) {
        String from = getAppUid(event.getConversation());
        L.e(TAG, "?????????????????????-------->?????????" + from);
        if (!TextUtils.isEmpty(from) && !from.equals(HttpClient.getUid())) {
            List<Message> list = event.getOfflineMessageList();
            if (list != null && list.size() > 0) {
                ImUserBean bean = new ImUserBean();
//                bean.setId(from);
                Message message = list.get(list.size() - 1);
                bean.setLastTime(getMessageTimeString(message));
                bean.setUnReadCount(list.size());
                bean.setMsgType(getMessageType(message));
                bean.setLastMessage(getMessageString(message));
                EventBus.getDefault().post(new ImOffLineMsgEvent(bean));
                refreshConversationLastMsg(message);
            }
        }
    }

    /**
     * ????????????????????????ID
     */
    private String getMessageUid(Message message) {
        if (message.getTargetType() == ConversationType.single) {
            UserInfo userInfo = (UserInfo) message.getTargetInfo();
            return userInfo.getUserName();
        } else if (message.getTargetType() == ConversationType.group) {
            GroupInfo groupInfo = (GroupInfo) message.getTargetInfo();
            return groupInfo.getGroupID() + "";
        } else {
            return "";
        }
    }

    /**
     * ?????????????????????
     */
    public int getMessageType(Message msg) {
        int type = 0;
        if (msg == null) {
            L.e("message == null");
            return type;
        }
        MessageContent content = msg.getContent();
        if (content == null) {
            L.e("MessageContent == null");
            return type;
        }
        switch (msg.getContentType()) {
            case text://??????
                type = ImMessageBean.TYPE_TEXT;
                break;
            case image://??????
                type = ImMessageBean.TYPE_IMAGE;
                break;
            case voice://??????
                type = ImMessageBean.TYPE_VOICE;
                break;
            case location://??????
                type = ImMessageBean.TYPE_LOCATION;
                break;
            case custom://?????????
                CustomContent customContent = (CustomContent) content;
                String messageType = customContent.getStringValue(ImMessageBean.MESSAGE_TYPE);
                if (!TextUtils.isEmpty(messageType)) {
                    int messageTypeInt = Integer.parseInt(messageType);
                    if (messageTypeInt == 0) {
                        type = ImMessageBean.TYPE_IMAGE;
                    } else if (messageTypeInt == 1) {
                        type = ImMessageBean.TYPE_GIFT;
                    } else if (messageTypeInt == 2) {
                        type = ImMessageBean.TYPE_VOICE;
                    } else if (messageTypeInt == 4) {
                        type = ImMessageBean.TYPE_CALL_VOICE;
                    } else if (messageTypeInt == 5) {
                        type = ImMessageBean.TYPE_CALL_VIDEO;
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(((CustomContent) content).getStringValue("text"));
                        int msgType = jsonObject.getInt("msgType");
                        if (msgType == 100) {
                            type = ImMessageBean.TYPE_SHOP_ORDER;
                            return type;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case eventNotification:
                type = ImMessageBean.TYPE_GROUP;
                break;
        }
        return type;
    }

    /**
     * ??????????????????
     */
    public void onEvent(ConversationRefreshEvent event) {
        Conversation conversation = event.getConversation();
        String from = getAppUid(conversation);
        L.e(TAG, "?????????????????????-------->?????????" + from);
        if (!TextUtils.isEmpty(from) && !from.equals(HttpClient.getUid())) {
            Message message = conversation.getLatestMessage();
            ImUserBean bean = new ImUserBean();
//            bean.setId(from);
            bean.setLastTime(getMessageTimeString(message));
            bean.setUnReadCount(conversation.getUnReadMsgCnt());
            bean.setMsgType(getMessageType(message));
            bean.setLastMessage(getMessageString(message));
            EventBus.getDefault().post(new ImRoamMsgEvent(bean));
            refreshAllUnReadMsgCount();
        }
    }

    /**
     * ??????????????????????????????
     */
    public String getMessageString(Message message) {
        String result = "";
        if (message == null) {
            return result;
        }
        MessageContent content = message.getContent();
        if (content == null) {
            return result;
        }
        switch (content.getContentType()) {
            case text://??????
                result = ((TextContent) content).getText();
                break;
            case image://??????
                result = mImageString;
                break;
            case voice://??????
                result = mVoiceString;
                break;
            case location://??????
                result = mLocationString;
                break;
            case custom://?????????
                CustomContent customContent = (CustomContent) content;
                String messageType = customContent.getStringValue(ImMessageBean.MESSAGE_TYPE);
                if (!TextUtils.isEmpty(messageType)) {
                    int type = Integer.parseInt(messageType);
                    if (type == 0) {
                        result = mImageString;
                    } else if (type == 1) {
                        result = mGiftString;
                    } else if (type == 2) {
                        result = mVoiceString;
                    } else if (type == 4) {
                        result = mCallVoiceString;
                    } else if (type == 5) {
                        result = mCallVideoString;
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(((CustomContent) content).getStringValue("text"));
                        int msgType = jsonObject.getInt("msgType");
                        if (msgType == 100) {
                            result = mShopOrderString;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case eventNotification:
                //?????????????????????????????????
                EventNotificationContent eventNotificationContent = (EventNotificationContent) message.getContent();
                result = eventNotificationContent.getEventText();
//                //????????????????????????
//                switch (eventNotificationContent.getEventNotificationType()) {
//                    case group_member_added:
//                        //?????????????????????
//                        result = eventNotificationContent.getEventText();
//                        break;
//                    case group_member_removed:
//                        //?????????????????????
//                        break;
//                    case group_member_exit:
//                        //?????????????????????
//                        break;
//                    case group_info_updated://since 2.2.1
//                        //?????????????????????
//                        break;
//                }
                break;
        }
        return result;
    }

    public void getGroupUsers(Long toUid, GetGroupInfoCallback infoCallback) {
        JMessageClient.getGroupInfo(toUid, infoCallback);
    }

    public String getMessageTimeString(Message message) {
        if (message == null) {
            return "";
        }
        return mSimpleDateFormat.format(new Date(message.getCreateTime()));
    }

    public String getMessageTimeString(long time) {
        return mSimpleDateFormat.format(new Date(time));
    }

    /**
     * ??????????????????
     *
     * @param uid     ????????????id
     * @param status  ????????????  0 ???????????????   1 ????????????  2 ???????????????  3 ????????????????????????????????????
     * @param time    ???????????? ????????????????????? ???????????? ???????????????0?????????
     * @param isVideo ?????????????????????
     * @return
     */
    public void sendCallMessage(long uid, final int status, final long time, final boolean isVideo) {
        LiveConstants.activeId = 0;
        if (uid < 1) return;
        Message message = JMessageClient.createSingleCustomMessage(String.valueOf(uid), new HashMap<String, String>() {{
            put(ImMessageBean.MESSAGE_TYPE, isVideo ? "5" : "4");
            put(ImMessageBean.STATUS, String.valueOf(status));
            put(ImMessageBean.TIME, DateUtil.formatDuring2(time));
        }});
        if (message == null) {
            return;
        }
        sendMessage(message, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    L.e("????????????????????????");
                }
            }
        });
    }

    /**
     * ??????????????????
     *
     * @param toUid    ??????ID
     * @param toAvater ????????????
     * @param mUid     ??????ID
     * @param mAvater  ????????????
     * @param giftIcon ????????????
     * @param count    ????????????
     */
    public void sendGiftMsg(final long toUid, final String toAvater, final long mUid, final String mAvater, final String giftIcon, final int count) {
        Message message = JMessageClient.createSingleCustomMessage(String.valueOf(toUid), new HashMap<String, String>() {{
            put(ImMessageBean.MESSAGE_TYPE, "1");
            put(ImMessageBean.GIFT_ICON, giftIcon);
            put(ImMessageBean.GIFT_COUNT, count + "");
            put(ImMessageBean.OWN_ICON, mAvater);
            put(ImMessageBean.OTHER_ICON, toAvater);
            put(ImMessageBean.OWN_UID, mUid + "");
            put(ImMessageBean.OTHER_UID, toUid + "");
        }});
        if (message == null) {
            return;
        }
        sendMessage(message, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    L.e("??????????????????????????????");
                }
            }
        });

    }

    /**
     * ????????????????????????
     *
     * @param uid ????????????id
     * @return
     */
    public void sendGoodsMessage(long uid) {
        if (uid < 1) return;
        Message message = JMessageClient.createSingleTextMessage(String.valueOf(uid), "?????????");
        if (message == null) {
            return;
        }
        sendMessage(message, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    L.e("??????????????????????????????");
                }
            }
        });
    }


    /**
     * ?????????????????????
     * <p>???
     * Map<String, String> map = new HashMap<>();
     * map.put(ImMessageBean.CUSTOM_KEY_TYPE, ImMessageBean.CUSTOM_VALUE_CP);
     * map.put(ImMessageBean.CUSTOM_KEY_JSON, "");
     */
    public ImMessageBean createCustomMessage(String toUid, Map<String, String> map, int type) {
        Message message;
        if (isSingle) {
            message = JMessageClient.createSingleCustomMessage(toUid, map);
        } else {
            message = JMessageClient.createGroupCustomMessage(Long.parseLong(toUid), map);
        }
        if (message == null) {
            L.e(TAG, "??????????????????");
            loginJMessage(HttpClient.getUid() + "");
            return null;
        }
        return new ImMessageBean(String.valueOf(HttpClient.getUid()), message, type, true);
    }

    /**
     * ??????????????????
     *
     * @param toUid
     * @param content
     * @return
     */
    public ImMessageBean createTextMessage(String toUid, String content) {
        Message message;
        if (isSingle) {
            message = JMessageClient.createSingleTextMessage(toUid, content);
        } else {
            message = JMessageClient.createGroupTextMessage(Long.parseLong(toUid), content);
        }
        if (message == null) {
            L.e(TAG, "??????????????????");
            loginJMessage(HttpClient.getUid() + "");
            return null;
        }
        L.e(message.toJson());
        return new ImMessageBean(String.valueOf(HttpClient.getUid()), message, ImMessageBean.TYPE_TEXT, true);
    }

    /**
     * ??????????????????
     *
     * @param toUid ?????????id
     * @param path  ????????????
     * @return
     */
    public ImMessageBean createImageMessage(String toUid, String path) {
//        AppConfig appConfig = AppConfig.getInstance();
//        String appKey = appConfig.getJPushAppKey();
        String appKey = "appConfig.getJPushAppKey()";
        try {
            Message message = JMessageClient.createSingleImageMessage(PREFIX + toUid, appKey, new File(path));
            return new ImMessageBean(String.valueOf(HttpClient.getUid()), message, ImMessageBean.TYPE_IMAGE, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * ??????????????????
     *
     * @param toUid
     * @param lat     ??????
     * @param lng     ??????
     * @param scale   ????????????
     * @param address ??????????????????
     * @return
     */
    public ImMessageBean createLocationMessage(String toUid, double lat, double lng, int scale, String address) {
        String appKey = "AppConfig.getInstance().getJPushAppKey()";
        Message message = JMessageClient.createSingleLocationMessage(PREFIX + toUid, appKey, lat, lng, scale, address);
        if (message == null) {
            return null;
        }
        return new ImMessageBean(String.valueOf(HttpClient.getUid()), message, ImMessageBean.TYPE_LOCATION, true);
    }

    /**
     * F
     * ??????????????????
     *
     * @param toUid
     * @param voiceFile ????????????
     * @param duration  ????????????
     * @return
     */
    public ImMessageBean createVoiceMessage(String toUid, File voiceFile, long duration) {
        String appKey = "AppConfig.getInstance().getJPushAppKey();";
        try {
            Message message = JMessageClient.createSingleVoiceMessage(toUid, voiceFile, (int) (duration / 1000));
            if (message == null) {
                return null;
            }
            return new ImMessageBean(String.valueOf(HttpClient.getUid()), message, ImMessageBean.TYPE_VOICE, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ????????????
     */
    public void sendMessage(Message msg, BasicCallback callback) {
        JMessageClient.sendMessage(msg, mOptions);
        msg.setOnSendCompleteCallback(callback);
        refreshConversationLastMsg(msg);
    }

    public void sendMessage(String toUid, Message msg) {
        JMessageClient.sendSingleTransCommand(toUid, null, "???????????????", new BasicCallback() {

            @Override
            public void gotResult(int i, String s) {
                L.e("aaaa------>" + s);
            }
        });
    }


    public void removeMessage(String toUid, Message message) {
        if (!TextUtils.isEmpty(toUid) && message != null) {
            Conversation conversation = JMessageClient.getSingleConversation(toUid);
            if (conversation != null) {
                conversation.deleteMessage(message.getId());
            }
        }
    }

    /**
     * ????????????????????????
     */
    public void removeAllConversation() {
        List<Conversation> list = JMessageClient.getConversationList();
        for (Conversation conversation : list) {
            Object targetInfo = conversation.getTargetInfo();
            JMessageClient.deleteSingleConversation(((UserInfo) targetInfo).getUserName());
        }
    }

    /**
     * ????????????????????????
     */
    public void removeSingleConversation(String uid) {
        JMessageClient.deleteSingleConversation(uid);
        refreshAllUnReadMsgCount();
    }

    /**
     * ????????????????????????
     */
    public void removeGroupConversation(long uid) {
        JMessageClient.deleteGroupConversation(uid);
        refreshAllUnReadMsgCount();
    }

    /**
     * ????????????????????????
     */
    public void addUsersToBlack(final String userName) {
        List<String> list = new ArrayList<>();
        list.add(userName);
        JMessageClient.addUsersToBlacklist(list, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {

            }
        });
    }

    /**
     * ????????????????????????
     */
    public void delUsersFromBlack(final String userName) {
        List<String> list = new ArrayList<>();
        list.add(userName);
        JMessageClient.delUsersFromBlacklist(list, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {

            }
        });
    }

    public void setUserExtras(String avaterUrl) {
        UserInfo userInfo = JMessageClient.getMyInfo();
        String avatarUrl = userInfo.getExtra("avatarUrlStr");
        if (TextUtils.isEmpty(avatarUrl)) {
            userInfo.setUserExtras("avatarUrlStr", avaterUrl);
            JMessageClient.updateMyInfo(UserInfo.Field.extras, userInfo, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    L.e(TAG, "????????????????????????????????????---gotResult--->code: " + i + " msg: " + s);
                }
            });
        }
    }

}
