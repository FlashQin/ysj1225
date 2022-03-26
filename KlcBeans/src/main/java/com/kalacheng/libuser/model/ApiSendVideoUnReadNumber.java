package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户评论未读数消息
 */
public class ApiSendVideoUnReadNumber  implements Parcelable
{
 public ApiSendVideoUnReadNumber()
{
}

/**
 * 消息条数
 */
public int number;

/**
 * 状态1成功2失败
 */
public int code;

/**
 * 状态描述
 */
public String msg;

   public ApiSendVideoUnReadNumber(Parcel in) 
{
number=in.readInt();
code=in.readInt();
msg=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(number);
dest.writeInt(code);
dest.writeString(msg);

}

  public static void cloneObj(ApiSendVideoUnReadNumber source,ApiSendVideoUnReadNumber target)
{

target.number=source.number;

target.code=source.code;

target.msg=source.msg;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiSendVideoUnReadNumber> CREATOR = new Creator<ApiSendVideoUnReadNumber>() {
        @Override
        public ApiSendVideoUnReadNumber createFromParcel(Parcel in) {
            return new ApiSendVideoUnReadNumber(in);
        }

        @Override
        public ApiSendVideoUnReadNumber[] newArray(int size) {
            return new ApiSendVideoUnReadNumber[size];
        }
    };
}
