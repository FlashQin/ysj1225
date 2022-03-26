package com.kalacheng.util.utils.city_select;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.R;
import com.kalacheng.util.listener.OnBeanCallback;

import java.util.ArrayList;
import java.util.List;

public class CityItemAdapter extends RecyclerView.Adapter {
    OnBeanCallback<CityBean> onBeanCallback;

    private List<CityBean> mList = new ArrayList<>();

    public CityItemAdapter(List<CityBean> list) {
        mList = list;
    }

    public void setData(List<CityBean> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sort_city, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
        ((CityViewHolder) vh).setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class CityViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_city_name);
        }

        void setData(final CityBean bean) {
            tvName.setText(bean.name);
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBeanCallback.onClick(bean);
                }
            });
        }
    }

    public void setOnItemClickListener(OnBeanCallback<CityBean> onBeanCallback) {
        this.onBeanCallback = onBeanCallback;
    }
}
