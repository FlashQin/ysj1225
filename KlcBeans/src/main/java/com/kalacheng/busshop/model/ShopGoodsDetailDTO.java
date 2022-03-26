package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 商品详情返回
 */
public class ShopGoodsDetailDTO  implements Parcelable
{
 public ShopGoodsDetailDTO()
{
}

/**
 * 累计销量
 */
public int totalSoldNum;

/**
 * 商品对应规格组合
 */
public List<com.kalacheng.busshop.model.ShopAttrCompose> composeList;

/**
 * 商品信息
 */
public com.kalacheng.busshop.model.ShopGoods shopGoods;

/**
 * 商家logo
 */
public String businessLogo;

/**
 * 商家名称
 */
public String businessName;

/**
 * 商家商品列表
 */
public List<com.kalacheng.busshop.model.ShopGoodsDTO> shopGoodsDTOS;

/**
 * 推荐商品列表
 */
public List<com.kalacheng.busshop.model.ShopGoodsDTO> recommendGoodsDTOS;

/**
 * 主播id
 */
public long anchorId;

/**
 * 在售商品数量
 */
public int effectiveGoodsNum;

/**
 * 商品对应属性
 */
public List<com.kalacheng.busshop.model.ShopGoodsAttrDTO> attrDTOList;

/**
 * 购物车数量
 */
public int shopCarNum;

   public ShopGoodsDetailDTO(Parcel in) 
{
totalSoldNum=in.readInt();

if(composeList==null){
composeList=  new ArrayList<>();
 }
in.readTypedList(composeList,com.kalacheng.busshop.model.ShopAttrCompose.CREATOR);

shopGoods=in.readParcelable(com.kalacheng.busshop.model.ShopGoods.class.getClassLoader());
businessLogo=in.readString();
businessName=in.readString();

if(shopGoodsDTOS==null){
shopGoodsDTOS=  new ArrayList<>();
 }
in.readTypedList(shopGoodsDTOS,com.kalacheng.busshop.model.ShopGoodsDTO.CREATOR);

if(recommendGoodsDTOS==null){
recommendGoodsDTOS=  new ArrayList<>();
 }
in.readTypedList(recommendGoodsDTOS,com.kalacheng.busshop.model.ShopGoodsDTO.CREATOR);
anchorId=in.readLong();
effectiveGoodsNum=in.readInt();

if(attrDTOList==null){
attrDTOList=  new ArrayList<>();
 }
in.readTypedList(attrDTOList,com.kalacheng.busshop.model.ShopGoodsAttrDTO.CREATOR);
shopCarNum=in.readInt();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeInt(totalSoldNum);

dest.writeTypedList(composeList);

dest.writeParcelable(shopGoods,flags);
dest.writeString(businessLogo);
dest.writeString(businessName);

dest.writeTypedList(shopGoodsDTOS);

dest.writeTypedList(recommendGoodsDTOS);
dest.writeLong(anchorId);
dest.writeInt(effectiveGoodsNum);

dest.writeTypedList(attrDTOList);
dest.writeInt(shopCarNum);

}

  public static void cloneObj(ShopGoodsDetailDTO source,ShopGoodsDetailDTO target)
{

target.totalSoldNum=source.totalSoldNum;

        if(source.composeList==null)
        {
            target.composeList=null;
        }else
        {
            target.composeList=new ArrayList();
            for(int i=0;i<source.composeList.size();i++)
            {
            ShopAttrCompose.cloneObj(source.composeList.get(i),target.composeList.get(i));
            }
        }

        if(source.shopGoods==null)
        {
            target.shopGoods=null;
        }else
        {
            ShopGoods.cloneObj(source.shopGoods,target.shopGoods);
        }

target.businessLogo=source.businessLogo;

target.businessName=source.businessName;

        if(source.shopGoodsDTOS==null)
        {
            target.shopGoodsDTOS=null;
        }else
        {
            target.shopGoodsDTOS=new ArrayList();
            for(int i=0;i<source.shopGoodsDTOS.size();i++)
            {
            ShopGoodsDTO.cloneObj(source.shopGoodsDTOS.get(i),target.shopGoodsDTOS.get(i));
            }
        }


        if(source.recommendGoodsDTOS==null)
        {
            target.recommendGoodsDTOS=null;
        }else
        {
            target.recommendGoodsDTOS=new ArrayList();
            for(int i=0;i<source.recommendGoodsDTOS.size();i++)
            {
            ShopGoodsDTO.cloneObj(source.recommendGoodsDTOS.get(i),target.recommendGoodsDTOS.get(i));
            }
        }


target.anchorId=source.anchorId;

target.effectiveGoodsNum=source.effectiveGoodsNum;

        if(source.attrDTOList==null)
        {
            target.attrDTOList=null;
        }else
        {
            target.attrDTOList=new ArrayList();
            for(int i=0;i<source.attrDTOList.size();i++)
            {
            ShopGoodsAttrDTO.cloneObj(source.attrDTOList.get(i),target.attrDTOList.get(i));
            }
        }


target.shopCarNum=source.shopCarNum;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopGoodsDetailDTO> CREATOR = new Creator<ShopGoodsDetailDTO>() {
        @Override
        public ShopGoodsDetailDTO createFromParcel(Parcel in) {
            return new ShopGoodsDetailDTO(in);
        }

        @Override
        public ShopGoodsDetailDTO[] newArray(int size) {
            return new ShopGoodsDetailDTO[size];
        }
    };
}
