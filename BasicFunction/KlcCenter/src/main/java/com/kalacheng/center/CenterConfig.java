package com.kalacheng.center;

import android.content.res.TypedArray;

import com.kalacheng.base.activty.BaseApplication;

public class CenterConfig {
    private static String[] bottomNames = BaseApplication.getInstance().getResources().getStringArray(R.array.me_bottom_names);
    private static TypedArray bottomIds = BaseApplication.getInstance().getResources().obtainTypedArray(R.array.me_bottom_ids);

    private static String[] anchorCenterNames = BaseApplication.getInstance().getResources().getStringArray(R.array.anchor_center_names);
    private static TypedArray anchorCenterIds = BaseApplication.getInstance().getResources().obtainTypedArray(R.array.anchor_center_ids);

    public static String[] getBottomNames() {
        String[] names = ((BaseApplication)BaseApplication.getInstance()).getCenterConfigBottomNames();
        if (names != null) {
            return names;
        }
        return bottomNames;
    }

    public static TypedArray getBottomIds() {
        TypedArray ids = ((BaseApplication)BaseApplication.getInstance()).getCenterConfigBottomIds();
        if (ids != null) {
            return ids;
        }
        return bottomIds;
    }

    public static String[] getAnchorCenterNames() {
        String[] names = ((BaseApplication)BaseApplication.getInstance()).getCenterConfigBottomNames();
        if (names != null) {
            return names;
        }
        return anchorCenterNames;
    }

    public static TypedArray getAnchorCenterIds() {
        TypedArray ids = ((BaseApplication)BaseApplication.getInstance()).getCenterConfigBottomIds();
        if (ids != null) {
            return ids;
        }
        return anchorCenterIds;
    }

}
