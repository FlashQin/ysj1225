package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP轩嗵云响应
 */
public class ApiQinuResp  implements Parcelable
{
 public ApiQinuResp()
{
}

/**
 * 文件名
 */
public String fileName;

/**
 * 轩嗵云域名
 */
public String rootPath;

   public ApiQinuResp(Parcel in) 
{
fileName=in.readString();
rootPath=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(fileName);
dest.writeString(rootPath);

}

  public static void cloneObj(ApiQinuResp source,ApiQinuResp target)
{

target.fileName=source.fileName;

target.rootPath=source.rootPath;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiQinuResp> CREATOR = new Creator<ApiQinuResp>() {
        @Override
        public ApiQinuResp createFromParcel(Parcel in) {
            return new ApiQinuResp(in);
        }

        @Override
        public ApiQinuResp[] newArray(int size) {
            return new ApiQinuResp[size];
        }
    };
}
