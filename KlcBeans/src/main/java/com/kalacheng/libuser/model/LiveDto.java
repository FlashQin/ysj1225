package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 勋章页数据
 */
public class LiveDto  implements Parcelable
{
 public LiveDto()
{
}

/**
 * 类型值
 */
public String typeVal;

/**
 * 直播状态 0:关播 1：正常
 */
public int islive;

/**
 * 门票房间是否需要付费 0:不需要付费 1:需要付费
 */
public int isPay;

/**
 * showid
 */
public String showid;

/**
 * 直播类型 0:一般直播 1:私密直播 2:是收费直播 3:是计时直播 4:贵族房间(跟列表一致此处返回的是11)
 */
public int sourceType;

/**
 * 封面
 */
public String thumb;

/**
 * 观看人数
 */
public int nums;

/**
 * 房间号
 */
public long roomId;

/**
 * 类型描述
 */
public String typeDec;

/**
 * 付费/计时房间金额
 */
public double coin;

   public LiveDto(Parcel in) 
{
typeVal=in.readString();
islive=in.readInt();
isPay=in.readInt();
showid=in.readString();
sourceType=in.readInt();
thumb=in.readString();
nums=in.readInt();
roomId=in.readLong();
typeDec=in.readString();
coin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(typeVal);
dest.writeInt(islive);
dest.writeInt(isPay);
dest.writeString(showid);
dest.writeInt(sourceType);
dest.writeString(thumb);
dest.writeInt(nums);
dest.writeLong(roomId);
dest.writeString(typeDec);
dest.writeDouble(coin);

}

  public static void cloneObj(LiveDto source,LiveDto target)
{

target.typeVal=source.typeVal;

target.islive=source.islive;

target.isPay=source.isPay;

target.showid=source.showid;

target.sourceType=source.sourceType;

target.thumb=source.thumb;

target.nums=source.nums;

target.roomId=source.roomId;

target.typeDec=source.typeDec;

target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveDto> CREATOR = new Creator<LiveDto>() {
        @Override
        public LiveDto createFromParcel(Parcel in) {
            return new LiveDto(in);
        }

        @Override
        public LiveDto[] newArray(int size) {
            return new LiveDto[size];
        }
    };
}
