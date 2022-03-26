package com.kalacheng.util.utils;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kalacheng.util.utils.fragment.ActivityResultCallback;
import com.kalacheng.util.utils.fragment.ProcessFragment;


/**
 * Created by cxf on 2018/9/29.
 */

public class ProcessResultUtil {

    protected ProcessFragment mFragment;


    public ProcessResultUtil(FragmentActivity activity) {
        mFragment = new ProcessFragment();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.add(mFragment, "ProcessFragment").commitAllowingStateLoss();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(String[] permissions, Runnable runnable) {
        mFragment.requestPermissions(permissions, runnable);
    }


    public void startActivityForResult(Intent intent, ActivityResultCallback callback){
        mFragment.startActivityForResult(intent,callback);
    }


    public void release(){
        if(mFragment!=null){
            mFragment.release();
        }
    }

}
