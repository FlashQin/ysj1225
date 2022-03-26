package com.kalacheng.buschatroom.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.libuser.model.*;




/**
 * 常用语 提示
 */
public class CommonTipsDTO  implements Parcelable
{
 public CommonTipsDTO()
{
}

/**
 * 私聊扣费提示语
 */
public String privateChatDeductionTips;

/**
 * 聊天常用语 集合
 */
public List<com.kalacheng.libuser.model.AppCommonWords> commonWordsList;

   public CommonTipsDTO(Parcel in) 
{
privateChatDeductionTips=in.readString();

if(commonWordsList==null){
commonWordsList=  new ArrayList<>();
 }
in.readTypedList(commonWordsList,com.kalacheng.libuser.model.AppCommonWords.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(privateChatDeductionTips);

dest.writeTypedList(commonWordsList);

}

  public static void cloneObj(CommonTipsDTO source,CommonTipsDTO target)
{

target.privateChatDeductionTips=source.privateChatDeductionTips;

        if(source.commonWordsList==null)
        {
            target.commonWordsList=null;
        }else
        {
            target.commonWordsList=new ArrayList();
            for(int i=0;i<source.commonWordsList.size();i++)
            {
            AppCommonWords.cloneObj(source.commonWordsList.get(i),target.commonWordsList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommonTipsDTO> CREATOR = new Creator<CommonTipsDTO>() {
        @Override
        public CommonTipsDTO createFromParcel(Parcel in) {
            return new CommonTipsDTO(in);
        }

        @Override
        public CommonTipsDTO[] newArray(int size) {
            return new CommonTipsDTO[size];
        }
    };
}
