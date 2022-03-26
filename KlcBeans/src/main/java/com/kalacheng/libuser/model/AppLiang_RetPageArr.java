package com.kalacheng.libuser.model;

import java.util.List;
import com.kalacheng.base.http.HttpRetArr;
import com.kalacheng.base.http.PageInfo;





public class AppLiang_RetPageArr  implements HttpRetArr
{
    public int code;
    public String msg;
	public int pageSize;
	public int pageIndex;
	public int outTotalPage;
	public int outTotalCount;
    public List<AppLiang> retArr;
    public int getCode() {
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
        PageInfo pageInfo=new PageInfo();
        pageInfo.pageIndex=this.pageIndex;
        pageInfo.pageSize=this.pageSize;
        pageInfo.outTotalPage=this.outTotalPage;
        pageInfo.outTotalCount=this.outTotalCount;
        return pageInfo;
    }
}
