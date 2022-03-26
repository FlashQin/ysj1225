package com.kalacheng.commonview.listener;


import com.kalacheng.libuser.model.ApiUsersVideoComments;

public interface OnTrendCommentItemClickListener {
    void onItemClick(ApiUsersVideoComments commentBean);

    void onUserName(ApiUsersVideoComments commentBean);

    void onToUserName(ApiUsersVideoComments commentBean);
}
