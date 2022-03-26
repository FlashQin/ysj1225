package com.kalacheng.base.activty;

/**
 * Activity管理接口
 * Created by ysj on 2016/11/18.
 */

public interface ActivityManage {
    /**
     * 获取当前Activity索引
     */
    public int getIndex();

    /**
     * 设置当前Activity索引
     */
    public void setIndex(int index);

    /**
     * 获取当前Activity是否正在运行
     */
    public boolean isRun();

    /**
     * 手动恢复Activity运行状态
     */
    public void restore();

    /**
     * 关闭Activity
     */
    public void close();
}
