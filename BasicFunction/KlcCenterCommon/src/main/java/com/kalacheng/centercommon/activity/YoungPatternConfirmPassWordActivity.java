package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiYunthModel;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;

/*
* 青上年模式修改密码
* */
@Route(path = ARouterPath.YoungPatternConfirmPassWordActivity)
public class YoungPatternConfirmPassWordActivity extends BaseActivity {

    private boolean isConfirm = false;

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youngpattern_setpassword);
        setTitle("修改密码");

        getInitView();
    }

    public void getInitView(){
        TextView YoungPattern_Title = findViewById(R.id.YoungPattern_Title);
        YoungPattern_Title.setText("请确认密码");

        TextView YoungPattern_forgetPassword = findViewById(R.id.YoungPattern_forgetPassword);
        YoungPattern_forgetPassword.setVisibility(View.GONE);

        final EditText YoungPattern_Password = findViewById(R.id.YoungPattern_Password);
        final EditText YoungPattern_Password1 = findViewById(R.id.YoungPattern_Password1);
        final EditText YoungPattern_Password2 = findViewById(R.id.YoungPattern_Password2);
        final EditText YoungPattern_Password3 = findViewById(R.id.YoungPattern_Password3);
        final EditText YoungPattern_Password4 = findViewById(R.id.YoungPattern_Password4);


        YoungPattern_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pwd = editable.toString();
                if (pwd.length() == 0) {
                    YoungPattern_Password1.setText("");
                    YoungPattern_Password2.setText("");
                    YoungPattern_Password3.setText("");
                    YoungPattern_Password4.setText("");
                } else if (pwd.length() == 1) {
                    YoungPattern_Password1.setText(pwd);
                    YoungPattern_Password2.setText("");
                    YoungPattern_Password3.setText("");
                    YoungPattern_Password4.setText("");
                } else if (pwd.length() == 2) {
                    YoungPattern_Password2.setText(pwd.substring(1, 2));
                    YoungPattern_Password3.setText("");
                    YoungPattern_Password4.setText("");
                } else if (pwd.length() == 3) {
                    YoungPattern_Password3.setText(pwd.substring(2, 3));
                    YoungPattern_Password4.setText("");
                } else if (pwd.length() == 4) {
                    YoungPattern_Password4.setText(pwd.substring(3, 4));
                    setPwy(pwd.toString());
                }
            }
        });
    }
    //修改密码
    public void setPwy(String pwy){
        HttpApiYunthModel.updateYunthPwd(pwy, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code ==1){
                    ARouter.getInstance().build(ARouterPath.YoungPatternSetPassWordActivity).withInt("isOpenYoung",1).withInt("isforget",2).navigation(mContext, new NavigationCallback() {
                        @Override
                        public void onFound(Postcard postcard) {

                        }

                        @Override
                        public void onLost(Postcard postcard) {

                        }

                        @Override
                        public void onArrival(Postcard postcard) {
                            finish();
                        }

                        @Override
                        public void onInterrupt(Postcard postcard) {

                        }
                    });

                }else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
