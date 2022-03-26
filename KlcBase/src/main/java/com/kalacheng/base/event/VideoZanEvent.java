package com.kalacheng.base.event;

/**
 * @author: Administrator
 * @date: 2020/10/16
 * @info:   location：位于项目中的位置（用于区分广播回调）
 *          position：列表的item下标（用于去改变列表点赞和评论数）
 *          zanNumber：点赞数
 *          commentNumber: 评论数
 *          isLike：是否喜欢 0:未喜欢 1:已喜欢
 *          isZanComment: 1：赞  2：评论
 */
public class VideoZanEvent {

    private String location;
    private int position;
    private int zanNumber;
    private int commentNumber;
    private int isLike;
    private int isZanComment;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getZanNumber() {
        return zanNumber;
    }

    public void setZanNumber(int zanNumber) {
        this.zanNumber = zanNumber;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getIsZanComment() {
        return isZanComment;
    }

    public void setIsZanComment(int isZanComment) {
        this.isZanComment = isZanComment;
    }

}