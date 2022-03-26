package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP查询拉黑信息
 */
public class ApiUsersVideoBlack  implements Parcelable
{
 public ApiUsersVideoBlack()
{
}

/**
 * 视频是否被拉黑0未拉黑1已拉黑
 */
public int videoBlack;

/**
 * 被拉黑用户id
 */
public long toUId;

/**
 * 用户是否被拉黑0未拉黑1已拉黑
 */
public int userBlack;

/**
 * 语音是否被拉黑0未拉黑1已拉黑
 */
public int voiceBlack;

/**
 * 文本是否被拉黑0未拉黑1已拉黑
 */
public int textBlack;

/**
 * 当前用户id
 */
public long userId;

   public ApiUsersVideoBlack(Parcel in) 
{
videoBlack=in.readInt();
toUId=in.readLong();
userBlack=in.readInt();
voiceBlack=in.readInt();
textBlack=in.readInt();
userId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(videoBlack);
dest.writeLong(toUId);
dest.writeInt(userBlack);
dest.writeInt(voiceBlack);
dest.writeInt(textBlack);
dest.writeLong(userId);

}

  public static void cloneObj(ApiUsersVideoBlack source,ApiUsersVideoBlack target)
{

target.videoBlack=source.videoBlack;

target.toUId=source.toUId;

target.userBlack=source.userBlack;

target.voiceBlack=source.voiceBlack;

target.textBlack=source.textBlack;

target.userId=source.userId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUsersVideoBlack> CREATOR = new Creator<ApiUsersVideoBlack>() {
        @Override
        public ApiUsersVideoBlack createFromParcel(Parcel in) {
            return new ApiUsersVideoBlack(in);
        }

        @Override
        public ApiUsersVideoBlack[] newArray(int size) {
            return new ApiUsersVideoBlack[size];
        }
    };
}
