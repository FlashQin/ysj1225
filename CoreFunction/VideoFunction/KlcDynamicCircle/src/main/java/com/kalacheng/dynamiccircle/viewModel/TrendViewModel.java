package com.kalacheng.dynamiccircle.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;


import com.kalacheng.libuser.model.ApiUserVideo;

import java.util.List;

/*首页动态列表*/
public class TrendViewModel extends AndroidViewModel {
    public TrendViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<List<ApiUserVideo>> bean = new ObservableField<>();
}
