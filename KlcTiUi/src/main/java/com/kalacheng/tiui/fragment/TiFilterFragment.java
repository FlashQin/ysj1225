package com.kalacheng.tiui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.tiui.adapter.TiFilterAdapter;
import com.kalacheng.tiui.model.TiFilter;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kalacheng.tiui.R;

/**
 * Created by Anko on 2018/12/1.
 * Copyright (c) 2018-2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiFilterFragment extends LazyFragment {

    private List<TiFilter> filterList = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_recyclerview);

        filterList.clear();
        filterList.addAll(Arrays.asList(TiFilter.values()));

        RecyclerView tiFilterRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiFilterAdapter filterAdapter = new TiFilterAdapter(filterList);
        tiFilterRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tiFilterRV.setAdapter(filterAdapter);
    }

}
