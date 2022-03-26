package com.kalacheng.commonview.jguangIm;

public class UnReadCountEvent {

    /**
     * 总未读数
     */
    private int totalNoRead;

    /**
     * 短视频未读数
     */
    private int shortVideoNoRead;

    /**
     * 通知未读数
     */
    private int systemNoRead;

    /**
     * 动态未读数
     */
    private int videoNoRead;

    /**
     * 官方未读数
     */
    private int officialNewsNoRead;

    public UnReadCountEvent(int totalNoRead, int systemNoRead, int videoNoRead, int shortVideoNoRead, int officialNewsNoRead) {
        this.totalNoRead = totalNoRead;
        this.systemNoRead = systemNoRead;
        this.videoNoRead = videoNoRead;
        this.shortVideoNoRead = shortVideoNoRead;
        this.officialNewsNoRead = officialNewsNoRead;
    }

    public int getTotalNoRead() {
        return totalNoRead;
    }

    public void setTotalNoRead(int totalNoRead) {
        this.totalNoRead = totalNoRead;
    }

    public int getSystemNoRead() {
        return systemNoRead;
    }

    public void setSystemNoRead(int systemNoRead) {
        this.systemNoRead = systemNoRead;
    }

    public int getVideoNoRead() {
        return videoNoRead;
    }

    public void setVideoNoRead(int videoNoRead) {
        this.videoNoRead = videoNoRead;
    }

    public int getReviewCount() {
        return shortVideoNoRead + videoNoRead;
    }

    public int getOfficialNewsNoRead() {
        return officialNewsNoRead;
    }

    public void setOfficialNewsNoRead(int officialNewsNoRead) {
        this.officialNewsNoRead = officialNewsNoRead;
    }

    public int getShortVideoNoRead() {
        return shortVideoNoRead;
    }

    public void setShortVideoNoRead(int shortVideoNoRead) {
        this.shortVideoNoRead = shortVideoNoRead;
    }
}
