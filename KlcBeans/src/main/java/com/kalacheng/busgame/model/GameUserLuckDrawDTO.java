package com.kalacheng.busgame.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busgame.model.*;




/**
 * 抽奖之后返回
 */
public class GameUserLuckDrawDTO  implements Parcelable
{
 public GameUserLuckDrawDTO()
{
}

/**
 * 用户抽奖次数
 */
public int userGameNum;

/**
 * 中奖记录
 */
public List<com.kalacheng.busgame.model.GamePrizeRecord> gamePrizeRecordList;

/**
 * 抽奖记录
 */
public com.kalacheng.busgame.model.GameLuckDraw gameLuckDraw;

   public GameUserLuckDrawDTO(Parcel in) 
{
userGameNum=in.readInt();

if(gamePrizeRecordList==null){
gamePrizeRecordList=  new ArrayList<>();
 }
in.readTypedList(gamePrizeRecordList,com.kalacheng.busgame.model.GamePrizeRecord.CREATOR);

gameLuckDraw=in.readParcelable(com.kalacheng.busgame.model.GameLuckDraw.class.getClassLoader());

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(userGameNum);

dest.writeTypedList(gamePrizeRecordList);

dest.writeParcelable(gameLuckDraw,flags);

}

  public static void cloneObj(GameUserLuckDrawDTO source,GameUserLuckDrawDTO target)
{

target.userGameNum=source.userGameNum;

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

        if(source.gameLuckDraw==null)
        {
            target.gameLuckDraw=null;
        }else
        {
            GameLuckDraw.cloneObj(source.gameLuckDraw,target.gameLuckDraw);
        }

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameUserLuckDrawDTO> CREATOR = new Creator<GameUserLuckDrawDTO>() {
        @Override
        public GameUserLuckDrawDTO createFromParcel(Parcel in) {
            return new GameUserLuckDrawDTO(in);
        }

        @Override
        public GameUserLuckDrawDTO[] newArray(int size) {
            return new GameUserLuckDrawDTO[size];
        }
    };
}
