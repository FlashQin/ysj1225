package com.kalacheng.util.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author: Administrator
 * @date: 2020/8/28
 * @info:
 */
public class getTypeFaceUtil {

    public static Typeface getArialBlackRegular(Context context){
        Typeface mtypeface= Typeface.createFromAsset(context.getAssets(),"com.kalacheng.util.assets.ArialBlackRegular.ttf");
        return mtypeface;
    }

    public static Typeface getYouSheBiaoTiHei(Context context){
        Typeface mtypeface= Typeface.createFromAsset(context.getAssets(),"com.kalacheng.util.assets.YouSheBiaoTiHei.ttf");
        return mtypeface;
    }

}