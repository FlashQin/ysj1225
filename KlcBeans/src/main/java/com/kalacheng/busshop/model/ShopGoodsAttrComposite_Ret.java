package com.kalacheng.busshop.model;

import com.kalacheng.base.http.HttpRet;





public class ShopGoodsAttrComposite_Ret implements HttpRet
{
    public int code;
    public String msg;
    public ShopGoodsAttrComposite retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
