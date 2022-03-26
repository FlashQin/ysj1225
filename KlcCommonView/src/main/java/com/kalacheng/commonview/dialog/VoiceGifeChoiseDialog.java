package com.kalacheng.commonview.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.VoiceSendGiftChoseAdpater;
import com.klc.bean.SendGiftPeopleBean;

import java.util.List;

public class VoiceGifeChoiseDialog {
    private static Dialog mNoticeDialog;

    private SendGiftCallBack back;
    public VoiceSendGiftChoseAdpater mVoiceSendGiftChoseAdpater;

    @SuppressLint("WrongConstant")
    public void ShowDialog(Context mContext, final List<SendGiftPeopleBean> mBeanList, boolean hideRoleTip) {

        mNoticeDialog = new Dialog(mContext, R.style.dialog2);
        mNoticeDialog.setContentView(R.layout.voice_live_gift_choise_dialog);
        mNoticeDialog.setCancelable(true);
        mNoticeDialog.setCanceledOnTouchOutside(true);

        Window window = mNoticeDialog.getWindow();
        window.setGravity(Gravity.LEFT);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 100;
        lp.y = -120;
        window.setAttributes(lp);

        mNoticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        RecyclerView mikeList = mNoticeDialog.findViewById(R.id.voice_live_gift_mikeList);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mikeList.setLayoutManager(manager);


//        Collections.sort(miclistsBeanList);
        mVoiceSendGiftChoseAdpater = new VoiceSendGiftChoseAdpater(mContext);
        mVoiceSendGiftChoseAdpater.setHideRoleTip(hideRoleTip);
        mikeList.setAdapter(mVoiceSendGiftChoseAdpater);
        mVoiceSendGiftChoseAdpater.getData(mBeanList);
        mVoiceSendGiftChoseAdpater.setOnItmeClickBack(new VoiceSendGiftChoseAdpater.OnItmeClickBack() {
            @Override
            public void onClick(int poistion) {
                back.onClick(mBeanList.get(poistion));
                mNoticeDialog.dismiss();
            }
        });

        mNoticeDialog.show();
    }

    public static void DismissDialog() {
        if (mNoticeDialog != null) {
            mNoticeDialog.dismiss();
        }

    }

    public void setSendGiftCallBack(SendGiftCallBack callBack) {
        this.back = callBack;
    }

    public interface SendGiftCallBack {
        public void onClick(SendGiftPeopleBean bean);
    }

    public void setDismiss() {
        mNoticeDialog.dismiss();
    }
}
