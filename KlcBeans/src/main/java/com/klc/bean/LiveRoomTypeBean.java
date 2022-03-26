package com.klc.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LiveRoomTypeBean implements Parcelable {
    public LiveRoomTypeBean()
    {
    }
    public int id;

    public String name;

    //是否选中
    public boolean isChecked;

    //2开播 1修改
    public int type;

    //1 视频 2语音
    public int LiveType;

    public LiveRoomTypeBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        isChecked = in.readByte() != 0;
        mContent = in.readString();
        type = in.readInt();
        LiveType = in.readInt();
    }

    public static final Creator<LiveRoomTypeBean> CREATOR = new Creator<LiveRoomTypeBean>() {
        @Override
        public LiveRoomTypeBean createFromParcel(Parcel in) {
            return new LiveRoomTypeBean(in);
        }

        @Override
        public LiveRoomTypeBean[] newArray(int size) {
            return new LiveRoomTypeBean[size];
        }
    };

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    //内容
    public String mContent;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeString(mContent);
        dest.writeInt(type);
        dest.writeInt(LiveType);
    }
}
