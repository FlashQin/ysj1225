package com.kalacheng.shopping.buyer.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.busshop.httpApi.HttpApiShopQuiteOrder;
import com.kalacheng.busshop.model.ApplyRefundDTO;
import com.kalacheng.commonview.activity.DynamicMakeActivity;
import com.kalacheng.commonview.activity.PictureChooseActivity;
import com.kalacheng.commonview.bean.PictureChooseBean;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.buyer.adapter.RefundReasonAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;

import static android.app.Activity.RESULT_OK;

/**
 * 买家申请退款dialog
 */
public class BuyerApplyRefundDialog extends BaseDialogFragment {
    private LinearLayout layoutType1;
    private LinearLayout layoutType2;
    private ImageView ivPicture1;
    private ImageView ivPictureDelete1;
    private ImageView ivPicture2;
    private ImageView ivPictureDelete2;
    private ImageView ivPicture3;
    private ImageView ivPictureDelete3;
    private EditText etRemark;
    private TextView tvReasonSelect;
    private TextView tvAmount;
    private RecyclerView recyclerView;
    private RefundReasonAdapter adapter;

    private Dialog mLoading;
    private List<PictureChooseBean> mPicture = new ArrayList<>();
    private String picPath = "";
    private OnBuyerApplyRefundListener listener;
    private long orderId;
    private int orderStatus;//订单状态， 2 待发货

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_buyer_apply_refund;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.BottomDialogStyle;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderId = getArguments().getLong("orderId", 0);
        orderStatus = getArguments().getInt("orderStatus", 2);
        mLoading = DialogUtil.loadingDialog(mContext);
        initView();
        getRefundReason();
    }

    private void initView() {
        etRemark = mRootView.findViewById(R.id.etRemark);
        tvReasonSelect = mRootView.findViewById(R.id.tvReasonSelect);
        tvAmount = mRootView.findViewById(R.id.tvAmount);
        layoutType1 = mRootView.findViewById(R.id.layoutType1);
        layoutType2 = mRootView.findViewById(R.id.layoutType2);
        layoutType1.setSelected(true);
        if (orderStatus == 2) {
            layoutType2.setEnabled(false);
            layoutType2.setClickable(false);
        }
        ivPicture1 = mRootView.findViewById(R.id.ivPicture1);
        ivPictureDelete1 = mRootView.findViewById(R.id.ivPictureDelete1);
        ivPicture2 = mRootView.findViewById(R.id.ivPicture2);
        ivPictureDelete2 = mRootView.findViewById(R.id.ivPictureDelete2);
        ivPicture3 = mRootView.findViewById(R.id.ivPicture3);
        ivPictureDelete3 = mRootView.findViewById(R.id.ivPictureDelete3);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new RefundReasonAdapter(mContext);
        recyclerView.setAdapter(adapter);
        adapter.setOnReasonListener(new RefundReasonAdapter.OnReasonListener() {
            @Override
            public void onSelect(String reason) {
                tvReasonSelect.setText(reason);
                mRootView.findViewById(R.id.layoutReason).setVisibility(View.GONE);
            }
        });

        mRootView.findViewById(R.id.ivDialogClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                dismiss();
            }
        });
        layoutType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (!layoutType1.isSelected()) {
                    layoutType1.setSelected(true);
                    layoutType2.setSelected(false);
                    layoutType1.setBackgroundResource(R.drawable.bg_apply_refund_reason_select);
                    layoutType2.setBackgroundResource(R.drawable.bg_apply_refund_reason_unselect);
                    ((TextView) mRootView.findViewById(R.id.tvTypeTitle1)).setTextColor(Color.parseColor("#FFFC8F3A"));
                    ((TextView) mRootView.findViewById(R.id.tvTypeTitleInfo1)).setTextColor(Color.parseColor("#FFFC8F3A"));
                    ((TextView) mRootView.findViewById(R.id.tvTypeTitle2)).setTextColor(Color.parseColor("#FF666666"));
                    ((TextView) mRootView.findViewById(R.id.tvTypeTitleInfo2)).setTextColor(Color.parseColor("#FF666666"));
                    ((TextView) mRootView.findViewById(R.id.tvRemarkTitle)).setText("备注");
                    ((TextView) mRootView.findViewById(R.id.tvRemarkInfo)).setText("");
                    mRootView.findViewById(R.id.layoutPictures).setVisibility(View.INVISIBLE);
                }
            }
        });
        layoutType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (!layoutType2.isSelected()) {
                    layoutType1.setSelected(false);
                    layoutType2.setSelected(true);
                    layoutType1.setBackgroundResource(R.drawable.bg_apply_refund_reason_unselect);
                    layoutType2.setBackgroundResource(R.drawable.bg_apply_refund_reason_select);
                    ((TextView) mRootView.findViewById(R.id.tvTypeTitle1)).setTextColor(Color.parseColor("#FF666666"));
                    ((TextView) mRootView.findViewById(R.id.tvTypeTitleInfo1)).setTextColor(Color.parseColor("#FF666666"));
                    ((TextView) mRootView.findViewById(R.id.tvTypeTitle2)).setTextColor(Color.parseColor("#FFFC8F3A"));
                    ((TextView) mRootView.findViewById(R.id.tvTypeTitleInfo2)).setTextColor(Color.parseColor("#FFFC8F3A"));
                    ((TextView) mRootView.findViewById(R.id.tvRemarkTitle)).setText("上传凭证和备注");
                    ((TextView) mRootView.findViewById(R.id.tvRemarkInfo)).setText("上传凭证更助于商家或售后处理哦～");
                    mRootView.findViewById(R.id.layoutPictures).setVisibility(View.VISIBLE);
                }
            }
        });
        mRootView.findViewById(R.id.layoutReasonSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                mRootView.findViewById(R.id.layoutReason).setVisibility(View.VISIBLE);
            }
        });
        mRootView.findViewById(R.id.layoutReason).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                mRootView.findViewById(R.id.layoutReason).setVisibility(View.GONE);
            }
        });
        mRootView.findViewById(R.id.layoutPictures).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mRootView.findViewById(R.id.layoutPictureAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (mPicture.size() >= 3) {
                    ToastUtil.show("最多3张图片");
                    return;
                }
                DialogUtil.showStringArrayDialog(mContext, new Integer[]{
                        R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
                    @Override
                    public void onItemClick(String text, int tag) {
                        if (tag == R.string.camera) {
                            DynamicMakeActivity.forward(BuyerApplyRefundDialog.this, 1, 0, false, 1, 3 - mPicture.size());
                        } else {
                            Intent intent = new Intent(mContext, PictureChooseActivity.class);
                            intent.putExtra(PictureChooseActivity.PICTURE_CHOOSE_NUM, 3 - mPicture.size());
                            startActivityForResult(intent, 1);
                        }
                    }
                });
            }
        });
        ivPicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (mPicture.size() > 0) {
                    ImagePreview.getInstance().setContext(mContext).setImage(mPicture.get(0).getPath()).setShowDownButton(false).start();
                }
            }
        });
        ivPicture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (mPicture.size() > 1) {
                    ImagePreview.getInstance().setContext(mContext).setImage(mPicture.get(1).getPath()).setShowDownButton(false).start();
                }
            }
        });
        ivPicture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (mPicture.size() > 2) {
                    ImagePreview.getInstance().setContext(mContext).setImage(mPicture.get(2).getPath()).setShowDownButton(false).start();
                }
            }
        });
        ivPictureDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                mPicture.remove(0);
                refreshPictures();
            }
        });
        ivPictureDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                mPicture.remove(1);
                refreshPictures();
            }
        });
        ivPictureDelete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                mPicture.remove(2);
                refreshPictures();
            }
        });
        mRootView.findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (adapter.getReasonId() == -1) {
                    ToastUtil.show("请选择退款原因");
                    return;
                }
                if (mLoading != null) {
                    mLoading.show();
                }
                if (layoutType1.isSelected()) {
                    applyRefund(1);
                } else {
                    if (mPicture.size() > 0) {
                        uploadPictures();
                    } else {
                        applyRefund(2);
                    }
                }
            }
        });
    }

    /**
     * 刷新图片布局
     */
    private void refreshPictures() {
        ivPicture1.setVisibility(View.INVISIBLE);
        ivPicture2.setVisibility(View.INVISIBLE);
        ivPicture3.setVisibility(View.INVISIBLE);
        ivPictureDelete1.setVisibility(View.INVISIBLE);
        ivPictureDelete2.setVisibility(View.INVISIBLE);
        ivPictureDelete3.setVisibility(View.INVISIBLE);

        if (mPicture.size() > 0) {
            ImageLoader.display(mPicture.get(0).path, ivPicture1);
            ivPicture1.setVisibility(View.VISIBLE);
            ivPictureDelete1.setVisibility(View.VISIBLE);
        }
        if (mPicture.size() > 1) {
            ImageLoader.display(mPicture.get(1).path, ivPicture2);
            ivPicture2.setVisibility(View.VISIBLE);
            ivPictureDelete2.setVisibility(View.VISIBLE);
        }
        if (mPicture.size() > 2) {
            ImageLoader.display(mPicture.get(2).path, ivPicture3);
            ivPicture3.setVisibility(View.VISIBLE);
            ivPictureDelete3.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 上传图片
     */
    private void uploadPictures() {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < mPicture.size(); i++) {
            if (!mPicture.get(i).getPath().equals("")) {
                files.add(new File(mPicture.get(i).getPath()));
            }
        }
        UploadUtil.getInstance().uploadPictures(2, files, new PictureUploadCallback() {
            @Override
            public void onSuccess(String imgStr) {
                if (!TextUtils.isEmpty(imgStr)) {
                    picPath = imgStr;
                    applyRefund(2);
                } else {
                    if (mLoading != null && mLoading.isShowing()) {
                        mLoading.dismiss();
                    }
                    ToastUtil.show("上传失败");
                }
            }

            @Override
            public void onFailure() {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                ToastUtil.show("上传失败");
            }
        });
    }

    /**
     * 获取退款原因
     */
    private void getRefundReason() {
        HttpApiShopQuiteOrder.beforApplyRefund(orderId, new HttpApiCallBack<ApplyRefundDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ApplyRefundDTO retModel) {
                if (code == 1 && retModel != null) {
                    tvAmount.setText("¥" + String.valueOf(retModel.amount));
                    adapter.setData(retModel.reasonList);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 确认申请退款
     */
    private void applyRefund(int type) {
        HttpApiShopQuiteOrder.applyRefund(orderId, adapter.getReasonId(), etRemark.getText().toString().trim(), picPath, type, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }

                if (code == 1) {
                    if (listener != null) {
                        listener.onSuccess();
                    }
                    dismiss();
                }
                ToastUtil.show(msg);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                mPicture.addAll(data.<PictureChooseBean>getParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST));
                refreshPictures();
            }
        }
    }

    public void setOnBuyerApplyRefundListener(OnBuyerApplyRefundListener listener) {
        this.listener = listener;
    }

    public interface OnBuyerApplyRefundListener {
        void onSuccess();
    }
}
