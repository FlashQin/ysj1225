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

import java.util.ArrayList;
import java.util.List;

public class CityStringAdapter1 extends RecyclerView.Adapter<CityStringAdapter1.Holder> {

    int level = 0;
    int itemCount = 1;
    List<AddressBean> list;

    int indexA = -1;
    int indexB = -1;
    int indexC = -1;

    OnItemClickListenes onItemClickListenes;
    public CityStringAdapter1() {
        list = new ArrayList<>();
    }

    public void setList(List<AddressBean> list,int level) {
        itemCount = 1;
        indexA = -1;
        indexB = -1;
        indexC = -1;
        this.level = level;
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String name = "";
        if (position == 0) {

            name = indexA != -1 ? list.get(indexA).getAreaName() : "请选择";
            holder.textView.setText(name);
            holder.textView.setTextColor(level == 0 ? 0xFFFF5500:0xFF444444);
            holder.textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, level == 0 ? R.drawable.line_long : 0);

        } else if (position == 1) {
            name = indexB != -1 ? list.get(indexA).getCities().get(indexB).getAreaName() : "请选择";
            holder.textView.setText(name);
            holder.textView.setTextColor(level == 1 ? 0xFFFF5500:0xFF444444);
            holder.textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, level == 1 ? R.drawable.line_long : 0);

        } else if (position == 2) {
            name = indexC != -1 ? list.get(indexA).getCities().get(indexB).getCounties().get(indexC).getAreaName() : "请选择";
            holder.textView.setText(name);
            holder.textView.setTextColor(level == 2 ? 0xFFFF5500:0xFF444444);
            holder.textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, level == 2 ? R.drawable.line_long : 0);
        }

    }

    @Override
    public int getItemCount() {
        itemCount = 1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                itemCount++;
                indexA = i;
                for (int j = 0; j < list.get(i).getCities().size(); j++) {
                    if (list.get(i).getCities().get(j).isChecked()) {
                        itemCount++;
                        indexB = j;
                        for (int k = 0; k < list.get(i).getCities().get(j).getCounties().size(); k++) {
                            if (list.get(i).getCities().get(j).getCounties().get(k).isChecked()) {
                                indexC = k;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return itemCount;
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
                    if (level != getBindingAdapterPosition()){
                        level = getBindingAdapterPosition();
                        notifyDataSetChanged();
                        if (onItemClickListenes != null){
                            onItemClickListenes.onItemClickListenes1(level);
                        }
                    }

                }
            });

        }
    }

    public void setOnItemClickListenes(OnItemClickListenes onItemClickListenes) {
        this.onItemClickListenes = onItemClickListenes;
    }

    public interface OnItemClickListenes{
        void onItemClickListenes1(int level);
    }


}
