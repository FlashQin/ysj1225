package com.kalacheng.util.utils.city_select;

public class CityBean {

    public String name;//显示的数据
    public String sortLetters;//显示数据拼音的首字母
    public boolean isChecked;

    public CityBean(String name, String sortLetters) {
        this.name = name;
        this.sortLetters = sortLetters;
    }
}
