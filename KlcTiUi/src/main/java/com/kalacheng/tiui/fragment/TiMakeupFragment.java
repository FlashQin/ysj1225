package com.kalacheng.tiui.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.tiui.adapter.TiMakeupAdapter;
import com.kalacheng.tiui.model.TiMakeup;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kalacheng.tiui.R;

/**
 * Created by Anko on 2019-09-05.
 * Copyright (c) 2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiMakeupFragment extends LazyFragment {

    private List<TiMakeup> makeupList = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_recyclerview);

        makeupList.clear();
        makeupList.addAll(Arrays.asList(TiMakeup.values()));

        RecyclerView tiMakeupRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiMakeupAdapter makeupAdapter = new TiMakeupAdapter(makeupList);
        tiMakeupRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false));
        tiMakeupRV.setAdapter(makeupAdapter);
    }
}
