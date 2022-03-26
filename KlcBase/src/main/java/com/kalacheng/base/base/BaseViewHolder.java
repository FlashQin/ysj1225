package com.kalacheng.base.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kalacheng.util.utils.L;


/**
 * Created by cxf on 2018/9/22.
 */

public abstract class BaseViewHolder {

    protected Context mContext;
    protected ViewGroup mParentView;
    protected View mContentView;

    public BaseViewHolder(Context context, ViewGroup parentView) {
        mContext = context;
        mParentView = parentView;
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), mParentView, false);
        init();
    }

    public BaseViewHolder(Context context, ViewGroup parentView, Object... args) {
        processArguments(args);
        mContext = context;
        mParentView = parentView;
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), mParentView, false);
        init();
    }

    protected void processArguments(Object... args) {

    }

    protected abstract int getLayoutId();

    protected abstract void init();

    protected View findViewById(int res) {
        return mContentView.findViewById(res);
    }

    public View getContentView() {
        return mContentView;
    }

    public void addToParent() {
        if (getChildA(mContentView)){
            removeFromParent();
        }
        if (mParentView != null && mContentView != null) {
            mParentView.addView(mContentView);
        }
        L.e("ViewHolder = "+getClass().getSimpleName());

    }

    public void removeFromParent() {
        ViewParent parent = mContentView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mContentView);
        }
    }
    //是否存在这个子viewA,返回true就是有，false就是没有
    private Boolean getChildA(View view) {
        Boolean a = false;
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
                //里面的1000即为需要找View的tag
                if(vp.getTag() != null && String.valueOf(vp.getTag()).equals("LiveMessageComponent")) {
                    Log.i("已存在", String.valueOf(vp.getTag()));
                    a = true;
                }else {
                    a= false;
                }
        }
        return a;
    }

    //显示
    public void setVisibility(){
        if (mContentView!=null){
            mContentView.setVisibility(View.VISIBLE);
        }

    }

    //隐藏
    public void setGone(){
        if (mContentView!=null){
            mContentView.setVisibility(View.GONE);
        }

    }

}
