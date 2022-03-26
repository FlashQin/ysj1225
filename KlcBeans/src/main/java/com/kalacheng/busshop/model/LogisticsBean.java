package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 物流信息
 */
public class LogisticsBean  implements Parcelable
{
 public LogisticsBean()
{
}

/**
 * null
 */
public String msg;

/**
 * 快递公司名称
 */
public String expName;

/**
 * 发货到收货消耗时长 (截止最新轨迹)
 */
public String takeTime;

/**
 * 快递公司电话
 */
public String expPhone;

/**
 * 快递公司官网
 */
public String expSite;

/**
 * 快递轨迹信息最新时间
 */
public String updateTime;

/**
 * 快递物流信息
 */
public List<com.kalacheng.busshop.model.TraceBean> list;

/**
 * 快递公司简称
 */
public String type;

/**
 * 1.签收 0.未签收
 */
public String issign;

/**
 * 单号
 */
public String number;

/**
 * 快递员 或 快递站(没有则为空)
 */
public String courier;

/**
 * 0：快递收件(揽件)1.在途中 2.正在派件 3.已签收 4.派送失败 5.疑难件 6.退件签收
 */
public String deliverystatus;

/**
 * 快递员电话 (没有则为空)
 */
public String courierPhone;

/**
 * 快递公司LOGO
 */
public String logo;

/**
 * 快递状态 0:正常查询 201:快递单号错误 203:快递公司不存在 204:快递公司识别失败 205:没有信息 207:该单号被限制，错误单号
 */
public String status;

   public LogisticsBean(Parcel in) 
{
msg=in.readString();
expName=in.readString();
takeTime=in.readString();
expPhone=in.readString();
expSite=in.readString();
updateTime=in.readString();

if(list==null){
list=  new ArrayList<>();
 }
in.readTypedList(list,com.kalacheng.busshop.model.TraceBean.CREATOR);
type=in.readString();
issign=in.readString();
number=in.readString();
courier=in.readString();
deliverystatus=in.readString();
courierPhone=in.readString();
logo=in.readString();
status=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(msg);
dest.writeString(expName);
dest.writeString(takeTime);
dest.writeString(expPhone);
dest.writeString(expSite);
dest.writeString(updateTime);

dest.writeTypedList(list);
dest.writeString(type);
dest.writeString(issign);
dest.writeString(number);
dest.writeString(courier);
dest.writeString(deliverystatus);
dest.writeString(courierPhone);
dest.writeString(logo);
dest.writeString(status);

}

  public static void cloneObj(LogisticsBean source,LogisticsBean target)
{

target.msg=source.msg;

target.expName=source.expName;

target.takeTime=source.takeTime;

target.expPhone=source.expPhone;

target.expSite=source.expSite;

target.updateTime=source.updateTime;

        if(source.list==null)
        {
            target.list=null;
        }else
        {
            target.list=new ArrayList();
            for(int i=0;i<source.list.size();i++)
            {
            TraceBean.cloneObj(source.list.get(i),target.list.get(i));
            }
        }


target.type=source.type;

target.issign=source.issign;

target.number=source.number;

target.courier=source.courier;

target.deliverystatus=source.deliverystatus;

target.courierPhone=source.courierPhone;

target.logo=source.logo;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LogisticsBean> CREATOR = new Creator<LogisticsBean>() {
        @Override
        public LogisticsBean createFromParcel(Parcel in) {
            return new LogisticsBean(in);
        }

        @Override
        public LogisticsBean[] newArray(int size) {
            return new LogisticsBean[size];
        }
    };
}
