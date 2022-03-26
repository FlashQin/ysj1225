package com.kalacheng.commonview.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.base.event.TokenInvalidEvent;
import com.kalacheng.base.socket.SocketUtils;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 执行成功dialog
 */
public class ShowSuccessDialog extends BaseDialogFragment {

    /**
     * 要显示的成功提示语
     */
    private String showMsg;

    /**
     * 是否为 账号注销
     * true 标识 是
     */
    private boolean isAccountCancellation = false;

    public ShowSuccessDialog() {
    }

    private ShoSuccessCallback callback;

    public ShowSuccessDialog(String showMsg, ShoSuccessCallback callback) {
        this.showMsg = showMsg;
        this.callback = callback;
    }

    public ShowSuccessDialog(String showMsg, boolean isAccountCancellation) {
        this.showMsg = showMsg;
        this.isAccountCancellation = isAccountCancellation;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_show_success;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

        ((TextView) mRootView.findViewById(R.id.show_msg)).setText(StringUtil.isEmpty(showMsg) ? "" : showMsg);


        mRootView.findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                //账号注销弹出处理逻辑
                if (isAccountCancellation) {
                    SpUtil.getInstance().clearLoginInfo();
                    SocketUtils.stopSocket();

//                    /*------ 推送(退出登入注销对应推送别名) S -----------------------------------------------------------------*/
//                    //退出登录 注销推送别名
//                    AliasEvent alias = SpUtil.getInstance().getModel(SpUtil.USER_ALIAS, AliasEvent.class);
//                    if (null != alias && null != alias.getAlias() && alias.getAlias().length() > 0) {
//                        //先注销 之前的别名
//                        PushRegisterSet.unsetAlias(BaseApplication.getInstance(), alias.getAlias());
//                    }
//                    /*------ 推送(退出登入注销对应推送别名) E -----------------------------------------------------------------*/

                    EventBus.getDefault().post(new TokenInvalidEvent());
                } else {
                    if (null != callback) {
                        callback.dismissCallback(tag);
                    }
                }

            }
        });
    }

    public interface ShoSuccessCallback {
        /**
         * 页面销毁后的处理回调
         *
         * @param tag 弹出框标识
         */
        void dismissCallback(String tag);
    }
}
