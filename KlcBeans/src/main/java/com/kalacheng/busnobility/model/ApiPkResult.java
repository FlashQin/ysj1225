package com.kalacheng.busnobility.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 赠送礼物后返回双方血条信息
 */
public class ApiPkResult  implements Parcelable
{
 public ApiPkResult()
{
}

/**
 * 主播id
 */
public long anchorId;

/**
 * 当前pk值
 */
public double votesPK;

/**
 * 对方pk值
 */
public double toVotesPK;

   public ApiPkResult(Parcel in) 
{
anchorId=in.readLong();
votesPK=in.readDouble();
toVotesPK=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(anchorId);
dest.writeDouble(votesPK);
dest.writeDouble(toVotesPK);

}

  public static void cloneObj(ApiPkResult source,ApiPkResult target)
{

target.anchorId=source.anchorId;

target.votesPK=source.votesPK;

target.toVotesPK=source.toVotesPK;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiPkResult> CREATOR = new Creator<ApiPkResult>() {
        @Override
        public ApiPkResult createFromParcel(Parcel in) {
            return new ApiPkResult(in);
        }

        @Override
        public ApiPkResult[] newArray(int size) {
            return new ApiPkResult[size];
        }
    };
}
