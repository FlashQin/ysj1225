package com.kalacheng.busshop.model;

import com.kalacheng.base.http.HttpRet;





public class ApiShopLogisticsDTO_Ret implements HttpRet
{
    public int code;
    public String msg;
    public ApiShopLogisticsDTO retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
