package com.kalacheng.commonview.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.buscommon.model.GuardUserDto;

import java.util.List;

public class LiveGuardListViewModel extends AndroidViewModel {
    public LiveGuardListViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<List<GuardUserDto>> bean = new ObservableField<>();
}
