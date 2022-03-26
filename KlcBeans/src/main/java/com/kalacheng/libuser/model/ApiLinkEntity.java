package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 
 */
public class ApiLinkEntity  implements Parcelable
{
 public ApiLinkEntity()
{
}

/**
 * 会话ID
 */
public long sessionID;

   public ApiLinkEntity(Parcel in) 
{
sessionID=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(sessionID);

}

  public static void cloneObj(ApiLinkEntity source,ApiLinkEntity target)
{

target.sessionID=source.sessionID;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiLinkEntity> CREATOR = new Creator<ApiLinkEntity>() {
        @Override
        public ApiLinkEntity createFromParcel(Parcel in) {
            return new ApiLinkEntity(in);
        }

        @Override
        public ApiLinkEntity[] newArray(int size) {
            return new ApiLinkEntity[size];
        }
    };
}
