package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 等级特权
 */
public class AppGradePrivilege  implements Parcelable
{
 public AppGradePrivilege()
{
}

/**
 * 在线logo
 */
public String lineLogo;

/**
 * 是否启用 0：未启用， 1：启用
 */
public int isStart;

/**
 * 排序
 */
public int sort;

/**
 * 特权类型 1:用户特权 2:财富特权 3:主播特权 4:贵族特权
 */
public int type;

/**
 * 用户id
 */
public long userId;

/**
 * 特权描述
 */
public String desr;

/**
 * 不在线logo
 */
public String offLineLogo;

/**
 * 标识
 */
public String identification;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 等级
 */
public int grade;

/**
 * 特权名称
 */
public String name;

/**
 * null
 */
public long id;

/**
 * 0：未拥有 1：已拥有
 */
public int status;

   public AppGradePrivilege(Parcel in) 
{
lineLogo=in.readString();
isStart=in.readInt();
sort=in.readInt();
type=in.readInt();
userId=in.readLong();
desr=in.readString();
offLineLogo=in.readString();
identification=in.readString();
addtime=new Date( in.readLong());
grade=in.readInt();
name=in.readString();
id=in.readLong();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(lineLogo);
dest.writeInt(isStart);
dest.writeInt(sort);
dest.writeInt(type);
dest.writeLong(userId);
dest.writeString(desr);
dest.writeString(offLineLogo);
dest.writeString(identification);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeInt(grade);
dest.writeString(name);
dest.writeLong(id);
dest.writeInt(status);

}

  public static void cloneObj(AppGradePrivilege source,AppGradePrivilege target)
{

target.lineLogo=source.lineLogo;

target.isStart=source.isStart;

target.sort=source.sort;

target.type=source.type;

target.userId=source.userId;

target.desr=source.desr;

target.offLineLogo=source.offLineLogo;

target.identification=source.identification;

target.addtime=source.addtime;

target.grade=source.grade;

target.name=source.name;

target.id=source.id;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppGradePrivilege> CREATOR = new Creator<AppGradePrivilege>() {
        @Override
        public AppGradePrivilege createFromParcel(Parcel in) {
            return new AppGradePrivilege(in);
        }

        @Override
        public AppGradePrivilege[] newArray(int size) {
            return new AppGradePrivilege[size];
        }
    };
}
