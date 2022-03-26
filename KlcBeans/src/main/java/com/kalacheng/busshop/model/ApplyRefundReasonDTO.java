package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 申请退款原因model
 */
public class ApplyRefundReasonDTO  implements Parcelable
{
 public ApplyRefundReasonDTO()
{
}

/**
 * 退款原因
 */
public String reason;

/**
 * null
 */
public long id;

/**
 * 序号
 */
public int sort;

   public ApplyRefundReasonDTO(Parcel in) 
{
reason=in.readString();
id=in.readLong();
sort=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(reason);
dest.writeLong(id);
dest.writeInt(sort);

}

  public static void cloneObj(ApplyRefundReasonDTO source,ApplyRefundReasonDTO target)
{

target.reason=source.reason;

target.id=source.id;

target.sort=source.sort;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApplyRefundReasonDTO> CREATOR = new Creator<ApplyRefundReasonDTO>() {
        @Override
        public ApplyRefundReasonDTO createFromParcel(Parcel in) {
            return new ApplyRefundReasonDTO(in);
        }

        @Override
        public ApplyRefundReasonDTO[] newArray(int size) {
            return new ApplyRefundReasonDTO[size];
        }
    };
}
