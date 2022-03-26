package com.kalacheng.base.listener;

public interface MsgListener {
    interface NullMsgListener {
        void onMsg();
    }

    interface SimpleMsgListener<T> {
        void onMsg(T t);

        void onTagMsg(String tag, T t);
    }


}
