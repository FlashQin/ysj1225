package com.kalacheng.centercommon.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.buscommon.model.UserLogoutVerificationDTO;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.httpApi.HttpApiBingAccount;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;

/**
 * 用户注销
 */
@Route(path = ARouterPath.AccountCancellationActivity)
public class AccountCancellationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_cancellation);
        intView();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void intView() {
        setTitle("账号注销");

        ((TextView) findViewById(R.id.tvUserCancel)).setText(Html.fromHtml((String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_USER_CANCEL, "")));
        findViewById(R.id.tvNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                HttpApiBingAccount.logoutVerification(new HttpApiCallBack<UserLogoutVerificationDTO>() {
                    @Override
                    public void onHttpRet(int code, String msg, UserLogoutVerificationDTO retModel) {
                        if (code == 1 && retModel != null) {
                            if (retModel.role == 0) {//用户
                                if (retModel.coin > 0) {
                                    //用户提示框
                                    showUserTipDialog(retModel);
                                } else {
                                    goNext();
                                }
                            } else {//主播
                                if (retModel.logOffSwitch == 0) {//开关，开启
                                    if (retModel.votes > 0) {
                                        //主播提示框
                                        showAnchorTipDialog(retModel);
                                    } else {
                                        if (retModel.coin > 0) {
                                            //用户提示框
                                            showUserTipDialog(retModel);
                                        } else {
                                            goNext();
                                        }
                                    }
                                } else {//开关，关闭
                                    if (retModel.coin > 0) {
                                        //用户提示框
                                        showUserTipDialog(retModel);
                                    } else {
                                        goNext();
                                    }
                                }
                            }
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }
        });
    }

    /**
     * 进入下一页
     */
    private void goNext() {
        ARouter.getInstance().build(ARouterPath.AccountCancellationConfirmActivity).withInt("type", 1).navigation();
    }

    /**
     * 用户提示框
     */
    private void showUserTipDialog(UserLogoutVerificationDTO retModel) {
        showDialog("你的账户还有" + retModel.coin + SpUtil.getInstance().getCoinUnit() + "，可以先消费完再注销哦！", "确认注销", "去花" + SpUtil.getInstance().getCoinUnit(),
                new OnAccountCancellationListener() {
                    @Override
                    public void onCancel() {
                        goNext();
                    }

                    @Override
                    public void onConfirm() {

                    }
                });
    }

    /**
     * 主播提示框
     */
    private void showAnchorTipDialog(UserLogoutVerificationDTO retModel) {
        showDialog("你的可提现收益为" + retModel.votes + "映票，请先提现后再注销账户！", "取消", "我知道了",
                new OnAccountCancellationListener() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onConfirm() {

                    }
                });
    }

    private void showDialog(String tip, String strCancel, String strConfirm, final OnAccountCancellationListener listener) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(R.layout.dialog_account_cancellation);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        ((TextView) dialog.findViewById(R.id.tvTip)).setText(tip);
        ((TextView) dialog.findViewById(R.id.tvCancel)).setText(strCancel);
        ((TextView) dialog.findViewById(R.id.tvConfirm)).setText(strConfirm);
        dialog.findViewById(R.id.tvCancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onCancel();
                        }
                        dialog.dismiss();
                    }
                }
        );
        dialog.findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onConfirm();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public interface OnAccountCancellationListener {
        void onCancel();

        void onConfirm();
    }
}
