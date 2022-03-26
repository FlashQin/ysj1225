package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 快递节点信息
 */
public class LogisticsNodeDTO  implements Parcelable
{
 public LogisticsNodeDTO()
{
}

/**
 * 快递员 或 快递站(没有则为空)
 */
public String courier;

/**
 * 快递员电话 (没有则为空)
 */
public String courierPhone;

/**
 * 快递时间节点
 */
public Date time;

/**
 * 物流信息
 */
public String content;

/**
 * 快递状态：0：快递收件(揽件)1.在途中 2.正在派件 3.已签收 4.派送失败 5.疑难件 6.退件签收
 */
public String deliveryStatus;

   public LogisticsNodeDTO(Parcel in) 
{
courier=in.readString();
courierPhone=in.readString();
time=new Date( in.readLong());
content=in.readString();
deliveryStatus=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(courier);
dest.writeString(courierPhone);
dest.writeLong(time==null?0:time.getTime());
dest.writeString(content);
dest.writeString(deliveryStatus);

}

  public static void cloneObj(LogisticsNodeDTO source,LogisticsNodeDTO target)
{

target.courier=source.courier;

target.courierPhone=source.courierPhone;

target.time=source.time;

target.content=source.content;

target.deliveryStatus=source.deliveryStatus;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LogisticsNodeDTO> CREATOR = new Creator<LogisticsNodeDTO>() {
        @Override
        public LogisticsNodeDTO createFromParcel(Parcel in) {
            return new LogisticsNodeDTO(in);
        }

        @Override
        public LogisticsNodeDTO[] newArray(int size) {
            return new LogisticsNodeDTO[size];
        }
    };
}
