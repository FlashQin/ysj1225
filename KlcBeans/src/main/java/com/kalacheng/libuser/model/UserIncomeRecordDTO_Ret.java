package com.kalacheng.libuser.model;

import com.kalacheng.base.http.HttpRet;





public class UserIncomeRecordDTO_Ret implements HttpRet
{
    public int code;
    public String msg;
    public UserIncomeRecordDTO retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
