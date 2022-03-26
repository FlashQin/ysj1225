package com.kalacheng.commonview.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;

import com.kalacheng.libuser.model.ApiGuardEntity;


public class OpenGuardViewModel extends AndroidViewModel {
    public OpenGuardViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<ApiGuardEntity> bean = new ObservableField();}
