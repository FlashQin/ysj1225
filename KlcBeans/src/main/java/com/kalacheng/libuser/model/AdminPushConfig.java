package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理-三方设置-推送配置
 */
public class AdminPushConfig  implements Parcelable
{
 public AdminPushConfig()
{
}

/**
 * 腾讯SecretKey
 */
public String txSecretKey;

/**
 * voip推送authKeyPath
 */
public String voipAuthKeyPath;

/**
 * vivo推送SecretKey
 */
public String vivoSecretKey;

/**
 * oppo推送SecretKey
 */
public String oppoSecretKey;

/**
 * 华为推送AppSecret
 */
public String hwAppSecret;

/**
 * 小米推送通道id
 */
public String miPassageId;

/**
 * vivo推送AppId
 */
public String vivoAppId;

/**
 * APNS推送teamId
 */
public String apnsTeamId;

/**
 * APNS推送keyId/p12Pwd
 */
public String apnsKeyId;

/**
 * 华为推送AppId
 */
public String hwAppId;

/**
 * 极光推送模式
 */
public int jpushSandbox;

/**
 * 小米推送SecretKey
 */
public String miSecretKey;

/**
 * voip推送p12Pwd
 */
public String voipKeyId;

/**
 * APNS推送authKeyPath
 */
public String apnsAuthKeyPath;

/**
 * oppo推送AppKey
 */
public String oppoAppKey;

/**
 * 安卓包名
 */
public String androidPackageNames;

/**
 * ios包名
 */
public String iosPackageNames;

/**
 * 腾讯AppId
 */
public String txAppId;

/**
 * 极光推送APP_KEY
 */
public String jpushKey;

/**
 * vivo推送AppKey
 */
public String vivoAppKey;

/**
 * null
 */
public long id;

/**
 * 极光推送master_secret
 */
public String jpushSecret;

/**
 * oppo推送通道id
 */
public String oppoPassageId;

   public AdminPushConfig(Parcel in) 
{
txSecretKey=in.readString();
voipAuthKeyPath=in.readString();
vivoSecretKey=in.readString();
oppoSecretKey=in.readString();
hwAppSecret=in.readString();
miPassageId=in.readString();
vivoAppId=in.readString();
apnsTeamId=in.readString();
apnsKeyId=in.readString();
hwAppId=in.readString();
jpushSandbox=in.readInt();
miSecretKey=in.readString();
voipKeyId=in.readString();
apnsAuthKeyPath=in.readString();
oppoAppKey=in.readString();
androidPackageNames=in.readString();
iosPackageNames=in.readString();
txAppId=in.readString();
jpushKey=in.readString();
vivoAppKey=in.readString();
id=in.readLong();
jpushSecret=in.readString();
oppoPassageId=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(txSecretKey);
dest.writeString(voipAuthKeyPath);
dest.writeString(vivoSecretKey);
dest.writeString(oppoSecretKey);
dest.writeString(hwAppSecret);
dest.writeString(miPassageId);
dest.writeString(vivoAppId);
dest.writeString(apnsTeamId);
dest.writeString(apnsKeyId);
dest.writeString(hwAppId);
dest.writeInt(jpushSandbox);
dest.writeString(miSecretKey);
dest.writeString(voipKeyId);
dest.writeString(apnsAuthKeyPath);
dest.writeString(oppoAppKey);
dest.writeString(androidPackageNames);
dest.writeString(iosPackageNames);
dest.writeString(txAppId);
dest.writeString(jpushKey);
dest.writeString(vivoAppKey);
dest.writeLong(id);
dest.writeString(jpushSecret);
dest.writeString(oppoPassageId);

}

  public static void cloneObj(AdminPushConfig source,AdminPushConfig target)
{

target.txSecretKey=source.txSecretKey;

target.voipAuthKeyPath=source.voipAuthKeyPath;

target.vivoSecretKey=source.vivoSecretKey;

target.oppoSecretKey=source.oppoSecretKey;

target.hwAppSecret=source.hwAppSecret;

target.miPassageId=source.miPassageId;

target.vivoAppId=source.vivoAppId;

target.apnsTeamId=source.apnsTeamId;

target.apnsKeyId=source.apnsKeyId;

target.hwAppId=source.hwAppId;

target.jpushSandbox=source.jpushSandbox;

target.miSecretKey=source.miSecretKey;

target.voipKeyId=source.voipKeyId;

target.apnsAuthKeyPath=source.apnsAuthKeyPath;

target.oppoAppKey=source.oppoAppKey;

target.androidPackageNames=source.androidPackageNames;

target.iosPackageNames=source.iosPackageNames;

target.txAppId=source.txAppId;

target.jpushKey=source.jpushKey;

target.vivoAppKey=source.vivoAppKey;

target.id=source.id;

target.jpushSecret=source.jpushSecret;

target.oppoPassageId=source.oppoPassageId;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminPushConfig> CREATOR = new Creator<AdminPushConfig>() {
        @Override
        public AdminPushConfig createFromParcel(Parcel in) {
            return new AdminPushConfig(in);
        }

        @Override
        public AdminPushConfig[] newArray(int size) {
            return new AdminPushConfig[size];
        }
    };
}
