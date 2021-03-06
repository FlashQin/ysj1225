package com.kalacheng.tiui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.tiui.adapter.TiGiftAdapter;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cn.tillusory.sdk.TiSDK;
import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.TiGift;
import com.kalacheng.tiui.R;

/**
 * Created by Anko on 2018/12/1.
 * Copyright (c) 2018-2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public class TiGiftFragment extends LazyFragment implements Observer {

    private List<TiGift> giftList = new ArrayList<>();
    private TiSDKManager tiSDKManager;
    private TiGiftAdapter tiGiftAdapter;

    public TiGiftFragment setTiSDKManager(TiSDKManager tiSDKManager) {
        this.tiSDKManager = tiSDKManager;
        TiSDK.addObserver(this);
        return this;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ti_sticker);

        if (getContext() == null) return;

        giftList.clear();
        giftList.add(TiGift.NO_GIFT);
        giftList.addAll(TiGift.getAllGifts(getContext()));

        RecyclerView tiGiftRV = (RecyclerView) findViewById(R.id.tiRecyclerView);
        tiGiftAdapter = new TiGiftAdapter(giftList, tiSDKManager);
        tiGiftRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        tiGiftRV.setAdapter(tiGiftAdapter);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (getContext() == null) return;

        giftList.clear();
        giftList.add(TiGift.NO_GIFT);
        giftList.addAll(TiGift.getAllGifts(getContext()));

        if (tiGiftAdapter != null)
            tiGiftAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();

        TiSDK.deleteObserver(this);
    }
}
