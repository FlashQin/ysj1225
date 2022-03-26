package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 短视频首页对象
 */
public class ApiShortVideoDto  implements Parcelable
{
 public ApiShortVideoDto()
{
}

/**
 * 推荐 1:推荐 0:不推荐
 */
public int isRecommend;

/**
 * 付费次数
 */
public int fees;

/**
 * 分类id(多个逗号隔开)
 */
public String classifyId;

/**
 * 类型 1:视频 2:图片
 */
public int type;

/**
 * 商品名称
 */
public String productName;

/**
 * 数量(列表记录广告间隔次数)
 */
public int number;

/**
 * 观看次数
 */
public int looks;

/**
 * 本周必看 0:是 1:否
 */
public int isWeek;

/**
 * null
 */
public long id;

/**
 * 视频时长(单位秒)
 */
public int videoTime;

/**
 * 分类名称(多个逗号隔开)
 */
public String classifyName;

/**
 * 纬度
 */
public double lat;

/**
 * 封面图高
 */
public int height;

/**
 * 点赞数量
 */
public int likes;

/**
 * 按钮名称
 */
public String adsText;

/**
 * 最低需要特权名称
 */
public String privilegesLowestName;

/**
 * 图片（逗号拼接）
 */
public String images;

/**
 * 广告地址
 */
public String adsUrl;

/**
 * 经度
 */
public double lng;

/**
 * 商品id
 */
public long productId;

/**
 * 短视频试看时间(秒)
 */
public int shortVideoTrialTime;

/**
 * 私密动态需要的金币
 */
public double coin;

/**
 * 视频状态 0:未审核 1:通过 2:拒绝
 */
public int status;

/**
 * 是否虚拟 1:是 0:不是
 */
public int virtualOrNot;

/**
 * 是否是主播 1:主播 0:用户
 */
public int role;

/**
 * 是否点赞 1:点赞 0:未点赞
 */
public int isLike;

/**
 * 城市
 */
public String city;

/**
 * 封面图
 */
public String thumb;

/**
 * 广告标题
 */
public String adsTitle;

/**
 * 是否私密 0:正常 1:私密
 */
public int isPrivate;

/**
 * 文字内容
 */
public String content;

/**
 * 视频地址
 */
public String videoUrl;

/**
 * 广告类型 0:无 1:用户广告 2:平台广告
 */
public int adsType;

/**
 * 是否删除 1:删除 0:未删除
 */
public int isdel;

/**
 * 图片5
 */
public String image5;

/**
 * 图片6
 */
public String image6;

/**
 * 图片3）
 */
public String image3;

/**
 * 详细地址
 */
public String address;

/**
 * 评论数量
 */
public int comments;

/**
 * 图片4
 */
public String image4;

/**
 * 是否支付 1:已支付 0:未支付
 */
public int isPay;

/**
 * 用户头像
 */
public String avatar;

/**
 * 图片1
 */
public String image1;

/**
 * 图片2
 */
public String image2;

/**
 * 用户id
 */
public long userId;

/**
 * 是否关注 1:关注 0:未关注
 */
public int isFollow;

/**
 * 发布时间
 */
public Date addtime;

/**
 * 封面图宽
 */
public int width;

/**
 * 用户名
 */
public String username;

