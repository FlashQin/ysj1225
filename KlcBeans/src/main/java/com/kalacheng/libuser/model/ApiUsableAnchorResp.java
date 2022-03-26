package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP获取可互动主播响应
 */
public class ApiUsableAnchorResp  implements Parcelable
{
 public ApiUsableAnchorResp()
{
}

/**
 * 是否可互动 1:可互动 0:不可互动
 */
public int ismic;

/**
 * 等级
 */
public String level;

/**
 * 流名
 */
public String stream;

/**
 * 用户id
 */
public long user_id;

/**
 * 用户性别 0:保密 1:男 2:女
 */
public int sex;

/**
 * 用户头像
 */
public String avatar_thumb;

/**
 * 用户昵称
 */
public String nickname;

/**
 * 直播id
 */
public long id;

   public ApiUsableAnchorResp(Parcel in) 
{
ismic=in.readInt();
level=in.readString();
stream=in.readString();
user_id=in.readLong();
sex=in.readInt();
avatar_thumb=in.readString();
nickname=in.readString();
id=in.readLong();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(ismic);
dest.writeString(level);
dest.writeString(stream);
dest.writeLong(user_id);
dest.writeInt(sex);
dest.writeString(avatar_thumb);
dest.writeString(nickname);
dest.writeLong(id);

}

  public static void cloneObj(ApiUsableAnchorResp source,ApiUsableAnchorResp target)
{

target.ismic=source.ismic;

target.level=source.level;

target.stream=source.stream;

target.user_id=source.user_id;

target.sex=source.sex;

target.avatar_thumb=source.avatar_thumb;

target.nickname=source.nickname;

target.id=source.id;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUsableAnchorResp> CREATOR = new Creator<ApiUsableAnchorResp>() {
        @Override
        public ApiUsableAnchorResp createFromParcel(Parcel in) {
            return new ApiUsableAnchorResp(in);
        }

        @Override
        public ApiUsableAnchorResp[] newArray(int size) {
            return new ApiUsableAnchorResp[size];
        }
    };
}
