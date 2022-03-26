package com.kalacheng.template.model;

import com.kalacheng.base.http.HttpRet;





public class aTestModle_Ret implements HttpRet
{
    public int code;
    public String msg;
    public aTestModle retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
