package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理-三方设置-视频配置
 */
public class AdminVideoConfig  implements Parcelable
{
 public AdminVideoConfig()
{
}

/**
 * 上传地址
 */
public String upUrl;

/**
 * babaKey信息
 */
public String babaKeyInfo;

/**
 * 视频评论开关0开1关
 */
public int videoCommentSwitch;

/**
 * 轩嗵secretKey
 */
public String babaSecretKey;

/**
 * 区域z0华东,z1华北,z华南,na0北美,as0东南亚, z0sh2华东上海2
 */
public String zone;

/**
 * 视频审核开关0开1关
 */
public int videoAuditSwitch;

/**
 * 存储方式
 */
public int cloudtype;

/**
 * 轩嗵云存储KEY
 */
public String babaKey;

/**
 * 轩嗵appid
 */
public String babaAppid;

/**
 * null
 */
public long id;

   public AdminVideoConfig(Parcel in) 
{
upUrl=in.readString();
babaKeyInfo=in.readString();
videoCommentSwitch=in.readInt();
babaSecretKey=in.readString();
zone=in.readString();
videoAuditSwitch=in.readInt();
cloudtype=in.readInt();
babaKey=in.readString();
babaAppid=in.readString();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(upUrl);
dest.writeString(babaKeyInfo);
dest.writeInt(videoCommentSwitch);
dest.writeString(babaSecretKey);
dest.writeString(zone);
dest.writeInt(videoAuditSwitch);
dest.writeInt(cloudtype);
dest.writeString(babaKey);
dest.writeString(babaAppid);
dest.writeLong(id);

}

  public static void cloneObj(AdminVideoConfig source,AdminVideoConfig target)
{

target.upUrl=source.upUrl;

target.babaKeyInfo=source.babaKeyInfo;

target.videoCommentSwitch=source.videoCommentSwitch;

target.babaSecretKey=source.babaSecretKey;

target.zone=source.zone;

target.videoAuditSwitch=source.videoAuditSwitch;

target.cloudtype=source.cloudtype;

target.babaKey=source.babaKey;

target.babaAppid=source.babaAppid;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminVideoConfig> CREATOR = new Creator<AdminVideoConfig>() {
        @Override
        public AdminVideoConfig createFromParcel(Parcel in) {
            return new AdminVideoConfig(in);
        }

        @Override
        public AdminVideoConfig[] newArray(int size) {
            return new AdminVideoConfig[size];
        }
    };
}
