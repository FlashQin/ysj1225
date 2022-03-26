package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 用户交易流水结果返回
 */
public class ChangeDto  implements Parcelable
{
 public ChangeDto()
{
}

/**
 * 礼物名称
 */
public String giftName;

/**
 * 交易类型
 */
public String moneyType;

/**
 * 变更金额
 */
public double delta;

/**
 * 项目类型
 */
public int projectType;

/**
 * 来源交易类型
 */
public int fromChangeType;

/**
 * 金币图片地址
 */
public String moneyPictureIcon;

/**
 * 发起支付的时间时间抽
 */
public String timeStr;

/**
 * 交易ID
 */
public long changeId;

/**
 * 靓号ID
 */
public long numberId;

/**
 * 房间ID
 */
public long roomId;

/**
 * 更新金额字段名称
 */
public String changeFieldName;

/**
 * 礼物ID
 */
public long giftId;

/**
 * 收益比例
 */
public double perc;

/**
 * 更新金额字段
 */
public String changeField;

/**
 * 来源子交易类型
 */
public int fromChildChangeType;

/**
 * 提现状态，和提现记录保持一致即可
 */
public int state;

/**
 * 收益用户推荐级别： 主播， 族长， 推荐人， 此字段可以直接使用业务类型判断， 如果细分的话：分为一级推荐人，二级推荐人代理：分为第一层级代理， 第二层级代理， 不过个人觉得意义不大， 看业务需求吧， 此处仅代理和推荐需要级别，其余默认为0即可
 */
public int userLimit;

/**
 * 金币基数（基于此金币做分佣）
 */
public double baseDelta;

/**
 * 来源项目类型
 */
public int fromProjectType;

/**
 * 子交易名称
 */
public String childChangeName;

/**
 * 更新后映票
 */
public double afterVotes;

/**
 * 来源单号
 */
public String orderNo;

/**
 * 来源用户ID
 */
public long fromUserId;

/**
 * 交易类型
 */
public int changeType;

/**
 * 数量
 */
public int count;

/**
 * 来源子交易名称
 */
public String fromChildChangeName;

/**
 * 坐骑ID
 */
public long mountsId;

/**
 * 用户头像
 */
public String avatar;

/**
 * 心愿ID
 */
public long wishId;

/**
 * 收益来源于记录ID
 */
public long fromId;

/**
 * 公会ID
 */
public long guildId;

/**
 * 用户ID
 */
public long userId;

/**
 * 交易名称
 */
public String changeName;

/**
 * 直播标识ID
 */
public String showid;

/**
 * 更新后金币
 */
public double afterCoin;

/**
 * 发起支付的时间
 */
public Date createTime;

/**
 * 守护ID
 */
public long guardId;

/**
 * vip套餐ID
 */
public long vipMealId;

/**
 * 项目名称
 */
public String projectName;

/**
 * 更新后佣金
 */
public double afterAmount;

/**
 * 子交易类型
 */
public int childChangeType;

/**
 * 0默认，1直属礼物，2推广礼物，3专属礼物
 */
public int mark;

/**
 * 备注
 */
public String remarks;

/**
 * 直接受益人（主播）ID
 */
public long beneficiaryId;

/**
 * 单价(充值时，额外记录一下充值的真实金额)
 */
public double coin;

/**
 * 来源交易名称
 */
public String fromChangeName;

/**
 * 来源项目名称
 */
public String fromProjectName;

/**
 * 用户名
 */
public String username;

