package com.kalacheng.centercommon.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.listener.OnItemClickListener;
import com.kalacheng.buscommon.model.TabInfoDto;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemTexttagBinding;
import com.kalacheng.util.utils.DrawableUtil;

import java.util.ArrayList;
import java.util.List;

public class InterestTagAdpater extends RecyclerView.Adapter<InterestTagAdpater.ViewHolder> {

    private List<TabInfoDto> mList = new ArrayList<>();
    private OnItemClickListener<TabInfoDto> mCallBack;
    private final DrawableUtil.Builder builder;
    private Drawable drawable;

    public InterestTagAdpater(List<TabInfoDto> data) {
        mList.clear();
        if (data != null) {
            mList.addAll(data);
        }
        builder = DrawableUtil.getBuilder(DrawableUtil.Builder.RECTANGLE);
        builder.setStroke(2, Color.parseColor("#c8c8c8"));
        builder.setColor(Color.parseColor("#ffffff"));
        builder.setCornerRadius(40f);
        drawable = builder.create();
    }

    public void getData(List<TabInfoDto> data) {
        mList.clear();
        if (data != null) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InterestTagAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTexttagBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_texttag, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull InterestTagAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        if (mList.get(position).status == 0) {
            holder.binding.text.setBackground(drawable);
            holder.binding.text.setTextColor(Color.parseColor("#c8c8c8"));
        } else {
            DrawableUtil.Builder builder = DrawableUtil.getBuilder(DrawableUtil.Builder.RECTANGLE);
            try {
                builder.setStroke(2, Color.parseColor(mList.get(position).fontColor));
            } catch (Exception e) {
                builder.setStroke(2, Color.parseColor("#ff0000"));
            }
            builder.setColor(Color.parseColor("#ffffff"));
            builder.setCornerRadius(40f);
            Drawable drawable = builder.create();
            holder.binding.text.setBackground(drawable);
            try {
                holder.binding.text.setTextColor(Color.parseColor(mList.get(position).fontColor));
            } catch (Exception e) {
                holder.binding.text.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        holder.binding.text.setText(mList.get(position).name);
        holder.binding.llTagtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mCallBack)
                    mCallBack.onItemClick(position, mList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemTexttagBinding binding;

        public ViewHolder(@NonNull ItemTexttagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public void setOnItemClickListener(OnItemClickListener<TabInfoDto> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
