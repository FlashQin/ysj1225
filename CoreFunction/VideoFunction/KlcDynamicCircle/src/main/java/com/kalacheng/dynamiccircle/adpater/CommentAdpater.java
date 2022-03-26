package com.kalacheng.dynamiccircle.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.databinding.ItemMainTrendCommentBinding;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.commonview.listener.OnTrendCommentItemClickListener;
import com.kalacheng.commonview.view.TextRender;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class CommentAdpater extends RecyclerView.Adapter {
    public Context mContext;
    public List<ApiUsersVideoComments> mList = new ArrayList<>();
    private OnTrendCommentItemClickListener onTrendCommentItemClickListener;

    public CommentAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void load(List<ApiUsersVideoComments> mList) {
        this.mList = mList;
    }

    public void setOnTrendCommentItemClickListener(OnTrendCommentItemClickListener onTrendCommentItemClickListener) {
        this.onTrendCommentItemClickListener = onTrendCommentItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainTrendCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_main_trend_comment, parent, false);
        CommentViewHolder holder = new CommentViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CommentViewHolder) holder).getData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemMainTrendCommentBinding binding;

        public CommentViewHolder(ItemMainTrendCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // 设置此方法后，点击事件才能生效
//            LinkTouchMovementMethod linkTouchMovementMethod = new LinkTouchMovementMethod();
//            binding.tvName.setLinkTouchMovementMethod(linkTouchMovementMethod);
//            binding.tvName.setMovementMethod(linkTouchMovementMethod);
        }

        public void getData(final ApiUsersVideoComments bean) {
            this.binding.setViewModel(bean);
            this.binding.executePendingBindings();

            binding.tvComment.setTag(bean);
            ImageLoader.display(bean.avatar, binding.ivAvatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            if (bean.commentType == 1) {
                binding.tvName.setText(bean.userName);
            } else {
                binding.tvName.setText(WordUtil.strToSpanned(bean.userName + WordUtil.strAddColor(" 回复  ", "#333333") + bean.toUserName));
            }
            binding.tvComment.setText(TextRender.renderChatMessage(bean.content));
//            if (bean.commentType == 1) {
//            binding.tvComment.setText(TextRender.renderVideoComment(bean.userName + ":", "", bean.content, onTrendCommentItemClickListener));
//            } else {
//            binding.tvComment.setText(TextRender.renderVideoComment(bean.userName, bean.toUserName, bean.content, onTrendCommentItemClickListener));
//            }

            binding.layoutComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTrendCommentItemClickListener.onItemClick(bean);
                }
            });
            binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.uid).navigation();
                }
            });
        }
    }

}
