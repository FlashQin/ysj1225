package com.kalacheng.shopping.seller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kalacheng.shopping.seller.fragment.OrderManageListFragment2;

import java.util.ArrayList;
import java.util.List;

public class OrderManageFragmentAdapter2 extends FragmentStateAdapter {

    List<OrderManageListFragment2> list;

    public OrderManageFragmentAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        list = new ArrayList<>();
        list.add(new OrderManageListFragment2(-1, 0));
        list.add(new OrderManageListFragment2(-1, 1));
        list.add(new OrderManageListFragment2(-1, 2));
        list.add(new OrderManageListFragment2(-1, 3));
        list.add(new OrderManageListFragment2(0, -1));
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

    public OrderManageListFragment2 getItem(int index) {
        return list.get(index);
    }

    public List<OrderManageListFragment2> getList() {
        return list;
    }
}
