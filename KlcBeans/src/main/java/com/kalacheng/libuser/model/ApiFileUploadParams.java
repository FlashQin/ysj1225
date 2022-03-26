package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * APP轩嗵云获取token请求参数
 */
public class ApiFileUploadParams  implements Parcelable
{
 public ApiFileUploadParams()
{
}

/**
 * 图片使用场景 1:用户图片 2:动态圈图片 3:动态圈视频 4:用户视频 :5用户语音
 */
public int scene;

   public ApiFileUploadParams(Parcel in) 
{
scene=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(scene);

}

  public static void cloneObj(ApiFileUploadParams source,ApiFileUploadParams target)
{

target.scene=source.scene;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiFileUploadParams> CREATOR = new Creator<ApiFileUploadParams>() {
        @Override
        public ApiFileUploadParams createFromParcel(Parcel in) {
            return new ApiFileUploadParams(in);
        }

        @Override
        public ApiFileUploadParams[] newArray(int size) {
            return new ApiFileUploadParams[size];
        }
    };
}
