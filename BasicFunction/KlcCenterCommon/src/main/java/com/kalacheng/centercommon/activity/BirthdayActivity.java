package com.kalacheng.centercommon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.commonview.dialog.DatePickerDialog;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;

/**
 * 编辑生日
 */
@Route(path = ARouterPath.BirthdayActivity)
public class BirthdayActivity extends BaseActivity implements View.OnClickListener {
    TextView tvBirthday, tvConstellation;
    @Autowired(name = ARouterValueNameConfig.EDIT_USER_BIRTH)
    String birthday;
    @Autowired(name = ARouterValueNameConfig.EDIT_USER_CONSTELLATION)
    String constellation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        initView();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView() {
        tvBirthday = findViewById(R.id.tv_birthday);
        tvConstellation = findViewById(R.id.tv_constellation);
        tvBirthday.setText(birthday);
        tvConstellation.setText(constellation);
        TextView rightTextView = findViewById(R.id.tvOther);
        rightTextView.setText(WordUtil.getString(R.string.save));
        rightTextView.setVisibility(View.VISIBLE);
        rightTextView.setOnClickListener(this);
        setTitle("生日");
        findViewById(R.id.ll_birthday).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvOther) {
            HttpApiAppUser.user_update(null, null, birthday, null, constellation, -1, -1, null, -1, null, -1, null, null, null, null, -1, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        ToastUtil.show("修改成功");
                        Intent intent = new Intent();
                        intent.putExtra("birthday", birthday);
                        intent.putExtra("constellation", constellation);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        } else if (view.getId() == R.id.ll_birthday) {
            showDialog();
        }
    }

    private void showDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        datePickerDialog.show(getSupportFragmentManager(), "DatePickerDialog");
        datePickerDialog.setOnDataPickerListener(new DatePickerDialog.OnDataPickerListener() {
            @Override
            public void onConfirm(String date) {
                birthday = date;
                tvBirthday.setText(date);
                String string[] = date.split("-");
                int month = Integer.parseInt(string[1].toString());
                int dateInt = Integer.parseInt(string[2].toString());
                switch (month) {
                    case 1:
                        if (dateInt < 21)
                            constellation = "摩羯座";
                        else
                            constellation = "水瓶座";
                        break;
                    case 2:
                        if (dateInt < 20)
                            constellation = "水瓶座";
                        else
                            constellation = "双鱼座";
                        break;
                    case 3:
                        if (dateInt < 21)
                            constellation = "双鱼座";
                        else
                            constellation = "白羊座";
                        break;
                    case 4:
                        if (dateInt < 21)
                            constellation = "白羊座";
                        else
                            constellation = "金牛座";
                        break;
                    case 5:
                        if (dateInt < 22)
                            constellation = "金牛座";
                        else
                            constellation = "双子座";
                        break;
                    case 6:
                        if (dateInt < 22)
                            constellation = "双子座";
                        else
                            constellation = "巨蟹座";
                        break;
                    case 7:
                        if (dateInt < 23)
                            constellation = "巨蟹座";
                        else
                            constellation = "狮子座";
                        break;
                    case 8:
                        if (dateInt < 24)
                            constellation = "狮子座";
                        else
                            constellation = "处女座";
                        break;
                    case 9:
                        if (dateInt < 24)
                            constellation = "处女座";
                        else
                            constellation = "天秤座";
                        break;
                    case 10:
                        if (dateInt < 24)
                            constellation = "天秤座";
                        else
                            constellation = "天蝎座";
                        break;
                    case 11:
                        if (dateInt < 23)
                            constellation = "天蝎座";
                        else
                            constellation = "射手座";
                        break;
                    case 12:
                        if (dateInt < 22)
                            constellation = "射手座";
                        else
                            constellation = "摩羯座";
                        break;
                }
                tvConstellation.setText(constellation);
            }
        });
    }
}
