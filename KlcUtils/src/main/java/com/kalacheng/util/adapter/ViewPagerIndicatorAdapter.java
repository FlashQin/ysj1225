package com.kalacheng.util.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.TextViewUtil;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerIndicatorAdapter extends RecyclerView.Adapter<ViewPagerIndicatorAdapter.Vh> {
    private List<String> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;
    private ViewPagerIndicatorListener viewPagerIndicatorListener;
    private int selectPosition;
    //正常字体颜色
    private int mNormalColor;
    //高亮字体颜色
    private int mLightColor;
    //正常字体加粗
    private boolean mNormalBold;
    //选中字体加粗
    private boolean mLightBold;
    //正常字体大小
    private int mNormalTextSize;
    //选中字体大小
    private int mLightTextSize;
    //间距
    private int mPadding;
    //是否显示指示器
    private boolean mIndicatorShow;
    //指示器宽度
    private int mIndicatorWidth;
    //指示器高度
    private int mIndicatorHeight;
    //指示器颜色
    private int mIndicatorColor;
    //指示器底部距离
    private int mIndicatorMarginBottom;

    public ViewPagerIndicatorAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null && viewPagerIndicatorListener != null) {
                    int position = (int) tag;
                    viewPagerIndicatorListener.onItemClick(mList.get(position), position);
                }
            }
        };
    }

    public void setData(List<String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<String> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public void setNormalColor(int normalColor) {
        this.mNormalColor = normalColor;
    }

    public void setLightColor(int lightColor) {
        this.mLightColor = lightColor;
    }

    public void setNormalBold(boolean normalBold) {
        this.mNormalBold = normalBold;
    }

    public void setLightBold(boolean lightBold) {
        this.mLightBold = lightBold;
    }

    public void setNormalTextSize(int normalTextSize) {
        this.mNormalTextSize = normalTextSize;
    }

    public void setLightTextSize(int lightTextSize) {
        this.mLightTextSize = lightTextSize;
    }

    public void setPadding(int padding) {
        this.mPadding = padding;
    }

    public void setIndicatorShow(boolean indicatorShow) {
        this.mIndicatorShow = indicatorShow;
    }

    public void setIndicatorWidth(int indicatorWidth) {
        this.mIndicatorWidth = indicatorWidth;
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.mIndicatorHeight = indicatorHeight;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    public void setIndicatorMarginBottom(int indicatorMarginBottom) {
        this.mIndicatorMarginBottom = indicatorMarginBottom;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_view_pager_indicator, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        holder.setData(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Vh extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvIndicator;

        public Vh(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvIndicator = itemView.findViewById(R.id.tvIndicator);
            itemView.setOnClickListener(mOnClickListener);
        }

        void setData(String bean, int position) {
            itemView.setTag(position);
            tvName.setText(bean);
            tvName.setPadding(DpUtil.dp2px(mPadding), 0, DpUtil.dp2px(mPadding), 0);

            if (position == selectPosition) {
                tvName.setTextColor(mLightColor);
                if (mLightBold) {
                    tvName.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tvName.setTypeface(Typeface.DEFAULT);
                }
                tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, mLightTextSize);
                if (mIndicatorShow) {
                    tvIndicator.setVisibility(View.VISIBLE);
                    tvIndicator.setBackgroundColor(mIndicatorColor);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tvIndicator.getLayoutParams();
                    if (mIndicatorWidth != 0) {
                        params.width = DpUtil.dp2px(mIndicatorWidth);
                    }
                    params.height = DpUtil.dp2px(mIndicatorHeight);
                    params.setMargins(0, 0, 0, DpUtil.dp2px(mIndicatorMarginBottom));
                }
                TextViewUtil.setDrawableNull(tvName);
//                if (bean.equals("附近")) {
//                    TextViewUtil.setDrawableRight(tvName, R.mipmap.icon_triangle);
//                } else if (bean.equals("附近的人")) {
//                    TextViewUtil.setDrawableRight(tvName, R.mipmap.icon_voice_hot1);
//                } else {
//                    TextViewUtil.setDrawableNull(tvName);
//                }
            } else {
                tvName.setTextColor(mNormalColor);
                if (mNormalBold) {
                    tvName.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tvName.setTypeface(Typeface.DEFAULT);
                }
                tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, mNormalTextSize);
                tvIndicator.setVisibility(View.GONE);
//                if (bean.equals("附近的人")) {
//                    TextViewUtil.setDrawableRight(tvName, R.mipmap.icon_voice_hot1);
//                } else {
//                    TextViewUtil.setDrawableNull(tvName);
//                }
                TextViewUtil.setDrawableNull(tvName);
            }

        }
    }

    public void setViewPagerIndicatorListener(ViewPagerIndicatorListener viewPagerIndicatorListener) {
        this.viewPagerIndicatorListener = viewPagerIndicatorListener;
    }

    public interface ViewPagerIndicatorListener {
        void onItemClick(String name, int position);
    }

    public List<String> getTitle() {
        return mList;
    }
}
