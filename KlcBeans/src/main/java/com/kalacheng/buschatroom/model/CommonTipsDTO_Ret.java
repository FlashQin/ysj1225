package com.kalacheng.buschatroom.model;

import com.kalacheng.base.http.HttpRet;





public class CommonTipsDTO_Ret implements HttpRet
{
    public int code;
    public String msg;
    public CommonTipsDTO retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
