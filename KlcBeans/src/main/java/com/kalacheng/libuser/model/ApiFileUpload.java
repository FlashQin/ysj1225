package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP轩嗵云获取token响应
 */
public class ApiFileUpload  implements Parcelable
{
 public ApiFileUpload()
{
}

/**
 * 区域z0华东,z1华北,z2华南,na0北美,as0东南亚
 */
public String zone;

/**
 * 图片信息
 */
public List<com.kalacheng.libuser.model.ApiQinuResp> filePath;

/**
 * 轩嗵云token
 */
public String token;

   public ApiFileUpload(Parcel in) 
{
zone=in.readString();

if(filePath==null){
filePath=  new ArrayList<>();
 }
in.readTypedList(filePath,com.kalacheng.libuser.model.ApiQinuResp.CREATOR);
token=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(zone);

dest.writeTypedList(filePath);
dest.writeString(token);

}

  public static void cloneObj(ApiFileUpload source,ApiFileUpload target)
{

target.zone=source.zone;

        if(source.filePath==null)
        {
            target.filePath=null;
        }else
        {
            target.filePath=new ArrayList();
            for(int i=0;i<source.filePath.size();i++)
            {
            ApiQinuResp.cloneObj(source.filePath.get(i),target.filePath.get(i));
            }
        }


target.token=source.token;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiFileUpload> CREATOR = new Creator<ApiFileUpload>() {
        @Override
        public ApiFileUpload createFromParcel(Parcel in) {
            return new ApiFileUpload(in);
        }

        @Override
        public ApiFileUpload[] newArray(int size) {
            return new ApiFileUpload[size];
        }
    };
}
