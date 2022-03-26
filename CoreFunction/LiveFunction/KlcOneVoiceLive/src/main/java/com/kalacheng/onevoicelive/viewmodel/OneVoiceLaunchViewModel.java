package com.kalacheng.onevoicelive.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.buscommon.model.ApiUserInfo;


public class OneVoiceLaunchViewModel extends AndroidViewModel {
    public OneVoiceLaunchViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<ApiUserInfo> bean = new ObservableField<>();
}
