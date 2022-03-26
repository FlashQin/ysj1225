package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 返回商品属性及属性值组合
 */
public class ShopAttrAndComposeDTO  implements Parcelable
{
 public ShopAttrAndComposeDTO()
{
}

/**
 * 属性值组合
 */
public List<com.kalacheng.busshop.model.ShopAttrCompose> attrComposeList;

/**
 * 属性及属性值
 */
public List<com.kalacheng.busshop.model.ShopGoodsAttrDTO> shopGoodsAttrDTOS;

   public ShopAttrAndComposeDTO(Parcel in) 
{

if(attrComposeList==null){
attrComposeList=  new ArrayList<>();
 }
in.readTypedList(attrComposeList,com.kalacheng.busshop.model.ShopAttrCompose.CREATOR);

if(shopGoodsAttrDTOS==null){
shopGoodsAttrDTOS=  new ArrayList<>();
 }
in.readTypedList(shopGoodsAttrDTOS,com.kalacheng.busshop.model.ShopGoodsAttrDTO.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeTypedList(attrComposeList);

dest.writeTypedList(shopGoodsAttrDTOS);

}

  public static void cloneObj(ShopAttrAndComposeDTO source,ShopAttrAndComposeDTO target)
{

        if(source.attrComposeList==null)
        {
            target.attrComposeList=null;
        }else
        {
            target.attrComposeList=new ArrayList();
            for(int i=0;i<source.attrComposeList.size();i++)
            {
            ShopAttrCompose.cloneObj(source.attrComposeList.get(i),target.attrComposeList.get(i));
            }
        }


        if(source.shopGoodsAttrDTOS==null)
        {
            target.shopGoodsAttrDTOS=null;
        }else
        {
            target.shopGoodsAttrDTOS=new ArrayList();
            for(int i=0;i<source.shopGoodsAttrDTOS.size();i++)
            {
            ShopGoodsAttrDTO.cloneObj(source.shopGoodsAttrDTOS.get(i),target.shopGoodsAttrDTOS.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopAttrAndComposeDTO> CREATOR = new Creator<ShopAttrAndComposeDTO>() {
        @Override
        public ShopAttrAndComposeDTO createFromParcel(Parcel in) {
            return new ShopAttrAndComposeDTO(in);
        }

        @Override
        public ShopAttrAndComposeDTO[] newArray(int size) {
            return new ShopAttrAndComposeDTO[size];
        }
    };
}
