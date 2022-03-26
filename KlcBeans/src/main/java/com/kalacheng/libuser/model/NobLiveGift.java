package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 直播礼物表
 */
public class NobLiveGift  implements Parcelable
{
 public NobLiveGift()
{
}

/**
 * 礼物图标
 */
public String gifticon;

/**
 * 蓝量属性
 */
public int magic;

/**
 * 背包id
 */
public int backid;

/**
 * 礼物排序
 */
public int orderno;

/**
 * 动画链接
 */
public String swf;

/**
 * 礼物类型 0:普通礼物 1:粉丝团(豪华礼物) 2:守护(专属礼物) 3:贵族礼物(特殊礼物) 4:签到礼物
 */
public int type;

/**
 * 血量属性
 */
public int blood;

/**
 * 动画类型 0:是GIF 1:是SVGA）
 */
public int swftype;

/**
 * 礼物名称
 */
public String giftname;

/**
 * 礼物数量
 */
public int number;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 礼物价格
 */
public double needcoin;

/**
 * 0:未选择 1:选中
 */
public int checked;

/**
 * null
 */
public long id;

/**
 * 当前分页值
 */
public int page;

/**
 * 施法对象 0:默认 1:自己 2:对方
 */
public int casting_obj;

/**
 * 礼物标识 0:普通 1:热门 2:守护
 */
public int mark;

/**
 * 动画时长
 */
public double swftime;

/**
 * 状态 0:显示 1:不显示
 */
public int status;

   public NobLiveGift(Parcel in) 
{
gifticon=in.readString();
magic=in.readInt();
backid=in.readInt();
orderno=in.readInt();
swf=in.readString();
type=in.readInt();
blood=in.readInt();
swftype=in.readInt();
giftname=in.readString();
number=in.readInt();
addtime=new Date( in.readLong());
needcoin=in.readDouble();
checked=in.readInt();
id=in.readLong();
page=in.readInt();
casting_obj=in.readInt();
mark=in.readInt();
swftime=in.readDouble();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(gifticon);
dest.writeInt(magic);
dest.writeInt(backid);
dest.writeInt(orderno);
dest.writeString(swf);
dest.writeInt(type);
dest.writeInt(blood);
dest.writeInt(swftype);
dest.writeString(giftname);
dest.writeInt(number);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeDouble(needcoin);
dest.writeInt(checked);
dest.writeLong(id);
dest.writeInt(page);
dest.writeInt(casting_obj);
dest.writeInt(mark);
dest.writeDouble(swftime);
dest.writeInt(status);

}

  public static void cloneObj(NobLiveGift source,NobLiveGift target)
{

target.gifticon=source.gifticon;

target.magic=source.magic;

target.backid=source.backid;

target.orderno=source.orderno;

target.swf=source.swf;

target.type=source.type;

target.blood=source.blood;

target.swftype=source.swftype;

target.giftname=source.giftname;

target.number=source.number;

target.addtime=source.addtime;

target.needcoin=source.needcoin;

target.checked=source.checked;

target.id=source.id;

target.page=source.page;

target.casting_obj=source.casting_obj;

target.mark=source.mark;

target.swftime=source.swftime;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NobLiveGift> CREATOR = new Creator<NobLiveGift>() {
        @Override
        public NobLiveGift createFromParcel(Parcel in) {
            return new NobLiveGift(in);
        }

        @Override
        public NobLiveGift[] newArray(int size) {
            return new NobLiveGift[size];
        }
    };
}
