package com.kalacheng.centercommon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.centercommon.R;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.base.http.HttpApiCallBack;

/**
 * 个性签名
 */
@Route(path = ARouterPath.PersonalActivity)
public class PersonalActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.EDIT_USER_PERSONAL)
    public String name;

    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initView();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etName.setText(name);
        TextView rightTextView = findViewById(R.id.tvOther);
        rightTextView.setText(WordUtil.getString(R.string.save));
        rightTextView.setVisibility(View.VISIBLE);
        rightTextView.setOnClickListener(this);
        setTitle("个性签名");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvOther) {
            name = etName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                ToastUtil.show("个性签名不能为空");
                return;
            }
            HttpApiAppUser.user_update(null, null, null, null, null, -1, -1, null, -1, null, -1, name, null, null, null, -1, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        ToastUtil.show("修改成功");
                        Intent intent = new Intent();
                        intent.putExtra("personal", name);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }
    }
}
