package com.kalacheng.util.utils;

import android.text.TextUtils;

public class StringShowUtil {


    /**
     * 显示 名字
     */
    public static String showName(String touName) {
        //昵称（5个汉字长度，其它用 { . }）
        String name = "";

        name = showName(touName,true,5);

        return name;
    }

    /**
     * 显示 名字
     */
    public static String showNameAll(String touName) {

        return showName(touName,false,0);
    }

    /**
     * 显示 名字
     * @param isMaxLength 是否 有字长限制 （true 有限制）
     * @param lengthNum 字长限制数
     */
    public static String showName(String touName,boolean isMaxLength,int lengthNum) {
        //昵称（5个汉字长度，其它用 { . }）
        String name = "";

        if (!TextUtils.isEmpty(touName) && isMaxLength == true) {
            if (touName.length() <= lengthNum) {
                name = touName;
            } else {
                name = touName.substring(0, lengthNum) + ".";
            }
        }else {
            name = !TextUtils.isEmpty(touName) ? touName : "";
        }

        return name;
    }
}
