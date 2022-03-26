package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 新手大礼包
 */
public class AdminGiftPack  implements Parcelable
{
 public AdminGiftPack()
{
}

/**
 * 礼物图标
 */
public String gifticon;

/**
 * 类型值
 */
public int typeVal;

/**
 * 视频次数
 */
public int typeValVideo;

/**
 * 数量、时间
 */
public int typeValTwo;

/**
 * 礼包类型1新手大礼包2首冲奖励
 */
public int packType;

/**
 * 关联礼物id
 */
public int typeTree;

/**
 * 奖励类型
 */
public int type;

/**
 * 数量、时间
 */
public int typeValTree;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 奖励名称
 */
public String name;

/**
 * 奖励项目
 */
public String action;

/**
 * null
 */
public long id;

/**
 * 状态
 */
public int status;

   public AdminGiftPack(Parcel in) 
{
gifticon=in.readString();
typeVal=in.readInt();
typeValVideo=in.readInt();
typeValTwo=in.readInt();
packType=in.readInt();
typeTree=in.readInt();
type=in.readInt();
typeValTree=in.readInt();
addtime=new Date( in.readLong());
name=in.readString();
action=in.readString();
id=in.readLong();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(gifticon);
dest.writeInt(typeVal);
dest.writeInt(typeValVideo);
dest.writeInt(typeValTwo);
dest.writeInt(packType);
dest.writeInt(typeTree);
dest.writeInt(type);
dest.writeInt(typeValTree);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeString(name);
dest.writeString(action);
dest.writeLong(id);
dest.writeInt(status);

}

  public static void cloneObj(AdminGiftPack source,AdminGiftPack target)
{

target.gifticon=source.gifticon;

target.typeVal=source.typeVal;

target.typeValVideo=source.typeValVideo;

target.typeValTwo=source.typeValTwo;

target.packType=source.packType;

target.typeTree=source.typeTree;

target.type=source.type;

target.typeValTree=source.typeValTree;

target.addtime=source.addtime;

target.name=source.name;

target.action=source.action;

target.id=source.id;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminGiftPack> CREATOR = new Creator<AdminGiftPack>() {
        @Override
        public AdminGiftPack createFromParcel(Parcel in) {
            return new AdminGiftPack(in);
        }

        @Override
        public AdminGiftPack[] newArray(int size) {
            return new AdminGiftPack[size];
        }
    };
}
