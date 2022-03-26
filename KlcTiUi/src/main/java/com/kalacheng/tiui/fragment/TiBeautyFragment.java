package com.kalacheng.tiui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.tiui.model.TiBeauty;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kalacheng.tiui.R;
import com.kalacheng.tiui.adapter.TiBeautyAdapter;

/**
 * Created by Anko on 2018/12/1.
 * Copyright (c) 2018-2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiBeautyFragment extends LazyFragment {

    private List<TiBeauty> beautyList = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_recyclerview);

        beautyList.clear();
        beautyList.addAll(Arrays.asList(TiBeauty.values()));

        RecyclerView tiBeautyRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiBeautyAdapter beautyAdapter = new TiBeautyAdapter(beautyList);
        tiBeautyRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tiBeautyRV.setAdapter(beautyAdapter);
    }

}
