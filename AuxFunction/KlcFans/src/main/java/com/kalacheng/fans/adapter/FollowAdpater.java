package com.kalacheng.fans.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.fans.R;
import com.kalacheng.fans.databinding.FollowItemBinding;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiUserAtten;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

//关注和粉丝公用一个适配器
public class FollowAdpater extends RecyclerView.Adapter<FollowAdpater.FollowViewHolder> {
    private Context mContext;
    private List<ApiUserAtten> mList = new ArrayList<>();
    private OnBeanCallback<ApiUserAtten> onBeanCallback;
    private OnBeanCallback<ApiUserAtten> onFollowClickListener;

    public FollowAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getLoadData(List<ApiUserAtten> data) {
        if (data != null) {
            this.mList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void getRefreshData(List<ApiUserAtten> data) {
        this.mList.clear();
        if (data != null) {
            this.mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FollowItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.follow_item, parent, false);
        FollowViewHolder holder = new FollowViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowViewHolder holder, int position) {
        holder.setData(mList.get(position));
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FollowViewHolder extends RecyclerView.ViewHolder {
        FollowItemBinding binding;

        public FollowViewHolder(@NonNull FollowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final ApiUserAtten bean) {
            binding.setViewModel(bean);
            binding.executePendingBindings();
//            binding.ivSex.setImageResource(bean.sex == 2 ? R.mipmap.icon_girl_white : R.mipmap.icon_boy_white);
//            binding.layoutSex.setBackgroundResource(bean.sex == 2 ? R.drawable.bg_sex_girl : R.drawable.bg_sex_boy);
//            binding.tvAge.setText(bean.age + "");
            ImageLoader.display(bean.avatar_thumb, binding.iconImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

            SexUtlis.getInstance().setSex(mContext, binding.layoutSex, bean.sex, bean.age);
            if (bean.role == 1) {
                ImageLoader.display(bean.anchorGradeImg, binding.ivGrade);
            } else {
                ImageLoader.display(bean.userGradeImg, binding.ivGrade);
            }
            ImageLoader.display(bean.wealthGradeImg, binding.ivWealthGrade);
            if (!TextUtils.isEmpty(bean.nobleGradeImg)) {
                binding.ivNobleGrade.setVisibility(View.VISIBLE);
                ImageLoader.display(bean.nobleGradeImg, binding.ivNobleGrade);
            } else {
                binding.ivNobleGrade.setVisibility(View.GONE);
            }
            if (bean.status == 1) {
                binding.tvFollow.setBackgroundResource(R.drawable.bg_follow_yes);
                binding.tvFollow.setText("取消关注");
            } else {
                binding.tvFollow.setBackgroundResource(R.drawable.bg_follow_no);
                binding.tvFollow.setText("关注");
            }

            binding.followItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (onBeanCallback != null) {
                        onBeanCallback.onClick(bean);
                    }
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.uid).navigation();
                }
            });
            binding.tvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (onFollowClickListener != null) {
                        onFollowClickListener.onClick(bean);
                    }
                }
            });
        }
    }

    public void setOnBeanCallback(OnBeanCallback<ApiUserAtten> onBeanCallback) {
        this.onBeanCallback = onBeanCallback;
    }

    public void setOnFollowClickListener(OnBeanCallback<ApiUserAtten> onFollowClickListener) {
        this.onFollowClickListener = onFollowClickListener;
    }
}
