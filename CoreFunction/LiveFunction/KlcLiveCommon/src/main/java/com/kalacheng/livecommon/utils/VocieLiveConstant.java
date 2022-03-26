package com.kalacheng.livecommon.utils;


import com.kalacheng.libuser.model.ApiUsersVoiceAssistan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VocieLiveConstant {
    private static volatile VocieLiveConstant singleton;
    private Map<Integer, ApiUsersVoiceAssistan> mMikeList = new HashMap<>();

    private List<ApiUsersVoiceAssistan> mList = new ArrayList<>();

    private VocieLiveConstant() {
    }

    public static VocieLiveConstant getInstance() {

        if (singleton == null) {
            synchronized (VocieLiveConstant.class) {
                if (singleton == null) {
                    singleton = new VocieLiveConstant();
                }
            }
        }
        return singleton;
    }

    //初始化麦序
    public void setVoiceMikeList(List<ApiUsersVoiceAssistan> data) {
        this.mMikeList.clear();
        for (int i = 0; i < data.size(); i++) {
            mMikeList.put(data.get(i).no, data.get(i));
        }

    }

    //获取麦序
    public Map<Integer, ApiUsersVoiceAssistan> getVoiceMikeList() {
        return mMikeList;
    }

    //改变麦序的itme
    public void updataMikeData(ApiUsersVoiceAssistan apiUsersVoiceAssistan) {
        mMikeList.put(apiUsersVoiceAssistan.no, apiUsersVoiceAssistan);
    }


    //list麦序
    public void setList(List<ApiUsersVoiceAssistan> data) {
        if (data != null && data.size()>0) {
            this.mList.clear();
            this.mList.addAll(data);
        }

    }

    public List<ApiUsersVoiceAssistan> getList() {
        return mList;
    }

}
