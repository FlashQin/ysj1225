package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;

import com.kalacheng.buscommon.model.*;




/**
 * APP遇见主播端相应
 */
public class OOOMeetAnchor  implements Parcelable
{
 public OOOMeetAnchor()
{
}

/**
 * 未接听用户列表
 */
public List<com.kalacheng.libuser.model.OOOLiveRoomNoAnswerDto> noAnswerUserList;

/**
 * 匹配中的用户列表
 */
public List<com.kalacheng.buscommon.model.ApiUserInfo> matchingUserList;

/**
 * 邀请通话的用户列表
 */
public List<com.kalacheng.buscommon.model.ApiUserInfo> reqUserList;

   public OOOMeetAnchor(Parcel in) 
{

if(noAnswerUserList==null){
noAnswerUserList=  new ArrayList<>();
 }
in.readTypedList(noAnswerUserList,com.kalacheng.libuser.model.OOOLiveRoomNoAnswerDto.CREATOR);

if(matchingUserList==null){
matchingUserList=  new ArrayList<>();
 }
in.readTypedList(matchingUserList,com.kalacheng.buscommon.model.ApiUserInfo.CREATOR);

if(reqUserList==null){
reqUserList=  new ArrayList<>();
 }
in.readTypedList(reqUserList,com.kalacheng.buscommon.model.ApiUserInfo.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(noAnswerUserList);

dest.writeTypedList(matchingUserList);

dest.writeTypedList(reqUserList);

}

  public static void cloneObj(OOOMeetAnchor source,OOOMeetAnchor target)
{

        if(source.noAnswerUserList==null)
        {
            target.noAnswerUserList=null;
        }else
        {
            target.noAnswerUserList=new ArrayList();
            for(int i=0;i<source.noAnswerUserList.size();i++)
            {
            OOOLiveRoomNoAnswerDto.cloneObj(source.noAnswerUserList.get(i),target.noAnswerUserList.get(i));
            }
        }


        if(source.matchingUserList==null)
        {
            target.matchingUserList=null;
        }else
        {
            target.matchingUserList=new ArrayList();
            for(int i=0;i<source.matchingUserList.size();i++)
            {
            ApiUserInfo.cloneObj(source.matchingUserList.get(i),target.matchingUserList.get(i));
            }
        }


        if(source.reqUserList==null)
        {
            target.reqUserList=null;
        }else
        {
            target.reqUserList=new ArrayList();
            for(int i=0;i<source.reqUserList.size();i++)
            {
            ApiUserInfo.cloneObj(source.reqUserList.get(i),target.reqUserList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OOOMeetAnchor> CREATOR = new Creator<OOOMeetAnchor>() {
        @Override
        public OOOMeetAnchor createFromParcel(Parcel in) {
            return new OOOMeetAnchor(in);
        }

        @Override
        public OOOMeetAnchor[] newArray(int size) {
            return new OOOMeetAnchor[size];
        }
    };
}
