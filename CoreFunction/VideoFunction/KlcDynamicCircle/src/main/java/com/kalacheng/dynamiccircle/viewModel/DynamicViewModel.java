package com.kalacheng.dynamiccircle.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;


import com.kalacheng.libuser.model.ApiUserVideo;

import java.util.List;

public class DynamicViewModel extends AndroidViewModel {
    public DynamicViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> title = new ObservableField<>("动态");

    public ObservableField<List<ApiUserVideo>> mlist = new ObservableField<>();
}
