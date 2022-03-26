package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理-三方设置-支付配置
 */
public class CfgPayWay  implements Parcelable
{
 public CfgPayWay()
{
}

/**
 * H5AppSecret
 */
public String H5AppSecret;

/**
 * 支付KEY， 秘钥（微信支付需要）
 */
public String apiPayKey;

/**
 * 回调地址
 */
public String callBackUrl;

/**
 * 支付公钥配置
 */
public String publicKey;

/**
 * 排序
 */
public int sort;

/**
 * 是否开启此支付通道 0：开启  1：关闭
 */
public int isTip;

/**
 * h5AppId
 */
public String h5AppId;

/**
 * 支付私钥配置
 */
public String privateKey;

/**
 * 支付页面类型 1：APP支付， 2：web网页支付 3：扫码支付
 */
public int pageType;

/**
 * 商户号（微信，支付宝商户号）
 */
public String partner;

/**
 * appId
 */
public String appId;

/**
 * 支付渠道名称
 */
public String name;

/**
 * 支付渠道LOGO
 */
public String logo;

/**
 * appSecret
 */
public String appSecret;

/**
 * 支付渠道类型 1：支付宝  2：微信 3:ios内购
 */
public int payChannel;

/**
 * null
 */
public long id;

   public CfgPayWay(Parcel in) 
{
H5AppSecret=in.readString();
apiPayKey=in.readString();
callBackUrl=in.readString();
publicKey=in.readString();
sort=in.readInt();
isTip=in.readInt();
h5AppId=in.readString();
privateKey=in.readString();
pageType=in.readInt();
partner=in.readString();
appId=in.readString();
name=in.readString();
logo=in.readString();
appSecret=in.readString();
payChannel=in.readInt();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(H5AppSecret);
dest.writeString(apiPayKey);
dest.writeString(callBackUrl);
dest.writeString(publicKey);
dest.writeInt(sort);
dest.writeInt(isTip);
dest.writeString(h5AppId);
dest.writeString(privateKey);
dest.writeInt(pageType);
dest.writeString(partner);
dest.writeString(appId);
dest.writeString(name);
dest.writeString(logo);
dest.writeString(appSecret);
dest.writeInt(payChannel);
dest.writeLong(id);

}

  public static void cloneObj(CfgPayWay source,CfgPayWay target)
{

target.H5AppSecret=source.H5AppSecret;

target.apiPayKey=source.apiPayKey;

target.callBackUrl=source.callBackUrl;

target.publicKey=source.publicKey;

target.sort=source.sort;

target.isTip=source.isTip;

target.h5AppId=source.h5AppId;

target.privateKey=source.privateKey;

target.pageType=source.pageType;

target.partner=source.partner;

target.appId=source.appId;

target.name=source.name;

target.logo=source.logo;

target.appSecret=source.appSecret;

target.payChannel=source.payChannel;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CfgPayWay> CREATOR = new Creator<CfgPayWay>() {
        @Override
        public CfgPayWay createFromParcel(Parcel in) {
            return new CfgPayWay(in);
        }

        @Override
        public CfgPayWay[] newArray(int size) {
            return new CfgPayWay[size];
        }
    };
}
