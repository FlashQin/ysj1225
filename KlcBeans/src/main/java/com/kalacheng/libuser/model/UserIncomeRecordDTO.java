package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户收益记录
 */
public class UserIncomeRecordDTO  implements Parcelable
{
 public UserIncomeRecordDTO()
{
}

/**
 * 打赏人
 */
public String fromUserName;

/**
 * 礼物名称
 */
public String giftname;

/**
 * 主播分成比
 */
public double perc;

/**
 * 时间
 */
public String createTime;

/**
 * 主播余额
 */
public double anchorVotes;

/**
 * 类型
 */
public int changeType;

/**
 * 数量
 */
public int count;

/**
 * 分成金额
 */
public double anchorBaseDelta;

/**
 * 文字内容
 */
public String content;

/**
 * 备注
 */
public String remarks;

/**
 * 打赏金额
 */
public double baseDelta;

/**
 * 交易名称
 */
public String fromChangeName;

   public UserIncomeRecordDTO(Parcel in) 
{
fromUserName=in.readString();
giftname=in.readString();
perc=in.readDouble();
createTime=in.readString();
anchorVotes=in.readDouble();
changeType=in.readInt();
count=in.readInt();
anchorBaseDelta=in.readDouble();
content=in.readString();
remarks=in.readString();
baseDelta=in.readDouble();
fromChangeName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(fromUserName);
dest.writeString(giftname);
dest.writeDouble(perc);
dest.writeString(createTime);
dest.writeDouble(anchorVotes);
dest.writeInt(changeType);
dest.writeInt(count);
dest.writeDouble(anchorBaseDelta);
dest.writeString(content);
dest.writeString(remarks);
dest.writeDouble(baseDelta);
dest.writeString(fromChangeName);

}

  public static void cloneObj(UserIncomeRecordDTO source,UserIncomeRecordDTO target)
{

target.fromUserName=source.fromUserName;

target.giftname=source.giftname;

target.perc=source.perc;

target.createTime=source.createTime;

target.anchorVotes=source.anchorVotes;

target.changeType=source.changeType;

target.count=source.count;

target.anchorBaseDelta=source.anchorBaseDelta;

target.content=source.content;

target.remarks=source.remarks;

target.baseDelta=source.baseDelta;

target.fromChangeName=source.fromChangeName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserIncomeRecordDTO> CREATOR = new Creator<UserIncomeRecordDTO>() {
        @Override
        public UserIncomeRecordDTO createFromParcel(Parcel in) {
            return new UserIncomeRecordDTO(in);
        }

        @Override
        public UserIncomeRecordDTO[] newArray(int size) {
            return new UserIncomeRecordDTO[size];
        }
    };
}
