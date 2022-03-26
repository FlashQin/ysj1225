package com.klc.bean;

import android.view.SurfaceView;

public class SwitchBigAndSmallBean  {
    public long id;
    public SurfaceView surfaceView;
    public String userName;

    //判断副播退出的时候为大图
    public boolean isOut =false;

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SurfaceView getSurfaceView() {
        return surfaceView;
    }

    public void setSurfaceView(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }
}
