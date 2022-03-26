package com.kalacheng.one2onelive.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalacheng.one2onelive.R;


public class SvipSearchDialog {

    private static volatile SvipSearchDialog singleton;
    private SvipSearchCallBack svipSearchCallBack;

    private SvipSearchDialog() {
    }

    public static SvipSearchDialog getInstance() {

        if (singleton == null) {
            synchronized (SvipSearchDialog.class) {
                if (singleton == null) {
                    singleton = new SvipSearchDialog();
                }
            }
        }
        return singleton;
    }

    private int genderType = 0;

    public void show(final Context mContext) {

        final Dialog dialog = new Dialog(mContext, R.style.dialog);
        dialog.setContentView(R.layout.svip_search_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);


        final LinearLayout girl = dialog.findViewById(R.id.girl);
        final LinearLayout boy = dialog.findViewById(R.id.boy);
        final LinearLayout anyway = dialog.findViewById(R.id.anyway);

        final ImageView girl_img = dialog.findViewById(R.id.girl_img);
        final ImageView boy_img = dialog.findViewById(R.id.boy_img);
        final ImageView anyway_img = dialog.findViewById(R.id.anyway_img);
        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                girl_img.setBackgroundResource(R.mipmap.svip_unseleted);
                boy_img.setBackgroundResource(R.mipmap.svip_seleted);
                anyway_img.setBackgroundResource(R.mipmap.svip_seleted);

                genderType = 2;
            }
        });

        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genderType = 1;
                girl_img.setBackgroundResource(R.mipmap.svip_seleted);
                boy_img.setBackgroundResource(R.mipmap.svip_unseleted);
                anyway_img.setBackgroundResource(R.mipmap.svip_seleted);
            }
        });


        anyway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                girl_img.setBackgroundResource(R.mipmap.svip_seleted);
                boy_img.setBackgroundResource(R.mipmap.svip_seleted);
                anyway_img.setBackgroundResource(R.mipmap.svip_unseleted);
                genderType = 0;
            }
        });

        TextView confirm = dialog.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svipSearchCallBack.onClick(genderType);
                dialog.dismiss();
            }
        });
        TextView canle = dialog.findViewById(R.id.canle);
        canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setSvipSearchCallBack(SvipSearchCallBack back) {
        this.svipSearchCallBack = back;
    }

    public interface SvipSearchCallBack {
        public void onClick(int type);
    }
}
