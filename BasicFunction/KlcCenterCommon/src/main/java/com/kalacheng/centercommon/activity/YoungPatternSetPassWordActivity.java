package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiYunthModel;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.http.HttpApiCallBack;

/*
* 青上年模式设置密码
* */
@Route(path = ARouterPath.YoungPatternSetPassWordActivity)
public class YoungPatternSetPassWordActivity extends BaseActivity {
    @Autowired(name = "isOpenYoung")
    int isOpenYoung;
    @Autowired(name = "isforget")
    int isforget;//1 正常进入 2，忘记密码

    private boolean isConfirm = false;

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youngpattern_setpassword);



    }

    @Override
    protected void onResume() {
        super.onResume();
        getInitView();
    }

    public void getInitView(){
        TextView YoungPattern_forgetPassword = findViewById(R.id.YoungPattern_forgetPassword);
        TextView YoungPattern_Title = findViewById(R.id.YoungPattern_Title);

        if (isOpenYoung == 1){
            setTitle("退出青少年模式");
            YoungPattern_Title.setText("请输入密码");
            if (isforget == 1){
                YoungPattern_forgetPassword.setVisibility(View.VISIBLE);
                YoungPattern_forgetPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build(ARouterPath.AccountCancellationConfirmActivity).withInt("type",2).navigation();
                        finish();
                    }
                });
            }else {
                YoungPattern_forgetPassword.setVisibility(View.VISIBLE);

            }

        }else {
            setTitle("开启青少年模式");
            YoungPattern_Title.setText("设置数字密码");
            YoungPattern_forgetPassword.setVisibility(View.GONE);
        }



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

    private int isYouthModel;
    public void setPwy(String parssword){
        if(isOpenYoung == 1){
            isYouthModel = 2;
        }else {
            isYouthModel =1;
        }
        HttpApiYunthModel.setYunthModel(isYouthModel, parssword, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1){
                    finish();
                }else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
