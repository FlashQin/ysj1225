package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP守护表
 */
public class ApiGuard  implements Parcelable
{
 public ApiGuard()
{
}

/**
 * 提示消息
 */
public String msg;

/**
 * 时长
 */
public int lengthTime;

/**
 * 时长类型，0天，1月，2年
 */
public int lengthType;

/**
 * 序号
 */
public int orderno;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 时长
 */
public int length;

/**
 * 守护名称
 */
public String name;

/**
 * 守护功能
 */
public List<com.kalacheng.libuser.model.GuardEffect> guardEffectList;

/**
 * 守护类型，1普通2尊贵
 */
public int type;

/**
 * null
 */
public long tid;

/**
 * 守护价格
 */
public double coin;

/**
 * 结束时间
 */
public Date uptime;

   public ApiGuard(Parcel in) 
{
msg=in.readString();
lengthTime=in.readInt();
lengthType=in.readInt();
orderno=in.readInt();
addtime=new Date( in.readLong());
length=in.readInt();
name=in.readString();

if(guardEffectList==null){
guardEffectList=  new ArrayList<>();
 }
in.readTypedList(guardEffectList,com.kalacheng.libuser.model.GuardEffect.CREATOR);
type=in.readInt();
tid=in.readLong();
coin=in.readDouble();
uptime=new Date( in.readLong());

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(msg);
dest.writeInt(lengthTime);
dest.writeInt(lengthType);
dest.writeInt(orderno);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeInt(length);
dest.writeString(name);

dest.writeTypedList(guardEffectList);
dest.writeInt(type);
dest.writeLong(tid);
dest.writeDouble(coin);
dest.writeLong(uptime==null?0:uptime.getTime());

}

  public static void cloneObj(ApiGuard source,ApiGuard target)
{

target.msg=source.msg;

target.lengthTime=source.lengthTime;

target.lengthType=source.lengthType;

target.orderno=source.orderno;

target.addtime=source.addtime;

target.length=source.length;

target.name=source.name;

        if(source.guardEffectList==null)
        {
            target.guardEffectList=null;
        }else
        {
            target.guardEffectList=new ArrayList();
            for(int i=0;i<source.guardEffectList.size();i++)
            {
            GuardEffect.cloneObj(source.guardEffectList.get(i),target.guardEffectList.get(i));
            }
        }


target.type=source.type;

target.tid=source.tid;

target.coin=source.coin;

target.uptime=source.uptime;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiGuard> CREATOR = new Creator<ApiGuard>() {
        @Override
        public ApiGuard createFromParcel(Parcel in) {
            return new ApiGuard(in);
        }

        @Override
        public ApiGuard[] newArray(int size) {
            return new ApiGuard[size];
        }
    };
}
