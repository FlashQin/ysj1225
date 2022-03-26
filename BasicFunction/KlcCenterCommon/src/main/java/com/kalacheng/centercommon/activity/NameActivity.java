package com.kalacheng.centercommon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;

/**
 * 修改昵称
 */
@Route(path = ARouterPath.NameActivity)
public class NameActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.TYPE)
    public int type;
    @Autowired(name = ARouterValueNameConfig.EDIT_USER_OTHER)
    public String name;

    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
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
        findViewById(R.id.iv_close).setOnClickListener(this);
        if (type == 0) {
            setTitle("昵称");
            etName.setInputType(InputType.TYPE_CLASS_TEXT);
            etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            etName.setHint("请输入你的昵称（最多10个字符）");
        } else if (type == 1) {
            setTitle("身高");
            etName.setInputType(InputType.TYPE_CLASS_NUMBER);
            etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
            etName.setHint("请输入你的身高（cm）");
        } else if (type == 2) {
            setTitle("体重");
            etName.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            etName.setHint("请输入你的体重（kg）");
        } else if (type == 3) {
            setTitle("职业");
            etName.setInputType(InputType.TYPE_CLASS_TEXT);
            etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
            etName.setHint("请输入你的职业");
        } else if (type == 5) {
            setTitle("微信号");
            etName.setInputType(InputType.TYPE_CLASS_TEXT);
            etName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
            etName.setHint("请输入你的微信号");
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_close) {
            etName.setText("");
        } else if (view.getId() == R.id.tvOther) {
            name = etName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                if (type == 0) {
                    ToastUtil.show("昵称不能为空");
                } else if (type == 1) {
                    ToastUtil.show("身高不能为空");
                } else if (type == 2) {
                    ToastUtil.show("体重不能为空");
                } else if (type == 3) {
                    ToastUtil.show("职业不能为空");
                } else if (type == 5) {
                    ToastUtil.show("微信号不能为空");
                }
                return;
            }
            if (type == 0) {
                HttpApiAppUser.user_update(null, null, null, null, null, -1, -1, null, -1, null, -1, null, name, null, null, -1, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show("修改成功");
                            Intent intent = new Intent();
                            intent.putExtra("name", name);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });
            } else if (type == 1) {
                HttpApiAppUser.user_update(null, null, null, null, null, Integer.parseInt(name), -1, null, -1, null, -1, null, null, null, null, -1, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show("修改成功");
                            Intent intent = new Intent();
                            intent.putExtra("height", name);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });
            } else if (type == 2) {
                HttpApiAppUser.user_update(null, null, null, null, null, -1, -1, null, -1, null, -1, null, null, null, null, Double.parseDouble(name), new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show("修改成功");
                            Intent intent = new Intent();
                            intent.putExtra("weight", name);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });
            } else if (type == 3) {
                HttpApiAppUser.user_update(null, null, null, null, null, -1, -1, null, -1, null, -1, null, null, name, null, -1, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show("修改成功");
                            Intent intent = new Intent();
                            intent.putExtra("profession", name);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });
            } else if (type == 5) {
                HttpApiAppUser.user_update(null, null, null, null, null, -1, -1, null, -1, null, -1, null, null, null, name, -1, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show("修改成功");
                            Intent intent = new Intent();
                            intent.putExtra("wx", name);
                            ApiUserInfo userInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                            if (userInfo != null) {
                                userInfo.wechatNo = name;
                                SpUtil.getInstance().putModel(SpUtil.USER_INFO, userInfo);
                            }
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });
            }

        }
    }
}
