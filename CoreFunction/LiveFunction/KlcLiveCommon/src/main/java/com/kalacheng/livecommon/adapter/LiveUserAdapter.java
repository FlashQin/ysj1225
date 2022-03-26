package com.kalacheng.livecommon.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.buscommon.model.ApiUserBasicInfo;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.ItemLiveUserBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dxx on 2017/11/10.
 */

public class LiveUserAdapter extends RecyclerView.Adapter<LiveUserAdapter.ViewHolder> {

    List<ApiUserBasicInfo> liveBean = new ArrayList<>();
    OnBeanCallback itemClickCallback;


    public void setliveBean(final List<ApiUserBasicInfo> list) {
        liveBean.clear();
        if (list != null) {
            liveBean.addAll(list);
        }
        notifyDataSetChanged();
    }


//    public LiveUserAdapter(List<ApiUserBasicInfo> list) {
//        liveBean.addAll(list);
//    }

    public void setOnItemClickCallback(OnBeanCallback clickCallback) {
        this.itemClickCallback = clickCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLiveUserBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_live_user,
                        parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(liveBean.get(position));
//        if (position == 0) {
//            if (userBean.hasContribution())
//            holder.binding.wrap.setImageResource(R.mipmap.icon_live_user_list_1);
//                mWrap.setImageResource(R.mipmap.icon_live_user_list_1);

//        } else if (position == 1) {
//            if (userBean.hasContribution())
//            holder.binding.wrap.setImageResource(R.mipmap.icon_live_user_list_2);
//                mWrap.setImageResource(R.mipmap.icon_live_user_list_2);

//        } else if (position == 2)
//            if (userBean.hasContribution())
//            holder.binding.wrap.setImageResource(R.mipmap.icon_live_user_list_3);
//                mWrap.setImageResource(R.mipmap.icon_live_user_list_3);
//        else
//            holder.binding.wrap.setImageDrawable(null);

        /*holder.binding.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/

        holder.binding.setCallback(new OnItemClickCallback<ApiUserBasicInfo>() {

            @Override
            public void onClick(View view, ApiUserBasicInfo item) {
                itemClickCallback.onClick(item);

            }
        });

        holder.binding.executePendingBindings();
    }

    int count;

    @Override
    public int getItemCount() {

        if (liveBean == null) {
            count = 0;
        } else if (liveBean.size() >= 0 && liveBean.size() <= 3) {
            count = liveBean.size();
        } else if (liveBean.size() > 3) {
            count = 3;
        }
        return count;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLiveUserBinding binding;

        public ViewHolder(ItemLiveUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public List<ApiUserBasicInfo> getList() {
        return liveBean;
    }


}
