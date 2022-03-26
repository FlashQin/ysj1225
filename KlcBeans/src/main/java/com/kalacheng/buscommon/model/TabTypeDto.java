package com.kalacheng.buscommon.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.buscommon.model.*;




/**
 * 标签类型
 */
public class TabTypeDto  implements Parcelable
{
 public TabTypeDto()
{
}

/**
 * tab类型名称
 */
public String name;

/**
 * 兴趣标签列表
 */
public List<com.kalacheng.buscommon.model.TabInfoDto> tabInfoList;

/**
 * null
 */
public long id;

/**
 * 排序
 */
public int sort;

/**
 * 标签描述
 */
public String desr;

   public TabTypeDto(Parcel in) 
{
name=in.readString();

if(tabInfoList==null){
tabInfoList=  new ArrayList<>();
 }
in.readTypedList(tabInfoList,com.kalacheng.buscommon.model.TabInfoDto.CREATOR);
id=in.readLong();
sort=in.readInt();
desr=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(name);

dest.writeTypedList(tabInfoList);
dest.writeLong(id);
dest.writeInt(sort);
dest.writeString(desr);

}

  public static void cloneObj(TabTypeDto source,TabTypeDto target)
{

target.name=source.name;

        if(source.tabInfoList==null)
        {
            target.tabInfoList=null;
        }else
        {
            target.tabInfoList=new ArrayList();
            for(int i=0;i<source.tabInfoList.size();i++)
            {
            TabInfoDto.cloneObj(source.tabInfoList.get(i),target.tabInfoList.get(i));
            }
        }


target.id=source.id;

target.sort=source.sort;

target.desr=source.desr;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TabTypeDto> CREATOR = new Creator<TabTypeDto>() {
        @Override
        public TabTypeDto createFromParcel(Parcel in) {
            return new TabTypeDto(in);
        }

        @Override
        public TabTypeDto[] newArray(int size) {
            return new TabTypeDto[size];
        }
    };
}
