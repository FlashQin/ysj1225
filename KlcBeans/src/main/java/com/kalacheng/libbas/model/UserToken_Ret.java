package com.kalacheng.libbas.model;

import com.kalacheng.base.http.HttpRet;





public class UserToken_Ret implements HttpRet
{
    public int code;
    public String msg;
    public UserToken retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
