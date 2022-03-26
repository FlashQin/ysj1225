package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 邀请码
 */
public class InviteDto  implements Parcelable
{
 public InviteDto()
{
}

/**
 * 剩余佣金
 */
public double amount;

/**
 * 提示信息2
 */
public List<com.kalacheng.libuser.model.KeyValueDto> msg2;

/**
 * 标题图片
 */
public String titleImg;

/**
 * 邀请码
 */
public String code;

/**
 * 提示信息1
 */
public String msg1;

/**
 * 微信邀请
 */
public com.kalacheng.libuser.model.KeyValueDto wxInvite;

/**
 * 邀请规则
 */
public String inviteRule;

/**
 * 图片分享
 */
public com.kalacheng.libuser.model.KeyValueDto shareImg;

/**
 * 总收益佣金
 */
public double totalAmount;

/**
 * 背景图
 */
public String backgroundImg;

/**
 * QQ邀请
 */
public com.kalacheng.libuser.model.KeyValueDto qqInvite;

/**
 * 邀请地址
 */
public String inviteUrl;

/**
 * 累计提现佣金
 */
public double totalCash;

   public InviteDto(Parcel in) 
{
amount=in.readDouble();

if(msg2==null){
msg2=  new ArrayList<>();
 }
in.readTypedList(msg2,com.kalacheng.libuser.model.KeyValueDto.CREATOR);
titleImg=in.readString();
code=in.readString();
msg1=in.readString();

wxInvite=in.readParcelable(com.kalacheng.libuser.model.KeyValueDto.class.getClassLoader());
inviteRule=in.readString();

shareImg=in.readParcelable(com.kalacheng.libuser.model.KeyValueDto.class.getClassLoader());
totalAmount=in.readDouble();
backgroundImg=in.readString();

qqInvite=in.readParcelable(com.kalacheng.libuser.model.KeyValueDto.class.getClassLoader());
inviteUrl=in.readString();
totalCash=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(amount);

dest.writeTypedList(msg2);
dest.writeString(titleImg);
dest.writeString(code);
dest.writeString(msg1);

dest.writeParcelable(wxInvite,flags);
dest.writeString(inviteRule);

dest.writeParcelable(shareImg,flags);
dest.writeDouble(totalAmount);
dest.writeString(backgroundImg);

dest.writeParcelable(qqInvite,flags);
dest.writeString(inviteUrl);
dest.writeDouble(totalCash);

}

  public static void cloneObj(InviteDto source,InviteDto target)
{

target.amount=source.amount;

        if(source.msg2==null)
        {
            target.msg2=null;
        }else
        {
            target.msg2=new ArrayList();
            for(int i=0;i<source.msg2.size();i++)
            {
            KeyValueDto.cloneObj(source.msg2.get(i),target.msg2.get(i));
            }
        }


target.titleImg=source.titleImg;

target.code=source.code;

target.msg1=source.msg1;
        if(source.wxInvite==null)
        {
            target.wxInvite=null;
        }else
        {
            KeyValueDto.cloneObj(source.wxInvite,target.wxInvite);
        }

target.inviteRule=source.inviteRule;
        if(source.shareImg==null)
        {
            target.shareImg=null;
        }else
        {
            KeyValueDto.cloneObj(source.shareImg,target.shareImg);
        }

target.totalAmount=source.totalAmount;

target.backgroundImg=source.backgroundImg;
        if(source.qqInvite==null)
        {
            target.qqInvite=null;
        }else
        {
            KeyValueDto.cloneObj(source.qqInvite,target.qqInvite);
        }

target.inviteUrl=source.inviteUrl;

target.totalCash=source.totalCash;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InviteDto> CREATOR = new Creator<InviteDto>() {
        @Override
        public InviteDto createFromParcel(Parcel in) {
            return new InviteDto(in);
        }

        @Override
        public InviteDto[] newArray(int size) {
            return new InviteDto[size];
        }
    };
}
