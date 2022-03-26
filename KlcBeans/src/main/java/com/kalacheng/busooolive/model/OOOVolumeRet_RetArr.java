package com.kalacheng.busooolive.model;

import java.util.List;
import com.kalacheng.base.http.HttpRetArr;
import com.kalacheng.base.http.PageInfo;





public class OOOVolumeRet_RetArr  implements HttpRetArr
{
    public int code;
    public String msg;
    public List<OOOVolumeRet> retArr;    public int getCode() {
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
