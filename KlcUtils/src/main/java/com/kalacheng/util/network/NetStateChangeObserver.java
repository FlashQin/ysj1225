package com.kalacheng.util.network;

public interface NetStateChangeObserver {
    void onNetDisconnected();

    void onNetConnected(NetworkType networkType);
}
