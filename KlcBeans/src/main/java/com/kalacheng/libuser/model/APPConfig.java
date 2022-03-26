package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 初始化APP公共设置
 */
public class APPConfig  implements Parcelable
{
 public APPConfig()
{
}

/**
 * 货币
 */
public String vcUnit;

/**
 * 直播云下相关配置
 */
public com.kalacheng.libuser.model.AdminLiveConfig adminLiveConfig;

/**
 * 关键字屏蔽配置
 */
public com.kalacheng.libuser.model.AdminKeywordManage keywordManage;

/**
 * 直播相关Key
 */
public com.kalacheng.libuser.model.CfgLiveKey liveKey;

/**
 * 微信支付的AppId
 */
public String wxAppId;

/**
 * 支付配置
 */
public List<com.kalacheng.libuser.model.PayConfigDto> payConfigList;

/**
 * 极光相关配置
 */
public com.kalacheng.libuser.model.AdminPushConfig adminPushConfig;

/**
 * 轩嗵云相关配置
 */
public com.kalacheng.libuser.model.AdminVideoConfig adminVideoConfig;

/**
 * 登录开关
 */
public com.kalacheng.libuser.model.AdminLoginSwitch loginSwitch;

/**
 * 当前时间
 */
public long currTime;

/**
 * 客服服务设置
 */
public com.kalacheng.libuser.model.CustomerServiceSetting customerServiceSetting;

/**
 * APP版本配置
 */
public com.kalacheng.libuser.model.AdminVersionManage versionManage;

/**
 * 直播画质
 */
public int imageQuality;

   public APPConfig(Parcel in) 
{
vcUnit=in.readString();

adminLiveConfig=in.readParcelable(com.kalacheng.libuser.model.AdminLiveConfig.class.getClassLoader());

keywordManage=in.readParcelable(com.kalacheng.libuser.model.AdminKeywordManage.class.getClassLoader());

liveKey=in.readParcelable(com.kalacheng.libuser.model.CfgLiveKey.class.getClassLoader());
wxAppId=in.readString();

if(payConfigList==null){
payConfigList=  new ArrayList<>();
 }
in.readTypedList(payConfigList,com.kalacheng.libuser.model.PayConfigDto.CREATOR);

adminPushConfig=in.readParcelable(com.kalacheng.libuser.model.AdminPushConfig.class.getClassLoader());

adminVideoConfig=in.readParcelable(com.kalacheng.libuser.model.AdminVideoConfig.class.getClassLoader());

loginSwitch=in.readParcelable(com.kalacheng.libuser.model.AdminLoginSwitch.class.getClassLoader());
currTime=in.readLong();

customerServiceSetting=in.readParcelable(com.kalacheng.libuser.model.CustomerServiceSetting.class.getClassLoader());

versionManage=in.readParcelable(com.kalacheng.libuser.model.AdminVersionManage.class.getClassLoader());
imageQuality=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(vcUnit);

dest.writeParcelable(adminLiveConfig,flags);

dest.writeParcelable(keywordManage,flags);

dest.writeParcelable(liveKey,flags);
dest.writeString(wxAppId);

dest.writeTypedList(payConfigList);

dest.writeParcelable(adminPushConfig,flags);

dest.writeParcelable(adminVideoConfig,flags);

dest.writeParcelable(loginSwitch,flags);
dest.writeLong(currTime);

dest.writeParcelable(customerServiceSetting,flags);

dest.writeParcelable(versionManage,flags);
dest.writeInt(imageQuality);

}

  public static void cloneObj(APPConfig source,APPConfig target)
{

target.vcUnit=source.vcUnit;
        if(source.adminLiveConfig==null)
        {
            target.adminLiveConfig=null;
        }else
        {
            AdminLiveConfig.cloneObj(source.adminLiveConfig,target.adminLiveConfig);
        }
        if(source.keywordManage==null)
        {
            target.keywordManage=null;
        }else
        {
            AdminKeywordManage.cloneObj(source.keywordManage,target.keywordManage);
        }
        if(source.liveKey==null)
        {
            target.liveKey=null;
        }else
        {
            CfgLiveKey.cloneObj(source.liveKey,target.liveKey);
        }

target.wxAppId=source.wxAppId;

        if(source.payConfigList==null)
        {
            target.payConfigList=null;
        }else
        {
            target.payConfigList=new ArrayList();
            for(int i=0;i<source.payConfigList.size();i++)
            {
            PayConfigDto.cloneObj(source.payConfigList.get(i),target.payConfigList.get(i));
            }
        }

        if(source.adminPushConfig==null)
        {
            target.adminPushConfig=null;
        }else
        {
            AdminPushConfig.cloneObj(source.adminPushConfig,target.adminPushConfig);
        }
        if(source.adminVideoConfig==null)
        {
            target.adminVideoConfig=null;
        }else
        {
            AdminVideoConfig.cloneObj(source.adminVideoConfig,target.adminVideoConfig);
        }
        if(source.loginSwitch==null)
        {
            target.loginSwitch=null;
        }else
        {
            AdminLoginSwitch.cloneObj(source.loginSwitch,target.loginSwitch);
        }

target.currTime=source.currTime;
        if(source.customerServiceSetting==null)
        {
            target.customerServiceSetting=null;
        }else
        {
            CustomerServiceSetting.cloneObj(source.customerServiceSetting,target.customerServiceSetting);
        }
        if(source.versionManage==null)
        {
            target.versionManage=null;
        }else
        {
            AdminVersionManage.cloneObj(source.versionManage,target.versionManage);
        }

target.imageQuality=source.imageQuality;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<APPConfig> CREATOR = new Creator<APPConfig>() {
        @Override
        public APPConfig createFromParcel(Parcel in) {
            return new APPConfig(in);
        }

        @Override
        public APPConfig[] newArray(int size) {
            return new APPConfig[size];
        }
    };
}
