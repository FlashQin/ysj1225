package com.kalacheng.tiui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.tiui.adapter.TiRockAdapter;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.tillusory.sdk.TiSDKManager;
import com.kalacheng.tiui.R;
import com.kalacheng.tiui.model.TiRock;

/**
 * Created by Anko on 2018/12/1.
 * Copyright (c) 2018-2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiRockFragment extends LazyFragment {

    private List<TiRock> rockList = new ArrayList<>();
    private TiSDKManager tiSDKManager;

    public TiRockFragment setTiSDKManager(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;
        return this;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_recyclerview);

        rockList.clear();
        rockList.addAll(Arrays.asList(TiRock.values()));

        RecyclerView tiRockRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        TiRockAdapter rockAdapter = new TiRockAdapter(rockList, tiSDKManager);
        tiRockRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        tiRockRV.setAdapter(rockAdapter);
    }
}
