package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP用户中心接口响应
 */
public class ApiUserIndexResp  implements Parcelable
{
 public ApiUserIndexResp()
{
}

/**
 * 生日
 */
public String birthday;

/**
 * 8服务热线等2
 */
public List<com.kalacheng.libuser.model.ApiUserIndexNode> userlist;

/**
 * 地址
 */
public String address;

/**
 * 签名
 */
public String signature;

/**
 * 用户名称
 */
public String user_name;

/**
 * 性别；0：保密，1：男；2：女
 */
public int sex;

/**
 * 用户头像
 */
public String avatar;

/**
 * 6设置等
 */
public List<com.kalacheng.libuser.model.ApiUserIndexNode> setList;

/**
 * 用户id
 */
public long userId;

/**
 * 主播等级
 */
public String anchor_level;

/**
 * 用户等级
 */
public String user_level;

/**
 * 动态
 */
public List<com.kalacheng.libuser.model.ApiUserIndexNode> infoList;

/**
 * 封面图
 */
public String live_thumb;

   public ApiUserIndexResp(Parcel in) 
{
birthday=in.readString();

if(userlist==null){
userlist=  new ArrayList<>();
 }
in.readTypedList(userlist,com.kalacheng.libuser.model.ApiUserIndexNode.CREATOR);
address=in.readString();
signature=in.readString();
user_name=in.readString();
sex=in.readInt();
avatar=in.readString();

if(setList==null){
setList=  new ArrayList<>();
 }
in.readTypedList(setList,com.kalacheng.libuser.model.ApiUserIndexNode.CREATOR);
userId=in.readLong();
anchor_level=in.readString();
user_level=in.readString();

if(infoList==null){
infoList=  new ArrayList<>();
 }
in.readTypedList(infoList,com.kalacheng.libuser.model.ApiUserIndexNode.CREATOR);
live_thumb=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(birthday);

dest.writeTypedList(userlist);
dest.writeString(address);
dest.writeString(signature);
dest.writeString(user_name);
dest.writeInt(sex);
dest.writeString(avatar);

dest.writeTypedList(setList);
dest.writeLong(userId);
dest.writeString(anchor_level);
dest.writeString(user_level);

dest.writeTypedList(infoList);
dest.writeString(live_thumb);

}

  public static void cloneObj(ApiUserIndexResp source,ApiUserIndexResp target)
{

target.birthday=source.birthday;

        if(source.userlist==null)
        {
            target.userlist=null;
        }else
        {
            target.userlist=new ArrayList();
            for(int i=0;i<source.userlist.size();i++)
            {
            ApiUserIndexNode.cloneObj(source.userlist.get(i),target.userlist.get(i));
            }
        }


target.address=source.address;

target.signature=source.signature;

target.user_name=source.user_name;

target.sex=source.sex;

target.avatar=source.avatar;

        if(source.setList==null)
        {
            target.setList=null;
        }else
        {
            target.setList=new ArrayList();
            for(int i=0;i<source.setList.size();i++)
            {
            ApiUserIndexNode.cloneObj(source.setList.get(i),target.setList.get(i));
            }
        }


target.userId=source.userId;

target.anchor_level=source.anchor_level;

target.user_level=source.user_level;

        if(source.infoList==null)
        {
            target.infoList=null;
        }else
        {
            target.infoList=new ArrayList();
            for(int i=0;i<source.infoList.size();i++)
            {
            ApiUserIndexNode.cloneObj(source.infoList.get(i),target.infoList.get(i));
            }
        }


target.live_thumb=source.live_thumb;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUserIndexResp> CREATOR = new Creator<ApiUserIndexResp>() {
        @Override
        public ApiUserIndexResp createFromParcel(Parcel in) {
            return new ApiUserIndexResp(in);
        }

        @Override
        public ApiUserIndexResp[] newArray(int size) {
            return new ApiUserIndexResp[size];
        }
    };
}
