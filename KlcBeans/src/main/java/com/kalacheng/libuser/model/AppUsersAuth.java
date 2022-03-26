package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 主播认证
 */
public class AppUsersAuth  implements Parcelable
{
 public AppUsersAuth()
{
}

/**
 * qq账号
 */
public String qq;

/**
 * 其他资料图3
 */
public String other3View;

/**
 * 审核说明
 */
public String reason;

/**
 * 提交时间
 */
public Date addTime;

/**
 * 反面
 */
public String backView;

/**
 * 电话
 */
public String mobile;

/**
 * 所属区域家族id
 */
public int pidLevel2;

/**
 * 微信账号
 */
public String wechat;

/**
 * 所属总家族ID
 */
public int pidLevel1;

/**
 * 所属经纪人id
 */
public int pidLevel4;

/**
 * 其他资料图1
 */
public String other1View;

/**
 * 所属家族id
 */
public int pidLevel3;

/**
 * 更新时间
 */
public Date uptime;

/**
 * 正面
 */
public String frontView;

/**
 * 姓名
 */
public String realName;

/**
 * 用户id
 */
public long uid;

/**
 * 身份证号
 */
public String cerNo;

/**
 * 手持
 */
public String handsetView;

/**
 * 短视频地址
 */
public String videoUrl;

/**
 * 步骤
 */
public int step;

/**
 * 其他资料图2
 */
public String other2View;

/**
 * 附加信息，用于确认分销商
 */
public String extraInfo;

/**
 * 状态
 */
public int status;

   public AppUsersAuth(Parcel in) 
{
qq=in.readString();
other3View=in.readString();
reason=in.readString();
addTime=new Date( in.readLong());
backView=in.readString();
mobile=in.readString();
pidLevel2=in.readInt();
wechat=in.readString();
pidLevel1=in.readInt();
pidLevel4=in.readInt();
other1View=in.readString();
pidLevel3=in.readInt();
uptime=new Date( in.readLong());
frontView=in.readString();
realName=in.readString();
uid=in.readLong();
cerNo=in.readString();
handsetView=in.readString();
videoUrl=in.readString();
step=in.readInt();
other2View=in.readString();
extraInfo=in.readString();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(qq);
dest.writeString(other3View);
dest.writeString(reason);
dest.writeLong(addTime==null?0:addTime.getTime());
dest.writeString(backView);
dest.writeString(mobile);
dest.writeInt(pidLevel2);
dest.writeString(wechat);
dest.writeInt(pidLevel1);
dest.writeInt(pidLevel4);
dest.writeString(other1View);
dest.writeInt(pidLevel3);
dest.writeLong(uptime==null?0:uptime.getTime());
dest.writeString(frontView);
dest.writeString(realName);
dest.writeLong(uid);
dest.writeString(cerNo);
dest.writeString(handsetView);
dest.writeString(videoUrl);
dest.writeInt(step);
dest.writeString(other2View);
dest.writeString(extraInfo);
dest.writeInt(status);

}

  public static void cloneObj(AppUsersAuth source,AppUsersAuth target)
{

target.qq=source.qq;

target.other3View=source.other3View;

target.reason=source.reason;

target.addTime=source.addTime;

target.backView=source.backView;

target.mobile=source.mobile;

target.pidLevel2=source.pidLevel2;

target.wechat=source.wechat;

target.pidLevel1=source.pidLevel1;

target.pidLevel4=source.pidLevel4;

target.other1View=source.other1View;

target.pidLevel3=source.pidLevel3;

target.uptime=source.uptime;

target.frontView=source.frontView;

target.realName=source.realName;

target.uid=source.uid;

target.cerNo=source.cerNo;

target.handsetView=source.handsetView;

target.videoUrl=source.videoUrl;

target.step=source.step;

target.other2View=source.other2View;

target.extraInfo=source.extraInfo;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppUsersAuth> CREATOR = new Creator<AppUsersAuth>() {
        @Override
        public AppUsersAuth createFromParcel(Parcel in) {
            return new AppUsersAuth(in);
        }

        @Override
        public AppUsersAuth[] newArray(int size) {
            return new AppUsersAuth[size];
        }
    };
}
