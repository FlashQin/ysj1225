package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.base.listener.OnItemClickListener;
import com.kalacheng.commonview.utils.IconUtil;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.JsonUtil;
import com.kalacheng.util.utils.LruJsonCache;
import com.kalacheng.util.utils.MImageGetter;
import com.kalacheng.util.utils.StringShowUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.VerticalImageSpan;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxf on 2018/10/10.
 */

public class LiveChatAdapter extends RecyclerView.Adapter {
    //    消息类型1进场消息2退场消息3礼物消息4点亮5红包6禁言解禁消息7设置取消管理员8踢人消息9购买守护10关注和取消关注11文字聊天消息12主播离开回来消息13系统消息14弹幕消息
    private List<ApiSimpleMsgRoom> mList;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;
    private OnItemClickListener<ApiSimpleMsgRoom> mOnItemClickListener;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Context mContext;
    private LruJsonCache cache;

    public LiveChatAdapter(Context context) {
        this.mContext = context;
        mList = new ArrayList<ApiSimpleMsgRoom>();
        mInflater = LayoutInflater.from(context);
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null) {
                    ApiSimpleMsgRoom bean = (ApiSimpleMsgRoom) tag;
                    if (bean.type != 0 && bean.type != 13 && mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(0, bean);
                    }
                }

            }
        };
        cache = LruJsonCache.get(mContext);
    }

    public void setData(List<ApiSimpleMsgRoom> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public void addDataList(List<ApiSimpleMsgRoom> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<ApiSimpleMsgRoom> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return mList.get(position).getType();
//    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == LiveChatBean.RED_PACK) {
//            return new RedPackVh(mInflater.inflate(R.layout.item_live_chat_red_pack, parent, false));
//        } else {
        return new Vh(mInflater.inflate(R.layout.item_live_chat, null, false));
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
//        if (vh instanceof Vh) {
        ((Vh) vh).setData(mList.get(position));
//        } else if (vh instanceof RedPackVh) {
//            ((RedPackVh) vh).setData(mList.get(position));
//        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
    }

    class RedPackVh extends RecyclerView.ViewHolder {

        TextView mTextView;

        public RedPackVh(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        void setData(String bean) {
            mTextView.setText(bean);
        }
    }

    class Vh extends RecyclerView.ViewHolder {

        TextView mTextView;
        RelativeLayout live_chat_imageRe;

        RoundedImageView live_chat_fansorguard;
        RoundedImageView live_chat_headimage;
        RelativeLayout live_chat_headimage_isvip;

        public Vh(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.live_chat_Content);
            live_chat_imageRe = itemView.findViewById(R.id.live_chat_imageRe);
            live_chat_headimage = itemView.findViewById(R.id.live_chat_headimage);
            live_chat_fansorguard = itemView.findViewById(R.id.live_chat_fansorguard);
            live_chat_headimage_isvip = itemView.findViewById(R.id.live_chat_headimage_isvip);

            itemView.setOnClickListener(mOnClickListener);
        }

        void setData(ApiSimpleMsgRoom bean) {
            itemView.setTag(bean);
            if (bean.type == 13) {
                //13:系统消息
                live_chat_imageRe.setVisibility(View.GONE);
                mTextView.setTextColor(Color.parseColor("#FFD176"));
                mTextView.setText(bean.content);

            } else {
                live_chat_imageRe.setVisibility(View.VISIBLE);
                if (bean.type == 4) {
                    //4:点亮
                    mTextView.setTextColor(0xffffffff);
                } else {
                    mTextView.setTextColor(0xffffffff);
                }
                render(mTextView, bean);

                if (bean.type == 7 || bean.type == 8) {
                    //7:设置取消管理员 / 8:踢人消息
                    ImageLoader.display(bean.avatar, live_chat_headimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    live_chat_headimage_isvip.setBackgroundResource(0);
                    if (bean.isFans == 1) {
                        //粉丝 1:是粉丝
                        live_chat_fansorguard.setVisibility(View.VISIBLE);
                        ImageLoader.display(R.mipmap.followed_icon, live_chat_fansorguard);
                    } else {
                        live_chat_fansorguard.setVisibility(View.GONE);
                    }
                } else {
                    if (bean.isVip == 1) {
                        //1:是
                        live_chat_headimage_isvip.setBackgroundResource(R.drawable.yellow_oval);
                        ImageLoader.display(bean.avatar, live_chat_headimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        live_chat_fansorguard.setVisibility(View.GONE);

                    } else {
                        live_chat_headimage_isvip.setBackgroundResource(0);
                        if (bean.isSh == 1) {
                            //守护1:是
                            ImageLoader.display(bean.anchorAvatar, live_chat_headimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                            live_chat_fansorguard.setVisibility(View.VISIBLE);
                            ImageLoader.display(bean.avatar, live_chat_fansorguard, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        } else {
                            ImageLoader.display(bean.avatar, live_chat_headimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                            if (bean.isFans == 1) {
                                //粉丝 1:是粉丝
                                live_chat_fansorguard.setVisibility(View.VISIBLE);
                                ImageLoader.display(R.mipmap.followed_icon, live_chat_fansorguard);
                            } else {
                                live_chat_fansorguard.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        }
    }

    public void insertItem(ApiSimpleMsgRoom bean) {
        if (bean == null || bean.type == 1) {
            return;
        }
        int size = mList.size();
        mList.add(bean);
        notifyItemInserted(size);
        notifyItemChanged(size);
        int lastItemPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
        if (lastItemPosition != size) {
            mRecyclerView.smoothScrollToPosition(size);
        } else {
            mRecyclerView.scrollToPosition(size);
        }
        cache.put(LiveConstants.Cache_SmallMessage, JsonUtil.toJson(mList));
        Log.e("Cache>>>", "" + cache.getAsString(LiveConstants.Cache_SmallMessage));
    }

    public void scrollToBottom() {
        if (mList.size() > 0) {
            mRecyclerView.smoothScrollToPosition(mList.size() - 1);
        }
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }

    int index = 0;

    public void render(final TextView textView, final ApiSimpleMsgRoom bean) {

        index = 0;
        if (textView != null) {
            SpannableStringBuilder builder = createPrefix(null, bean);
            int color = 0;
//            if (bean.isAnchor()) {
//                color = 0xffffdd00;
//            } else {
            color = Color.parseColor("#FFD176");
//            }
            String url;
            if (bean.role == 1) {
                //1:主播
                url = "<img src='" + bean.anchorGrade + "'/>" + "&nbsp;"
                        + "<img src='" + bean.nobleGrade + "'/>" + "&nbsp;" + "<img src='" + bean.wealthGrade + "'/>" + "&nbsp;" + "<font color='#FFD176' size='12'>" + StringShowUtil.showNameAll(bean.uname) + ":" + "</font>";
                switch (bean.type) {
                    case 15:
                        //15:骰子
                        url += "<br/><img src='" + bean.content + "'/>";
                        break;
                    case 3:
                        //3:礼物消息
                        url += setSendGiftTextType(bean);
                        break;
                    default:
                        if (bean.fontDiscoloration == 1) {//是彩色
                            url += setTextColour(bean.content);
                        } else {//不是彩色
                            url += "<font color='#ffffff' size='12'>" + bean.content + "</font>";
                        }
                        break;
                }

            } else {
                url = "<img src='" + bean.userGrade + "'/>" + "&nbsp;"
                        + "<img src='" + bean.nobleGrade + "'/>" + "&nbsp;" + "<img src='" + bean.wealthGrade + "'/>" + "&nbsp;" + "<font color='#FFD176' size='12'>" + StringShowUtil.showNameAll(bean.uname) + ":" + "</font>";
                switch (bean.type) {
                    case 15:
                        //15:骰子
                        url += "<br/><img src='" + bean.content + "'/>";

                        break;
                    case 3:
                        //3:礼物消息
                        url += setSendGiftTextType(bean);
                        break;
                    default:
                        if (bean.fontDiscoloration == 1) {//是彩色
                            url += setTextColour(bean.content);
                        } else {//不是彩色
                            url += "<font color='#ffffff' size='12'>" + bean.content + "</font>";
                        }
                        break;
                }


            }

            textView.setText(Html.fromHtml(url, new MImageGetter(textView, mContext), null));

            /*switch (bean.type) {
                case 3:
                    builder = renderGift(color, builder, bean);
                    break;
                default:
                    builder = renderChat(color, builder, bean);
                    break;
            }
            textView.setText(builder);*/
        }



        /*if (bean.role == 1){
            ImageLoader.display(bean.anchorGrade, null, 0, 0, false, null, null, new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                    if (textView != null) {
                        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText());
                        builder.append(" ");
                        drawable.setBounds(0, 0, DpUtil.dp2px(28), DpUtil.dp2px(15));
                        builder.setSpan(new VerticalImageSpan(drawable), index, index+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                        textView.setText(builder);
                        index = index+1;
                    }
                }
            });
        }else {
            ImageLoader.display(bean.userGrade, null, 0, 0, false, null, null, new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                    if (textView != null) {
                        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText());
                        builder.append(" ");
                        drawable.setBounds(0, 0, DpUtil.dp2px(28), DpUtil.dp2px(15));
                        builder.setSpan(new VerticalImageSpan(drawable), index, index+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                        textView.setText(builder);
                        index = index+1;
                    }
                }
            });
        }

        ImageLoader.display(bean.wealthGrade, null, 0, 0, false, null, null, new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                if (textView != null) {
                    SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText());
                    drawable.setBounds(6, 0, DpUtil.dp2px(34), DpUtil.dp2px(15));
                    builder.setSpan(new VerticalImageSpan(drawable), index, index+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                    textView.setText(builder);
                    index = index+1;
                }
            }
        });
        ImageLoader.display(bean.nobleGrade, null, 0, 0, false, null, null, new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                if (textView != null) {
                    SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText());
                    drawable.setBounds(6, 0, DpUtil.dp2px(34), DpUtil.dp2px(15));
                    builder.setSpan(new VerticalImageSpan(drawable), index, index+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                    textView.setText(builder);
                    index = index+1;
                }
            }
        });*/
    }

    /**
     * 送礼物 文字样式
     */
    private String setSendGiftTextType(ApiSimpleMsgRoom bean) {
        String url = "";

        url = "<font color='#ffffff' size='12'>" + "赠送" + "</font>"
                + "<font color='#FFD176' size='12'>" + StringShowUtil.showNameAll(bean.touname) + "</font>"
                + "<font color='#ffffff' size='12'>" + bean.giftNumber + "个" + bean.giftname + "</font>";

        return url;
    }


    // 编辑彩色字体
    private final String colors[] = {"#FF3937", "#FFA837", "#FFFF37", "#6BFF37", "#37FFEB", "#37A2FF", "#F837FF"};

    private String setTextColour(String content) {
        String str = "";
        int position = 0;
        try {
            for (int i = 0; i < content.length(); i++) {
                if (i < content.length()) {
                    str += "<font color='" + colors[position] + "' size='1'>" + content.substring(i, i + 1) + "</font>";
                }
                position++;
                if (position > 6) {
                    position = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 生成前缀
     */
    private SpannableStringBuilder createPrefix(Drawable levelDrawable, ApiSimpleMsgRoom bean) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("   ");
        if (levelDrawable != null) {
            levelDrawable.setBounds(0, 0, DpUtil.dp2px(28), DpUtil.dp2px(15));
            builder.setSpan(new VerticalImageSpan(levelDrawable), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
//        int index = 2;
//        if (bean.isVip != 0) {//vip图标
////            Drawable vipDrawable = ContextCompat.getDrawable(BaseApplication.getInstance(), R.mipmap.icon_live_chat_vip);
//            if (levelDrawable != null) {
//                builder.append("  ");
//                levelDrawable.setBounds(0, 0, DpUtil.dp2px(28), DpUtil.dp2px(14));
//                builder.setSpan(new VerticalImageSpan(levelDrawable), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                index += 2;
//            }
//        }


//        if (bean.isManager()) {//直播间管理员图标
//            Drawable drawable = ContextCompat.getDrawable(AppContext.sInstance, R.mipmap.icon_live_chat_m);
//            if (drawable != null) {
//                builder.append("  ");
//                drawable.setBounds(0, 0, DpUtil.dp2px(14), DpUtil.dp2px(14));
//                builder.setSpan(new VerticalImageSpan(drawable), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                index += 2;
//            }
//        }
//        if (bean.isSh != 0) {//守护图标
////            Drawable drawable = ContextCompat.getDrawable(AppContext.sInstance,
////                    bean.getGuardType() == Constants.GUARD_TYPE_YEAR ? R.mipmap.icon_live_chat_guard_2 : R.mipmap.icon_live_chat_guard_1);
//            Drawable drawable = ContextCompat.getDrawable(BaseApplication.getInstance(), R.mipmap.icon_live_chat_guard_1);
//            if (drawable != null) {
//                builder.append("  ");
//                drawable.setBounds(2, 0, DpUtil.dp2px(14), DpUtil.dp2px(14));
//                builder.setSpan(new VerticalImageSpan(drawable), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                index += 2;
//            }
//        }
//        if (!TextUtils.isEmpty(bean.getLiangName())) {//靓号图标
//            Drawable drawable = ContextCompat.getDrawable(AppContext.sInstance, R.mipmap.icon_live_chat_liang);
//            if (drawable != null) {
//                builder.append("  ");
//                drawable.setBounds(0, 0, DpUtil.dp2px(14), DpUtil.dp2px(14));
//                builder.setSpan(new VerticalImageSpan(drawable), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
        return builder;
    }

    /**
     * 渲染普通聊天消息
     */
    private static SpannableStringBuilder renderChat(int color, SpannableStringBuilder builder, ApiSimpleMsgRoom bean) {
        int length = builder.length();
        String name = bean.uname;
        if (bean.type != 1) {//产品规定，进场消息不允许加冒号
            name += "：";
        }
        builder.append(name);
        builder.setSpan(new ForegroundColorSpan(color), length, length + name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(bean.content);
        if (bean.type == 4) {
            Drawable heartDrawable = ContextCompat.getDrawable(BaseApplication.getInstance(), IconUtil.getLiveLightIcon(5));
            if (heartDrawable != null) {
                builder.append(" ");
                heartDrawable.setBounds(0, 0, DpUtil.dp2px(12), DpUtil.dp2px(12));
                length = builder.length();
                builder.setSpan(new VerticalImageSpan(heartDrawable), length - 1, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }

    /**
     * 渲染送礼物消息
     */
    private static SpannableStringBuilder renderGift(int color, SpannableStringBuilder builder, ApiSimpleMsgRoom bean) {
        int length = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), length, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        length = builder.length();
        String niceName = bean.uname;
        if (null != niceName) {
            builder.append(niceName);
            builder.setSpan(new ForegroundColorSpan(color), length, length + niceName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        length = builder.length();
        String giftNum = bean.content;
        builder.append(giftNum);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), length, length + giftNum.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}
