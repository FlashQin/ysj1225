package com.kalacheng.buscommon;

import com.kalacheng.base.http.HttpRet;





public class AppHomeHallDTO_Ret implements HttpRet
{
    public int code;
    public String msg;
    public AppHomeHallDTO retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
