package com.kalacheng.libuser.model;

import com.kalacheng.base.http.HttpRet;





public class AppUserPrivilegeDTO_Ret implements HttpRet
{
    public int code;
    public String msg;
    public AppUserPrivilegeDTO retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
