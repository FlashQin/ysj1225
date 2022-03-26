package com.kalacheng.commonview.music.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.kalacheng.libuser.model_fun.AppMusic_uploadMusic;
import com.kalacheng.util.utils.DateUtil;

/**
 * 上传音乐 音乐选择
 */
public class MusicChooseBean implements Parcelable {

    public MusicChooseBean() {

    }

    public String path;
    public int num;

    /**
     * 歌手名称
     */
    private String author;
    /**
     * 音乐封面
     */
    private String cover;
    /**
     * 音乐名称
     */
    private String name;
    /**
     * 音乐时长
     */
    private int duration;

    /**
     * 音乐空间上的 外链地址
     */
    private String music_url;

    /**
     * 音乐封面 外链地址
     */
    private String cover_url;

    /**
     * 上传音乐状态
     * -2 为 上传失败 / -1 为 未上传 / 0 为 上传中 / 1 为 已上传
     */
    private int tv_upload_type = -1;


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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMusic_url() {
        return music_url;
    }

    public void setMusic_url(String music_url) {
        this.music_url = music_url;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }


    public int getTv_upload_type() {
        return tv_upload_type;
    }

    public void setTv_upload_type(int tv_upload_type) {
        this.tv_upload_type = tv_upload_type;
    }


    /**
     * 显示 歌曲名 (歌曲名)
     *
     * @return
     */
    public String showName() {

        String name = "";

        name = getName();

        return name;
    }

    /**
     * 显示 歌手名 (歌手名 + 文件类型)
     * @return
     */
    public String showAuthorTag() {
        String tag = "";

        if (!TextUtils.isEmpty(showAuthor())) {
            tag = tag + " - " + showAuthor();
        }
        tag = tag + ".mp3";

        return tag;
    }

    /**
     * 显示 音乐时长
     *
     * @return 显示字符串
     */
    public String showDuration() {
        return DateUtil.formatDuring2(duration);
    }

    /**
     * 显示 歌手名称
     *
     * @return 显示字符串
     */
    public String showAuthor() {
        return TextUtils.isEmpty(author) || (!TextUtils.isEmpty(author) && author.equals("<unknown>")) ? "未知歌手" : author;
    }


    public AppMusic_uploadMusic getMusicData() {
        AppMusic_uploadMusic music = new AppMusic_uploadMusic();
        music.author = this.author;
        music.cover = this.cover_url;
        music.musicUrl = this.music_url;
        music.name = this.name;

        return music;
    }

    public MusicChooseBean(Parcel in) {
        path = in.readString();
        num = in.readInt();
        author = in.readString();
        cover = in.readString();
        name = in.readString();
        duration = in.readInt();
        music_url = in.readString();
        cover_url = in.readString();
        tv_upload_type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 序列化过程：必须按成员变量声明的顺序进行封装
        dest.writeString(path);
        dest.writeInt(num);
        dest.writeString(author);
        dest.writeString(cover);
        dest.writeString(name);
        dest.writeInt(duration);
        dest.writeString(music_url);
        dest.writeString(cover_url);
        dest.writeInt(tv_upload_type);
    }

    public static void cloneObj(MusicChooseBean source, MusicChooseBean target) {
        target.path = source.path;
        target.num = source.num;
        target.author = source.author;
        target.cover = source.cover;
        target.name = source.name;
        target.duration = source.duration;
        target.music_url = source.music_url;
        target.cover_url = source.cover_url;
        target.tv_upload_type = source.tv_upload_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    // 反序列过程：必须实现Parcelable.Creator接口，并且对象名必须为CREATOR
    // 读取Parcel里面数据时必须按照成员变量声明的顺序，Parcel数据来源上面writeToParcel方法，读出来的数据供逻辑层使用
    public static final Creator<MusicChooseBean> CREATOR = new Creator<MusicChooseBean>() {

        @Override
        public MusicChooseBean createFromParcel(Parcel source) {
            return new MusicChooseBean(source);
        }

        @Override
        public MusicChooseBean[] newArray(int size) {
            return new MusicChooseBean[size];
        }
    };
}
