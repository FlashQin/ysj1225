package com.kalacheng.one2onelive.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.kalacheng.busooolive.model.OOOReturn;


public class SvipSideshowDisplayViewModel extends AndroidViewModel {
    public SvipSideshowDisplayViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<OOOReturn> oooreturn = new ObservableField<>();
}
