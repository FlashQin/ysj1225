package com.kalacheng.dynamiccircle.adpater;

import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.databinding.ItemHomepageTrendBinding;
import com.kalacheng.base.event.VideoZanEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiUserVideo;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.commonview.listener.OnTrendCommentItemClickListener;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.TextViewUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class HomePageTrendAdapter extends RecyclerView.Adapter<HomePageTrendAdapter.ViewHolder> {
    private Context mContext;
    private List<ApiUserVideo> mList = new ArrayList<>();
    private DynamicListItemCallBack back;
    private boolean isHomePage;
    private String location;

    public HomePageTrendAdapter(boolean isHomePage) {
        this.isHomePage = isHomePage;
    }

    public void setLocation(String location){
        this.location = location;
    }

    //加载更多
    public void loadData(List<ApiUserVideo> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新
    public void RefreshData(List<ApiUserVideo> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    public void setData(List<ApiUserVideo> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void setZanComment(VideoZanEvent event){
        if (null != event){
            if (event.getIsZanComment()==1){
                this.mList.get(event.getPosition()).likes = event.getZanNumber();
                this.mList.get(event.getPosition()).isLike = event.getIsLike();
            }else if (event.getIsZanComment()==2){
                this.mList.get(event.getPosition()).comments = event.getCommentNumber();
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 获取数据列表对象
     */
    public List<ApiUserVideo> getData() {
        return mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ItemHomepageTrendBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_homepage_trend,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.setIsHomePage(isHomePage);
        ImageLoader.display(mList.get(position).avatar, holder.binding.ivAvatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        holder.binding.ivSex.setImageResource(mList.get(position).sex == 2 ? R.mipmap.icon_girl : R.mipmap.icon_boy);
        ImageLoader.display(mList.get(position).gradeImg, holder.binding.ivGrade);
        ImageLoader.display(mList.get(position).wealthGradeImg, holder.binding.ivWealthGrade);

        SexUtlis.getInstance().setSex(mContext, holder.binding.layoutSex, mList.get(position).sex, 0);

        if (TextUtils.isEmpty(mList.get(position).nobleGradeImg)) {
            holder.binding.ivNobleGrade.setVisibility(View.GONE);
        } else {
            holder.binding.ivNobleGrade.setVisibility(View.VISIBLE);
            ImageLoader.display(mList.get(position).nobleGradeImg, holder.binding.ivNobleGrade);
        }
        if (!TextUtils.isEmpty(mList.get(position).city) && !TextUtils.isEmpty(mList.get(position).address)) {
            holder.binding.tvCity.setText(mList.get(position).city + " · " + mList.get(position).address);
        } else {
            holder.binding.tvCity.setText((TextUtils.isEmpty(mList.get(position).city) ? "" : mList.get(position).city) + (TextUtils.isEmpty(mList.get(position).address) ? "" : mList.get(position).address));
        }
//        DateUtil addTime = new DateUtil(mList.get(position).addtimeStr, "yyyy-MM-dd HH:mm:ss");
        holder.binding.tvAddTime.setText(mList.get(position).addtimeStr);
        if (!TextUtils.isEmpty(mList.get(position).title)) {
            holder.binding.tvTitle.setVisibility(View.VISIBLE);
        } else {
            holder.binding.tvTitle.setVisibility(View.GONE);
        }
        if (mList.get(position).isLike == 1) {
            TextViewUtil.setDrawableLeft(holder.binding.tvLikes, R.mipmap.icon_likes);
        } else {
            TextViewUtil.setDrawableLeft(holder.binding.tvLikes, R.mipmap.icon_likes_none);
        }
        if (mList.get(position).images != null && !TextUtils.isEmpty(mList.get(position).images)) {
            String images = mList.get(position).images;
            String[] imagesStr = images.split(",");
            initBanner(mList.get(position), imagesStr, holder.binding.convenientBanner, position);
        }
        if (!TextUtils.isEmpty(mList.get(position).videoTime)) {
            long minute = Long.parseLong(mList.get(position).videoTime) / (1000 * 60);
            long second = (Long.parseLong(mList.get(position).videoTime) - (1000 * 60 * minute)) / 1000;
            String str = "";
            if (minute >= 10) {
                str += minute;
            } else {
                str += "0" + minute;
            }
            str += ":";
            if (second >= 10) {
                str += second;
            } else {
                str += "0" + second;
            }
            holder.binding.tvVideoTime.setText(str);
        } else {
            holder.binding.tvVideoTime.setText("00:00");
        }

        //观看动态视频
        if (mList.get(position).type == 1) {
            holder.binding.layoutVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    List<ApiUserVideo> list = new ArrayList<>();
                    list.add(mList.get(position));
                    ARouter.getInstance().build(ARouterPath.LookVideo)
                            .withInt(ARouterValueNameConfig.VIDEO_TYPE, -1)
                            .withInt(ARouterValueNameConfig.POSITION, 0)
                            .withInt(ARouterValueNameConfig.ITEM_POSITION, position)
                            .withString(ARouterValueNameConfig.COMMENT_LOCATION, location)
                            .withParcelableArrayList(ARouterValueNameConfig.Beans, (ArrayList<? extends Parcelable>) list).navigation();
                }
            });
        }
        //查看个人信息
        holder.binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, mList.get(position).uid).navigation();
            }
        });
        //喜欢
        holder.binding.tvLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                back.onLike(position);
            }
        });
        //评论
        holder.binding.tvCommentMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                back.onLookMoreComment(mList.get(position));
            }
        });
        //分享
        holder.binding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                back.onShare(mList.get(position), location[1]);

            }
        });
        //去评论
        holder.binding.tvComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
