package com.kalacheng.shopping.seller.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopGoodsCategory;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.busshop.model_fun.ShopGoods_creatGoods;
import com.kalacheng.busshop.model_fun.ShopGoods_updateGoods;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseFragment;
import com.kalacheng.shopping.databinding.FragmentAddOtherGoodsBinding;
import com.kalacheng.shopping.seller.activity.AddGoodsActivity;
import com.kalacheng.shopping.seller.adapter.MoneyValueFilter;
import com.kalacheng.shopping.seller.viewmodel.AddOtherGoodsViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;
import java.util.Objects;

public class AddOtherGoodsFragment extends SBaseFragment<FragmentAddOtherGoodsBinding, AddOtherGoodsViewModel>
        implements ImageResultCallback {

    final int goodsClassifyCode = 888;
    private ShopGoodsCategory appCategory;
    private ShopGoods_creatGoods creatGoods;
    private ShopGoods_updateGoods updateGoods;

    private ProcessImageUtil mImageUtil;
    long channelId;
    String picture;
    ShopGoodsDTO goods;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_add_other_goods;
    }

    @Override
    public void initData() {

        binding.priceEt.setFilters(new InputFilter[]{new MoneyValueFilter()});
        mImageUtil = new ProcessImageUtil(getActivity());
        mImageUtil.setImageResultCallback(this);

//        binding.goodsLinkEt.setText("https://item.m.jd.com/product/100005941389.html?wxa_abtest=a&utm_user=plusmember&ad_od=share&utm_source=androidapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=CopyURL");

        binding.goodsClassifyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                Postcard postcard = ARouter.getInstance().build(ARouterPath.GoodsClassifyActivity);
                LogisticsCenter.completion(postcard);
                Intent intent = new Intent(getActivity(), postcard.getDestination());
                startActivityForResult(intent, goodsClassifyCode);
            }
        });

        binding.shopPhotoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                showImageDialog();
            }
        });

        binding.addGoodsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (goods == null) {
                    creatGoods();
                } else {
                    if (!isNoEdit()) {
                        updateGoods();
                    } else {
                        Objects.requireNonNull(getActivity()).finish();
                    }
                }

            }
        });

        binding.previewGoodsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;

                isGotoUrl();
