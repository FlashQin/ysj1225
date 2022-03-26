package com.kalacheng.shopping.buyer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.shopping.R;
import com.kalacheng.util.bean.AddressBean;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ReadAssetsJsonUtil;

import java.util.ArrayList;
import java.util.List;

public class CityStringAdapter extends RecyclerView.Adapter<CityStringAdapter.Holder> {

    int level = 0;
    ArrayList<AddressBean> list;
    private List<AddressBean.CitiesBean> cities;
    private List<AddressBean.CitiesBean.CountiesBean> counties;

    OnItemClickListenes onItemClickListenes;

    public CityStringAdapter(ArrayList<AddressBean> addressBeans) {
        if (addressBeans == null || addressBeans.size() < 1) {
            list = new ArrayList<>();
            list.addAll(ReadAssetsJsonUtil.getAddressBeans());
            cities = new ArrayList<>();
            counties = new ArrayList<>();
        } else {
            list = new ArrayList<>();
            list.addAll(addressBeans);
            cities = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isChecked()) {
                    cities.clear();
                    cities.addAll(list.get(i).getCities());
                }
            }
            counties = new ArrayList<>();
            for (int i = 0; i < cities.size(); i++) {
                if (cities.get(i).isChecked()) {
                    counties.clear();
                    counties.addAll(cities.get(i).getCounties());
                }
            }
            level = 2;

        }

    }

    public List<AddressBean> getList() {
        return list;
    }

    public void setOnItemClickListenes(OnItemClickListenes onItemClickListenes) {
        this.onItemClickListenes = onItemClickListenes;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String name = "";
        boolean isChecked = false;
        if (level == 2 || level == 3) {
            name = counties.get(position).getAreaName();
            isChecked = counties.get(position).isChecked();
        } else if (level == 1) {
            name = cities.get(position).getAreaName();
            isChecked = cities.get(position).isChecked();
        } else {
            name = list.get(position).getAreaName();
            isChecked = list.get(position).isChecked();
        }
        holder.textView.setText(name);
        holder.textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.line_bottom, isChecked ? R.mipmap.icon_gouxuan : 0, 0);
    }

    @Override
    public int getItemCount() {
        if (level == 2 || level == 3) {
            return counties.size();
        } else if (level == 1) {
            return cities.size();
        } else {
            return list.size();
        }
    }

    public void setLevel(int level) {
        this.level = level;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick())return;
                    if (level == 2) {
                        if (!counties.get(getBindingAdapterPosition()).isChecked()) {
                            for (int i = 0; i < counties.size(); i++) {
                                counties.get(i).setChecked(i == getBindingAdapterPosition());
                                if (i == getBindingAdapterPosition()) {
                                    level = 3;
                                }
                            }
                        }
                    } else if (level == 1) {
                        if (!cities.get(getBindingAdapterPosition()).isChecked()) {
                            for (int i = 0; i < cities.size(); i++) {
                                cities.get(i).setChecked(i == getBindingAdapterPosition());
                                if (i == getBindingAdapterPosition()) {
                                    level = 2;
                                    counties.clear();
                                    counties.addAll(cities.get(i).getCounties());
                                }
                            }
                        }

                    } else {
                        if (!list.get(getBindingAdapterPosition()).isChecked()) {
                            for (int i = 0; i < list.size(); i++) {
                                list.get(i).setChecked(i == getBindingAdapterPosition());
                                if (i == getBindingAdapterPosition()) {
                                    level = 1;
                                    cities.clear();
                                    cities.addAll(list.get(i).getCities());
                                }

                            }
                        }
                    }
                    notifyDataSetChanged();

                    if (onItemClickListenes != null) {
                        onItemClickListenes.onItemClickListenes(level);
                    }
                }
            });

        }
    }

    public interface OnItemClickListenes {
        void onItemClickListenes(int level);
    }


}
