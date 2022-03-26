package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 商品属性返回
 */
public class ShopGoodsAttrDTO  implements Parcelable
{
 public ShopGoodsAttrDTO()
{
}

/**
 * 商品属性id
 */
public long attrId;

/**
 * 商品属性值
 */
public List<com.kalacheng.busshop.model.ShopAttrValue> attrValueList;

/**
 * 商品属性名称
 */
public String attrName;

   public ShopGoodsAttrDTO(Parcel in) 
{
attrId=in.readLong();

if(attrValueList==null){
attrValueList=  new ArrayList<>();
 }
in.readTypedList(attrValueList,com.kalacheng.busshop.model.ShopAttrValue.CREATOR);
attrName=in.readString();

}

	public void writeToParcel(Parcel dest, int flags)
{
dest.writeLong(attrId);

dest.writeTypedList(attrValueList);
dest.writeString(attrName);

}

  public static void cloneObj(ShopGoodsAttrDTO source,ShopGoodsAttrDTO target)
{

target.attrId=source.attrId;

        if(source.attrValueList==null)
        {
            target.attrValueList=null;
        }else
        {
            target.attrValueList=new ArrayList();
            for(int i=0;i<source.attrValueList.size();i++)
            {
            ShopAttrValue.cloneObj(source.attrValueList.get(i),target.attrValueList.get(i));
            }
        }


target.attrName=source.attrName;

}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopGoodsAttrDTO> CREATOR = new Creator<ShopGoodsAttrDTO>() {
        @Override
        public ShopGoodsAttrDTO createFromParcel(Parcel in) {
            return new ShopGoodsAttrDTO(in);
        }

        @Override
        public ShopGoodsAttrDTO[] newArray(int size) {
            return new ShopGoodsAttrDTO[size];
        }
    };
}
