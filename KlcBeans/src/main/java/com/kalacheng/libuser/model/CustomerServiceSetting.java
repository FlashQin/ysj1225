package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 查看收费设置项
 */
public class CustomerServiceSetting  implements Parcelable
{
 public CustomerServiceSetting()
{
}

/**
 * 客服QQ
 */
public String qq;

/**
 * 申诉电话
 */
public String complaintTelephone;

/**
 * 客服电话
 */
public String consumerHotline;

/**
 * 客服微信
 */
public String wechat;

/**
 * null
 */
public long id;

/**
 * 客服微信二维码
 */
public String wechatCode;

   public CustomerServiceSetting(Parcel in) 
{
qq=in.readString();
complaintTelephone=in.readString();
consumerHotline=in.readString();
wechat=in.readString();
id=in.readLong();
wechatCode=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(qq);
dest.writeString(complaintTelephone);
dest.writeString(consumerHotline);
dest.writeString(wechat);
dest.writeLong(id);
dest.writeString(wechatCode);

}

  public static void cloneObj(CustomerServiceSetting source,CustomerServiceSetting target)
{

target.qq=source.qq;

target.complaintTelephone=source.complaintTelephone;

target.consumerHotline=source.consumerHotline;

target.wechat=source.wechat;

target.id=source.id;

target.wechatCode=source.wechatCode;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomerServiceSetting> CREATOR = new Creator<CustomerServiceSetting>() {
        @Override
        public CustomerServiceSetting createFromParcel(Parcel in) {
            return new CustomerServiceSetting(in);
        }

        @Override
        public CustomerServiceSetting[] newArray(int size) {
            return new CustomerServiceSetting[size];
        }
    };
}
