package com.kalacheng.centercommon.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kalacheng.libuser.model.InvisiblePrivilegeDTO;
import com.kalacheng.util.utils.DialogUtil;

/**
 * @author: Administrator
 * @date: 2020/8/19
 * @info:
 */
public class PowerSettingViewModel extends AndroidViewModel {

    private Context context;
    public MutableLiveData<InvisiblePrivilegeDTO> data;

    public PowerSettingViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<InvisiblePrivilegeDTO> getData(){
        if (data == null){
            data = new MutableLiveData<>();
        }
        return data;
    }

    public void setData(MutableLiveData<InvisiblePrivilegeDTO> data){
        this.data = data;
    }

}