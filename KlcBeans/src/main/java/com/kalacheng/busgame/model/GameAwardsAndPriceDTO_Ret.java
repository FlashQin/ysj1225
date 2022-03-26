package com.kalacheng.busgame.model;

import com.kalacheng.base.http.HttpRet;





public class GameAwardsAndPriceDTO_Ret implements HttpRet
{
    public int code;
    public String msg;
    public GameAwardsAndPriceDTO retObj;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getObj() {
        return retObj;
    }
}
