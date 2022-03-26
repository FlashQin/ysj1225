package com.kalacheng.util.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.kalacheng.util.bean.AddressBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cn.jmessage.support.google.gson.Gson;
import cn.jmessage.support.google.gson.reflect.TypeToken;

public class ReadAssetsJsonUtil {

    public static JSONObject getCityJSONObject() {
        try {
            return new JSONObject(getJson("city.json", ApplicationUtil.getInstance()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<AddressBean> getAddressBeans() {
        Gson gson = new Gson();
        return gson.fromJson(getCityString(), new TypeToken<ArrayList<AddressBean>>() {
        }.getType());
    }

    public static String getCityString() {
        return getJson("city.json", ApplicationUtil.getInstance());
    }

    public static JSONObject getJSONObject(String fileName, Context context) {
        try {
            return new JSONObject(getJson(fileName, context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getJson(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
