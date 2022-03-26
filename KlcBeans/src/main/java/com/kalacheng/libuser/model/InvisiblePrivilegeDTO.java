package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 贵族隐身特权
 */
public class InvisiblePrivilegeDTO  implements Parcelable
{
 public InvisiblePrivilegeDTO()
{
}

/**
 * 充值隐身特权 0不隐身1隐身
 */
public int chargeShow;

/**
 * 拥有隐身特权的最低贵族身份
 */
public String lowestLv;

/**
 * 加入房间隐身特权 0不隐身1隐身
 */
public int joinRoomShow;

/**
 * 是否拥有特权 0没有隐身特权1有隐身特权
 */
public int hasPrivilege;

/**
 * 贡献榜排行隐身特权 0不隐身1隐身
 */
public int devoteShow;

   public InvisiblePrivilegeDTO(Parcel in) 
{
chargeShow=in.readInt();
lowestLv=in.readString();
joinRoomShow=in.readInt();
hasPrivilege=in.readInt();
devoteShow=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(chargeShow);
dest.writeString(lowestLv);
dest.writeInt(joinRoomShow);
dest.writeInt(hasPrivilege);
dest.writeInt(devoteShow);

}

  public static void cloneObj(InvisiblePrivilegeDTO source,InvisiblePrivilegeDTO target)
{

target.chargeShow=source.chargeShow;

target.lowestLv=source.lowestLv;

target.joinRoomShow=source.joinRoomShow;

target.hasPrivilege=source.hasPrivilege;

target.devoteShow=source.devoteShow;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InvisiblePrivilegeDTO> CREATOR = new Creator<InvisiblePrivilegeDTO>() {
        @Override
        public InvisiblePrivilegeDTO createFromParcel(Parcel in) {
            return new InvisiblePrivilegeDTO(in);
        }

        @Override
        public InvisiblePrivilegeDTO[] newArray(int size) {
            return new InvisiblePrivilegeDTO[size];
        }
    };
}
