package com.kalacheng.main.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.kalacheng.main.R;
import com.kalacheng.main.databinding.FragmentBirthdayWelcomeBinding;
import com.kalacheng.main.viewmodel.BirthdayWelcomeViewModel;
import com.kalacheng.util.utils.glide.GlideUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.base.base.BaseMVVMFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * @author: Administrator
 * @date: 2020/8/28
 * @info: 首页生日闪屏
 */
public class BirthdayWelcomeFragment extends BaseMVVMFragment<FragmentBirthdayWelcomeBinding, BirthdayWelcomeViewModel> {

    private String name = "";
    private String pic = "";

    public BirthdayWelcomeFragment(){
    }

    public BirthdayWelcomeFragment(Context mContext, ViewGroup mParentView, String name, String pic){
        super(mContext, mParentView);
        this.name = name;
        this.pic = pic;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_birthday_welcome;
    }

    @Override
    public void initData() {
        setText("亲爱的" + name);

        Typeface mtypeface= Typeface.createFromAsset(getActivity().getAssets(),"ArialBlackRegular.ttf");
        //Typeface mtypeface= com.kalacheng.util.utils.getTypeFaceUtil.getArialBlackRegular(BaseApplication.getInstance());
        binding.tv1.setTypeface(mtypeface);
        binding.tv2.setTypeface(mtypeface);

        Typeface mtypeface2= Typeface.createFromAsset(getActivity().getAssets(),"YouSheBiaoTiHei.ttf");
        //Typeface mtypeface2= com.kalacheng.util.utils.getTypeFaceUtil.getYouSheBiaoTiHei(BaseApplication.getInstance());
        binding.birthdayContent.setTypeface(mtypeface2);
        binding.birthdayName.setTypeface(mtypeface2);

        ImageLoader.display(pic, binding.avatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        GlideUtils.loadOneTimeGif(getActivity(), R.mipmap.birthday_gif, binding.ivGif,1, new GlideUtils.GifListener() {
            @Override
            public void gifPlayComplete() {
                EventBus.getDefault().post("birthday");
            }
        });
    }


    private void setText(String content){
        binding.birthdayName.setText(content);
        binding.birthdayContent.setText("生日快乐!");
    }

}