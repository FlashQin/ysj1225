package com.kalacheng.busgame.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busgame.model.*;




/**
 * 中奖数据返回
 */
public class GameUserPrizeDTO  implements Parcelable
{
 public GameUserPrizeDTO()
{
}

/**
 * 抽奖日期
 */
public String luckDrawDate;

/**
 * 游戏名称
 */
public String gameName;

/**
 * 奖品数量
 */
public List<com.kalacheng.busgame.model.GamePrizeRecord> gamePrizeRecordList;

   public GameUserPrizeDTO(Parcel in) 
{
luckDrawDate=in.readString();
gameName=in.readString();

if(gamePrizeRecordList==null){
gamePrizeRecordList=  new ArrayList<>();
 }
in.readTypedList(gamePrizeRecordList,com.kalacheng.busgame.model.GamePrizeRecord.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(luckDrawDate);
dest.writeString(gameName);

dest.writeTypedList(gamePrizeRecordList);

}

  public static void cloneObj(GameUserPrizeDTO source,GameUserPrizeDTO target)
{

target.luckDrawDate=source.luckDrawDate;

target.gameName=source.gameName;

        if(source.gamePrizeRecordList==null)
        {
            target.gamePrizeRecordList=null;
        }else
        {
            target.gamePrizeRecordList=new ArrayList();
            for(int i=0;i<source.gamePrizeRecordList.size();i++)
            {
            GamePrizeRecord.cloneObj(source.gamePrizeRecordList.get(i),target.gamePrizeRecordList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameUserPrizeDTO> CREATOR = new Creator<GameUserPrizeDTO>() {
        @Override
        public GameUserPrizeDTO createFromParcel(Parcel in) {
            return new GameUserPrizeDTO(in);
        }

        @Override
        public GameUserPrizeDTO[] newArray(int size) {
            return new GameUserPrizeDTO[size];
        }
    };
}
