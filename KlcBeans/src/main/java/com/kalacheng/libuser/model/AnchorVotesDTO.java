package com.kalacheng.libuser.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;




/**
 * 主播可提现余额 model
 */
public class AnchorVotesDTO  implements Parcelable
{
 public AnchorVotesDTO()
{
}

/**
 * 主播比例（有公会优先显示主播公会比例，否则显示主播平台比例）
 */
public double anchorPerc;

/**
 * 主播可提现余额
 */
public double anchorVotes;

   public AnchorVotesDTO(Parcel in) 
{
anchorPerc=in.readDouble();
anchorVotes=in.readDouble();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeDouble(anchorPerc);
dest.writeDouble(anchorVotes);

}

  public static void cloneObj(AnchorVotesDTO source,AnchorVotesDTO target)
{

target.anchorPerc=source.anchorPerc;

target.anchorVotes=source.anchorVotes;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnchorVotesDTO> CREATOR = new Creator<AnchorVotesDTO>() {
        @Override
        public AnchorVotesDTO createFromParcel(Parcel in) {
            return new AnchorVotesDTO(in);
        }

        @Override
        public AnchorVotesDTO[] newArray(int size) {
            return new AnchorVotesDTO[size];
        }
    };
}
