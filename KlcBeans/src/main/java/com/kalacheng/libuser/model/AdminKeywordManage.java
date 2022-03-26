package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 后台管理-系统配置-关键词屏蔽管理
 */
public class AdminKeywordManage  implements Parcelable
{
 public AdminKeywordManage()
{
}

/**
 * 提现提示
 */
public String cashOutTip;

/**
 * null
 */
public long id;

/**
 * 敏感词汇
 */
public String keyword;

   public AdminKeywordManage(Parcel in) 
{
cashOutTip=in.readString();
id=in.readLong();
keyword=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(cashOutTip);
dest.writeLong(id);
dest.writeString(keyword);

}

  public static void cloneObj(AdminKeywordManage source,AdminKeywordManage target)
{

target.cashOutTip=source.cashOutTip;

target.id=source.id;

target.keyword=source.keyword;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdminKeywordManage> CREATOR = new Creator<AdminKeywordManage>() {
        @Override
        public AdminKeywordManage createFromParcel(Parcel in) {
            return new AdminKeywordManage(in);
        }

        @Override
        public AdminKeywordManage[] newArray(int size) {
            return new AdminKeywordManage[size];
        }
    };
}
