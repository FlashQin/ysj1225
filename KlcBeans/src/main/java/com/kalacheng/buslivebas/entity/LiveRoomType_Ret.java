package com.kalacheng.buslivebas.entity;

import com.kalacheng.base.http.HttpRet;





public class LiveRoomType_Ret implements HttpRet
{
    public int code;
    public String msg;
    public LiveRoomType retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
