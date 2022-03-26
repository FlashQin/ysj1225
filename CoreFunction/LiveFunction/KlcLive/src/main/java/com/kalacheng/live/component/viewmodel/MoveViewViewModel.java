package com.kalacheng.live.component.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.libuser.model.AppJoinRoomVO;

/**
 * @author: Administrator
 * @date: 2020/8/8
 * @info:
 */
public class MoveViewViewModel extends AndroidViewModel {
    public MoveViewViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<AppJoinRoomVO> apiJoinRoom = new ObservableField<>();

}