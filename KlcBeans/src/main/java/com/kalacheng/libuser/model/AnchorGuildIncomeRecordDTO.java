package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 主播公会收益记录
 */
public class AnchorGuildIncomeRecordDTO  implements Parcelable
{
 public AnchorGuildIncomeRecordDTO()
{
}

/**
 * 主播余额
 */
public double anchorVotes;

/**
 * 类型
 */
public int changeType;

/**
 * 主播当前余额(新增字段)
 */
public double anchorAfterVotes;

/**
 * 数量
 */
public int count;

/**
 * 文字内容
 */
public String content;

/**
 * 打赏人
 */
public String fromUserName;

/**
 * 礼物名称
 */
public String giftname;

/**
 * 收入总金额
 */
public double totalAmount;

/**
 * 主播分成比 此字段为了兼容 老数据
 */
public double perc;

/**
 * 主播分成比 新增字段
 */
public double anchorPerc;

/**
 * 时间
 */
public String createTime;

/**
 * 变更金额
 */
public double changeDisplay;

/**
 * 分成金额
 */
public double anchorBaseDelta;

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

   public AnchorGuildIncomeRecordDTO(Parcel in) 
{
anchorVotes=in.readDouble();
changeType=in.readInt();
anchorAfterVotes=in.readDouble();
count=in.readInt();
content=in.readString();
fromUserName=in.readString();
giftname=in.readString();
totalAmount=in.readDouble();
perc=in.readDouble();
anchorPerc=in.readDouble();
createTime=in.readString();
changeDisplay=in.readDouble();
anchorBaseDelta=in.readDouble();
remarks=in.readString();
baseDelta=in.readDouble();
fromChangeName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(anchorVotes);
dest.writeInt(changeType);
dest.writeDouble(anchorAfterVotes);
dest.writeInt(count);
dest.writeString(content);
dest.writeString(fromUserName);
dest.writeString(giftname);
dest.writeDouble(totalAmount);
dest.writeDouble(perc);
dest.writeDouble(anchorPerc);
dest.writeString(createTime);
dest.writeDouble(changeDisplay);
dest.writeDouble(anchorBaseDelta);
dest.writeString(remarks);
dest.writeDouble(baseDelta);
dest.writeString(fromChangeName);

}

  public static void cloneObj(AnchorGuildIncomeRecordDTO source,AnchorGuildIncomeRecordDTO target)
{

target.anchorVotes=source.anchorVotes;

target.changeType=source.changeType;

target.anchorAfterVotes=source.anchorAfterVotes;

target.count=source.count;

target.content=source.content;

target.fromUserName=source.fromUserName;

target.giftname=source.giftname;

target.totalAmount=source.totalAmount;

target.perc=source.perc;

target.anchorPerc=source.anchorPerc;

target.createTime=source.createTime;

target.changeDisplay=source.changeDisplay;

target.anchorBaseDelta=source.anchorBaseDelta;

target.remarks=source.remarks;

target.baseDelta=source.baseDelta;

target.fromChangeName=source.fromChangeName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnchorGuildIncomeRecordDTO> CREATOR = new Creator<AnchorGuildIncomeRecordDTO>() {
        @Override
        public AnchorGuildIncomeRecordDTO createFromParcel(Parcel in) {
            return new AnchorGuildIncomeRecordDTO(in);
        }

        @Override
        public AnchorGuildIncomeRecordDTO[] newArray(int size) {
            return new AnchorGuildIncomeRecordDTO[size];
        }
    };
}
