package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP关闭直播间响应
 */
public class ApiCloseLive  implements Parcelable
{
 public ApiCloseLive()
{
}

/**
 * 直播时长(秒)
 */
public long duration;

/**
 * 状态描述
 */
public String msg;

/**
 * 打赏人数
 */
public int rewardNumber;

/**
 * 状态1成功2失败
 */
public int code;

/**
 * 新增关注人数
 */
public int addFollow;

/**
 * 加入粉丝团人数
 */
public int addFansGroup;

/**
 * 收益
 */
public double votes;

/**
 * 用户头像
 */
public String avatar;

/**
 * 主播id
 */
public long anchorId;

/**
 * 主播名称
 */
public String anchorName;

/**
 * 关播时人数
 */
public int nums;

/**
 * 房间号
 */
public long roomId;

   public ApiCloseLive(Parcel in) 
{
duration=in.readLong();
msg=in.readString();
rewardNumber=in.readInt();
code=in.readInt();
addFollow=in.readInt();
addFansGroup=in.readInt();
votes=in.readDouble();
avatar=in.readString();
anchorId=in.readLong();
anchorName=in.readString();
nums=in.readInt();
roomId=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(duration);
dest.writeString(msg);
dest.writeInt(rewardNumber);
dest.writeInt(code);
dest.writeInt(addFollow);
dest.writeInt(addFansGroup);
dest.writeDouble(votes);
dest.writeString(avatar);
dest.writeLong(anchorId);
dest.writeString(anchorName);
dest.writeInt(nums);
dest.writeLong(roomId);

}

  public static void cloneObj(ApiCloseLive source,ApiCloseLive target)
{

target.duration=source.duration;

target.msg=source.msg;

target.rewardNumber=source.rewardNumber;

target.code=source.code;

target.addFollow=source.addFollow;

target.addFansGroup=source.addFansGroup;

target.votes=source.votes;

target.avatar=source.avatar;

target.anchorId=source.anchorId;

target.anchorName=source.anchorName;

target.nums=source.nums;

target.roomId=source.roomId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiCloseLive> CREATOR = new Creator<ApiCloseLive>() {
        @Override
        public ApiCloseLive createFromParcel(Parcel in) {
            return new ApiCloseLive(in);
        }

        @Override
        public ApiCloseLive[] newArray(int size) {
            return new ApiCloseLive[size];
        }
    };
}
