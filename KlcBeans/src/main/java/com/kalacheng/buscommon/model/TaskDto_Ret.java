package com.kalacheng.buscommon.model;

import com.kalacheng.base.http.HttpRet;





public class TaskDto_Ret implements HttpRet
{
    public int code;
    public String msg;
    public TaskDto retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
