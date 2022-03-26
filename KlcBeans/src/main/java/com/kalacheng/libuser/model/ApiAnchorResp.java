package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP主播信息接口
 */
public class ApiAnchorResp  implements Parcelable
{
 public ApiAnchorResp()
{
}

/**
 * 生日
 */
public String birthday;

/**
 * 签名
 */
public String signature;

/**
 * 用户名称
 */
public String user_name;

/**
 * 房间类型0是一般直播，1是私密直播，2是收费直播，3是计时直播
 */
public int type;

/**
 * 房间id
 */
public long roomId;

/**
 * 房间类型文字弹框描述
 */
public String typeDec;

/**
 * 当前用户跟直播间的关系1当前直播间主播2管理员3普通用户
 */
public int relation;

/**
 * 主播等级
 */
public String anchor_level;

/**
 * 对方用户跟直播间的关系:1当前直播间主播;2管理员;3普通用户
 */
public int toRelation;

/**
 * 封面图
 */
public String live_thumb;

/**
 * 房间类型值
 */
public String typeVal;

/**
 * 直播状态 1直播中0关播
 */
public int islive;

/**
 * 地址
 */
public String address;

/**
 * 门票房间是否需要付费0不需要付费1需要付费
 */
public int isPay;

/**
 * 性别；0：保密，1：男；2：女
 */
public int sex;

/**
 * 送出娱乐币
 */
public String consumption;

/**
 * 用户头像
 */
public String avatar;

/**
 * 动态数
 */
public int dynamicNumber;

/**
 * 关注数
 */
public int followNumber;

/**
 * 主播id
 */
public long anchorId;

/**
 * 用户id
 */
public long userId;

/**
 * 关注粉丝动态
 */
public List<com.kalacheng.libuser.model.ApiUserIndexNode> list1;

/**
 * 是否关注1已关注0未关注
 */
public int isFollow;

/**
 * 流地址
 */
public String pull;

/**
 * 粉丝数
 */
public int fansNumber;

/**
 * 是否拉黑 1已拉给0未拉黑
 */
public int isBlack;

/**
 * 用户等级
 */
public String user_level;

   public ApiAnchorResp(Parcel in) 
{
birthday=in.readString();
signature=in.readString();
user_name=in.readString();
type=in.readInt();
roomId=in.readLong();
typeDec=in.readString();
relation=in.readInt();
anchor_level=in.readString();
toRelation=in.readInt();
live_thumb=in.readString();
typeVal=in.readString();
islive=in.readInt();
address=in.readString();
isPay=in.readInt();
sex=in.readInt();
consumption=in.readString();
avatar=in.readString();
dynamicNumber=in.readInt();
followNumber=in.readInt();
anchorId=in.readLong();
userId=in.readLong();

if(list1==null){
list1=  new ArrayList<>();
 }
in.readTypedList(list1,com.kalacheng.libuser.model.ApiUserIndexNode.CREATOR);
isFollow=in.readInt();
pull=in.readString();
fansNumber=in.readInt();
isBlack=in.readInt();
user_level=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(birthday);
dest.writeString(signature);
dest.writeString(user_name);
dest.writeInt(type);
dest.writeLong(roomId);
dest.writeString(typeDec);
dest.writeInt(relation);
dest.writeString(anchor_level);
dest.writeInt(toRelation);
dest.writeString(live_thumb);
dest.writeString(typeVal);
dest.writeInt(islive);
dest.writeString(address);
dest.writeInt(isPay);
dest.writeInt(sex);
dest.writeString(consumption);
dest.writeString(avatar);
dest.writeInt(dynamicNumber);
dest.writeInt(followNumber);
dest.writeLong(anchorId);
dest.writeLong(userId);

dest.writeTypedList(list1);
dest.writeInt(isFollow);
dest.writeString(pull);
dest.writeInt(fansNumber);
dest.writeInt(isBlack);
dest.writeString(user_level);

}

  public static void cloneObj(ApiAnchorResp source,ApiAnchorResp target)
{

target.birthday=source.birthday;

target.signature=source.signature;

target.user_name=source.user_name;

target.type=source.type;

target.roomId=source.roomId;

target.typeDec=source.typeDec;

target.relation=source.relation;

target.anchor_level=source.anchor_level;

target.toRelation=source.toRelation;

target.live_thumb=source.live_thumb;

target.typeVal=source.typeVal;

target.islive=source.islive;

target.address=source.address;

target.isPay=source.isPay;

target.sex=source.sex;

target.consumption=source.consumption;

target.avatar=source.avatar;

target.dynamicNumber=source.dynamicNumber;

target.followNumber=source.followNumber;

target.anchorId=source.anchorId;

target.userId=source.userId;

        if(source.list1==null)
        {
            target.list1=null;
        }else
        {
            target.list1=new ArrayList();
            for(int i=0;i<source.list1.size();i++)
            {
            ApiUserIndexNode.cloneObj(source.list1.get(i),target.list1.get(i));
            }
        }


target.isFollow=source.isFollow;

target.pull=source.pull;

target.fansNumber=source.fansNumber;

target.isBlack=source.isBlack;

target.user_level=source.user_level;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiAnchorResp> CREATOR = new Creator<ApiAnchorResp>() {
        @Override
        public ApiAnchorResp createFromParcel(Parcel in) {
            return new ApiAnchorResp(in);
        }

        @Override
        public ApiAnchorResp[] newArray(int size) {
            return new ApiAnchorResp[size];
        }
    };
}
