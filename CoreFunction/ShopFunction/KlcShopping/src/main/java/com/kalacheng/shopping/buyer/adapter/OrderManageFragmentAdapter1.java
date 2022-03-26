package com.kalacheng.shopping.buyer.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kalacheng.shopping.buyer.fragment.OrderManageListFragment1;

import java.util.ArrayList;
import java.util.List;

public class OrderManageFragmentAdapter1 extends FragmentStateAdapter {

    List<OrderManageListFragment1> list;

    public OrderManageFragmentAdapter1(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        list = new ArrayList<>();
        list.add(new OrderManageListFragment1(-1, 0));
        list.add(new OrderManageListFragment1(-1, 1));
        list.add(new OrderManageListFragment1(-1, 2));
        list.add(new OrderManageListFragment1(-1, 3));
        list.add(new OrderManageListFragment1(0, -1));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        int status = position;
//        if (position == 1){
//            status = 2;
//        }
//        if (position == 2){
//            status = 1;
//        }
//        list.add(new GoodsManageListFragment(status));
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public OrderManageListFragment1 getItem(int index) {
        return list.get(index);
    }

    public List<OrderManageListFragment1> getList() {
        return list;
    }
}
