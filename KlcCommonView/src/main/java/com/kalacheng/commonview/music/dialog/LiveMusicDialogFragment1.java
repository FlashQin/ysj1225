package com.kalacheng.commonview.music.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.UserFragmentAdpater;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.view.ViewPagerIndicator;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.base.base.BaseMVVMFragment;

import java.util.ArrayList;
import java.util.List;

public class LiveMusicDialogFragment1 extends DialogFragment {

    private FragmentActivity mContext;
    public LiveMusicDialogFragment1(){

    }

    public LiveMusicDialogFragment1(FragmentActivity mContext) {
        this.mContext = mContext;
    }

    private LiveMusicUpLoadDialogFragment liveMusicUpLoadDialogFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);//设置点击外部Dialog消失
        View view =inflater.inflate(R.layout.live_music_dialog_1,null,false);

        final List<Fragment> fragmentList = new ArrayList<>();
        ViewPager LiveMusic_viewpager = view.findViewById(R.id.LiveMusic_viewpager);

        ViewPagerIndicator LiveMusic_Indicator = view.findViewById(R.id.LiveMusic_Indicator);

        //音乐播放列表
        LiveMusicListDialogFragment liveMusicListDialogFragment = new LiveMusicListDialogFragment();
        fragmentList.add(liveMusicListDialogFragment);

        //音乐库
        LiveMusicLibraryDialogFragment liveMusicLibraryDialogFragment = new LiveMusicLibraryDialogFragment();
        fragmentList.add(liveMusicLibraryDialogFragment);


        LiveMusic_Indicator.setTitles(LiveConstants.LIVEMUSIC);
        LiveMusic_Indicator.setViewPager(LiveMusic_viewpager);


        UserFragmentAdpater userFragmentAdpater = new UserFragmentAdpater(getChildFragmentManager());
        userFragmentAdpater.loadData(fragmentList);
        LiveMusic_viewpager.setAdapter(userFragmentAdpater);
        LiveMusic_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < fragmentList.size(); i++) {
                    if (i == position) {
                        if (fragmentList.get(i) instanceof BaseMVVMFragment) {
                            ((BaseMVVMFragment) fragmentList.get(i)).setShowed(true);
                        }
                    } else {
                        if (fragmentList.get(i) instanceof BaseMVVMFragment) {
                            ((BaseMVVMFragment) fragmentList.get(i)).setShowed(false);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        if (liveMusicUpLoadDialogFragment != null) {
            liveMusicUpLoadDialogFragment.dismiss();
            liveMusicUpLoadDialogFragment = null;
        }
        //上传音乐
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_Music_UpLoad, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                if (!ConfigUtil.getBoolValue(R.bool.useMusicOld)) {
                    if (liveMusicUpLoadDialogFragment != null) {
                        liveMusicUpLoadDialogFragment.dismiss();
                        liveMusicUpLoadDialogFragment = null;
                    }
                    liveMusicUpLoadDialogFragment = new LiveMusicUpLoadDialogFragment(mContext);
                    liveMusicUpLoadDialogFragment.show(mContext.getSupportFragmentManager(), "LiveMusicUpLoadDialogFragment");
                }
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //上传音乐
        ImageView upload_img = (ImageView) view.findViewById(R.id.upload_img);
        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;

                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_Music_UpLoad,null);
            }
        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog2);


    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        clean();
    }

    public void clean() {
        LiveBundle.getInstance().removeNullMsgListener(LiveConstants.LM_Music_UpLoad);
    }


    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.getScreenHeight() *4/7;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

    }
}
