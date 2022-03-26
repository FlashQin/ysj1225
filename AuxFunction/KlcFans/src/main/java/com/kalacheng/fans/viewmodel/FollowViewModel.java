package com.kalacheng.fans.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

public class FollowViewModel extends AndroidViewModel {


    public FollowViewModel(@NonNull Application application) {
        super(application);
    }
    public ObservableField<String> title = new ObservableField<>("关注");

}
