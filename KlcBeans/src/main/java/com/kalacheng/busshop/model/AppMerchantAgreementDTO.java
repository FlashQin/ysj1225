package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 协议返回
 */
public class AppMerchantAgreementDTO  implements Parcelable
{
 public AppMerchantAgreementDTO()
{
}

/**
 * post内容
 */
public String postExcerpt;

/**
 * post标题
 */
public String postTitle;

/**
 * 审核备注
 */
public String remake;

/**
 * 状态 0:没有申请 1:审核中 2.审核通过 3.审核拒绝 4.冻结
 */
public int status;

   public AppMerchantAgreementDTO(Parcel in) 
{
postExcerpt=in.readString();
postTitle=in.readString();
remake=in.readString();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(postExcerpt);
dest.writeString(postTitle);
dest.writeString(remake);
dest.writeInt(status);

}

  public static void cloneObj(AppMerchantAgreementDTO source,AppMerchantAgreementDTO target)
{

target.postExcerpt=source.postExcerpt;

target.postTitle=source.postTitle;

target.remake=source.remake;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppMerchantAgreementDTO> CREATOR = new Creator<AppMerchantAgreementDTO>() {
        @Override
        public AppMerchantAgreementDTO createFromParcel(Parcel in) {
            return new AppMerchantAgreementDTO(in);
        }

        @Override
        public AppMerchantAgreementDTO[] newArray(int size) {
            return new AppMerchantAgreementDTO[size];
        }
    };
}
