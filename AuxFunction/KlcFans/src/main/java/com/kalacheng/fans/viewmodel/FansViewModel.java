package com.kalacheng.fans.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

public class FansViewModel extends AndroidViewModel {

    public FansViewModel(@NonNull Application application) {
        super(application);
    }

    //列表数
    public ObservableField<Integer> listsize = new ObservableField<>(0);
}
