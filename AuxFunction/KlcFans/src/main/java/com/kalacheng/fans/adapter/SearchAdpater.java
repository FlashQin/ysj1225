package com.kalacheng.fans.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.model.LiveBean;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.fans.R;
import com.kalacheng.fans.databinding.SearchItmeBinding;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.util.utils.CheckDoubleClick;

import java.util.ArrayList;
import java.util.List;

public class SearchAdpater extends RecyclerView.Adapter<SearchAdpater.SearchViewHolder> {

    private Context mContext;
    private List<LiveBean> mList = new ArrayList<>();

    public SearchAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void load(List<LiveBean> mList) {
        this.mList.clear();
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.search_itme, parent, false);
        SearchViewHolder holder = new SearchViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.setData(mList.get(position));
        SexUtlis.getInstance().setSex(mContext, holder.binding.layoutSex, mList.get(position).sex, 0);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        SearchItmeBinding binding;

        public SearchViewHolder(@NonNull SearchItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final LiveBean bean) {
            binding.setViewModel(bean);
            binding.executePendingBindings();

            binding.followItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.uid).navigation();
                }
            });
        }
    }

}
