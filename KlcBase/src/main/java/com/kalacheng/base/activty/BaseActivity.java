package com.kalacheng.base.activty;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base.R;
import com.kalacheng.base.base.SuspensionFramePermission;
import com.kalacheng.base.livecloud.TTTConfig;
import com.kalacheng.base.socket.SocketUtils;
import com.kalacheng.base.upload.UploadConfig;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.ToastUtil;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xuantongyun.livecloud.config.TTTConfigUtils;
import com.xuantongyun.storagecloud.upload.UploadUtil;

import java.lang.ref.WeakReference;


/**
 * Created by ysj on 2016/11/18.
 */

public abstract class BaseActivity extends RxAppCompatActivity implements ActivityManage {
    /**
     * Activity是否运行中
     */
    private boolean run = false;
    /**
     * 当前Activity索引标志
     */
    private int index = -1;
    protected Context mContext;
    protected Fragment currentFragment;

    public static int REQUEST_DIALOG_PERMISSION = 5003;

    //状态栏字体为白色
    private boolean mStatusBarWhite;
    //弱引用持有
    private WeakReference<LifecycleProvider> lifecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientation();
        L.e("Activity -> " + this.getLocalClassName());
        L.e("Activity -> " + getClass().getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(setNavigationBarColor());
        }
        setActivityBackgroundResource();
        ARouter.getInstance().inject(this);
        run = true;
        index = BaseApplication.getListActivitys().size();
        BaseApplication.getListActivitys().add(this);
        mStatusBarWhite = isStatusBarWhite();
        setStatusBar();
        mContext = this;

        L.e("Activity -> " + getClass().getSimpleName());

        //防止单例对象被回收，造成null
        if (UploadUtil.getInstance().getConfig() == null) {
            UploadUtil.getInstance().initConfig(new UploadConfig());
        }
        if (TTTConfigUtils.getInstance().getConfig() == null) {
            TTTConfigUtils.getInstance().initConfig(new TTTConfig());
        }
        SocketUtils.checkSocket(this);

        if (ConfigUtil.getBoolValue(R.bool.forbidScreenCap)) {
            // 禁止录屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        run = true;

        //防止单例对象被回收，造成null
        if (UploadUtil.getInstance().getConfig() == null) {
            UploadUtil.getInstance().initConfig(new UploadConfig());
        }
        if (TTTConfigUtils.getInstance().getConfig() == null) {
            TTTConfigUtils.getInstance().initConfig(new TTTConfig());
        }
        SocketUtils.checkSocket(this);
        //SocketService.checkService(this);
        // 重新更新Activity
        BaseApplication.getListActivitys().set(index, this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        BaseApplication.getListActivitys().remove(index);
        for (int i = index; i < BaseApplication.getListActivitys().size(); ++i) {
            int selIndex = BaseApplication.getListActivitys().get(i).getIndex();
            BaseApplication.getListActivitys().get(i).setIndex(--selIndex);
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        run = false;
    }

    @Override
    public boolean isRun() {
        return run;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void restore() {
        run = true;
        BaseApplication.getListActivitys().set(index, this);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void startActivity(Intent intent) {
        if (run) {
            super.startActivity(intent);
            run = false;
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (run) {
            run = false;
            super.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        run = true;
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == REQUEST_DIALOG_PERMISSION) {
//            if (SuspensionFramePermission.getInstance().checkFloatPermission(mContext)) {
//                ToastUtil.show("悬浮框开启成功");
//            } else {
//                ToastUtil.show("悬浮框开启失败");
//            }
//        }

    }

    /**
     * 设置屏幕方向
     */
    protected void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
    }

    /**
     * 设置navigation bar颜色（底部虚拟键背景色）
     */
    protected int setNavigationBarColor() {
        return Color.WHITE;
    }

    /**
     * 添加fragment
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragment(Fragment fragment, @IdRes int frameId) {
        if (null == fragment)
            return;
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .add(frameId, fragment, fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();

    }

    /**
     * 设置Activity背景色
     */
    protected void setActivityBackgroundResource() {
        //Activity的布局最终会添加在DecorView中，这个View会中的背景是不是就没有必要了
//        getWindow().setBackgroundDrawable(null);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
    }

    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    protected void hideFragment(Fragment fragment) {
        if (null == fragment)
            return;
        getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commitAllowingStateLoss();

    }


    /**
     * 显示fragment
     *
     * @param fragment
     */
    protected void showFragment(Fragment fragment, @IdRes int frameId) {
        if (currentFragment != fragment) {//  判断传入的fragment是不是当前的currentFragmentgit
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (null != currentFragment) {
                transaction.hide(currentFragment);//  不是则隐藏
                currentFragment.onPause();
            }
            currentFragment = fragment;  //  然后将传入的fragment赋值给currentFragment
            if (!fragment.isAdded()) { //  判断传入的fragment是否已经被add()过
                transaction.add(frameId, fragment, fragment.getClass().getSimpleName()).show(fragment).commitAllowingStateLoss();
            } else {
                transaction.show(fragment).commitAllowingStateLoss();
                fragment.onResume();
            }
        }
    }

    /**
     * 弹出栈顶部的Fragment
     */
    protected void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 设置透明状态栏
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (mStatusBarWhite) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0);
        }
    }

    public void backClick(View v) {
        finish();
    }

    /* @Override
     public Resources getResources() {

         return ScreenHelper.applyAdapt(super.getResources(), 440f, ScreenHelper.WIDTH_DP);
     }*/
    protected void setTitle(String title) {
        TextView titleView = (TextView) findViewById(R.id.titleView);
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    protected boolean isStatusBarWhite() {
        return true;
    }

    /**
     * 修改状态栏字体颜色
     */
    public void setStatusBarWhite(boolean white) {
        mStatusBarWhite = white;
        setStatusBar();
    }

    /**
     * 注入RxLifecycle生命周期
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }
}