//                back.onComment(mList.get(position), position);
                back.onLookMoreComment(mList.get(position));
            }
        });
        //评论
        if (mList.get(position).commentList != null && mList.get(position).commentList.size() > 0) {
            holder.binding.rvComments.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            holder.binding.rvComments.setLayoutManager(manager);
            holder.binding.rvComments.setHasFixedSize(true);
            CommentAdpater adapter = new CommentAdpater(mContext);
            holder.binding.rvComments.setAdapter(adapter);
            adapter.load(mList.get(position).commentList);

            adapter.setOnTrendCommentItemClickListener(new OnTrendCommentItemClickListener() {
                @Override
                public void onItemClick(ApiUsersVideoComments commentBean) {
                    back.onReply(mList.get(position), commentBean, position);
                }

                @Override
                public void onUserName(ApiUsersVideoComments commentBean) {

                }

                @Override
                public void onToUserName(ApiUsersVideoComments commentBean) {

                }
            });
        } else {
            holder.binding.rvComments.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemHomepageTrendBinding binding;

        public ViewHolder(ItemHomepageTrendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void initBanner(final ApiUserVideo apiUserVideo, String[] imagesStr, ConvenientBanner convenientBanner, final int itemPosition) {
        final List<String> stringList = new ArrayList<>();
        for (int i = 0; i < imagesStr.length; i++) {
            stringList.add(imagesStr[i]);
        }
        if (stringList.size() > 1) {
            convenientBanner.setCanLoop(true);
            convenientBanner.setPointViewVisible(true);
        } else {
            convenientBanner.setCanLoop(false);
            convenientBanner.setPointViewVisible(false);
        }
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, stringList);
        convenientBanner.setOnItemClickListener(new com.bigkoo.convenientbanner.listener.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                List<ApiUserVideo> list = new ArrayList<>();
                list.add(apiUserVideo);
                ARouter.getInstance().build(ARouterPath.LookVideo)
                        .withInt(ARouterValueNameConfig.VIDEO_TYPE, -1)
                        .withInt(ARouterValueNameConfig.POSITION, 0)
                        .withInt(ARouterValueNameConfig.ITEM_POSITION, itemPosition)
                        .withString(ARouterValueNameConfig.COMMENT_LOCATION, location)
                        .withParcelableArrayList(ARouterValueNameConfig.Beans, (ArrayList<? extends Parcelable>) list).navigation();
            }
        });
        convenientBanner.setPageIndicator(new int[]{R.drawable.banner_indicator_grey, R.drawable.banner_indicator_white});
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
//        convenientBanner.startTurning(3000);
        convenientBanner.setManualPageable(true);
        if (convenientBanner.getViewPager() != null) {
            convenientBanner.getViewPager().setClipToPadding(false);
            convenientBanner.getViewPager().setClipChildren(false);
            try {
                ((RelativeLayout) convenientBanner.getViewPager().getParent()).setClipChildren(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            convenientBanner.getViewPager().setOffscreenPageLimit(3);
        }

    }

    // banner加载图片适配
    public static class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.center_banner_item, null);
            imageView = view.findViewById(R.id.iv_banner_img);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            // 图片
            ImageLoader.display(data, imageView);
        }
    }

    public void setDynamicListItemCallBack(DynamicListItemCallBack back) {
        this.back = back;
    }

    public interface DynamicListItemCallBack {
        public void onShare(ApiUserVideo videoBean, int locationY);//分享

        public void onComment(ApiUserVideo bean, int positon);//评论

        public void onReply(ApiUserVideo bean, ApiUsersVideoComments comments, int position);//回复

        public void onLike(int poistion);//喜欢

        public void onLookMoreComment(ApiUserVideo bean);//查看跟多评论


    }
}
