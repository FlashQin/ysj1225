package com.kalacheng.busgame.model;

import java.util.List;
import com.kalacheng.base.http.HttpRetArr;
import com.kalacheng.base.http.PageInfo;





public class GameUserPrizeDTO_RetArr  implements HttpRetArr
{
    public int code;
    public String msg;
    public List<GameUserPrizeDTO> retArr;    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getRetArr() {
        return retArr;
    }

    public PageInfo getPageInfo()
    {
     return null;
    }
}
