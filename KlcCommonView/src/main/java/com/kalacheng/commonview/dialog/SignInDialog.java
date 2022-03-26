package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.R;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiSignInDto;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.FlowLayout;
import com.kalacheng.base.http.HttpApiCallBack;

public class SignInDialog extends Dialog {
    public SignInDialog(@NonNull Context context, ApiSignInDto signInDto, final SignInListener signInListener) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_sign_in);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        FlowLayout flowLayout = findViewById(R.id.flowLayout);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final LinearLayout prizeLl = findViewById(R.id.prizeLl);
        ImageView dayPrizeIv = findViewById(R.id.dayPrizeIv);

        final TextView okTv = findViewById(R.id.okTv);
        if (signInDto != null) {
            okTv.setText(signInDto.isSign == 0 ? "立即签到" : "已存入背包");
            if (signInDto.isSign == 0) {
                okTv.setBackgroundResource(R.drawable.bg_sign_in_btn);
                okTv.setTextColor(Color.WHITE);
            } else {
                okTv.setBackgroundResource(R.drawable.bg_sign_in_btn_clicked);
                okTv.setTextColor(Color.parseColor("#999999"));
            }
            okTv.setEnabled(signInDto.isSign == 0);
            okTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    HttpApiAppUser.signIn(new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (code == 1) {
                                prizeLl.setVisibility(View.VISIBLE);
                                okTv.setText("已存入背包");
                                okTv.setEnabled(false);
                                okTv.setBackgroundResource(R.drawable.bg_sign_in_btn_clicked);
                                okTv.setTextColor(Color.parseColor("#999999"));
                                if (signInListener != null) {
                                    signInListener.signIn();
                                }
                            } else {
                                ToastUtil.show(msg);
                            }
                        }
                    });
                }
            });

            for (int i = 0; i < signInDto.signList.size(); i++) {
                FrameLayout frameLayout = new FrameLayout(context);
                frameLayout.setLayoutParams(layoutParams);
                View view;
                if (i == 6) {
                    view = LayoutInflater.from(context).inflate(R.layout.item_prize_7, frameLayout, false);
                } else {
                    view = LayoutInflater.from(context).inflate(R.layout.item_prize, frameLayout, false);
                }
                LinearLayout rootView = view.findViewById(R.id.rootView);
                TextView dayNumTv = view.findViewById(R.id.dayNumTv);
                ImageView prizeIv = view.findViewById(R.id.prizeIv);
                TextView nameTv = view.findViewById(R.id.nameTv);
                RelativeLayout receivedRl = view.findViewById(R.id.receivedRl);

                //类型1金币 (男朋友中为钻石)2礼物
                if (signInDto.signList.get(i).type == 1) {
                    ImageLoader.display(R.mipmap.icon_money_big, prizeIv);
                    nameTv.setText(SpUtil.getInstance().getCoinUnit() + "*" + signInDto.signList.get(i).typeVal);
                } else {
                    ImageLoader.display(signInDto.signList.get(i).image, prizeIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    nameTv.setText(signInDto.signList.get(i).name);
                }
                rootView.setBackgroundResource(signInDto.signList.get(i).isGet == 0 ? R.drawable.bg_prize_1 : R.drawable.bg_prize_0);
                dayNumTv.setText("第" + (i + 1) + "天");

                receivedRl.setVisibility(signInDto.signList.get(i).isGet == 1 ? View.VISIBLE : View.GONE);

                if (signInDto.signList.get(i).isGet == 0) {
                    if (signInDto.signList.get(i).type == 1) {
                        ImageLoader.display(R.mipmap.icon_money_big, dayPrizeIv);
                    } else {
                        ImageLoader.display(signInDto.signList.get(i).image, dayPrizeIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                    }
                }

                frameLayout.addView(view);
                flowLayout.addView(frameLayout);
            }
        } else {
            okTv.setVisibility(View.GONE);
        }

        ImageView Sign_close = findViewById(R.id.Sign_close);
        Sign_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public interface SignInListener {
        void signIn();

    }


}
