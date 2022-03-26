package com.kalacheng.util.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.R;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;
import com.kalacheng.util.databinding.SimpleImageurlTextBinding;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class SimpleImgTextAdapter extends RecyclerView.Adapter<SimpleImgTextAdapter.ViewHolder> {

    private List<SimpleImageUrlTextBean> mList = new ArrayList<>();
    private boolean mLayoutWidthWrapContent;//布局宽度自适应
    private int mLayoutWidthDp;//布局宽度
    private int widthDp;
    private int heightDp;
    private boolean mOval;//是否切圆
    private ImageView.ScaleType mScaleType;//剪裁类型
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBottom;
    private String mTextColor;//字体颜色
    private int mTextSize;//字体大小
    private OnBeanCallback itemClickCallback;
    private int sex;

    public SimpleImgTextAdapter(List<SimpleImageUrlTextBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setSex(int sex) {
        this.sex = sex;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SimpleImageurlTextBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.simple_imageurl_text,
                        parent, false);
        binding.setCallback(new OnItemClickCallback<SimpleImageUrlTextBean>() {
            @Override
            public void onClick(View view, SimpleImageUrlTextBean item) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (itemClickCallback != null) {
                    itemClickCallback.onClick(item);
                }
            }
        });
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mLayoutWidthWrapContent) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.binding.layoutIcon.setLayoutParams(params);
        } else if (mLayoutWidthDp != 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DpUtil.dp2px(mLayoutWidthDp), LinearLayout.LayoutParams.WRAP_CONTENT);
            holder.binding.layoutIcon.setLayoutParams(params);
        }
        holder.binding.layoutIcon.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.binding.icon.getLayoutParams();
        if (widthDp != 0) {
            layoutParams.width = DpUtil.dp2px(widthDp);
        }
        if (heightDp != 0) {
            layoutParams.height = DpUtil.dp2px(heightDp);
        }
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.icon.setOval(mOval);
        if (mScaleType != null) {
            holder.binding.icon.setScaleType(mScaleType);
        }
        if (!TextUtils.isEmpty(mTextColor)) {
            holder.binding.name.setTextColor(Color.parseColor(mTextColor));
        }
        if (mTextSize != 0) {
            holder.binding.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        }

        if (mList.get(position).src != 0) {
            ImageLoader.display(mList.get(position).src, holder.binding.icon);
        } else {
            ImageLoader.display(mList.get(position).url, holder.binding.icon);
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleImageurlTextBinding binding;

        public ViewHolder(SimpleImageurlTextBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * 设置布局宽度自适应
     */
    public void setLayoutWidthWrapContent() {
        mLayoutWidthWrapContent = true;
    }

    /**
     * 设置布局宽度
     */
    public void setLayoutWidth(int layoutWidthDp) {
        mLayoutWidthDp = layoutWidthDp;
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
     * 设置字体颜色
     */
    public void setTextColor(String textColor) {
        mTextColor = textColor;
    }

    /**
     * 设置字体大小
     */
    public void setTextSize(int textSize) {
        mTextSize = textSize;
    }

    /**
     * 设置点击回调
     */
    public void setOnItemClickCallback(OnBeanCallback clickCallback) {
        this.itemClickCallback = clickCallback;
    }
}
