package com.kalacheng.tiui.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.kalacheng.tiui.R;

/**
 * Created by Anko on 2018/11/25.
 * Copyright (c) 2018-2019 鑫颜科技 - tillusory.cn. All rights reserved.
 */
public enum TiFaceTrim {
    EYE_MAGNIFYING(R.string.eye_magnifying, R.drawable.ic_ti_eye_magnifying),
    CHIN_SLIMMING(R.string.chin_slimming, R.drawable.ic_ti_chin_slimming),
    FACE_NARROWING(R.string.face_narrowing, R.drawable.ic_ti_face_narrowing),
    JAW_TRANSFORMING(R.string.jaw_transforming, R.drawable.ic_ti_jaw_transforming),
    FOREHEAD_TRANSFORMING(R.string.forehead_transforming, R.drawable.ic_ti_forehead_transforming),
    MOUTH_TRANSFORMING(R.string.mouth_transforming, R.drawable.ic_ti_mouth_transforming),
    NOSE_MINIFYING(R.string.nose_minifying, R.drawable.ic_ti_nose_minifying),
    TEETH_WHITENING(R.string.teeth_whitening, R.drawable.ic_ti_teeth_whitening);

    private int stringId;
    private int imageId;

    TiFaceTrim(@StringRes int stringId, @DrawableRes int imageId) {
        this.stringId = stringId;
        this.imageId = imageId;
    }

    public String getString(@NonNull Context context) {
        return context.getResources().getString(stringId);
    }

    public Drawable getImageDrawable(@NonNull Context context) {
        return context.getResources().getDrawable(imageId);
    }
}