package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP动态短视频
 */
public class ApiUserVideo  implements Parcelable
{
 public ApiUserVideo()
{
}

/**
 * 评论列表
 */
public List<com.kalacheng.libuser.model.ApiUsersVideoComments> commentList;

/**
 * 状态描述
 */
public String msg;

/**
 * 状态1成功2失败
 */
public int code;

/**
 * 是否喜欢 0:未喜欢 1:已喜欢
 */
public int isLike;

/**
 * 城市
 */
public String city;

/**
 * 动态封面图
 */
public String thumb;

/**
 * 是否私密 0:正常 1:私密
 */
public int isPrivate;

/**
 * 动态标题
 */
public String title;

/**
 * 类型 0:只有文字 1:视频动态 2:图片动态）
 */
public int type;

/**
 * 贵族等级图片
 */
public String nobleGradeImg;

/**
 * 发布时间str
 */
public String addtimeStr;

/**
 * 分享数量
 */
public int shares;

/**
 * 用户id
 */
public long uid;

/**
 * 背景音乐id
 */
public int musicId;

/**
 * 视频地址
 */
public String href;

/**
 * null
 */
public int id;

/**
 * 视频时长
 */
public String videoTime;

/**
 * 浏览数量
 */
public int views;

/**
 * 视频高
 */
public int height;

/**
 * 点赞数量
 */
public int likes;

/**
 * 财富等级图片
 */
public String wealthGradeImg;

/**
 * 动态图片（逗号拼接）
 */
public String images;

/**
 * 详细地址
 */
public String address;

/**
 * 评论数量
 */
public int comments;

/**
 * 性别 0:保密 1:男 2:女
 */
public int sex;

/**
 * 用户头像
 */
public String avatar;

/**
 * 是否关注 0:未关注 1:已关注
 */
public int isAtt;

/**
 * 用户名称
 */
public String userName;

/**
 * 踩数量
 */
public int steps;

/**
 * 是否是贵族发的 0:是 1:否
 */
public int isVip;

/**
 * 是否可分享 1:可以 0:不可用
 */
public int isShares;

/**
 * 话题ID
 */
public long topicId;

/**
 * 发布时间
 */
public Date addtime;

/**
 * 视频宽
 */
public int width;

/**
 * 等级图片
 */
public String gradeImg;

/**
 * 话题名
 */
public String topicName;

/**
 * 私密动态资源查看所需金币
 */
public double coin;

   public ApiUserVideo(Parcel in) 
{

if(commentList==null){
commentList=  new ArrayList<>();
 }
in.readTypedList(commentList,com.kalacheng.libuser.model.ApiUsersVideoComments.CREATOR);
msg=in.readString();
code=in.readInt();
isLike=in.readInt();
city=in.readString();
thumb=in.readString();
isPrivate=in.readInt();
title=in.readString();
type=in.readInt();
nobleGradeImg=in.readString();
addtimeStr=in.readString();
shares=in.readInt();
uid=in.readLong();
musicId=in.readInt();
href=in.readString();
id=in.readInt();
videoTime=in.readString();
views=in.readInt();
height=in.readInt();
likes=in.readInt();
wealthGradeImg=in.readString();
images=in.readString();
address=in.readString();
comments=in.readInt();
sex=in.readInt();
avatar=in.readString();
isAtt=in.readInt();
userName=in.readString();
steps=in.readInt();
isVip=in.readInt();
isShares=in.readInt();
topicId=in.readLong();
addtime=new Date( in.readLong());
width=in.readInt();
gradeImg=in.readString();
topicName=in.readString();
coin=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(commentList);
dest.writeString(msg);
dest.writeInt(code);
dest.writeInt(isLike);
dest.writeString(city);
dest.writeString(thumb);
dest.writeInt(isPrivate);
dest.writeString(title);
dest.writeInt(type);
dest.writeString(nobleGradeImg);
dest.writeString(addtimeStr);
dest.writeInt(shares);
dest.writeLong(uid);
dest.writeInt(musicId);
dest.writeString(href);
dest.writeInt(id);
dest.writeString(videoTime);
dest.writeInt(views);
dest.writeInt(height);
dest.writeInt(likes);
dest.writeString(wealthGradeImg);
dest.writeString(images);
dest.writeString(address);
dest.writeInt(comments);
dest.writeInt(sex);
dest.writeString(avatar);
dest.writeInt(isAtt);
dest.writeString(userName);
dest.writeInt(steps);
dest.writeInt(isVip);
dest.writeInt(isShares);
dest.writeLong(topicId);
dest.writeLong(addtime==null?0:addtime.getTime());
dest.writeInt(width);
dest.writeString(gradeImg);
dest.writeString(topicName);
dest.writeDouble(coin);

}

  public static void cloneObj(ApiUserVideo source,ApiUserVideo target)
{

        if(source.commentList==null)
        {
            target.commentList=null;
        }else
        {
            target.commentList=new ArrayList();
            for(int i=0;i<source.commentList.size();i++)
            {
            ApiUsersVideoComments.cloneObj(source.commentList.get(i),target.commentList.get(i));
            }
        }


target.msg=source.msg;

target.code=source.code;

target.isLike=source.isLike;

target.city=source.city;

target.thumb=source.thumb;

target.isPrivate=source.isPrivate;

target.title=source.title;

target.type=source.type;

target.nobleGradeImg=source.nobleGradeImg;

target.addtimeStr=source.addtimeStr;

target.shares=source.shares;

target.uid=source.uid;

target.musicId=source.musicId;

target.href=source.href;

target.id=source.id;

target.videoTime=source.videoTime;

target.views=source.views;

target.height=source.height;

target.likes=source.likes;

target.wealthGradeImg=source.wealthGradeImg;

target.images=source.images;

target.address=source.address;

target.comments=source.comments;

target.sex=source.sex;

target.avatar=source.avatar;

target.isAtt=source.isAtt;

target.userName=source.userName;

target.steps=source.steps;

target.isVip=source.isVip;

target.isShares=source.isShares;

target.topicId=source.topicId;

target.addtime=source.addtime;

target.width=source.width;

target.gradeImg=source.gradeImg;

target.topicName=source.topicName;

target.coin=source.coin;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUserVideo> CREATOR = new Creator<ApiUserVideo>() {
        @Override
        public ApiUserVideo createFromParcel(Parcel in) {
            return new ApiUserVideo(in);
        }

        @Override
        public ApiUserVideo[] newArray(int size) {
            return new ApiUserVideo[size];
        }
    };
}
