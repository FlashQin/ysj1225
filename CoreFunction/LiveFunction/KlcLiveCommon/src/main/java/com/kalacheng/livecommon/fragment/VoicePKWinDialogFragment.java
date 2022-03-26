package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiPkResultRoom;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;

public class VoicePKWinDialogFragment extends BaseDialogFragment {
    private ApiPkResultRoom apiPkResultRoom;

    @Override
    protected int getLayoutId() {
        return R.layout.voice_pk_win;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width =  WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        apiPkResultRoom =getArguments().getParcelable(ARouterValueNameConfig.ApiPkResultRoom);

        RoundedImageView win_headimage = mRootView.findViewById(R.id.win_headimage);

        TextView win_name = mRootView.findViewById(R.id.win_name);
        if (apiPkResultRoom.pkType ==2){
            ImageLoader.display(apiPkResultRoom.winUserAvatar,win_headimage,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
            win_name.setText(apiPkResultRoom.winUserName);
        }else if(apiPkResultRoom.pkType ==1){
            if (apiPkResultRoom.isWin == 1){
                win_headimage.setImageResource(R.mipmap.win_red);
            }else if(apiPkResultRoom.isWin == -1){
                win_headimage.setImageResource(R.mipmap.win_blue);
            }
        }else if(apiPkResultRoom.pkType ==3){
            ImageLoader.display(apiPkResultRoom.winUserAvatar,win_headimage,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
            win_name.setText(apiPkResultRoom.winUserName);
        }

        cdTimer.start();
    }

    private CountDownTimer cdTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            dismiss();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cdTimer!=null){
            cdTimer.cancel();
        }
        dismiss();
    }
}
