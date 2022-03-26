package com.kalacheng.live.component.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.libuser.model.AppJoinRoomVO;


public class LiveCommonViewModel extends AndroidViewModel {

    public ObservableField<AppJoinRoomVO> apiJoinRoom = new ObservableField<>();

    public ObservableField<Boolean> isBtnVisibility = new ObservableField<Boolean>();

    public LiveCommonViewModel(@NonNull Application application) {
        super(application);
    }
}
