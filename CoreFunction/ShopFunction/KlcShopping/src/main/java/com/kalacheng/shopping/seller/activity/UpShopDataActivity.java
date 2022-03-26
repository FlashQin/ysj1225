package com.kalacheng.shopping.seller.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.model.AppMerchantAgreementDTO;
import com.kalacheng.busshop.model.ShopBusiness;
import com.kalacheng.busshop.model_fun.ShopBusiness_applicationForResidence;
import com.kalacheng.busshop.model_fun.ShopBusiness_updateBusiness;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityUpShopDataBinding;
import com.kalacheng.shopping.seller.adapter.UpImgAdapter;
import com.kalacheng.shopping.seller.viewmodel.UpShopDataViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;
import java.util.Arrays;

/**
 * 商家简介
 */
@Route(path = ARouterPath.UpShopDataActivity)
public class UpShopDataActivity extends SBaseActivity<ActivityUpShopDataBinding, UpShopDataViewModel> implements ImageResultCallback {

    UpImgAdapter photoAdapter;
    UpImgAdapter legalAdapter;
    String logoUrl;

    int type = 0;

    private ProcessImageUtil mImageUtil;

    ShopBusiness_applicationForResidence mdl;
    ShopBusiness_updateBusiness updateBusiness;
    ShopBusiness appBusiness;

