package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 商家主页返回
 */
public class ShopBusinessDTO  implements Parcelable
{
 public ShopBusinessDTO()
{
}

/**
 * 商家
 */
public com.kalacheng.busshop.model.ShopBusiness business;

/**
 * 资源类型
 */
public int sourceType;

/**
 * 直播信息
 */
public com.kalacheng.busshop.model.ShopLiveDetailDTO liveDetailDTO;

/**
 * 商品列表
 */
public List<com.kalacheng.busshop.model.ShopGoodsDTO> goodsDTOList;

/**
 * 房间id
 */
public long roomId;

/**
 * 直播状态0,关播中1,开播中
 */
public int status;

   public ShopBusinessDTO(Parcel in) 
{

business=in.readParcelable(com.kalacheng.busshop.model.ShopBusiness.class.getClassLoader());
sourceType=in.readInt();

liveDetailDTO=in.readParcelable(com.kalacheng.busshop.model.ShopLiveDetailDTO.class.getClassLoader());

if(goodsDTOList==null){
goodsDTOList=  new ArrayList<>();
 }
in.readTypedList(goodsDTOList,com.kalacheng.busshop.model.ShopGoodsDTO.CREATOR);
roomId=in.readLong();
status=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeParcelable(business,flags);
dest.writeInt(sourceType);

dest.writeParcelable(liveDetailDTO,flags);

dest.writeTypedList(goodsDTOList);
dest.writeLong(roomId);
dest.writeInt(status);

}

  public static void cloneObj(ShopBusinessDTO source,ShopBusinessDTO target)
{
        if(source.business==null)
        {
            target.business=null;
        }else
        {
            ShopBusiness.cloneObj(source.business,target.business);
        }

target.sourceType=source.sourceType;
        if(source.liveDetailDTO==null)
        {
            target.liveDetailDTO=null;
        }else
        {
            ShopLiveDetailDTO.cloneObj(source.liveDetailDTO,target.liveDetailDTO);
        }

        if(source.goodsDTOList==null)
        {
            target.goodsDTOList=null;
        }else
        {
            target.goodsDTOList=new ArrayList();
            for(int i=0;i<source.goodsDTOList.size();i++)
            {
            ShopGoodsDTO.cloneObj(source.goodsDTOList.get(i),target.goodsDTOList.get(i));
            }
        }


target.roomId=source.roomId;

target.status=source.status;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopBusinessDTO> CREATOR = new Creator<ShopBusinessDTO>() {
        @Override
        public ShopBusinessDTO createFromParcel(Parcel in) {
            return new ShopBusinessDTO(in);
        }

        @Override
        public ShopBusinessDTO[] newArray(int size) {
            return new ShopBusinessDTO[size];
        }
    };
}
