package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 标签详情
 */
public class TabInfoDto  implements Parcelable
{
 public TabInfoDto()
{
}

/**
 * 标签类型ID
 */
public long tabTypeId;

/**
 * tab名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 排序
 */
public int sort;

/**
 * tab描述
 */
public String desr;

/**
 * tab字体颜色
 */
public String fontColor;

/**
 * 兴趣标签选中状态： 0：未选中  1：已选中
 */
public int status;

   public TabInfoDto(Parcel in) 
{
tabTypeId=in.readLong();
name=in.readString();
id=in.readLong();
sort=in.readInt();
desr=in.readString();
fontColor=in.readString();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(tabTypeId);
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(sort);
dest.writeString(desr);
dest.writeString(fontColor);
dest.writeInt(status);

}

  public static void cloneObj(TabInfoDto source,TabInfoDto target)
{

target.tabTypeId=source.tabTypeId;

target.name=source.name;

target.id=source.id;

target.sort=source.sort;

target.desr=source.desr;

target.fontColor=source.fontColor;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TabInfoDto> CREATOR = new Creator<TabInfoDto>() {
        @Override
        public TabInfoDto createFromParcel(Parcel in) {
            return new TabInfoDto(in);
        }

        @Override
        public TabInfoDto[] newArray(int size) {
            return new TabInfoDto[size];
        }
    };
}
