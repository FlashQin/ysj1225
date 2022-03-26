package com.kalacheng.commonview.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.libuser.model.AppJoinRoomVO;


public class FloatingScreenViewModel extends AndroidViewModel {
    public FloatingScreenViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<AppJoinRoomVO> joinRoom = new ObservableField<>();
}
