package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * APP用户中心接口动态功能实体
 */
public class ApiUserIndexNode  implements Parcelable
{
 public ApiUserIndexNode()
{
}

/**
 * app类型h5/route
 */
public String app_type;

/**
 * app转跳地址
 */
public String app_url;

/**
 * 图标
 */
public String icon;

/**
 * 功能名称
 */
public String name;

/**
 * 副标题
 */
public String remark;

/**
 * id
 */
public int id;

/**
 * 类型 1:横向1 2:横向2 3:竖向
 */
public int type;

/**
 * 列表
 */
public List<com.kalacheng.libuser.model.ApiUserIndexNode> userIndexNodeList;

   public ApiUserIndexNode(Parcel in) 
{
app_type=in.readString();
app_url=in.readString();
icon=in.readString();
name=in.readString();
remark=in.readString();
id=in.readInt();
type=in.readInt();

if(userIndexNodeList==null){
userIndexNodeList=  new ArrayList<>();
 }
in.readTypedList(userIndexNodeList,com.kalacheng.libuser.model.ApiUserIndexNode.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(app_type);
dest.writeString(app_url);
dest.writeString(icon);
dest.writeString(name);
dest.writeString(remark);
dest.writeInt(id);
dest.writeInt(type);

dest.writeTypedList(userIndexNodeList);

}

  public static void cloneObj(ApiUserIndexNode source,ApiUserIndexNode target)
{

target.app_type=source.app_type;

target.app_url=source.app_url;

target.icon=source.icon;

target.name=source.name;

target.remark=source.remark;

target.id=source.id;

target.type=source.type;

        if(source.userIndexNodeList==null)
        {
            target.userIndexNodeList=null;
        }else
        {
            target.userIndexNodeList=new ArrayList();
            for(int i=0;i<source.userIndexNodeList.size();i++)
            {
            ApiUserIndexNode.cloneObj(source.userIndexNodeList.get(i),target.userIndexNodeList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUserIndexNode> CREATOR = new Creator<ApiUserIndexNode>() {
        @Override
        public ApiUserIndexNode createFromParcel(Parcel in) {
            return new ApiUserIndexNode(in);
        }

        @Override
        public ApiUserIndexNode[] newArray(int size) {
            return new ApiUserIndexNode[size];
        }
    };
}
