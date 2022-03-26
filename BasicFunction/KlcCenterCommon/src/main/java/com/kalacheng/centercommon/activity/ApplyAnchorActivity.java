package com.kalacheng.centercommon.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ActivityApplyAnchorBinding;
import com.kalacheng.centercommon.viewmodel.ApplyAnchorViewModel;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libuser.model.AppUsersAuth;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.StringUtil;

/**
 * 申请认证
 */
@Route(path = ARouterPath.ApplyAnchorActivity)
public class ApplyAnchorActivity extends BaseMVVMActivity<ActivityApplyAnchorBinding, ApplyAnchorViewModel> implements View.OnClickListener {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_apply_anchor;
    }

    @Override
    public void initData() {
        setTitle("申请认证");
        ApiUserInfo info = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
        if (info != null) {
            if (!TextUtils.isEmpty(info.mobile) && !"-1".equals(info.mobile)) {
                binding.editPhone.setText(info.mobile);
            }
            if (!TextUtils.isEmpty(info.wechatNo) && !"-1".equals(info.wechatNo)) {
                binding.editWechat.setText(info.wechatNo);
            }
        }

        binding.btnNext.setOnClickListener(this);
        binding.tvAgreement.setOnClickListener(this);
        if (ConfigUtil.getBoolValue(R.bool.isVideo)) {
            binding.tvAgreement.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {
            /**
             * 主播认证第一步
             *
             * @param cerNo     身份证号码
             * @param extraInfo 附加信息
             * @param mobile    手机号码
             * @param qq        qq号
             * @param realName  真实姓名
             * @param wechat    微信号
             */
            String cerNo = binding.editId.getText().toString().trim();
            String extraInfo = binding.editMore.getText().toString().trim();
            String mobile = binding.editPhone.getText().toString().trim();
            String qq = binding.editQq.getText().toString().trim();
            String realName = binding.editName.getText().toString().trim();
            String wechat = binding.editWechat.getText().toString().trim();
            if (TextUtils.isEmpty(realName)) {
                ToastUtil.show("姓名不能为空！");
                return;
            }
            if (TextUtils.isEmpty(cerNo)) {
                ToastUtil.show("身份证号不能为空！");
                return;
            }
            if (TextUtils.isEmpty(mobile)) {
                ToastUtil.show("手机号不能为空");
                return;
            }
            if (!StringUtil.isPhoneNum(mobile)) {
                ToastUtil.show("请输入正确的手机号码");
                return;
            }
            HttpApiAPPAnchor.authFirst(cerNo, extraInfo, mobile, qq, realName, wechat, new HttpApiCallBack<AppUsersAuth>() {
                @Override
                public void onHttpRet(int code, String msg, AppUsersAuth retModel) {
                    if (code == 1) {
                        ToastUtil.show("信息上传成功");
                        ARouter.getInstance().build(ARouterPath.UpLoadIdCardActivity).navigation();
                        finish();
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        } else if (view.getId() == R.id.tv_agreement) {
            ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, HttpClient.getInstance().getUrl() + "/api/login/appSite?type=5").navigation();
        }

    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }
}
