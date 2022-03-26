package com.kalacheng.commonview.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ShareDialogBean implements Parcelable {
    public long id;
    public int src;
    public String text;

    public ShareDialogBean() {

    }

    public ShareDialogBean(Parcel in) {
        id = in.readLong();
        src = in.readInt();
        text = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(src);
        dest.writeString(text);
    }

    public static void cloneObj(ShareDialogBean source, ShareDialogBean target) {
        target.id = source.id;
        target.src = source.src;
        target.text = source.text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShareDialogBean> CREATOR = new Creator<ShareDialogBean>() {
        @Override
        public ShareDialogBean createFromParcel(Parcel in) {
            return new ShareDialogBean(in);
        }

        @Override
        public ShareDialogBean[] newArray(int size) {
            return new ShareDialogBean[size];
        }
    };
}
