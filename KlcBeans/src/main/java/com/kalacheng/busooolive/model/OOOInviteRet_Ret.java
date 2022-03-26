package com.kalacheng.busooolive.model;

import com.kalacheng.base.http.HttpRet;





public class OOOInviteRet_Ret implements HttpRet
{
    public int code;
    public String msg;
    public OOOInviteRet retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
