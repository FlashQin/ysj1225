package com.kalacheng.busooolive.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busooolive.model.*;




/**
 * 音量信息
 */
public class OOOVolumeRet  implements Parcelable
{
 public OOOVolumeRet()
{
}

/**
 * 操作人id
 */
public long operateUid;

/**
 * 操作人音量状态1开启0关播
 */
public int operateStatus;

/**
 * 主持人音量状态1开启0关闭
 */
public int hostStatus;

/**
 * 主持人id
 */
public long hostUid;

/**
 * 消费人id
 */
public long feeUid;

/**
 * 当前房间中已接通的副播集合
 */
public List<com.kalacheng.busooolive.model.OTMAssisRet> assisRets;

/**
 * 消费人音量状态1开启0关播
 */
public int feeStatus;

   public OOOVolumeRet(Parcel in) 
{
operateUid=in.readLong();
operateStatus=in.readInt();
hostStatus=in.readInt();
hostUid=in.readLong();
feeUid=in.readLong();

if(assisRets==null){
assisRets=  new ArrayList<>();
 }
in.readTypedList(assisRets,com.kalacheng.busooolive.model.OTMAssisRet.CREATOR);
feeStatus=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(operateUid);
dest.writeInt(operateStatus);
dest.writeInt(hostStatus);
dest.writeLong(hostUid);
dest.writeLong(feeUid);

dest.writeTypedList(assisRets);
dest.writeInt(feeStatus);

}

  public static void cloneObj(OOOVolumeRet source,OOOVolumeRet target)
{

target.operateUid=source.operateUid;

target.operateStatus=source.operateStatus;

target.hostStatus=source.hostStatus;

target.hostUid=source.hostUid;

target.feeUid=source.feeUid;

        if(source.assisRets==null)
        {
            target.assisRets=null;
        }else
        {
            target.assisRets=new ArrayList();
            for(int i=0;i<source.assisRets.size();i++)
            {
            OTMAssisRet.cloneObj(source.assisRets.get(i),target.assisRets.get(i));
            }
        }


target.feeStatus=source.feeStatus;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OOOVolumeRet> CREATOR = new Creator<OOOVolumeRet>() {
        @Override
        public OOOVolumeRet createFromParcel(Parcel in) {
            return new OOOVolumeRet(in);
        }

        @Override
        public OOOVolumeRet[] newArray(int size) {
            return new OOOVolumeRet[size];
        }
    };
}
