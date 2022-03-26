package com.kalacheng.commonview.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PictureChooseBean implements Parcelable {

    public PictureChooseBean() {

    }

    public String path;
    public int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PictureChooseBean(Parcel in) {
        path = in.readString();
        num = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 序列化过程：必须按成员变量声明的顺序进行封装
        dest.writeString(path);
        dest.writeInt(num);
    }

    public static void cloneObj(PictureChooseBean source, PictureChooseBean target) {
        target.path = source.path;
        target.num = source.num;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    // 反序列过程：必须实现Parcelable.Creator接口，并且对象名必须为CREATOR
    // 读取Parcel里面数据时必须按照成员变量声明的顺序，Parcel数据来源上面writeToParcel方法，读出来的数据供逻辑层使用
    public static final Creator<PictureChooseBean> CREATOR = new Creator<PictureChooseBean>() {

        @Override
        public PictureChooseBean createFromParcel(Parcel source) {
            return new PictureChooseBean(source);
        }

        @Override
        public PictureChooseBean[] newArray(int size) {
            return new PictureChooseBean[size];
        }
    };
}
