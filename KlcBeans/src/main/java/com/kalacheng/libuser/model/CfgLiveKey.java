package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理-三方设置-直播配置
 */
public class CfgLiveKey  implements Parcelable
{
 public CfgLiveKey()
{
}

/**
 * 美颜开关
 */
public int beautySwitch;

/**
 * imkey
 */
public String imKey;

/**
 * 美颜key
 */
public String beautyKey;

/**
 * IM secretKey
 */
public String imSecretKey;

/**
 * 直播CDNInfo
 */
public String cdnInfo;

/**
 * 短视频SDK secretKey
 */
public String videoClipsSecretKey;

/**
 * 直播云secretKey
 */
public String liveSecretKey;

/**
 * 直播key
 */
public String liveKey;

/**
 * 短视频SDK Info
 */
public String videoClipsInfo;

/**
 * 直播云appid
 */
public String liveAppid;

/**
 * 直播CDN appid
 */
public String cdnAppId;

/**
 * 短视频SDKKey
 */
public String videoClipsKey;

/**
 * IM appId
 */
public String imAppid;

/**
 * 短视频SDK appid
 */
public String videoClipsAppId;

/**
 * 美颜信息
 */
public String beautyKeyInfo;

/**
 * 美颜appid
 */
public String beautyAppid;

/**
 * 直播CDN Cfg Key
 */
public String cdnCfgKey;

/**
 * liveKey信息
 */
public String liveKeyInfo;

/**
 * 直播CDN secretKey
 */
public String cdnSecretKey;

/**
 * imProt
 */
public int imProt;

/**
 * 美颜secretKey
 */
public String beautySecretKey;

/**
 * imIp
 */
public String imIp;

/**
 * null
 */
public long id;

/**
 * imInfo
 */
public String imInfo;

   public CfgLiveKey(Parcel in) 
{
beautySwitch=in.readInt();
imKey=in.readString();
beautyKey=in.readString();
imSecretKey=in.readString();
cdnInfo=in.readString();
videoClipsSecretKey=in.readString();
liveSecretKey=in.readString();
liveKey=in.readString();
videoClipsInfo=in.readString();
liveAppid=in.readString();
cdnAppId=in.readString();
videoClipsKey=in.readString();
imAppid=in.readString();
videoClipsAppId=in.readString();
beautyKeyInfo=in.readString();
beautyAppid=in.readString();
cdnCfgKey=in.readString();
liveKeyInfo=in.readString();
cdnSecretKey=in.readString();
imProt=in.readInt();
beautySecretKey=in.readString();
imIp=in.readString();
id=in.readLong();
imInfo=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(beautySwitch);
dest.writeString(imKey);
dest.writeString(beautyKey);
dest.writeString(imSecretKey);
dest.writeString(cdnInfo);
dest.writeString(videoClipsSecretKey);
dest.writeString(liveSecretKey);
dest.writeString(liveKey);
dest.writeString(videoClipsInfo);
dest.writeString(liveAppid);
dest.writeString(cdnAppId);
dest.writeString(videoClipsKey);
dest.writeString(imAppid);
dest.writeString(videoClipsAppId);
dest.writeString(beautyKeyInfo);
dest.writeString(beautyAppid);
dest.writeString(cdnCfgKey);
dest.writeString(liveKeyInfo);
dest.writeString(cdnSecretKey);
dest.writeInt(imProt);
dest.writeString(beautySecretKey);
dest.writeString(imIp);
dest.writeLong(id);
dest.writeString(imInfo);

}

  public static void cloneObj(CfgLiveKey source,CfgLiveKey target)
{

target.beautySwitch=source.beautySwitch;

target.imKey=source.imKey;

target.beautyKey=source.beautyKey;

target.imSecretKey=source.imSecretKey;

target.cdnInfo=source.cdnInfo;

target.videoClipsSecretKey=source.videoClipsSecretKey;

target.liveSecretKey=source.liveSecretKey;

target.liveKey=source.liveKey;

target.videoClipsInfo=source.videoClipsInfo;

target.liveAppid=source.liveAppid;

target.cdnAppId=source.cdnAppId;

target.videoClipsKey=source.videoClipsKey;

target.imAppid=source.imAppid;

target.videoClipsAppId=source.videoClipsAppId;

target.beautyKeyInfo=source.beautyKeyInfo;

target.beautyAppid=source.beautyAppid;

target.cdnCfgKey=source.cdnCfgKey;

target.liveKeyInfo=source.liveKeyInfo;

target.cdnSecretKey=source.cdnSecretKey;

target.imProt=source.imProt;

target.beautySecretKey=source.beautySecretKey;

target.imIp=source.imIp;

target.id=source.id;

target.imInfo=source.imInfo;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CfgLiveKey> CREATOR = new Creator<CfgLiveKey>() {
        @Override
        public CfgLiveKey createFromParcel(Parcel in) {
            return new CfgLiveKey(in);
        }

        @Override
        public CfgLiveKey[] newArray(int size) {
            return new CfgLiveKey[size];
        }
    };
}
