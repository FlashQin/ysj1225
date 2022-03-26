package com.kalacheng.util.bean;

/**
 * Created by hgy on 2019/10/10.
 * 简单的上下结构imagetext类
 */

public class SimpleImageUrlTextBean {
    public long id;
    public String url;
    public String text;
    public String text2;
    public int src;

    public SimpleImageUrlTextBean(String url, String text) {
        this.url = url;
        this.text = text;
    }

    public SimpleImageUrlTextBean(int src, String text) {
        this.src = src;
        this.text = text;
    }

    public SimpleImageUrlTextBean(long id, String url, String text, String text2) {
        this.id = id;
        this.url = url;
        this.text = text;
        this.text2 = text2;
    }

    public SimpleImageUrlTextBean(long id, String url, String text) {
        this.id = id;
        this.url = url;
        this.text = text;
    }
}
