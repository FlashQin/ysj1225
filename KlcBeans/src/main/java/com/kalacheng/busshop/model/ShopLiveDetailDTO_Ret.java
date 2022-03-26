package com.kalacheng.busshop.model;

import com.kalacheng.base.http.HttpRet;





public class ShopLiveDetailDTO_Ret implements HttpRet
{
    public int code;
    public String msg;
    public ShopLiveDetailDTO retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
