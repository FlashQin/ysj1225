package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP连线时弹窗
 */
public class ApiLineBeforeOOO  implements Parcelable
{
 public ApiLineBeforeOOO()
{
}

/**
 * 弹出费用值
 */
public String typeVal;

/**
 * 是否需要弹出弹窗1需要2不需要
 */
public int isPopup;

/**
 * 弹出内容
 */
public String typeDec;

   public ApiLineBeforeOOO(Parcel in) 
{
typeVal=in.readString();
isPopup=in.readInt();
typeDec=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(typeVal);
dest.writeInt(isPopup);
dest.writeString(typeDec);

}

  public static void cloneObj(ApiLineBeforeOOO source,ApiLineBeforeOOO target)
{

target.typeVal=source.typeVal;

target.isPopup=source.isPopup;

target.typeDec=source.typeDec;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiLineBeforeOOO> CREATOR = new Creator<ApiLineBeforeOOO>() {
        @Override
        public ApiLineBeforeOOO createFromParcel(Parcel in) {
            return new ApiLineBeforeOOO(in);
        }

        @Override
        public ApiLineBeforeOOO[] newArray(int size) {
            return new ApiLineBeforeOOO[size];
        }
    };
}
