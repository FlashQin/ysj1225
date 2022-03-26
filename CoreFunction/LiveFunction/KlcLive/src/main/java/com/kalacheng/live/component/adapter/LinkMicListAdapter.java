package com.kalacheng.live.component.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.LiveBean;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiUsableAnchorResp;
import com.kalacheng.live.R;
import com.kalacheng.live.databinding.ItemLinkMicBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dxx on 2017/11/10.
 */

public class LinkMicListAdapter extends RecyclerView.Adapter<LinkMicListAdapter.ViewHolder> {

    List<ApiUsableAnchorResp> mList = new ArrayList<>();

    public LinkMicListAdapter() {

    }

    public void setList(final List<ApiUsableAnchorResp> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLinkMicBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_link_mic,
                        parent, false);
        binding.setCallback(new com.kalacheng.util.listener.OnItemClickCallback<ApiUsableAnchorResp>() {
            @Override
            public void onClick(View view, ApiUsableAnchorResp item) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LaunchInteraction, item);

            }
        });
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLinkMicBinding binding;

        public ViewHolder(ItemLinkMicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickCallback {
        void onClick(LiveBean item);
    }

}
