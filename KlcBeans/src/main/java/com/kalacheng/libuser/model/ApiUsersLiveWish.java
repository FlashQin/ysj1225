package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 心愿单响应
 */
public class ApiUsersLiveWish  implements Parcelable
{
 public ApiUsersLiveWish()
{
}

/**
 * 礼物头像
 */
public String gifticon;

/**
 * 心愿单礼物数
 */
public int num;

/**
 * 礼物类型
 */
public int type;

/**
 * 礼物名称
 */
public String giftname;

/**
 * 礼物头像
 */
public List<com.kalacheng.libuser.model.ApiWishUser> wishUserList;

/**
 * 礼物id
 */
public int giftid;

/**
 * 用户id
 */
public long uid;

/**
 * 是否选择 0:未选中 1:选中
 */
public int isCheck;

/**
 * 添加时间
 */
public Date addtime;

/**
 * 礼物价格
 */
public double needcoin;

/**
 * 心愿单id
 */
public long id;

/**
 * 收到的礼物数
 */
public int sendNum;

/**
 * 状态0进行中，1已结束
 */
public int status;

   public ApiUsersLiveWish(Parcel in) 
{
gifticon=in.readString();
num=in.readInt();
type=in.readInt();
giftname=in.readString();

if(wishUserList==null){
wishUserList=  new ArrayList<>();
 }
in.readTypedList(wishUserList,com.kalacheng.libuser.model.ApiWishUser.CREATOR);
giftid=in.readInt();
uid=in.readLong();
isCheck=in.readInt();
addtime=new Date( in.readLong());
needcoin=in.readDouble();
id=in.readLong();
sendNum=in.readInt();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(gifticon);
dest.writeInt(num);
dest.writeInt(type);
dest.writeString(giftname);

dest.writeTypedList(wishUserList);
dest.writeInt(giftid);
dest.writeLong(uid);
dest.writeInt(isCheck);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeDouble(needcoin);
dest.writeLong(id);
dest.writeInt(sendNum);
dest.writeInt(status);

}

  public static void cloneObj(ApiUsersLiveWish source,ApiUsersLiveWish target)
{

target.gifticon=source.gifticon;

target.num=source.num;

target.type=source.type;

target.giftname=source.giftname;

        if(source.wishUserList==null)
        {
            target.wishUserList=null;
        }else
        {
            target.wishUserList=new ArrayList();
            for(int i=0;i<source.wishUserList.size();i++)
            {
            ApiWishUser.cloneObj(source.wishUserList.get(i),target.wishUserList.get(i));
            }
        }


target.giftid=source.giftid;

target.uid=source.uid;

target.isCheck=source.isCheck;

target.addtime=source.addtime;

target.needcoin=source.needcoin;

target.id=source.id;

target.sendNum=source.sendNum;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUsersLiveWish> CREATOR = new Creator<ApiUsersLiveWish>() {
        @Override
        public ApiUsersLiveWish createFromParcel(Parcel in) {
            return new ApiUsersLiveWish(in);
        }

        @Override
        public ApiUsersLiveWish[] newArray(int size) {
            return new ApiUsersLiveWish[size];
        }
    };
}
