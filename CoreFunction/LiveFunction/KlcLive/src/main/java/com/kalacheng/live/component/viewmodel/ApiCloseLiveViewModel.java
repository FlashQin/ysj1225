package com.kalacheng.live.component.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.libuser.model.ApiCloseLive;
import com.kalacheng.libuser.model.AppJoinRoomVO;


public class ApiCloseLiveViewModel extends AndroidViewModel {
    public ObservableField<ApiCloseLive> bean = new ObservableField();
    public ObservableField<AppJoinRoomVO> apijoinroom = new ObservableField();
    public ApiCloseLiveViewModel(@NonNull Application application) {
        super(application);
    }
}
