package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 物流轨迹
 */
public class TraceBean  implements Parcelable
{
 public TraceBean()
{
}

/**
 * 时间
 */
public String time;

/**
 * 状态
 */
public String status;

   public TraceBean(Parcel in) 
{
time=in.readString();
status=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(time);
dest.writeString(status);

}

  public static void cloneObj(TraceBean source,TraceBean target)
{

target.time=source.time;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TraceBean> CREATOR = new Creator<TraceBean>() {
        @Override
        public TraceBean createFromParcel(Parcel in) {
            return new TraceBean(in);
        }

        @Override
        public TraceBean[] newArray(int size) {
            return new TraceBean[size];
        }
    };
}
