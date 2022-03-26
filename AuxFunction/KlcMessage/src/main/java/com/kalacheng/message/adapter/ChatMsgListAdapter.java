package com.kalacheng.message.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.kalacheng.commonview.jguangIm.ImChatImageBean;
import com.kalacheng.commonview.jguangIm.ImDateUtil;
import com.kalacheng.commonview.jguangIm.ImMessageBean;
import com.kalacheng.commonview.jguangIm.ImMessageUtil;
import com.kalacheng.commonview.pay.Constants;
import com.kalacheng.commonview.view.TextRender;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.message.R;
import com.kalacheng.message.bean.MsgShopOrderBean;
import com.kalacheng.message.util.view.MyImageView;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.MediaContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class ChatMsgListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT_LEFT = 1;
    private static final int TYPE_TEXT_RIGHT = 2;
    private static final int TYPE_IMAGE_LEFT = 3;
    private static final int TYPE_IMAGE_RIGHT = 4;
    private static final int TYPE_VOICE_LEFT = 5;
    private static final int TYPE_VOICE_RIGHT = 6;
    private static final int TYPE_LOCATION_LEFT = 7;
    private static final int TYPE_LOCATION_RIGHT = 8;
    private static final int TYPE_CUSTOM_CP_LEFT = 9;
    private static final int TYPE_CUSTOM_CP_RIGHT = 10;
    private static final int TYPE_GIFT = 11;
    private static final int TYPE_GROUP = 12;
    private static final int TYPE_CALL_VOICE_LEFT = 13;
    private static final int TYPE_CALL_VIDEO_RIGHT = 14;
    private static final int TYPE_SHOP_ORDER_LEFT = 15;
    private static final int TYPE_SHOP_ORDER_RIGHT = 16;


    private List<ImMessageBean> mList;
    private long mLastMessageTime;
    private LayoutInflater mInflater;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private int[] mLocation;
    private OnImageClick onImageClick;
    private OnSendSuccess onSendSuccess;
    private Context context;

    Point point;

    public ChatMsgListAdapter(Context context) {
//        Log.e("ImMessage", "ChatMsgListAdapter");
        mInflater = LayoutInflater.from(context);
        this.context = context;
        mList = new ArrayList();
        mLocation = new int[2];
    }

    /**
     * set数据
     */
    public void setData(List<ImMessageBean> list) {
        mList.clear();
        for (ImMessageBean bean : list) {
            if (!(bean.getRawMessage().getContent() instanceof MediaContent)) {
                mList.add(bean);
            }
        }
        notifyDataSetChanged();
        scrollToBottom();
    }

    /**
     * 设置图片点击监听
     */
    public void setOnImageClick(OnImageClick onImageClick) {
        this.onImageClick = onImageClick;
    }

    /**
     * 消息发送成功
     */
    public void setOnSendSuccess(OnSendSuccess onSendSuccess) {
        this.onSendSuccess = onSendSuccess;
    }

    /**
     * 添加一条数据
     */
    public int insertItem(ImMessageBean bean) {
        if (mList != null && bean != null) {
            int size = mList.size();
            mList.add(bean);
            L.e("setTime2= " + ImDateUtil.getTimestampString(bean.getTime()));
            notifyItemInserted(size);
            int lastItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
            if (lastItemPosition != size - 1) {
                mRecyclerView.smoothScrollToPosition(size);
            } else {
                mRecyclerView.scrollToPosition(size);
            }
            return size;
        }
        return -1;
    }

    /**
     * 添加自己发送的一条消息
     */
    public void insertSelfItem(final ImMessageBean bean) {
        bean.setLoading(true);
        L.e("setTime2= " + ImDateUtil.getTimestampString(bean.getTime()));
        final int position = insertItem(bean);
        if (position != -1) {
            final Message msg = bean.getRawMessage();
            if (msg != null) {
                ImMessageUtil.getInstance().sendMessage(msg, new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseDesc) {
                        bean.setLoading(false);
                        L.e("极光IM---消息发送回调");
                        L.e("极光IM---responseCode = " + responseCode + "   responseDesc = " + responseDesc);
                        if (responseCode == 0) {
                            if (onSendSuccess != null) {
                                String text = ((TextContent) bean.getRawMessage().getContent()).getText();
                                int type = bean.getType();
                                onSendSuccess.onSuccess(text, type);
                            }
                            bean.setSendFail(false);
                            L.e("极光IM---消息发送成功--->  responseDesc:" + responseDesc);
                        } else if (responseCode == 803008) {
                            ToastUtil.show("你已被拉黑");
                            bean.setSendFail(true);
                            L.e("极光IM---你已被拉黑--->  responseDesc:" + responseDesc);
                        } else {
                            bean.setSendFail(true);
                            //消息发送失败
                            ToastUtil.show(WordUtil.getString(R.string.im_msg_send_failed));
                            L.e("极光IM---消息发送失败--->  responseDesc:" + responseDesc);
                        }
                        notifyItemChanged(position, Constants.PAYLOAD);
                        L.e("setTime4= " + ImDateUtil.getTimestampString(mList.get(position).getTime()));
                    }
                });
            }
        }
    }

    /**
     * 滚动到底部
     */
    public void scrollToBottom() {
        if (mList.size() > 0 && mLayoutManager != null) {
            mLayoutManager.scrollToPositionWithOffset(mList.size() - 1, -DpUtil.dp2px(20));
        }
    }

    /**
     * 获取去当前的所有图片
     */
    public ImChatImageBean getChatImageBean(int msgId) {
        List<ImMessageBean> list = new ArrayList<>();
        int imagePosition = 0;
        for (int i = 0, size = mList.size(); i < size; i++) {
            ImMessageBean bean = mList.get(i);
            if (bean.getType() == ImMessageBean.TYPE_IMAGE) {
                list.add(bean);
                if (bean.getRawMessage().getId() == msgId) {
                    imagePosition = list.size() - 1;
                }
            }
        }
        return new ImChatImageBean(list, imagePosition);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.e("ImMessage", "onCreateViewHolder");
        switch (viewType) {
            case TYPE_TEXT_LEFT:
                return new TextVh(mInflater.inflate(R.layout.item_chat_text_left, parent, false));
            case TYPE_TEXT_RIGHT:
                return new SelfTextVh(mInflater.inflate(R.layout.item_chat_text_right, parent, false));
            case TYPE_IMAGE_LEFT:
                return new ImageVh(mInflater.inflate(R.layout.item_chat_img_left, parent, false));
            case TYPE_IMAGE_RIGHT:
                return new SelfImageVh(mInflater.inflate(R.layout.item_chat_img_right, parent, false));
            case TYPE_VOICE_LEFT:
                return new VoiceVh(mInflater.inflate(R.layout.item_chat_voice_left, parent, false));
            case TYPE_VOICE_RIGHT:
                return new SelfVoiceVh(mInflater.inflate(R.layout.item_chat_voice_right, parent, false));
            case TYPE_GIFT:
                return new GiftVh(mInflater.inflate(R.layout.item_chat_gift, parent, false));
            case TYPE_GROUP:
                return new GroupEventVh(mInflater.inflate(R.layout.item_chat_group_event, parent, false));
            case TYPE_CALL_VOICE_LEFT:
                return new CallVoiceVh(mInflater.inflate(R.layout.item_chat_call_voice_left, parent, false));
            case TYPE_CALL_VIDEO_RIGHT:
                return new SelfCallVoiceVh(mInflater.inflate(R.layout.item_chat_call_voice_right, parent, false));
//            case TYPE_LOCATION_LEFT:
//                return new LocationVh(mInflater.inflate(R.layout.item_chat_location_left, parent, false));
//            case TYPE_LOCATION_RIGHT:
//                return new SelfLocationVh(mInflater.inflate(R.layout.item_chat_location_right, parent, false));
//            case TYPE_CUSTOM_CP_LEFT:
//                return new CustomCpVh(mInflater.inflate(R.layout.item_chat_custom_cp_left, parent, false));
//            case TYPE_CUSTOM_CP_RIGHT:
//                return new SelfCustomCpVh(mInflater.inflate(R.layout.item_chat_custom_cp_right, parent, false));
            case TYPE_SHOP_ORDER_LEFT:
                return new ShopOrderVh(mInflater.inflate(R.layout.item_chat_shop_order_left, parent, false));
            case TYPE_SHOP_ORDER_RIGHT:
                return new SelfShopOrderVh(mInflater.inflate(R.layout.item_chat_shop_order_right, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Log.e("ImMessage", "onBindViewHolder11");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
//        Log.e("ImMessage", "onBindViewHolder22");
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        ((Vh) holder).setData(mList.get(position), position, payload);
    }

    @Override
    public int getItemCount() {
//        Log.e("ImMessage", "getItemCount");
        return mList.size();
    }


    @Override
    public int getItemViewType(int position) {
//        Log.e("ImMessage", "getItemViewType");
        ImMessageBean msg = mList.get(position);
        switch (msg.getType()) {
            case ImMessageBean.TYPE_TEXT:
                if (msg.isFromSelf()) {
                    return TYPE_TEXT_RIGHT;
                } else {
                    return TYPE_TEXT_LEFT;
                }
            case ImMessageBean.TYPE_IMAGE:
                if (msg.isFromSelf()) {
                    return TYPE_IMAGE_RIGHT;
                } else {
                    return TYPE_IMAGE_LEFT;
                }
            case ImMessageBean.TYPE_VOICE:
                if (msg.isFromSelf()) {
                    return TYPE_VOICE_RIGHT;
                } else {
                    return TYPE_VOICE_LEFT;
                }
            case ImMessageBean.TYPE_LOCATION:
                if (msg.isFromSelf()) {
                    return TYPE_LOCATION_RIGHT;
                } else {
                    return TYPE_LOCATION_LEFT;
                }
            case ImMessageBean.TYPE_CUSTOM_CP:
                if (msg.isFromSelf()) {
                    return TYPE_CUSTOM_CP_RIGHT;
                } else {
                    return TYPE_CUSTOM_CP_LEFT;
                }
            case ImMessageBean.TYPE_GIFT:
                if (msg.isFromSelf()) {
                    return TYPE_GIFT;
                } else {
                    return TYPE_GIFT;
                }
            case ImMessageBean.TYPE_GROUP:
                if (msg.isFromSelf()) {
                    return TYPE_GROUP;
                } else {
                    return TYPE_GROUP;
                }
            case ImMessageBean.TYPE_CALL_VIDEO:
            case ImMessageBean.TYPE_CALL_VOICE:
                if (msg.isFromSelf()) {
                    return TYPE_CALL_VIDEO_RIGHT;
                } else {
                    return TYPE_CALL_VOICE_LEFT;
                }
            case ImMessageBean.TYPE_SHOP_ORDER:
                if (msg.isFromSelf()) {
                    return TYPE_SHOP_ORDER_RIGHT;
                } else {
                    return TYPE_SHOP_ORDER_LEFT;
                }
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }


    class Vh extends RecyclerView.ViewHolder {
        TextView mTime;
        ImMessageBean mImMessageBean;
        View mFailIcon;
        View mLoading;

        public Vh(View itemView) {
            super(itemView);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mFailIcon = itemView.findViewById(R.id.icon_fail);
            mLoading = itemView.findViewById(R.id.loading);
        }

        void setData(ImMessageBean bean, final int position, Object payload) {
            mImMessageBean = bean;
            if (payload == null && mTime != null) {
                if (position == 0) {
                    if (mTime.getVisibility() != View.VISIBLE) {
                        mTime.setVisibility(View.VISIBLE);
                    }
                    mTime.setText(ImDateUtil.getTimestampString(bean.getTime()));
                } else {
                    if (ImDateUtil.isCloseEnough(bean.getTime(), mList.get(position - 1).getTime())) {
                        if (mTime.getVisibility() == View.VISIBLE) {
                            mTime.setVisibility(View.GONE);
                        }
                    } else {
                        if (mTime.getVisibility() != View.VISIBLE) {
                            mTime.setVisibility(View.VISIBLE);
                        }
                        mTime.setText(ImDateUtil.getTimestampString(bean.getTime()));
                    }
                }
            }
            if (payload == null && mImMessageBean.isFromSelf() && mFailIcon != null) {
                mFailIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImMessageBean bean = new ImMessageBean(mList.get(position));
                        bean.setTime(System.currentTimeMillis());
                        mList.remove(position);
                        notifyItemRemoved(position);
                        insertSelfItem(bean);
                    }
                });
            }
        }
    }

    class TextVh extends ChatMsgListAdapter.Vh {
        TextView mText;
        RoundedImageView avatarIv;

        TextVh(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.contentTv);
            avatarIv = itemView.findViewById(R.id.avatarIv);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public void setData(final ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (payload == null) {
                String text = ((TextContent) bean.getRawMessage().getContent()).getText();
                mText.setText(TextRender.renderChatMessage(text));
                if (avatarIv != null) {
                    UserInfo userInfo = bean.getRawMessage().getFromUser();
                    if (userInfo != null) {
                        String avatarUrlStr = userInfo.getExtra("avatarUrlStr");
                        ImageLoader.display(avatarUrlStr, avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        if (TextUtils.isEmpty(avatarUrlStr)) {
                            JMessageClient.getUserInfo(userInfo.getUserName(), new GetUserInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, UserInfo userInfo) {
                                    ImageLoader.display(userInfo.getExtra("avatarUrlStr"), avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                                }
                            });
                        }
                    }

                    avatarIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (CheckDoubleClick.isFastDoubleClick()) {
                                return;
                            }
                            long id = Long.parseLong(bean.getUid());
                            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, id).navigation();

                        }
                    });
                }
            }

        }
    }

    class SelfTextVh extends ChatMsgListAdapter.TextVh {
        SelfTextVh(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (bean.isLoading()) {
                if (mLoading.getVisibility() != View.VISIBLE) {
                    mLoading.setVisibility(View.VISIBLE);
                }
            } else {
                if (mLoading.getVisibility() == View.VISIBLE) {
                    mLoading.setVisibility(View.INVISIBLE);
                }
            }
            if (bean.isSendFail()) {
                if (mFailIcon.getVisibility() != View.VISIBLE) {
                    mFailIcon.setVisibility(View.VISIBLE);
                }
            } else {
                if (mFailIcon.getVisibility() == View.VISIBLE) {
                    mFailIcon.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    class ImageVh extends ChatMsgListAdapter.Vh {
        RoundedImageView avatarIv;
        MyImageView imageIv;

        public ImageVh(View itemView) {
            super(itemView);
            imageIv = itemView.findViewById(R.id.imageIv);
            avatarIv = itemView.findViewById(R.id.avatarIv);
//            imageIv.setOnClickListener(mOnImageClickListener);
        }

        @Override
        public void setData(final ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (payload == null) {
                if (avatarIv != null) {
                    UserInfo userInfo = bean.getRawMessage().getFromUser();
                    if (userInfo != null) {
                        String avatarUrlStr = userInfo.getExtra("avatarUrlStr");
                        ImageLoader.display(avatarUrlStr, avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        if (TextUtils.isEmpty(avatarUrlStr)) {
                            JMessageClient.getUserInfo(userInfo.getUserName(), new GetUserInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, UserInfo userInfo) {
                                    ImageLoader.display(userInfo.getExtra("avatarUrlStr"), avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                                }
                            });
                        }
                    }

                    avatarIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (CheckDoubleClick.isFastDoubleClick()) {
                                return;
                            }
                            long id = Long.parseLong(bean.getUid());
                            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, id).navigation();

                        }
                    });
                }
                imageIv.setMsgId(bean.getRawMessage().getId());
                CustomContent customContent = (CustomContent) bean.getRawMessage().getContent();
                String url = customContent.getStringValue(ImMessageBean.PIC_URL_STR);
                L.e("url -->", url + "");
                imageIv.setUrl(url);
                ImageLoader.display(url, imageIv);
                if (onImageClick != null) {
                    imageIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (CheckDoubleClick.isFastDoubleClick()) {
                                return;
                            }
                            imageIv.getLocationOnScreen(mLocation);
                            onImageClick.onImageClick(imageIv, mLocation[0], mLocation[1]);
                        }
                    });
                }
            }
        }
    }

    class SelfImageVh extends ChatMsgListAdapter.ImageVh {
        public SelfImageVh(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (bean.isLoading()) {
                if (mLoading.getVisibility() != View.VISIBLE) {
                    mLoading.setVisibility(View.VISIBLE);
                }
            } else {
                if (mLoading.getVisibility() == View.VISIBLE) {
                    mLoading.setVisibility(View.INVISIBLE);
                }
            }
            if (bean.isSendFail()) {
                if (mFailIcon.getVisibility() != View.VISIBLE) {
                    mFailIcon.setVisibility(View.VISIBLE);
                }
            } else {
                if (mFailIcon.getVisibility() == View.VISIBLE) {
                    mFailIcon.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    class VoiceVh extends ChatMsgListAdapter.Vh {
        RoundedImageView avatarIv;
        TextView mDuration;
        MyImageView voice;
        RelativeLayout voiceRl;
        View redPoint;

        public VoiceVh(View itemView) {
            super(itemView);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            mDuration = (TextView) itemView.findViewById(R.id.durationTv);
            voice = itemView.findViewById(R.id.voice);
            voiceRl = itemView.findViewById(R.id.voiceRl);
            redPoint = itemView.findViewById(R.id.redPoint);
        }

        @Override
        public void setData(final ImMessageBean bean, final int position, Object payload) {
            super.setData(bean, position, payload);
            if (payload == null) {
                if (avatarIv != null) {
                    UserInfo userInfo = bean.getRawMessage().getFromUser();
                    if (userInfo != null) {
                        String avatarUrlStr = userInfo.getExtra("avatarUrlStr");
                        ImageLoader.display(avatarUrlStr, avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        if (TextUtils.isEmpty(avatarUrlStr)) {
                            JMessageClient.getUserInfo(userInfo.getUserName(), new GetUserInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, UserInfo userInfo) {
                                    ImageLoader.display(userInfo.getExtra("avatarUrlStr"), avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                                }
                            });
                        }
                    }

                    avatarIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (CheckDoubleClick.isFastDoubleClick()) {
                                return;
                            }
                            long id = Long.parseLong(bean.getUid());
                            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, id).navigation();

                        }
                    });
                }
                Message messageContent = mImMessageBean.getRawMessage();
                CustomContent customContent = (CustomContent) messageContent.getContent();
                String time = customContent.getStringValue(ImMessageBean.TIME);
                String url = customContent.getStringValue(ImMessageBean.RECORD_URL);
                if (Integer.parseInt(time) > 1000) {
                    time = String.valueOf(Integer.parseInt(time) / 1000);
                }
                if (!TextUtils.isEmpty(time) && !time.equals("0")) {
                    mDuration.setVisibility(View.VISIBLE);
                    mDuration.setText(time + "``");
                } else {
                    mDuration.setVisibility(View.INVISIBLE);
                }
                voice.setUrl(url);
                voice.setSelf(false);
                voiceRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckDoubleClick.isFastDoubleClick()) {
                            return;
                        }
                        onImageClick.onVoiceClick(voice);
                        bean.hasRead(new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                notifyItemChanged(position, Constants.PAYLOAD);
                            }
                        });
                    }
                });
            }
            if (redPoint != null) {
                if (bean.isRead()) {
                    if (redPoint.getVisibility() == View.VISIBLE) {
                        redPoint.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (redPoint.getVisibility() != View.VISIBLE) {
                        redPoint.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    class SelfVoiceVh extends ChatMsgListAdapter.VoiceVh {

        RelativeLayout voiceRl;

        public SelfVoiceVh(View itemView) {
            super(itemView);
            voiceRl = itemView.findViewById(R.id.voiceRl);
            voiceRl.measure(0, 0);
            mFailIcon = itemView.findViewById(R.id.icon_fail);
            mLoading = itemView.findViewById(R.id.loading);
        }

        @Override
        public void setData(ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (payload == null) {
                voice.setSelf(true);
            }
            if (bean.isLoading()) {
                if (mLoading.getVisibility() != View.VISIBLE) {
                    mLoading.setVisibility(View.VISIBLE);
                }
            } else {
                if (mLoading.getVisibility() == View.VISIBLE) {
                    mLoading.setVisibility(View.INVISIBLE);
                }
            }
            if (bean.isSendFail()) {
                if (mFailIcon.getVisibility() != View.VISIBLE) {
                    mFailIcon.setVisibility(View.VISIBLE);
                }
            } else {
                if (mFailIcon.getVisibility() == View.VISIBLE) {
                    mFailIcon.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    class GiftVh extends Vh {
        RoundedImageView fromIv;
        RoundedImageView toIv;
        ImageView giftIv;
        TextView typeTv;
        TextView countTv;

        public GiftVh(View itemView) {
            super(itemView);
            fromIv = itemView.findViewById(R.id.fromIv);
            toIv = itemView.findViewById(R.id.toIv);
            giftIv = itemView.findViewById(R.id.giftIv);
            typeTv = itemView.findViewById(R.id.typeTv);
            countTv = itemView.findViewById(R.id.countTv);
        }

        void setData(ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
            mImMessageBean = bean;
            if (payload == null) {
                CustomContent customContent = (CustomContent) mImMessageBean.getRawMessage().getContent();
                ImageLoader.display(customContent.getStringValue(ImMessageBean.OWN_ICON), fromIv);
                ImageLoader.display(customContent.getStringValue(ImMessageBean.GIFT_ICON), giftIv);
                if (bean.getRawMessage().getTargetType() == ConversationType.single) {
                    toIv.setVisibility(View.GONE);
                    if (mImMessageBean.isFromSelf()) {
                        typeTv.setText("送TA");
                    } else {
                        typeTv.setText("送你");
                    }
                } else {
                    toIv.setVisibility(View.VISIBLE);
                    ImageLoader.display(customContent.getStringValue(ImMessageBean.OTHER_ICON), toIv);
                    if (mImMessageBean.isFromSelf()) {
                        typeTv.setText("送TA");
                    } else {
                        typeTv.setText("送TA");
                    }
                }
                countTv.setText("x" + customContent.getStringValue(ImMessageBean.GIFT_COUNT));

            }
        }
    }

    class GroupEventVh extends Vh {
        TextView eventTv;

        public GroupEventVh(View itemView) {
            super(itemView);
            eventTv = itemView.findViewById(R.id.eventTv);
        }

        void setData(ImMessageBean bean, int position, Object payload) {
            mImMessageBean = bean;
            if (payload == null) {
                EventNotificationContent event = (EventNotificationContent) mImMessageBean.getRawMessage().getContent();
                eventTv.setText(event.getEventText());
            }
        }
    }

    class CallVoiceVh extends ChatMsgListAdapter.Vh {

        TextView mText;
        RoundedImageView avatarIv;

        CallVoiceVh(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.contentTv);
            avatarIv = itemView.findViewById(R.id.avatarIv);
        }

        @Override
        public void setData(final ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (payload == null) {
                CustomContent customContent = (CustomContent) bean.getRawMessage().getContent();
                String messageType = customContent.getStringValue(ImMessageBean.MESSAGE_TYPE);
                String status = customContent.getStringValue(ImMessageBean.STATUS);
                String time = customContent.getStringValue(ImMessageBean.TIME);
                if (messageType.equals("4")) {
                    mText.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.tonghua_black, 0, 0, 0);
                } else {
                    mText.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.shipin_black, 0, 0, 0);
                }
                if (bean.isFromSelf()) {
                    switch (status) {
                        case "0":
                            mText.setText("通话时长 " + time);
                            break;
                        case "1":
                            mText.setText("已取消");
                            break;
                        case "2":
                            mText.setText("已被挂断");
                            break;
                        case "3":
                            mText.setText("无人接听");
                            break;
                    }
                } else {
                    switch (status) {
                        case "0":
                            mText.setText("通话时长 " + time);
                            break;
                        case "1":
                            mText.setText("已取消");
                            break;
                        case "2":
                            mText.setText("被你挂断");
                            break;
                        case "3":
                            mText.setText("未接听");
                            break;
                    }
                }

                if (avatarIv != null) {
                    UserInfo userInfo = bean.getRawMessage().getFromUser();
                    if (userInfo != null) {
                        String avatarUrlStr = userInfo.getExtra("avatarUrlStr");
                        ImageLoader.display(avatarUrlStr, avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        if (TextUtils.isEmpty(avatarUrlStr)) {
                            JMessageClient.getUserInfo(userInfo.getUserName(), new GetUserInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, UserInfo userInfo) {
                                    ImageLoader.display(userInfo.getExtra("avatarUrlStr"), avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                                }
                            });
                        }
                    }

                    avatarIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (CheckDoubleClick.isFastDoubleClick()) {
                                return;
                            }
                            long id = Long.parseLong(bean.getUid());
                            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, id).navigation();

                        }
                    });
                }

            }
        }
    }

    class SelfCallVoiceVh extends ChatMsgListAdapter.CallVoiceVh {

        SelfCallVoiceVh(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
        }
    }

    /**
     * 购物订单（左侧）
     */
    class ShopOrderVh extends ChatMsgListAdapter.Vh {
        RoundedImageView avatarIv;
        LinearLayout layoutMsgShopOrder;
        TextView tvOrderType;
        ImageView ivProduct;
        TextView tvProductName;
        TextView tvProductNum;
        TextView tvTotalAmount;
        TextView tvName;
        TextView tvPhone;
        TextView tvAddress;
        TextView tvRefundType;
        TextView tvRefundAmount;
        TextView tvRefundNotes;
        TextView tvRefundAmountConfirm;
        TextView tvRefundTime;
        TextView tvReason;
        LinearLayout layoutRefundImages;
        ImageView ivRefundImage0;
        ImageView ivRefundImage1;
        ImageView ivRefundImage2;
        TextView tvTip;
        TextView tvHandle;

        public ShopOrderVh(View itemView) {
            super(itemView);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            layoutMsgShopOrder = itemView.findViewById(R.id.layoutMsgShopOrder);
            tvOrderType = itemView.findViewById(R.id.tvOrderType);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductNum = itemView.findViewById(R.id.tvProductNum);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvRefundType = itemView.findViewById(R.id.tvRefundType);
            tvRefundAmount = itemView.findViewById(R.id.tvRefundAmount);
            tvRefundNotes = itemView.findViewById(R.id.tvRefundNotes);
            tvRefundAmountConfirm = itemView.findViewById(R.id.tvRefundAmountConfirm);
            tvRefundTime = itemView.findViewById(R.id.tvRefundTime);
            tvReason = itemView.findViewById(R.id.tvReason);
            layoutRefundImages = itemView.findViewById(R.id.layoutRefundImages);
            ivRefundImage0 = itemView.findViewById(R.id.ivRefundImage0);
            ivRefundImage1 = itemView.findViewById(R.id.ivRefundImage1);
            ivRefundImage2 = itemView.findViewById(R.id.ivRefundImage2);
            tvTip = itemView.findViewById(R.id.tvTip);
            tvHandle = itemView.findViewById(R.id.tvHandle);
        }

        @Override
        public void setData(final ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (payload == null) {
                if (avatarIv != null) {
                    UserInfo userInfo = bean.getRawMessage().getFromUser();
                    if (userInfo != null) {
                        String avatarUrlStr = userInfo.getExtra("avatarUrlStr");
                        ImageLoader.display(avatarUrlStr, avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        if (TextUtils.isEmpty(avatarUrlStr)) {
                            JMessageClient.getUserInfo(userInfo.getUserName(), new GetUserInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, UserInfo userInfo) {
                                    ImageLoader.display(userInfo.getExtra("avatarUrlStr"), avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                                }
                            });
                        }
                    }

                    avatarIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (CheckDoubleClick.isFastDoubleClick()) {
                                return;
                            }
                            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, Long.parseLong(bean.getUid())).navigation();
                        }
                    });
                }

                tvTotalAmount.setVisibility(View.GONE);
                tvName.setVisibility(View.GONE);
                tvPhone.setVisibility(View.GONE);
                tvAddress.setVisibility(View.GONE);
                tvRefundType.setVisibility(View.GONE);
                tvRefundAmount.setVisibility(View.GONE);
                tvRefundNotes.setVisibility(View.GONE);
                tvRefundAmountConfirm.setVisibility(View.GONE);
                tvRefundTime.setVisibility(View.GONE);
                tvReason.setVisibility(View.GONE);
                layoutRefundImages.setVisibility(View.GONE);
                tvTip.setVisibility(View.GONE);
                tvHandle.setVisibility(View.GONE);

                CustomContent customContent = (CustomContent) bean.getRawMessage().getContent();
                final MsgShopOrderBean msgShopOrderBean = JSON.parseObject(customContent.getStringValue("text"), MsgShopOrderBean.class);
                if (msgShopOrderBean != null) {
                    switch (msgShopOrderBean.getOrderStatus()) {
                        case 1://待卖家发货
                            tvOrderType.setText("下单提醒");
                            ImageLoader.display(msgShopOrderBean.getProductImage(), ivProduct);
                            tvProductName.setText(msgShopOrderBean.getProductName());
                            tvProductNum.setText("共" + msgShopOrderBean.getProductNum() + "件");
                            tvTotalAmount.setVisibility(View.VISIBLE);
                            tvTotalAmount.setText("总金额 ¥" + msgShopOrderBean.getTotalAmount());
                            tvName.setVisibility(View.VISIBLE);
                            tvName.setText("收货人：" + msgShopOrderBean.getName());
                            tvPhone.setVisibility(View.VISIBLE);
                            tvPhone.setText("电话：" + msgShopOrderBean.getPhoneNum());
                            tvAddress.setVisibility(View.VISIBLE);
                            tvAddress.setText("地址：" + msgShopOrderBean.getAddress());
                            tvHandle.setVisibility(View.VISIBLE);
                            break;
                        case 2://待买家收货
                            tvOrderType.setText("您的订单已发货");
                            ImageLoader.display(msgShopOrderBean.getProductImage(), ivProduct);
                            tvProductName.setText(msgShopOrderBean.getProductName());
                            tvProductNum.setText("共" + msgShopOrderBean.getProductNum() + "件");
                            tvTotalAmount.setVisibility(View.VISIBLE);
                            tvTotalAmount.setText("总金额 ¥" + msgShopOrderBean.getTotalAmount());
                            tvHandle.setVisibility(View.VISIBLE);
                            break;
                        case 3://申请退款待审核
                            tvOrderType.setText("退款申请");
                            ImageLoader.display(msgShopOrderBean.getProductImage(), ivProduct);
                            tvProductName.setText(msgShopOrderBean.getProductName());
                            tvProductNum.setText("共" + msgShopOrderBean.getProductNum() + "件");
                            tvRefundType.setVisibility(View.VISIBLE);
                            tvRefundType.setText("退款类型：" + (msgShopOrderBean.getRefundType() == 1 ? "仅退款" : "退货退款"));
                            tvRefundAmount.setVisibility(View.VISIBLE);
                            tvRefundAmount.setText("退款金额：¥" + msgShopOrderBean.getTotalAmount());
                            tvRefundNotes.setVisibility(View.VISIBLE);
                            tvRefundNotes.setText("退款" + msgShopOrderBean.getRefundNotes());
                            if (!TextUtils.isEmpty(msgShopOrderBean.getRefundNotesImages())) {
                                layoutRefundImages.setVisibility(View.VISIBLE);
                                String[] images = msgShopOrderBean.getRefundNotesImages().split(",");
                                if (images.length > 0) {
                                    ivRefundImage0.setVisibility(View.VISIBLE);
                                    ImageLoader.display(images[0], ivRefundImage0);
                                } else {
                                    ivRefundImage0.setVisibility(View.INVISIBLE);
                                }
                                if (images.length > 1) {
                                    ivRefundImage1.setVisibility(View.VISIBLE);
                                    ImageLoader.display(images[1], ivRefundImage1);
                                } else {
                                    ivRefundImage1.setVisibility(View.INVISIBLE);
                                }
                                if (images.length > 2) {
                                    ivRefundImage2.setVisibility(View.VISIBLE);
                                    ImageLoader.display(images[2], ivRefundImage2);
                                } else {
                                    ivRefundImage2.setVisibility(View.INVISIBLE);
                                }
                            }
                            tvTip.setVisibility(View.VISIBLE);
                            tvTip.setText(msgShopOrderBean.getTips() + "未处理订单将自动同意退款申请");
                            tvHandle.setVisibility(View.VISIBLE);
                            break;
                        case 4://申请退款审核失败
                            tvOrderType.setText("您的退款申请审核未通过");
                            ImageLoader.display(msgShopOrderBean.getProductImage(), ivProduct);
                            tvProductName.setText(msgShopOrderBean.getProductName());
                            tvProductNum.setText("共" + msgShopOrderBean.getProductNum() + "件");
                            tvRefundType.setVisibility(View.VISIBLE);
                            tvRefundType.setText("退款类型：" + (msgShopOrderBean.getRefundType() == 1 ? "仅退款" : "退货退款"));
                            tvRefundAmount.setVisibility(View.VISIBLE);
                            tvRefundAmount.setText("退款金额：¥" + msgShopOrderBean.getTotalAmount());
                            tvReason.setVisibility(View.VISIBLE);
                            tvReason.setText(msgShopOrderBean.getReason());
                            tvHandle.setVisibility(View.VISIBLE);
                            break;
                        case 5://待买家发货
                            tvOrderType.setText("您的退款申请审核已通过");
                            ImageLoader.display(msgShopOrderBean.getProductImage(), ivProduct);
                            tvProductName.setText(msgShopOrderBean.getProductName());
                            tvProductNum.setText("共" + msgShopOrderBean.getProductNum() + "件");
                            tvRefundType.setVisibility(View.VISIBLE);
                            tvRefundType.setText("退款类型：" + (msgShopOrderBean.getRefundType() == 1 ? "仅退款" : "退货退款"));
                            tvRefundAmount.setVisibility(View.VISIBLE);
                            tvRefundAmount.setText("退款金额：¥" + msgShopOrderBean.getTotalAmount());
                            if (msgShopOrderBean.getRefundType() == 1) {
                                tvTip.setVisibility(View.VISIBLE);
                                tvTip.setText("您的退款将于" + msgShopOrderBean.getTips() + "内退回付款账户");
                            } else {
                                tvTip.setVisibility(View.VISIBLE);
                                tvTip.setText("请您于" + msgShopOrderBean.getTips() + "内将货物退回并填写物流单号，超时订单自动取消退款");
                                tvHandle.setVisibility(View.VISIBLE);
                            }
                            break;
                        case 6://待卖家收货
                            tvOrderType.setText("退款收货提醒");
                            ImageLoader.display(msgShopOrderBean.getProductImage(), ivProduct);
                            tvProductName.setText(msgShopOrderBean.getProductName());
                            tvProductNum.setText("共" + msgShopOrderBean.getProductNum() + "件");
                            tvRefundType.setVisibility(View.VISIBLE);
                            tvRefundType.setText("退款类型：退货退款");
                            tvRefundAmount.setVisibility(View.VISIBLE);
                            tvRefundAmount.setText("退款金额：¥" + msgShopOrderBean.getTotalAmount());
                            tvRefundNotes.setVisibility(View.VISIBLE);
                            tvRefundNotes.setText("退款" + msgShopOrderBean.getRefundNotes());
                            tvTip.setVisibility(View.VISIBLE);
                            tvTip.setText("货物已发出，" + msgShopOrderBean.getTips() + "内未处理订单将自动退款");
                            tvHandle.setVisibility(View.VISIBLE);
                            break;
                        case 7://退款完成
                            tvOrderType.setText("您的退款已到账");
                            ImageLoader.display(msgShopOrderBean.getProductImage(), ivProduct);
                            tvProductName.setText(msgShopOrderBean.getProductName());
                            tvProductNum.setText("共" + msgShopOrderBean.getProductNum() + "件");
                            tvRefundType.setVisibility(View.VISIBLE);
                            tvRefundType.setText("退款类型：" + (msgShopOrderBean.getRefundType() == 1 ? "仅退款" : "退货退款"));
                            tvRefundAmountConfirm.setVisibility(View.VISIBLE);
                            tvRefundAmountConfirm.setText("到账金额：¥" + msgShopOrderBean.getTotalAmount());
                            tvRefundTime.setVisibility(View.VISIBLE);
                            tvRefundTime.setText("到账时间：" + new DateUtil(msgShopOrderBean.getRefundTime()).getDateToFormat("yyyy-MM-dd HH:mm"));
                            break;
                        default:
                            tvOrderType.setText("");
                            ivProduct.setImageResource(R.mipmap.ic_launcher);
                            tvProductName.setText("");
                            tvProductNum.setText("");
                            break;
                    }
                }

                layoutMsgShopOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckDoubleClick.isFastDoubleClick()) return;
                        if (msgShopOrderBean != null) {
                            if (msgShopOrderBean.getBuyerId() == HttpClient.getUid()) {//买家
                                ARouter.getInstance().build(ARouterPath.OrderDetailsActivity).withInt(ARouterValueNameConfig.TYPE, 1)
                                        .withLong(ARouterValueNameConfig.orderId, msgShopOrderBean.getOrderId()).navigation();
                            } else {
                                ARouter.getInstance().build(ARouterPath.OrderDetailsActivity).withInt(ARouterValueNameConfig.TYPE, 2)
                                        .withLong(ARouterValueNameConfig.orderId, msgShopOrderBean.getOrderId()).navigation();
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * 购物订单（右侧）
     */
    class SelfShopOrderVh extends ChatMsgListAdapter.ShopOrderVh {

        public SelfShopOrderVh(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(ImMessageBean bean, int position, Object payload) {
            super.setData(bean, position, payload);
            if (payload == null) {
                tvHandle.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 弹出菜单栏
     *
     * @param context
     * @param anchor                  触发事件的View
     * @param menuRes                 菜单栏内容
     * @param point                   点击事件相对整个屏幕的坐标
     * @param onMenuItemClickListener 菜单栏点击事件
     * @param onDismissListener       菜单栏消失
     */
    @SuppressLint("RestrictedApi")
    public static void showPopupMenu(Context context, View anchor, @MenuRes int menuRes, Point point,
                                     PopupMenu.OnMenuItemClickListener onMenuItemClickListener,
                                     PopupMenu.OnDismissListener onDismissListener) {
        //这里的anchor代表PopupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(context, anchor);
        //填充PopupMenu内容
        popupMenu.getMenuInflater().inflate(menuRes, popupMenu.getMenu());
        //PopupMenu点击事件
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
        //PopupMenu消失事件
        popupMenu.setOnDismissListener(onDismissListener);
        //通过反射机制修改弹出位置，在点击的位置弹出PopupMenu
        try {
            //获取PopupMenu类的成员变量MenuPopupHelper mPopup
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            Object o = field.get(popupMenu);
            //MenuPopupHelper -> show(int x, int y)方法
            if (o instanceof MenuPopupHelper) {
                MenuPopupHelper menuPopupHelper = (MenuPopupHelper) o;
                int[] position = new int[2];
                //获取anchor左上角在屏幕上的相对坐标
                anchor.getLocationInWindow(position);
                //计算xOffset、yOffset，相对anchor左下角位置为弹出位置
                int xOffset = (point.x - position[0]);
                int yOffset;
                //菜单高度
                int popupMenuHeight = DpUtil.dp2px(48 * popupMenu.getMenu().size());
                //如果菜单高度大于底部剩余空间，菜单就会向上弹出；否则向下弹出
                if (DpUtil.getScreenHeight() - point.y >= popupMenuHeight) {
                    yOffset = (point.y - (position[1] + anchor.getHeight()));
                } else {
                    yOffset = (point.y - (position[1]));
                }
                menuPopupHelper.show(xOffset, yOffset);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            //出错时调用普通show方法。未出错时此方法也不会影响正常显示
            popupMenu.show();
        }
    }

    public interface OnImageClick {
        void onImageClick(MyImageView imageView, int x, int y);

        void onVoiceClick(MyImageView imageView);
    }

    public interface OnSendSuccess {
        void onSuccess(String content, int type);
    }

}
