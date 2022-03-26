package com.kalacheng.shopping.buyer.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kalacheng.shopping.base.SBaseFragment;
import com.kalacheng.shopping.buyer.fragment.ShopGoodsListFragment;
import com.kalacheng.shopping.buyer.fragment.ShopSynopsisFragment;

import java.util.ArrayList;
import java.util.List;

public class ShopFragmentAdapter extends FragmentStateAdapter {

    List<SBaseFragment> list ;

    public ShopFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        list = new ArrayList<>();
        list.add(new ShopGoodsListFragment());
        list.add(new ShopSynopsisFragment());
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

    public SBaseFragment getItem(int index) {
        return list.get(index);
    }

    public List<SBaseFragment> getList() {
        return list;
    }
}
