package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 等级升级大礼包
 */
public class ApiGradeReWarRe  implements Parcelable
{
 public ApiGradeReWarRe()
{
}

/**
 * 下一等级总经验值
 */
public int nextLevelTotalEmpirical;

/**
 * 下一等级还需经验值
 */
public int nextLevelEmpirical;

/**
 * 礼包列表
 */
public List<com.kalacheng.libuser.model.ApiGradeReList> apiGradeReList;

/**
 * 下一等级
 */
public int nextLevel;

/**
 * 当前等级
 */
public int currLevel;

   public ApiGradeReWarRe(Parcel in) 
{
nextLevelTotalEmpirical=in.readInt();
nextLevelEmpirical=in.readInt();

if(apiGradeReList==null){
apiGradeReList=  new ArrayList<>();
 }
in.readTypedList(apiGradeReList,com.kalacheng.libuser.model.ApiGradeReList.CREATOR);
nextLevel=in.readInt();
currLevel=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(nextLevelTotalEmpirical);
dest.writeInt(nextLevelEmpirical);

dest.writeTypedList(apiGradeReList);
dest.writeInt(nextLevel);
dest.writeInt(currLevel);

}

  public static void cloneObj(ApiGradeReWarRe source,ApiGradeReWarRe target)
{

target.nextLevelTotalEmpirical=source.nextLevelTotalEmpirical;

target.nextLevelEmpirical=source.nextLevelEmpirical;

        if(source.apiGradeReList==null)
        {
            target.apiGradeReList=null;
        }else
        {
            target.apiGradeReList=new ArrayList();
            for(int i=0;i<source.apiGradeReList.size();i++)
            {
            ApiGradeReList.cloneObj(source.apiGradeReList.get(i),target.apiGradeReList.get(i));
            }
        }


target.nextLevel=source.nextLevel;

target.currLevel=source.currLevel;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiGradeReWarRe> CREATOR = new Creator<ApiGradeReWarRe>() {
        @Override
        public ApiGradeReWarRe createFromParcel(Parcel in) {
            return new ApiGradeReWarRe(in);
        }

        @Override
        public ApiGradeReWarRe[] newArray(int size) {
            return new ApiGradeReWarRe[size];
        }
    };
}
