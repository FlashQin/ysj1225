package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 键值对数据
 */
public class KeyValueDto  implements Parcelable
{
 public KeyValueDto()
{
}

/**
 * 默认是否进入到此选项页面 0： 否  1：是
 */
public int isDefault;

/**
 * 补充值2
 */
public String value2;

/**
 * 补充值1
 */
public String value1;

/**
 * 值
 */
public String value;

/**
 * 键
 */
public String key;

   public KeyValueDto(Parcel in) 
{
isDefault=in.readInt();
value2=in.readString();
value1=in.readString();
value=in.readString();
key=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isDefault);
dest.writeString(value2);
dest.writeString(value1);
dest.writeString(value);
dest.writeString(key);

}

  public static void cloneObj(KeyValueDto source,KeyValueDto target)
{

target.isDefault=source.isDefault;

target.value2=source.value2;

target.value1=source.value1;

target.value=source.value;

target.key=source.key;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KeyValueDto> CREATOR = new Creator<KeyValueDto>() {
        @Override
        public KeyValueDto createFromParcel(Parcel in) {
            return new KeyValueDto(in);
        }

        @Override
        public KeyValueDto[] newArray(int size) {
            return new KeyValueDto[size];
        }
    };
}
