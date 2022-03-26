package com.kalacheng.shopping.seller.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopGoods;
import com.kalacheng.busshop.model.ShopGoodsAttr;
import com.kalacheng.busshop.model.ShopGoodsCategory;
import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.busshop.model_fun.ShopGoods_creatGoods;
import com.kalacheng.busshop.model_fun.ShopGoods_updateGoods;
import com.kalacheng.commonview.activity.PictureChooseActivity;
import com.kalacheng.commonview.bean.PictureChooseBean;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseFragment;
import com.kalacheng.shopping.databinding.FragmentAddAuthorityGoodsBinding;
import com.kalacheng.shopping.seller.activity.AddGoodsActivity;
import com.kalacheng.shopping.seller.adapter.MoneyValueFilter;
import com.kalacheng.shopping.seller.adapter.MyItemTouchHelper;
import com.kalacheng.shopping.seller.adapter.UpImgAdapter1;
import com.kalacheng.shopping.seller.viewmodel.AddAuthorGoodsViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.ToastUtil;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AddAuthorityGoodsFragment extends SBaseFragment<FragmentAddAuthorityGoodsBinding, AddAuthorGoodsViewModel>
        implements ImageResultCallback {

    int type;
    UpImgAdapter1 adapter8;
    UpImgAdapter1 adapter16;
    final int goodsClassifyCode = 666;

    private ProcessImageUtil mImageUtil;
    private ShopGoodsCategory appCategory;

    List<ShopGoodsAttr> list;

    ShopGoods_creatGoods creatGoods;
    private ShopGoods_updateGoods updateGoods;
    long channelId;
    String picture;
    String picture2;

    ShopGoodsDTO goods;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_add_authority_goods;
    }

    @Override
    public void initData() {

        binding.priceEt.setFilters(new InputFilter[]{new MoneyValueFilter()});
        binding.favorablePriceEt.setFilters(new InputFilter[]{new MoneyValueFilter()});

        mImageUtil = new ProcessImageUtil(getActivity());
        mImageUtil.setImageResultCallback(this);

        adapter8 = new UpImgAdapter1();
        adapter8.setListener(new UpImgAdapter1.addImgClickListener() {
            @Override
            public void addImgClick(UpImgAdapter1 adapter) {
                showImageDialog(1);
            }
        });

        adapter16 = new UpImgAdapter1("add", 16);
        adapter16.setListener(new UpImgAdapter1.addImgClickListener() {
            @Override
            public void addImgClick(UpImgAdapter1 adapter) {
                showImageDialog(2);
            }
        });

        binding.recyclerView8.setLayoutManager(new GridLayoutManager(mContext, 4));
        binding.recyclerView8.setHasFixedSize(false);
        binding.recyclerView8.setAdapter(adapter8);
        ItemTouchHelper helper8 = new ItemTouchHelper(new MyItemTouchHelper(mContext, adapter8));
        helper8.attachToRecyclerView(binding.recyclerView8);


        binding.recyclerView16.setLayoutManager(new GridLayoutManager(mContext, 4));
        binding.recyclerView16.setHasFixedSize(false);
        binding.recyclerView16.setAdapter(adapter16);
        ItemTouchHelper helper16 = new ItemTouchHelper(new MyItemTouchHelper(mContext, adapter16));
        helper16.attachToRecyclerView(binding.recyclerView16);

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

        binding.goodsAttributeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                Postcard postcard = ARouter.getInstance().build(ARouterPath.GoodsAttributeActivity).withParcelable(ARouterValueNameConfig.shopGoods, goods);
                LogisticsCenter.completion(postcard);
                Intent intent = new Intent(getActivity(), postcard.getDestination());
                intent.putExtras(postcard.getExtras());
                startActivityForResult(intent, 999);
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


        AddGoodsActivity addGoodsActivity = (AddGoodsActivity) getActivity();
        if (addGoodsActivity != null && addGoodsActivity.goods != null) {
            setGoods(addGoodsActivity.goods);
            updateGoods = new ShopGoods_updateGoods();
            updateGoods.goodsId = goods.goodsId;
            updateGoods.channelId = goods.channelId;
            updateGoods.type = 2;
        } else {
            creatGoods = new ShopGoods_creatGoods();
            creatGoods.type = 2;
        }
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
        if (creatGoods != null) {
            creatGoods.channelId = channelId;
        }

    }

    public void setGoods(ShopGoodsDTO goods) {
        this.goods = goods;
        binding.goodsNameTv.setText(goods.goodsName);

        appCategory = new ShopGoodsCategory();
        appCategory.id = goods.categoryId;
        binding.goodsClassifyTv.setText(goods.categoryName);

        List<String> list8 = Arrays.asList(goods.goodsPicture.split(","));
        adapter8.addImgs(list8);
        adapter8.notifyDataSetChanged();
        picture = goods.goodsPicture;

        binding.goodsPresentEt.setText(goods.present);

        List<String> list16 = Arrays.asList(goods.detailPicture.split(","));
        adapter16.addImgs(list16);
        adapter16.notifyDataSetChanged();
        picture2 = goods.detailPicture;

        if (TextUtils.isEmpty(goods.attrName)) {
            binding.priceEt.setText(String.valueOf(goods.price));
            binding.favorablePriceEt.setText(String.valueOf(goods.favorablePrice));
        } else {
            binding.goodsAttributeTv.setText(goods.attrName);
            binding.priceLl.setVisibility(View.GONE);
        }

        binding.addGoodsTv.setText("立即修改");
    }

    private boolean isNoEdit() {
        boolean b = goods.goodsName.equals(binding.goodsNameTv.getText().toString().trim());
        boolean c = goods.categoryId == appCategory.id;
        boolean d = goods.goodsPicture.equals(TextUtils.join(",", adapter8.getImgs()));
        //boolean e = goods.present.equals(binding.goodsPresentEt.getText().toString().trim());
        boolean f = goods.detailPicture.equals(TextUtils.join(",", adapter16.getImgs()));
        boolean g = list == null;

        boolean h;
        if (TextUtils.isEmpty(binding.priceEt.getText().toString().trim())) {
            h = goods.price == 0;
        } else {
            h = goods.price == Double.parseDouble(binding.priceEt.getText().toString().trim());
        }
        boolean i;
        if (TextUtils.isEmpty(binding.favorablePriceEt.getText().toString().trim())) {
            i = goods.favorablePrice == 0;
        } else {
            i = goods.favorablePrice == Double.parseDouble(binding.favorablePriceEt.getText().toString().trim());
        }

//        return b && c && d && e && f && g && h && i;
        return b && c && d && f && g && h && i;
    }


    private void creatGoods() {

        if (TextUtils.isEmpty(binding.goodsNameTv.getText().toString().trim())) {
            ToastUtil.show("请输入商品名称");
            return;
        } else {
            creatGoods.goodsName = binding.goodsNameTv.getText().toString().trim();
        }

        if (appCategory == null) {
            ToastUtil.show("请选择商品分类");
            return;
        } else {
            creatGoods.categoryId = appCategory.id;
        }

        if (adapter8.getImgs().size() > 0) {
            creatGoods.goodsPicture = TextUtils.join(",", adapter8.getImgs());
        } else {
            ToastUtil.show("请上传商品图片");
            return;
        }

        if (TextUtils.isEmpty(binding.goodsPresentEt.getText().toString().trim())) {
            creatGoods.present = "";
        } else {
            creatGoods.present = binding.goodsPresentEt.getText().toString().trim();
        }

        if (adapter16.getImgs().size() > 0) {
            creatGoods.detailPicture = TextUtils.join(",", adapter16.getImgs());
        } else {
            ToastUtil.show("请上传商品详情图片");
            return;
        }

        if (list != null && list.size() > 0) {
            creatGoods.goodsId = list.get(0).goodsId;
        } else {
            creatGoods.goodsId = 0;

            if (TextUtils.isEmpty(binding.priceEt.getText().toString().trim())) {
                ToastUtil.show("请填写商品价格");
                return;
            } else {
                creatGoods.price = Double.parseDouble(binding.priceEt.getText().toString().trim());
            }
            if (TextUtils.isEmpty(binding.favorablePriceEt.getText().toString().trim())) {
                creatGoods.favorablePrice = 0;
            } else {
                creatGoods.favorablePrice = Double.parseDouble(binding.favorablePriceEt.getText().toString().trim());
            }

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


    private void updateGoods() {

        if (TextUtils.isEmpty(binding.goodsNameTv.getText().toString().trim())) {
            ToastUtil.show("请输入商品名称");
            return;
        } else {
            updateGoods.goodsName = binding.goodsNameTv.getText().toString().trim();
        }

        if (appCategory == null) {
            ToastUtil.show("请选择商品分类");
            return;
        } else {
            updateGoods.categoryId = appCategory.id;
        }

        if (adapter8.getImgs().size() > 0) {
            updateGoods.goodsPicture = TextUtils.join(",", adapter8.getImgs());
        } else {
            ToastUtil.show("请上传商品图片");
            return;
        }

        if (TextUtils.isEmpty(binding.goodsPresentEt.getText().toString().trim())) {
            updateGoods.present = "";
        } else {
            updateGoods.present = binding.goodsPresentEt.getText().toString().trim();
        }

        if (adapter16.getImgs().size() > 0) {
            updateGoods.detailPicture = TextUtils.join(",", adapter16.getImgs());
        } else {
            ToastUtil.show("请上传商品详情图片");
            return;
        }

        if (TextUtils.isEmpty(binding.goodsAttributeTv.getText().toString())) {

            if (TextUtils.isEmpty(binding.priceEt.getText().toString().trim())) {
                ToastUtil.show("请填写商品价格");
            } else {
                updateGoods.price = Double.parseDouble(binding.priceEt.getText().toString().trim());
            }
            if (TextUtils.isEmpty(binding.favorablePriceEt.getText().toString().trim())) {
                updateGoods.favorablePrice = 0;
            } else {
                updateGoods.favorablePrice = Double.parseDouble(binding.favorablePriceEt.getText().toString().trim());
            }

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


    private void showImageDialog(int i) {
        type = i;
        DialogUtil.showStringArrayDialog(getContext(), new Integer[]{
                R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.camera) {
                    mImageUtil.getImageByCamera(false);
                } else if (tag == R.string.alumb) {
//                    mImageUtil.getImageByAlbumCustom(false);
                    Intent intent = new Intent(getContext(), PictureChooseActivity.class);
                    if (type == 1) {
                        intent.putExtra(PictureChooseActivity.PICTURE_CHOOSE_NUM, 8 - adapter8.getItemCount() + 1);
                    } else {
                        intent.putExtra(PictureChooseActivity.PICTURE_CHOOSE_NUM, 16 - adapter16.getItemCount() + 1);
                    }
                    startActivityForResult(intent, 888);
                }
            }
        });
    }

    @Override
    public void beforeCamera() {

    }

    @Override
    public void onSuccess(File file) {
        List<File> files = new ArrayList<>();
        files.add(file);
        uploadFiles(files);
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == goodsClassifyCode) {
            if (resultCode == -1 && data != null) {
                appCategory = data.getParcelableExtra("classify");
                binding.goodsClassifyTv.setText(appCategory.name);
            }
        } else if (requestCode == 999) {
            if (resultCode == -1 && data != null) {
                list = data.getParcelableArrayListExtra("EditPriceActivity");
                if (list != null && list.size() > 0) {
                    binding.goodsAttributeTv.setText("");
                }
                for (ShopGoodsAttr arrt : list) {
                    binding.goodsAttributeTv.append(arrt.name + " ");
                }
                binding.priceLl.setVisibility(View.GONE);
            }
        } else if (requestCode == 888) {
            if (resultCode == RESULT_OK) {
                List<PictureChooseBean> pictures = data.<PictureChooseBean>getParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST);
                List<File> files = new ArrayList<>();
                for (PictureChooseBean bean : pictures) {
                    files.add(new File(bean.getPath()));
                }
                uploadFiles(files);
            }
        }
    }

    private void uploadFiles(List<File> files) {
        final Dialog dialog = DialogUtil.loadingDialog(getContext());
        dialog.show();
        UploadUtil.getInstance().uploadPictures(1, files, new PictureUploadCallback() {
            @Override
            public void onSuccess(String imgStr) {
                if (type == 1) {
                    List<String> imgs = Arrays.asList(imgStr.split(","));
                    for (String img : imgs) {
                        adapter8.addImg(img);
                    }
                } else if (type == 2) {
                    List<String> imgs = Arrays.asList(imgStr.split(","));
                    for (String img : imgs) {
                        adapter16.addImg(img);
                    }
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
}
