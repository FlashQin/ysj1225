package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户下单信息
 */
public class UserBuyDTO  implements Parcelable
{
 public UserBuyDTO()
{
}

/**
 * null
 */
public long serialVersionUID;

/**
 * 用户头像
 */
public String avatarThumb;

/**
 * 用户名
 */
public String username;

/**
 * 商品名称
 */
public String goodsName;

   public UserBuyDTO(Parcel in) 
{
serialVersionUID=in.readLong();
avatarThumb=in.readString();
username=in.readString();
goodsName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(serialVersionUID);
dest.writeString(avatarThumb);
dest.writeString(username);
dest.writeString(goodsName);

}

  public static void cloneObj(UserBuyDTO source,UserBuyDTO target)
{

target.serialVersionUID=source.serialVersionUID;

target.avatarThumb=source.avatarThumb;

target.username=source.username;

target.goodsName=source.goodsName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserBuyDTO> CREATOR = new Creator<UserBuyDTO>() {
        @Override
        public UserBuyDTO createFromParcel(Parcel in) {
            return new UserBuyDTO(in);
        }

        @Override
        public UserBuyDTO[] newArray(int size) {
            return new UserBuyDTO[size];
        }
    };
}
