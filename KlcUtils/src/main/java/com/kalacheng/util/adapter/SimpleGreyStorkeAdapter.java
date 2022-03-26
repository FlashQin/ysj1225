package com.kalacheng.util.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.R;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.bean.SimpleTextBean;

import java.util.ArrayList;
import java.util.List;

public class SimpleGreyStorkeAdapter extends RecyclerView.Adapter {
    OnBeanCallback<SimpleTextBean> onBeanCallback;
    Context mContext;
    private List<SimpleTextBean> mList = new ArrayList<>();

    public SimpleGreyStorkeAdapter() {

    }

    public void addData(List<SimpleTextBean> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    public void refreshData(List<SimpleTextBean> data) {
        this.mList.clear();
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    public List<SimpleTextBean> getData() {
        return mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_text_greystroke, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, final int position) {
        ((CityViewHolder) vh).tvName.setText(mList.get(position).name);
        if (!TextUtils.isEmpty(mList.get(position).name) && mList.get(position).name.length() > 5) {
            ((CityViewHolder) vh).tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        } else {
            ((CityViewHolder) vh).tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        }
        if (mList.get(position).isChecked) {
            ((CityViewHolder) vh).tvName.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_city_selected));
        } else {
            ((CityViewHolder) vh).tvName.setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_city_normal));
        }
        ((CityViewHolder) vh).tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBeanCallback.onClick(mList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class CityViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

    public void setOnItemClickListener(OnBeanCallback<SimpleTextBean> onBeanCallback) {
        this.onBeanCallback = onBeanCallback;
    }
}
