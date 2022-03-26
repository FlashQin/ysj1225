package com.kalacheng.one2onelive.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.buscommon.model.ApiUserInfo;


public class One2OneLaunchViewModel extends AndroidViewModel {
    public One2OneLaunchViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<ApiUserInfo> bean = new ObservableField<>();
}
