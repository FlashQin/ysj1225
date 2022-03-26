package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * pk麦位
 */
public class PkUserVoiceAssistan  implements Parcelable
{
 public PkUserVoiceAssistan()
{
}

/**
 * 麦位信息实体
 */
public com.kalacheng.libuser.model.ApiUsersVoiceAssistan usersVoiceAssistan;

/**
 * 当前PK麦位主播所收的礼物值
 */
public double giftVotes;

/**
 * PK页面麦序编号
 */
public int pkNo;

   public PkUserVoiceAssistan(Parcel in) 
{

usersVoiceAssistan=in.readParcelable(com.kalacheng.libuser.model.ApiUsersVoiceAssistan.class.getClassLoader());
giftVotes=in.readDouble();
pkNo=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeParcelable(usersVoiceAssistan,flags);
dest.writeDouble(giftVotes);
dest.writeInt(pkNo);

}

  public static void cloneObj(PkUserVoiceAssistan source,PkUserVoiceAssistan target)
{
        if(source.usersVoiceAssistan==null)
        {
            target.usersVoiceAssistan=null;
        }else
        {
            ApiUsersVoiceAssistan.cloneObj(source.usersVoiceAssistan,target.usersVoiceAssistan);
        }

target.giftVotes=source.giftVotes;

target.pkNo=source.pkNo;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PkUserVoiceAssistan> CREATOR = new Creator<PkUserVoiceAssistan>() {
        @Override
        public PkUserVoiceAssistan createFromParcel(Parcel in) {
            return new PkUserVoiceAssistan(in);
        }

        @Override
        public PkUserVoiceAssistan[] newArray(int size) {
            return new PkUserVoiceAssistan[size];
        }
    };
}
