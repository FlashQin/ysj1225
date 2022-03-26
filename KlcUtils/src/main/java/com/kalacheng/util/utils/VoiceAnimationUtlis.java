package com.kalacheng.util.utils;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.kalacheng.util.R;

public class VoiceAnimationUtlis {

    private AnimationDrawable animationDrawable;
    private static volatile VoiceAnimationUtlis singleton;

    public static VoiceAnimationUtlis getInstance() {

        if (singleton == null) {
            synchronized (VoiceAnimationUtlis.class) {
                if (singleton == null) {
                    singleton = new VoiceAnimationUtlis();
                }
            }
        }
        return singleton;
    }

    public void showAnimation(ImageView imageView){
        Resources resources = ApplicationUtil.getInstance().getResources();
        animationDrawable = new AnimationDrawable();
        animationDrawable.setOneShot(false);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice1),100);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice2),100);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice3),100);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice4),100);
        animationDrawable.addFrame(resources.getDrawable(R.mipmap.voice5),100);

        imageView.setBackground(animationDrawable);
        animationDrawable.start();
    }

    public void stopAnimation(){
        if (animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }
}
