package com.kalacheng.util.utils;


import java.util.ArrayList;
import java.util.List;

import cn.jmessage.support.google.gson.Gson;
import cn.jmessage.support.google.gson.JsonArray;
import cn.jmessage.support.google.gson.JsonElement;
import cn.jmessage.support.google.gson.JsonParser;
import cn.jmessage.support.google.gson.reflect.TypeToken;

/**
 * json 转换工具类
 */
public class JsonUtil {

    // 将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
//        String s = gson.toJson(jsonData);
//        L.e("qpf", "jsonData -- > " + jsonData);
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    //普通bean转list
    public static <T> List<T> objBeanToList(String obj, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        Gson gson = new Gson();
//        String jsonStr = gson.toJson(obj);

        JsonArray jsonArray = jsonParser.parse(obj).getAsJsonArray();

        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            T bean = gson.fromJson(user, clazz);
            list.add(bean);
        }
        return list;
    }

    //Object 转 String
    public static String toJson(Object jsonData) {
        Gson gson = new Gson();
        return gson.toJson(jsonData);
    }

}
