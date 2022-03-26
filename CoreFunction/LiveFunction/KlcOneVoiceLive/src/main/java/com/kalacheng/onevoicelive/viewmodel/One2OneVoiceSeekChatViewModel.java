package com.kalacheng.onevoicelive.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.buscommon.model.ApiUserInfo;


public class One2OneVoiceSeekChatViewModel extends AndroidViewModel {
    public One2OneVoiceSeekChatViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<ApiUserInfo> bean = new ObservableField<>();

}
