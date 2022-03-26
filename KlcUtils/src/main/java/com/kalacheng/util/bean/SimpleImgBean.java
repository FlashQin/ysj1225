package com.kalacheng.util.bean;

public class SimpleImgBean {
    public long id;
    public int src;
    public String url;
    public boolean isChecked;

    public SimpleImgBean(long id, int src) {
        this.id = id;
        this.src = src;
    }

    public SimpleImgBean(long id, String url) {
        this.id = id;
        this.url = url;
    }
}