   public ApiShortVideoDto(Parcel in) 
{
isRecommend=in.readInt();
fees=in.readInt();
classifyId=in.readString();
type=in.readInt();
productName=in.readString();
number=in.readInt();
looks=in.readInt();
isWeek=in.readInt();
id=in.readLong();
videoTime=in.readInt();
classifyName=in.readString();
lat=in.readDouble();
height=in.readInt();
likes=in.readInt();
adsText=in.readString();
privilegesLowestName=in.readString();
images=in.readString();
adsUrl=in.readString();
lng=in.readDouble();
productId=in.readLong();
shortVideoTrialTime=in.readInt();
coin=in.readDouble();
status=in.readInt();
virtualOrNot=in.readInt();
role=in.readInt();
isLike=in.readInt();
city=in.readString();
thumb=in.readString();
adsTitle=in.readString();
isPrivate=in.readInt();
content=in.readString();
videoUrl=in.readString();
adsType=in.readInt();
isdel=in.readInt();
image5=in.readString();
image6=in.readString();
image3=in.readString();
address=in.readString();
comments=in.readInt();
image4=in.readString();
isPay=in.readInt();
avatar=in.readString();
image1=in.readString();
image2=in.readString();
userId=in.readLong();
isFollow=in.readInt();
addtime=new Date( in.readLong());
width=in.readInt();
username=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(isRecommend);
dest.writeInt(fees);
dest.writeString(classifyId);
dest.writeInt(type);
dest.writeString(productName);
dest.writeInt(number);
dest.writeInt(looks);
dest.writeInt(isWeek);
dest.writeLong(id);
dest.writeInt(videoTime);
dest.writeString(classifyName);
dest.writeDouble(lat);
dest.writeInt(height);
dest.writeInt(likes);
dest.writeString(adsText);
dest.writeString(privilegesLowestName);
dest.writeString(images);
dest.writeString(adsUrl);
dest.writeDouble(lng);
dest.writeLong(productId);
dest.writeInt(shortVideoTrialTime);
dest.writeDouble(coin);
dest.writeInt(status);
dest.writeInt(virtualOrNot);
dest.writeInt(role);
dest.writeInt(isLike);
dest.writeString(city);
dest.writeString(thumb);
dest.writeString(adsTitle);
dest.writeInt(isPrivate);
dest.writeString(content);
dest.writeString(videoUrl);
dest.writeInt(adsType);
dest.writeInt(isdel);
dest.writeString(image5);
dest.writeString(image6);
dest.writeString(image3);
dest.writeString(address);
dest.writeInt(comments);
dest.writeString(image4);
dest.writeInt(isPay);
dest.writeString(avatar);
dest.writeString(image1);
dest.writeString(image2);
dest.writeLong(userId);
dest.writeInt(isFollow);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeInt(width);
dest.writeString(username);

}

  public static void cloneObj(ApiShortVideoDto source,ApiShortVideoDto target)
{

target.isRecommend=source.isRecommend;

target.fees=source.fees;

target.classifyId=source.classifyId;

target.type=source.type;

target.productName=source.productName;

target.number=source.number;

target.looks=source.looks;

target.isWeek=source.isWeek;

target.id=source.id;

target.videoTime=source.videoTime;

target.classifyName=source.classifyName;

target.lat=source.lat;

target.height=source.height;

target.likes=source.likes;

target.adsText=source.adsText;

target.privilegesLowestName=source.privilegesLowestName;

target.images=source.images;

target.adsUrl=source.adsUrl;

target.lng=source.lng;

target.productId=source.productId;

target.shortVideoTrialTime=source.shortVideoTrialTime;

target.coin=source.coin;

target.status=source.status;

target.virtualOrNot=source.virtualOrNot;

target.role=source.role;

target.isLike=source.isLike;

target.city=source.city;

target.thumb=source.thumb;

target.adsTitle=source.adsTitle;

target.isPrivate=source.isPrivate;

target.content=source.content;

target.videoUrl=source.videoUrl;

target.adsType=source.adsType;

target.isdel=source.isdel;

target.image5=source.image5;

target.image6=source.image6;

target.image3=source.image3;

target.address=source.address;

target.comments=source.comments;

target.image4=source.image4;

target.isPay=source.isPay;

target.avatar=source.avatar;

target.image1=source.image1;

target.image2=source.image2;

target.userId=source.userId;

target.isFollow=source.isFollow;

target.addtime=source.addtime;

target.width=source.width;

target.username=source.username;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiShortVideoDto> CREATOR = new Creator<ApiShortVideoDto>() {
        @Override
        public ApiShortVideoDto createFromParcel(Parcel in) {
            return new ApiShortVideoDto(in);
        }

        @Override
        public ApiShortVideoDto[] newArray(int size) {
            return new ApiShortVideoDto[size];
        }
    };
}
