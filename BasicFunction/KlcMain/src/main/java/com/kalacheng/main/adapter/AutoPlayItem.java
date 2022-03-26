package com.kalacheng.main.adapter;

import android.view.View;

public interface AutoPlayItem {
    void setActive();
    void deactivate();
    void onPause();
    void onResume();
    View getAutoplayView();
}
