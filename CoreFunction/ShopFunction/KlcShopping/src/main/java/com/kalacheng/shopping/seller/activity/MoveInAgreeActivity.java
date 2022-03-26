package com.kalacheng.shopping.seller.activity;


import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityMoveInAgreeBinding;
import com.kalacheng.shopping.seller.viewmodel.MoveInAgreeViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ToastUtil;

/**
 * 入驻协议
 */
@Route(path = ARouterPath.MoveInAgreeActivity)
public class MoveInAgreeActivity extends SBaseActivity<ActivityMoveInAgreeBinding, MoveInAgreeViewModel> {
    @Autowired(name = ARouterValueNameConfig.TITLE_NAME)
    public String titleName;
    @Autowired(name = ARouterValueNameConfig.POST)
    public String post;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_move_in_agree;
    }

    @Override
    public void initData() {
        super.initData();

        binding.titleNameTv.setText(titleName);
        binding.postTv.setText(Html.fromHtml(post));

        binding.agreeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.isAgreeCb.isChecked()) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.UpShopDataActivity).navigation();
                    finish();
                } else {
                    ToastUtil.show("请阅读入驻协议并同意协议");
                }
            }
        });
    }
}
