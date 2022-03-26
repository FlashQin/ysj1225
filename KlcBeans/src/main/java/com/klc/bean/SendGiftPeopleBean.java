package com.klc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SendGiftPeopleBean implements Parcelable {
    public long uid;
    public String name;
    public String headimage;
    public String showid;
    public long roomID;
    public long shortVideoId;
    public int liveType;
    public long anchorID;

    public SendGiftPeopleBean(){

    }
    protected SendGiftPeopleBean(Parcel in) {
        uid = in.readLong();
        name = in.readString();
        headimage = in.readString();
        showid = in.readString();
        roomID = in.readLong();
        shortVideoId= in.readLong();
        liveType = in.readInt();
        anchorID = in.readLong();
    }

    public static final Creator<SendGiftPeopleBean> CREATOR = new Creator<SendGiftPeopleBean>() {
        @Override
        public SendGiftPeopleBean createFromParcel(Parcel in) {
            return new SendGiftPeopleBean(in);
        }

        @Override
        public SendGiftPeopleBean[] newArray(int size) {
            return new SendGiftPeopleBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uid);
        dest.writeString(name);
        dest.writeString(headimage);
        dest.writeString(showid);
        dest.writeLong(roomID);
        dest.writeLong(shortVideoId);
        dest.writeInt(liveType);
        dest.writeLong(anchorID);
    }
}