   public ChangeDto(Parcel in) 
{
giftName=in.readString();
moneyType=in.readString();
delta=in.readDouble();
projectType=in.readInt();
fromChangeType=in.readInt();
moneyPictureIcon=in.readString();
timeStr=in.readString();
changeId=in.readLong();
numberId=in.readLong();
roomId=in.readLong();
changeFieldName=in.readString();
giftId=in.readLong();
perc=in.readDouble();
changeField=in.readString();
fromChildChangeType=in.readInt();
state=in.readInt();
userLimit=in.readInt();
baseDelta=in.readDouble();
fromProjectType=in.readInt();
childChangeName=in.readString();
afterVotes=in.readDouble();
orderNo=in.readString();
fromUserId=in.readLong();
changeType=in.readInt();
count=in.readInt();
fromChildChangeName=in.readString();
mountsId=in.readLong();
avatar=in.readString();
wishId=in.readLong();
fromId=in.readLong();
guildId=in.readLong();
userId=in.readLong();
changeName=in.readString();
showid=in.readString();
afterCoin=in.readDouble();
createTime=new Date( in.readLong());
guardId=in.readLong();
vipMealId=in.readLong();
projectName=in.readString();
afterAmount=in.readDouble();
childChangeType=in.readInt();
mark=in.readInt();
remarks=in.readString();
beneficiaryId=in.readLong();
coin=in.readDouble();
fromChangeName=in.readString();
fromProjectName=in.readString();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(giftName);
dest.writeString(moneyType);
dest.writeDouble(delta);
dest.writeInt(projectType);
dest.writeInt(fromChangeType);
dest.writeString(moneyPictureIcon);
dest.writeString(timeStr);
dest.writeLong(changeId);
dest.writeLong(numberId);
dest.writeLong(roomId);
dest.writeString(changeFieldName);
dest.writeLong(giftId);
dest.writeDouble(perc);
dest.writeString(changeField);
dest.writeInt(fromChildChangeType);
dest.writeInt(state);
dest.writeInt(userLimit);
dest.writeDouble(baseDelta);
dest.writeInt(fromProjectType);
dest.writeString(childChangeName);
dest.writeDouble(afterVotes);
dest.writeString(orderNo);
dest.writeLong(fromUserId);
dest.writeInt(changeType);
dest.writeInt(count);
dest.writeString(fromChildChangeName);
dest.writeLong(mountsId);
dest.writeString(avatar);
dest.writeLong(wishId);
dest.writeLong(fromId);
dest.writeLong(guildId);
dest.writeLong(userId);
dest.writeString(changeName);
dest.writeString(showid);
dest.writeDouble(afterCoin);
dest.writeLong(createTime==null?0:createTime.getTime());
dest.writeLong(guardId);
dest.writeLong(vipMealId);
dest.writeString(projectName);
dest.writeDouble(afterAmount);
dest.writeInt(childChangeType);
dest.writeInt(mark);
dest.writeString(remarks);
dest.writeLong(beneficiaryId);
dest.writeDouble(coin);
dest.writeString(fromChangeName);
dest.writeString(fromProjectName);
dest.writeString(username);

}

  public static void cloneObj(ChangeDto source,ChangeDto target)
{

target.giftName=source.giftName;

target.moneyType=source.moneyType;

target.delta=source.delta;

target.projectType=source.projectType;

target.fromChangeType=source.fromChangeType;

target.moneyPictureIcon=source.moneyPictureIcon;

target.timeStr=source.timeStr;

target.changeId=source.changeId;

target.numberId=source.numberId;

target.roomId=source.roomId;

target.changeFieldName=source.changeFieldName;

target.giftId=source.giftId;

target.perc=source.perc;

target.changeField=source.changeField;

target.fromChildChangeType=source.fromChildChangeType;

target.state=source.state;

target.userLimit=source.userLimit;

target.baseDelta=source.baseDelta;

target.fromProjectType=source.fromProjectType;

target.childChangeName=source.childChangeName;

target.afterVotes=source.afterVotes;

target.orderNo=source.orderNo;

target.fromUserId=source.fromUserId;

target.changeType=source.changeType;

target.count=source.count;

target.fromChildChangeName=source.fromChildChangeName;

target.mountsId=source.mountsId;

target.avatar=source.avatar;

target.wishId=source.wishId;

target.fromId=source.fromId;

target.guildId=source.guildId;

target.userId=source.userId;

target.changeName=source.changeName;

target.showid=source.showid;

target.afterCoin=source.afterCoin;

target.createTime=source.createTime;

target.guardId=source.guardId;

target.vipMealId=source.vipMealId;

target.projectName=source.projectName;

target.afterAmount=source.afterAmount;

target.childChangeType=source.childChangeType;

target.mark=source.mark;

target.remarks=source.remarks;

target.beneficiaryId=source.beneficiaryId;

target.coin=source.coin;

target.fromChangeName=source.fromChangeName;

target.fromProjectName=source.fromProjectName;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChangeDto> CREATOR = new Creator<ChangeDto>() {
        @Override
        public ChangeDto createFromParcel(Parcel in) {
            return new ChangeDto(in);
        }

        @Override
        public ChangeDto[] newArray(int size) {
            return new ChangeDto[size];
        }
    };
}
