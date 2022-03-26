package com.kalacheng.template.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 测试Model
 */
public class aTestModle  implements Parcelable
{
 public aTestModle()
{
}

/**
 * null
 */
public String name;

/**
 * null
 */
public long id;

/**
 * null
 */
public long pid;

/**
 * null
 */
public String nickname;

/**
 * null
 */
public Date createTime;

/**
 * null
 */
public double moneyxx;

/**
 * null
 */
public double moneysdfsdf;

   public aTestModle(Parcel in) 
{
name=in.readString();
id=in.readLong();
pid=in.readLong();
nickname=in.readString();
createTime=new Date( in.readLong());
moneyxx=in.readDouble();
moneysdfsdf=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(name);
dest.writeLong(id);
dest.writeLong(pid);
dest.writeString(nickname);
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeDouble(moneyxx);
dest.writeDouble(moneysdfsdf);

}

  public static void cloneObj(aTestModle source,aTestModle target)
{

target.name=source.name;

target.id=source.id;

target.pid=source.pid;

target.nickname=source.nickname;

target.createTime=source.createTime;

target.moneyxx=source.moneyxx;

target.moneysdfsdf=source.moneysdfsdf;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<aTestModle> CREATOR = new Creator<aTestModle>() {
        @Override
        public aTestModle createFromParcel(Parcel in) {
            return new aTestModle(in);
        }

        @Override
        public aTestModle[] newArray(int size) {
            return new aTestModle[size];
        }
    };
}
