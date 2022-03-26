package com.kalacheng.util.utils.city_select;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.R;
import com.kalacheng.util.adapter.SimpleGreyStorkeAdapter;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.bean.SimpleTextBean;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.view.ItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<List<CityBean>> list = null;
    private ItemDecoration itemDecoration;

    public CityAdapter(Context context, List<List<CityBean>> list) {
        this.mContext = context;
        this.list = list;
        itemDecoration = new ItemDecoration(mContext, 0, 10, 10);
    }

    public void setCityAdapter(List<List<CityBean>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 基于项不同的类型来获得不同的viewholder,关联对应的布局
     *
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DetailsHolder(inflate(parent, R.layout.simple_text_recycleview));
    }

    /**
     * 关联布局
     */
    private View inflate(ViewGroup parent, int resId) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            List<SimpleTextBean> simpleTextBeans = new ArrayList<>();
            for (CityBean cityBean : list.get(position)) {
                simpleTextBeans.add(new SimpleTextBean(cityBean.name));
            }
            SimpleGreyStorkeAdapter adapter = new SimpleGreyStorkeAdapter();
            adapter.refreshData(simpleTextBeans);
            ((DetailsHolder) holder).tvCatagory.setText("热门城市");
            ((DetailsHolder) holder).tvCatagory.setTextColor(Color.parseColor("#aaaaaa"));
            ((DetailsHolder) holder).recyclerList.setLayoutManager(new GridLayoutManager(mContext, 4));
            ((DetailsHolder) holder).recyclerList.setAdapter(adapter);
            ((DetailsHolder) holder).recyclerList.addItemDecoration(itemDecoration);
            ((DetailsHolder) holder).recyclerList.setPadding(DpUtil.dp2px(12), 0, 0, 0);
            adapter.setOnItemClickListener(new OnBeanCallback<SimpleTextBean>() {
                @Override
                public void onClick(SimpleTextBean bean) {
                    listener.cityName(bean.name);
                }
            });
        } else {
            CityItemAdapter adapter = new CityItemAdapter(list.get(position));
            ((DetailsHolder) holder).tvCatagory.setText(list.get(position).get(0).sortLetters);
            ((DetailsHolder) holder).tvCatagory.setTextColor(Color.parseColor("#666666"));
            ((DetailsHolder) holder).recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
            ((DetailsHolder) holder).recyclerList.setAdapter(adapter);
            ((DetailsHolder) holder).recyclerList.removeItemDecoration(itemDecoration);
            ((DetailsHolder) holder).recyclerList.setPadding(0, 0, 0, 0);
            adapter.setOnItemClickListener(new OnBeanCallback<CityBean>() {
                @Override
                public void onClick(CityBean bean) {
                    listener.cityName(bean.name);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DetailsHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerList;
        public TextView tvCatagory;

        public DetailsHolder(View itemView) {
            super(itemView);
            recyclerList = itemView.findViewById(R.id.recycler_list);
            tvCatagory = itemView.findViewById(R.id.tv_catagory);
        }

    }

    private MoveToZeroListener listener;

    public void setListener(MoveToZeroListener listener) {
        this.listener = listener;
    }

    /**
     * 将城市名回调给activity
     */
    public interface MoveToZeroListener {

        void cityName(String cityName);

    }
}