//                if (TextUtils.isEmpty(binding.goodsLinkEt.getText().toString().trim())) {
//                    ToastUtil.show("请输入商品链接");
//                    return;
//                } else {
//
////                    ARouter.getInstance().build(ARouterPath.WebActivity).withString(ARouterValueNameConfig.WEBURL, binding.goodsLinkEt.getText().toString().trim()).navigation();
//                }
            }
        });

        AddGoodsActivity addGoodsActivity = (AddGoodsActivity) getActivity();
        if (addGoodsActivity != null && addGoodsActivity.goods != null) {
            setGoods(addGoodsActivity.goods);
            updateGoods = new ShopGoods_updateGoods();
            updateGoods.goodsId = goods.goodsId;
            updateGoods.channelId = goods.channelId;
            updateGoods.type = 1;
        } else {
            creatGoods = new ShopGoods_creatGoods();
            creatGoods.channelId = channelId;
            creatGoods.type = 1;
        }

    }

    // 判断是否可以预览商品
    private void isGotoUrl() {
        try {
            if (null != goods) {
                if (verifyUrl(goods.productLinks)) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(goods.productLinks);
                    intent.setData(content_url);
                    startActivity(intent);
                }
            } else {
                if (verifyUrl(binding.goodsLinkEt.getText().toString().trim())) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(binding.goodsLinkEt.getText().toString().trim());
                    intent.setData(content_url);
                    startActivity(intent);
                } else {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean verifyUrl(String url) {
        boolean success = false;
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("http")) {
                success = true;
            } else {
                ToastUtil.show("非商品链接");
            }
        } else {
            ToastUtil.show("请输入商品链接");
        }
        return success;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public void setGoods(ShopGoodsDTO goods) {
        this.goods = goods;
        binding.goodsNameTv.setText(goods.goodsName);
        binding.goodsLinkEt.setText(goods.productLinks);
        binding.priceEt.setText(String.valueOf(goods.price));

        appCategory = new ShopGoodsCategory();
        appCategory.id = goods.categoryId;
        binding.goodsClassifyTv.setText(goods.categoryName);
        picture = goods.goodsPicture;
        ImageLoader.display(goods.goodsPicture, binding.shopPhotoIv);

        binding.addGoodsTv.setText("立即修改");

    }

    private void creatGoods() {

        if (TextUtils.isEmpty(binding.goodsLinkEt.getText().toString().trim())) {
            ToastUtil.show("请输入商品链接");
            return;
        }

        if (!binding.goodsLinkEt.getText().toString().trim().startsWith("http")) {
            ToastUtil.show("非商品链接");
            return;
        } else {
            creatGoods.productLinks = binding.goodsLinkEt.getText().toString().trim();
        }

        if (TextUtils.isEmpty(binding.goodsNameTv.getText().toString().trim())) {
            ToastUtil.show("请输入商品名称");
            return;
        } else {
            creatGoods.goodsName = binding.goodsNameTv.getText().toString().trim();
        }

        if (TextUtils.isEmpty(binding.priceEt.getText().toString().trim())) {
            ToastUtil.show("请输入商品价格");
            return;
        } else {
            creatGoods.price = Double.parseDouble(binding.priceEt.getText().toString().trim());
        }

        if (appCategory == null) {
            ToastUtil.show("请选择商品分类");
            return;
        } else {
            creatGoods.categoryId = appCategory.id;
        }

        if (TextUtils.isEmpty(picture)) {
            ToastUtil.show("请添加商品图片");
            return;
        } else {
            creatGoods.goodsPicture = picture;
        }

        HttpApiShopGoods.creatGoods(creatGoods, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    msg = "商品添加成功";
                    Objects.requireNonNull(getActivity()).finish();
                } else {
                    msg = "商品添加失败";
                }
                ToastUtil.show(msg);
            }
        });

    }

    private boolean isNoEdit() {
        boolean a = goods.productLinks.equals(binding.goodsLinkEt.getText().toString().trim());
        boolean b = goods.goodsName.equals(binding.goodsNameTv.getText().toString().trim());
        boolean c = goods.categoryId == appCategory.id;
        boolean d = goods.goodsPicture.equals(picture);
        boolean e;
        if (TextUtils.isEmpty(binding.priceEt.getText().toString().trim())) {
            e = goods.price == 0;
        } else {
            e = goods.price == Double.parseDouble(binding.priceEt.getText().toString().trim());
        }
        return a && b && c && d && e;
    }

    private void updateGoods() {

        if (TextUtils.isEmpty(binding.goodsLinkEt.getText().toString().trim())) {
            ToastUtil.show("请输入商品链接");
            return;
        } else {
            updateGoods.productLinks = binding.goodsLinkEt.getText().toString().trim();
        }

        if (TextUtils.isEmpty(binding.goodsNameTv.getText().toString().trim())) {
            ToastUtil.show("请输入商品名称");
            return;
        } else {
            updateGoods.goodsName = binding.goodsNameTv.getText().toString().trim();
        }

        if (TextUtils.isEmpty(binding.priceEt.getText().toString().trim())) {
            ToastUtil.show("请输入商品价格");
            return;
        } else {
            updateGoods.price = Double.parseDouble(binding.priceEt.getText().toString().trim());
        }

        if (appCategory == null) {
            ToastUtil.show("请选择商品分类");
            return;
        } else {
            updateGoods.categoryId = appCategory.id;
        }

        if (TextUtils.isEmpty(picture)) {
            ToastUtil.show("请添加商品图片");
            return;
        } else {
            updateGoods.goodsPicture = picture;
        }

        HttpApiShopGoods.updateGoods(updateGoods, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    //msg = "商品修改成功";
                    Objects.requireNonNull(getActivity()).finish();
                    ToastUtil.show("商品修改成功");
                } else {
                    //msg = "商品修改失败";
                    ToastUtil.show(msg);
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == goodsClassifyCode) {
            if (resultCode == -1 && data != null) {
                appCategory = data.getParcelableExtra("classify");
                binding.goodsClassifyTv.setText(appCategory.name);
            }
        }
    }


    private void showImageDialog() {
        DialogUtil.showStringArrayDialog(getContext(), new Integer[]{
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
        final Dialog dialog = DialogUtil.loadingDialog(getContext());
        dialog.show();
        UploadUtil.getInstance().uploadPicture(1, file, new PictureUploadCallback() {
            @Override
            public void onSuccess(String imgStr) {
                picture = imgStr;
                ImageLoader.display(imgStr, binding.shopPhotoIv);
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

    }


}
