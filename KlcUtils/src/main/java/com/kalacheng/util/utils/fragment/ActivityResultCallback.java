
package com.kalacheng.util.utils.fragment;

import android.content.Intent;

/**
 * Created by cxf on 2018/9/29.
 */

public abstract class ActivityResultCallback {
    public abstract void onSuccess(Intent intent);

    public void onFailure() {

    }
}
