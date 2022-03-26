package com.kalacheng.shopping.seller.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kalacheng.shopping.seller.fragment.GoodsManageListFragment;

import java.util.ArrayList;
import java.util.List;

public class GoodsManageFragmentAdapter extends FragmentStateAdapter {

    List<GoodsManageListFragment> list ;

    public GoodsManageFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        list = new ArrayList<>();
        list.add(new GoodsManageListFragment(0));
        list.add(new GoodsManageListFragment(2));
        list.add(new GoodsManageListFragment(1));
        list.add(new GoodsManageListFragment(3));
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

    public GoodsManageListFragment getItem(int index) {
        return list.get(index);
    }

    public List<GoodsManageListFragment> getList() {
        return list;
    }
}
