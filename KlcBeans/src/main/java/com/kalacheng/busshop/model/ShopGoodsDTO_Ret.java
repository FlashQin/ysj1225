package com.kalacheng.busshop.model;

import com.kalacheng.base.http.HttpRet;





public class ShopGoodsDTO_Ret implements HttpRet
{
    public int code;
    public String msg;
    public ShopGoodsDTO retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
