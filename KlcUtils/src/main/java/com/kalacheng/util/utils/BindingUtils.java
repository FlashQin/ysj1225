package com.kalacheng.util.utils;

import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.math.BigDecimal;


/**
 * Created by hgy on 2019/09/17.
 */

public class BindingUtils {
    static int defaultRound = 6;

    @BindingAdapter(value = {"image", "placeholderRes", "errorRes", "circleCrop"}, requireAll = false)
    public static void loadImage(ImageView image, String url, int placeholderRes, int errorRes, boolean circleCrop) {
        if (image != null) {
            ImageLoader.display(url, image, placeholderRes, errorRes, circleCrop, null, null, null);
        }
    }

    @BindingAdapter("image")
    public static void loadImage(ImageView image, int id) {
        if (image != null) {
            image.setImageResource(id);
        }
    }

    @BindingAdapter("background")
    public static void loadBackground(View view, int id) {
        if (view != null) {
            view.setBackgroundResource(id);
        }
    }

    @BindingAdapter(value = {"cornersimage", "placeholderRes", "errorRes"}, requireAll = false)
    public static void loadRoundedCornersImage(ImageView image, String url, int placeholderRes, int errorRes) {
        if (image != null) {
            ImageLoader.displayRoundedCorners(url, image, placeholderRes, defaultRound);
        } else {
            if (errorRes != 0)
                image.setImageResource(errorRes);
        }
    }

    @BindingAdapter("onNavigationItemSelectedListener")
    public static void setOnNavigationItemSelectedListener(
            BottomNavigationView view, BottomNavigationView.OnNavigationItemSelectedListener listener) {
        view.setOnNavigationItemSelectedListener(listener);
    }

    @BindingAdapter("viewPagerAdapter")
    public static void setViewPagerAdapter(ViewPager view, FragmentStatePagerAdapter adapter) {
        view.setAdapter(adapter);
    }


    @BindingAdapter("addTextChanged")
    public static void addTextChangedListener(EditText view, TextWatcher textWatcher) {
        view.addTextChangedListener(textWatcher);
    }

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("BigDecimal")
    public static void setText(TextView view, BigDecimal decimal) {
        view.setText(decimal.toString());
    }

}
