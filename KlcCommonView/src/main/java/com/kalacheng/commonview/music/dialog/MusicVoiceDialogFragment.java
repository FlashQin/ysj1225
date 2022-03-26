package com.kalacheng.commonview.music.dialog;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.SpUtil;

/*
* 音乐播放器调节声音大小
* */
public class MusicVoiceDialogFragment  extends BaseDialogFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.musicvoice_dialog;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.dp2px(120);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SeekBar voice_seek = mRootView.findViewById(R.id.voice_seek);
        voice_seek.setProgress((int) SpUtil.getInstance().getSharedPreference(SpUtil.VOICE_VALUE,0));
        voice_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override//拖动条进度改变的时候调用
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SpUtil.getInstance().put(SpUtil.VOICE_VALUE,i);
                callBack.onProgressChanged(i);
            }


            @Override//拖动条开始拖动的时候调用
            public void onStartTrackingTouch(SeekBar seekBar) {

            }


            @Override //拖动条停止拖动的时候调用
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setSeekBarColor(voice_seek,R.color.pk_blue);
    }

    public void setSeekBarColor(SeekBar seekBar, int color){
        LayerDrawable layerDrawable = (LayerDrawable) seekBar.getProgressDrawable();
        Drawable dra=layerDrawable.getDrawable(1);
        dra.setColorFilter(color, PorterDuff.Mode.SRC);
        Drawable dra2=seekBar.getThumb();
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.color_8A8DFF), PorterDuff.Mode.SRC_ATOP);
        seekBar.invalidate();
    }
    private MusicVoiceDialogCallBack callBack;
    public void setMusicVoiceDialogCallBack(MusicVoiceDialogCallBack back){
        this.callBack =back;
    }
    public interface MusicVoiceDialogCallBack{
        public void onProgressChanged(int progress);
    }
}
