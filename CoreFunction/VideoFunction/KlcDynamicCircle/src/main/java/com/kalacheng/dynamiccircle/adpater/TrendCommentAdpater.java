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
import com.kalacheng.dynamiccircle.databinding.ItemDialogTrendCommentBinding;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.commonview.listener.OnTrendCommentItemClickListener;
import com.kalacheng.commonview.view.TextRender;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TrendCommentAdpater extends RecyclerView.Adapter {
    private Context mContext;
    private List<ApiUsersVideoComments> mList = new ArrayList<>();

    private OnTrendCommentItemClickListener onTrendCommentItemClickListener;

    public TrendCommentAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getLoadData(List<ApiUsersVideoComments> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    public void getRefresh(List<ApiUsersVideoComments> data) {
        this.mList.clear();
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    public void insertData(int position, ApiUsersVideoComments bean) {
        this.mList.add(position, bean);
        notifyDataSetChanged();
    }

    public void setOnTrendCommentItemClickListener(OnTrendCommentItemClickListener onTrendCommentItemClickListener) {
        this.onTrendCommentItemClickListener = onTrendCommentItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDialogTrendCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_dialog_trend_comment, parent, false);
        TrendCommentViewHolder holder = new TrendCommentViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TrendCommentViewHolder) holder).setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TrendCommentViewHolder extends RecyclerView.ViewHolder {
        ItemDialogTrendCommentBinding binding;

        public TrendCommentViewHolder(ItemDialogTrendCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void setData(final ApiUsersVideoComments bean) {
            this.binding.setViewModel(bean);
            this.binding.executePendingBindings();

            ImageLoader.display(bean.avatar, binding.ivAvatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            if (bean.commentType == 1) {
                binding.tvName.setText(bean.userName);
            } else {
                binding.tvName.setText(WordUtil.strToSpanned(bean.userName + WordUtil.strAddColor(" 回复  ", "#333333") + bean.toUserName));
            }
            binding.tvComment.setText(TextRender.renderChatMessage(bean.content));

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
