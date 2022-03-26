package com.kalacheng.util.bean;


/**
 * 用于 存储 每人是否第一次登陆标识
 * （每天第一次登录签到）
 */
public class TodayIsFirstLoginTagBean {

    private String tag;
    private String value;


    public TodayIsFirstLoginTagBean() {
    }

    public TodayIsFirstLoginTagBean(String tag) {
        this.tag = tag;
        this.value = "";
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
