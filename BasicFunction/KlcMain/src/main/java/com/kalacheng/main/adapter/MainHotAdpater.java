package com.kalacheng.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.model.LiveBean;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.AppAds;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.MainhotItmeBinding;
import com.kalacheng.util.utils.DpUtil;

import java.util.ArrayList;
import java.util.List;


public class MainHotAdpater extends RecyclerView.Adapter {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private int Type;
    private View mHeaderView;
    private Context mContext;
    private List<LiveBean> mList = new ArrayList<>();
    private List<AppAds> mSlideList = new ArrayList<>();

    public MainHotAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void loadData(List<LiveBean> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    public void RefreshData(List<LiveBean> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
        notifyItemInserted(0);

    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeadHotViewHolder(mHeaderView);
        } else {
            MainhotItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.mainhot_itme, parent, false);
            HotViewHolder holder = new HotViewHolder(binding);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        int with = DpUtil.getScreenWidth() / 2 - 25;
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) ((HotViewHolder) holder).binding.cover.getLayoutParams();
        linearParams.width = with;
        linearParams.height = with;
        ((HotViewHolder) holder).binding.cover.setLayoutParams(linearParams);
        ((HotViewHolder) holder).setData(mList.get(position - 1));


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }


    public class HotViewHolder extends RecyclerView.ViewHolder {
        MainhotItmeBinding binding;

        public HotViewHolder(MainhotItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final LiveBean bean) {
            if (bean.type == 0) {
                binding.roomtype.setBackgroundResource(R.mipmap.icon_main_live_type_0);
                binding.roomtype.setText(mContext.getResources().getString(R.string.roomtype1));
            } else if (bean.type == 1) {
                binding.roomtype.setBackgroundResource(R.mipmap.icon_main_live_type_1);
                binding.roomtype.setText(mContext.getResources().getString(R.string.roomtype2));
            } else if (bean.type == 2) {
                binding.roomtype.setBackgroundResource(R.mipmap.icon_main_live_type_2);
                binding.roomtype.setText(mContext.getResources().getString(R.string.roomtype3));
            } else if (bean.type == 3) {
                binding.roomtype.setBackgroundResource(R.mipmap.icon_main_live_type_3);
                binding.roomtype.setText(mContext.getResources().getString(R.string.roomtype4));
            }

            binding.setViewModel(bean);
            binding.executePendingBindings();
            binding.ReleFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.islive == 1) {
//                        LookRoomUtlis.getInstance().getData(bean, mContext);
                    } else {
                        ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.uid).navigation();
                    }
                }
            });

        }
    }

    public class HeadHotViewHolder extends RecyclerView.ViewHolder {
        public HeadHotViewHolder(@NonNull View itemView) {
            super(itemView);

        }

    }

}
