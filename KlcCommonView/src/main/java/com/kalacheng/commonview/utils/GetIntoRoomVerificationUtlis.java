package com.kalacheng.commonview.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.util.R;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.util.utils.DialogUtil;

//进入房间验证
public class GetIntoRoomVerificationUtlis {
    private static volatile GetIntoRoomVerificationUtlis singleton;

    private GetIntoRoomVerificationUtlis() {
    }

    public static GetIntoRoomVerificationUtlis getInstance() {

        if (singleton == null) {
            synchronized (GetIntoRoomVerificationUtlis.class) {
                if (singleton == null) {
                    singleton = new GetIntoRoomVerificationUtlis();
                }
            }
        }
        return singleton;
    }


    //金币不足
    //，code:1成功；2对方正忙；3对方正在通话！;4对方不在线；9用户余额不足无法邀请通话；10不能向自己发起邀请； 11数据错误12贵族才能通话13用户和用户不能打电话14主播和主播不能打电话15对方开启了勿扰16主播正忙
    // 17.短视频金币支付
    public void getTipsDialog(Context mContext, final int type) {
        final Dialog dialog = DialogUtil.getBaseDialog(mContext, R.style.dialog2, R.layout.verification_tips_dialog, true, true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        TextView title = ((TextView) dialog.findViewById(R.id.title));
        TextView title2 = ((TextView) dialog.findViewById(R.id.title2));
        if (type == 1) {
            title.setText("你的余额不足");
            title2.setText("先去充值吧");
        } else if (type == 2) {
            title.setText("你还不是贵族");
            title2.setText("无法进行1v1通话功能");
        } else if (type == 3) {
            title.setText("对方正在忙");
            title2.setText("请稍后拨打");
        } else if (type == 4) {
            title.setText("对方正在通话");
            title2.setText("请稍后拨打");
        } else if (type == 5) {
            title.setText("对方不在线");
            title2.setText("请稍后拨打");
        } else if (type == 6) {
            title.setText("对方是用户");
            title2.setText("不能进行通话");
        } else if (type == 7) {
            title.setText("对方是主播");
            title2.setText("不能进行通话");
        } else if (type == 8) {
            title.setText("不能向自己发起邀请");
            title2.setText("");
        } else if (type == 9) {
            title.setText("对方开启了勿扰");
            title2.setText("");
        } else if (type == 10) {
            title.setText("主播正忙");
            title2.setText("");
        }


        TextView tv_sure = dialog.findViewById(R.id.tv_sure);
        if (type == 1) {
            tv_sure.setText("立即充值");
        } else if (type == 2) {
            tv_sure.setText("开通贵族");
        } else if (type == 3) {
            tv_sure.setText("知道了");
        } else if (type == 4) {
            tv_sure.setText("知道了");
        } else if (type == 5) {
            tv_sure.setText("知道了");
        } else if (type == 6) {
            tv_sure.setText("知道了");
        } else if (type == 7) {
            tv_sure.setText("知道了");
        } else if (type == 8) {
            tv_sure.setText("知道了");
        } else if (type == 9) {
            tv_sure.setText("知道了");
        } else if (type == 10) {
            tv_sure.setText("知道了");
        }
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
                    dialog.dismiss();
                } else if (type == 2) {
                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl()
                            + HttpConstants.URL_NOBLE +"_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken()).navigation();
                    dialog.dismiss();
                } else if (type == 3) {
                    dialog.dismiss();
                } else if (type == 4) {
                    dialog.dismiss();
                } else if (type == 5) {
                    dialog.dismiss();
                } else if (type == 6) {
                    dialog.dismiss();
                } else if (type == 7) {
                    dialog.dismiss();
                } else if (type == 8) {
                    dialog.dismiss();
                } else if (type == 9) {
                    dialog.dismiss();
                } else if (type == 10) {
                    dialog.dismiss();
                }

            }
        });
        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // 半透明 蓝底 通用提示框
    public void showTipsDialog(Context mContext, String tips, String buttonText) {
        final Dialog dialog = DialogUtil.getBaseDialog(mContext, R.style.dialog2, R.layout.verification_tips_dialog, true, true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        TextView title = ((TextView) dialog.findViewById(R.id.title));
        TextView title2 = ((TextView) dialog.findViewById(R.id.title2));
        TextView tv_sure = dialog.findViewById(R.id.tv_sure);
        title2.setVisibility(View.GONE);

        if (!tips.isEmpty()){
            title.setText(tips);
        }
        tv_sure.setText(buttonText);

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
