package com.kalacheng.util.bean;

public class SimpleTextBean {
    public String name;
    public String value;
    public long id;
    public String IdStr;
    public boolean isChecked;

    public SimpleTextBean(String name) {
        this.name = name;
    }

    public SimpleTextBean(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleTextBean(String IdStr, String name, String value) {
        this.IdStr = IdStr;
        this.name = name;
        this.value = value;
    }
}