    @Autowired(name = ARouterValueNameConfig.isUp)
    public int isUp = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_up_shop_data;
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    public void initData() {
        super.initData();

        mImageUtil = new ProcessImageUtil(this);
        mImageUtil.setImageResultCallback(this);

        mdl = new ShopBusiness_applicationForResidence();

        binding.shopLogoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog(0);
            }
        });

        binding.submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (isUp == 1) {
                    modifyData();
                } else {
                    upData();
                }
            }
        });

        binding.shopDataEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.scrollView.scrollTo(0, binding.shopLogoIv.getBottom());
                }
            }
        });
        binding.shopDataEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.scrollView.scrollTo(0, binding.shopLogoIv.getBottom());
            }
        });

        binding.photoRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.photoRecyclerView.setHasFixedSize(true);
        binding.photoRecyclerView.setNestedScrollingEnabled(false);
        photoAdapter = new UpImgAdapter();
        photoAdapter.setListener(new UpImgAdapter.addImgClickListener() {
            @Override
            public void addImgClick(UpImgAdapter adapter) {
                showImageDialog(1);
            }
        });
        binding.photoRecyclerView.setAdapter(photoAdapter);

        binding.legalRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.legalRecyclerView.setHasFixedSize(true);
        binding.legalRecyclerView.setNestedScrollingEnabled(false);
        legalAdapter = new UpImgAdapter("add1");
        legalAdapter.setListener(new UpImgAdapter.addImgClickListener() {
            @Override
            public void addImgClick(UpImgAdapter adapter) {
                showImageDialog(2);
            }
        });
        binding.legalRecyclerView.setAdapter(legalAdapter);

        if (isUp == 1) {
            getOne();
        }

    }

    private void showImageDialog(int i) {
        type = i;
        DialogUtil.showStringArrayDialog(this, new Integer[]{
                R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.camera) {
                    mImageUtil.getImageByCamera(false);
                } else if (tag == R.string.alumb) {
                    mImageUtil.getImageByAlbumCustom(false);
                }
            }
        });
    }

    @Override
    public void beforeCamera() {

    }

    @Override
    public void onSuccess(File file) {
        final Dialog dialog = DialogUtil.loadingDialog(this);
        dialog.show();
        UploadUtil.getInstance().uploadPicture(1, file, new PictureUploadCallback() {
            @Override
            public void onSuccess(String imgStr) {
                if (type == 1) {
                    photoAdapter.addImg(imgStr);
                } else if (type == 2) {
                    legalAdapter.addImg(imgStr);
                } else {
                    logoUrl = imgStr;
                    ImageLoader.display(imgStr, binding.shopLogoIv, R.mipmap.upload_merchant_logo, R.mipmap.upload_merchant_logo);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure() {
                ToastUtil.show("图片上传失败");
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onFailure() {
        ToastUtil.show("图片选取失败");
    }

    /**
     * 提交资料 申请入驻商家
     */
    public void upData() {
        if (TextUtils.isEmpty(logoUrl)) {
            ToastUtil.show("请上传商家Logo");
            return;
        } else {
            mdl.logo = logoUrl;
        }

        if (TextUtils.isEmpty(binding.shopNameEt.getText().toString().trim())) {
            ToastUtil.show("请填写商家名称");
            return;
        } else {
            mdl.name = binding.shopNameEt.getText().toString().trim();
        }

        if (TextUtils.isEmpty(binding.shopPhoneEt.getText().toString().trim())) {
            ToastUtil.show("请填写商家电话");
            return;
        } else {
            mdl.mobile = binding.shopPhoneEt.getText().toString().trim();
        }

        if (TextUtils.isEmpty(binding.shopDataEt.getText().toString().trim())) {
            mdl.present = "";
        } else {
            mdl.present = binding.shopDataEt.getText().toString().trim();
        }

        if (photoAdapter.getImgs().size() > 0) {
            mdl.presentPicture = TextUtils.join(",", photoAdapter.getImgs());
        } else {
            mdl.presentPicture = "";
        }

        if (legalAdapter.getImgs().size() > 0) {
            mdl.businessLicense = TextUtils.join(",", legalAdapter.getImgs());
        } else {
            mdl.businessLicense = "";
        }

        HttpApiShopBusiness.applicationForResidence(mdl, new HttpApiCallBack<AppMerchantAgreementDTO>() {
            @Override
            public void onHttpRet(int code, String msg, AppMerchantAgreementDTO retModel) {
                if (code == 1) {
                    ARouter.getInstance().build(ARouterPath.ShopManageActivity)
                            .withString(ARouterValueNameConfig.AuditRemake, retModel.remake)
                            .withInt(ARouterValueNameConfig.AuditStatus, retModel.status)
                            .navigation();
                    finish();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 修改商家资料
     */
    public void modifyData() {

        if (TextUtils.isEmpty(logoUrl)) {
            ToastUtil.show("请上传商家Logo");
            return;
        } else {
            updateBusiness.logo = logoUrl;
        }

        if (TextUtils.isEmpty(binding.shopNameEt.getText().toString().trim())) {
            ToastUtil.show("请填写商家名称");
            return;
        } else {
            updateBusiness.name = binding.shopNameEt.getText().toString().trim();
        }

        if (TextUtils.isEmpty(binding.shopPhoneEt.getText().toString().trim())) {
            ToastUtil.show("请填写商家电话");
            return;
        } else {
            updateBusiness.mobile = binding.shopPhoneEt.getText().toString().trim();
        }

        if (TextUtils.isEmpty(binding.shopDataEt.getText().toString().trim())) {
            updateBusiness.present = "";
        } else {
            updateBusiness.present = binding.shopDataEt.getText().toString().trim();
        }

        if (photoAdapter.getImgs().size() > 0) {
            updateBusiness.presentPicture = TextUtils.join(",", photoAdapter.getImgs());
        } else {
            updateBusiness.presentPicture = "";
        }

        if (legalAdapter.getImgs().size() > 0) {
            updateBusiness.businessLicense = TextUtils.join(",", legalAdapter.getImgs());
        } else {
            updateBusiness.businessLicense = "";
        }

        if (equals()) {
            finish();
            return;
        }
        HttpApiShopBusiness.updateBusiness(updateBusiness, new HttpApiCallBack<AppMerchantAgreementDTO>() {
            @Override
            public void onHttpRet(int code, String msg, AppMerchantAgreementDTO retModel) {
                if (code == 1) {
                    BaseApplication.containsActivity("ShopManageActivity");
                    ARouter.getInstance().build(ARouterPath.ShopManageActivity)
                            .withString(ARouterValueNameConfig.AuditRemake, retModel.remake)
                            .withInt(ARouterValueNameConfig.AuditStatus, retModel.status)
                            .navigation();
                    finish();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 获取商家信息
     */
    public void getOne() {
        HttpApiShopBusiness.getOne(new HttpApiCallBack<ShopBusiness>() {
            @Override
            public void onHttpRet(int code, String msg, ShopBusiness retModel) {
                if (code == 1) {
                    appBusiness = retModel;
                    updateBusiness = new ShopBusiness_updateBusiness();
                    updateBusiness.businessId = retModel.id;
                    logoUrl = retModel.logo;
                    ImageLoader.display(retModel.logo, binding.shopLogoIv, R.mipmap.upload_merchant_logo, R.mipmap.upload_merchant_logo);
                    binding.shopNameEt.setText(retModel.name);
                    binding.shopPhoneEt.setText(retModel.mobile);
                    binding.shopDataEt.setText(retModel.present);
                    if (!TextUtils.isEmpty(retModel.presentPicture)) {
                        photoAdapter.addImgs(Arrays.asList(retModel.presentPicture.split(",")));
                    }
                    if (!TextUtils.isEmpty(retModel.businessLicense)) {
                        legalAdapter.addImgs(Arrays.asList(retModel.businessLicense.split(",")));
                    }

                    binding.underReview.setVisibility(appBusiness.status == 1 ? View.VISIBLE : View.GONE);
                }
            }
        });
    }

    /**
     * 比较是否有修改内容
     *
     * @return
     */
    public boolean equals() {
        return updateBusiness.logo.equals(appBusiness.logo)
                && updateBusiness.name.equals(appBusiness.name)
                && updateBusiness.mobile.equals(appBusiness.mobile)
                && updateBusiness.present.equals(appBusiness.present)
                && updateBusiness.presentPicture.equals(appBusiness.presentPicture)
                && updateBusiness.businessLicense.equals(appBusiness.businessLicense);
    }

}
