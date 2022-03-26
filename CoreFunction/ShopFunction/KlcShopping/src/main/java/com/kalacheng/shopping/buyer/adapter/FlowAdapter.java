package com.kalacheng.shopping.buyer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kalacheng.busshop.model.ShopAttrValue;
import com.kalacheng.shopping.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

public class FlowAdapter extends TagAdapter<ShopAttrValue> {



    public FlowAdapter(List datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, ShopAttrValue value) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tag_text,parent,false);
        textView.setText(value.name);
        return textView;
    }

    @Override
    public void onSelected(int position, View view) {
        super.onSelected(position, view);
        TextView textView = (TextView) view;
        textView.setText(getItem(position).name);
        textView.setBackgroundResource(R.drawable.bg_attribure_value);
        textView.setTextColor(0xFFFF5500);
    }

    @Override
    public void unSelected(int position, View view) {
        super.unSelected(position, view);
        TextView textView = (TextView) view;
        textView.setText(getItem(position).name);
        textView.setBackgroundResource(R.drawable.bg_attribure_value_add);
        textView.setTextColor(0xFF666666);
    }
}
