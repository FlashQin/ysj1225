package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * null
 */
public class ApiBaseEntity  implements Parcelable
{
 public ApiBaseEntity()
{
}

/**
 * 状态描述
 */
public String msg;

/**
 * 状态1成功2失败
 */
public int code;

   public ApiBaseEntity(Parcel in) 
{
msg=in.readString();
code=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(msg);
dest.writeInt(code);

}

  public static void cloneObj(ApiBaseEntity source,ApiBaseEntity target)
{

target.msg=source.msg;

target.code=source.code;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiBaseEntity> CREATOR = new Creator<ApiBaseEntity>() {
        @Override
        public ApiBaseEntity createFromParcel(Parcel in) {
            return new ApiBaseEntity(in);
        }

        @Override
        public ApiBaseEntity[] newArray(int size) {
            return new ApiBaseEntity[size];
        }
    };
}
