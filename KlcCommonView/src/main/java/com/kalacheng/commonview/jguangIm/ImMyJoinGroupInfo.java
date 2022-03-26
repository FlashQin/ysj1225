package com.kalacheng.commonview.jguangIm;

import cn.jpush.im.android.api.model.GroupInfo;

/**
 * 我加入的群组列表
 */
public class ImMyJoinGroupInfo {

    public ImMyJoinGroupInfo() {
    }

    public ImMyJoinGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    private GroupInfo groupInfo;

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }
}
