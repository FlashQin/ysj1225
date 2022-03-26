package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buslivebas.entity.LiveRoomType;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.ItemRoomModeLayoutBinding;
import com.kalacheng.util.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

public class RoomModeAdapter extends RecyclerView.Adapter<RoomModeAdapter.ViewHolder> {

    private List<LiveRoomType> mList = new ArrayList<>();
    private Context mContext;
    private itemListener listener;
    private int itemPosition = 0;
    private String content = "";

    public RoomModeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(List<LiveRoomType> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    public String getEdit(){
        return content;
    }

    public void setEdit(int itemPosition, String content){
        this.itemPosition = itemPosition;
        this.content = content;
    }

    @NonNull
    @Override
    public RoomModeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRoomModeLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_room_mode_layout, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull List<Object> payloads) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.tvCompany.setVisibility(View.GONE);
        holder.binding.etValue.setVisibility(View.GONE);
        holder.binding.etValue.setText("");
        if (itemPosition == position) {
            holder.binding.ivChoice.setBackgroundResource(R.mipmap.selection);
            if (!TextUtils.isEmpty(content)){
                holder.binding.etValue.setText(content);
            }
        } else {
            holder.binding.ivChoice.setBackgroundResource(R.mipmap.unchecked);
        }

        switch (mList.get(position).roomType) {
            case 0:

                break;
            case 1:
                holder.binding.etValue.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.binding.tvCompany.setText(SpUtil.getInstance().getCoinUnit());
                holder.binding.tvCompany.setVisibility(View.VISIBLE);
                holder.binding.etValue.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.binding.tvCompany.setText(SpUtil.getInstance().getCoinUnit() + "/分钟");
                holder.binding.tvCompany.setVisibility(View.VISIBLE);
                holder.binding.etValue.setVisibility(View.VISIBLE);
                break;
            case 4:

                break;
            default:
                break;
        }

        holder.binding.etValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString().trim();
            }
        });

        holder.binding.ivChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    if (itemPosition != position){
                        itemPosition = position;
                        listener.onChoice(mList.get(position), holder.binding.etValue.getText().toString().trim().isEmpty() ? "" : holder.binding.etValue.getText().toString().trim(), position);
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRoomModeLayoutBinding binding;

        public ViewHolder(@NonNull ItemRoomModeLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setListener(itemListener listener) {
        this.listener = listener;
    }

    public interface itemListener {
        void onChoice(LiveRoomType bean, String content, int position);
    }

}
