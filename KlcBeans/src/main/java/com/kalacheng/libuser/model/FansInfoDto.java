package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 粉丝团信息
 */
public class FansInfoDto  implements Parcelable
{
 public FansInfoDto()
{
}

/**
 * 特权
 */
public List<com.kalacheng.libuser.model.KeyValueDto> privileges;

/**
 * 粉丝数量
 */
public int fansNum;

/**
 * 粉丝团创建与否  0：为创建 1：已创建
 */
public int isCreate;

/**
 * 主播头像
 */
public String anchorAvatar;

/**
 * 粉丝团名称
 */
public String fansTeamName;

/**
 * 总打赏金币
 */
public double totalCoin;

/**
 * 是否是粉丝团成员  0：不是 1：是
 */
public int isMember;

/**
 * 四位粉丝头像
 */
public List<com.kalacheng.libuser.model.AppUserAvatar> avatars;

/**
 * 加入一个粉丝团所需金币
 */
public double coin;

   public FansInfoDto(Parcel in) 
{

if(privileges==null){
privileges=  new ArrayList<>();
 }
in.readTypedList(privileges,com.kalacheng.libuser.model.KeyValueDto.CREATOR);
fansNum=in.readInt();
isCreate=in.readInt();
anchorAvatar=in.readString();
fansTeamName=in.readString();
totalCoin=in.readDouble();
isMember=in.readInt();

if(avatars==null){
avatars=  new ArrayList<>();
 }
in.readTypedList(avatars,com.kalacheng.libuser.model.AppUserAvatar.CREATOR);
coin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(privileges);
dest.writeInt(fansNum);
dest.writeInt(isCreate);
dest.writeString(anchorAvatar);
dest.writeString(fansTeamName);
dest.writeDouble(totalCoin);
dest.writeInt(isMember);

dest.writeTypedList(avatars);
dest.writeDouble(coin);

}

  public static void cloneObj(FansInfoDto source,FansInfoDto target)
{

        if(source.privileges==null)
        {
            target.privileges=null;
        }else
        {
            target.privileges=new ArrayList();
            for(int i=0;i<source.privileges.size();i++)
            {
            KeyValueDto.cloneObj(source.privileges.get(i),target.privileges.get(i));
            }
        }


target.fansNum=source.fansNum;

target.isCreate=source.isCreate;

target.anchorAvatar=source.anchorAvatar;

target.fansTeamName=source.fansTeamName;

target.totalCoin=source.totalCoin;

target.isMember=source.isMember;

        if(source.avatars==null)
        {
            target.avatars=null;
        }else
        {
            target.avatars=new ArrayList();
            for(int i=0;i<source.avatars.size();i++)
            {
            AppUserAvatar.cloneObj(source.avatars.get(i),target.avatars.get(i));
            }
        }


target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FansInfoDto> CREATOR = new Creator<FansInfoDto>() {
        @Override
        public FansInfoDto createFromParcel(Parcel in) {
            return new FansInfoDto(in);
        }

        @Override
        public FansInfoDto[] newArray(int size) {
            return new FansInfoDto[size];
        }
    };
}
