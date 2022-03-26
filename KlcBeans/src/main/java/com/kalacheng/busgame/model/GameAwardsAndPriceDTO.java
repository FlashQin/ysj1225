package com.kalacheng.busgame.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busgame.model.*;




/**
 * 获取游戏奖项以及收费标准
 */
public class GameAwardsAndPriceDTO  implements Parcelable
{
 public GameAwardsAndPriceDTO()
{
}

/**
 * 幸运宝箱结束时间
 */
public String luckyEndTime;

/**
 * 幸运加成总次数
 */
public int luckyPrizeNum;

/**
 * 特别说明
 */
public String specialNote;

/**
 * 用户抽奖次数
 */
public int userLuckyNum;

/**
 * 游戏奖项
 */
public List<com.kalacheng.busgame.model.GameAwards> gameAwardsList;

/**
 * 幸运宝箱开启时间
 */
public String luckyStartTime;

/**
 * 游戏收费
 */
public List<com.kalacheng.busgame.model.GamePrice> gamePriceList;

   public GameAwardsAndPriceDTO(Parcel in) 
{
luckyEndTime=in.readString();
luckyPrizeNum=in.readInt();
specialNote=in.readString();
userLuckyNum=in.readInt();

if(gameAwardsList==null){
gameAwardsList=  new ArrayList<>();
 }
in.readTypedList(gameAwardsList,com.kalacheng.busgame.model.GameAwards.CREATOR);
luckyStartTime=in.readString();

if(gamePriceList==null){
gamePriceList=  new ArrayList<>();
 }
in.readTypedList(gamePriceList,com.kalacheng.busgame.model.GamePrice.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeString(luckyEndTime);
dest.writeInt(luckyPrizeNum);
dest.writeString(specialNote);
dest.writeInt(userLuckyNum);

dest.writeTypedList(gameAwardsList);
dest.writeString(luckyStartTime);

dest.writeTypedList(gamePriceList);

}

  public static void cloneObj(GameAwardsAndPriceDTO source,GameAwardsAndPriceDTO target)
{

target.luckyEndTime=source.luckyEndTime;

target.luckyPrizeNum=source.luckyPrizeNum;

target.specialNote=source.specialNote;

target.userLuckyNum=source.userLuckyNum;

        if(source.gameAwardsList==null)
        {
            target.gameAwardsList=null;
        }else
        {
            target.gameAwardsList=new ArrayList();
            for(int i=0;i<source.gameAwardsList.size();i++)
            {
            GameAwards.cloneObj(source.gameAwardsList.get(i),target.gameAwardsList.get(i));
            }
        }


target.luckyStartTime=source.luckyStartTime;

        if(source.gamePriceList==null)
        {
            target.gamePriceList=null;
        }else
        {
            target.gamePriceList=new ArrayList();
            for(int i=0;i<source.gamePriceList.size();i++)
            {
            GamePrice.cloneObj(source.gamePriceList.get(i),target.gamePriceList.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameAwardsAndPriceDTO> CREATOR = new Creator<GameAwardsAndPriceDTO>() {
        @Override
        public GameAwardsAndPriceDTO createFromParcel(Parcel in) {
            return new GameAwardsAndPriceDTO(in);
        }

        @Override
        public GameAwardsAndPriceDTO[] newArray(int size) {
            return new GameAwardsAndPriceDTO[size];
        }
    };
}
