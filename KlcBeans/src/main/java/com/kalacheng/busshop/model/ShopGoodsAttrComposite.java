package com.kalacheng.busshop.model;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

import com.kalacheng.busshop.model.*;




/**
 * 商品属性属性值组合实体
 */
public class ShopGoodsAttrComposite  implements Parcelable
{
 public ShopGoodsAttrComposite()
{
}

/**
 * 商品属性
 */
public com.kalacheng.busshop.model.ShopGoodsAttr shopGoodsAttr;

/**
 * 属性值
 */
public List<com.kalacheng.busshop.model.ShopAttrValue> shopAttrValues;

   public ShopGoodsAttrComposite(Parcel in) 
{

shopGoodsAttr=in.readParcelable(com.kalacheng.busshop.model.ShopGoodsAttr.class.getClassLoader());

if(shopAttrValues==null){
shopAttrValues=  new ArrayList<>();
 }
in.readTypedList(shopAttrValues,com.kalacheng.busshop.model.ShopAttrValue.CREATOR);

}

	public void writeToParcel(Parcel dest, int flags)
{

dest.writeParcelable(shopGoodsAttr,flags);

dest.writeTypedList(shopAttrValues);

}

  public static void cloneObj(ShopGoodsAttrComposite source,ShopGoodsAttrComposite target)
{
        if(source.shopGoodsAttr==null)
        {
            target.shopGoodsAttr=null;
        }else
        {
            ShopGoodsAttr.cloneObj(source.shopGoodsAttr,target.shopGoodsAttr);
        }

        if(source.shopAttrValues==null)
        {
            target.shopAttrValues=null;
        }else
        {
            target.shopAttrValues=new ArrayList();
            for(int i=0;i<source.shopAttrValues.size();i++)
            {
            ShopAttrValue.cloneObj(source.shopAttrValues.get(i),target.shopAttrValues.get(i));
            }
        }


}
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopGoodsAttrComposite> CREATOR = new Creator<ShopGoodsAttrComposite>() {
        @Override
        public ShopGoodsAttrComposite createFromParcel(Parcel in) {
            return new ShopGoodsAttrComposite(in);
        }

        @Override
        public ShopGoodsAttrComposite[] newArray(int size) {
            return new ShopGoodsAttrComposite[size];
        }
    };
}
