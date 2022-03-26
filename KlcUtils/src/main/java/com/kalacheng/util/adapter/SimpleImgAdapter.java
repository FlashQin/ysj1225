package com.kalacheng.util.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.R;
import com.kalacheng.util.bean.SimpleImgBean;
import com.kalacheng.util.databinding.SimpleImageBinding;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.listener.OnBeanCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 * 圆型img
 */

public class SimpleImgAdapter extends RecyclerView.Adapter<SimpleImgAdapter.ViewHolder> {

    private List<SimpleImgBean> mList = new ArrayList<>();
    private int widthDp;
    private int heightDp;
    private boolean mOval;//是否切圆
    private ImageView.ScaleType mScaleType;//剪裁类型
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBottom;
    private OnBeanCallback<SimpleImgBean> itemClickCallback;
    private boolean forbidClick;//是否禁止点击

    public SimpleImgAdapter() {

    }

    public void setData(List<SimpleImgBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<SimpleImgBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SimpleImageBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.simple_image, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.layoutIcon.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.binding.icon.getLayoutParams();
        if (widthDp != 0)
            layoutParams.width = DpUtil.dp2px(widthDp);
        if (heightDp != 0)
            layoutParams.height = DpUtil.dp2px(heightDp);
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

        holder.binding.icon.setOval(mOval);
        if (mScaleType != null) {
            holder.binding.icon.setScaleType(mScaleType);
        }
        if (mList.get(position).src != 0) {
            ImageLoader.display(mList.get(position).src, holder.binding.icon);
        } else {
            ImageLoader.display(mList.get(position).url, holder.binding.icon);
        }
        if (!forbidClick) {
            holder.binding.layoutIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickCallback != null) {
                        itemClickCallback.onClick(mList.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleImageBinding binding;

        public ViewHolder(SimpleImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * 设置宽高
     */
    public void setImgWidthHeight(int widthDp, int heightDp) {
        this.widthDp = widthDp;
        this.heightDp = heightDp;
    }

    /**
     * 是否切圆
     */
    public void setOval(boolean oval) {
        mOval = oval;
    }

    /**
     * 设置剪裁类型
     */
    public void setScaleType(ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
    }

    /**
     * 设置边界
     */
    public void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        mPaddingLeft = paddingLeft == 0 ? 0 : DpUtil.dp2px(paddingLeft);
        mPaddingTop = paddingTop == 0 ? 0 : DpUtil.dp2px(paddingTop);
        mPaddingRight = paddingRight == 0 ? 0 : DpUtil.dp2px(paddingRight);
        mPaddingBottom = paddingBottom == 0 ? 0 : DpUtil.dp2px(paddingBottom);
    }

    /**
     * 设置点击回调
     */
    public void setOnItemClickCallback(OnBeanCallback<SimpleImgBean> clickCallback) {
        this.itemClickCallback = clickCallback;
    }

    /**
     * 禁止点击事件
     */
    public void setForbidClick(boolean forbidClick) {
        this.forbidClick = forbidClick;
    }
}
