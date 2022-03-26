package com.kalacheng.livecommon.lister;

import com.klc.bean.live.JniObjs;

public class AudioLevelCallBack {
    public AudioLevelBack back;

    public interface AudioLevelBack {
        void init(JniObjs jniObjs);

    }

    private static class SingletonHolder {
        private static final AudioLevelCallBack INSTANCE = new AudioLevelCallBack();
    }

    private AudioLevelCallBack() {

    }
    public static final AudioLevelCallBack getInstance() {
        return AudioLevelCallBack.SingletonHolder.INSTANCE;
    }

    public void setAudioLevelCallBack(AudioLevelBack back){
        this.back =back;
    }

}
