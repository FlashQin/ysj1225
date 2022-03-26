package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.StringUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class GoldInsufficientDialogFragment extends BaseDialogFragment {

    private int Time;
    private Disposable timeDisposable;
    private GoldInsufficientListener listener;

    @Override
    protected int getLayoutId() {
        return R.layout.gold_insufficient_dialog;
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
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Time = getArguments().getInt("Time");
        getInitView();


    }

    public void getInitView(){
        final TextView time = mRootView.findViewById(R.id.time);
        TextView Tips = mRootView.findViewById(R.id.tips);
        TextView buttom_Recharge  = mRootView.findViewById(R.id.buttom_Recharge);
        if (LiveConstants.ANCHORID == HttpClient.getUid()){
            Tips.setText("TA的余额不足"  );
            buttom_Recharge.setText("知道了");
        }else {
            Tips.setText("你的余额不足");
            buttom_Recharge.setText("立即充值");
        }

        buttom_Recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LiveConstants.ANCHORID == HttpClient.getUid()){
                    dismiss();
                }else {
//                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveRecharge,null);
                    ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
                    if (null != listener){
                        listener.onClose();
                    }
                }
            }
        });


        timeDisposable = Observable.interval(0,1, TimeUnit.SECONDS).take(Time).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                time.setText("剩余时间 "+StringUtil.getDurationText((Time-aLong) * 1000));

                if ((Time-aLong) == 1){
                    timeDisposable.dispose();
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != timeDisposable){
            timeDisposable.dispose();
        }
    }

    public void setListener(GoldInsufficientListener listener){
        this.listener = listener;
    }

    public interface GoldInsufficientListener{
        void onClose();
    }

}
