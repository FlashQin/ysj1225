package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 1vs1-求聊话费设置
 */
public class AdminRushToTalkConfig  implements Parcelable
{
 public AdminRushToTalkConfig()
{
}

/**
 * 添加时间
 */
public Date createTime;

/**
 * 话费
 */
public int telephoneMoney;

/**
 * null
 */
public long id;

/**
 * 聊天类型 1:视频聊天 2:语音聊天
 */
public int chatType;

   public AdminRushToTalkConfig(Parcel in) 
{
createTime=new Date( in.readLong());
telephoneMoney=in.readInt();
id=in.readLong();
chatType=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeInt(telephoneMoney);
dest.writeLong(id);
dest.writeInt(chatType);

}

  public static void cloneObj(AdminRushToTalkConfig source,AdminRushToTalkConfig target)
{

target.createTime=source.createTime;

target.telephoneMoney=source.telephoneMoney;

target.id=source.id;

target.chatType=source.chatType;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminRushToTalkConfig> CREATOR = new Creator<AdminRushToTalkConfig>() {
        @Override
        public AdminRushToTalkConfig createFromParcel(Parcel in) {
            return new AdminRushToTalkConfig(in);
        }

        @Override
        public AdminRushToTalkConfig[] newArray(int size) {
            return new AdminRushToTalkConfig[size];
        }
    };
}
