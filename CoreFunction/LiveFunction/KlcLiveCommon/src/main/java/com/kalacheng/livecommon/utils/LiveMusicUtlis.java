package com.kalacheng.livecommon.utils;

import com.kalacheng.libuser.model.ApiMusic;
import com.kalacheng.commonview.utils.SexUtlis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LiveMusicUtlis {
    private List<ApiMusic> mList = new ArrayList<>();



    private static volatile LiveMusicUtlis singleton;

    private LiveMusicUtlis() {
    }

    public static LiveMusicUtlis getInstance() {

        if (singleton == null) {
            synchronized (SexUtlis.class) {
                if (singleton == null) {
                    singleton = new LiveMusicUtlis();
                }
            }
        }
        return singleton;
    }


    /*
    * 获取随机数
    * */
    public int getRandom(){


        Random random = new Random();
        int randomPoistion = random.nextInt(mList.size());
        return randomPoistion;
    }


    /*
    * 设置音乐
    * */
    public void setMusicList(List<ApiMusic> data){
        this.mList.clear();
        this.mList = data;

    }


    /*
       h获取音乐
    */
    public  List<ApiMusic> getMusicList(){
        return mList;
    }


    /*
    * 随机获取一个音乐
    * */
    public ApiMusic getRandomMusic(){
        if (mList.size()!=0){
            return mList.get(getRandom());
        }
        return null;
    }
}
