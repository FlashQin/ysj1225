package com.kalacheng.live.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.bean.SimpleImageSrcTextBean;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.util.databinding.SimpleImagetextBinding;
import com.kalacheng.live.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class LiveFunctionAdapter extends RecyclerView.Adapter<LiveFunctionAdapter.ViewHolder> {

    private List<SimpleImageSrcTextBean> mList = new ArrayList<>();

    public LiveFunctionAdapter(Context context, List<SimpleImageSrcTextBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SimpleImagetextBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.simple_imagetext,
                        parent, false);
        binding.setCallback(new OnItemClickCallback<SimpleImageSrcTextBean>() {
            @Override
            public void onClick(View view, SimpleImageSrcTextBean item) {
//                LiveBundle.getInstance().sendLM_DialogDismiss();
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_DialogDismiss, null);
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.FunctionMsg, item.src);


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
        SimpleImagetextBinding binding;

        public ViewHolder(SimpleImagetextBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
