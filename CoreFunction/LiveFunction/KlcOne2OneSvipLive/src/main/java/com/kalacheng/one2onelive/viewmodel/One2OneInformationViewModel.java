package com.kalacheng.one2onelive.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.libuser.model.AppJoinRoomVO;


public class One2OneInformationViewModel extends AndroidViewModel {
    public One2OneInformationViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<AppJoinRoomVO> joinRoom = new ObservableField<>();
}
