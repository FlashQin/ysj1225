package com.kalacheng.money.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

public class MyCoinViewModel extends AndroidViewModel {
    public MyCoinViewModel(@NonNull Application application) {
        super(application);
    }
    public ObservableField<String> id=new ObservableField<>("");
    public ObservableField<String> coin =new ObservableField<>("");
    public ObservableField<String> money=new ObservableField<>("");
    public ObservableField<String> give=new ObservableField<>("");
}
